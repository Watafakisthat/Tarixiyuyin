package com.example.ui.campaign

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.BattleAutosaveEntity
import com.example.data.local.MissionProgressEntity
import com.example.model.BattleSide
import com.example.model.CampaignDefinition
import com.example.model.LanguageCode
import com.example.model.MissionDefinition
import com.example.model.UiStrings
import com.example.ui.components.HeroPortraitMedallion
import com.example.ui.components.SamarkandOrnamentalCard
import com.example.ui.components.SourceReliabilityBadge
import com.example.ui.theme.BurntTerracotta
import com.example.ui.theme.CrimsonSeal
import com.example.ui.theme.LapisDeepBlue
import com.example.ui.theme.LapisRoyalBlue
import com.example.ui.theme.ManuscriptInk
import com.example.ui.theme.ParchmentAged
import com.example.ui.theme.ParchmentIvory
import com.example.ui.theme.ParchmentWarm
import com.example.ui.theme.RegistanGold
import com.example.ui.theme.RegistanGoldBright
import com.example.ui.theme.SamarkandTealDark
import com.example.ui.theme.SamarkandTurquoise

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CampaignScreen(
    campaigns: List<CampaignDefinition>,
    selectedCampaignId: String,
    progressMap: Map<String, MissionProgressEntity>,
    autosave: BattleAutosaveEntity?,
    lang: LanguageCode,
    onSelectCampaign: (String) -> Unit,
    onStartMission: (MissionDefinition, BattleSide, Boolean) -> Unit,
    onQuickStartFeaturedBattle: () -> Unit,
    onResumeAutosave: () -> Unit
) {
    var selectedCenturyFilter by remember { mutableIntStateOf(0) } // 0 = All, 11..15
    var briefingMission by remember { mutableStateOf<MissionDefinition?>(null) }

    val filteredCampaigns = if (selectedCenturyFilter == 0) {
        campaigns
    } else {
        campaigns.filter { c ->
            c.century == selectedCenturyFilter ||
                c.missions.any { (it.year / 100) + 1 == selectedCenturyFilter }
        }
    }
    val activeCampaign = filteredCampaigns.firstOrNull { it.id == selectedCampaignId }
        ?: filteredCampaigns.firstOrNull()
        ?: campaigns.first()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ParchmentIvory),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Hero Header Banner + Quick Play & Resume Autosave
        item {
            SamarkandOrnamentalCard(
                containerColor = LapisDeepBlue,
                borderColor = RegistanGold,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CENTURIES",
                            style = MaterialTheme.typography.headlineLarge,
                            color = RegistanGoldBright
                        )
                        Text(
                            text = UiStrings.appTagline(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = ParchmentIvory
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = UiStrings.offlineBadge(lang),
                            style = MaterialTheme.typography.labelMedium,
                            color = SamarkandTurquoise
                        )
                    }
                    HeroPortraitMedallion(heroId = "amir_timur", size = 60.dp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onQuickStartFeaturedBattle,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RegistanGold,
                            contentColor = ManuscriptInk
                        ),
                        modifier = Modifier.testTag("quick_battle_kondurcha_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (lang) {
                                LanguageCode.EN -> "Play Flagship Battle: Kondurcha 1391"
                                LanguageCode.UZ -> "Asosiy jang: Qunduzcha 1391"
                                LanguageCode.RU -> "Ключевой бой: Кондурча 1391"
                            },
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    if (autosave != null) {
                        OutlinedButton(
                            onClick = onResumeAutosave,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = RegistanGoldBright),
                            modifier = Modifier.testTag("resume_autosave_button")
                        ) {
                            Icon(Icons.Default.Restore, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${UiStrings.resumeSavedBattle(lang)} (T${autosave.turnNumber})"
                            )
                        }
                    }
                }
            }
        }

        // 2. Stylized Silk Road World Map with Glowing Battle Markers & Era Timeline Slider
        item {
            SilkRoadWorldMapCard(
                missions = activeCampaign.missions,
                progressMap = progressMap,
                selectedCentury = selectedCenturyFilter,
                lang = lang,
                onSelectCentury = { selectedCenturyFilter = it },
                onTapMissionMarker = { briefingMission = it }
            )
        }

        // 3. Campaign Tabs Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredCampaigns, key = { it.id }) { campaign ->
                    val selected = campaign.id == activeCampaign.id
                    FilterChip(
                        selected = selected,
                        onClick = { onSelectCampaign(campaign.id) },
                        label = {
                            Text(
                                text = campaign.title.get(lang),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LapisDeepBlue,
                            selectedLabelColor = RegistanGoldBright,
                            containerColor = ParchmentWarm,
                            labelColor = ManuscriptInk
                        ),
                        modifier = Modifier.testTag("campaign_tab_${campaign.id}")
                    )
                }
            }
        }

        // 4. Active Campaign Description Banner
        item {
            SamarkandOrnamentalCard(
                containerColor = ParchmentWarm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${activeCampaign.title.get(lang)} (${activeCampaign.eraRange})",
                    style = MaterialTheme.typography.titleLarge,
                    color = LapisDeepBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = activeCampaign.description.get(lang),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // 5. Mission List for Selected Campaign
        items(activeCampaign.missions, key = { it.id }) { mission ->
            val progress = progressMap[mission.id]
            MissionRowCard(
                mission = mission,
                progress = progress,
                lang = lang,
                onOpenBriefing = { briefingMission = mission },
                onDirectStartSideA = { onStartMission(mission, BattleSide.SIDE_A, false) }
            )
        }
    }

    // Mission Briefing, Side Selection & Sources Dialog
    val currentBriefing = briefingMission
    if (currentBriefing != null) {
        MissionBriefingDialog(
            mission = currentBriefing,
            lang = lang,
            onDismiss = { briefingMission = null },
            onLaunchBattle = { side, isWhatIf ->
                briefingMission = null
                onStartMission(currentBriefing, side, isWhatIf)
            }
        )
    }
}

