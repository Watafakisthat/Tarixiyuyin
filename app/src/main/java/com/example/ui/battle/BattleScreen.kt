package com.example.ui.battle

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.engine.BattleState
import com.example.domain.engine.BattleUnitState
import com.example.domain.engine.CombatPreview
import com.example.model.BattleSide
import com.example.model.HexCoord
import com.example.model.LanguageCode
import com.example.model.LocalizedText
import com.example.model.MissionDefinition
import com.example.model.SpecialAbilityType
import com.example.model.UiStrings
import com.example.ui.components.HeroPortraitMedallion
import com.example.ui.components.SamarkandOrnamentalCard
import com.example.ui.components.SourceReliabilityBadge
import com.example.ui.components.UnitCategoryBadge
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

@Composable
fun BattleScreen(
    battleState: BattleState,
    missionDefinition: MissionDefinition?,
    lang: LanguageCode,
    colorblindSafe: Boolean,
    onHexTapped: (HexCoord) -> Unit,
    onUndoMove: () -> Unit,
    onToggleOrTriggerAbility: () -> Unit,
    onTriggerHeroCommand: () -> Unit,
    onConfirmAttack: () -> Unit,
    onCancelAttackPreview: () -> Unit,
    onEndTurn: () -> Unit,
    onDismissTutorial: () -> Unit,
    onExitBattle: () -> Unit,
    onReplayBattle: () -> Unit
) {
    BackHandler {
        onExitBattle()
    }

    var showMidBattleSourcesDialog by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(LapisDeepBlue)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        val isLandscapeOrExpanded = maxWidth >= 680.dp

        Column(modifier = Modifier.fillMaxSize()) {
            // 1. Top Tactical Command Bar
            BattleTopHeader(
                battleState = battleState,
                lang = lang,
                hasSources = missionDefinition != null,
                onShowSources = { showMidBattleSourcesDialog = true },
                onEndTurn = onEndTurn,
                onExitBattle = onExitBattle
            )

            // 2. Hypothetical "What If" Banner if active
            if (battleState.isWhatIf) {
                Surface(
                    color = BurntTerracotta,
                    contentColor = ParchmentIvory,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = UiStrings.hypotheticalBanner(lang),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            // 3. Guided Interactive Tutorial Banner if active
            AnimatedVisibility(visible = battleState.tutorialStep > 0) {
                TutorialStepBanner(
                    step = battleState.tutorialStep,
                    lang = lang,
                    onDismiss = onDismissTutorial
                )
            }

            // 4. Main Adaptive Body: Map + Inspector/Preview Panel
            if (isLandscapeOrExpanded) {
                Row(modifier = Modifier.weight(1f)) {
                    HexBattleCanvas(
                        battleState = battleState,
                        colorblindSafe = colorblindSafe,
                        onHexTapped = onHexTapped,
                        modifier = Modifier
                            .weight(0.66f)
                            .fillMaxHeight()
                    )
                    BattleCommandDrawer(
                        battleState = battleState,
                        lang = lang,
                        onUndoMove = onUndoMove,
                        onToggleOrTriggerAbility = onToggleOrTriggerAbility,
                        onTriggerHeroCommand = onTriggerHeroCommand,
                        onConfirmAttack = onConfirmAttack,
                        onCancelAttackPreview = onCancelAttackPreview,
                        modifier = Modifier
                            .weight(0.34f)
                            .fillMaxHeight()
                    )
                }
            } else {
                Column(modifier = Modifier.weight(1f)) {
                    HexBattleCanvas(
                        battleState = battleState,
                        colorblindSafe = colorblindSafe,
                        onHexTapped = onHexTapped,
                        modifier = Modifier
                            .weight(0.62f)
                            .fillMaxWidth()
                    )
                    BattleCommandDrawer(
                        battleState = battleState,
                        lang = lang,
                        onUndoMove = onUndoMove,
                        onToggleOrTriggerAbility = onToggleOrTriggerAbility,
                        onTriggerHeroCommand = onTriggerHeroCommand,
                        onConfirmAttack = onConfirmAttack,
                        onCancelAttackPreview = onCancelAttackPreview,
                        modifier = Modifier
                            .weight(0.38f)
                            .fillMaxWidth()
                    )
                }
            }
        }

        // 5. Mid-Battle Historical Sources & Fact-vs-Game Modal
        if (showMidBattleSourcesDialog && missionDefinition != null) {
            HistoricalSourcesModal(
                mission = missionDefinition,
                lang = lang,
                onDismiss = { showMidBattleSourcesDialog = false }
            )
        }

        // 6. Post-Battle Victory / Defeat Historical Debrief Modal
        if (battleState.outcome != null) {
            BattleDebriefDialog(
                battleState = battleState,
                mission = missionDefinition,
                lang = lang,
                onExitToCampaigns = onExitBattle,
                onReplay = onReplayBattle
            )
        }
    }
}

@Composable
private fun BattleTopHeader(
    battleState: BattleState,
    lang: LanguageCode,
    hasSources: Boolean,
    onShowSources: () -> Unit,
    onEndTurn: () -> Unit,
    onExitBattle: () -> Unit
) {
    val activeFactionName = if (battleState.activeSide == BattleSide.SIDE_A) {
        battleState.sideAName.get(lang)
    } else {
        battleState.sideBName.get(lang)
    }
    val objectiveText = if (battleState.playerSide == BattleSide.SIDE_A) {
        battleState.sideAObjectiveDesc.get(lang)
    } else {
        battleState.sideBObjectiveDesc.get(lang)
    }

    Surface(
        color = LapisDeepBlue,
        contentColor = ParchmentIvory,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, RegistanGold.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = onExitBattle,
                        modifier = Modifier.testTag("battle_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Exit Battle",
                            tint = RegistanGoldBright
                        )
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = UiStrings.turnLabel(battleState.turnNumber, battleState.maxTurns, lang),
                                style = MaterialTheme.typography.titleMedium,
                                color = RegistanGoldBright,
                                modifier = Modifier.testTag("turn_counter_text")
                            )
                            Surface(
                                color = SamarkandTealDark,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = battleState.weather.label.get(lang),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = ParchmentIvory,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = activeFactionName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = ParchmentIvory,
                            maxLines = 1
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (hasSources) {
                        IconButton(
                            onClick = onShowSources,
                            modifier = Modifier.testTag("battle_sources_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = "Historical Sources",
                                tint = RegistanGoldBright
                            )
                        }
                    }

                    Button(
                        onClick = onEndTurn,
                        enabled = battleState.isPlayerTurn() && battleState.outcome == null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RegistanGold,
                            contentColor = ManuscriptInk
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("end_turn_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = UiStrings.endTurn(lang),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            // Objective Tracker Line
            Text(
                text = "Objective: $objectiveText",
                style = MaterialTheme.typography.labelMedium,
                color = ParchmentAged,
                maxLines = 1,
                modifier = Modifier.padding(start = 12.dp, bottom = 2.dp)
            )
        }
    }
}

@Composable
private fun TutorialStepBanner(
    step: Int,
    lang: LanguageCode,
    onDismiss: () -> Unit
) {
    val msg = when (step) {
        1 -> LocalizedText(
            "Tutorial (1/4): Tap one of your blue-ringed units (such as Amir Timur), then tap a highlighted turquoise hex to move. Terrain modifies movement and defense!",
            "Qo'llanma (1/4): Ko'k halqali bo'linmangizni (masalan, Amir Temurni) bosing, so'ngra firuza rangli katakka yurish qiling.",
            "Обучение (1/4): Выберите свой отряд (например, Амира Тимура) и нажмите на подсвеченный бирюзовый гекс для перемещения."
        )
        2 -> LocalizedText(
            "Tutorial (2/4): You can tap 'Undo Move' before attacking! Now select a unit within range of an enemy and tap the red-ringed foe to preview combat.",
            "Qo'llanma (2/4): Hujum qilishdan oldin «Orqaga» tugmasi bilan yurishni qaytarish mumkin! Endi qizil halqali dushman ustiga bosib zarbani ko'ring.",
            "Обучение (2/4): До атаки ход можно отменить кнопкой «Отменить шаг». Нажмите на врага в красном кольце для прогноза боя."
        )
        3 -> LocalizedText(
            "Tutorial (3/4): Inspect the Combat Preview (Flanking, High Ground, Morale drop). Tap 'Confirm Strike' to execute your attack!",
            "Qo'llanma (3/4): Zarba natijasini ko'rib chiqing (Qanot zarbasi, Balandlik, Ruhiyat). Hujum qilish uchun «Zarbani tasdiqlash»ni bosing!",
            "Обучение (3/4): Изучите прогноз урона (фланг, высота, боевой дух) и нажмите «Атаковать»!"
        )
        else -> LocalizedText(
            "Tutorial (4/4): Heroes boost nearby allies' Morale & Attack and have an active Command Ability! Press 'End Turn' when your units finish acting.",
            "Qo'llanma (4/4): Sarkardalar atrofdagi qo'shin ruhiyatini ko'taradi va maxsus «Sarkarda amri»ga ega! Yurish tugagach «Yurishni tugatish»ni bosing.",
            "Обучение (4/4): Полководцы укрепляют дух соседних войск и имеют активный приказ! В конце хода нажмите «Завершить ход»."
        )
    }

    Surface(
        color = SamarkandTealDark,
        contentColor = ParchmentIvory,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = msg.get(lang),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("dismiss_tutorial_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss Tutorial",
                    tint = RegistanGoldBright
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BattleCommandDrawer(
    battleState: BattleState,
    lang: LanguageCode,
    onUndoMove: () -> Unit,
    onToggleOrTriggerAbility: () -> Unit,
    onTriggerHeroCommand: () -> Unit,
    onConfirmAttack: () -> Unit,
    onCancelAttackPreview: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedUnit = battleState.unitById(battleState.selectedUnitId)
    val preview = battleState.pendingCombatPreview

    Surface(
        color = ParchmentWarm,
        contentColor = ManuscriptInk,
        modifier = modifier
            .border(1.5.dp, RegistanGold)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 1. If a Combat Preview is pending, show it prominently at the top
            if (preview != null) {
                CombatPreviewPanel(
                    battleState = battleState,
                    preview = preview,
                    lang = lang,
                    onConfirm = onConfirmAttack,
                    onCancel = onCancelAttackPreview
                )
            }

            // 2. Selected Unit Inspector & Tactical Actions
            if (selectedUnit != null) {
                SelectedUnitInspector(
                    battleState = battleState,
                    unit = selectedUnit,
                    lang = lang,
                    onUndoMove = onUndoMove,
                    onToggleOrTriggerAbility = onToggleOrTriggerAbility,
                    onTriggerHeroCommand = onTriggerHeroCommand
                )
            } else {
                // Empty Selection Helper: Show Hero Summary & Terrain/Battle Log
                SamarkandOrnamentalCard(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = when (lang) {
                            LanguageCode.EN -> "Tap any unit on the hex map to inspect stats, movement range, and special abilities."
                            LanguageCode.UZ -> "Bo'linma xususiyatlari, yurish masofasi va maxsus usullarini ko'rish uchun xaritadagi jangchini bosing."
                            LanguageCode.RU -> "Нажмите на любой отряд на карте, чтобы увидеть характеристики, радиус хода и навыки."
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (battleState.battleLog.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = battleState.battleLog.last(),
                            style = MaterialTheme.typography.labelMedium,
                            color = SamarkandTealDark
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CombatPreviewPanel(
    battleState: BattleState,
    preview: CombatPreview,
    lang: LanguageCode,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    val defender = battleState.unitById(preview.defenderId)
    val defenderName = defender?.customName ?: defender?.archetype?.name?.get(lang) ?: "Target"

    SamarkandOrnamentalCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("combat_preview_card"),
        containerColor = Color(0xFFFFF4E0),
        borderColor = CrimsonSeal,
        contentPadding = PaddingValues(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Strike Preview vs. $defenderName",
                style = MaterialTheme.typography.titleMedium,
                color = CrimsonSeal
            )
            if (preview.defenderWillBeDefeated) {
                Surface(color = CrimsonSeal, shape = RoundedCornerShape(6.dp)) {
                    Text(
                        text = "LETHAL BLOW",
                        style = MaterialTheme.typography.labelMedium,
                        color = ParchmentIvory,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            } else if (preview.defenderWillRout) {
                Surface(color = BurntTerracotta, shape = RoundedCornerShape(6.dp)) {
                    Text(
                        text = "CAUSES ROUT",
                        style = MaterialTheme.typography.labelMedium,
                        color = ParchmentIvory,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "DMG: -${preview.expectedDamageToDefender} HP",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = CrimsonSeal
            )
            Text(
                text = "Counter: -${preview.expectedCounterDamageToAttacker} HP",
                style = MaterialTheme.typography.bodyMedium,
                color = ManuscriptInk
            )
            Text(
                text = "Morale: -${preview.expectedMoraleDropDefender}",
                style = MaterialTheme.typography.bodyMedium,
                color = SamarkandTealDark
            )
        }

        if (preview.modifierTags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                preview.modifierTags.forEach { tag ->
                    Surface(
                        color = LapisDeepBlue,
                        contentColor = RegistanGoldBright,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CrimsonSeal,
                    contentColor = ParchmentIvory
                ),
                modifier = Modifier
                    .weight(1f)
                    .testTag("confirm_attack_button")
            ) {
                Icon(Icons.Default.Gavel, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(UiStrings.confirmAttack(lang))
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.testTag("cancel_attack_button")
            ) {
                Text(UiStrings.cancel(lang))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectedUnitInspector(
    battleState: BattleState,
    unit: BattleUnitState,
    lang: LanguageCode,
    onUndoMove: () -> Unit,
    onToggleOrTriggerAbility: () -> Unit,
    onTriggerHeroCommand: () -> Unit
) {
    val tile = battleState.tileAt(unit.coord)
    val heroDef = unit.heroDefinition
    val isFriendlyTurn = unit.side == battleState.activeSide && battleState.isPlayerTurn()

    SamarkandOrnamentalCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("selected_unit_card"),
        contentPadding = PaddingValues(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (unit.isHero && unit.heroId != null) {
                HeroPortraitMedallion(heroId = unit.heroId, size = 48.dp)
            } else {
                UnitCategoryBadge(category = unit.archetype.category, size = 44.dp)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = unit.customName ?: unit.archetype.name.get(lang),
                    style = MaterialTheme.typography.titleMedium,
                    color = LapisDeepBlue
                )
                Text(
                    text = "${unit.factionId.displayName.get(lang)} • ${unit.archetype.category.label.get(lang)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = SamarkandTealDark
                )
            }

            Surface(
                color = LapisDeepBlue,
                contentColor = RegistanGoldBright,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "HP ${unit.currentHp}/${unit.maxHp} • Morale ${unit.currentMorale}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Stat & Terrain Row
        Text(
            text = "ATK ${unit.archetype.attack} | DEF ${unit.archetype.defense} | Move ${unit.archetype.movement} | Range ${unit.archetype.range}" +
                (tile?.let { " | Terrain: ${it.terrain.label.get(lang)} (${if (it.effectiveDefenseBonus >= 0) "+${it.effectiveDefenseBonus}%" else "${it.effectiveDefenseBonus}%"} DEF)" } ?: ""),
            style = MaterialTheme.typography.bodyMedium
        )

        if (heroDef != null) {
            Text(
                text = "Aura: ${heroDef.passiveAuraName.get(lang)} — ${heroDef.passiveAuraDesc.get(lang)}",
                style = MaterialTheme.typography.labelMedium,
                color = SamarkandTealDark
            )
        }

        // Action Buttons Row for Player's Active Units
        if (isFriendlyTurn) {
            Spacer(modifier = Modifier.height(6.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (unit.canUndoMove) {
                    FilledTonalButton(
                        onClick = onUndoMove,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = ParchmentAged,
                            contentColor = ManuscriptInk
                        ),
                        modifier = Modifier.testTag("undo_move_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Undo, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(UiStrings.undoMove(lang))
                    }
                }

                // Unit Special Ability Button
                val ability = unit.archetype.specialAbility
                val isSelfSupport = ability == SpecialAbilityType.SHIELD_WALL || ability == SpecialAbilityType.RALLY_TROOPS
                val abilityEnabled = !unit.hasAttackedThisTurn && unit.abilityCooldown == 0 && !unit.isRouted
                FilledTonalButton(
                    onClick = onToggleOrTriggerAbility,
                    enabled = abilityEnabled,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (battleState.useSpecialAbilityOnConfirm) BurntTerracotta else LapisRoyalBlue,
                        contentColor = ParchmentIvory
                    ),
                    modifier = Modifier.testTag("special_ability_button")
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    val cdSuffix = if (unit.abilityCooldown > 0) " (${unit.abilityCooldown}t)" else ""
                    val activePrefix = if (!isSelfSupport && battleState.useSpecialAbilityOnConfirm) "✓ " else ""
                    Text("$activePrefix${ability.title.get(lang)}$cdSuffix")
                }

                // Hero Active Command Ability Button
                if (unit.isHero && heroDef != null) {
                    Button(
                        onClick = onTriggerHeroCommand,
                        enabled = unit.heroCooldown == 0 && !unit.isRouted,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RegistanGold,
                            contentColor = ManuscriptInk
                        ),
                        modifier = Modifier.testTag("hero_ability_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        val cdText = if (unit.heroCooldown > 0) " (${unit.heroCooldown}t)" else ""
                        Text("${heroDef.activeAbilityName.get(lang)}$cdText")
                    }
                }
            }
        }
    }
}

@Composable
fun HistoricalSourcesModal(
    mission: MissionDefinition,
    lang: LanguageCode,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ParchmentIvory,
        titleContentColor = LapisDeepBlue,
        textContentColor = ManuscriptInk,
        title = {
            Text(
                text = "${mission.title.get(lang)} — ${UiStrings.historicalSources(lang)}",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = ManuscriptInk
                    )
                }

                mission.sources.forEach { src ->
                    SamarkandOrnamentalCard(
                        containerColor = ParchmentWarm,
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        Text(
                            text = src.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = LapisDeepBlue
                        )
                        Text(
                            text = src.authorOrPeriod,
                            style = MaterialTheme.typography.labelMedium,
                            color = SamarkandTealDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        SourceReliabilityBadge(reliability = src.reliability, lang = lang)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = src.note.get(lang),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Text(
                    text = UiStrings.reviewedByNote(mission.reviewedBy, lang),
                    style = MaterialTheme.typography.labelMedium,
                    color = SamarkandTealDark
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = LapisDeepBlue, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
private fun BattleDebriefDialog(
    battleState: BattleState,
    mission: MissionDefinition?,
    lang: LanguageCode,
    onExitToCampaigns: () -> Unit,
    onReplay: () -> Unit
) {
    val outcome = battleState.outcome ?: return

    AlertDialog(
        onDismissRequest = onExitToCampaigns,
        containerColor = ParchmentIvory,
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (outcome.playerWon) UiStrings.victoryTitle(lang) else UiStrings.defeatTitle(lang),
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (outcome.playerWon) LapisDeepBlue else CrimsonSeal
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    for (i in 1..3) {
                        Icon(
                            imageVector = if (i <= outcome.starsEarned) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = RegistanGold,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = outcome.reason.get(lang),
                    style = MaterialTheme.typography.titleMedium,
                    color = SamarkandTealDark
                )

                if (mission != null) {
                    SamarkandOrnamentalCard(
                        containerColor = ParchmentWarm,
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        Text(
                            text = UiStrings.whatReallyHappened(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = LapisDeepBlue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = mission.whatReallyHappened.get(lang),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    SamarkandOrnamentalCard(
                        containerColor = ParchmentWarm,
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        Text(
                            text = UiStrings.factVsGame(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = LapisDeepBlue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = mission.factVsGameAccurate.get(lang),
                            style = MaterialTheme.typography.bodyMedium,
                            color = SamarkandTealDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = mission.factVsGameChanged.get(lang),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onExitToCampaigns,
                colors = ButtonDefaults.buttonColors(containerColor = LapisDeepBlue, contentColor = ParchmentIvory),
                modifier = Modifier.testTag("debrief_continue_button")
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Continue")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onReplay,
                modifier = Modifier.testTag("debrief_replay_button")
            ) {
                Text("Replay Battle")
            }
        }
    )
}
