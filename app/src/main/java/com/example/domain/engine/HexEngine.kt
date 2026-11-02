package com.example.domain.engine

import androidx.compose.ui.geometry.Offset
import com.example.data.pack.UnitsAndHeroesCatalog
import com.example.model.AiDifficulty
import com.example.model.BattleSide
import com.example.model.FactionId
import com.example.model.GameMode
import com.example.model.HeroActiveEffectType
import com.example.model.HexCoord
import com.example.model.LocalizedText
import com.example.model.MissionDefinition
import com.example.model.ObjectiveType
import com.example.model.SpecialAbilityType
import com.example.model.TerrainType
import com.example.model.UnitCategory
import com.example.model.WeatherType
import java.util.PriorityQueue
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Pure Kotlin Hex Grid Tactical Engine:
 * - Flat-top hex geometry & pixel conversions
 * - Dijkstra shortest pathfinding with terrain, weather, faction, and ZOC rules
 * - Combat preview & execution with flanking, rear attack, elevation, hero auras, morale, and siege wall integrity
 */
object HexEngine {

    private val SQRT_3 = sqrt(3.0).toFloat()

    /**
     * Converts rectangular grid (col = q, row = r) to pixel center for a flat-top hex grid.
     * Uses offset-adjusted visual rows so a `mapWidth x mapHeight` board forms a neat rectangle on screen!
     */
    fun hexToPixel(coord: HexCoord, hexRadius: Float): Offset {
        val x = hexRadius * 1.5f * coord.q
        val y = hexRadius * SQRT_3 * (coord.r + 0.5f * (coord.q and 1))
        return Offset(x, y)
    }

    /**
     * Converts screen pixel (in map space) to the nearest grid coordinate `(q, r)` within bounds.
     */
    fun pixelToHex(
        pixel: Offset,
        hexRadius: Float,
        mapWidth: Int,
        mapHeight: Int
    ): HexCoord? {
        var bestCoord: HexCoord? = null
        var bestDistSq = Float.MAX_VALUE
        val maxAcceptSq = (hexRadius * 1.05f) * (hexRadius * 1.05f)

        for (q in 0 until mapWidth) {
            for (r in 0 until mapHeight) {
                val candidate = HexCoord(q, r)
                val center = hexToPixel(candidate, hexRadius)
                val dx = pixel.x - center.x
                val dy = pixel.y - center.y
                val dSq = dx * dx + dy * dy
                if (dSq < bestDistSq && dSq <= maxAcceptSq) {
                    bestDistSq = dSq
                    bestCoord = candidate
                }
            }
        }
        return bestCoord
    }

    /**
     * Returns the 6 flat-top polygon vertices around `center`.
     */
    fun hexVertices(center: Offset, hexRadius: Float): List<Offset> {
        return (0 until 6).map { i ->
            val angleRad = (60.0 * i) * PI / 180.0
            Offset(
                x = center.x + (hexRadius * cos(angleRad)).toFloat(),
                y = center.y + (hexRadius * sin(angleRad)).toFloat()
            )
        }
    }

    /**
     * Offset-aware hex neighbors for our rectangular flat-top column-offset grid (`q` = col, `r` = row).
     * Even columns (`q & 1 == 0`) vs Odd columns (`q & 1 == 1`) have exact 6-neighbor adjacency.
     */
    fun offsetNeighbors(coord: HexCoord, mapWidth: Int = 100, mapHeight: Int = 100): List<HexCoord> {
        val q = coord.q
        val r = coord.r
        val deltas = if ((q and 1) == 0) {
            listOf(
                1 to 0,   // 0: SE
                1 to -1,  // 1: NE
                0 to -1,  // 2: N
                -1 to -1, // 3: NW
                -1 to 0,  // 4: SW
                0 to 1    // 5: S
            )
        } else {
            listOf(
                1 to 1,   // 0: SE
                1 to 0,   // 1: NE
                0 to -1,  // 2: N
                -1 to 0,  // 3: NW
                -1 to 1,  // 4: SW
                0 to 1    // 5: S
            )
        }
        return deltas.map { (dq, dr) -> HexCoord(q + dq, r + dr) }
            .filter { it.q in 0 until mapWidth && it.r in 0 until mapHeight }
    }

    /**
     * Exact hex step distance on our column-offset flat-top grid by converting `(col, row)` to cube coordinates.
     */
    fun hexDistance(a: HexCoord, b: HexCoord): Int {
        val ax = a.q
        val az = a.r - (a.q - (a.q and 1)) / 2
        val ay = -ax - az

        val bx = b.q
        val bz = b.r - (b.q - (b.q and 1)) / 2
        val by = -bx - bz

        return max(abs(ax - bx), max(abs(ay - by), abs(az - bz)))
    }

    /**
     * Computes facing direction (0..5) from `from` toward `to`.
     */
    fun computeFacing(from: HexCoord, to: HexCoord): Int {
        return if (to.q > from.q) {
            if (to.r < from.r) 1 else 0
        } else if (to.q < from.q) {
            if (to.r < from.r) 3 else 4
        } else {
            if (to.r < from.r) 2 else 5
        }
    }

