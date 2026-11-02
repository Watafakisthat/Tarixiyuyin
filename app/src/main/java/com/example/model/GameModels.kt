package com.example.model

import kotlin.math.abs
import kotlin.math.max

enum class LanguageCode(val code: String, val displayName: String) {
    EN("en", "English"),
    UZ("uz", "O'zbekcha"),
    RU("ru", "Русский");

    companion object {
        fun fromCode(code: String): LanguageCode =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: EN
    }
}

data class LocalizedText(
    val en: String,
    val uz: String = en,
    val ru: String = en
) {
    fun get(lang: LanguageCode): String = when (lang) {
        LanguageCode.EN -> en
        LanguageCode.UZ -> uz.ifBlank { en }
        LanguageCode.RU -> ru.ifBlank { en }
    }
}

enum class Era(val label: LocalizedText) {
    STEPPE(LocalizedText("Steppe Nomads", "Dasht ko'chmanchilari", "Степные кочевники")),
    CENTRAL_ASIAN(LocalizedText("Central Asia & Transoxiana", "Markaziy Osiyo va Movarounnahr", "Центральная Азия и Мавераннахр")),
    MIDDLE_EAST(LocalizedText("Middle East & Levant", "Yaqin Sharq va Shom", "Ближний Восток и Левант")),
    EUROPE(LocalizedText("Medieval Europe", "O'rta asr Yevropasi", "Средневековая Европа")),
    COMMON(LocalizedText("Universal Arms", "Umumiy qo'shinlar", "Общие рода войск"))
}

