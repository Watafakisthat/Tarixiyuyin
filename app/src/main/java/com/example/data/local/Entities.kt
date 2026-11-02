package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mission_progress")
data class MissionProgressEntity(
    @PrimaryKey val missionId: String,
    val campaignId: String,
    val completedSideA: Boolean = false,
    val completedSideB: Boolean = false,
    val completedWhatIf: Boolean = false,
    val bestStars: Int = 0, // 0..3
    val bestTurns: Int = 0,
    val lastPlayedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "unlocked_heroes")
data class UnlockedHeroEntity(
    @PrimaryKey val heroId: String,
    val unlockedAtTimestamp: Long = System.currentTimeMillis(),
    val battlesLed: Int = 0,
    val victories: Int = 0
)

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val languageCode: String = "en", // "en", "uz", "ru"
    val musicVolume: Float = 0.65f,
    val sfxVolume: Float = 0.80f,
    val animationSpeed: Float = 1.0f, // 0.75f, 1.0f, 1.5f, 2.0f
    val aiDifficulty: String = "COMMANDER", // CHRONICLER, COMMANDER, CONQUEROR
    val colorblindSafeMode: Boolean = false,
    val textSizeScale: Float = 1.0f, // 0.9f .. 1.25f
    val hapticsEnabled: Boolean = true,
    val narratedBriefingTts: Boolean = false,
    val tutorialCompleted: Boolean = false
)

@Entity(tableName = "battle_autosave")
data class BattleAutosaveEntity(
    @PrimaryKey val slotId: String = "AUTOSAVE",
    val missionId: String,
    val campaignId: String,
    val gameMode: String,
    val playerSide: String,
    val isWhatIf: Boolean,
    val turnNumber: Int,
    val activeSide: String,
    val serializedBattleStateJson: String,
    val savedAtTimestamp: Long = System.currentTimeMillis()
)
