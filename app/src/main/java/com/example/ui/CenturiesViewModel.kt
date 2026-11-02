package com.example.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundEngine
import com.example.data.local.BattleAutosaveEntity
import com.example.data.local.CenturiesDatabase
import com.example.data.local.CenturiesRepository
import com.example.data.local.MissionProgressEntity
import com.example.data.local.UserSettingsEntity
import com.example.data.pack.ContentPackLoader
import com.example.data.pack.LoadedContentBundle
import com.example.domain.engine.BattleState
import com.example.domain.engine.HexEngine
import com.example.domain.engine.TacticalAiEngine
import com.example.model.AiDifficulty
import com.example.model.BattleSide
import com.example.model.FactionId
import com.example.model.GameMode
import com.example.model.HexCoord
import com.example.model.LanguageCode
import com.example.model.LocalizedText
import com.example.model.MissionDefinition
import com.example.model.UnitCategory
import com.example.model.WeatherType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class TopDestination {
    CAMPAIGNS,
    WHAT_IF,
    SKIRMISH,
    PASS_AND_PLAY,
    CHRONICLE
}

class CenturiesViewModel(
    private val repository: CenturiesRepository,
    val contentBundle: LoadedContentBundle,
    private val soundEngine: SoundEngine
) : ViewModel() {

    val settingsState: StateFlow<UserSettingsEntity> = repository.settingsFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserSettingsEntity())

    val missionProgressState: StateFlow<Map<String, MissionProgressEntity>> = repository.missionProgressFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyMap())

    val autosaveState: StateFlow<BattleAutosaveEntity?> = repository.autosaveFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _selectedDestination = MutableStateFlow(TopDestination.CAMPAIGNS)
    val selectedDestination: StateFlow<TopDestination> = _selectedDestination.asStateFlow()

    private val _selectedCampaignId = MutableStateFlow(
        contentBundle.campaigns.firstOrNull()?.id ?: "campaign_timur_rise"
    )
    val selectedCampaignId: StateFlow<String> = _selectedCampaignId.asStateFlow()

    private val _activeBattleState = MutableStateFlow<BattleState?>(null)
    val activeBattleState: StateFlow<BattleState?> = _activeBattleState.asStateFlow()

    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog: StateFlow<Boolean> = _showSettingsDialog.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureInitialized()
        }
        viewModelScope.launch {
            settingsState.collect { s ->
                soundEngine.musicVolume = s.musicVolume
                soundEngine.sfxVolume = s.sfxVolume
                soundEngine.hapticsEnabled = s.hapticsEnabled
                soundEngine.ttsEnabled = s.narratedBriefingTts
            }
        }
    }

    fun selectDestination(dest: TopDestination) {
        soundEngine.playTap()
        _selectedDestination.value = dest
    }

    fun selectCampaign(campaignId: String) {
        soundEngine.playTap()
        _selectedCampaignId.value = campaignId
        val campaign = contentBundle.campaigns.firstOrNull { it.id == campaignId }
        if (campaign != null) {
            soundEngine.setCampaignTheme(campaign.musicThemeId)
        }
    }

    fun setShowSettings(show: Boolean) {
        soundEngine.playTap()
        _showSettingsDialog.value = show
    }

    fun startMissionBattle(
        mission: MissionDefinition,
        playerSide: BattleSide = BattleSide.SIDE_A,
        isWhatIf: Boolean = false
    ) {
        soundEngine.playBattleHorn()
        val settings = settingsState.value
        val difficulty = try {
            AiDifficulty.valueOf(settings.aiDifficulty)
        } catch (_: Exception) {
            AiDifficulty.COMMANDER
        }
        val state = HexEngine.createBattleFromMission(
            mission = mission,
            playerSide = playerSide,
            gameMode = if (isWhatIf) GameMode.WHAT_IF else GameMode.CAMPAIGN,
            isWhatIf = isWhatIf,
            aiDifficulty = difficulty,
            enableTutorial = !settings.tutorialCompleted && mission.isTutorialFeatured
        )
        // Auto-select the player's hero or first unit so reachable hexes are immediately visible!
        val initialUnit = state.units.firstOrNull { it.side == state.activeSide && it.isHero }
            ?: state.units.firstOrNull { it.side == state.activeSide }
        val selectedState = HexEngine.selectUnit(state, initialUnit?.instanceId)
        _activeBattleState.value = selectedState
        persistMidBattleAutosave(selectedState)

        val lang = LanguageCode.fromCode(settings.languageCode)
        soundEngine.narrateBriefingIfEnabled(mission.briefing.get(lang), lang.code)

        if (!selectedState.isPlayerTurn()) {
            triggerAiTurnLoop()
        }
    }

    fun startQuickKondurchaFlagshipBattle() {
        val kondurcha = ContentPackLoader.findMission("mission_kondurcha_1391")
            ?: contentBundle.campaigns.first().missions.first()
        startMissionBattle(kondurcha, BattleSide.SIDE_A, isWhatIf = false)
    }

    fun startCustomSkirmishBattle(
        sideAFaction: FactionId,
        sideBFaction: FactionId,
        weather: WeatherType,
        difficulty: AiDifficulty,
        isPassAndPlay: Boolean,
        seed: Long
    ) {
        soundEngine.playBattleHorn()
        val state = HexEngine.createSkirmishBattle(
            sideAFaction = sideAFaction,
            sideBFaction = sideBFaction,
            weather = weather,
            aiDifficulty = difficulty,
            isPassAndPlay = isPassAndPlay,
            mapSeed = seed
        )
        val initialUnit = state.units.firstOrNull { it.side == state.activeSide && it.isHero }
            ?: state.units.firstOrNull { it.side == state.activeSide }
        val selectedState = HexEngine.selectUnit(state, initialUnit?.instanceId)
        _activeBattleState.value = selectedState
        persistMidBattleAutosave(selectedState)
    }

    fun resumeAutosaveBattle() {
        val saved = autosaveState.value ?: return
        val mission = ContentPackLoader.findMission(saved.missionId)
        try {
            val restored = BattleState.fromJsonString(
                json = saved.serializedBattleStateJson,
                sideAName = mission?.sideAName ?: LocalizedText("Side A"),
                sideBName = mission?.sideBName ?: LocalizedText("Side B"),
                sideAObjectiveDesc = mission?.sideAObjectiveDesc ?: LocalizedText("Defeat enemy army"),
                sideBObjectiveDesc = mission?.sideBObjectiveDesc ?: LocalizedText("Defeat enemy army")
            )
            soundEngine.playBattleHorn()
            _activeBattleState.value = restored
        } catch (_: Exception) {
            viewModelScope.launch { repository.clearAutosave() }
        }
    }

    fun onHexTapped(coord: HexCoord) {
        val current = _activeBattleState.value ?: return
        if (current.outcome != null || !current.isPlayerTurn()) return

        val tappedUnit = current.unitAt(coord)
        val selectedUnit = current.unitById(current.selectedUnitId)

        // Case 1: Tapped an attackable enemy unit while a friendly unit is selected
        if (selectedUnit != null && tappedUnit != null &&
            tappedUnit.side != current.activeSide &&
            tappedUnit.instanceId in current.attackableUnitIds
        ) {
            soundEngine.playTap()
            val next = HexEngine.requestOrConfirmAttack(
                state = current,
                attackerId = selectedUnit.instanceId,
                defenderId = tappedUnit.instanceId
            )
            // If attack executed (pendingCombatPreview became null), play combat SFX
            if (current.pendingCombatPreview != null && next.pendingCombatPreview == null) {
                playUnitAttackSfx(selectedUnit.archetype.category)
            }
            updateBattleStateAndCheckOutcome(next)
            return
        }

        // Case 2: Tapped a reachable empty hex while a friendly unit is selected -> Move!
        if (selectedUnit != null && coord in current.reachableHexes) {
            soundEngine.playHooves()
            val next = HexEngine.moveUnit(current, selectedUnit.instanceId, coord)
            updateBattleStateAndCheckOutcome(next)
            return
        }

        // Case 3: Tapped any unit -> Select/Inspect it
        if (tappedUnit != null) {
            soundEngine.playTap()
            _activeBattleState.value = HexEngine.selectUnit(current, tappedUnit.instanceId)
            return
        }

        // Case 4: Tapped empty non-reachable hex -> Deselect
        _activeBattleState.value = HexEngine.selectUnit(current, null)
    }

    fun onUndoLastMove() {
        val current = _activeBattleState.value ?: return
        val selectedId = current.selectedUnitId ?: return
        soundEngine.playTap()
        _activeBattleState.value = HexEngine.undoLastMove(current, selectedId)
    }

    fun onToggleOrTriggerSpecialAbility() {
        val current = _activeBattleState.value ?: return
        val selectedId = current.selectedUnitId ?: return
        soundEngine.playTap()
        val next = HexEngine.activateSelfOrSupportAbility(current, selectedId)
        updateBattleStateAndCheckOutcome(next)
    }

    fun onTriggerHeroCommandAbility() {
        val current = _activeBattleState.value ?: return
        val selectedId = current.selectedUnitId ?: return
        soundEngine.playBattleHorn()
        val next = HexEngine.activateHeroCommandAbility(current, selectedId)
        updateBattleStateAndCheckOutcome(next)
    }

    fun onConfirmPendingAttack() {
        val current = _activeBattleState.value ?: return
        val preview = current.pendingCombatPreview ?: return
        val attacker = current.unitById(preview.attackerId)
        if (attacker != null) {
            playUnitAttackSfx(attacker.archetype.category)
        }
        val next = HexEngine.executeConfirmedCombat(current, preview, current.useSpecialAbilityOnConfirm)
        updateBattleStateAndCheckOutcome(next)
    }

    fun onCancelAttackPreview() {
        val current = _activeBattleState.value ?: return
        _activeBattleState.value = current.copy(pendingCombatPreview = null)
    }

    fun onEndTurn() {
        val current = _activeBattleState.value ?: return
        if (current.outcome != null || !current.isPlayerTurn()) return
        soundEngine.playTap()

        val afterEndTurn = HexEngine.endTurn(current)
        updateBattleStateAndCheckOutcome(afterEndTurn)

        if (afterEndTurn.outcome == null && !afterEndTurn.isPlayerTurn()) {
            triggerAiTurnLoop()
        } else if (afterEndTurn.outcome == null && afterEndTurn.isPlayerTurn()) {
            val firstUnit = afterEndTurn.units.firstOrNull { it.side == afterEndTurn.activeSide && !it.isRouted }
            _activeBattleState.value = HexEngine.selectUnit(afterEndTurn, firstUnit?.instanceId)
        }
    }

    fun onDismissTutorial() {
        val current = _activeBattleState.value ?: return
        _activeBattleState.value = current.copy(tutorialStep = 0)
        viewModelScope.launch {
            repository.updateSettings { it.copy(tutorialCompleted = true) }
        }
    }

    fun onExitBattle() {
        soundEngine.stopNarration()
        _activeBattleState.value = null
    }

    fun onReplayCurrentBattle() {
        val current = _activeBattleState.value ?: return
        val mission = ContentPackLoader.findMission(current.missionId)
        if (mission != null) {
            startMissionBattle(mission, current.playerSide, current.isWhatIf)
        } else {
            startCustomSkirmishBattle(
                sideAFaction = current.sideAFaction,
                sideBFaction = current.sideBFaction,
                weather = current.weather,
                difficulty = current.aiDifficulty,
                isPassAndPlay = current.gameMode == GameMode.PASS_AND_PLAY,
                seed = System.currentTimeMillis()
            )
        }
    }

    private fun triggerAiTurnLoop() {
        viewModelScope.launch {
            val animSpeed = settingsState.value.animationSpeed.coerceIn(0.5f, 2.5f)
            val stepDelay = (220L / animSpeed).toLong().coerceAtLeast(60L)

            while (true) {
                val state = _activeBattleState.value ?: break
                if (state.outcome != null || state.isPlayerTurn()) break

                delay(stepDelay)
                val (nextState, acted) = TacticalAiEngine.executeNextAiUnitStep(state)
                if (!acted) {
                    val backToPlayer = HexEngine.endTurn(nextState)
                    val firstPlayerUnit = backToPlayer.units.firstOrNull {
                        it.side == backToPlayer.activeSide && it.isHero
                    } ?: backToPlayer.units.firstOrNull { it.side == backToPlayer.activeSide }
                    val readyState = HexEngine.selectUnit(backToPlayer, firstPlayerUnit?.instanceId)
                    updateBattleStateAndCheckOutcome(readyState)
                    break
                } else {
                    updateBattleStateAndCheckOutcome(nextState)
                }
            }
        }
    }

    private fun updateBattleStateAndCheckOutcome(newState: BattleState) {
        _activeBattleState.value = newState
        val outcome = newState.outcome
        if (outcome != null) {
            if (outcome.playerWon) {
                soundEngine.playVictoryFanfare()
                viewModelScope.launch {
                    val heroId = newState.units.firstOrNull { it.side == newState.playerSide && it.isHero }?.heroId
                    repository.recordMissionVictory(
                        missionId = newState.missionId,
                        campaignId = newState.campaignId,
                        side = newState.playerSide,
                        isWhatIf = newState.isWhatIf,
                        stars = outcome.starsEarned,
                        turnsTaken = outcome.turnsTaken,
                        heroIdUsed = heroId
                    )
                }
            } else {
                viewModelScope.launch {
                    repository.clearAutosave()
                }
            }
        } else if (newState.isPlayerTurn()) {
            persistMidBattleAutosave(newState)
        }
    }

    private fun persistMidBattleAutosave(state: BattleState) {
        viewModelScope.launch {
            try {
                repository.saveMidBattleAutosave(
                    BattleAutosaveEntity(
                        missionId = state.missionId,
                        campaignId = state.campaignId,
                        gameMode = state.gameMode.name,
                        playerSide = state.playerSide.name,
                        isWhatIf = state.isWhatIf,
                        turnNumber = state.turnNumber,
                        activeSide = state.activeSide.name,
                        serializedBattleStateJson = state.toJsonString()
                    )
                )
            } catch (_: Exception) {
            }
        }
    }

    private fun playUnitAttackSfx(category: UnitCategory) {
        when (category) {
            UnitCategory.RANGED, UnitCategory.HORSE_ARCHER -> soundEngine.playArrowVolley()
            UnitCategory.SIEGE, UnitCategory.ELEPHANT -> soundEngine.playSiegeImpact()
            else -> soundEngine.playSwordClash()
        }
    }

    // Settings updates
    fun updateLanguage(lang: LanguageCode) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(languageCode = lang.code) }
        }
    }

    fun updateDifficulty(diff: AiDifficulty) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(aiDifficulty = diff.name) }
        }
    }

    fun updateMusicVolume(vol: Float) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(musicVolume = vol.coerceIn(0f, 1f)) }
        }
    }

    fun updateSfxVolume(vol: Float) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(sfxVolume = vol.coerceIn(0f, 1f)) }
        }
    }

    fun updateColorblind(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(colorblindSafeMode = enabled) }
        }
    }

    fun updateTextScale(scale: Float) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(textSizeScale = scale.coerceIn(0.9f, 1.25f)) }
        }
    }

    fun updateHaptics(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(hapticsEnabled = enabled) }
        }
    }

    fun updateTts(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(narratedBriefingTts = enabled) }
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val db = CenturiesDatabase.getInstance(context)
                    val repo = CenturiesRepository(db.centuriesDao())
                    val bundle = ContentPackLoader.loadAll(context)
                    val sound = SoundEngine(context.applicationContext)
                    return CenturiesViewModel(repo, bundle, sound) as T
                }
            }
        }
    }
}