enum class FactionId(
    val era: Era,
    val displayName: LocalizedText,
    val bannerPrimaryHex: Long,
    val bannerSecondaryHex: Long,
    val uniqueDoctrineName: LocalizedText,
    val uniqueDoctrineDesc: LocalizedText
) {
    TIMURID(
        era = Era.CENTRAL_ASIAN,
        displayName = LocalizedText("Timurid Amirate (Turan)", "Temuriylar davlati (Turon)", "Держава Тимуридов (Туран)"),
        bannerPrimaryHex = 0xFF0F3B5F,
        bannerSecondaryHex = 0xFFD4AF37,
        uniqueDoctrineName = LocalizedText("Qol-Barangar-Juvangar Encirclement", "Qo'l-Barangar-Juvangar qurshovi", "Охват Кол-Барангар-Джувангар"),
        uniqueDoctrineDesc = LocalizedText("+15% flanking bonus and +1 morale recovery near commanders.", "Yonboshdan hujumda +15% kuch va sarkarda yonida +1 ruhiyat.", "+15% к фланговому удару и +1 к боевому духу рядом с полководцем.")
    ),
    GOLDEN_HORDE(
        era = Era.STEPPE,
        displayName = LocalizedText("Golden Horde (Ulus of Jochi)", "Oltin O'rda (Jo'ji ulusi)", "Золотая Орда (Улус Джучи)"),
        bannerPrimaryHex = 0xFF8C6514,
        bannerSecondaryHex = 0xFFF5D061,
        uniqueDoctrineName = LocalizedText("Steppe Outrider Screen", "Dasht ilg'or qanoti", "Степной заслон"),
        uniqueDoctrineDesc = LocalizedText("Horse archers and light cavalry ignore steppe movement penalties.", "Kamondor otliqlar dashtda harakat jarimasini olmaydi.", "Конные лучники и легкая конница не теряют ход в степи.")
    ),
    MONGOL(
        era = Era.STEPPE,
        displayName = LocalizedText("Mongol Tumen", "Mo'g'ul tumanlari", "Монгольские тумены"),
        bannerPrimaryHex = 0xFF5A2A18,
        bannerSecondaryHex = 0xFFE3B23C,
        uniqueDoctrineName = LocalizedText("Yassa Coordinated Volley", "Yasoq yoppasiga o'q uzish", "Слаженный залп Ясы"),
        uniqueDoctrineDesc = LocalizedText("Ranged and horse archer units deal +10% morale shock on hit.", "Kamondorlar zarbasi dushman ruhiyatini 10% ko'proq tushiradi.", "Лучники и конные стрелки снижают боевой дух врага на +10% сильнее.")
    ),
    KHWARAZMIAN(
        era = Era.CENTRAL_ASIAN,
        displayName = LocalizedText("Khwarazmian Empire", "Xorazmshohlar davlati", "Государство Хорезмшахов"),
        bannerPrimaryHex = 0xFF136F63,
        bannerSecondaryHex = 0xFFE0B145,
        uniqueDoctrineName = LocalizedText("Oxus Citadel Bastion", "Jayhun qal'a mudofaasi", "Оплот цитаделей Амударьи"),
        uniqueDoctrineDesc = LocalizedText("+20% defense on rivers, city walls, and fortresses.", "Daryo bo'yi, shahar devori va qal'alarda +20% himoya.", "+20% к защите у рек, городских стен и крепостей.")
    ),
    AYYUBID(
        era = Era.MIDDLE_EAST,
        displayName = LocalizedText("Ayyubid Sultanate", "Ayyubiylar sultonligi", "Султанат Айюбидов"),
        bannerPrimaryHex = 0xFF9E6B18,
        bannerSecondaryHex = 0xFF1F3A52,
        uniqueDoctrineName = LocalizedText("Desert Mirage Logistics", "Sahro ta'minot ustunligi", "Пустынная логистика"),
        uniqueDoctrineDesc = LocalizedText("No desert movement penalty and +10% attack in arid terrain.", "Sahroda harakat jarimasi yo'q va +10% hujum ustunligi.", "Нет штрафа движения в пустыне и +10% к атаке в песках.")
    ),
    CRUSADER(
        era = Era.EUROPE,
        displayName = LocalizedText("Crusader States & Orders", "Salibchilar qo'shini", "Войска крестоносцев"),
        bannerPrimaryHex = 0xFF8C1D24,
        bannerSecondaryHex = 0xFFF4EFE6,
        uniqueDoctrineName = LocalizedText("Couched Lance Charge", "Zirhli nayza hamlası", "Таранный удар копьём"),
        uniqueDoctrineDesc = LocalizedText("Heavy cavalry and knights gain +15% shock attack on open plains.", "Og'ir otliqlar va ritsarlar tekislikda +15% zarba oladi.", "Тяжелая конница и рыцари получают +15% к атаке на равнине.")
    ),
    OTTOMAN(
        era = Era.MIDDLE_EAST,
        displayName = LocalizedText("Ottoman Beylik / Sultanate", "Usmonlilar davlati", "Османский султанат"),
        bannerPrimaryHex = 0xFF7B1E22,
        bannerSecondaryHex = 0xFFD9B24C,
        uniqueDoctrineName = LocalizedText("Kapikulu Center Discipline", "Qopiquli markaz intizomi", "Дисциплина Капыкулу"),
        uniqueDoctrineDesc = LocalizedText("Infantry and archers lose 25% less morale when taking casualties.", "Piyoda va kamondorlar yo'qotishda 25% kamroq ruhiyat yo'qotadi.", "Пехота и лучники теряют на 25% меньше духа при потерях.")
    ),
    DELHI_SULTANATE(
        era = Era.CENTRAL_ASIAN,
        displayName = LocalizedText("Sultanate of Delhi", "Dehli sultonligi", "Делийский султанат"),
        bannerPrimaryHex = 0xFF3E5622,
        bannerSecondaryHex = 0xFFE3A83B,
        uniqueDoctrineName = LocalizedText("Armored Elephant Line", "Zirhli fillar safi", "Линия боевых слонов"),
        uniqueDoctrineDesc = LocalizedText("War elephants intimidate adjacent enemies for -8 morale per turn.", "Jangovar fillar yonidagi dushman ruhiyatini har yurishda -8 ga tushiradi.", "Боевые слоны снижают боевой дух соседних врагов на 8 за ход.")
    ),
    CHAGATAI(
        era = Era.STEPPE,
        displayName = LocalizedText("Moghulistan / Chagatai Ulus", "Mo'g'uliston / Chig'atoy ulusi", "Могулистан / Чагатайский улус"),
        bannerPrimaryHex = 0xFF2E5266,
        bannerSecondaryHex = 0xFFD9A05B,
        uniqueDoctrineName = LocalizedText("Storm Riders of Jetisu", "Yettisuv bo'ron otliqlari", "Всадники бури Семиречья"),
        uniqueDoctrineDesc = LocalizedText("Cavalry retains full attack power during rain, mud, and snow.", "Otliqlar yomg'ir, loy va qorda hujum kuchini yo'qotmaydi.", "Конница сохраняет полную атаку в дождь, грязь и снег.")
    ),
    ENGLAND(
        era = Era.EUROPE,
        displayName = LocalizedText("Kingdom of England", "Angliya qirolligi", "Королевство Англия"),
        bannerPrimaryHex = 0xFF8B1E24,
        bannerSecondaryHex = 0xFFE6BE4A,
        uniqueDoctrineName = LocalizedText("Yeoman Stakes & Longbows", "Uzun kamon va qoziqlar", "Английский длинный лук и колья"),
        uniqueDoctrineDesc = LocalizedText("Archers gain +15% defense against cavalry charges.", "Kamondorlar otliq hujumiga qarshi +15% himoya oladi.", "Лучники получают +15% к защите против атак конницы.")
    ),
    FRANCE(
        era = Era.EUROPE,
        displayName = LocalizedText("Kingdom of France", "Fransiya qirolligi", "Королевство Франция"),
        bannerPrimaryHex = 0xFF1B3B6F,
        bannerSecondaryHex = 0xFFE8C547,
        uniqueDoctrineName = LocalizedText("Chivalric Vanguard", "Ritsarlar ilg'or safi", "Рыцарский авангард"),
        uniqueDoctrineDesc = LocalizedText("Knights and men-at-arms start battle with +10 bonus morale.", "Ritsarlar va zirhli piyodalar jangni +10 ruhiyat bilan boshlaydi.", "Рыцари и латники начинают бой с +10 к боевому духу.")
    ),
    ANGLO_SAXON(
        era = Era.EUROPE,
        displayName = LocalizedText("Anglo-Saxon Fyrd & Huscarls", "Anglo-Sakson qo'shini", "Англосаксонская рать"),
        bannerPrimaryHex = 0xFF6B2D26,
        bannerSecondaryHex = 0xFFD8B04A,
        uniqueDoctrineName = LocalizedText("Senlac Shield Wall", "Qalqon devori", "Стена щитов"),
        uniqueDoctrineDesc = LocalizedText("Infantry on hills receive +20% defense when adjacent to allies.", "Tepalikdagi piyodalar ittifoqchi yonida +20% himoya oladi.", "Пехота на холмах получает +20% к защите рядом с союзниками.")
    ),
    NORMAN(
        era = Era.EUROPE,
        displayName = LocalizedText("Norman Duchy Army", "Normand gersogligi qo'shini", "Нормандское войско"),
        bannerPrimaryHex = 0xFF7C2E1E,
        bannerSecondaryHex = 0xFFE5B949,
        uniqueDoctrineName = LocalizedText("Combined Arms Feint", "Yolg'on chekinish usuli", "Притворное отступление"),
        uniqueDoctrineDesc = LocalizedText("Cavalry attacks after ranged fire deal +15% bonus damage.", "Kamondor zarbasidan so'ng otliq hujumi +15% ko'proq zarar yetkazadi.", "Атака конницы после обстрела наносит +15% урона.")
    )
}