    /**
     * Creates a new `BattleState` from a `MissionDefinition` (or What-If variant).
     */
    fun createBattleFromMission(
        mission: MissionDefinition,
        playerSide: BattleSide,
        gameMode: GameMode,
        isWhatIf: Boolean,
        aiDifficulty: AiDifficulty,
        enableTutorial: Boolean = false
    ): BattleState {
        val tiles = mutableMapOf<HexCoord, HexTileState>()
        for (q in 0 until mission.mapWidth) {
            for (r in 0 until mission.mapHeight) {
                val c = HexCoord(q, r)
                tiles[c] = HexTileState(c, mission.defaultTerrain)
            }
        }
        mission.terrainOverrides.forEach { spec ->
            val c = HexCoord(spec.q, spec.r)
            if (spec.q in 0 until mission.mapWidth && spec.r in 0 until mission.mapHeight) {
                tiles[c] = HexTileState(c, spec.terrain, spec.label)
            }
        }

        val units = mutableListOf<BattleUnitState>()
        val sideASpawns = if (isWhatIf) mission.sideASpawns + mission.whatIfSideABonusUnits else mission.sideASpawns
        val sideBSpawns = if (isWhatIf) mission.sideBSpawns + mission.whatIfSideBBonusUnits else mission.sideBSpawns

        sideASpawns.forEachIndexed { idx, spawn ->
            val arch = UnitsAndHeroesCatalog.unitById(spawn.archetypeId)
            val heroBonusHp = if (spawn.isHero) 35 else 0
            val franceBonusMorale = if (mission.sideAFaction == FactionId.FRANCE) 10 else 0
            units.add(
                BattleUnitState(
                    instanceId = "A_${idx}_${spawn.archetypeId}",
                    archetypeId = spawn.archetypeId,
                    customName = spawn.customName ?: spawn.heroId?.let { UnitsAndHeroesCatalog.heroById(it).name.en },
                    side = BattleSide.SIDE_A,
                    factionId = mission.sideAFaction,
                    coord = HexCoord(spawn.q, spawn.r),
                    facing = 0,
                    currentHp = arch.maxHp + heroBonusHp,
                    maxHp = arch.maxHp + heroBonusHp,
                    currentMorale = min(100, arch.maxMorale + franceBonusMorale),
                    maxMorale = arch.maxMorale,
                    isHero = spawn.isHero,
                    heroId = spawn.heroId
                )
            )
        }

        sideBSpawns.forEachIndexed { idx, spawn ->
            val arch = UnitsAndHeroesCatalog.unitById(spawn.archetypeId)
            val heroBonusHp = if (spawn.isHero) 35 else 0
            val aiBonusMorale = if (playerSide == BattleSide.SIDE_A && gameMode != GameMode.PASS_AND_PLAY) aiDifficulty.aiMoraleBonus else 0
            val coord = if (units.any { it.coord == HexCoord(spawn.q, spawn.r) }) {
                HexCoord((spawn.q + 1).coerceAtMost(mission.mapWidth - 1), spawn.r)
            } else {
                HexCoord(spawn.q, spawn.r)
            }
            units.add(
                BattleUnitState(
                    instanceId = "B_${idx}_${spawn.archetypeId}",
                    archetypeId = spawn.archetypeId,
                    customName = spawn.customName ?: spawn.heroId?.let { UnitsAndHeroesCatalog.heroById(it).name.en },
                    side = BattleSide.SIDE_B,
                    factionId = mission.sideBFaction,
                    coord = coord,
                    facing = 3,
                    currentHp = arch.maxHp + heroBonusHp,
                    maxHp = arch.maxHp + heroBonusHp,
                    currentMorale = min(100, arch.maxMorale + aiBonusMorale),
                    maxMorale = arch.maxMorale,
                    isHero = spawn.isHero,
                    heroId = spawn.heroId
                )
            )
        }

        val activeWeather = if (isWhatIf && mission.whatIfWeatherOverride != null) {
            mission.whatIfWeatherOverride
        } else {
            mission.weather
        }

        return BattleState(
            missionId = mission.id,
            campaignId = mission.campaignId,
            gameMode = gameMode,
            isWhatIf = isWhatIf,
            playerSide = playerSide,
            aiDifficulty = aiDifficulty,
            mapWidth = mission.mapWidth,
            mapHeight = mission.mapHeight,
            tiles = tiles,
            units = units,
            weather = activeWeather,
            turnNumber = 1,
            maxTurns = mission.maxTurns,
            activeSide = BattleSide.SIDE_A,
            sideAFaction = mission.sideAFaction,
            sideBFaction = mission.sideBFaction,
            sideAName = mission.sideAName,
            sideBName = mission.sideBName,
            sideAObjectiveType = mission.sideAObjectiveType,
            sideBObjectiveType = mission.sideBObjectiveType,
            sideAObjectiveDesc = mission.sideAObjectiveDesc,
            sideBObjectiveDesc = mission.sideBObjectiveDesc,
            targetHex = mission.targetHex,
            surviveTurnsTarget = mission.surviveTurnsTarget,
            battleLog = listOf("Battle commenced: ${mission.title.en}"),
            tutorialStep = if (enableTutorial && mission.isTutorialFeatured) 1 else 0
        )
    }

    /**
     * Generates a procedural Skirmish / Pass-and-Play battle on a custom or random seed map.
     */
    fun createSkirmishBattle(
        sideAFaction: FactionId,
        sideBFaction: FactionId,
        weather: WeatherType,
        aiDifficulty: AiDifficulty,
        isPassAndPlay: Boolean,
        mapSeed: Long = System.currentTimeMillis()
    ): BattleState {
        val rng = Random(mapSeed)
        val width = 10
        val height = 7
        val tiles = mutableMapOf<HexCoord, HexTileState>()

        for (q in 0 until width) {
            for (r in 0 until height) {
                val c = HexCoord(q, r)
                val terrain = when {
                    q == 4 && r == 3 -> TerrainType.ROAD
                    q == 5 && r == 3 -> TerrainType.FORTRESS
                    q == 4 && (r == 1 || r == 2 || r == 4 || r == 5) && rng.nextFloat() < 0.65f -> TerrainType.RIVER
                    rng.nextFloat() < 0.16f -> TerrainType.HILLS
                    rng.nextFloat() < 0.14f -> TerrainType.FOREST
                    rng.nextFloat() < 0.18f -> TerrainType.STEPPE
                    else -> TerrainType.PLAINS
                }
                tiles[c] = HexTileState(c, terrain, if (q == 5 && r == 3) "Central Stronghold" else null)
            }
        }

        val heroA = UnitsAndHeroesCatalog.allHeroes.firstOrNull { it.factionId == sideAFaction }
            ?: UnitsAndHeroesCatalog.allHeroes.first()
        val heroB = UnitsAndHeroesCatalog.allHeroes.firstOrNull { it.factionId == sideBFaction && it.id != heroA.id }
            ?: UnitsAndHeroesCatalog.allHeroes[1]

        fun factionRoster(faction: FactionId): List<String> = when (faction) {
            FactionId.TIMURID -> listOf("timurid_heavy_cavalry", "steppe_horse_archer", "sogdian_infantry", "war_elephants", "central_asian_siege_crew")
            FactionId.MONGOL, FactionId.GOLDEN_HORDE, FactionId.CHAGATAI -> listOf("steppe_heavy_cavalry", "steppe_horse_archer", "steppe_horse_archer", "steppe_light_cavalry", "nomad_raiders")
            FactionId.KHWARAZMIAN -> listOf("khwarazmian_cavalry", "khwarazmian_cavalry", "sogdian_infantry", "steppe_horse_archer", "common_archers")
            FactionId.AYYUBID, FactionId.OTTOMAN -> listOf("mamluk_cavalry", "ghazi_archers", "camel_riders", "saracen_infantry", "steppe_horse_archer")
            FactionId.DELHI_SULTANATE -> listOf("war_elephants", "war_elephants", "khwarazmian_cavalry", "common_spearmen", "common_archers")
            FactionId.ENGLAND -> listOf("longbowmen", "longbowmen", "men_at_arms", "european_knights", "pikemen")
            else -> listOf("european_knights", "european_knights", "men_at_arms", "crossbowmen", "pikemen")
        }

        val rosterA = factionRoster(sideAFaction)
        val rosterB = factionRoster(sideBFaction)
        val rowPositions = listOf(3, 1, 5, 2, 4, 0)

        val units = mutableListOf<BattleUnitState>()
        units.add(
            BattleUnitState(
                instanceId = "SKIRM_A_HERO",
                archetypeId = heroA.baseUnitArchetypeId,
                customName = heroA.name.en,
                side = BattleSide.SIDE_A,
                factionId = sideAFaction,
                coord = HexCoord(1, 3),
                facing = 0,
                currentHp = 150,
                maxHp = 150,
                isHero = true,
                heroId = heroA.id
            )
        )
        rosterA.forEachIndexed { index, archId ->
            val arch = UnitsAndHeroesCatalog.unitById(archId)
            units.add(
                BattleUnitState(
                    instanceId = "SKIRM_A_$index",
                    archetypeId = archId,
                    side = BattleSide.SIDE_A,
                    factionId = sideAFaction,
                    coord = HexCoord(if (index % 2 == 0) 2 else 1, rowPositions[(index + 1) % rowPositions.size]),
                    facing = 0,
                    currentHp = arch.maxHp,
                    maxHp = arch.maxHp
                )
            )
        }

        units.add(
            BattleUnitState(
                instanceId = "SKIRM_B_HERO",
                archetypeId = heroB.baseUnitArchetypeId,
                customName = heroB.name.en,
                side = BattleSide.SIDE_B,
                factionId = sideBFaction,
                coord = HexCoord(8, 3),
                facing = 3,
                currentHp = 150,
                maxHp = 150,
                isHero = true,
                heroId = heroB.id
            )
        )
        rosterB.forEachIndexed { index, archId ->
            val arch = UnitsAndHeroesCatalog.unitById(archId)
            units.add(
                BattleUnitState(
                    instanceId = "SKIRM_B_$index",
                    archetypeId = archId,
                    side = BattleSide.SIDE_B,
                    factionId = sideBFaction,
                    coord = HexCoord(if (index % 2 == 0) 7 else 8, rowPositions[(index + 1) % rowPositions.size]),
                    facing = 3,
                    currentHp = arch.maxHp,
                    maxHp = arch.maxHp
                )
            )
        }

        return BattleState(
            missionId = "skirmish_$mapSeed",
            campaignId = "skirmish",
            gameMode = if (isPassAndPlay) GameMode.PASS_AND_PLAY else GameMode.SKIRMISH,
            isWhatIf = false,
            playerSide = BattleSide.SIDE_A,
            aiDifficulty = aiDifficulty,
            mapWidth = width,
            mapHeight = height,
            tiles = tiles,
            units = units,
            weather = weather,
            turnNumber = 1,
            maxTurns = 15,
            activeSide = BattleSide.SIDE_A,
            sideAFaction = sideAFaction,
            sideBFaction = sideBFaction,
            sideAName = sideAFaction.displayName,
            sideBName = sideBFaction.displayName,
            sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
            sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
            sideAObjectiveDesc = LocalizedText(
                "Rout the enemy army or hold the Central Stronghold (5,3).",
                "Dushman qo'shinini tor-mor eting yoki Markaziy qo'rg'onni (5,3) egallang.",
                "Разгромите войско противника или займите центральную цитадель (5,3)."
            ),
            sideBObjectiveDesc = LocalizedText(
                "Rout the enemy army or hold the Central Stronghold (5,3).",
                "Dushman qo'shinini tor-mor eting yoki Markaziy qo'rg'onni (5,3) egallang.",
                "Разгромите войско противника или займите центральную цитадель (5,3)."
            ),
            targetHex = HexCoord(5, 3)
        )
    }

