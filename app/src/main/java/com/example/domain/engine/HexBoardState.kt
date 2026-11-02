package com.example.domain.engine

import com.example.data.pack.UnitsAndHeroesCatalog
import com.example.model.AiDifficulty
import com.example.model.BattleSide
import com.example.model.FactionId
import com.example.model.GameMode
import com.example.model.HeroDefinition
import com.example.model.HexCoord
import com.example.model.LocalizedText
import com.example.model.ObjectiveType
import com.example.model.TerrainType
import com.example.model.UnitArchetype
import com.example.model.WeatherType
import org.json.JSONArray
import org.json.JSONObject

data class HexTileState(
    val coord: HexCoord,
    val terrain: TerrainType,
    val label: String? = null,
    val wallIntegrity: Int = if (terrain.isFortification) 100 else 0
) {
    val isBreached: Boolean get() = terrain.isFortification && wallIntegrity <= 0
    val effectiveDefenseBonus: Int
        get() = if (isBreached) 10 else terrain.defenseBonus
}

data class BattleUnitState(
    val instanceId: String,
    val archetypeId: String,
    val customName: String? = null,
    val side: BattleSide,
    val factionId: FactionId,
    val coord: HexCoord,
    val facing: Int = if (side == BattleSide.SIDE_A) 0 else 3, // 0 = East, 3 = West
    val currentHp: Int,
    val maxHp: Int,
    val currentMorale: Int = 100,
    val maxMorale: Int = 100,
    val isHero: Boolean = false,
    val heroId: String? = null,
    val heroCooldown: Int = 0,
    val abilityCooldown: Int = 0,
    val hasMovedThisTurn: Boolean = false,
    val hasAttackedThisTurn: Boolean = false,
    val distanceMovedThisTurn: Int = 0,
    val previousCoordBeforeMove: HexCoord? = null,
    val isRouted: Boolean = false,
    val temporaryAttackBuffPercent: Int = 0,
    val temporaryDefenseBuffPercent: Int = 0
) {
    val archetype: UnitArchetype
        get() = UnitsAndHeroesCatalog.unitById(archetypeId)

    val heroDefinition: HeroDefinition?
        get() = heroId?.let { UnitsAndHeroesCatalog.heroById(it) }

    val canAct: Boolean
        get() = currentHp > 0 && !isRouted && (!hasMovedThisTurn || !hasAttackedThisTurn)

    val canUndoMove: Boolean
        get() = hasMovedThisTurn && !hasAttackedThisTurn && previousCoordBeforeMove != null
}

data class CombatPreview(
    val attackerId: String,
    val defenderId: String,
    val expectedDamageToDefender: Int,
    val expectedCounterDamageToAttacker: Int,
    val expectedMoraleDropDefender: Int,
    val expectedWallDamage: Int,
    val isFlanking: Boolean,
    val isRearAttack: Boolean,
    val hasElevationAdvantage: Boolean,
    val hasHeroAura: Boolean,
    val defenderWillRout: Boolean,
    val defenderWillBeDefeated: Boolean,
    val modifierTags: List<String>
)

data class FloatingBattleCallout(
    val id: Long = System.nanoTime(),
    val coord: HexCoord,
    val text: String,
    val isPositive: Boolean = false,
    val isProjectile: Boolean = false,
    val fromCoord: HexCoord? = null
)

data class BattleOutcome(
    val winnerSide: BattleSide,
    val playerWon: Boolean,
    val starsEarned: Int,
    val turnsTaken: Int,
    val reason: LocalizedText
)