enum class TerrainType(
    val moveCost: Int,
    val defenseBonus: Int, // percentage e.g. 15 = +15%
    val attackBonus: Int,  // percentage e.g. 10 = +10%
    val elevation: Int,    // 0 = low/water, 1 = flat, 2 = elevated, 3 = high fortification/mountain
    val isFortification: Boolean,
    val blocksCavalryCharge: Boolean,
    val label: LocalizedText,
    val tacticalNote: LocalizedText
) {
    PLAINS(
        moveCost = 1,
        defenseBonus = 0,
        attackBonus = 5,
        elevation = 1,
        isFortification = false,
        blocksCavalryCharge = false,
        label = LocalizedText("Plains", "Tekislik", "Равнина"),
        tacticalNote = LocalizedText("Open ground ideal for cavalry charges (+5% ATK).", "Otliq hujumi uchun qulay ochiq maydon (+5% Hujum).", "Открытая местность, удобная для конной атаки (+5% АТК).")
    ),
    STEPPE(
        moveCost = 1,
        defenseBonus = 5,
        attackBonus = 10,
        elevation = 1,
        isFortification = false,
        blocksCavalryCharge = false,
        label = LocalizedText("Steppe Grasslands", "Dasht yaylovlari", "Степь"),
        tacticalNote = LocalizedText("Fast maneuver ground favoring steppe cavalry (+10% ATK, +5% DEF).", "Dasht otliqlari uchun tezkor maydon (+10% Hujum, +5% Himoya).", "Просторы для маневра конницы (+10% АТК, +5% ЗАЩ).")
    ),
    ROAD(
        moveCost = 1,
        defenseBonus = 0,
        attackBonus = 0,
        elevation = 1,
        isFortification = false,
        blocksCavalryCharge = false,
        label = LocalizedText("Silk Road Caravan Track", "Buyuk Ipak yo'li", "Караванный тракт"),
        tacticalNote = LocalizedText("Rapid march corridor unaffected by mud or snow.", "Loy va qordan ta'sirlanmaydigan tezkor yo'l.", "Быстрый тракт, не замедляемый грязью или снегом.")
    ),
    HILLS(
        moveCost = 2,
        defenseBonus = 20,
        attackBonus = 15,
        elevation = 2,
        isFortification = false,
        blocksCavalryCharge = true,
        label = LocalizedText("High Hills", "Tepaliklar", "Холмы"),
        tacticalNote = LocalizedText("High ground (+20% DEF, +15% elevation ATK, slows cavalry).", "Balandlik ustunligi (+20% Himoya, +15% Hujum).", "Высота дает +20% к защите и +15% к атаке.")
    ),
    FOREST(
        moveCost = 2,
        defenseBonus = 25,
        attackBonus = -5,
        elevation = 1,
        isFortification = false,
        blocksCavalryCharge = true,
        label = LocalizedText("Woodland / Tugai Forest", "To'qay o'rmoni", "Лес / Тугаи"),
        tacticalNote = LocalizedText("Strong cover against arrows (+25% DEF, disrupts cavalry charges).", "Kamondan himoya (+25% Himoya, otliq zarbasini to'xtatadi).", "Укрытие от стрел (+25% ЗАЩ, срывает натиск конницы).")
    ),
    RIVER(
        moveCost = 3,
        defenseBonus = -20,
        attackBonus = -15,
        elevation = 0,
        isFortification = false,
        blocksCavalryCharge = true,
        label = LocalizedText("River Ford (Jayhun / Sayhun)", "Daryo kechuvi", "Речной брод"),
        tacticalNote = LocalizedText("Exposed crossing (-20% DEF, -15% ATK while wading).", "Xavfli kechuv (suvda -20% Himoya, -15% Hujum).", "Уязвимая переправа (-20% ЗАЩ, -15% АТК в воде).")
    ),
    DESERT(
        moveCost = 2,
        defenseBonus = -5,
        attackBonus = 0,
        elevation = 1,
        isFortification = false,
        blocksCavalryCharge = false,
        label = LocalizedText("Kyzylkum / Karakum Sands", "Qizilqum / Qoraqum cho'li", "Пустыня"),
        tacticalNote = LocalizedText("Exhausting sands (cost 2 move, -5% DEF unless desert-adapted).", "Qumliklar (2 harakat, moslashmagan qo'shinga -5% Himoya).", "Тяжелые пески (2 очка хода, -5% ЗАЩ без адаптации).")
    ),
    MOUNTAIN(
        moveCost = 3,
        defenseBonus = 35,
        attackBonus = 20,
        elevation = 3,
        isFortification = false,
        blocksCavalryCharge = true,
        label = LocalizedText("Mountain Pass (Hindu Kush / Tien Shan)", "Tog' dovoni", "Горный перевал"),
        tacticalNote = LocalizedText("Commanding ridge (+35% DEF, +20% elevation ATK, impassable to siege/elephants).", "Tik qoyalar (+35% Himoya, +20% Hujum).", "Господствующая высота (+35% ЗАЩ, +20% АТК).")
    ),
    CITY_WALLS(
        moveCost = 2,
        defenseBonus = 45,
        attackBonus = 15,
        elevation = 3,
        isFortification = true,
        blocksCavalryCharge = true,
        label = LocalizedText("Fortified City Walls", "Shahar mudofaa devori", "Городские стены"),
        tacticalNote = LocalizedText("Heavy ramparts (+45% DEF until breached by siege engines or sappers).", "Mustahkam devor (+45% Himoya, qamal qurollari bilan buziladi).", "Крепостной вал (+45% ЗАЩ до пролома осадными орудиями).")
    ),
    FORTRESS(
        moveCost = 2,
        defenseBonus = 55,
        attackBonus = 20,
        elevation = 3,
        isFortification = true,
        blocksCavalryCharge = true,
        label = LocalizedText("Citadel / Ark Fortress", "Ark / Qal'a qo'rg'oni", "Цитадель / Арк"),
        tacticalNote = LocalizedText("Supreme stronghold (+55% DEF, +20% ATK, restores +5 morale/turn to garrison).", "Bosh qal'a (+55% Himoya, +20% Hujum, har yurishda +5 ruhiyat).", "Главная твердыня (+55% ЗАЩ, +20% АТК, +5 к духу гарнизона).")
    )
}

