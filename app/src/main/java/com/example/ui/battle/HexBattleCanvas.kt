package com.example.ui.battle

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.domain.engine.BattleState
import com.example.domain.engine.BattleUnitState
import com.example.domain.engine.HexEngine
import com.example.domain.engine.HexTileState
import com.example.model.BattleSide
import com.example.model.HexCoord
import com.example.model.TerrainType
import com.example.model.WeatherType
import com.example.ui.components.drawUnitCategoryGlyph
import com.example.ui.theme.BurntTerracotta
import com.example.ui.theme.CrimsonSeal
import com.example.ui.theme.LapisDeepBlue
import com.example.ui.theme.ManuscriptInk
import com.example.ui.theme.ParchmentIvory
import com.example.ui.theme.RegistanGold
import com.example.ui.theme.RegistanGoldBright
import com.example.ui.theme.SamarkandTurquoise
import com.example.ui.theme.TeamAColorblind
import com.example.ui.theme.TeamAStandard
import com.example.ui.theme.TeamBColorblind
import com.example.ui.theme.TeamBStandard
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun HexBattleCanvas(
    battleState: BattleState,
    colorblindSafe: Boolean,
    onHexTapped: (HexCoord) -> Unit,
    modifier: Modifier = Modifier
) {
    var zoom by remember { mutableFloatStateOf(1.0f) }
    var panOffset by remember { mutableStateOf(Offset.Zero) }

    val infiniteTransition = rememberInfiniteTransition(label = "hexPulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )
    val weatherPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "weatherPhase"
    )

    val textPaint = remember {
        android.graphics.Paint().apply {
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.SERIF, android.graphics.Typeface.BOLD)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A242D))
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .testTag("hex_battle_canvas")
                .pointerInput(battleState.mapWidth, battleState.mapHeight) {
                    detectTransformGestures { _, pan, gestureZoom, _ ->
                        zoom = (zoom * gestureZoom).coerceIn(0.65f, 2.25f)
                        panOffset = Offset(
                            x = (panOffset.x + pan.x).coerceIn(-size.width * 0.7f, size.width * 0.7f),
                            y = (panOffset.y + pan.y).coerceIn(-size.height * 0.7f, size.height * 0.7f)
                        )
                    }
                }
                .pointerInput(battleState, zoom, panOffset) {
                    detectTapGestures { tapOffset ->
                        val w = size.width.toFloat()
                        val h = size.height.toFloat()
                        val baseRadius = computeBaseHexRadius(w, h, battleState.mapWidth, battleState.mapHeight)
                        val effectiveRadius = baseRadius * zoom
                        val boardOrigin = computeBoardOrigin(
                            w,
                            h,
                            effectiveRadius,
                            battleState.mapWidth,
                            battleState.mapHeight,
                            panOffset
                        )
                        val localPoint = tapOffset - boardOrigin
                        val tappedHex = HexEngine.pixelToHex(
                            pixel = localPoint,
                            hexRadius = effectiveRadius,
                            mapWidth = battleState.mapWidth,
                            mapHeight = battleState.mapHeight
                        )
                        if (tappedHex != null) {
                            onHexTapped(tappedHex)
                        }
                    }
                }
        ) {
            val w = size.width
            val h = size.height
            val baseRadius = computeBaseHexRadius(w, h, battleState.mapWidth, battleState.mapHeight)
            val radius = baseRadius * zoom
            val origin = computeBoardOrigin(w, h, radius, battleState.mapWidth, battleState.mapHeight, panOffset)

            val selectedUnit = battleState.unitById(battleState.selectedUnitId)

            // 1. Draw all Hex Tiles
            for (q in 0 until battleState.mapWidth) {
                for (r in 0 until battleState.mapHeight) {
                    val coord = HexCoord(q, r)
                    val tile = battleState.tileAt(coord) ?: HexTileState(coord, TerrainType.PLAINS)
                    val center = origin + HexEngine.hexToPixel(coord, radius)
                    val vertices = HexEngine.hexVertices(center, radius * 0.98f)
                    val hexPath = Path().apply {
                        moveTo(vertices[0].x, vertices[0].y)
                        for (i in 1 until 6) lineTo(vertices[i].x, vertices[i].y)
                        close()
                    }

                    val fillColor = terrainFillColor(tile)
                    drawPath(hexPath, color = fillColor)

                    // Terrain artistic details
                    drawTerrainArtwork(tile, center, radius)

                    // Hex grid border
                    drawPath(
                        hexPath,
                        color = Color(0xFF4A3B28).copy(alpha = 0.55f),
                        style = Stroke(width = maxOf(1f, radius * 0.03f))
                    )

                    // Target Objective Hex highlight
                    if (battleState.targetHex == coord) {
                        drawPath(
                            hexPath,
                            color = RegistanGoldBright.copy(alpha = pulseAlpha * 0.38f)
                        )
                        drawPath(
                            hexPath,
                            color = RegistanGoldBright,
                            style = Stroke(
                                width = radius * 0.09f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
                            )
                        )
                    }

                    // Reachable Movement Highlight
                    val moveCost = battleState.reachableHexes[coord]
                    if (moveCost != null) {
                        drawPath(
                            hexPath,
                            color = SamarkandTurquoise.copy(alpha = 0.34f * pulseAlpha + 0.15f)
                        )
                        drawPath(
                            hexPath,
                            color = SamarkandTurquoise,
                            style = Stroke(width = radius * 0.07f)
                        )
                    }

                    // Tile Named Landmark Label
                    if (tile.label != null && radius >= 24f) {
                        textPaint.color = android.graphics.Color.argb(225, 255, 245, 214)
                        textPaint.textSize = radius * 0.22f
                        drawContext.canvas.nativeCanvas.drawText(
                            tile.label,
                            center.x,
                            center.y - radius * 0.62f,
                            textPaint
                        )
                    }
                }
            }

            // 2. Hero Passive Aura Ring (2 hexes) when a Hero is selected
            if (selectedUnit != null && selectedUnit.isHero) {
                val heroCenter = origin + HexEngine.hexToPixel(selectedUnit.coord, radius)
                drawCircle(
                    color = RegistanGoldBright.copy(alpha = 0.25f),
                    radius = radius * 3.4f,
                    center = heroCenter,
                    style = Stroke(
                        width = radius * 0.05f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
                    )
                )
            }

            // 3. Draw Units on the Hex Grid
            battleState.units.filter { it.currentHp > 0 }.forEach { unit ->
                val center = origin + HexEngine.hexToPixel(unit.coord, radius)
                val isSelected = unit.instanceId == battleState.selectedUnitId
                val isAttackable = unit.instanceId in battleState.attackableUnitIds
                val isPreviewTarget = battleState.pendingCombatPreview?.defenderId == unit.instanceId

                drawBattleUnitOnHex(
                    unit = unit,
                    center = center,
                    radius = radius,
                    isSelected = isSelected,
                    isAttackable = isAttackable,
                    isPreviewTarget = isPreviewTarget,
                    isActiveSide = unit.side == battleState.activeSide,
                    colorblindSafe = colorblindSafe,
                    pulseAlpha = pulseAlpha,
                    textPaint = textPaint
                )
            }

            // 4. Draw Recent Projectiles & Callouts
            battleState.recentCallouts.forEach { callout ->
                val targetCenter = origin + HexEngine.hexToPixel(callout.coord, radius)
                if (callout.isProjectile && callout.fromCoord != null) {
                    val fromCenter = origin + HexEngine.hexToPixel(callout.fromCoord, radius)
                    val mid = Offset(
                        x = (fromCenter.x + targetCenter.x) / 2f,
                        y = min(fromCenter.y, targetCenter.y) - radius * 0.9f
                    )
                    val arc = Path().apply {
                        moveTo(fromCenter.x, fromCenter.y)
                        quadraticTo(mid.x, mid.y, targetCenter.x, targetCenter.y)
                    }
                    drawPath(
                        arc,
                        color = RegistanGoldBright.copy(alpha = 0.85f),
                        style = Stroke(
                            width = radius * 0.07f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
                        )
                    )
                }

                // Callout pill
                val pillWidth = radius * 1.45f
                val pillHeight = radius * 0.42f
                val pillTopLeft = Offset(targetCenter.x - pillWidth / 2f, targetCenter.y - radius * 1.05f)
                drawRoundRect(
                    color = if (callout.isPositive) SamarkandTurquoise else CrimsonSeal,
                    topLeft = pillTopLeft,
                    size = Size(pillWidth, pillHeight),
                    cornerRadius = CornerRadius(6f, 6f)
                )
                textPaint.color = android.graphics.Color.WHITE
                textPaint.textSize = radius * 0.24f
                drawContext.canvas.nativeCanvas.drawText(
                    callout.text,
                    targetCenter.x,
                    pillTopLeft.y + pillHeight * 0.72f,
                    textPaint
                )
            }

            // 5. Weather Atmospheric Overlay
            drawWeatherOverlay(battleState.weather, weatherPhase)
        }

        // Camera Reset Button (top-right inside map)
        if (zoom != 1.0f || panOffset != Offset.Zero) {
            FilledTonalIconButton(
                onClick = {
                    zoom = 1.0f
                    panOffset = Offset.Zero
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .testTag("reset_camera_button"),
                shape = RoundedCornerShape(10.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = LapisDeepBlue.copy(alpha = 0.85f),
                    contentColor = RegistanGoldBright
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CenterFocusStrong,
                    contentDescription = "Reset Map Camera"
                )
            }
        }
    }
}