    /**
     * Computes movement cost for `unit` entering `tile` under `weather`.
     */
    fun tileMoveCost(unit: BattleUnitState, tile: HexTileState, weather: WeatherType): Int {
        val cat = unit.archetype.category
        // Siege & Elephants cannot scale steep mountain peaks
        if (tile.terrain == TerrainType.MOUNTAIN && (cat == UnitCategory.SIEGE || cat == UnitCategory.ELEPHANT)) {
            return 99
        }
        var cost = tile.terrain.moveCost
        // Faction doctrines
        if (tile.terrain == TerrainType.DESERT && (unit.factionId == FactionId.AYYUBID || unit.archetypeId == "camel_riders")) {
            cost = 1
        }
        if (tile.terrain == TerrainType.STEPPE && unit.factionId == FactionId.GOLDEN_HORDE) {
            cost = 1
        }
        // Weather impact on heavy cavalry/siege
        if (weather == WeatherType.MUD && tile.terrain != TerrainType.ROAD &&
            (cat == UnitCategory.HEAVY_CAVALRY || cat == UnitCategory.SIEGE) &&
            unit.factionId != FactionId.CHAGATAI
        ) {
            cost += weather.cavalryExtraMoveCost
        }
        return cost.coerceAtLeast(1)
    }

    /**
     * Dijkstra search returning all reachable hexes for `unit` mapped to the movement cost spent.
     */
    fun computeReachableHexes(state: BattleState, unit: BattleUnitState): Map<HexCoord, Int> {
        if (unit.hasMovedThisTurn || unit.isRouted || unit.currentHp <= 0) return emptyMap()
        val maxMove = unit.archetype.movement
        val costs = mutableMapOf<HexCoord, Int>()
        val pq = PriorityQueue<Pair<HexCoord, Int>>(compareBy { it.second })

        costs[unit.coord] = 0
        pq.add(unit.coord to 0)

        while (pq.isNotEmpty()) {
            val (current, spent) = pq.poll() ?: break
            if (spent > (costs[current] ?: Int.MAX_VALUE)) continue

            for (next in offsetNeighbors(current, state.mapWidth, state.mapHeight)) {
                val tile = state.tileAt(next) ?: continue
                val occupant = state.unitAt(next)
                // Cannot move onto an enemy unit
                if (occupant != null && occupant.side != unit.side) continue

                val stepCost = tileMoveCost(unit, tile, state.weather)
                if (stepCost >= 99) continue

                // Zone of Control (ZOC): entering a hex adjacent to an active enemy uses remaining movement
                val adjacentToEnemy = offsetNeighbors(next, state.mapWidth, state.mapHeight).any { n ->
                    val u = state.unitAt(n)
                    u != null && u.side != unit.side && !u.isRouted
                }
                val effectiveStepCost = if (adjacentToEnemy && spent > 0) max(stepCost, maxMove - spent) else stepCost
                val newCost = spent + effectiveStepCost

                if (newCost <= maxMove && newCost < (costs[next] ?: Int.MAX_VALUE)) {
                    costs[next] = newCost
                    pq.add(next to newCost)
                }
            }
        }

        // Remove hexes currently occupied by friendly units (can pass through allies, cannot stop on them)
        return costs.filterKeys { coord ->
            coord != unit.coord && state.unitAt(coord) == null
        }
    }

    /**
     * Returns IDs of enemy units that `unit` can attack from its current coordinate.
     */
    fun computeAttackableUnitIds(state: BattleState, unit: BattleUnitState): Set<String> {
        if (unit.hasAttackedThisTurn || unit.isRouted || unit.currentHp <= 0) return emptySet()
        val effectiveRange = max(1, unit.archetype.range + if (unit.archetype.range > 1) state.weather.rangedRangeDelta else 0)
        return state.units.asSequence()
            .filter { it.currentHp > 0 && it.side != unit.side }
            .filter { hexDistance(unit.coord, it.coord) <= effectiveRange }
            .map { it.instanceId }
            .toSet()
    }