enum class WeatherType(
    val label: LocalizedText,
    val effectSummary: LocalizedText,
    val rangedRangeDelta: Int = 0,
    val rangedAttackModPercent: Int = 0,
    val cavalryExtraMoveCost: Int = 0,
    val moraleDrainPerTurn: Int = 0
) {
    CLEAR(
        label = LocalizedText("Clear Skies", "Ochiq havo", "Ясная погода"),
        effectSummary = LocalizedText("Standard visibility and footing across the battlefield.", "Standart ko'rinish va harakat sharoiti.", "Обычная видимость и проходимость.")
    ),
    MUD(
        label = LocalizedText("Downpour & Deep Mud", "Jala va botqoq loy", "Ливень и распутица"),
        effectSummary = LocalizedText("Heavy cavalry & siege +1 move cost; composite bows -15% attack.", "Og'ir otliqlar +1 harakat sarflaydi; kamonlar kuchi -15%.", "Тяжелая конница тратит +1 хода; атака луков -15%."),
        rangedAttackModPercent = -15,
        cavalryExtraMoveCost = 1
    ),
    SANDSTORM(
        label = LocalizedText("Swirling Sandstorm", "Qum bo'roni", "Песчаная буря"),
        effectSummary = LocalizedText("Ranged units -1 max range (min 1) and -10% accuracy.", "Kamondorlar masofasi -1 va aniqligi -10%.", "Дальность стрельбы -1, атака стрелков -10%."),
        rangedRangeDelta = -1,
        rangedAttackModPercent = -10,
        moraleDrainPerTurn = 2
    ),
    RAIN(
        label = LocalizedText("Driving Rain", "Kuchli yomg'ir", "Проливной дождь"),
        effectSummary = LocalizedText("Bowstrings dampened (-10% ranged attack).", "Kamon iplari namlanadi (o'qchilar hujumi -10%).", "Тетива отсырела (-10% к атаке стрелков)."),
        rangedAttackModPercent = -10
    ),
    SNOW(
        label = LocalizedText("Steppe Frost & Snow", "Qahraton qor", "Степная стужа и снег"),
        effectSummary = LocalizedText("Non-steppe armies suffer -3 morale/turn from bitter cold.", "Dashtga moslashmagan qo'shinlar sovuqdan -3 ruhiyat yo'qotadi.", "Непривычные к стуже войска теряют 3 духа за ход."),
        moraleDrainPerTurn = 3
    ),
    NIGHT(
        label = LocalizedText("Night Engagement", "Tungi jang", "Ночной бой"),
        effectSummary = LocalizedText("Ranged distance reduced by 1; flanking attacks deal +10% extra shock.", "Masofadan o'q uzish -1; qanotdan zarba +10% kuchliroq.", "Дальность стрельбы -1; фланговые удары +10% сильнее."),
        rangedRangeDelta = -1
    )
}