private fun computeBaseHexRadius(
    canvasWidth: Float,
    canvasHeight: Float,
    mapWidth: Int,
    mapHeight: Int
): Float {
    val horizFactor = 1.5f * (mapWidth - 1) + 2.2f
    val vertFactor = sqrt(3f) * (mapHeight + 0.7f)
    val rByWidth = (canvasWidth * 0.94f) / horizFactor
    val rByHeight = (canvasHeight * 0.90f) / vertFactor
    return min(rByWidth, rByHeight).coerceAtLeast(18f)
}

private fun computeBoardOrigin(
    canvasWidth: Float,
    canvasHeight: Float,
    hexRadius: Float,
    mapWidth: Int,
    mapHeight: Int,
    panOffset: Offset
): Offset {
    val totalBoardWidth = hexRadius * (1.5f * (mapWidth - 1))
    val totalBoardHeight = hexRadius * sqrt(3f) * (mapHeight - 0.5f)
    val startX = (canvasWidth - totalBoardWidth) / 2f + panOffset.x
    val startY = (canvasHeight - totalBoardHeight) / 2f + panOffset.y
    return Offset(startX, startY)
}

private fun terrainFillColor(tile: HexTileState): Color {
    return when (tile.terrain) {
        TerrainType.PLAINS -> Color(0xFFDCC99E)
        TerrainType.STEPPE -> Color(0xFFC8BE7A)
        TerrainType.ROAD -> Color(0xFFE4D5B7)
        TerrainType.HILLS -> Color(0xFFB5986B)
        TerrainType.FOREST -> Color(0xFF7B945B)
        TerrainType.RIVER -> Color(0xFF287494)
        TerrainType.DESERT -> Color(0xFFE2B874)
        TerrainType.MOUNTAIN -> Color(0xFF7E7870)
        TerrainType.CITY_WALLS -> if (tile.isBreached) Color(0xFF9C8468) else Color(0xFF9E7B56)
        TerrainType.FORTRESS -> Color(0xFF1F4E69)
    }
}