data class BattleState(
    val missionId: String,
    val campaignId: String,
    val gameMode: GameMode,
    val isWhatIf: Boolean,
    val playerSide: BattleSide,
    val aiDifficulty: AiDifficulty,
    val mapWidth: Int,
    val mapHeight: Int,
    val tiles: Map<HexCoord, HexTileState>,
    val units: List<BattleUnitState>,
    val weather: WeatherType,
    val turnNumber: Int = 1,
    val maxTurns: Int = 14,
    val activeSide: BattleSide = BattleSide.SIDE_A,
    val sideAFaction: FactionId,
    val sideBFaction: FactionId,
    val sideAName: LocalizedText,
    val sideBName: LocalizedText,
    val sideAObjectiveType: ObjectiveType,
    val sideBObjectiveType: ObjectiveType,
    val sideAObjectiveDesc: LocalizedText,
    val sideBObjectiveDesc: LocalizedText,
    val targetHex: HexCoord? = null,
    val surviveTurnsTarget: Int = 9,
    val selectedUnitId: String? = null,
    val reachableHexes: Map<HexCoord, Int> = emptyMap(),
    val attackableUnitIds: Set<String> = emptySet(),
    val pendingCombatPreview: CombatPreview? = null,
    val useSpecialAbilityOnConfirm: Boolean = false,
    val battleLog: List<String> = emptyList(),
    val recentCallouts: List<FloatingBattleCallout> = emptyList(),
    val outcome: BattleOutcome? = null,
    val tutorialStep: Int = 0 // 0 = off, 1..4 = guided steps
) {
    fun unitAt(coord: HexCoord): BattleUnitState? =
        units.firstOrNull { it.currentHp > 0 && it.coord == coord }

    fun unitById(id: String?): BattleUnitState? =
        if (id == null) null else units.firstOrNull { it.instanceId == id && it.currentHp > 0 }

    fun tileAt(coord: HexCoord): HexTileState? = tiles[coord]

    fun isPlayerTurn(): Boolean {
        return when (gameMode) {
            GameMode.PASS_AND_PLAY -> true
            else -> activeSide == playerSide
        }
    }

    fun toJsonString(): String {
        val root = JSONObject()
        root.put("missionId", missionId)
        root.put("campaignId", campaignId)
        root.put("gameMode", gameMode.name)
        root.put("isWhatIf", isWhatIf)
        root.put("playerSide", playerSide.name)
        root.put("aiDifficulty", aiDifficulty.name)
        root.put("mapWidth", mapWidth)
        root.put("mapHeight", mapHeight)
        root.put("weather", weather.name)
        root.put("turnNumber", turnNumber)
        root.put("maxTurns", maxTurns)
        root.put("activeSide", activeSide.name)
        root.put("sideAFaction", sideAFaction.name)
        root.put("sideBFaction", sideBFaction.name)
        root.put("sideAObjectiveType", sideAObjectiveType.name)
        root.put("sideBObjectiveType", sideBObjectiveType.name)
        root.put("surviveTurnsTarget", surviveTurnsTarget)
        if (targetHex != null) {
            root.put("targetQ", targetHex.q)
            root.put("targetR", targetHex.r)
        }

        val tilesArr = JSONArray()
        tiles.values.forEach { t ->
            val obj = JSONObject()
            obj.put("q", t.coord.q)
            obj.put("r", t.coord.r)
            obj.put("terrain", t.terrain.name)
            if (t.label != null) obj.put("label", t.label)
            obj.put("wallIntegrity", t.wallIntegrity)
            tilesArr.put(obj)
        }
        root.put("tiles", tilesArr)

        val unitsArr = JSONArray()
        units.forEach { u ->
            val obj = JSONObject()
            obj.put("instanceId", u.instanceId)
            obj.put("archetypeId", u.archetypeId)
            if (u.customName != null) obj.put("customName", u.customName)
            obj.put("side", u.side.name)
            obj.put("factionId", u.factionId.name)
            obj.put("q", u.coord.q)
            obj.put("r", u.coord.r)
            obj.put("facing", u.facing)
            obj.put("currentHp", u.currentHp)
            obj.put("maxHp", u.maxHp)
            obj.put("currentMorale", u.currentMorale)
            obj.put("maxMorale", u.maxMorale)
            obj.put("isHero", u.isHero)
            if (u.heroId != null) obj.put("heroId", u.heroId)
            obj.put("heroCooldown", u.heroCooldown)
            obj.put("abilityCooldown", u.abilityCooldown)
            obj.put("hasMovedThisTurn", u.hasMovedThisTurn)
            obj.put("hasAttackedThisTurn", u.hasAttackedThisTurn)
            obj.put("isRouted", u.isRouted)
            unitsArr.put(obj)
        }
        root.put("units", unitsArr)
        return root.toString()
    }

    companion object {
        fun fromJsonString(
            json: String,
            sideAName: LocalizedText,
            sideBName: LocalizedText,
            sideAObjectiveDesc: LocalizedText,
            sideBObjectiveDesc: LocalizedText
        ): BattleState {
            val root = JSONObject(json)
            val tilesMap = mutableMapOf<HexCoord, HexTileState>()
            val tilesArr = root.getJSONArray("tiles")
            for (i in 0 until tilesArr.length()) {
                val obj = tilesArr.getJSONObject(i)
                val coord = HexCoord(obj.getInt("q"), obj.getInt("r"))
                val terrain = TerrainType.valueOf(obj.getString("terrain"))
                tilesMap[coord] = HexTileState(
                    coord = coord,
                    terrain = terrain,
                    label = if (obj.has("label")) obj.getString("label") else null,
                    wallIntegrity = obj.optInt("wallIntegrity", if (terrain.isFortification) 100 else 0)
                )
            }

            val unitsList = mutableListOf<BattleUnitState>()
            val unitsArr = root.getJSONArray("units")
            for (i in 0 until unitsArr.length()) {
                val obj = unitsArr.getJSONObject(i)
                unitsList.add(
                    BattleUnitState(
                        instanceId = obj.getString("instanceId"),
                        archetypeId = obj.getString("archetypeId"),
                        customName = if (obj.has("customName")) obj.getString("customName") else null,
                        side = BattleSide.valueOf(obj.getString("side")),
                        factionId = FactionId.valueOf(obj.getString("factionId")),
                        coord = HexCoord(obj.getInt("q"), obj.getInt("r")),
                        facing = obj.optInt("facing", 0),
                        currentHp = obj.getInt("currentHp"),
                        maxHp = obj.getInt("maxHp"),
                        currentMorale = obj.optInt("currentMorale", 100),
                        maxMorale = obj.optInt("maxMorale", 100),
                        isHero = obj.optBoolean("isHero", false),
                        heroId = if (obj.has("heroId")) obj.getString("heroId") else null,
                        heroCooldown = obj.optInt("heroCooldown", 0),
                        abilityCooldown = obj.optInt("abilityCooldown", 0),
                        hasMovedThisTurn = obj.optBoolean("hasMovedThisTurn", false),
                        hasAttackedThisTurn = obj.optBoolean("hasAttackedThisTurn", false),
                        isRouted = obj.optBoolean("isRouted", false)
                    )
                )
            }

            val targetHex = if (root.has("targetQ") && root.has("targetR")) {
                HexCoord(root.getInt("targetQ"), root.getInt("targetR"))
            } else null

            return BattleState(
                missionId = root.getString("missionId"),
                campaignId = root.getString("campaignId"),
                gameMode = GameMode.valueOf(root.getString("gameMode")),
                isWhatIf = root.getBoolean("isWhatIf"),
                playerSide = BattleSide.valueOf(root.getString("playerSide")),
                aiDifficulty = AiDifficulty.valueOf(root.optString("aiDifficulty", "COMMANDER")),
                mapWidth = root.getInt("mapWidth"),
                mapHeight = root.getInt("mapHeight"),
                tiles = tilesMap,
                units = unitsList,
                weather = WeatherType.valueOf(root.getString("weather")),
                turnNumber = root.getInt("turnNumber"),
                maxTurns = root.getInt("maxTurns"),
                activeSide = BattleSide.valueOf(root.getString("activeSide")),
                sideAFaction = FactionId.valueOf(root.getString("sideAFaction")),
                sideBFaction = FactionId.valueOf(root.getString("sideBFaction")),
                sideAName = sideAName,
                sideBName = sideBName,
                sideAObjectiveType = ObjectiveType.valueOf(root.getString("sideAObjectiveType")),
                sideBObjectiveType = ObjectiveType.valueOf(root.getString("sideBObjectiveType")),
                sideAObjectiveDesc = sideAObjectiveDesc,
                sideBObjectiveDesc = sideBObjectiveDesc,
                targetHex = targetHex,
                surviveTurnsTarget = root.optInt("surviveTurnsTarget", 9)
            )
        }
    }
}