    /**
     * Selects a unit or handles tile click.
     */
    fun selectUnit(state: BattleState, unitId: String?): BattleState {
        val unit = state.unitById(unitId)
        if (unit == null) {
            return state.copy(
                selectedUnitId = null,
                reachableHexes = emptyMap(),
                attackableUnitIds = emptySet(),
                pendingCombatPreview = null,
                useSpecialAbilityOnConfirm = false
            )
        }
        val isControllable = unit.side == state.activeSide && state.isPlayerTurn()
        val reachable = if (isControllable) computeReachableHexes(state, unit) else emptyMap()
        val attackable = if (isControllable) computeAttackableUnitIds(state, unit) else emptySet()
        return state.copy(
            selectedUnitId = unit.instanceId,
            reachableHexes = reachable,
            attackableUnitIds = attackable,
            pendingCombatPreview = null,
            useSpecialAbilityOnConfirm = false
        )
    }

    /**
     * Moves the selected unit to `destination` (preserving `previousCoordBeforeMove` so the player can Undo before attacking!).
     */
    fun moveUnit(state: BattleState, unitId: String, destination: HexCoord): BattleState {
        val unit = state.unitById(unitId) ?: return state
        if (unit.side != state.activeSide || unit.hasMovedThisTurn) return state
        if (state.unitAt(destination) != null) return state

        val dist = hexDistance(unit.coord, destination)
        val newFacing = computeFacing(unit.coord, destination)
        val updatedUnit = unit.copy(
            previousCoordBeforeMove = unit.coord,
            coord = destination,
            facing = newFacing,
            hasMovedThisTurn = true,
            distanceMovedThisTurn = dist
        )
        val newUnits = state.units.map { if (it.instanceId == unitId) updatedUnit else it }
        val nextTutorial = if (state.tutorialStep == 1) 2 else state.tutorialStep
        val interim = state.copy(
            units = newUnits,
            pendingCombatPreview = null,
            tutorialStep = nextTutorial
        )
        val evaluated = evaluateVictoryConditions(interim)
        return selectUnit(evaluated, unitId)
    }

    /**
     * Undoes the last move of `unitId` if the unit has not attacked yet this turn.
     */
    fun undoLastMove(state: BattleState, unitId: String): BattleState {
        val unit = state.unitById(unitId) ?: return state
        val prev = unit.previousCoordBeforeMove ?: return state
        if (!unit.canUndoMove || state.unitAt(prev) != null) return state

        val restored = unit.copy(
            coord = prev,
            previousCoordBeforeMove = null,
            hasMovedThisTurn = false,
            distanceMovedThisTurn = 0
        )
        val newUnits = state.units.map { if (it.instanceId == unitId) restored else it }
        return selectUnit(state.copy(units = newUnits, pendingCombatPreview = null), unitId)
    }

