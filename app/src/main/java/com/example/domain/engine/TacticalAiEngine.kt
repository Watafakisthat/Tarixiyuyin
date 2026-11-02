package com.example.domain.engine

import com.example.model.AiDifficulty
import com.example.model.Era
import com.example.model.HexCoord
import com.example.model.SpecialAbilityType
import com.example.model.UnitCategory
import kotlin.math.max

/**
 * Era-aware Tactical AI Engine:
 * - Evaluates terrain defense, focus fire, flanking, hero protection, and objective control.
 * - Adapts doctrine by faction era:
 *   - Steppe AI kites with horse archers and uses Parthian Shot.
 *   - Central Asian AI coordinates siege/elephants with flanking heavy cavalry.
 *   - European AI holds shield-wall high ground or executes concentrated lance charges.
 * - Executes deterministically and rapidly without blocking the UI thread.
 */
object TacticalAiEngine {

    /**
     * Executes a single AI unit action on `state`, returning the updated `BattleState` and `true`
     * if an action was taken, or `false` when all AI units have completed their actions for the turn.
     */
    fun executeNextAiUnitStep(state: BattleState): Pair<BattleState, Boolean> {
        if (state.outcome != null) return state to false
        val aiSide = state.activeSide
        val candidateUnits = state.units.filter {
            it.side == aiSide && it.currentHp > 0 && !it.isRouted && (!it.hasMovedThisTurn || !it.hasAttackedThisTurn)
        }
        if (candidateUnits.isEmpty()) {
            return state to false
        }

        // Prioritize heroes with ready command abilities, then ranged/siege, then cavalry/infantry
        val unit = candidateUnits.sortedWith(
            compareByDescending<BattleUnitState> { it.isHero && it.heroCooldown == 0 }
                .thenByDescending { it.archetype.range }
                .thenByDescending { it.archetype.movement }
        ).first()

        var current = state

        // 1. If Hero Command Ability is ready and allies/enemies are nearby, trigger it
        if (unit.isHero && unit.heroCooldown == 0) {
            val alliesNearby = current.units.count {
                it.side == aiSide && it.currentHp > 0 && HexEngine.hexDistance(it.coord, unit.coord) <= 2
            }
            if (alliesNearby >= 2) {
                current = HexEngine.activateHeroCommandAbility(current, unit.instanceId)
                if (current.outcome != null) return current to true
            }
        }

        val refreshedUnit = current.unitById(unit.instanceId) ?: return current to true

        // 2. Evaluate best (moveHex, targetEnemyId, useSpecial) combination
        val reachable = HexEngine.computeReachableHexes(current, refreshedUnit)
        val candidateMoveHexes = (reachable.keys + refreshedUnit.coord).toList()
        val enemyUnits = current.units.filter { it.side != aiSide && it.currentHp > 0 }

        if (enemyUnits.isEmpty()) {
            return markUnitDone(current, refreshedUnit.instanceId) to true
        }

        var bestMoveHex: HexCoord = refreshedUnit.coord
        var bestTargetId: String? = null
        var bestUseSpecial = false
        var bestScore = Int.MIN_VALUE

        val effectiveRange = max(
            1,
            refreshedUnit.archetype.range +
                if (refreshedUnit.archetype.range > 1) current.weather.rangedRangeDelta else 0
        )

        for (moveHex in candidateMoveHexes) {
            val simulatedUnit = refreshedUnit.copy(
                coord = moveHex,
                distanceMovedThisTurn = HexEngine.hexDistance(refreshedUnit.coord, moveHex)
            )
            val simulatedUnits = current.units.map { if (it.instanceId == refreshedUnit.instanceId) simulatedUnit else it }
            val simulatedState = current.copy(units = simulatedUnits)

            val tile = simulatedState.tileAt(moveHex)
            var positionalScore = 0

            // Terrain defense & elevation preference
            if (tile != null) {
                positionalScore += tile.effectiveDefenseBonus
                positionalScore += tile.terrain.elevation * 6
            }

            // Objective awareness
            val targetHex = current.targetHex
            if (targetHex != null) {
                val distToTarget = HexEngine.hexDistance(moveHex, targetHex)
                positionalScore -= distToTarget * 12
                if (moveHex == targetHex) positionalScore += 80
            } else {
                val nearestEnemyDist = enemyUnits.minOf { HexEngine.hexDistance(moveHex, it.coord) }
                positionalScore -= nearestEnemyDist * 8
            }

            // Era-specific positioning
            val isSteppeKiter = refreshedUnit.factionId.era == Era.STEPPE && refreshedUnit.archetype.range > 1
            if (isSteppeKiter) {
                val adjacentEnemies = enemyUnits.count { HexEngine.hexDistance(moveHex, it.coord) == 1 }
                if (adjacentEnemies == 0) positionalScore += 25 else positionalScore -= 20
            }

            // Retreat/preserve wounded hero
            if (refreshedUnit.isHero && refreshedUnit.currentHp < refreshedUnit.maxHp * 0.35f) {
                val adjacentEnemies = enemyUnits.count { HexEngine.hexDistance(moveHex, it.coord) == 1 }
                positionalScore -= adjacentEnemies * 45
            }

            // Check attacks from `moveHex`
            val targetsInRange = enemyUnits.filter { HexEngine.hexDistance(moveHex, it.coord) <= effectiveRange }
            if (targetsInRange.isEmpty()) {
                if (positionalScore > bestScore) {
                    bestScore = positionalScore
                    bestMoveHex = moveHex
                    bestTargetId = null
                    bestUseSpecial = false
                }
            } else {
                for (enemy in targetsInRange) {
                    val canUseSpecial = simulatedUnit.abilityCooldown == 0 &&
                        simulatedUnit.archetype.specialAbility != SpecialAbilityType.SHIELD_WALL &&
                        simulatedUnit.archetype.specialAbility != SpecialAbilityType.RALLY_TROOPS &&
                        current.aiDifficulty != AiDifficulty.CHRONICLER
                    val preview = HexEngine.previewCombat(simulatedState, simulatedUnit, enemy, canUseSpecial)

                    var attackScore = positionalScore + preview.expectedDamageToDefender * 3 - preview.expectedCounterDamageToAttacker * 2
                    if (preview.defenderWillBeDefeated) attackScore += 120
                    if (preview.defenderWillRout) attackScore += 65
                    if (enemy.isHero) attackScore += 45
                    if (preview.isFlanking && current.aiDifficulty != AiDifficulty.CHRONICLER) attackScore += 25
                    if (preview.expectedWallDamage > 0) attackScore += preview.expectedWallDamage

                    if (attackScore > bestScore) {
                        bestScore = attackScore
                        bestMoveHex = moveHex
                        bestTargetId = enemy.instanceId
                        bestUseSpecial = canUseSpecial
                    }
                }
            }
        }

        // 3. Execute chosen move
        if (bestMoveHex != refreshedUnit.coord && !refreshedUnit.hasMovedThisTurn) {
            current = HexEngine.moveUnit(current, refreshedUnit.instanceId, bestMoveHex)
            if (current.outcome != null) return current to true
        }

        // 4. Execute chosen attack or support ability
        val postMoveUnit = current.unitById(refreshedUnit.instanceId) ?: return current to true
        if (bestTargetId != null && !postMoveUnit.hasAttackedThisTurn) {
            val defender = current.unitById(bestTargetId)
            if (defender != null) {
                val preview = HexEngine.previewCombat(current, postMoveUnit, defender, bestUseSpecial)
                current = HexEngine.executeConfirmedCombat(current, preview, bestUseSpecial)
            }
        } else if (!postMoveUnit.hasAttackedThisTurn && postMoveUnit.abilityCooldown == 0 &&
            (postMoveUnit.archetype.specialAbility == SpecialAbilityType.SHIELD_WALL ||
                postMoveUnit.archetype.specialAbility == SpecialAbilityType.RALLY_TROOPS)
        ) {
            current = HexEngine.activateSelfOrSupportAbility(current, postMoveUnit.instanceId)
        }

        return markUnitDone(current, refreshedUnit.instanceId) to true
    }

    private fun markUnitDone(state: BattleState, unitId: String): BattleState {
        return state.copy(
            units = state.units.map { u ->
                if (u.instanceId == unitId) {
                    u.copy(
                        hasMovedThisTurn = true,
                        hasAttackedThisTurn = true,
                        previousCoordBeforeMove = null
                    )
                } else u
            },
            selectedUnitId = null,
            reachableHexes = emptyMap(),
            attackableUnitIds = emptySet(),
            pendingCombatPreview = null
        )
    }
}
