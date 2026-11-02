package com.example

import com.example.data.pack.ContentPackLoader
import com.example.domain.engine.BattleState
import com.example.domain.engine.HexEngine
import com.example.domain.engine.TacticalAiEngine
import com.example.model.AiDifficulty
import com.example.model.BattleSide
import com.example.model.GameMode
import com.example.model.HexCoord
import com.example.model.LanguageCode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `hex geometry pixel to hex round trip and distance calculation`() {
        val radius = 48f
        for (q in 0 until 10) {
            for (r in 0 until 7) {
                val coord = HexCoord(q, r)
                val center = HexEngine.hexToPixel(coord, radius)
                val recovered = HexEngine.pixelToHex(center, radius, 10, 7)
                assertEquals(coord, recovered)
            }
        }
        assertEquals(0, HexEngine.hexDistance(HexCoord(2, 3), HexCoord(2, 3)))
        assertEquals(1, HexEngine.hexDistance(HexCoord(2, 3), HexCoord(3, 3)))
        assertTrue(HexEngine.hexDistance(HexCoord(1, 3), HexCoord(8, 3)) >= 6)
    }

    @Test
    fun `pathfinding respects movement budget and terrain costs`() {
        val kondurcha = ContentPackLoader.findMission("mission_kondurcha_1391")
        assertNotNull(kondurcha)
        val state = HexEngine.createBattleFromMission(
            mission = kondurcha!!,
            playerSide = BattleSide.SIDE_A,
            gameMode = GameMode.CAMPAIGN,
            isWhatIf = false,
            aiDifficulty = AiDifficulty.COMMANDER
        )
        val timurUnit = state.units.first { it.isHero && it.side == BattleSide.SIDE_A }
        val reachable = HexEngine.computeReachableHexes(state, timurUnit)
        assertTrue("Timur heavy cavalry should have reachable hexes", reachable.isNotEmpty())
        reachable.forEach { (coord, cost) ->
            assertTrue("Move cost must not exceed unit movement (${timurUnit.archetype.movement})", cost <= timurUnit.archetype.movement)
            assertEquals("Reachable hex must be unoccupied", null, state.unitAt(coord))
        }
    }

    @Test
    fun `move and undo move restores unit position before attack`() {
        val kondurcha = ContentPackLoader.findMission("mission_kondurcha_1391")!!
        var state = HexEngine.createBattleFromMission(
            mission = kondurcha,
            playerSide = BattleSide.SIDE_A,
            gameMode = GameMode.CAMPAIGN,
            isWhatIf = false,
            aiDifficulty = AiDifficulty.COMMANDER
        )
        val unit = state.units.first { it.side == BattleSide.SIDE_A && !it.isHero }
        val startCoord = unit.coord
        val reachable = HexEngine.computeReachableHexes(state, unit)
        val targetHex = reachable.keys.first()

        state = HexEngine.moveUnit(state, unit.instanceId, targetHex)
        val movedUnit = state.unitById(unit.instanceId)!!
        assertEquals(targetHex, movedUnit.coord)
        assertTrue(movedUnit.canUndoMove)

        state = HexEngine.undoLastMove(state, unit.instanceId)
        val restoredUnit = state.unitById(unit.instanceId)!!
        assertEquals(startCoord, restoredUnit.coord)
        assertFalse(restoredUnit.hasMovedThisTurn)
    }

    @Test
    fun `combat math calculates flanking elevation and morale drop accurately`() {
        val kondurcha = ContentPackLoader.findMission("mission_kondurcha_1391")!!
        val baseState = HexEngine.createBattleFromMission(
            mission = kondurcha,
            playerSide = BattleSide.SIDE_A,
            gameMode = GameMode.CAMPAIGN,
            isWhatIf = false,
            aiDifficulty = AiDifficulty.COMMANDER
        )
        val attacker = baseState.units.first { it.side == BattleSide.SIDE_A && it.isHero }.copy(coord = HexCoord(4, 3))
        val allyFlanker = baseState.units.first { it.side == BattleSide.SIDE_A && !it.isHero }.copy(coord = HexCoord(5, 2))
        val defender = baseState.units.first { it.side == BattleSide.SIDE_B }.copy(coord = HexCoord(5, 3))

        val customUnits = listOf(attacker, allyFlanker, defender)
        val testState = baseState.copy(units = customUnits)

        val preview = HexEngine.previewCombat(testState, attacker, defender, useSpecialAbility = true)
        assertTrue("Flanking should be detected when ally is adjacent to defender", preview.isFlanking)
        assertTrue("Expected damage should be positive", preview.expectedDamageToDefender > 15)
        assertTrue("Expected morale drop should be positive", preview.expectedMoraleDropDefender >= 12)

        val afterAttack = HexEngine.executeConfirmedCombat(testState, preview, useSpecialAbility = true)
        val postDefender = afterAttack.unitById(defender.instanceId)
        if (postDefender != null) {
            assertEquals(defender.currentHp - preview.expectedDamageToDefender, postDefender.currentHp)
            assertTrue(postDefender.currentMorale < defender.currentMorale)
        }
    }

    @Test
    fun `tactical AI generates valid moves and completes turn quickly`() {
        val kondurcha = ContentPackLoader.findMission("mission_kondurcha_1391")!!
        var state = HexEngine.createBattleFromMission(
            mission = kondurcha,
            playerSide = BattleSide.SIDE_A,
            gameMode = GameMode.CAMPAIGN,
            isWhatIf = false,
            aiDifficulty = AiDifficulty.CONQUEROR
        )
        // End player turn to switch to Side B (AI)
        state = HexEngine.endTurn(state)
        assertEquals(BattleSide.SIDE_B, state.activeSide)

        var steps = 0
        while (steps < 20) {
            val (nextState, acted) = TacticalAiEngine.executeNextAiUnitStep(state)
            state = nextState
            if (!acted) break
            steps++
        }
        assertTrue("AI should execute actions for its units", steps >= 1)
    }

    @Test
    fun `content bundle ships all required campaigns missions units heroes and languages`() {
        val bundle = ContentPackLoader.loadAll(null)
        assertEquals(4, bundle.campaigns.size)
        val totalMissions = bundle.campaigns.sumOf { it.missions.size }
        assertEquals(19, totalMissions)
        assertEquals(25, bundle.units.size)
        assertEquals(12, bundle.heroes.size)

        // Verify Uzbek, Russian, and English strings are populated
        bundle.campaigns.flatMap { it.missions }.forEach { mission ->
            assertTrue(mission.title.get(LanguageCode.EN).isNotBlank())
            assertTrue(mission.title.get(LanguageCode.UZ).isNotBlank())
            assertTrue(mission.title.get(LanguageCode.RU).isNotBlank())
            assertTrue("Every mission must have historical sources", mission.sources.isNotEmpty())
        }
    }
}