    /**
     * Calculates deterministic combat preview between `attacker` and `defender`.
     */
    fun previewCombat(
        state: BattleState,
        attacker: BattleUnitState,
        defender: BattleUnitState,
        useSpecialAbility: Boolean = false
    ): CombatPreview {
        val attackerTile = state.tileAt(attacker.coord) ?: HexTileState(attacker.coord, TerrainType.PLAINS)
        val defenderTile = state.tileAt(defender.coord) ?: HexTileState(defender.coord, TerrainType.PLAINS)
        val dist = hexDistance(attacker.coord, defender.coord)
        val tags = mutableListOf<String>()

        var attackMultiplier = 1.0f
        var defenseMultiplier = 1.0f

        // 1. Terrain modifiers
        if (attackerTile.terrain.attackBonus != 0) {
            attackMultiplier += attackerTile.terrain.attackBonus / 100f
            if (attackerTile.terrain.attackBonus > 0) tags.add("${attackerTile.terrain.label.en} +${attackerTile.terrain.attackBonus}% ATK")
        }
        val defBonus = defenderTile.effectiveDefenseBonus
        if (defBonus != 0) {
            defenseMultiplier += defBonus / 100f
            tags.add("${defenderTile.terrain.label.en} ${if (defBonus > 0) "+$defBonus%" else "$defBonus%"} DEF")
        }

        // 2. Elevation advantage
        val hasElevation = attackerTile.terrain.elevation > defenderTile.terrain.elevation
        if (hasElevation) {
            attackMultiplier += 0.18f
            tags.add("High Ground +18% ATK")
        }

        // 3. Flanking & Rear Attack
        val friendlyAdjacentToDefender = offsetNeighbors(defender.coord, state.mapWidth, state.mapHeight)
            .count { n ->
                val u = state.unitAt(n)
                u != null && u.side == attacker.side && u.instanceId != attacker.instanceId && !u.isRouted
            }
        val isFlanking = friendlyAdjacentToDefender > 0 && dist == 1
        if (isFlanking) {
            val flankBonus = 0.15f * friendlyAdjacentToDefender +
                if (attacker.factionId == FactionId.TIMURID) 0.15f else 0f
            attackMultiplier += flankBonus
            tags.add("Flanking +${(flankBonus * 100).roundToInt()}%")
        }

        val attackDir = computeFacing(attacker.coord, defender.coord)
        val isRearAttack = dist == 1 && attackDir == defender.facing
        if (isRearAttack) {
            attackMultiplier += 0.25f
            tags.add("Rear Strike +25%")
        }

        // 4. Unit category vs category counters
        val attCat = attacker.archetype.category
        val defCat = defender.archetype.category
        if (attCat == UnitCategory.SPEARMEN && (defCat == UnitCategory.HEAVY_CAVALRY || defCat == UnitCategory.LIGHT_CAVALRY || defCat == UnitCategory.HORSE_ARCHER || defCat == UnitCategory.ELEPHANT)) {
            attackMultiplier += 0.45f
            tags.add("Anti-Cavalry Spears +45%")
        }
        if (attCat == UnitCategory.HEAVY_CAVALRY && dist == 1 && !defenderTile.terrain.blocksCavalryCharge && attacker.distanceMovedThisTurn >= 1) {
            attackMultiplier += 0.20f
            tags.add("Momentum Charge +20%")
        }
        if (attCat == UnitCategory.SIEGE && defenderTile.terrain.isFortification) {
            attackMultiplier += 0.50f
            tags.add("Siege vs Ramparts +50%")
        }

        // 5. Weather modifiers
        if (attacker.archetype.range > 1 && state.weather.rangedAttackModPercent != 0) {
            attackMultiplier += state.weather.rangedAttackModPercent / 100f
            tags.add("${state.weather.label.en} ${state.weather.rangedAttackModPercent}% Ranged")
        }

        // 6. Hero passive aura within 2 hexes
        val friendlyHeroNearby = state.units.firstOrNull {
            it.currentHp > 0 && it.side == attacker.side && it.isHero && hexDistance(it.coord, attacker.coord) <= 2
        }
        val hasHeroAura = friendlyHeroNearby != null
        if (friendlyHeroNearby?.heroDefinition != null) {
            val bonus = friendlyHeroNearby.heroDefinition!!.passiveAttackBonus
            attackMultiplier += bonus / 100f
            tags.add("${friendlyHeroNearby.heroDefinition!!.passiveAuraName.en} +$bonus%")
        }

        val enemyHeroNearby = state.units.firstOrNull {
            it.currentHp > 0 && it.side == defender.side && it.isHero && hexDistance(it.coord, defender.coord) <= 2
        }
        if (enemyHeroNearby?.heroDefinition != null) {
            defenseMultiplier += enemyHeroNearby.heroDefinition!!.passiveDefenseBonus / 100f
        }

        // 7. Morale & Temporary Buffs
        if (attacker.currentMorale >= 85) attackMultiplier += 0.10f
        if (attacker.currentMorale <= 35) attackMultiplier -= 0.15f
        attackMultiplier += attacker.temporaryAttackBuffPercent / 100f
        defenseMultiplier += defender.temporaryDefenseBuffPercent / 100f
        if (defender.isRouted) {
            defenseMultiplier -= 0.30f
            tags.add("Target Routed (+30% DMG)")
        }

        // 8. Special Ability active modifier
        var wallDamage = if (attCat == UnitCategory.SIEGE && defenderTile.terrain.isFortification) 35 else 0
        if (useSpecialAbility && attacker.abilityCooldown == 0) {
            when (attacker.archetype.specialAbility) {
                SpecialAbilityType.LANCE_CHARGE -> {
                    attackMultiplier += 0.30f
                    tags.add("Shock Lance Charge +30%")
                }
                SpecialAbilityType.PIKE_PHALANX -> {
                    if (defCat == UnitCategory.HEAVY_CAVALRY || defCat == UnitCategory.LIGHT_CAVALRY || defCat == UnitCategory.ELEPHANT) {
                        attackMultiplier += 0.60f
                    } else {
                        attackMultiplier += 0.20f
                    }
                    tags.add("Braced Spear Wall")
                }
                SpecialAbilityType.VOLLEY_FIRE -> {
                    attackMultiplier += 0.25f
                    tags.add("Massed Arrow Volley +25%")
                }
                SpecialAbilityType.ELEPHANT_TRAMPLE -> {
                    attackMultiplier += 0.30f
                    tags.add("War Elephant Trample")
                }
                SpecialAbilityType.SAPPING -> {
                    attackMultiplier += 0.40f
                    if (defenderTile.terrain.isFortification) wallDamage += 60
                    tags.add("Breach & Sap Ramparts")
                }
                SpecialAbilityType.PARTHIAN_SHOT -> {
                    attackMultiplier += 0.15f
                    tags.add("Parthian Shot & Retreat")
                }
                SpecialAbilityType.FEIGNED_RETREAT -> {
                    attackMultiplier += 0.15f
                    tags.add("Feigned Retreat (-20% Enemy DEF)")
                }
                else -> {}
            }
        }

        // AI Difficulty scaling
        if (attacker.side != state.playerSide && state.gameMode != GameMode.PASS_AND_PLAY) {
            attackMultiplier *= state.aiDifficulty.aiDamageMultiplier
        }

        val rawAttack = attacker.archetype.attack * attackMultiplier.coerceAtLeast(0.4f)
        val rawDefense = defender.archetype.defense * defenseMultiplier.coerceAtLeast(0.35f)
        val expectedDamage = max(6, (rawAttack - rawDefense * 0.48f).roundToInt())

        val defenderRemainingHp = max(0, defender.currentHp - expectedDamage)
        val canCounter = defenderRemainingHp > 0 &&
            !defender.isRouted &&
            dist == 1 &&
            !(useSpecialAbility && attacker.archetype.specialAbility == SpecialAbilityType.VOLLEY_FIRE)

        val expectedCounter = if (canCounter) {
            val counterRaw = defender.archetype.attack * 0.58f - attacker.archetype.defense * 0.38f
            max(3, counterRaw.roundToInt())
        } else 0

        var moraleDrop = 12 + (expectedDamage * 100 / defender.maxHp.coerceAtLeast(1)) / 3
        if (isFlanking) moraleDrop += 10
        if (isRearAttack) moraleDrop += 14
        if (attacker.factionId == FactionId.MONGOL && attacker.archetype.range > 1) moraleDrop += 5
        if (useSpecialAbility && attacker.archetype.specialAbility == SpecialAbilityType.ELEPHANT_TRAMPLE) moraleDrop += 18
        if (defender.factionId == FactionId.OTTOMAN) moraleDrop = (moraleDrop * 0.75f).roundToInt()

        val newDefenderMorale = max(0, defender.currentMorale - moraleDrop)
        val willRout = defenderRemainingHp > 0 && newDefenderMorale == 0 &&
            !(enemyHeroNearby?.heroId == "jalal_ad_din")

        return CombatPreview(
            attackerId = attacker.instanceId,
            defenderId = defender.instanceId,
            expectedDamageToDefender = expectedDamage,
            expectedCounterDamageToAttacker = expectedCounter,
            expectedMoraleDropDefender = moraleDrop,
            expectedWallDamage = wallDamage,
            isFlanking = isFlanking,
            isRearAttack = isRearAttack,
            hasElevationAdvantage = hasElevation,
            hasHeroAura = hasHeroAura,
            defenderWillRout = willRout,
            defenderWillBeDefeated = defenderRemainingHp <= 0,
            modifierTags = tags
        )
    }

    /**
     * Prepares or executes an attack when the user taps an enemy unit.
     * First tap shows the `CombatPreview`; confirming executes the strike!
     */
    fun requestOrConfirmAttack(
        state: BattleState,
        attackerId: String,
        defenderId: String,
        useSpecialAbility: Boolean = state.useSpecialAbilityOnConfirm
    ): BattleState {
        val attacker = state.unitById(attackerId) ?: return state
        val defender = state.unitById(defenderId) ?: return state
        if (attacker.instanceId !in state.units.map { it.instanceId } || defenderId !in state.attackableUnitIds) {
            return state
        }

        val currentPreview = state.pendingCombatPreview
        if (currentPreview == null || currentPreview.defenderId != defenderId || state.useSpecialAbilityOnConfirm != useSpecialAbility) {
            val preview = previewCombat(state, attacker, defender, useSpecialAbility)
            val nextTutorial = if (state.tutorialStep == 2) 3 else state.tutorialStep
            return state.copy(
                pendingCombatPreview = preview,
                useSpecialAbilityOnConfirm = useSpecialAbility,
                tutorialStep = nextTutorial
            )
        }
        return executeConfirmedCombat(state, currentPreview, useSpecialAbility)
    }