private fun DrawScope.drawTerrainArtwork(
    tile: HexTileState,
    center: Offset,
    radius: Float
) {
    when (tile.terrain) {
        TerrainType.STEPPE -> {
            // Steppe grass tufts
            val grassColor = Color(0xFF6B7B3C).copy(alpha = 0.65f)
            drawLine(grassColor, Offset(center.x - radius * 0.35f, center.y + radius * 0.25f), Offset(center.x - radius * 0.42f, center.y + radius * 0.08f), strokeWidth = 2f)
            drawLine(grassColor, Offset(center.x - radius * 0.35f, center.y + radius * 0.25f), Offset(center.x - radius * 0.28f, center.y + radius * 0.08f), strokeWidth = 2f)
            drawLine(grassColor, Offset(center.x + radius * 0.32f, center.y - radius * 0.15f), Offset(center.x + radius * 0.38f, center.y - radius * 0.30f), strokeWidth = 2f)
        }
        TerrainType.HILLS -> {
            val hillPath = Path().apply {
                moveTo(center.x - radius * 0.55f, center.y + radius * 0.25f)
                quadraticTo(center.x, center.y - radius * 0.45f, center.x + radius * 0.55f, center.y + radius * 0.25f)
            }
            drawPath(hillPath, color = Color(0xFF7B5E3B).copy(alpha = 0.55f), style = Stroke(width = radius * 0.08f))
        }
        TerrainType.MOUNTAIN -> {
            val peak = Path().apply {
                moveTo(center.x - radius * 0.55f, center.y + radius * 0.35f)
                lineTo(center.x - radius * 0.10f, center.y - radius * 0.48f)
                lineTo(center.x + radius * 0.22f, center.y - radius * 0.10f)
                lineTo(center.x + radius * 0.40f, center.y - radius * 0.35f)
                lineTo(center.x + radius * 0.60f, center.y + radius * 0.35f)
                close()
            }
            drawPath(peak, color = Color(0xFF5A534A))
            // Snow cap
            val snow = Path().apply {
                moveTo(center.x - radius * 0.10f, center.y - radius * 0.48f)
                lineTo(center.x - radius * 0.24f, center.y - radius * 0.20f)
                lineTo(center.x + radius * 0.05f, center.y - radius * 0.20f)
                close()
            }
            drawPath(snow, color = ParchmentIvory.copy(alpha = 0.85f))
        }
        TerrainType.FOREST -> {
            val treeColor = Color(0xFF3E5C2A)
            listOf(
                Offset(center.x - radius * 0.26f, center.y + radius * 0.05f),
                Offset(center.x + radius * 0.26f, center.y + radius * 0.05f),
                Offset(center.x, center.y - radius * 0.22f)
            ).forEach { pos ->
                drawCircle(color = treeColor, radius = radius * 0.22f, center = pos)
            }
        }
        TerrainType.RIVER -> {
            val waveColor = Color(0xFF8CD3E8).copy(alpha = 0.55f)
            drawLine(
                color = waveColor,
                start = Offset(center.x - radius * 0.42f, center.y - radius * 0.12f),
                end = Offset(center.x + radius * 0.42f, center.y - radius * 0.12f),
                strokeWidth = radius * 0.06f
            )
            drawLine(
                color = waveColor,
                start = Offset(center.x - radius * 0.30f, center.y + radius * 0.16f),
                end = Offset(center.x + radius * 0.30f, center.y + radius * 0.16f),
                strokeWidth = radius * 0.06f
            )
        }
        TerrainType.DESERT -> {
            val dune = Path().apply {
                moveTo(center.x - radius * 0.5f, center.y + radius * 0.15f)
                quadraticTo(center.x, center.y - radius * 0.15f, center.x + radius * 0.5f, center.y + radius * 0.15f)
            }
            drawPath(dune, color = Color(0xFFB8863B).copy(alpha = 0.5f), style = Stroke(width = radius * 0.06f))
        }
        TerrainType.ROAD -> {
            drawLine(
                color = Color(0xFF8C6D46),
                start = Offset(center.x - radius * 0.65f, center.y),
                end = Offset(center.x + radius * 0.65f, center.y),
                strokeWidth = radius * 0.18f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
            )
        }
        TerrainType.CITY_WALLS, TerrainType.FORTRESS -> {
            val wallColor = if (tile.isBreached) CrimsonSeal.copy(alpha = 0.5f) else RegistanGold.copy(alpha = 0.75f)
            drawRoundRect(
                color = wallColor,
                topLeft = Offset(center.x - radius * 0.52f, center.y - radius * 0.52f),
                size = Size(radius * 1.04f, radius * 1.04f),
                cornerRadius = CornerRadius(radius * 0.15f),
                style = Stroke(width = radius * 0.08f)
            )
            if (tile.terrain == TerrainType.FORTRESS) {
                // Turquoise Samarkand dome arch
                drawCircle(
                    color = SamarkandTurquoise.copy(alpha = 0.45f),
                    radius = radius * 0.38f,
                    center = center
                )
            }
            // Wall integrity mini bar at top of fortification hex
            if (!tile.isBreached && tile.wallIntegrity < 100) {
                val barW = radius * 0.8f
                val barH = radius * 0.10f
                val topLeft = Offset(center.x - barW / 2f, center.y - radius * 0.72f)
                drawRect(Color.Black.copy(alpha = 0.5f), topLeft, Size(barW, barH))
                drawRect(
                    RegistanGoldBright,
                    topLeft,
                    Size(barW * (tile.wallIntegrity / 100f), barH)
                )
            }
        }
        TerrainType.PLAINS -> {}
    }
}

