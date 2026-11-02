package com.example.ui.modes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.UserSettingsEntity
import com.example.data.pack.LoadedContentBundle
import com.example.model.AiDifficulty
import com.example.model.BattleSide
import com.example.model.CampaignDefinition
import com.example.model.FactionId
import com.example.model.LanguageCode
import com.example.model.MissionDefinition
import com.example.model.UiStrings
import com.example.model.WeatherType
import com.example.ui.components.HeroPortraitMedallion
import com.example.ui.components.SamarkandOrnamentalCard
import com.example.ui.components.SourceReliabilityBadge
import com.example.ui.components.UnitCategoryBadge
import com.example.ui.theme.BurntTerracotta
import com.example.ui.theme.CrimsonSeal
import com.example.ui.theme.LapisDeepBlue
import com.example.ui.theme.ManuscriptInk
import com.example.ui.theme.ParchmentIvory
import com.example.ui.theme.ParchmentWarm
import com.example.ui.theme.RegistanGold
import com.example.ui.theme.RegistanGoldBright
import com.example.ui.theme.SamarkandTealDark

@Composable
fun WhatIfModeScreen(
    campaigns: List<CampaignDefinition>,
    lang: LanguageCode,
    onLaunchWhatIfMission: (MissionDefinition, BattleSide) -> Unit
) {
    val allMissions = remember(campaigns) { campaigns.flatMap { it.missions } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ParchmentIvory),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SamarkandOrnamentalCard(
                containerColor = BurntTerracotta,
                borderColor = RegistanGoldBright,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = UiStrings.navWhatIf(lang),
                    style = MaterialTheme.typography.headlineMedium,
                    color = RegistanGoldBright
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = UiStrings.hypotheticalBanner(lang),
                    style = MaterialTheme.typography.bodyMedium,
                    color = ParchmentIvory
                )
            }
        }

        items(allMissions, key = { "whatif_${it.id}" }) { mission ->
            SamarkandOrnamentalCard(
                containerColor = ParchmentWarm,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("whatif_card_${mission.id}")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mission.whatIfTitle.get(lang),
                        style = MaterialTheme.typography.titleMedium,
                        color = CrimsonSeal,
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        color = BurntTerracotta,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "WHAT IF • ${mission.year}",
                            style = MaterialTheme.typography.labelMedium,
                            color = ParchmentIvory,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${mission.title.get(lang)} — ${mission.locationName.get(lang)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = SamarkandTealDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = mission.whatIfDescription.get(lang),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onLaunchWhatIfMission(mission, BattleSide.SIDE_A) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LapisDeepBlue,
                            contentColor = ParchmentIvory
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("whatif_side_a_${mission.id}")
                    ) {
                        Icon(Icons.Default.HistoryEdu, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(mission.sideAName.get(lang), maxLines = 1)
                    }

                    OutlinedButton(
                        onClick = { onLaunchWhatIfMission(mission, BattleSide.SIDE_B) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("whatif_side_b_${mission.id}")
                    ) {
                        Text(mission.sideBName.get(lang), maxLines = 1)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkirmishAndPassPlayScreen(
    initialPassAndPlay: Boolean,
    defaultDifficulty: AiDifficulty,
    lang: LanguageCode,
    onLaunchCustomBattle: (
        sideAFaction: FactionId,
        sideBFaction: FactionId,
        weather: WeatherType,
        difficulty: AiDifficulty,
        isPassAndPlay: Boolean,
        seed: Long
    ) -> Unit
) {
    var isPassAndPlay by remember(initialPassAndPlay) { mutableStateOf(initialPassAndPlay) }
    var sideAFaction by remember { mutableStateOf(FactionId.TIMURID) }
    var sideBFaction by remember { mutableStateOf(FactionId.GOLDEN_HORDE) }
    var selectedWeather by remember { mutableStateOf(WeatherType.CLEAR) }
    var selectedDifficulty by remember { mutableStateOf(defaultDifficulty) }
    var mapSeed by remember { mutableLongStateOf(1391L) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ParchmentIvory),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SamarkandOrnamentalCard(
                containerColor = LapisDeepBlue,
                borderColor = RegistanGold,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isPassAndPlay) UiStrings.navPassPlay(lang) else UiStrings.navSkirmish(lang),
                    style = MaterialTheme.typography.headlineMedium,
                    color = RegistanGoldBright
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (lang) {
                        LanguageCode.EN -> "Configure any historical match-up on a procedural hex battlefield—against the Tactical AI or 2-Player Offline Pass-and-Play on one device."
                        LanguageCode.UZ -> "Istalgan ikki tarixiy qo'shinni tanlang — Sun'iy intellektga qarshi yoki bitta telefonda ikki kishi navbat bilan o'ynang."
                        LanguageCode.RU -> "Выберите любые две державы — против тактического ИИ или вдвоем на одном устройстве (Pass & Play)."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = ParchmentIvory
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = !isPassAndPlay,
                        onClick = { isPassAndPlay = false },
                        label = { Text("Singleplayer vs AI") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RegistanGold,
                            selectedLabelColor = ManuscriptInk,
                            labelColor = ParchmentIvory
                        ),
                        modifier = Modifier.testTag("mode_skirmish_ai_chip")
                    )
                    FilterChip(
                        selected = isPassAndPlay,
                        onClick = { isPassAndPlay = true },
                        label = { Text("2-Player Pass & Play") },
                        leadingIcon = { Icon(Icons.Default.Group, contentDescription = null, modifier = Modifier.size(16.dp)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RegistanGold,
                            selectedLabelColor = ManuscriptInk,
                            labelColor = ParchmentIvory
                        ),
                        modifier = Modifier.testTag("mode_pass_play_chip")
                    )
                }
            }
        }

        // Side A Faction Picker
        item {
            SamarkandOrnamentalCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Side A Faction: ${sideAFaction.displayName.get(lang)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )
                Text(
                    text = "${sideAFaction.uniqueDoctrineName.get(lang)} — ${sideAFaction.uniqueDoctrineDesc.get(lang)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = SamarkandTealDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FactionId.entries.forEach { faction ->
                        FilterChip(
                            selected = sideAFaction == faction,
                            onClick = { sideAFaction = faction },
                            label = { Text(faction.displayName.get(lang)) }
                        )
                    }
                }
            }
        }

        // Side B Faction Picker
        item {
            SamarkandOrnamentalCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Side B Faction: ${sideBFaction.displayName.get(lang)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = CrimsonSeal
                )
                Text(
                    text = "${sideBFaction.uniqueDoctrineName.get(lang)} — ${sideBFaction.uniqueDoctrineDesc.get(lang)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = SamarkandTealDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FactionId.entries.forEach { faction ->
                        FilterChip(
                            selected = sideBFaction == faction,
                            onClick = { sideBFaction = faction },
                            label = { Text(faction.displayName.get(lang)) }
                        )
                    }
                }
            }
        }

        // Weather, Difficulty & Random Map Seed
        item {
            SamarkandOrnamentalCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Weather & Battlefield Conditions",
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    WeatherType.entries.forEach { w ->
                        FilterChip(
                            selected = selectedWeather == w,
                            onClick = { selectedWeather = w },
                            label = { Text(w.label.get(lang)) }
                        )
                    }
                }

                if (!isPassAndPlay) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "AI Commander Difficulty",
                        style = MaterialTheme.typography.titleMedium,
                        color = LapisDeepBlue
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AiDifficulty.entries.forEach { diff ->
                            FilterChip(
                                selected = selectedDifficulty == diff,
                                onClick = { selectedDifficulty = diff },
                                label = { Text(diff.label.get(lang)) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = { mapSeed = (1000L..99999L).random() },
                        modifier = Modifier.testTag("randomize_seed_button")
                    ) {
                        Icon(Icons.Default.Casino, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Random Map (#$mapSeed)")
                    }

                    Button(
                        onClick = {
                            onLaunchCustomBattle(
                                sideAFaction,
                                sideBFaction,
                                selectedWeather,
                                selectedDifficulty,
                                isPassAndPlay,
                                mapSeed
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LapisDeepBlue,
                            contentColor = ParchmentIvory
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("launch_skirmish_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(UiStrings.enterBattle(lang))
                    }
                }
            }
        }
    }
}

@Composable
fun ChronicleEncyclopediaScreen(
    bundle: LoadedContentBundle,
    lang: LanguageCode
) {
    var activeTab by remember { mutableIntStateOf(0) } // 0 = Heroes, 1 = Units, 2 = Cities & Timeline, 3 = Sources & Review

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ParchmentIvory)
    ) {
        // Sub-navigation tabs inside Chronicle
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            val tabLabels = listOf(
                when (lang) {
                    LanguageCode.EN -> "Heroes (12)"
                    LanguageCode.UZ -> "Sarkardalar (12)"
                    LanguageCode.RU -> "Полководцы (12)"
                },
                when (lang) {
                    LanguageCode.EN -> "Units (24)"
                    LanguageCode.UZ -> "Qo'shinlar (24)"
                    LanguageCode.RU -> "Войска (24)"
                },
                when (lang) {
                    LanguageCode.EN -> "Cities & Timeline"
                    LanguageCode.UZ -> "Shaharlar va Solnoma"
                    LanguageCode.RU -> "Города и Хроника"
                },
                when (lang) {
                    LanguageCode.EN -> "Sources & Review"
                    LanguageCode.UZ -> "Manbalar"
                    LanguageCode.RU -> "Источники"
                }
            )
            tabLabels.forEachIndexed { idx, title ->
                FilterChip(
                    selected = activeTab == idx,
                    onClick = { activeTab = idx },
                    label = { Text(title) },
                    modifier = Modifier.testTag("chronicle_subtab_$idx")
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            when (activeTab) {
                0 -> {
                    items(bundle.heroes, key = { it.id }) { hero ->
                        SamarkandOrnamentalCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("chronicle_hero_${hero.id}")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                HeroPortraitMedallion(heroId = hero.id, size = 60.dp)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${hero.name.get(lang)} (${hero.lifespan})",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = LapisDeepBlue
                                    )
                                    Text(
                                        text = "${hero.title.get(lang)} • ${hero.factionId.displayName.get(lang)}",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = SamarkandTealDark
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = hero.biography.get(lang),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Passive Aura: ${hero.passiveAuraName.get(lang)} — ${hero.passiveAuraDesc.get(lang)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = LapisDeepBlue
                            )
                            Text(
                                text = "Active Command (${hero.activeCooldownTurns}t CD): ${hero.activeAbilityName.get(lang)} — ${hero.activeAbilityDesc.get(lang)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = CrimsonSeal
                            )
                        }
                    }
                }
                1 -> {
                    items(bundle.units, key = { it.id }) { unit ->
                        SamarkandOrnamentalCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("chronicle_unit_${unit.id}")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                UnitCategoryBadge(category = unit.category, size = 48.dp)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = unit.name.get(lang),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = LapisDeepBlue
                                    )
                                    Text(
                                        text = "${unit.era.label.get(lang)} • ${unit.category.label.get(lang)}",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = SamarkandTealDark
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "HP ${unit.maxHp} | ATK ${unit.attack} | DEF ${unit.defense} | Move ${unit.movement} | Range ${unit.range}",
                                style = MaterialTheme.typography.labelLarge,
                                color = CrimsonSeal
                            )
                            Text(
                                text = "Ability: ${unit.specialAbility.title.get(lang)} — ${unit.specialAbility.description.get(lang)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = LapisDeepBlue
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = unit.historicalNote.get(lang),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                2 -> {
                    item {
                        Text(
                            text = "Silk Road & World Cities",
                            style = MaterialTheme.typography.headlineMedium,
                            color = LapisDeepBlue
                        )
                    }
                    items(bundle.cities, key = { it.id }) { city ->
                        SamarkandOrnamentalCard(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${city.name.get(lang)} — ${city.region.get(lang)}",
                                style = MaterialTheme.typography.titleMedium,
                                color = LapisDeepBlue
                            )
                            Text(
                                text = city.goldenAgeEra,
                                style = MaterialTheme.typography.labelMedium,
                                color = SamarkandTealDark
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = city.summary.get(lang),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Monuments & Legacy: ${city.monumentsAndLegacy.get(lang)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = CrimsonSeal
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Chronological Timeline (1066–1420 CE)",
                            style = MaterialTheme.typography.headlineMedium,
                            color = LapisDeepBlue
                        )
                    }
                    items(bundle.timeline, key = { "${it.year}_${it.title.en}" }) { ev ->
                        SamarkandOrnamentalCard(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${ev.year} CE — ${ev.title.get(lang)} (${ev.region.get(lang)})",
                                style = MaterialTheme.typography.titleMedium,
                                color = LapisDeepBlue
                            )
                            Text(
                                text = ev.summary.get(lang),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                else -> {
                    item {
                        SamarkandOrnamentalCard(
                            containerColor = LapisDeepBlue,
                            borderColor = RegistanGold,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Verified, contentDescription = null, tint = RegistanGoldBright)
                                Text(
                                    text = "Historian Review & Attribution Panel",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = RegistanGoldBright
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            bundle.reviewerCredits.forEach { (region, note) ->
                                Text(
                                    text = "• $region: $note",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ParchmentIvory
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            Text(
                                text = "All mission briefings and historical notes are stored in editable JSON packs inside assets/packs/ so academic reviewers can refine translations and citations without modifying source code.",
                                style = MaterialTheme.typography.labelMedium,
                                color = RegistanGoldBright
                            )
                        }
                    }

                    val allMissions = bundle.campaigns.flatMap { it.missions }
                    items(allMissions, key = { "src_${it.id}" }) { m ->
                        SamarkandOrnamentalCard(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${m.title.get(lang)} (${m.displayDate})",
                                style = MaterialTheme.typography.titleMedium,
                                color = LapisDeepBlue
                            )
                            m.sources.forEach { src ->
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${src.title} — ${src.authorOrPeriod}",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                SourceReliabilityBadge(reliability = src.reliability, lang = lang)
                                Text(
                                    text = src.note.get(lang),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsDialog(
    settings: UserSettingsEntity,
    lang: LanguageCode,
    onUpdateLanguage: (LanguageCode) -> Unit,
    onUpdateDifficulty: (AiDifficulty) -> Unit,
    onUpdateMusicVolume: (Float) -> Unit,
    onUpdateSfxVolume: (Float) -> Unit,
    onUpdateColorblind: (Boolean) -> Unit,
    onUpdateTextScale: (Float) -> Unit,
    onUpdateHaptics: (Boolean) -> Unit,
    onUpdateTts: (Boolean) -> Unit,
    onResetProgress: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ParchmentIvory,
        title = {
            Text(
                text = UiStrings.navSettings(lang),
                style = MaterialTheme.typography.titleLarge,
                color = LapisDeepBlue
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. Language Switch (English, Uzbek Latin, Russian)
                Text(
                    text = "Language / Til / Язык",
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    LanguageCode.entries.forEach { code ->
                        FilterChip(
                            selected = lang == code,
                            onClick = { onUpdateLanguage(code) },
                            label = { Text(code.displayName) },
                            modifier = Modifier.testTag("lang_chip_${code.code}")
                        )
                    }
                }

                // 2. AI Difficulty
                Text(
                    text = "Default AI Difficulty",
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    AiDifficulty.entries.forEach { diff ->
                        FilterChip(
                            selected = settings.aiDifficulty == diff.name,
                            onClick = { onUpdateDifficulty(diff) },
                            label = { Text(diff.label.get(lang)) }
                        )
                    }
                }

                // 3. Volume Sliders
                Text(
                    text = "Music Volume (${(settings.musicVolume * 100).toInt()}%)",
                    style = MaterialTheme.typography.labelLarge
                )
                Slider(
                    value = settings.musicVolume,
                    onValueChange = onUpdateMusicVolume
                )

                Text(
                    text = "Sound Effects Volume (${(settings.sfxVolume * 100).toInt()}%)",
                    style = MaterialTheme.typography.labelLarge
                )
                Slider(
                    value = settings.sfxVolume,
                    onValueChange = onUpdateSfxVolume
                )

                // 4. Accessibility
                Text(
                    text = "Text Size Scale (${(settings.textSizeScale * 100).toInt()}%)",
                    style = MaterialTheme.typography.labelLarge
                )
                Slider(
                    value = settings.textSizeScale,
                    onValueChange = onUpdateTextScale,
                    valueRange = 0.9f..1.25f
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Colorblind-Safe Team Colors", style = MaterialTheme.typography.bodyMedium)
                    Switch(
                        checked = settings.colorblindSafeMode,
                        onCheckedChange = onUpdateColorblind,
                        modifier = Modifier.testTag("colorblind_switch")
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Battle Haptic Feedback", style = MaterialTheme.typography.bodyMedium)
                    Switch(
                        checked = settings.hapticsEnabled,
                        onCheckedChange = onUpdateHaptics
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Offline Narrated Briefing (TTS)", style = MaterialTheme.typography.bodyMedium)
                    Switch(
                        checked = settings.narratedBriefingTts,
                        onCheckedChange = onUpdateTts
                    )
                }

                OutlinedButton(
                    onClick = onResetProgress,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimsonSeal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("reset_progress_button")
                ) {
                    Icon(Icons.Default.DeleteForever, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Reset Campaign Progress & Autosave")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("close_settings_button")
            ) {
                Text("Done", color = LapisDeepBlue, fontWeight = FontWeight.Bold)
            }
        }
    )
}