@Composable
private fun SilkRoadWorldMapCard(
    missions: List<MissionDefinition>,
    progressMap: Map<String, MissionProgressEntity>,
    selectedCentury: Int,
    lang: LanguageCode,
    onSelectCentury: (Int) -> Unit,
    onTapMissionMarker: (MissionDefinition) -> Unit
) {
    val labelPaint = remember {
        android.graphics.Paint().apply {
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.SERIF, android.graphics.Typeface.BOLD)
        }
    }

    SamarkandOrnamentalCard(
        containerColor = ParchmentAged,
        borderColor = RegistanGold,
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFDFCFAF), Color(0xFFD2BA91))
                    )
                )
                .border(1.dp, LapisDeepBlue.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("silk_road_world_map")
                    .pointerInput(missions) {
                        detectTapGestures { tap ->
                            val w = size.width.toFloat()
                            val h = size.height.toFloat()
                            val hit = missions.minByOrNull { m ->
                                val mx = m.worldMapX * w
                                val my = m.worldMapY * h
                                (tap.x - mx) * (tap.x - mx) + (tap.y - my) * (tap.y - my)
                            }
                            if (hit != null) {
                                val mx = hit.worldMapX * w
                                val my = hit.worldMapY * h
                                val distSq = (tap.x - mx) * (tap.x - mx) + (tap.y - my) * (tap.y - my)
                                if (distSq <= 48f * 48f) {
                                    onTapMissionMarker(hit)
                                }
                            }
                        }
                    }
            ) {
                val w = size.width
                val h = size.height

                // Cartographic Latitude / Longitude Grid Lines
                val gridColor = Color(0x334A3B28)
                for (i in 1..4) {
                    drawLine(gridColor, Offset(0f, h * i / 5f), Offset(w, h * i / 5f), strokeWidth = 1f)
                    drawLine(gridColor, Offset(w * i / 5f, 0f), Offset(w * i / 5f, h), strokeWidth = 1f)
                }

                // Stylized Seas (Mediterranean, Black Sea, Caspian, Aral)
                val seaColor = Color(0xFF2B6785).copy(alpha = 0.45f)
                drawCircle(seaColor, radius = w * 0.08f, center = Offset(w * 0.20f, h * 0.56f)) // E. Med
                drawOval(
                    color = seaColor,
                    topLeft = Offset(w * 0.39f, h * 0.34f),
                    size = androidx.compose.ui.geometry.Size(w * 0.06f, h * 0.22f)
                ) // Caspian Sea
                drawCircle(seaColor, radius = w * 0.03f, center = Offset(w * 0.53f, h * 0.36f)) // Aral Sea

                // Golden Silk Road Route Line connecting markers
                if (missions.size >= 2) {
                    val routePath = Path().apply {
                        missions.forEachIndexed { idx, m ->
                            val pt = Offset(m.worldMapX * w, m.worldMapY * h)
                            if (idx == 0) moveTo(pt.x, pt.y) else lineTo(pt.x, pt.y)
                        }
                    }
                    drawPath(
                        routePath,
                        color = BurntTerracotta.copy(alpha = 0.75f),
                        style = Stroke(
                            width = 4f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
                        )
                    )
                }

                // Region Cartographic Watermarks
                labelPaint.color = android.graphics.Color.argb(135, 28, 22, 16)
                labelPaint.textSize = 23f
                drawContext.canvas.nativeCanvas.drawText("MOVAROUNNAHR / TRANSOXIANA", w * 0.63f, h * 0.44f, labelPaint)
                drawContext.canvas.nativeCanvas.drawText("DASHT-I KIPCHAK", w * 0.52f, h * 0.20f, labelPaint)
                drawContext.canvas.nativeCanvas.drawText("LEVANT", w * 0.28f, h * 0.68f, labelPaint)
                drawContext.canvas.nativeCanvas.drawText("EUROPE", w * 0.12f, h * 0.24f, labelPaint)

                // Glowing Battle Markers
                missions.forEach { m ->
                    val pt = Offset(m.worldMapX * w, m.worldMapY * h)
                    val completed = (progressMap[m.id]?.bestStars ?: 0) > 0
                    drawCircle(
                        color = if (completed) SamarkandTurquoise.copy(alpha = 0.38f) else RegistanGoldBright.copy(alpha = 0.42f),
                        radius = 22f,
                        center = pt
                    )
                    drawCircle(
                        color = if (completed) SamarkandTealDark else CrimsonSeal,
                        radius = 12f,
                        center = pt
                    )
                    drawCircle(
                        color = RegistanGoldBright,
                        radius = 12f,
                        center = pt,
                        style = Stroke(width = 2.5f)
                    )
                    labelPaint.color = android.graphics.Color.rgb(20, 35, 59)
                    labelPaint.textSize = 21f
                    drawContext.canvas.nativeCanvas.drawText(
                        "${m.orderIndex}. ${m.year}",
                        pt.x,
                        pt.y - 16f,
                        labelPaint
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Era Timeline Slider / Filter Pills (Centuries 11th–15th)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = selectedCentury == 0,
                    onClick = { onSelectCentury(0) },
                    label = { Text(UiStrings.centuryFilterAll(lang)) }
                )
            }
            items(listOf(11, 12, 13, 14, 15)) { century ->
                FilterChip(
                    selected = selectedCentury == century,
                    onClick = { onSelectCentury(century) },
                    label = { Text(UiStrings.centuryLabel(century, lang)) }
                )
            }
        }
    }
}