private fun DrawScope.drawBattleUnitOnHex(
    unit: BattleUnitState,
    center: Offset,
    radius: Float,
    isSelected: Boolean,
    isAttackable: Boolean,
    isPreviewTarget: Boolean,
    isActiveSide: Boolean,
    colorblindSafe: Boolean,
    pulseAlpha: Float,
    textPaint: android.graphics.Paint
) {
    val teamColor = if (unit.side == BattleSide.SIDE_A) {
        if (colorblindSafe) TeamAColorblind else TeamAStandard
    } else {
        if (colorblindSafe) TeamBColorblind else TeamBStandard
    }

    // Selection or Target Reticle Ring
    if (isSelected) {
        drawCircle(
            color = RegistanGoldBright,
            radius = radius * 0.74f,
            center = center,
            style = Stroke(width = radius * 0.10f)
        )
    } else if (isAttackable || isPreviewTarget) {
        drawCircle(
            color = CrimsonSeal.copy(alpha = pulseAlpha),
            radius = radius * 0.76f,
            center = center,
            style = Stroke(width = radius * 0.11f)
        )
    }

    val tokenRadius = radius * 0.56f

    // Colorblind-safe distinct shape: Side A = Circle Medallion, Side B = Octagonal/Diamond Shield
    if (colorblindSafe && unit.side == BattleSide.SIDE_B) {
        val diamond = Path().apply {
            moveTo(center.x, center.y - tokenRadius * 1.12f)
            lineTo(center.x + tokenRadius * 1.12f, center.y)
            lineTo(center.x, center.y + tokenRadius * 1.12f)
            lineTo(center.x - tokenRadius * 1.12f, center.y)
            close()
        }
        drawPath(diamond, color = teamColor)
        drawPath(
            diamond,
            color = if (unit.isHero) RegistanGoldBright else ParchmentIvory,
            style = Stroke(width = if (unit.isHero) radius * 0.08f else radius * 0.05f)
        )
    } else {
        drawCircle(
            color = teamColor,
            radius = tokenRadius,
            center = center
        )
        drawCircle(
            color = if (unit.isHero) RegistanGoldBright else ParchmentIvory,
            radius = tokenRadius,
            center = center,
            style = Stroke(width = if (unit.isHero) radius * 0.08f else radius * 0.05f)
        )
    }

    // Draw Unit Category Silhouette inside Token
    drawUnitCategoryGlyph(
        category = unit.archetype.category,
        tint = ParchmentIvory,
        accent = RegistanGoldBright,
        center = center,
        radius = tokenRadius * 0.85f
    )

    // Hero Crown/Star Badge at top of token
    if (unit.isHero) {
        val starCenter = Offset(center.x, center.y - tokenRadius * 0.95f)
        drawCircle(color = LapisDeepBlue, radius = radius * 0.18f, center = starCenter)
        drawCircle(color = RegistanGoldBright, radius = radius * 0.13f, center = starCenter)
    }

    // Action Ready Indicator Dot (top-right of token)
    if (isActiveSide && unit.canAct) {
        val dotCenter = Offset(center.x + tokenRadius * 0.75f, center.y - tokenRadius * 0.72f)
        drawCircle(color = ManuscriptInk, radius = radius * 0.13f, center = dotCenter)
        drawCircle(color = Color(0xFF4ADE80), radius = radius * 0.09f, center = dotCenter)
    }

    // Dual Health & Morale Bars at bottom of hex
    val barWidth = radius * 0.96f
    val hpHeight = radius * 0.12f
    val moraleHeight = radius * 0.09f
    val barLeft = center.x - barWidth / 2f
    val hpTop = center.y + tokenRadius * 0.72f
    val moraleTop = hpTop + hpHeight + 1.5f

    // Background box
    drawRoundRect(
        color = Color(0xCC11161B),
        topLeft = Offset(barLeft - 2f, hpTop - 2f),
        size = Size(barWidth + 4f, hpHeight + moraleHeight + 5f),
        cornerRadius = CornerRadius(4f, 4f)
    )

    // HP Bar
    val hpFraction = (unit.currentHp.toFloat() / unit.maxHp.coerceAtLeast(1)).coerceIn(0f, 1f)
    val hpColor = when {
        hpFraction > 0.55f -> Color(0xFF34D399)
        hpFraction > 0.28f -> RegistanGoldBright
        else -> BurntTerracotta
    }
    drawRect(
        color = hpColor,
        topLeft = Offset(barLeft, hpTop),
        size = Size(barWidth * hpFraction, hpHeight)
    )

    // Morale Bar
    val moraleFraction = (unit.currentMorale.toFloat() / unit.maxMorale.coerceAtLeast(1)).coerceIn(0f, 1f)
    val moraleColor = if (unit.isRouted) CrimsonSeal else Color(0xFF60A5FA)
    drawRect(
        color = moraleColor,
        topLeft = Offset(barLeft, moraleTop),
        size = Size(barWidth * moraleFraction, moraleHeight)
    )

    // Routed flag overlay
    if (unit.isRouted) {
        textPaint.color = android.graphics.Color.rgb(255, 190, 190)
        textPaint.textSize = radius * 0.22f
        drawContext.canvas.nativeCanvas.drawText(
            "ROUT!",
            center.x,
            center.y + radius * 0.08f,
            textPaint
        )
    }
}