/**
 * Flat-top axial hex coordinates (q = column, r = row).
 */
data class HexCoord(val q: Int, val r: Int) {
    val s: Int get() = -q - r

    fun distanceTo(other: HexCoord): Int {
        return max(
            abs(q - other.q),
            max(abs(r - other.r), abs(s - other.s))
        )
    }

    fun neighbors(): List<HexCoord> = listOf(
        HexCoord(q + 1, r),
        HexCoord(q + 1, r - 1),
        HexCoord(q, r - 1),
        HexCoord(q - 1, r),
        HexCoord(q - 1, r + 1),
        HexCoord(q, r + 1)
    )
}

enum class UnitCategory(val label: LocalizedText) {
    INFANTRY(LocalizedText("Heavy Infantry", "Og'ir piyoda", "Тяжелая пехота")),
    SPEARMEN(LocalizedText("Polearms & Spearmen", "Nayzadorlar", "Копейщики и пикинеры")),
    RANGED(LocalizedText("Foot Archers & Crossbows", "Yoychilar va arbaletchilar", "Пешие стрелки")),
    LIGHT_CAVALRY(LocalizedText("Light Cavalry & Raiders", "Yengil otliqlar", "Легкая конница")),
    HEAVY_CAVALRY(LocalizedText("Heavy Shock Cavalry", "Og'ir zirhli otliqlar", "Тяжелая ударная конница")),
    HORSE_ARCHER(LocalizedText("Steppe Horse Archers", "Otliq kamondorlar", "Конные лучники")),
    ELEPHANT(LocalizedText("War Elephants", "Jangovar fillar", "Боевые слоны")),
    SIEGE(LocalizedText("Siege Engines & Sappers", "Qamal qurollari va lahimchilar", "Осадные орудия и саперы")),
    SUPPORT(LocalizedText("Scouts & Tacticians", "Kuzatuvchilar va muhandislar", "Разведчики и инженеры"))
}

