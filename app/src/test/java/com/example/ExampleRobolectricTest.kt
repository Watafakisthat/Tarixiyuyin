package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.BattleAutosaveEntity
import com.example.data.local.CenturiesDatabase
import com.example.data.local.CenturiesRepository
import com.example.data.pack.ContentPackLoader
import com.example.model.BattleSide
import com.example.model.LanguageCode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var context: Context
    private lateinit var database: CenturiesDatabase
    private lateinit var repository: CenturiesRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = CenturiesDatabase.createInMemory(context)
        repository = CenturiesRepository(database.centuriesDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `verify app_name string resource and asset pack loader`() {
        val appName = context.getString(R.string.app_name)
        assertEquals("Centuries", appName)

        val bundle = ContentPackLoader.loadAll(context)
        assertTrue(bundle.campaigns.isNotEmpty())
        assertTrue(bundle.reviewerCredits.isNotEmpty())
    }

    @Test
    fun `room repository persists mission progress settings and mid-battle autosave`() = runBlocking {
        repository.ensureInitialized()

        // 1. Update language & colorblind setting
        repository.updateSettings {
            it.copy(languageCode = LanguageCode.UZ.code, colorblindSafeMode = true)
        }
        val settings = repository.settingsFlow.first()
        assertEquals("uz", settings.languageCode)
        assertTrue(settings.colorblindSafeMode)

        // 2. Save mid-battle autosave
        val autosave = BattleAutosaveEntity(
            missionId = "mission_kondurcha_1391",
            campaignId = "campaign_timur_rise",
            gameMode = "CAMPAIGN",
            playerSide = "SIDE_A",
            isWhatIf = false,
            turnNumber = 4,
            activeSide = "SIDE_A",
            serializedBattleStateJson = "{\"missionId\":\"mission_kondurcha_1391\"}"
        )
        repository.saveMidBattleAutosave(autosave)
        val loadedAutosave = repository.autosaveFlow.first()
        assertNotNull(loadedAutosave)
        assertEquals(4, loadedAutosave?.turnNumber)

        // 3. Record mission victory (clears autosave and saves star rating)
        repository.recordMissionVictory(
            missionId = "mission_kondurcha_1391",
            campaignId = "campaign_timur_rise",
            side = BattleSide.SIDE_A,
            isWhatIf = false,
            stars = 3,
            turnsTaken = 6,
            heroIdUsed = "amir_timur"
        )

        val progressMap = repository.missionProgressFlow.first()
        val kondurchaProgress = progressMap["mission_kondurcha_1391"]
        assertNotNull(kondurchaProgress)
        assertEquals(3, kondurchaProgress?.bestStars)
        assertTrue(kondurchaProgress?.completedSideA == true)
        assertNull("Autosave should clear after mission completion", repository.autosaveFlow.first())
    }

    @Test
    fun `autosave json serialization and deserialization preserves battle state`() {
        val indus = ContentPackLoader.findMission("mission_indus_1221", context)!!
        val original = com.example.domain.engine.HexEngine.createBattleFromMission(
            mission = indus,
            playerSide = BattleSide.SIDE_A,
            gameMode = com.example.model.GameMode.WHAT_IF,
            isWhatIf = true,
            aiDifficulty = com.example.model.AiDifficulty.COMMANDER
        )
        val json = original.toJsonString()
        val restored = com.example.domain.engine.BattleState.fromJsonString(
            json = json,
            sideAName = indus.sideAName,
            sideBName = indus.sideBName,
            sideAObjectiveDesc = indus.sideAObjectiveDesc,
            sideBObjectiveDesc = indus.sideBObjectiveDesc
        )
        assertEquals(original.missionId, restored.missionId)
        assertEquals(original.isWhatIf, restored.isWhatIf)
        assertEquals(original.units.size, restored.units.size)
        assertEquals(original.tiles.size, restored.tiles.size)
    }
}