private fun DrawScope.drawWeatherOverlay(
    weather: WeatherType,
    phase: Float
) {
    when (weather) {
        WeatherType.CLEAR -> {}
        WeatherType.SANDSTORM -> {
            drawRect(color = Color(0x26D49B4B))
            val streakColor = Color(0x40E8C682)
            for (i in 0 until 12) {
                val y = ((i * 83f + phase * size.height) % size.height)
                val x = ((i * 137f + phase * size.width) % size.width)
                drawLine(
                    color = streakColor,
                    start = Offset(x - 45f, y),
                    end = Offset(x + 45f, y - 12f),
                    strokeWidth = 3f
                )
            }
        }
        WeatherType.MUD, WeatherType.RAIN -> {
            drawRect(color = Color(0x1F1E3A5F))
            val rainColor = Color(0x4593C5FD)
            for (i in 0 until 16) {
                val x = ((i * 97f + phase * size.width * 0.5f) % size.width)
                val y = ((i * 71f + phase * size.height) % size.height)
                drawLine(
                    color = rainColor,
                    start = Offset(x, y),
                    end = Offset(x - 10f, y + 26f),
                    strokeWidth = 2f
                )
            }
        }
        WeatherType.SNOW -> {
            drawRect(color = Color(0x1FE2E8F0))
            val snowColor = Color(0x75FFFFFF)
            for (i in 0 until 18) {
                val x = ((i * 89f + sin((phase + i) * 2 * PI).toFloat() * 20f) % size.width).let { if (it < 0) it + size.width else it }
                val y = ((i * 67f + phase * size.height) % size.height)
                drawCircle(color = snowColor, radius = 3.5f, center = Offset(x, y))
            }
        }
        WeatherType.NIGHT -> {
            drawRect(color = Color(0x38091326))
        }
    }
}