    /**
     * Executes a confirmed `CombatPreview` and updates HP, morale, wall integrity, special movement (Parthian shot / Feigned retreat), and victory state.
     */
    fun executeConfirmedCombat(
        state: BattleState,
        preview: CombatPreview,
        useSpecialAbility: Boolean
    ): BattleState {
        val attacker = state.unitById(preview.attackerId) ?: return state
        val defender = state.unitById(preview.defenderId) ?: return state

        val callouts = mutableListOf<FloatingBattleCallout>()
        val logs = state.battleLog.toMutableList()

        if (attacker.archetype.range > 1) {
            callouts.add(
                FloatingBattleCallout(
                    coord = defender.coord,
                    text = "-${preview.expectedDamageToDefender} HP",
                    isPositive = false,
                    isProjectile = true,
                    fromCoord = attacker.coord
                )
            )
        } else {
            callouts.add(
                FloatingBattleCallout(
                    coord = defender.coord,
                    text = "-${preview.expectedDamageToDefender} HP",
                    isPositive = false
                )
            )
        }

        val newDefenderHp = max(0, defender.currentHp - preview.expectedDamageToDefender)
        val newDefenderMorale = max(0, defender.currentMorale - preview.expectedMoraleDropDefender)
        val newAttackerHp = max(1, attacker.currentHp - preview.expectedCounterDamageToAttacker)

        // Check Parthian Shot / Feigned Retreat step-back hex
        var finalAttackerCoord = attacker.coord
        if (useSpecialAbility && (attacker.archetype.specialAbility == SpecialAbilityType.PARTHIAN_SHOT ||
                attacker.archetype.specialAbility == SpecialAbilityType.FEIGNED_RETREAT)
        ) {
            val retreatCandidate = offsetNeighbors(attacker.coord, state.mapWidth, state.mapHeight)
                .filter { state.unitAt(it) == null && (state.tileAt(it)?.terrain?.moveCost ?: 99) <= 2 }
                .maxByOrNull { hexDistance(it, defender.coord) }
            if (retreatCandidate != null && hexDistance(retreatCandidate, defender.coord) > hexDistance(attacker.coord, defender.coord)) {
                finalAttackerCoord = retreatCandidate
                callouts.add(FloatingBattleCallout(coord = retreatCandidate, text = "Step Back!", isPositive = true))
            }
        }

        val newFacing = computeFacing(attacker.coord, defender.coord)
        val abilityCd = if (useSpecialAbility) attacker.archetype.specialAbility.cooldownTurns else attacker.abilityCooldown

        // Update tiles if siege wall damage occurred
        val updatedTiles = state.tiles.toMutableMap()
        if (preview.expectedWallDamage > 0) {
            val defTile = updatedTiles[defender.coord]
            if (defTile != null && defTile.terrain.isFortification) {
                val newIntegrity = max(0, defTile.wallIntegrity - preview.expectedWallDamage)
                updatedTiles[defender.coord] = defTile.copy(wallIntegrity = newIntegrity)
                if (newIntegrity == 0 && defTile.wallIntegrity > 0) {
                    callouts.add(FloatingBattleCallout(coord = defender.coord, text = "WALL BREACHED!", isPositive = true))
                    logs.add("Fortification breached at (${defender.coord.q}, ${defender.coord.r})!")
                }
            }
        }

        val heroFallen = defender.isHero && newDefenderHp <= 0
        if (heroFallen) {
            logs.add("Commander ${defender.customName ?: defender.archetype.name.en} has fallen in battle!")
            callouts.add(FloatingBattleCallout(coord = defender.coord, text = "COMMANDER FALLEN!", isPositive = false))
        } else if (preview.defenderWillRout) {
            logs.add("${defender.customName ?: defender.archetype.name.en} lost morale and routed!")
            callouts.add(FloatingBattleCallout(coord = defender.coord, text = "ROUTED!", isPositive = false))
        }

        val updatedUnits = state.units.mapNotNull { u ->
            when {
                u.instanceId == attacker.instanceId -> {
                    u.copy(
                        coord = finalAttackerCoord,
                        facing = newFacing,
                        currentHp = newAttackerHp,
                        hasAttackedThisTurn = true,
                        hasMovedThisTurn = true,
                        previousCoordBeforeMove = null,
                        abilityCooldown = abilityCd
                    )
                }
                u.instanceId == defender.instanceId -> {
                    if (newDefenderHp <= 0) {
                        null // Unit destroyed
                    } else {
                        val defDebuff = if (useSpecialAbility && attacker.archetype.specialAbility == SpecialAbilityType.FEIGNED_RETREAT) {
                            u.temporaryDefenseBuffPercent - 20
                        } else u.temporaryDefenseBuffPercent
                        u.copy(
                            currentHp = newDefenderHp,
                            currentMorale = newDefenderMorale,
                            isRouted = u.isRouted || preview.defenderWillRout,
                            temporaryDefenseBuffPercent = defDebuff
                        )
                    }
                }
                // Elephant trample splash morale shock on enemies adjacent to defender
                useSpecialAbility && attacker.archetype.specialAbility == SpecialAbilityType.ELEPHANT_TRAMPLE &&
                    u.side == defender.side && hexDistance(u.coord, defender.coord) == 1 -> {
                    val splashMorale = max(0, u.currentMorale - 20)
                    val splashHp = max(1, u.currentHp - 10)
                    u.copy(
                        currentHp = splashHp,
                        currentMorale = splashMorale,
                        isRouted = u.isRouted || splashMorale == 0
                    )
                }
                // Army-wide morale shock if their Hero Commander fell
                heroFallen && u.side == defender.side -> {
                    val droppedMorale = max(0, u.currentMorale - 35)
                    u.copy(
                        currentMorale = droppedMorale,
                        isRouted = u.isRouted || droppedMorale == 0
                    )
                }
                else -> u
            }
        }

        val nextTutorial = if (state.tutorialStep == 3) 4 else state.tutorialStep
        val postCombatState = state.copy(
            tiles = updatedTiles,
            units = updatedUnits,
            pendingCombatPreview = null,
            useSpecialAbilityOnConfirm = false,
            reachableHexes = emptyMap(),
            attackableUnitIds = emptySet(),
            battleLog = logs.takeLast(12),
            recentCallouts = callouts,
            tutorialStep = nextTutorial
        )

        return evaluateVictoryConditions(postCombatState)
    }

    /**
     * Activates a defensive/support unit special ability (`SHIELD_WALL` or `RALLY_TROOPS`) directly on self/allies.
     */
    fun activateSelfOrSupportAbility(state: BattleState, unitId: String): BattleState {
        val unit = state.unitById(unitId) ?: return state
        if (unit.abilityCooldown > 0 || unit.hasAttackedThisTurn) return state

        val callouts = mutableListOf<FloatingBattleCallout>()
        val updatedUnits = when (unit.archetype.specialAbility) {
            SpecialAbilityType.SHIELD_WALL -> {
                callouts.add(FloatingBattleCallout(coord = unit.coord, text = "+35% DEF Shield Wall", isPositive = true))
                state.units.map { u ->
                    if (u.instanceId == unit.instanceId) {
                        u.copy(
                            temporaryDefenseBuffPercent = u.temporaryDefenseBuffPercent + 35,
                            currentMorale = min(u.maxMorale, u.currentMorale + 15),
                            abilityCooldown = unit.archetype.specialAbility.cooldownTurns,
                            hasAttackedThisTurn = true,
                            previousCoordBeforeMove = null
                        )
                    } else u
                }
            }
            SpecialAbilityType.RALLY_TROOPS -> {
                callouts.add(FloatingBattleCallout(coord = unit.coord, text = "+25 Morale Rally!", isPositive = true))
                state.units.map { u ->
                    if (u.side == unit.side && hexDistance(u.coord, unit.coord) <= 1) {
                        u.copy(
                            currentMorale = min(u.maxMorale, u.currentMorale + 25),
                            isRouted = false,
                            abilityCooldown = if (u.instanceId == unit.instanceId) unit.archetype.specialAbility.cooldownTurns else u.abilityCooldown,
                            hasAttackedThisTurn = if (u.instanceId == unit.instanceId) true else u.hasAttackedThisTurn,
                            previousCoordBeforeMove = if (u.instanceId == unit.instanceId) null else u.previousCoordBeforeMove
                        )
                    } else u
                }
            }
            else -> return state.copy(useSpecialAbilityOnConfirm = !state.useSpecialAbilityOnConfirm)
        }

        return selectUnit(state.copy(units = updatedUnits, recentCallouts = callouts), unitId)
    }

