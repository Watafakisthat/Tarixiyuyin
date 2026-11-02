package com.example.domain.future

import com.example.model.FactionId
import com.example.model.WeatherType
import java.util.Calendar
import kotlin.random.Random

/**
 * Future expansion hooks prepared as clean interfaces and deterministic offline modules.
 * Strictly disabled at launch (`isEnabled = false`) with no paywalls or store UI visible.
 */
object FutureExpansionConfig {
    const val IS_COIN_ECONOMY_ENABLED: Boolean = false
    const val IS_RESOURCE_PACKS_ENABLED: Boolean = false
    const val IS_DAILY_CHALLENGE_ENABLED: Boolean = false
    const val IS_ACHIEVEMENTS_ENABLED: Boolean = false
}

interface CoinEconomyModule {
    val isEnabled: Boolean
    fun calculateBattleRewardCoins(starsEarned: Int, isFirstCompletion: Boolean): Int
    suspend fun grantCoins(amount: Int)
    suspend fun getBalance(): Int
}

class DisabledCoinEconomyModule : CoinEconomyModule {
    override val isEnabled: Boolean = FutureExpansionConfig.IS_COIN_ECONOMY_ENABLED
    override fun calculateBattleRewardCoins(starsEarned: Int, isFirstCompletion: Boolean): Int = 0
    override suspend fun grantCoins(amount: Int) { /* Empty module at launch */ }
    override suspend fun getBalance(): Int = 0
}

data class ExternalResourcePackDescriptor(
    val packId: String,
    val title: String,
    val version: Int,
    val assetFolderPath: String,
    val containsCampaigns: Boolean,
    val containsUnitSkins: Boolean
)

interface ResourcePackExpansionHook {
    val isEnabled: Boolean
    fun listInstalledPacks(): List<ExternalResourcePackDescriptor>
}

class DisabledResourcePackExpansionHook : ResourcePackExpansionHook {
    override val isEnabled: Boolean = FutureExpansionConfig.IS_RESOURCE_PACKS_ENABLED
    override fun listInstalledPacks(): List<ExternalResourcePackDescriptor> = emptyList()
}

data class OfflineDailyChallengeSpec(
    val dateSeed: Int,
    val sideAFaction: FactionId,
    val sideBFaction: FactionId,
    val weather: WeatherType,
    val mapSeed: Long
)

interface DailyChallengeGeneratorHook {
    val isEnabled: Boolean
    fun generateForDeviceDate(year: Int, dayOfYear: Int): OfflineDailyChallengeSpec
}

class OfflineDailyChallengeGenerator : DailyChallengeGeneratorHook {
    override val isEnabled: Boolean = FutureExpansionConfig.IS_DAILY_CHALLENGE_ENABLED

    override fun generateForDeviceDate(year: Int, dayOfYear: Int): OfflineDailyChallengeSpec {
        val seed = year * 1000 + dayOfYear
        val rng = Random(seed.toLong())
        val factions = FactionId.entries
        val sideA = factions[rng.nextInt(factions.size)]
        var sideB = factions[rng.nextInt(factions.size)]
        if (sideB == sideA) {
            sideB = factions[(sideA.ordinal + 1) % factions.size]
        }
        val weather = WeatherType.entries[rng.nextInt(WeatherType.entries.size)]
        return OfflineDailyChallengeSpec(
            dateSeed = seed,
            sideAFaction = sideA,
            sideBFaction = sideB,
            weather = weather,
            mapSeed = rng.nextLong()
        )
    }

    fun generateForToday(): OfflineDailyChallengeSpec {
        val cal = Calendar.getInstance()
        return generateForDeviceDate(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.DAY_OF_YEAR)
        )
    }
}

data class AchievementDescriptor(
    val id: String,
    val titleKey: String,
    val unlocked: Boolean
)

interface AchievementHook {
    val isEnabled: Boolean
    fun checkPostBattleAchievements(missionId: String, stars: Int, turnsTaken: Int): List<String>
}

class DisabledAchievementHook : AchievementHook {
    override val isEnabled: Boolean = FutureExpansionConfig.IS_ACHIEVEMENTS_ENABLED
    override fun checkPostBattleAchievements(missionId: String, stars: Int, turnsTaken: Int): List<String> =
        emptyList()
}