@Composable
private fun MissionRowCard(
    mission: MissionDefinition,
    progress: MissionProgressEntity?,
    lang: LanguageCode,
    onOpenBriefing: () -> Unit,
    onDirectStartSideA: () -> Unit
) {
    val stars = progress?.bestStars ?: 0

    SamarkandOrnamentalCard(
        containerColor = ParchmentWarm,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenBriefing() }
            .testTag("mission_card_${mission.id}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${mission.title.get(lang)} (${mission.displayDate})",
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )
                Text(
                    text = mission.locationName.get(lang),
                    style = MaterialTheme.typography.labelMedium,
                    color = SamarkandTealDark
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                for (i in 1..3) {
                    Icon(
                        imageVector = if (i <= stars) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = RegistanGold,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = mission.subtitle.get(lang),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = onOpenBriefing,
                modifier = Modifier
                    .weight(1f)
                    .testTag("briefing_button_${mission.id}")
            ) {
                Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(UiStrings.historicalBriefing(lang))
            }

            Button(
                onClick = onDirectStartSideA,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LapisDeepBlue,
                    contentColor = ParchmentIvory
                ),
                modifier = Modifier
                    .weight(1f)
                    .testTag("fight_button_${mission.id}")
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(UiStrings.enterBattle(lang))
            }
        }
    }
}

