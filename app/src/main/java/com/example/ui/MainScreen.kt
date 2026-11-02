package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.pack.ContentPackLoader
import com.example.model.AiDifficulty
import com.example.model.LanguageCode
import com.example.model.UiStrings
import com.example.ui.battle.BattleScreen
import com.example.ui.campaign.CampaignScreen
import com.example.ui.modes.ChronicleEncyclopediaScreen
import com.example.ui.modes.SettingsDialog
import com.example.ui.modes.SkirmishAndPassPlayScreen
import com.example.ui.modes.WhatIfModeScreen
import com.example.ui.theme.CenturiesTheme
import com.example.ui.theme.LapisDeepBlue
import com.example.ui.theme.LapisRoyalBlue
import com.example.ui.theme.ParchmentIvory
import com.example.ui.theme.RegistanGoldBright

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenturiesApp(viewModel: CenturiesViewModel) {
    val settings by viewModel.settingsState.collectAsStateWithLifecycle()
    val progressMap by viewModel.missionProgressState.collectAsStateWithLifecycle()
    val autosave by viewModel.autosaveState.collectAsStateWithLifecycle()
    val destination by viewModel.selectedDestination.collectAsStateWithLifecycle()
    val selectedCampaignId by viewModel.selectedCampaignId.collectAsStateWithLifecycle()
    val activeBattle by viewModel.activeBattleState.collectAsStateWithLifecycle()
    val showSettings by viewModel.showSettingsDialog.collectAsStateWithLifecycle()

    val lang = LanguageCode.fromCode(settings.languageCode)
    val defaultDifficulty = try {
        AiDifficulty.valueOf(settings.aiDifficulty)
    } catch (_: Exception) {
        AiDifficulty.COMMANDER
    }

    CenturiesTheme(textSizeScale = settings.textSizeScale) {
        val currentBattle = activeBattle
        if (currentBattle != null) {
            val missionDef = ContentPackLoader.findMission(currentBattle.missionId)
            BattleScreen(
                battleState = currentBattle,
                missionDefinition = missionDef,
                lang = lang,
                colorblindSafe = settings.colorblindSafeMode,
                onHexTapped = viewModel::onHexTapped,
                onUndoMove = viewModel::onUndoLastMove,
                onToggleOrTriggerAbility = viewModel::onToggleOrTriggerSpecialAbility,
                onTriggerHeroCommand = viewModel::onTriggerHeroCommandAbility,
                onConfirmAttack = viewModel::onConfirmPendingAttack,
                onCancelAttackPreview = viewModel::onCancelAttackPreview,
                onEndTurn = viewModel::onEndTurn,
                onDismissTutorial = viewModel::onDismissTutorial,
                onExitBattle = viewModel::onExitBattle,
                onReplayBattle = viewModel::onReplayCurrentBattle
            )
        } else {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val useNavigationRail = maxWidth >= 680.dp

                Scaffold(
                    contentWindowInsets = WindowInsets.safeDrawing,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "CENTURIES — ${UiStrings.appTagline(lang)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = { viewModel.setShowSettings(true) },
                                    modifier = Modifier.testTag("open_settings_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = UiStrings.navSettings(lang),
                                        tint = RegistanGoldBright
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = LapisDeepBlue,
                                titleContentColor = RegistanGoldBright
                            )
                        )
                    },
                    bottomBar = {
                        if (!useNavigationRail) {
                            CenturiesBottomBar(
                                current = destination,
                                lang = lang,
                                onSelect = viewModel::selectDestination
                            )
                        }
                    }
                ) { innerPadding ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (useNavigationRail) {
                            CenturiesNavigationRail(
                                current = destination,
                                lang = lang,
                                onSelect = viewModel::selectDestination
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            when (destination) {
                                TopDestination.CAMPAIGNS -> {
                                    CampaignScreen(
                                        campaigns = viewModel.contentBundle.campaigns,
                                        selectedCampaignId = selectedCampaignId,
                                        progressMap = progressMap,
                                        autosave = autosave,
                                        lang = lang,
                                        onSelectCampaign = viewModel::selectCampaign,
                                        onStartMission = viewModel::startMissionBattle,
                                        onQuickStartFeaturedBattle = viewModel::startQuickKondurchaFlagshipBattle,
                                        onResumeAutosave = viewModel::resumeAutosaveBattle
                                    )
                                }
                                TopDestination.WHAT_IF -> {
                                    WhatIfModeScreen(
                                        campaigns = viewModel.contentBundle.campaigns,
                                        lang = lang,
                                        onLaunchWhatIfMission = { mission, side ->
                                            viewModel.startMissionBattle(mission, side, isWhatIf = true)
                                        }
                                    )
                                }
                                TopDestination.SKIRMISH -> {
                                    SkirmishAndPassPlayScreen(
                                        initialPassAndPlay = false,
                                        defaultDifficulty = defaultDifficulty,
                                        lang = lang,
                                        onLaunchCustomBattle = viewModel::startCustomSkirmishBattle
                                    )
                                }
                                TopDestination.PASS_AND_PLAY -> {
                                    SkirmishAndPassPlayScreen(
                                        initialPassAndPlay = true,
                                        defaultDifficulty = defaultDifficulty,
                                        lang = lang,
                                        onLaunchCustomBattle = viewModel::startCustomSkirmishBattle
                                    )
                                }
                                TopDestination.CHRONICLE -> {
                                    ChronicleEncyclopediaScreen(
                                        bundle = viewModel.contentBundle,
                                        lang = lang
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showSettings) {
            SettingsDialog(
                settings = settings,
                lang = lang,
                onUpdateLanguage = viewModel::updateLanguage,
                onUpdateDifficulty = viewModel::updateDifficulty,
                onUpdateMusicVolume = viewModel::updateMusicVolume,
                onUpdateSfxVolume = viewModel::updateSfxVolume,
                onUpdateColorblind = viewModel::updateColorblind,
                onUpdateTextScale = viewModel::updateTextScale,
                onUpdateHaptics = viewModel::updateHaptics,
                onUpdateTts = viewModel::updateTts,
                onResetProgress = viewModel::resetAllProgress,
                onDismiss = { viewModel.setShowSettings(false) }
            )
        }
    }
}

@Composable
private fun CenturiesBottomBar(
    current: TopDestination,
    lang: LanguageCode,
    onSelect: (TopDestination) -> Unit
) {
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = LapisDeepBlue,
        selectedTextColor = RegistanGoldBright,
        indicatorColor = RegistanGoldBright,
        unselectedIconColor = ParchmentIvory.copy(alpha = 0.75f),
        unselectedTextColor = ParchmentIvory.copy(alpha = 0.75f)
    )

    NavigationBar(containerColor = LapisDeepBlue) {
        NavigationBarItem(
            selected = current == TopDestination.CAMPAIGNS,
            onClick = { onSelect(TopDestination.CAMPAIGNS) },
            icon = { Icon(Icons.Default.Map, contentDescription = null) },
            label = { Text(UiStrings.navCampaigns(lang), maxLines = 1) },
            colors = itemColors,
            modifier = Modifier.testTag("nav_campaigns")
        )
        NavigationBarItem(
            selected = current == TopDestination.WHAT_IF,
            onClick = { onSelect(TopDestination.WHAT_IF) },
            icon = { Icon(Icons.Default.HistoryEdu, contentDescription = null) },
            label = { Text(UiStrings.navWhatIf(lang), maxLines = 1) },
            colors = itemColors,
            modifier = Modifier.testTag("nav_what_if")
        )
        NavigationBarItem(
            selected = current == TopDestination.SKIRMISH,
            onClick = { onSelect(TopDestination.SKIRMISH) },
            icon = { Icon(Icons.Default.Explore, contentDescription = null) },
            label = { Text(UiStrings.navSkirmish(lang), maxLines = 1) },
            colors = itemColors,
            modifier = Modifier.testTag("nav_skirmish")
        )
        NavigationBarItem(
            selected = current == TopDestination.PASS_AND_PLAY,
            onClick = { onSelect(TopDestination.PASS_AND_PLAY) },
            icon = { Icon(Icons.Default.Group, contentDescription = null) },
            label = { Text(UiStrings.navPassPlay(lang), maxLines = 1) },
            colors = itemColors,
            modifier = Modifier.testTag("nav_pass_play")
        )
        NavigationBarItem(
            selected = current == TopDestination.CHRONICLE,
            onClick = { onSelect(TopDestination.CHRONICLE) },
            icon = { Icon(Icons.Default.AutoStories, contentDescription = null) },
            label = { Text(UiStrings.navChronicle(lang), maxLines = 1) },
            colors = itemColors,
            modifier = Modifier.testTag("nav_chronicle")
        )
    }
}

@Composable
private fun CenturiesNavigationRail(
    current: TopDestination,
    lang: LanguageCode,
    onSelect: (TopDestination) -> Unit
) {
    NavigationRail(
        containerColor = LapisRoyalBlue,
        contentColor = ParchmentIvory
    ) {
        NavigationRailItem(
            selected = current == TopDestination.CAMPAIGNS,
            onClick = { onSelect(TopDestination.CAMPAIGNS) },
            icon = { Icon(Icons.Default.Map, contentDescription = null) },
            label = { Text(UiStrings.navCampaigns(lang)) },
            modifier = Modifier.testTag("nav_campaigns")
        )
        NavigationRailItem(
            selected = current == TopDestination.WHAT_IF,
            onClick = { onSelect(TopDestination.WHAT_IF) },
            icon = { Icon(Icons.Default.HistoryEdu, contentDescription = null) },
            label = { Text(UiStrings.navWhatIf(lang)) },
            modifier = Modifier.testTag("nav_what_if")
        )
        NavigationRailItem(
            selected = current == TopDestination.SKIRMISH,
            onClick = { onSelect(TopDestination.SKIRMISH) },
            icon = { Icon(Icons.Default.Explore, contentDescription = null) },
            label = { Text(UiStrings.navSkirmish(lang)) },
            modifier = Modifier.testTag("nav_skirmish")
        )
        NavigationRailItem(
            selected = current == TopDestination.PASS_AND_PLAY,
            onClick = { onSelect(TopDestination.PASS_AND_PLAY) },
            icon = { Icon(Icons.Default.Group, contentDescription = null) },
            label = { Text(UiStrings.navPassPlay(lang)) },
            modifier = Modifier.testTag("nav_pass_play")
        )
        NavigationRailItem(
            selected = current == TopDestination.CHRONICLE,
            onClick = { onSelect(TopDestination.CHRONICLE) },
            icon = { Icon(Icons.Default.AutoStories, contentDescription = null) },
            label = { Text(UiStrings.navChronicle(lang)) },
            modifier = Modifier.testTag("nav_chronicle")
        )
    }
}