enum class SpecialAbilityType(
    val title: LocalizedText,
    val description: LocalizedText,
    val cooldownTurns: Int
) {
    PARTHIAN_SHOT(
        title = LocalizedText("Parthian Shot", "To'lg'ama o'q uzish", "Парфянский выстрел"),
        description = LocalizedText("Fire a full volley and automatically step back 1 hex away from the enemy.", "Yoppasiga o'q uzib, dushmandan 1 katak orqaga chekinadi.", "Дает залп и автоматически отходит на 1 гекс назад."),
        cooldownTurns = 2
    ),
    FEIGNED_RETREAT(
        title = LocalizedText("Feigned Retreat", "Yolg'on chekinish", "Притворное отступление"),
        description = LocalizedText("Harass target and lower their defense by 20% while pulling back.", "Dushmanni chalg'itib, himoyasini 20% pasaytiradi.", "Тревожит цель, снижая её защиту на 20% при отходе."),
        cooldownTurns = 2
    ),
    LANCE_CHARGE(
        title = LocalizedText("Shock Lance Charge", "Zirhli nayza hamlasi", "Удар тяжелым копьем"),
        description = LocalizedText("+30% melee damage and -15 enemy morale on open terrain.", "Ochiq maydonda +30% zarba va dushman ruhiyatiga -15 zarba.", "+30% к урону в ближнем бою и -15 к боевому духу врага."),
        cooldownTurns = 2
    ),
    ELEPHANT_TRAMPLE(
        title = LocalizedText("War Elephant Trample", "Jangovar fil bosqini", "Топот боевых слонов"),
        description = LocalizedText("Crushes target and shocks all adjacent enemy units (-20 morale, splash damage).", "Nishonni yanchib, atrofdagi barcha dushman ruhiyatini -20 ga tushiradi.", "Сокрушает цель и пугает соседних врагов (-20 к духу, урон по площади)."),
        cooldownTurns = 3
    ),
    SHIELD_WALL(
        title = LocalizedText("Iron Shield Wall", "Qalqonlar devori", "Железная стена щитов"),
        description = LocalizedText("Fortifies position for +35% defense and +15 morale recovery.", "Joriy nuqtada +35% himoya va +15 ruhiyat tiklaydi.", "Укрепляет позицию: +35% к защите и +15 к боевому духу."),
        cooldownTurns = 2
    ),
    PIKE_PHALANX(
        title = LocalizedText("Braced Spear Wall", "Nayzalar to'sig'i", "Строй копий"),
        description = LocalizedText("Deals +60% bonus damage against cavalry and elephants.", "Otliqlar va fillarga qarshi +60% qo'shimcha zarar yetkazadi.", "Наносит +60% дополнительного урона коннице и слонам."),
        cooldownTurns = 2
    ),
    VOLLEY_FIRE(
        title = LocalizedText("Massed Arrow Volley", "Yoppasiga kamon do'li", "Массированный залп"),
        description = LocalizedText("Arcing barrage with +25% damage that ignores shield cover.", "To'siqlardan oshib o'tuvchi +25% kuchli kamon do'li.", "Навесной обстрел с +25% урона без ответного удара."),
        cooldownTurns = 2
    ),
    SAPPING(
        title = LocalizedText("Breach & Sap Ramparts", "Laqim qazish va devor buzish", "Подкоп и пролом стен"),
        description = LocalizedText("Inflicts 60 structural damage to city walls/fortresses and +40% damage to garrisons.", "Shahar devorini 60 birlikka yemiradi va garnizonga +40% zarar beradi.", "Сносит 60 ед. прочности стен и наносит +40% урона гарнизону."),
        cooldownTurns = 2
    ),
    RALLY_TROOPS(
        title = LocalizedText("Standards & Karnay Horn", "Tug' va Karnay da'vati", "Знамена и звук карная"),
        description = LocalizedText("Restores +25 morale to this unit and all adjacent friendly forces.", "Shu bo'linma va atrofdagi ittifoqchilarga +25 ruhiyat qaytaradi.", "Восстанавливает +25 к боевому духу себе и соседним союзникам."),
        cooldownTurns = 3
    )
}

