package com.example.model

/**
 * Instant multi-language dictionary (English, Uzbek Latin, Russian) for all UI labels.
 * Combined with Android XML string resources so switching language in Settings takes effect immediately.
 */
object UiStrings {
    fun appTagline(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Fight history. Rewrite it."
        LanguageCode.UZ -> "Tarixda jang qiling. Uni qayta yozing."
        LanguageCode.RU -> "Сражайся в истории. Перепиши её."
    }

    fun navCampaigns(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Campaigns"
        LanguageCode.UZ -> "Yurishlar"
        LanguageCode.RU -> "Кампании"
    }

    fun navWhatIf(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "What If"
        LanguageCode.UZ -> "Agar-chi?"
        LanguageCode.RU -> "Что если"
    }

    fun navSkirmish(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Skirmish"
        LanguageCode.UZ -> "Erkin jang"
        LanguageCode.RU -> "Схватка"
    }

    fun navPassPlay(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Pass & Play"
        LanguageCode.UZ -> "2 O'yinchi"
        LanguageCode.RU -> "2 Игрока"
    }

    fun navChronicle(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Chronicle"
        LanguageCode.UZ -> "Solnoma"
        LanguageCode.RU -> "Летопись"
    }

    fun navSettings(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Settings"
        LanguageCode.UZ -> "Sozlamalar"
        LanguageCode.RU -> "Настройки"
    }

    fun offlineBadge(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "100% Offline • Zero Ads • Historical Sources Included"
        LanguageCode.UZ -> "100% Oflayn • Reklamasiz • Tarixiy manbalar bilan"
        LanguageCode.RU -> "100% Офлайн • Без рекламы • С историческими источниками"
    }

    fun resumeSavedBattle(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Resume Autosaved Battle"
        LanguageCode.UZ -> "Saqlangan jangni davom ettirish"
        LanguageCode.RU -> "Продолжить сохраненный бой"
    }

    fun centuryFilterAll(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "All Eras (11th–15th C.)"
        LanguageCode.UZ -> "Barcha davrlar (XI–XV asr)"
        LanguageCode.RU -> "Все эпохи (XI–XV вв.)"
    }

    fun centuryLabel(century: Int, lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "${century}th Century"
        LanguageCode.UZ -> "$century-asr"
        LanguageCode.RU -> "$century век"
    }

    fun chooseYourSide(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Choose Command Side"
        LanguageCode.UZ -> "Tomonni tanlang"
        LanguageCode.RU -> "Выберите сторону"
    }

    fun historicalBriefing(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Historical Briefing"
        LanguageCode.UZ -> "Tarixiy ma'lumot"
        LanguageCode.RU -> "Историческая сводка"
    }

    fun factVsGame(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Fact vs. Game Adaptation"
        LanguageCode.UZ -> "Tarixiy haqiqat va O'yin"
        LanguageCode.RU -> "Исторический факт и Игра"
    }

    fun historicalSources(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Primary & Secondary Sources"
        LanguageCode.UZ -> "Tarixiy manbalar ro'yxati"
        LanguageCode.RU -> "Исторические источники"
    }

    fun enterBattle(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Take Command on the Hex Map"
        LanguageCode.UZ -> "Jang maydoniga kirish"
        LanguageCode.RU -> "Вступить в сражение"
    }

    fun playWhatIfVariant(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Launch 'What If' Hypothetical Scenario"
        LanguageCode.UZ -> "«Agar-chi?» faraziy jangini boshlash"
        LanguageCode.RU -> "Запустить альтернативный сценарий «Что если»"
    }

    fun hypotheticalBanner(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "HYPOTHETICAL SCENARIO — Counterfactual conditions applied to real historical terrain"
        LanguageCode.UZ -> "FARAZIY SENARIY — Tarixiy xaritada o'zgartirilgan sharoitlar"
        LanguageCode.RU -> "ГИПОТЕТИЧЕСКИЙ СЦЕНАРИЙ — Альтернативные условия на реальной карте"
    }

    fun turnLabel(turn: Int, maxTurns: Int, lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Turn $turn / $maxTurns"
        LanguageCode.UZ -> "Yurish $turn / $maxTurns"
        LanguageCode.RU -> "Ход $turn / $maxTurns"
    }

    fun endTurn(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "End Turn"
        LanguageCode.UZ -> "Yurishni tugatish"
        LanguageCode.RU -> "Завершить ход"
    }

    fun undoMove(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Undo Move"
        LanguageCode.UZ -> "Orqaga"
        LanguageCode.RU -> "Отменить шаг"
    }

    fun confirmAttack(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Confirm Strike"
        LanguageCode.UZ -> "Zarbani tasdiqlash"
        LanguageCode.RU -> "Атаковать"
    }

    fun cancel(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Cancel"
        LanguageCode.UZ -> "Bekor qilish"
        LanguageCode.RU -> "Отмена"
    }

    fun whatReallyHappened(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "What Really Happened in History"
        LanguageCode.UZ -> "Tarixda aslida nima bo'lgan edi?"
        LanguageCode.RU -> "Как это было в реальной истории"
    }

    fun victoryTitle(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Tactical Victory Achieved"
        LanguageCode.UZ -> "Zafar quchildi!"
        LanguageCode.RU -> "Тактическая победа!"
    }

    fun defeatTitle(lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Your Forces Were Routed"
        LanguageCode.UZ -> "Qo'shiningiz chekindi"
        LanguageCode.RU -> "Ваши войска отступили"
    }

    fun reviewedByNote(reviewer: String, lang: LanguageCode) = when (lang) {
        LanguageCode.EN -> "Historical Review Panel: $reviewer (Open JSON format ready for academic contributor updates)"
        LanguageCode.UZ -> "Tarixiy tahririyat: $reviewer (Akademik tuzatishlar uchun ochiq JSON formatida)"
        LanguageCode.RU -> "Историческая редакция: $reviewer (Открытый JSON-формат для академических правок)"
    }
}