    /**
     * Activates the Hero Commander's active ability (`heroCooldown == 0`).
     */
    fun activateHeroCommandAbility(state: BattleState, heroUnitId: String): BattleState {
        val heroUnit = state.unitById(heroUnitId) ?: return state
        val heroDef = heroUnit.heroDefinition ?: return state
        if (!heroUnit.isHero || heroUnit.heroCooldown > 0) return state

        val callouts = mutableListOf<FloatingBattleCallout>()
        callouts.add(
            FloatingBattleCallout(
                coord = heroUnit.coord,
                text = heroDef.activeAbilityName.en,
                isPositive = true
            )
        )

        val updatedUnits = state.units.map { u ->
            val dist = hexDistance(u.coord, heroUnit.coord)
            when {
                u.instanceId == heroUnit.instanceId -> {
                    val selfHeal = if (heroDef.activeEffectType == HeroActiveEffectType.INDUS_DEFIANCE ||
                        heroDef.activeEffectType == HeroActiveEffectType.ROYAL_GUARD
                    ) 20 else 10
                    u.copy(
                        currentHp = min(u.maxHp, u.currentHp + selfHeal),
                        currentMorale = min(u.maxMorale, u.currentMorale + 25),
                        isRouted = false,
                        temporaryAttackBuffPercent = u.temporaryAttackBuffPercent + 20,
                        temporaryDefenseBuffPercent = u.temporaryDefenseBuffPercent + 20,
                        heroCooldown = heroDef.activeCooldownTurns
                    )
                }
                u.side == heroUnit.side && dist <= 2 -> {
                    val heal = if (heroDef.activeEffectType == HeroActiveEffectType.ROYAL_GUARD) 15 else 0
                    u.copy(
                        currentHp = min(u.maxHp, u.currentHp + heal),
                        currentMorale = min(u.maxMorale, u.currentMorale + 25),
                        isRouted = false,
                        temporaryAttackBuffPercent = u.temporaryAttackBuffPercent + 20,
                        temporaryDefenseBuffPercent = u.temporaryDefenseBuffPercent + 15
                    )
                }
                u.side != heroUnit.side && dist <= 2 && (
                    heroDef.activeEffectType == HeroActiveEffectType.ARROW_STORM ||
                        heroDef.activeEffectType == HeroActiveEffectType.HORNS_OF_HATTIN
                    ) -> {
                    val volleyDmg = if (heroDef.activeEffectType == HeroActiveEffectType.ARROW_STORM) 15 else 8
                    val newMorale = max(0, u.currentMorale - 22)
                    u.copy(
                        currentHp = max(1, u.currentHp - volleyDmg),
                        currentMorale = newMorale,
                        isRouted = u.isRouted || newMorale == 0
                    )
                }
                else -> u
            }
        }

        val nextTutorial = if (state.tutorialStep == 4) 0 else state.tutorialStep
        return selectUnit(
            state.copy(
                units = updatedUnits,
                recentCallouts = callouts,
                battleLog = (state.battleLog + "${heroDef.name.en} invoked ${heroDef.activeAbilityName.en}!").takeLast(12),
                tutorialStep = nextTutorial
            ),
            heroUnitId
        )
    }

    /**
     * Advances to the next side's turn, refreshing action flags, ticking cooldowns, applying hero passive morale auras,
     * fortress garrison recovery, and weather/elephant morale effects.
     */
    fun endTurn(state: BattleState): BattleState {
        if (state.outcome != null) return state

        val nextSide = state.activeSide.opposite()
        val nextTurnNumber = if (nextSide == BattleSide.SIDE_A) state.turnNumber + 1 else state.turnNumber

        val refreshedUnits = state.units.map { u ->
            if (u.side == nextSide) {
                val tile = state.tileAt(u.coord)
                val friendlyHeroNearby = state.units.firstOrNull {
                    it.currentHp > 0 && it.side == u.side && it.isHero && hexDistance(it.coord, u.coord) <= 2
                }
                val adjacentEnemyElephant = state.units.any {
                    it.currentHp > 0 && it.side != u.side && it.archetype.category == UnitCategory.ELEPHANT &&
                        hexDistance(it.coord, u.coord) == 1
                }

                var moraleDelta = 0
                if (friendlyHeroNearby?.heroDefinition != null) {
                    moraleDelta += friendlyHeroNearby.heroDefinition!!.passiveMoraleAura
                }
                if (tile?.terrain == TerrainType.FORTRESS && !tile.isBreached) {
                    moraleDelta += 6
                }
                if (adjacentEnemyElephant) {
                    moraleDelta -= 8
                }
                if (state.weather.moraleDrainPerTurn > 0 && u.factionId.era != com.example.model.Era.STEPPE) {
                    moraleDelta -= state.weather.moraleDrainPerTurn
                }

                val newMorale = (u.currentMorale + moraleDelta).coerceIn(0, u.maxMorale)
                val recoveredFromRout = u.isRouted && newMorale >= 35

                u.copy(
                    hasMovedThisTurn = false,
                    hasAttackedThisTurn = false,
                    distanceMovedThisTurn = 0,
                    previousCoordBeforeMove = null,
                    currentMorale = newMorale,
                    isRouted = if (recoveredFromRout) false else (u.isRouted || newMorale == 0),
                    abilityCooldown = max(0, u.abilityCooldown - 1),
                    heroCooldown = max(0, u.heroCooldown - 1),
                    temporaryAttackBuffPercent = 0,
                    temporaryDefenseBuffPercent = 0
                )
            } else {
                u.copy(previousCoordBeforeMove = null)
            }
        }

        val advancedState = state.copy(
            activeSide = nextSide,
            turnNumber = nextTurnNumber,
            units = refreshedUnits,
            selectedUnitId = null,
            reachableHexes = emptyMap(),
            attackableUnitIds = emptySet(),
            pendingCombatPreview = null,
            useSpecialAbilityOnConfirm = false
        )

        return evaluateVictoryConditions(advancedState)
    }

