package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.LanguageCode
import com.example.model.SourceReliability
import com.example.model.UnitCategory
import com.example.ui.theme.BurntTerracotta
import com.example.ui.theme.CrimsonSeal
import com.example.ui.theme.LapisDeepBlue
import com.example.ui.theme.LapisRoyalBlue
import com.example.ui.theme.ManuscriptInk
import com.example.ui.theme.ParchmentIvory
import com.example.ui.theme.ParchmentWarm
import com.example.ui.theme.RegistanGold
import com.example.ui.theme.RegistanGoldBright
import com.example.ui.theme.SamarkandTealDark
import com.example.ui.theme.SamarkandTurquoise

/**
 * Parchment card with ornamental Central Asian geometric corner medallions and double gold-turquoise border.
 */
@Composable
fun SamarkandOrnamentalCard(
    modifier: Modifier = Modifier,
    containerColor: Color = ParchmentWarm,
    borderColor: Color = RegistanGold,
    contentPadding: PaddingValues = PaddingValues(14.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.5.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box {
            Canvas(modifier = Modifier.matchParentSize()) {
                val stroke = 1.dp.toPx()
                val inset = 4.dp.toPx()
                drawRoundRect(
                    color = SamarkandTurquoise.copy(alpha = 0.35f),
                    topLeft = Offset(inset, inset),
                    size = Size(size.width - inset * 2, size.height - inset * 2),
                    cornerRadius = CornerRadius(8.dp.toPx()),
                    style = Stroke(width = stroke)
                )
                // Four corner diamonds
                val d = 5.dp.toPx()
                val corners = listOf(
                    Offset(inset * 2, inset * 2),
                    Offset(size.width - inset * 2, inset * 2),
                    Offset(inset * 2, size.height - inset * 2),
                    Offset(size.width - inset * 2, size.height - inset * 2)
                )
                corners.forEach { c ->
                    val p = Path().apply {
                        moveTo(c.x, c.y - d)
                        lineTo(c.x + d, c.y)
                        lineTo(c.x, c.y + d)
                        lineTo(c.x - d, c.y)
                        close()
                    }
                    drawPath(p, color = borderColor.copy(alpha = 0.7f))
                }
            }
            Column(
                modifier = Modifier.padding(contentPadding),
                content = content
            )
        }
    }
}

/**
 * Hand-painted miniature style portrait medallion for any of the 12 historical commanders.
 */
@Composable
fun HeroPortraitMedallion(
    heroId: String,
    size: Dp = 64.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(LapisRoyalBlue, LapisDeepBlue)
                )
            )
            .border(2.dp, RegistanGold, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height
            val cx = w / 2f
            val cy = h / 2f

            // Inner illuminated halo / Samarkand arch
            drawCircle(
                color = RegistanGold.copy(alpha = 0.22f),
                radius = w * 0.38f,
                center = Offset(cx, cy * 0.88f)
            )
            drawCircle(
                color = SamarkandTurquoise.copy(alpha = 0.25f),
                radius = w * 0.44f,
                center = Offset(cx, cy),
                style = Stroke(width = w * 0.03f)
            )

            // Shoulders / Armored Coat
            val coatColor = when (heroId) {
                "amir_timur", "babur" -> Color(0xFF154360)
                "ulugh_beg" -> SamarkandTealDark
                "jalal_ad_din", "muhammad_khwarazm" -> Color(0xFF116A5E)
                "genghis_subutai", "tokhtamysh" -> Color(0xFF6E2C1B)
                "saladin", "bayezid_i" -> Color(0xFF8E5B16)
                else -> CrimsonSeal
            }
            val shoulderPath = Path().apply {
                moveTo(w * 0.18f, h * 0.95f)
                quadraticTo(cx, h * 0.62f, w * 0.82f, h * 0.95f)
                close()
            }
            drawPath(shoulderPath, color = coatColor)
            drawPath(shoulderPath, color = RegistanGold, style = Stroke(width = w * 0.03f))

            // Face silhouette
            drawCircle(
                color = Color(0xFFE5C298),
                radius = w * 0.17f,
                center = Offset(cx, h * 0.47f)
            )

            // Beard / Mustache detail
            val beardPath = Path().apply {
                moveTo(cx - w * 0.12f, h * 0.51f)
                quadraticTo(cx, h * 0.68f, cx + w * 0.12f, h * 0.51f)
                close()
            }
            drawPath(beardPath, color = Color(0xFF2B2118))

            // Period-accurate Headgear / Helmet / Turban / Crown
            when (heroId) {
                "amir_timur", "jalal_ad_din", "genghis_subutai", "tokhtamysh", "babur" -> {
                    // Pointed Turco-Mongol / Khwarazmian steel-and-gold conical helm with aventail
                    val helm = Path().apply {
                        moveTo(cx, h * 0.12f)
                        lineTo(cx + w * 0.20f, h * 0.39f)
                        lineTo(cx - w * 0.20f, h * 0.39f)
                        close()
                    }
                    drawPath(helm, color = RegistanGoldBright)
                    drawLine(
                        color = SamarkandTurquoise,
                        start = Offset(cx, h * 0.14f),
                        end = Offset(cx, h * 0.38f),
                        strokeWidth = w * 0.04f
                    )
                }
                "ulugh_beg", "muhammad_khwarazm", "saladin", "bayezid_i" -> {
                    // Royal turban wrapped around steel/gold cap + astronomer star for Ulugh Beg
                    drawOval(
                        color = ParchmentIvory,
                        topLeft = Offset(cx - w * 0.22f, h * 0.22f),
                        size = Size(w * 0.44f, h * 0.20f)
                    )
                    drawCircle(
                        color = if (heroId == "ulugh_beg") SamarkandTurquoise else RegistanGold,
                        radius = w * 0.06f,
                        center = Offset(cx, h * 0.28f)
                    )
                }
                else -> {
                    // European nasal helm / royal crown (Richard, William/Harold, Henry V)
                    val crown = Path().apply {
                        moveTo(cx - w * 0.19f, h * 0.38f)
                        lineTo(cx - w * 0.19f, h * 0.22f)
                        lineTo(cx - w * 0.09f, h * 0.30f)
                        lineTo(cx, h * 0.18f)
                        lineTo(cx + w * 0.09f, h * 0.30f)
                        lineTo(cx + w * 0.19f, h * 0.22f)
                        lineTo(cx + w * 0.19f, h * 0.38f)
                        close()
                    }
                    drawPath(crown, color = RegistanGoldBright)
                }
            }
        }
    }
}

