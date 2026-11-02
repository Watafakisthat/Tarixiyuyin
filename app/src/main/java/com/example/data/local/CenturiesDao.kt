package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CenturiesDao {

    @Query("SELECT * FROM mission_progress")
    fun observeAllMissionProgress(): Flow<List<MissionProgressEntity>>

    @Query("SELECT * FROM mission_progress WHERE missionId = :missionId LIMIT 1")
    suspend fun getMissionProgress(missionId: String): MissionProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMissionProgress(entity: MissionProgressEntity)

    @Query("DELETE FROM mission_progress")
    suspend fun clearAllMissionProgress()

    @Query("SELECT * FROM unlocked_heroes")
    fun observeUnlockedHeroes(): Flow<List<UnlockedHeroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUnlockedHero(entity: UnlockedHeroEntity)

    @Query("DELETE FROM unlocked_heroes")
    suspend fun clearUnlockedHeroes()

    @Query("SELECT * FROM user_settings WHERE id = 1 LIMIT 1")
    fun observeUserSettings(): Flow<UserSettingsEntity?>

    @Query("SELECT * FROM user_settings WHERE id = 1 LIMIT 1")
    suspend fun getUserSettingsOnce(): UserSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserSettings(settings: UserSettingsEntity)

    @Query("SELECT * FROM battle_autosave WHERE slotId = 'AUTOSAVE' LIMIT 1")
    fun observeAutosave(): Flow<BattleAutosaveEntity?>

    @Query("SELECT * FROM battle_autosave WHERE slotId = 'AUTOSAVE' LIMIT 1")
    suspend fun getAutosaveOnce(): BattleAutosaveEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAutosave(autosave: BattleAutosaveEntity)

    @Query("DELETE FROM battle_autosave WHERE slotId = 'AUTOSAVE'")
    suspend fun clearAutosave()
}