    /**
     * Evaluates whether either side has completed its objective or lost its army/commander.
     */
    fun evaluateVictoryConditions(state: BattleState): BattleState {
        if (state.outcome != null) return state

        val sideAUnits = state.units.filter { it.side == BattleSide.SIDE_A && it.currentHp > 0 }
        val sideBUnits = state.units.filter { it.side == BattleSide.SIDE_B && it.currentHp > 0 }
        val sideAActive = sideAUnits.filter { !it.isRouted }
        val sideBActive = sideBUnits.filter { !it.isRouted }

        // 1. Check total army rout/destruction
        if (sideAActive.isEmpty()) {
            return finishBattle(
                state = state,
                winner = BattleSide.SIDE_B,
                reason = LocalizedText(
                    "${state.sideBName.en} shattered the opposing army!",
                    "${state.sideBName.uz} raqib qo'shinini tor-mor etdi!",
                    "${state.sideBName.ru} разгромили армию противника!"
                )
            )
        }
        if (sideBActive.isEmpty()) {
            return finishBattle(
                state = state,
                winner = BattleSide.SIDE_A,
                reason = LocalizedText(
                    "${state.sideAName.en} shattered the opposing army!",
                    "${state.sideAName.uz} raqib qo'shinini tor-mor etdi!",
                    "${state.sideAName.ru} разгромили армию противника!"
                )
            )
        }

        // 2. Check PROTECT_HERO failure
        if (state.sideAObjectiveType == ObjectiveType.PROTECT_HERO && sideAUnits.none { it.isHero }) {
            return finishBattle(
                state = state,
                winner = BattleSide.SIDE_B,
                reason = LocalizedText(
                    "Side A's Commander fell in battle!",
                    "Sarkarda jangda halok bo'ldi!",
                    "Полководец пал в бою!"
                )
            )
        }
        if (state.sideBObjectiveType == ObjectiveType.PROTECT_HERO && sideBUnits.none { it.isHero }) {
            return finishBattle(
                state = state,
                winner = BattleSide.SIDE_A,
                reason = LocalizedText(
                    "Side B's Commander fell in battle!",
                    "Raqib sarkardasi safdan chiqdi!",
                    "Вражеский полководец пал в бою!"
                )
            )
        }

        // 3. Check CAPTURE_HEX / BREAK_SIEGE / CROSS_RIVER on targetHex
        val target = state.targetHex
        if (target != null) {
            val occupant = state.unitAt(target)
            if (occupant != null && !occupant.isRouted) {
                if (occupant.side == BattleSide.SIDE_A &&
                    (state.sideAObjectiveType == ObjectiveType.CAPTURE_HEX ||
                        state.sideAObjectiveType == ObjectiveType.BREAK_SIEGE ||
                        state.sideAObjectiveType == ObjectiveType.CROSS_RIVER)
                ) {
                    return finishBattle(
                        state = state,
                        winner = BattleSide.SIDE_A,
                        reason = LocalizedText(
                            "${state.sideAName.en} captured the strategic objective (${target.q}, ${target.r})!",
                            "${state.sideAName.uz} strategik nuqtani (${target.q}, ${target.r}) egalladi!",
                            "${state.sideAName.ru} захватили ключевой рубеж (${target.q}, ${target.r})!"
                        )
                    )
                }
                if (occupant.side == BattleSide.SIDE_B &&
                    (state.sideBObjectiveType == ObjectiveType.CAPTURE_HEX ||
                        state.sideBObjectiveType == ObjectiveType.BREAK_SIEGE ||
                        state.sideBObjectiveType == ObjectiveType.CROSS_RIVER)
                ) {
                    // Only trigger immediate capture win if Side A didn't start on that hex as defender
                    val isAssaultCapture = state.sideAObjectiveType == ObjectiveType.SURVIVE_TURNS ||
                        state.sideAObjectiveType == ObjectiveType.PROTECT_HERO ||
                        state.turnNumber > 1
                    if (isAssaultCapture) {
                        return finishBattle(
                            state = state,
                            winner = BattleSide.SIDE_B,
                            reason = LocalizedText(
                                "${state.sideBName.en} seized the stronghold (${target.q}, ${target.r})!",
                                "${state.sideBName.uz} muhim qal'ani (${target.q}, ${target.r}) egalladi!",
                                "${state.sideBName.ru} взяли ключевую твердыню (${target.q}, ${target.r})!"
                            )
                        )
                    }
                }
            }
        }

        // 4. Check SURVIVE_TURNS / maxTurns completion
        if (state.turnNumber > state.surviveTurnsTarget) {
            if (state.sideAObjectiveType == ObjectiveType.SURVIVE_TURNS) {
                return finishBattle(
                    state = state,
                    winner = BattleSide.SIDE_A,
                    reason = LocalizedText(
                        "${state.sideAName.en} successfully held the stronghold for ${state.surviveTurnsTarget} turns!",
                        "${state.sideAName.uz} ${state.surviveTurnsTarget} yurish davomida mudofaani sharaf bilan ushlab qoldi!",
                        "${state.sideAName.ru} успешно удерживали рубеж ${state.surviveTurnsTarget} ходов!"
                    )
                )
            }
            if (state.sideBObjectiveType == ObjectiveType.SURVIVE_TURNS) {
                return finishBattle(
                    state = state,
                    winner = BattleSide.SIDE_B,
                    reason = LocalizedText(
                        "${state.sideBName.en} successfully held the stronghold for ${state.surviveTurnsTarget} turns!",
                        "${state.sideBName.uz} ${state.surviveTurnsTarget} yurish davomida mudofaani ushlab qoldi!",
                        "${state.sideBName.ru} успешно удерживали оборону ${state.surviveTurnsTarget} ходов!"
                    )
                )
            }
        }

        if (state.turnNumber > state.maxTurns) {
            val hpA = sideAUnits.sumOf { it.currentHp }
            val hpB = sideBUnits.sumOf { it.currentHp }
            val winner = if (hpA >= hpB) BattleSide.SIDE_A else BattleSide.SIDE_B
            return finishBattle(
                state = state,
                winner = winner,
                reason = LocalizedText(
                    "Turn limit reached — tactical advantage decided by surviving forces.",
                    "Yurishlar yakunlandi — ustunlik saqlab qolingan kuchlar bo'yicha aniqlandi.",
                    "Лимит ходов исчерпан — исход решен по перевесу сохраненных сил."
                )
            )
        }

        return state
    }

    private fun finishBattle(
        state: BattleState,
        winner: BattleSide,
        reason: LocalizedText
    ): BattleState {
        val playerWon = if (state.gameMode == GameMode.PASS_AND_PLAY) true else (winner == state.playerSide)
        val winnerUnits = state.units.filter { it.side == winner && it.currentHp > 0 }
        val heroSurvived = winnerUnits.any { it.isHero }
        val fastVictory = state.turnNumber <= (state.maxTurns * 2) / 3

        val stars = if (!playerWon) {
            0
        } else {
            var s = 1
            if (heroSurvived && winnerUnits.size >= 3) s++
            if (fastVictory) s++
            s.coerceIn(1, 3)
        }

        return state.copy(
            outcome = BattleOutcome(
                winnerSide = winner,
                playerWon = playerWon,
                starsEarned = stars,
                turnsTaken = state.turnNumber,
                reason = reason
            ),
            pendingCombatPreview = null
        )
    }
}