/**
 * Draws a unit's distinct silhouette inside a Canvas (used both in UI cards and on the Hex Map).
 */
fun DrawScope.drawUnitCategoryGlyph(
    category: UnitCategory,
    tint: Color,
    accent: Color,
    center: Offset,
    radius: Float
) {
    when (category) {
        UnitCategory.HORSE_ARCHER -> {
            // Horse body + recurve bow arc
            drawOval(
                color = tint,
                topLeft = Offset(center.x - radius * 0.48f, center.y - radius * 0.05f),
                size = Size(radius * 0.9f, radius * 0.42f)
            )
            drawCircle(color = tint, radius = radius * 0.20f, center = Offset(center.x - radius * 0.08f, center.y - radius * 0.26f))
            // Bow arc
            drawArc(
                color = accent,
                startAngle = -65f,
                sweepAngle = 130f,
                useCenter = false,
                topLeft = Offset(center.x - radius * 0.1f, center.y - radius * 0.52f),
                size = Size(radius * 0.65f, radius * 0.65f),
                style = Stroke(width = radius * 0.11f)
            )
        }
        UnitCategory.HEAVY_CAVALRY, UnitCategory.LIGHT_CAVALRY -> {
            // Armored charger + long lance with pennant
            drawOval(
                color = tint,
                topLeft = Offset(center.x - radius * 0.46f, center.y - radius * 0.02f),
                size = Size(radius * 0.92f, radius * 0.44f)
            )
            drawCircle(color = tint, radius = radius * 0.21f, center = Offset(center.x - radius * 0.05f, center.y - radius * 0.25f))
            drawLine(
                color = accent,
                start = Offset(center.x - radius * 0.45f, center.y + radius * 0.15f),
                end = Offset(center.x + radius * 0.58f, center.y - radius * 0.48f),
                strokeWidth = radius * 0.12f
            )
        }
        UnitCategory.ELEPHANT -> {
            // Elephant body, trunk, tusks, and royal howdah tower
            drawRoundRect(
                color = tint,
                topLeft = Offset(center.x - radius * 0.48f, center.y - radius * 0.12f),
                size = Size(radius * 0.88f, radius * 0.55f),
                cornerRadius = CornerRadius(radius * 0.2f)
            )
            // Howdah tower on back
            drawRect(
                color = accent,
                topLeft = Offset(center.x - radius * 0.28f, center.y - radius * 0.48f),
                size = Size(radius * 0.48f, radius * 0.34f)
            )
            // Curved tusk
            drawLine(
                color = ParchmentIvory,
                start = Offset(center.x + radius * 0.32f, center.y + radius * 0.08f),
                end = Offset(center.x + radius * 0.60f, center.y - radius * 0.08f),
                strokeWidth = radius * 0.10f
            )
        }
        UnitCategory.SIEGE -> {
            // Counterweight trebuchet / catapult frame & throwing arm
            val frame = Path().apply {
                moveTo(center.x - radius * 0.45f, center.y + radius * 0.38f)
                lineTo(center.x, center.y - radius * 0.25f)
                lineTo(center.x + radius * 0.45f, center.y + radius * 0.38f)
                close()
            }
            drawPath(frame, color = tint, style = Stroke(width = radius * 0.12f))
            drawLine(
                color = accent,
                start = Offset(center.x - radius * 0.48f, center.y - radius * 0.42f),
                end = Offset(center.x + radius * 0.42f, center.y + radius * 0.08f),
                strokeWidth = radius * 0.12f
            )
            drawCircle(color = accent, radius = radius * 0.16f, center = Offset(center.x - radius * 0.48f, center.y - radius * 0.42f))
        }
        UnitCategory.SPEARMEN -> {
            // Round shield + crossed long pikes
            drawLine(
                color = accent,
                start = Offset(center.x - radius * 0.42f, center.y + radius * 0.45f),
                end = Offset(center.x + radius * 0.42f, center.y - radius * 0.52f),
                strokeWidth = radius * 0.11f
            )
            drawLine(
                color = accent,
                start = Offset(center.x + radius * 0.42f, center.y + radius * 0.45f),
                end = Offset(center.x - radius * 0.42f, center.y - radius * 0.52f),
                strokeWidth = radius * 0.11f
            )
            drawCircle(color = tint, radius = radius * 0.34f, center = center)
            drawCircle(color = accent, radius = radius * 0.14f, center = center)
        }
        UnitCategory.RANGED -> {
            // Composite bow & arrow
            drawArc(
                color = tint,
                startAngle = -75f,
                sweepAngle = 150f,
                useCenter = false,
                topLeft = Offset(center.x - radius * 0.45f, center.y - radius * 0.48f),
                size = Size(radius * 0.85f, radius * 0.96f),
                style = Stroke(width = radius * 0.13f)
            )
            drawLine(
                color = accent,
                start = Offset(center.x - radius * 0.45f, center.y),
                end = Offset(center.x + radius * 0.52f, center.y),
                strokeWidth = radius * 0.11f
            )
        }
        UnitCategory.SUPPORT -> {
            // Battle Standard / Horn / Sapper emblem
            drawLine(
                color = accent,
                start = Offset(center.x - radius * 0.2f, center.y + radius * 0.48f),
                end = Offset(center.x - radius * 0.2f, center.y - radius * 0.48f),
                strokeWidth = radius * 0.12f
            )
            val banner = Path().apply {
                moveTo(center.x - radius * 0.2f, center.y - radius * 0.45f)
                lineTo(center.x + radius * 0.48f, center.y - radius * 0.22f)
                lineTo(center.x - radius * 0.2f, center.y + radius * 0.05f)
                close()
            }
            drawPath(banner, color = tint)
        }
        UnitCategory.INFANTRY -> {
            // Kite/Round shield + crossed scimitar/sword
            val shield = Path().apply {
                moveTo(center.x, center.y - radius * 0.46f)
                lineTo(center.x + radius * 0.38f, center.y - radius * 0.18f)
                lineTo(center.x, center.y + radius * 0.48f)
                lineTo(center.x - radius * 0.38f, center.y - radius * 0.18f)
                close()
            }
            drawPath(shield, color = tint)
            drawPath(shield, color = accent, style = Stroke(width = radius * 0.10f))
        }
    }
}

@Composable
fun UnitCategoryBadge(
    category: UnitCategory,
    primaryColor: Color = LapisDeepBlue,
    accentColor: Color = RegistanGold,
    size: Dp = 44.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(10.dp))
            .background(ParchmentIvory)
            .border(1.5.dp, accentColor, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.78f)) {
            drawUnitCategoryGlyph(
                category = category,
                tint = primaryColor,
                accent = accentColor,
                center = Offset(this.size.width / 2f, this.size.height / 2f),
                radius = this.size.minDimension / 2f
            )
        }
    }
}

@Composable
fun SourceReliabilityBadge(
    reliability: SourceReliability,
    lang: LanguageCode
) {
    val (bgColor, textColor) = when (reliability) {
        SourceReliability.DOCUMENTED -> SamarkandTealDark to ParchmentIvory
        SourceReliability.DEBATED -> BurntTerracotta to ParchmentIvory
        SourceReliability.GAME_SIMPLIFICATION -> RegistanGold to ManuscriptInk
    }
    Surface(
        color = bgColor,
        contentColor = textColor,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = reliability.label.get(lang),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
