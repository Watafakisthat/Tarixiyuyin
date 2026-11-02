package com.example.data.local

import com.example.data.pack.UnitsAndHeroesCatalog
import com.example.model.BattleSide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.max
import kotlin.math.min

class CenturiesRepository(
    private val dao: CenturiesDao
) {
    val missionProgressFlow: Flow<Map<String, MissionProgressEntity>> =
        dao.observeAllMissionProgress().map { list ->
            list.associateBy { it.missionId }
        }

    val unlockedHeroesFlow: Flow<Set<String>> =
        dao.observeUnlockedHeroes().map { list ->
            if (list.isEmpty()) {
                // Default initial unlocked heroes + all heroes accessible in Chronicle
                UnitsAndHeroesCatalog.allHeroes.map { it.id }.toSet()
            } else {
                list.map { it.heroId }.toSet() + setOf("amir_timur", "jalal_ad_din", "saladin", "richard_lionheart")
            }
        }

    val settingsFlow: Flow<UserSettingsEntity> =
        dao.observeUserSettings().map { it ?: UserSettingsEntity() }

    val autosaveFlow: Flow<BattleAutosaveEntity?> =
        dao.observeAutosave()

    suspend fun ensureInitialized() {
        val current = dao.getUserSettingsOnce()
        if (current == null) {
            dao.upsertUserSettings(UserSettingsEntity())
        }
        // Seed initial core heroes
        listOf("amir_timur", "genghis_subutai", "jalal_ad_din", "saladin", "richard_lionheart", "william_harold", "henry_v", "ulugh_beg").forEach { id ->
            dao.upsertUnlockedHero(UnlockedHeroEntity(heroId = id))
        }
    }

    suspend fun updateSettings(transform: (UserSettingsEntity) -> UserSettingsEntity) {
        val current = dao.getUserSettingsOnce() ?: UserSettingsEntity()
        dao.upsertUserSettings(transform(current))
    }

    suspend fun recordMissionVictory(
        missionId: String,
        campaignId: String,
        side: BattleSide,
        isWhatIf: Boolean,
        stars: Int,
        turnsTaken: Int,
        heroIdUsed: String?
    ) {
        val existing = dao.getMissionProgress(missionId)
        val clampedStars = stars.coerceIn(1, 3)
        val updated = if (existing == null) {
            MissionProgressEntity(
                missionId = missionId,
                campaignId = campaignId,
                completedSideA = side == BattleSide.SIDE_A && !isWhatIf,
                completedSideB = side == BattleSide.SIDE_B && !isWhatIf,
                completedWhatIf = isWhatIf,
                bestStars = clampedStars,
                bestTurns = turnsTaken,
                lastPlayedTimestamp = System.currentTimeMillis()
            )
        } else {
            existing.copy(
                completedSideA = existing.completedSideA || (side == BattleSide.SIDE_A && !isWhatIf),
                completedSideB = existing.completedSideB || (side == BattleSide.SIDE_B && !isWhatIf),
                completedWhatIf = existing.completedWhatIf || isWhatIf,
                bestStars = max(existing.bestStars, clampedStars),
                bestTurns = if (existing.bestTurns == 0) turnsTaken else min(existing.bestTurns, turnsTaken),
                lastPlayedTimestamp = System.currentTimeMillis()
            )
        }
        dao.upsertMissionProgress(updated)

        if (heroIdUsed != null) {
            dao.upsertUnlockedHero(
                UnlockedHeroEntity(
                    heroId = heroIdUsed,
                    battlesLed = 1,
                    victories = 1
                )
            )
        }
        // Clear mid-battle autosave upon mission completion
        dao.clearAutosave()
    }

    suspend fun saveMidBattleAutosave(autosave: BattleAutosaveEntity) {
        dao.upsertAutosave(autosave)
    }

    suspend fun clearAutosave() {
        dao.clearAutosave()
    }

    suspend fun resetAllProgress() {
        dao.clearAllMissionProgress()
        dao.clearAutosave()
    }
}