data class UnitArchetype(
    val id: String,
    val name: LocalizedText,
    val era: Era,
    val category: UnitCategory,
    val maxHp: Int,
    val attack: Int,
    val defense: Int,
    val movement: Int,
    val range: Int,
    val maxMorale: Int = 100,
    val specialAbility: SpecialAbilityType,
    val cost: Int,
    val historicalNote: LocalizedText
)

enum class HeroActiveEffectType {
    TIMURID_ENCIRCLEMENT,
    ARROW_STORM,
    INDUS_DEFIANCE,
    ROYAL_GUARD,
    HORNS_OF_HATTIN,
    LIONHEART_VANGUARD,
    SHIELD_WALL_OATH,
    AGINCOURT_STAKES,
    YILDIRIM_BLITZ,
    TULUGHMA_FLANK,
    ZIJ_ASTRONOMY_VOLLEY,
    GOLDEN_HORDE_SWARM
}

data class HeroDefinition(
    val id: String,
    val name: LocalizedText,
    val title: LocalizedText,
    val lifespan: String,
    val era: Era,
    val factionId: FactionId,
    val biography: LocalizedText,
    val passiveAuraName: LocalizedText,
    val passiveAuraDesc: LocalizedText,
    val passiveAttackBonus: Int,
    val passiveDefenseBonus: Int,
    val passiveMoraleAura: Int,
    val activeAbilityName: LocalizedText,
    val activeAbilityDesc: LocalizedText,
    val activeCooldownTurns: Int,
    val activeEffectType: HeroActiveEffectType,
    val baseUnitArchetypeId: String
)

enum class SourceReliability(val label: LocalizedText) {
    DOCUMENTED(LocalizedText("Documented Primary/Secondary Source", "Tasdiqlangan tarixiy manba", "Документированный источник")),
    DEBATED(LocalizedText("Debated Among Chroniclers", "Tarixchilar orasida bahsli", "Предмет споров хронистов")),
    GAME_SIMPLIFICATION(LocalizedText("Tactical Scale Adaptation", "O'yin miqyosiga moslashtirilgan", "Игровое тактическое упрощение"))
}

data class HistoricalSource(
    val title: String,
    val authorOrPeriod: String,
    val reliability: SourceReliability,
    val note: LocalizedText
)

enum class ObjectiveType(val label: LocalizedText) {
    DESTROY_ARMY(LocalizedText("Rout or Defeat Enemy Army", "Dushman qo'shinini tor-mor etish", "Разгромить вражеское войско")),
    CAPTURE_HEX(LocalizedText("Capture Strategic Stronghold", "Muhim qal'a yoki nuqtani egallash", "Захватить ключевую твердыню")),
    SURVIVE_TURNS(LocalizedText("Hold the Line for N Turns", "Belgilangan yurishgacha mudofaani ushlash", "Удержать рубеж N ходов")),
    PROTECT_HERO(LocalizedText("Protect Commander & Defeat Foe", "Sarkardani asrash va g'alaba qozonish", "Сохранить полководца и победить")),
    BREAK_SIEGE(LocalizedText("Break the Siege & Relieve Garrison", "Qamalni yorib o'tish", "Прорвать осаду города")),
    CROSS_RIVER(LocalizedText("Force a Crossing to the Far Bank", "Daryodan kechib o'tish", "Форсировать реку на другой берег"))
}

data class TerrainTileSpec(
    val q: Int,
    val r: Int,
    val terrain: TerrainType,
    val label: String? = null
)

data class UnitSpawn(
    val archetypeId: String,
    val q: Int,
    val r: Int,
    val isHero: Boolean = false,
    val heroId: String? = null,
    val customName: String? = null
)