@Composable
fun MissionBriefingDialog(
    mission: MissionDefinition,
    lang: LanguageCode,
    onDismiss: () -> Unit,
    onLaunchBattle: (BattleSide, Boolean) -> Unit
) {
    var chosenSide by remember { mutableStateOf(BattleSide.SIDE_A) }
    var isWhatIf by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ParchmentIvory,
        title = {
            Column {
                Text(
                    text = mission.title.get(lang),
                    style = MaterialTheme.typography.titleLarge,
                    color = LapisDeepBlue
                )
                Text(
                    text = "${mission.displayDate} • ${mission.locationName.get(lang)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = SamarkandTealDark
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. Choose Command Side (Every battle is playable from either side!)
                Text(
                    text = UiStrings.chooseYourSide(lang),
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilterChip(
                        selected = chosenSide == BattleSide.SIDE_A,
                        onClick = { chosenSide = BattleSide.SIDE_A },
                        label = {
                            Text(
                                text = mission.sideAName.get(lang),
                                maxLines = 1
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LapisDeepBlue,
                            selectedLabelColor = RegistanGoldBright
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("select_side_a_chip")
                    )

                    FilterChip(
                        selected = chosenSide == BattleSide.SIDE_B,
                        onClick = { chosenSide = BattleSide.SIDE_B },
                        label = {
                            Text(
                                text = mission.sideBName.get(lang),
                                maxLines = 1
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CrimsonSeal,
                            selectedLabelColor = ParchmentIvory
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("select_side_b_chip")
                    )
                }

                // 2. Mode Switch: Historical vs What-If Hypothetical
                FilterChip(
                    selected = isWhatIf,
                    onClick = { isWhatIf = !isWhatIf },
                    label = {
                        Text(
                            text = if (isWhatIf) "✓ ${mission.whatIfTitle.get(lang)}" else mission.whatIfTitle.get(lang)
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.HistoryEdu, contentDescription = null, modifier = Modifier.size(18.dp))
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BurntTerracotta,
                        selectedLabelColor = ParchmentIvory
                    ),
                    modifier = Modifier.testTag("toggle_what_if_chip")
                )

                if (isWhatIf) {
                    Surface(
                        color = BurntTerracotta.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = mission.whatIfDescription.get(lang),
                            style = MaterialTheme.typography.bodyMedium,
                            color = CrimsonSeal,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                // 3. Historical Briefing
                SamarkandOrnamentalCard(
                    containerColor = ParchmentWarm,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = UiStrings.historicalBriefing(lang),
                        style = MaterialTheme.typography.titleMedium,
                        color = LapisDeepBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = mission.briefing.get(lang),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // 4. Fact vs. Game Panel
                SamarkandOrnamentalCard(
                    containerColor = ParchmentWarm,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = UiStrings.factVsGame(lang),
                        style = MaterialTheme.typography.titleMedium,
                        color = LapisDeepBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "✓ ${mission.factVsGameAccurate.get(lang)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SamarkandTealDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "⚙ ${mission.factVsGameChanged.get(lang)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // 5. Historical Sources
                Text(
                    text = UiStrings.historicalSources(lang),
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )
                mission.sources.forEach { src ->
                    SamarkandOrnamentalCard(
                        containerColor = ParchmentWarm,
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        Text(
                            text = "${src.title} (${src.authorOrPeriod})",
                            style = MaterialTheme.typography.titleMedium,
                            color = LapisDeepBlue
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        SourceReliabilityBadge(reliability = src.reliability, lang = lang)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = src.note.get(lang),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onLaunchBattle(chosenSide, isWhatIf) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LapisDeepBlue,
                    contentColor = ParchmentIvory
                ),
                modifier = Modifier.testTag("start_mission_battle_button")
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(UiStrings.enterBattle(lang), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(UiStrings.cancel(lang))
            }
        }
    )
}