data class MissionDefinition(
    val id: String,
    val campaignId: String,
    val orderIndex: Int,
    val year: Int,
    val displayDate: String,
    val locationName: LocalizedText,
    val worldMapX: Float, // 0f..1f on Silk Road map canvas
    val worldMapY: Float, // 0f..1f on Silk Road map canvas
    val title: LocalizedText,
    val subtitle: LocalizedText,
    val weather: WeatherType,
    val maxTurns: Int,
    val sideAFaction: FactionId,
    val sideAName: LocalizedText,
    val sideAHeroId: String,
    val sideAObjectiveType: ObjectiveType,
    val sideAObjectiveDesc: LocalizedText,
    val sideBFaction: FactionId,
    val sideBName: LocalizedText,
    val sideBHeroId: String,
    val sideBObjectiveType: ObjectiveType,
    val sideBObjectiveDesc: LocalizedText,
    val targetHex: HexCoord?,
    val surviveTurnsTarget: Int = 8,
    val briefing: LocalizedText,
    val whatReallyHappened: LocalizedText,
    val factVsGameAccurate: LocalizedText,
    val factVsGameChanged: LocalizedText,
    val sources: List<HistoricalSource>,
    val reviewedBy: String,
    val whatIfTitle: LocalizedText,
    val whatIfDescription: LocalizedText,
    val whatIfSideABonusUnits: List<UnitSpawn> = emptyList(),
    val whatIfSideBBonusUnits: List<UnitSpawn> = emptyList(),
    val whatIfWeatherOverride: WeatherType? = null,
    val mapWidth: Int = 10,
    val mapHeight: Int = 8,
    val defaultTerrain: TerrainType = TerrainType.PLAINS,
    val terrainOverrides: List<TerrainTileSpec>,
    val sideASpawns: List<UnitSpawn>,
    val sideBSpawns: List<UnitSpawn>,
    val isTutorialFeatured: Boolean = false
)

data class CampaignDefinition(
    val id: String,
    val title: LocalizedText,
    val subtitle: LocalizedText,
    val eraRange: String,
    val century: Int,
    val description: LocalizedText,
    val musicThemeId: String,
    val primaryColorHex: Long,
    val missions: List<MissionDefinition>
)

data class ChronicleCityEntry(
    val id: String,
    val name: LocalizedText,
    val region: LocalizedText,
    val goldenAgeEra: String,
    val worldMapX: Float,
    val worldMapY: Float,
    val summary: LocalizedText,
    val monumentsAndLegacy: LocalizedText
)

data class ChronicleTimelineEvent(
    val year: Int,
    val title: LocalizedText,
    val region: LocalizedText,
    val summary: LocalizedText,
    val linkedMissionId: String? = null
)

enum class AiDifficulty(
    val label: LocalizedText,
    val description: LocalizedText,
    val aiDamageMultiplier: Float,
    val aiMoraleBonus: Int
) {
    CHRONICLER(
        label = LocalizedText("Chronicler (Easy)", "Solnomachi (Oson)", "Летописец (Легко)"),
        description = LocalizedText("For players exploring the historical narrative. AI takes fewer flanking risks.", "Tarixni o'rganuvchilar uchun qulay daraja.", "Для знакомства с историей. ИИ действует осторожнее."),
        aiDamageMultiplier = 0.85f,
        aiMoraleBonus = 0
    ),
    COMMANDER(
        label = LocalizedText("Commander (Normal)", "Amir / Sarkarda (O'rta)", "Полководец (Нормально)"),
        description = LocalizedText("Balanced historical tactics. AI uses terrain, focus fire, and era doctrines.", "Muvozanatli taktika: joylashuv, qanot zarbasi va davr usullari.", "Сбалансированная тактика: учет рельефа, флангов и доктрин эпохи."),
        aiDamageMultiplier = 1.0f,
        aiMoraleBonus = 5
    ),
    CONQUEROR(
        label = LocalizedText("Conqueror (Hard)", "Sohibqiron (Qiyin)", "Завоеватель (Сложно)"),
        description = LocalizedText("Relentless tactical pressure. AI aggressively kites, flanks, and guards its commander.", "Murakkab sinov: dushman har bir xatodan unumli foydalanadi.", "Суровое испытание: агрессивный охват, кайтинг и концентрация ударов."),
        aiDamageMultiplier = 1.15f,
        aiMoraleBonus = 15
    )
}

enum class BattleSide {
    SIDE_A,
    SIDE_B;

    fun opposite(): BattleSide = if (this == SIDE_A) SIDE_B else SIDE_A
}

enum class GameMode {
    CAMPAIGN,
    WHAT_IF,
    SKIRMISH,
    PASS_AND_PLAY
}
