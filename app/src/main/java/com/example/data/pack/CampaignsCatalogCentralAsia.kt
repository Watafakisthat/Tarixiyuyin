package com.example.data.pack

import com.example.model.CampaignDefinition
import com.example.model.FactionId
import com.example.model.HexCoord
import com.example.model.HistoricalSource
import com.example.model.LocalizedText
import com.example.model.MissionDefinition
import com.example.model.ObjectiveType
import com.example.model.SourceReliability
import com.example.model.TerrainTileSpec
import com.example.model.TerrainType
import com.example.model.UnitSpawn
import com.example.model.WeatherType

object CampaignsCatalogCentralAsia {

    val campaignTimurRise = CampaignDefinition(
        id = "campaign_timur_rise",
        title = LocalizedText("Campaign I: Timur's Rise", "I Yurish: Amir Temur yuksalishi", "Кампания I: Возвышение Тимура"),
        subtitle = LocalizedText("Transoxiana, the Kipchak Steppe, Delhi & Anatolia (1360s–1405)", "Movarounnahr, Dashti Qipchoq, Dehli va Onado'li (1360–1405)", "Мавераннахр, Дешт-и-Кипчак, Дели и Анатолия (1360–1405)"),
        eraRange = "1360–1405 CE",
        century = 14,
        description = LocalizedText(
            "From the mountain passes of Kesh (Shahrisabz) and the rain-swept banks of the Chirchiq to the vast Volga steppe, Delhi, and Ankara: command either Amir Timur's forces or his formidable rivals.",
            "Kesh (Shahrisabz) dovonlaridan va Chirchiq bo'yidagi Loy jangidan tortib Qunduzcha dashtlari, Dehli va Anqaragacha bo'lgan tarixiy yurishlar.",
            "От горных перевалов Кеша (Шахрисабза) и берегов Чирчика до степей Кондурчи, Дели и Анкары."
        ),
        musicThemeId = "theme_central_asia_dutar",
        primaryColorHex = 0xFF0F3B5F,
        missions = listOf(
            // Mission 1: Conquest of Transoxiana, 1360s (Guided Tutorial Mission)
            MissionDefinition(
                id = "mission_transoxiana_1360",
                campaignId = "campaign_timur_rise",
                orderIndex = 1,
                year = 1362,
                displayDate = "1362–1364 CE",
                locationName = LocalizedText("Kesh (Shahrisabz) & Stone Bridge of the Vakhsh", "Kesh (Shahrisabz) va Toshko'prik", "Кеш (Шахрисабз) и Каменный мост"),
                worldMapX = 0.63f,
                worldMapY = 0.52f,
                title = LocalizedText("1. Struggle for Transoxiana", "1. Movarounnahr uchun kurash", "1. Борьба за Мавераннахр"),
                subtitle = LocalizedText("Outmaneuvering the Jete (Moghulistan) Garrison at the River Crossing", "Toshko'prik kechuvida Mo'g'uliston qo'shiniga qarshi harbiy hiyla", "Ночной маневр у Каменного моста против войск Могулистана"),
                weather = WeatherType.CLEAR,
                maxTurns = 12,
                sideAFaction = FactionId.TIMURID,
                sideAName = LocalizedText("Amir Timur's Barlas Horsemen", "Amir Temurning Barlos suvorilari", "Всадники барласов Амира Тимура"),
                sideAHeroId = "amir_timur",
                sideAObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideAObjectiveDesc = LocalizedText("Capture the Stone Bridge hill stronghold (7,3) or rout the Chagatai force.", "Toshko'prik tepaligini (7,3) egallang yoki raqib qo'shinini tor-mor eting.", "Захватите укрепление у Каменного моста (7,3) или разбейте врага."),
                sideBFaction = FactionId.CHAGATAI,
                sideBName = LocalizedText("Moghulistan (Jete) Frontier Host", "Mo'g'uliston (Jete) qo'shini", "Войско Могулистана"),
                sideBHeroId = "tokhtamysh",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Hold the Stone Bridge crossing and defeat Timur's insurgents.", "Kechuvni himoya qiling va Temur suvorilarini to'xtating.", "Удержите переправу и разгромите отряды Тимура."),
                targetHex = HexCoord(7, 3),
                briefing = LocalizedText(
                    "In the early 1360s, Transoxiana (modern Uzbekistan and Tajikistan) was fractured between local amirs and the eastern Chagatai khans of Moghulistan. With a small band of horsemen near Kesh and the Oxus tributaries, young Timur used campfires on the hills to deceive a much larger Moghul army into believing a vast host had arrived, before striking their flank at dawn.",
                    "1360-yillar boshida Movarounnahr tarqoq beklar va Mo'g'uliston xonlari o'rtasida kurash maydoniga aylangan edi. Kesh va Toshko'prik atrofida oz sonli suvorilar bilan harakat qilgan yosh Amir Temur tepaliklarda ko'plab gulxanlar yoqtirib, raqibda katta qo'shin kelgandek tasavvur uyg'otdi va tongda qanotdan hal qiluvchi zarba berdi.",
                    "В начале 1360-х годов Мавераннахр был ареной борьбы местных эмиров и правителей Могулистана. С малым отрядом у Кеша и Каменного моста молодой Тимур зажег множество костров на холмах, создав видимость огромного войска, и утром ударил во фланг."
                ),
                whatReallyHappened = LocalizedText(
                    "Timur's night ruse and rapid seizure of the bridge crossing forced the Moghulistan vanguard under Ilyas Khoja's commanders to fall back across the Syr Darya, establishing Timur's reputation as a resourceful tactician.",
                    "Amir Temurning tungi harbiy hiylasi va Toshko'prik kechuvini chaqqonlik bilan egallashi Ilyosxo'ja lashkarboshilarini chekinishga majbur qildi va Temurning mohir sarkarda sifatidagi dovrug'ini yoydi.",
                    "Военная хитрость с кострами и захват переправы вынудили авангард Могулистана отступить, укрепив авторитет Тимура в Мавераннахре."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The river gorge topography, Timur's numerical inferiority in his early Kesh campaigns, and the reliance on mobility and psychological shock.",
                    "Tarixiy haqiqat: Daryo kechuvi relyefi, Temurning dastlabki kurashlarda oz sonli suvorilar bilan harakat qilgani va harbiy hiyla ishlatgani.",
                    "Исторично: Рельеф переправы, численное меньшинство отряда Тимура и ставка на маневр и психологический шок."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Condensed several weeks of mountain skirmishes around Kesh and the Stone Bridge (Pul-i Sangin) into a single decisive tactical hex engagement.",
                    "O'yin moslashuvi: Kesh va Toshko'prik atrofidagi bir necha haftalik to'qnashuvlar bitta ixcham taktik xarita jangiga birlashtirildi.",
                    "Игровое упрощение: Несколько недель маневров у Кеша и Пул-и-Сангина объединены в один тактический бой."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Zafarnama (Book of Victories)",
                        authorOrPeriod = "Sharaf ad-Din Ali Yazdi (c. 1425)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Describes the Pul-i Sangin crossing and the campfire deception on the surrounding ridges.", "Puli Sangin (Toshko'prik) kechuvi va tepaliklarda gulxan yoqish hiylasini batafsil tasvirlaydi.", "Описывает переправу Пул-и-Сангин и военную хитрость с кострами на хребтах.")
                    ),
                    HistoricalSource(
                        title = "Ajayib al-Maqdur fi Nawayib Timur",
                        authorOrPeriod = "Ibn Arabshah (1436)",
                        reliability = SourceReliability.DEBATED,
                        note = LocalizedText("Provides a critical Damascus perspective on Timur's early years as a frontier raider in Kesh.", "Temurning Keshdagi yoshlik yillari haqida tanqidiy, ammo muhim ma'lumotlar beradi.", "Содержит критический, но ценный взгляд на ранние годы Тимура в Кеше.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Amir Husayn's Wing Arrived Early?", "Agar Amir Husayn qanoti o'z vaqtida yetib kelganida?", "Что если бы корпус эмира Хусейна подошел вовремя?"),
                whatIfDescription = LocalizedText("Hypothetical: Both sides bring reinforced heavy cavalry squadrons to the Stone Bridge.", "Faraziy holat: Har ikki tomon Toshko'prik kechuviga qo'shimcha og'ir zirhli otliqlar bilan chiqadi.", "Гипотеза: Обе стороны выводят к переправе дополнительные отряды тяжелой конницы."),
                whatIfSideABonusUnits = listOf(UnitSpawn("timurid_heavy_cavalry", 1, 5)),
                whatIfSideBBonusUnits = listOf(UnitSpawn("steppe_heavy_cavalry", 8, 2)),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.STEPPE,
                terrainOverrides = listOf(
                    TerrainTileSpec(4, 0, TerrainType.RIVER),
                    TerrainTileSpec(4, 1, TerrainType.RIVER),
                    TerrainTileSpec(4, 2, TerrainType.RIVER),
                    TerrainTileSpec(4, 3, TerrainType.ROAD, "Pul-i Sangin Bridge"),
                    TerrainTileSpec(4, 4, TerrainType.RIVER),
                    TerrainTileSpec(4, 5, TerrainType.RIVER),
                    TerrainTileSpec(4, 6, TerrainType.RIVER),
                    TerrainTileSpec(2, 2, TerrainType.HILLS),
                    TerrainTileSpec(2, 4, TerrainType.HILLS),
                    TerrainTileSpec(6, 2, TerrainType.HILLS),
                    TerrainTileSpec(7, 3, TerrainType.FORTRESS, "Bridgehead Camp"),
                    TerrainTileSpec(8, 4, TerrainType.FOREST),
                    TerrainTileSpec(1, 3, TerrainType.ROAD),
                    TerrainTileSpec(2, 3, TerrainType.ROAD),
                    TerrainTileSpec(3, 3, TerrainType.ROAD),
                    TerrainTileSpec(5, 3, TerrainType.ROAD),
                    TerrainTileSpec(6, 3, TerrainType.ROAD)
                ),
                sideASpawns = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 1, 3, isHero = true, heroId = "amir_timur"),
                    UnitSpawn("steppe_horse_archer", 2, 2),
                    UnitSpawn("steppe_light_cavalry", 2, 4),
                    UnitSpawn("sogdian_infantry", 3, 3),
                    UnitSpawn("common_archers", 1, 4)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 7, 3, isHero = true, heroId = "tokhtamysh"),
                    UnitSpawn("steppe_horse_archer", 6, 2),
                    UnitSpawn("steppe_light_cavalry", 6, 4),
                    UnitSpawn("common_spearmen", 5, 3),
                    UnitSpawn("nomad_raiders", 8, 3)
                ),
                isTutorialFeatured = true
            ),

            // Mission 2: Battle of the Mud (Chinaz), 1365
            MissionDefinition(
                id = "mission_mud_1365",
                campaignId = "campaign_timur_rise",
                orderIndex = 2,
                year = 1365,
                displayDate = "22 May 1365",
                locationName = LocalizedText("Chirchiq River near Chinaz / Tashkent", "Chinoz va Chirchiq daryosi bo'yi", "Река Чирчик у Чиназа / Ташкента"),
                worldMapX = 0.66f,
                worldMapY = 0.44f,
                title = LocalizedText("2. Battle of the Mud (Jang-i Loy)", "2. Loy jangi (Chinoz, 1365)", "2. Грязевая битва (Чиназ, 1365)"),
                subtitle = LocalizedText("Clashing with Ilyas Khoja in a Torrential Spring Downpour", "Bahorgi jala va botqoqda Ilyosxo'ja qo'shiniga qarshi jang", "Сражение с Ильяс-Ходжой под проливным весенним ливнем"),
                weather = WeatherType.MUD,
                maxTurns = 12,
                sideAFaction = FactionId.TIMURID,
                sideAName = LocalizedText("Timur & Husayn's Transoxianian Army", "Temur va Husayn qo'shini", "Войско Тимура и Хусейна"),
                sideAHeroId = "amir_timur",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Break the Moghulistan wings despite the deep mud and protect Amir Timur.", "Botqoqlikka qaramay Mo'g'uliston qanotlarini yanching va Amir Temurni asrang.", "Сокрушите фланги Могулистана в глубокой грязи и сохраните Тимура."),
                sideBFaction = FactionId.CHAGATAI,
                sideBName = LocalizedText("Ilyas Khoja's Moghulistan Host", "Ilyosxo'ja qo'shini", "Армия хана Ильяс-Ходжи"),
                sideBHeroId = "tokhtamysh",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Use Moghul felt-covered horses to overwhelm the bogged Transoxianian line.", "Yomg'irga chidamli otliqlar bilan Movarounnahr safini yorib o'ting.", "Используйте устойчивую к непогоде конницу для прорыва строя."),
                targetHex = null,
                briefing = LocalizedText(
                    "On 22 May 1365, near Chinaz between Tashkent and the Syr Darya, a ferocious thunderstorm turned the plain into knee-deep mud. Bowstrings went slack, armor grew heavy with soaked felt, and horses slipped in the mire. While Timur's left wing broke the opposing flank, his ally Amir Husayn hesitated to commit reserves—leaving the battle on a knife's edge.",
                    "1365-yil 22-mayda Toshkent va Sirdaryo oralig'idagi Chinoz yaqinida kuchli jala quyib, jang maydonini tizzagacha loyga aylantirdi. Kamon iplari bo'shashib, otlar botqoqqa botib qoldi. Amir Temur o'z qanotida dushmanni chekintirgan bo'lsa-da, ittifoqchisi Amir Husayn zaxira kuchlarini yuborishdan bosh tortdi.",
                    "22 мая 1365 года у Чиназа между Ташкентом и Сырдарьей разразился чудовищный ливень, превративший поле в сплошное болото. Тетивы луков отсырели, кони вязли в грязи. Тимур опрокинул крыло противника на своем фланге, но эмир Хусейн медлил с вводом резерва."
                ),
                whatReallyHappened = LocalizedText(
                    "Historically, because Amir Husayn refused to advance his wing in the mud, Timur's exhausted forces had to withdraw south across the Zarafshan. Meanwhile in Samarkand, the Sarbadar citizen movement successfully defended the city walls against Ilyas Khoja!",
                    "Tarixda Amir Husayn o'z qanotini oldinga tashlamagani sababli Temur qo'shini Kesh tomonga chekinishga majbur bo'ldi. Samarqandda esa Sarbadorlar xalq harakati shahar mudofaasini o'z qo'liga olib, Ilyosxo'ja qo'shinini shahardan quvib chiqardi.",
                    "Из-за бездействия Хусейна войско Тимура было вынуждено отступить за Зеравшан, однако в самом Самарканде народное движение сарбадаров отстояло город от армии Ильяс-Ходжи."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The severe mud weather penalty on cavalry and bows, and the Chirchiq river terrain.",
                    "Tarixiy haqiqat: Jala va loyning kamon hamda og'ir otliqlarga salbiy ta'siri va Chirchiq bo'yi tabiati.",
                    "Исторично: Штрафы грязи для конницы и луков, а также рельеф берегов Чирчика."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Allows the player commanding Timur to coordinate both wings directly and win the field that Husayn forfeited.",
                    "O'yin moslashuvi: O'yinchi barcha bo'linmalarni bevosita boshqarib, tarixda boy berilgan jangni g'alabaga aylantira oladi.",
                    "Игровое упрощение: Игрок управляет обоими крыльями напрямую, имея шанс одержать победу."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Zafarnama",
                        authorOrPeriod = "Nizam ad-Din Shami (1404) & Yazdi (1425)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Primary Timurid accounts detailing the 'Jang-i Loy' (Battle of the Mud) and the dispute with Amir Husayn.", "«Jangi Loy» tafsilotlari va Amir Husayn bilan kelishmovchilik haqidagi birlamchi manba.", "Первоисточники, подробно описывающие «Джанг-и-Лой» (Грязевую битву).")
                    ),
                    HistoricalSource(
                        title = "Tarikh-i Rashidi",
                        authorOrPeriod = "Mirza Muhammad Haidar Dughlat (1546)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Chagatai/Moghul perspective on Ilyas Khoja's campaign and the felt rain-covers used by steppe warriors.", "Mo'g'uliston manbalarida Ilyosxo'ja yurishi va namat yopinchiqlar haqidagi ma'lumot.", "Чагатайский взгляд на поход Ильяс-Ходжи и войлочные попоны от ливня.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Clear Dry Weather Held at Chinaz?", "Agar Chinozda havo ochiq va quruq bo'lganida?", "Что если бы у Чиназа стояла сухая погода?"),
                whatIfDescription = LocalizedText("Hypothetical: Fight on dry steppe without mud penalties, enabling full composite bow volleys and rapid heavy cavalry charges.", "Faraziy holat: Loy va yomg'ir jarimalarisiz, quruq maydonda kamon va og'ir suvorilarning to'liq kuchi bilan jang qiling.", "Гипотеза: Сражение в сухую погоду без штрафов грязи при полной силе луков и тяжелой конницы."),
                whatIfWeatherOverride = WeatherType.CLEAR,
                whatIfSideABonusUnits = listOf(UnitSpawn("timurid_heavy_cavalry", 2, 5)),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(0, 1, TerrainType.RIVER),
                    TerrainTileSpec(1, 1, TerrainType.RIVER),
                    TerrainTileSpec(2, 0, TerrainType.RIVER),
                    TerrainTileSpec(3, 2, TerrainType.HILLS),
                    TerrainTileSpec(4, 4, TerrainType.FOREST),
                    TerrainTileSpec(5, 2, TerrainType.FOREST),
                    TerrainTileSpec(6, 5, TerrainType.HILLS),
                    TerrainTileSpec(4, 3, TerrainType.STEPPE),
                    TerrainTileSpec(5, 3, TerrainType.STEPPE)
                ),
                sideASpawns = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 1, 3, isHero = true, heroId = "amir_timur"),
                    UnitSpawn("timurid_heavy_cavalry", 2, 2),
                    UnitSpawn("steppe_horse_archer", 2, 4),
                    UnitSpawn("sogdian_infantry", 3, 3),
                    UnitSpawn("common_spearmen", 3, 4),
                    UnitSpawn("common_scouts", 1, 5)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 8, 3, isHero = true, heroId = "tokhtamysh"),
                    UnitSpawn("steppe_heavy_cavalry", 7, 2),
                    UnitSpawn("steppe_horse_archer", 7, 4),
                    UnitSpawn("steppe_light_cavalry", 6, 1),
                    UnitSpawn("nomad_raiders", 6, 5),
                    UnitSpawn("common_spearmen", 6, 3)
                )
            ),

            // Mission 3: Siege of Urgench, 1370s
            MissionDefinition(
                id = "mission_urgench_1379",
                campaignId = "campaign_timur_rise",
                orderIndex = 3,
                year = 1379,
                displayDate = "1379 CE",
                locationName = LocalizedText("Urgench (Gurganj), Khwarazm Oasis", "Urganch (Gurganj), Xorazm vohasi", "Ургенч (Гургандж), Хорезм"),
                worldMapX = 0.51f,
                worldMapY = 0.42f,
                title = LocalizedText("3. Siege of Urgench (1379)", "3. Urganch qamali (1379)", "3. Осада Ургенча (1379)"),
                subtitle = LocalizedText("Campaign Against the Sufi Dynasty of Khwarazm", "Xorazmning Qo'ng'irot-So'fiylar sulolasiga qarshi yurish", "Поход против династии Суфи в Хорезме"),
                weather = WeatherType.CLEAR,
                maxTurns = 14,
                sideAFaction = FactionId.TIMURID,
                sideAName = LocalizedText("Timurid Siege Army", "Amir Temur qamal qo'shini", "Осадная армия Тимура"),
                sideAHeroId = "amir_timur",
                sideAObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideAObjectiveDesc = LocalizedText("Breach the Urgench ramparts with manjaniq siege crews and capture the Citadel (8,3).", "Manjaniqlar bilan devorni yorib o'ting va Urganch arkini (8,3) egallang.", "Пробейте стены манджаниками и займите цитадель Ургенча (8,3)."),
                sideBFaction = FactionId.KHWARAZMIAN,
                sideBName = LocalizedText("Yusuf Sufi's Khwarazmian Garrison", "Yusuf So'fiy garnizoni", "Гарнизон Юсуфа Суфи"),
                sideBHeroId = "muhammad_khwarazm",
                sideBObjectiveType = ObjectiveType.SURVIVE_TURNS,
                sideBObjectiveDesc = LocalizedText("Hold the Urgench Citadel for 10 turns or destroy the Timurid siege engines.", "Urganch qal'asini 10 yurish davomida himoya qiling yoki qamal qurollarini yo'q qiling.", "Удерживайте цитадель Ургенча 10 ходов или уничтожьте осадные орудия."),
                targetHex = HexCoord(8, 3),
                surviveTurnsTarget = 10,
                briefing = LocalizedText(
                    "Seeking to reunify the western oasis of Khwarazm with Transoxiana and secure the lower Amu Darya trade routes, Amir Timur marched on Urgench, ruled by the Qongrat Sufi dynasty. When Yusuf Sufi challenged Timur to single combat on the moat's edge, Timur rode out alone in full armor—only for Yusuf Sufi to stay behind the gates, triggering a full siege.",
                    "Quyi Amudaryo savdo yo'llarini va Xorazm vohasini Movarounnahr bilan birlashtirish uchun Amir Temur Qo'ng'irot-So'fiylar boshqarayotgan Urganchga yurish qildi. Yusuf So'fiy Temurni yakkama-yakka olishuvga chorlaganida, Sohibqiron xandaq bo'yiga yolg'iz o'zi ot surib chiqdi, biroq raqibi qal'adan chiqmadi va qamal boshlandi.",
                    "Стремясь объединить Хорезм с Мавераннахром, Тимур выступил к Ургенчу, где правила династия Кунграт-Суфи. Когда Юсуф Суфи вызвал Тимура на поединок, Тимур сам выехал ко рву крепости, но правитель Ургенча не вышел за ворота, и началась осада."
                ),
                whatReallyHappened = LocalizedText(
                    "After a three-month siege in 1379 during which Yusuf Sufi died of illness inside the citadel, Urgench opened its gates; skilled artisans and scholars were invited to enrich Samarkand and Kesh.",
                    "Uch oylik qamal davomida Yusuf So'fiy xastalikdan vafot etgach, Urganch taslim bo'ldi; xorazmlik mohir ustalar, me'morlar va olimlar Samarqand hamda Kesh obodonchiligiga jalb etildi.",
                    "После трехмесячной осады и смерти Юсуфа Суфи город сдался, а искусные хорезмийские зодчие и ученые были приглашены в Самарканд и Кеш."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The famous single-combat challenge outside the moat of Urgench recorded in the chronicles and the heavy mud-brick fortifications along the Oxus canals.",
                    "Tarixiy haqiqat: Urganch xandaqi oldidagi mashhur yakkama-yakka chorlov va Amudaryo kanallari bilan o'ralgan qal'a devorlari.",
                    "Исторично: Вызов на поединок у крепостного рва Ургенча и система каналов и глинобитных стен."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Represents the five Khwarazmian expeditions (1371–1388) through the decisive 1379 siege.",
                    "O'yin moslashuvi: Xorazmga qilingan besh yurish (1371–1388) eng muhim 1379-yilgi qamal misolida aks ettirildi.",
                    "Игровое упрощение: Пять хорезмийских походов (1371–1388) представлены через ключевую осаду 1379 года."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Zafarnama",
                        authorOrPeriod = "Sharaf ad-Din Ali Yazdi (1425)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Records the 1379 siege of Urgench and the single-combat challenge between Timur and Yusuf Sufi.", "1379-yilgi Urganch qamali va Yusuf So'fiy bilan yakkama-yakka jang chorlovini tasvirlaydi.", "Описывает осаду 1379 года и вызов на поединок между Тимуром и Юсуфом Суфи.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Tokhtamysh Sent Golden Horde Relief to Urgench?", "Agar To'xtamishxon Urganchga yordam qo'shini yuborganida?", "Что если бы Тохтамыш прислал конницу на помощь Ургенчу?"),
                whatIfDescription = LocalizedText("Hypothetical: Steppe horse archers arrive on the northern flank to harass the Timurid siege lines.", "Faraziy holat: Shimoliy qanotdan Oltin O'rda otliq kamondorlari qamalchilarga zarba berish uchun yetib keladi.", "Гипотеза: С севера к осажденному городу подходят конные лучники Золотой Орды."),
                whatIfSideBBonusUnits = listOf(
                    UnitSpawn("steppe_horse_archer", 5, 1),
                    UnitSpawn("steppe_light_cavalry", 5, 5)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.DESERT,
                terrainOverrides = listOf(
                    TerrainTileSpec(5, 0, TerrainType.RIVER),
                    TerrainTileSpec(5, 1, TerrainType.RIVER),
                    TerrainTileSpec(5, 2, TerrainType.RIVER),
                    TerrainTileSpec(5, 3, TerrainType.ROAD, "Oxus Canal Bridge"),
                    TerrainTileSpec(5, 4, TerrainType.RIVER),
                    TerrainTileSpec(5, 5, TerrainType.RIVER),
                    TerrainTileSpec(5, 6, TerrainType.RIVER),
                    TerrainTileSpec(7, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(7, 3, TerrainType.CITY_WALLS),
                    TerrainTileSpec(7, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(8, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(8, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(8, 3, TerrainType.FORTRESS, "Urgench Citadel")
                ),
                sideASpawns = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 2, 3, isHero = true, heroId = "amir_timur"),
                    UnitSpawn("central_asian_siege_crew", 3, 2),
                    UnitSpawn("central_asian_siege_crew", 3, 4),
                    UnitSpawn("sogdian_infantry", 4, 3),
                    UnitSpawn("common_engineers", 4, 2),
                    UnitSpawn("steppe_horse_archer", 2, 5)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("khwarazmian_cavalry", 8, 3, isHero = true, heroId = "muhammad_khwarazm"),
                    UnitSpawn("sogdian_infantry", 7, 2),
                    UnitSpawn("sogdian_infantry", 7, 3),
                    UnitSpawn("sogdian_infantry", 7, 4),
                    UnitSpawn("common_archers", 8, 2),
                    UnitSpawn("steppe_light_cavalry", 6, 4)
                )
            ),

            // Mission 4: Battle of the Kondurcha River, 1391 (Featured Launch Flagship Mission!)
            MissionDefinition(
                id = "mission_kondurcha_1391",
                campaignId = "campaign_timur_rise",
                orderIndex = 4,
                year = 1391,
                displayDate = "18 June 1391",
                locationName = LocalizedText("Kondurcha River (Volga-Samara Steppe)", "Qunduzcha (Kondurcha) daryosi bo'yi", "Река Кондурча (Самарское Поволжье)"),
                worldMapX = 0.48f,
                worldMapY = 0.25f,
                title = LocalizedText("4. Battle of Kondurcha River (1391)", "4. Qunduzcha daryosi jangi (1391)", "4. Битва на реке Кондурче (1391)"),
                subtitle = LocalizedText("Amir Timur's Seven-Corps Order vs. Khan Tokhtamysh of the Golden Horde", "Amir Temurning «Yetti qo'l» tartibi Oltin O'rda xoni To'xtamishxonga qarshi", "Строй семи корпусов Тимура против хана Золотой Орды Тохтамыша"),
                weather = WeatherType.CLEAR,
                maxTurns = 15,
                sideAFaction = FactionId.TIMURID,
                sideAName = LocalizedText("Timurid Seven-Corps Expedition", "Amir Temurning Yetti qo'l qo'shini", "Семь корпусов Амира Тимура"),
                sideAHeroId = "amir_timur",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Absorb Tokhtamysh's outflanking sweep and shatter the Golden Horde center.", "To'xtamishxonning qanot zarbasini qaytarib, Oltin O'rda markazini tor-mor eting.", "Отразите фланговый охват Тохтамыша и разгромите армию Золотой Орды."),
                sideBFaction = FactionId.GOLDEN_HORDE,
                sideBName = LocalizedText("Golden Horde (Ulus of Jochi)", "Oltin O'rda qo'shini", "Армия Золотой Орды"),
                sideBHeroId = "tokhtamysh",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Envelop both Timurid wings with fast steppe cavalry and break Timur's reserve.", "Tezkor dasht otliqlari bilan Temur qanotlarini o'rab oling va g'alaba qozoning.", "Охватите оба фланга Тимура степной конницей и сокрушите его ставку."),
                targetHex = null,
                briefing = LocalizedText(
                    "To end Khan Tokhtamysh's raids into Transoxiana, Amir Timur led 200,000 troops on a grueling four-month march across the Kazakh steppe, leaving an inscription at Ulugh Tagh (Karsakpay) now preserved in the Hermitage. On 18 June 1391 at the Kondurcha River, Timur deployed an innovative Seven-Corps formation (Qol, Barangar, Juvangar, vanguard, and deep mobile reserves) with entrenched shield lines to blunt Tokhtamysh's wider steppe encirclement.",
                    "To'xtamishxonning Movarounnahrga bosqinlariga chek qo'yish uchun Amir Temur Ulug'tog' (Qarsaqpoy) orqali to'rt oylik mashaqqatli dasht yurishini amalga oshirdi. 1391-yil 18-iyunda Qunduzcha daryosi bo'yida Temur tarixda ilk bor «Yetti qo'l» jang tartibini qo'lladi: qalqonlar va xandaqlar bilan mustahkamlangan markaz hamda shaxsiy zaxira To'xtamishxonning qanotdan o'rab olish urinishini chippakka chiqardi.",
                    "Чтобы положить конец набегам Тохтамыша, Амир Тимур совершил четырехмесячный переход через степи Сары-Арки, оставив знаменитую Карсакпайскую надпись. 18 июня 1391 года на реке Кондурче Тимур впервые применил боевой порядок из семи крупных корпусов с мощным личным резервом, остановив широкий фланговый охват Золотой Орды."
                ),
                whatReallyHappened = LocalizedText(
                    "When Tokhtamysh's horsemen swung around Timur's flanks, Timur ordered his tent-poles lowered on the battlefield—signaling such calm confidence that the Golden Horde vanguard faltered before Timur's elite reserve charged and routed Tokhtamysh toward the Volga.",
                    "To'xtamishxon qanotlardan aylanib o'tgan eng qaltis lahzada Amir Temur jang maydonida chodirlar tiklash va osh damlashni buyurdi — bu sovuqqonlik dushmanni sarosimaga soldi va Temurning zaxira suvorilari Oltin O'rda qo'shinini Edil (Volga) daryosigacha quvib bordi.",
                    "В критический момент боя Тимур приказал разбить шатры прямо на поле сражения, продемонстрировав полное хладнокровие, после чего ввод элитного резерва обратил войско Тохтамыша в бегство к Волге."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The Seven-Corps deployment, the Kondurcha river bend, Tokhtamysh's double-envelopment attempt on the flanks, and the Karsakpay inscription context.",
                    "Tarixiy haqiqat: «Yetti qo'l» jang tartibi, Qunduzcha daryosi o'zani, To'xtamishxonning ikki qanotdan o'rab olish harakati va Qarsaqpoy bitiktoshi.",
                    "Исторично: Построение семью корпусами, излучина Кондурчи, попытка двойного охвата со стороны Тохтамыша и контекст Карсакпайской стелы."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Represents the seven macro-divisions (tumens/corps) as 7 tactical units per side on a 10x8 hex battlefield.",
                    "O'yin moslashuvi: Har bir yirik qo'l (tuman) 10x8 oltiburchakli xaritada alohida taktik bo'linma sifatida tasvirlangan.",
                    "Игровое упрощение: Семь корпусов представлены семью тактическими отрядами на гексагональной карте 10x8."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Karsakpay (Ulugh Tagh) Stone Inscription (1391)",
                        authorOrPeriod = "Carved in Chagatai (Uyghur script) & Arabic, April 1391",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Archaeological monument recording Timur's march north with 200,000 soldiers against Tokhtamysh Khan.", "Amir Temurning 1391-yilda Ulug'tog'da qoldirgan haqiqiy arxeologik tosh bitigi.", "Подлинный эпиграфический памятник похода Тимура 1391 года, найденный в Улытау.")
                    ),
                    HistoricalSource(
                        title = "Zafarnama",
                        authorOrPeriod = "Nizam ad-Din Shami (1404) & Sharaf ad-Din Ali Yazdi (1425)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Detailed account of the Seven-Corps formation and the three-day battle at the Kondurcha River.", "Yetti qo'l jang tartibi va Qunduzcha bo'yidagi uch kunlik jangning to'liq tavsifi.", "Подробное описание построения из семи корпусов и хода битвы на Кондурче.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If the Steppe Blizzard Struck During the Battle?", "Agar jang paytida dasht bo'roni turganida?", "Что если бы во время битвы ударила степная буря?"),
                whatIfDescription = LocalizedText("Hypothetical: Tokhtamysh receives an extra wing of Golden Horde heavy lancers under snowstorm conditions.", "Faraziy holat: Qor bo'roni sharoitida To'xtamishxon qo'shimcha og'ir otliqlar qanotiga ega bo'ladi.", "Гипотеза: Сражение в непогоду, где Тохтамыш получает дополнительный корпус тяжелых уланов."),
                whatIfSideBBonusUnits = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 8, 1),
                    UnitSpawn("steppe_horse_archer", 8, 6)
                ),
                whatIfWeatherOverride = WeatherType.SNOW,
                mapWidth = 10,
                mapHeight = 8,
                defaultTerrain = TerrainType.STEPPE,
                terrainOverrides = listOf(
                    TerrainTileSpec(0, 0, TerrainType.RIVER),
                    TerrainTileSpec(0, 1, TerrainType.RIVER),
                    TerrainTileSpec(1, 7, TerrainType.RIVER),
                    TerrainTileSpec(2, 7, TerrainType.RIVER),
                    TerrainTileSpec(8, 0, TerrainType.RIVER),
                    TerrainTileSpec(9, 0, TerrainType.RIVER),
                    TerrainTileSpec(9, 1, TerrainType.RIVER),
                    TerrainTileSpec(3, 3, TerrainType.HILLS, "Shield Trench Line"),
                    TerrainTileSpec(3, 4, TerrainType.HILLS, "Shield Trench Line"),
                    TerrainTileSpec(5, 2, TerrainType.PLAINS),
                    TerrainTileSpec(5, 5, TerrainType.PLAINS),
                    TerrainTileSpec(6, 3, TerrainType.HILLS),
                    TerrainTileSpec(4, 1, TerrainType.FOREST),
                    TerrainTileSpec(4, 6, TerrainType.FOREST)
                ),
                sideASpawns = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 1, 3, isHero = true, heroId = "amir_timur", customName = "Qol Reserve (Amir Timur)"),
                    UnitSpawn("timurid_heavy_cavalry", 2, 2, customName = "Barangar Right Wing"),
                    UnitSpawn("timurid_heavy_cavalry", 2, 5, customName = "Juvangar Left Wing"),
                    UnitSpawn("sogdian_infantry", 3, 3, customName = "Entrenched Center"),
                    UnitSpawn("sogdian_infantry", 3, 4, customName = "Shield Line"),
                    UnitSpawn("steppe_horse_archer", 3, 1, customName = "Right Vanguard"),
                    UnitSpawn("steppe_horse_archer", 3, 6, customName = "Left Vanguard")
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 8, 3, isHero = true, heroId = "tokhtamysh", customName = "Khan Tokhtamysh"),
                    UnitSpawn("steppe_heavy_cavalry", 7, 3, customName = "Golden Horde Center"),
                    UnitSpawn("steppe_heavy_cavalry", 7, 4, customName = "Sarai Guard"),
                    UnitSpawn("steppe_horse_archer", 6, 1, customName = "North Flanking Tumen"),
                    UnitSpawn("steppe_horse_archer", 6, 6, customName = "South Flanking Tumen"),
                    UnitSpawn("steppe_light_cavalry", 7, 1, customName = "Kipchak Outriders"),
                    UnitSpawn("nomad_raiders", 7, 6, customName = "Volga Raiders")
                )
            ),

            // Mission 5: Battle before Delhi, 1398
            MissionDefinition(
                id = "mission_delhi_1398",
                campaignId = "campaign_timur_rise",
                orderIndex = 5,
                year = 1398,
                displayDate = "17 December 1398",
                locationName = LocalizedText("Plains before Delhi (Yamuna Approach)", "Dehli ostonalari", "Равнина перед Дели"),
                worldMapX = 0.75f,
                worldMapY = 0.67f,
                title = LocalizedText("5. Battle of Delhi (1398)", "5. Dehli jangi (1398)", "5. Битва под Дели (1398)"),
                subtitle = LocalizedText("Countering Sultan Mahmud Tughluq's 120 Armored War Elephants", "Sulton Mahmud Tug'luqning zirhli jangovar fillariga qarshi taktika", "Противостояние бронированным боевым слонам султана Махмуда Туглука"),
                weather = WeatherType.CLEAR,
                maxTurns = 14,
                sideAFaction = FactionId.TIMURID,
                sideAName = LocalizedText("Timurid Expeditionary Army", "Amir Temur qo'shini", "Армия Амира Тимура"),
                sideAHeroId = "amir_timur",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Neutralize the charging War Elephants using trenches, archers, and flanking cavalry.", "Xandaqlar, kamondorlar va qanot suvorilari yordamida jangovar fillarni to'xtating.", "Остановите боевых слонов с помощью укреплений, лучников и фланговых ударов."),
                sideBFaction = FactionId.DELHI_SULTANATE,
                sideBName = LocalizedText("Sultanate of Delhi (Mahmud Shah & Mallu Iqbal)", "Dehli sultonligi qo'shini", "Войско Делийского султаната"),
                sideBHeroId = "jalal_ad_din",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Shatter the Timurid center with Armored War Elephants and heavy cavalry.", "Zirhli jangovar fillar bilan Temur markazini yanchib o'ting.", "Прорвите центр Тимуридов ударом бронированных боевых слонов."),
                targetHex = null,
                briefing = LocalizedText(
                    "In December 1398, Sultan Mahmud Shah Tughluq and Mallu Iqbal marched out of Delhi with 120 armored war elephants whose tusks were fitted with poisoned blades. Recognizing that his steppe horses feared the elephants, Timur ordered trenches dug, chained buffaloes and shields placed before the center, iron caltrops scattered across the approach, and camels loaded with burning reeds driven toward the elephants.",
                    "1398-yil dekabrda Sulton Mahmud Tug'luq va Malluxon 120 ta zirhli jangovar fil bilan Dehli darvozalaridan chiqdi. Otlar fildan hurkishini hisobga olgan Amir Temur markaz oldidan xandaq qazdirib, temir tikanlar (uch shoxli temir qoziqlar) sochdirdi va kamondorlarga fillarning xartumi hamda haydovchilarini nishonga olishni buyurdi.",
                    "В декабре 1398 года султан Махмуд Туглук и Маллу Икбал вывели из Дели 120 боевых слонов с отравленными клинками на бивнях. Тимур приказал вырыть рвы перед центром, разбросать железные чесноки-шипы и направить на слонов верблюдов с горящим камышом."
                ),
                whatReallyHappened = LocalizedText(
                    "Turned back by the iron caltrops and fire, the panicked war elephants wheeled into their own infantry lines. The subsequent sack of Delhi caused immense human suffering and destruction recorded starkly by contemporaries, while captured war elephants were marched all the way back to Samarkand.",
                    "Temir tikanlar va olovdan hurkkan fillar ortga qaytib, o'z piyoda saflarini bosib ketdi. Jangdan keyingi talafotlar solnomalarda og'ir tarixiy fojia sifatida qayd etilgan; qo'lga kiritilgan fillar esa Samarqandga olib ketilib, keyinchalik Anqara jangida qo'llangan.",
                    "Остановленные шипами и огнем слоны повернули вспять, смяв собственные порядки. Захваченные слоны позже были приведены в Самарканд и участвовали в битве при Анкаре."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The deployment of armored war elephants, Timur's anti-elephant field fortifications, and the honest acknowledgement of the campaign's devastating civilian cost.",
                    "Tarixiy haqiqat: Zirhli fillarning qo'llanilishi, Temurning xandaq va temir tikanlar taktikasi hamda urushning og'ir oqibatlari haqidagi xolis baho.",
                    "Исторично: Применение боевых слонов, полевые заграждения Тимура и объективное отражение тяжелых последствий похода."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Field trenches and caltrops are represented as fortified hill hexes in front of the Timurid center.",
                    "O'yin moslashuvi: Xandaqlar va temir tikanlar Temur markazi oldidagi mustahkamlangan kataklar sifatida ko'rsatilgan.",
                    "Игровое упрощение: Рвы и железные шипы представлены укрепленными гексами перед центром Тимуридов."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Zafarnama & Malfuzat-i Timuri",
                        authorOrPeriod = "Yazdi (1425) / Timurid Court Chronicles",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Describes the iron caltrops, trench lines, and counter-elephant tactics outside Delhi.", "Dehli ostonalaridagi temir tikanlar va fillarga qarshi usullarni bayon qiladi.", "Описывает железные шипы, рвы и тактику борьбы со слонами под Дели.")
                    ),
                    HistoricalSource(
                        title = "Tarikh-i Mubarak Shahi",
                        authorOrPeriod = "Yahya bin Ahmad Sirhindi (c. 1434)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Indo-Persian chronicle documenting Sultan Mahmud's defense and the devastation of Delhi.", "Dehli sultonligi nuqtai nazaridan jang va uning oqibatlarini yorituvchi manba.", "Индо-персидская хроника, описывающая оборону Махмуд-шаха и последствия битвы.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Delhi's Elephants Bypassed the Caltrop Trenches?", "Agar Dehli fillari xandaqlarni chetlab o'tganida?", "Что если бы слоны Дели обошли линию рвов?"),
                whatIfDescription = LocalizedText("Hypothetical: The trench barriers are removed and Delhi deploys an extra elephant squadron on the flank.", "Faraziy holat: Markazdagi to'siqlar olib tashlanadi va Dehli qanotga qo'shimcha jangovar fil tushiradi.", "Гипотеза: Полевые укрепления убраны, а султанат вводит дополнительный отряд слонов на фланге."),
                whatIfSideBBonusUnits = listOf(UnitSpawn("war_elephants", 6, 1)),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(3, 2, TerrainType.HILLS, "Caltrop Trench"),
                    TerrainTileSpec(3, 3, TerrainType.HILLS, "Chained Shield Line"),
                    TerrainTileSpec(3, 4, TerrainType.HILLS, "Caltrop Trench"),
                    TerrainTileSpec(8, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(8, 3, TerrainType.FORTRESS, "Gates of Delhi"),
                    TerrainTileSpec(8, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(1, 0, TerrainType.RIVER),
                    TerrainTileSpec(2, 0, TerrainType.RIVER)
                ),
                sideASpawns = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 1, 3, isHero = true, heroId = "amir_timur"),
                    UnitSpawn("timurid_heavy_cavalry", 2, 1),
                    UnitSpawn("timurid_heavy_cavalry", 2, 5),
                    UnitSpawn("common_engineers", 3, 3),
                    UnitSpawn("steppe_horse_archer", 2, 2),
                    UnitSpawn("steppe_horse_archer", 2, 4)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("war_elephants", 6, 3, isHero = true, heroId = "jalal_ad_din", customName = "Mallu Iqbal's Royal Elephant"),
                    UnitSpawn("war_elephants", 5, 2),
                    UnitSpawn("war_elephants", 5, 4),
                    UnitSpawn("khwarazmian_cavalry", 6, 1),
                    UnitSpawn("common_spearmen", 6, 5),
                    UnitSpawn("common_archers", 7, 3)
                )
            ),

            // Mission 6: Battle of Ankara, 1402
            MissionDefinition(
                id = "mission_ankara_1402",
                campaignId = "campaign_timur_rise",
                orderIndex = 6,
                year = 1402,
                displayDate = "20 July 1402",
                locationName = LocalizedText("Cubuk Plain outside Ankara, Anatolia", "Anqara yaqinidagi Chubuk tekisligi", "Чубукская равнина у Анкары"),
                worldMapX = 0.27f,
                worldMapY = 0.43f,
                title = LocalizedText("6. Battle of Ankara (1402)", "6. Anqara jangi (1402)", "6. Ангорская битва (1402)"),
                subtitle = LocalizedText("Amir Timur vs. Ottoman Sultan Bayezid I 'The Thunderbolt'", "Sohibqiron Amir Temur va Usmonli sultoni Boyazid I Yildirim to'qnashuvi", "Амир Тимур против османского султана Баязида I Молниеносного"),
                weather = WeatherType.CLEAR,
                maxTurns = 15,
                sideAFaction = FactionId.TIMURID,
                sideAName = LocalizedText("Timurid Grand Army", "Amir Temur oliy qo'shini", "Великая армия Амира Тимура"),
                sideAHeroId = "amir_timur",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Hold the Cubuk stream, turn the Anatolian wings, and surround Bayezid on Catal Hill.", "Chubuk suv manbasini ushlab turing, qanotlarni yorib o'tib Boyazidni Chatal tepaligida qurshab oling.", "Удерживайте ручей Чубук, опрокиньте фланги и окружите Баязида на холме Чатал."),
                sideBFaction = FactionId.OTTOMAN,
                sideBName = LocalizedText("Ottoman Imperial Army", "Usmonli sultonligi qo'shini", "Османская султанская армия"),
                sideBHeroId = "bayezid_i",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Break through to the Cubuk water source and smash Timur's center with Janissaries & Serbian knights.", "Chubuk suv manbasiga yorib o'ting va Temur markazini tor-mor eting.", "Прорвитесь к воде ручья Чубук и сокрушите центр Тимура."),
                targetHex = null,
                briefing = LocalizedText(
                    "In the scorching heat of July 1402, two undefeated rulers met on the Cubuk plain northeast of Ankara. Timur outmarched Bayezid to occupy the heights and divert the Cubuk stream, leaving the Ottoman army parched. With Indian war elephants in his front line and his grandsons commanding the wings, Timur pressed the Ottoman flanks until Bayezid was left fighting heroically atop Catal Hill.",
                    "1402-yil 20-iyulning jazirama kunida ikki yengilmas hukmdor — Amir Temur va Boyazid Yildirim Anqara yaqinidagi Chubuk tekisligida to'qnashdi. Temur mohir manevr bilan Chubuk daryochasi suvini burib yuborib, raqibni suvsiz qoldirdi. Oldingi safda Hindistondan keltirilgan jangovar fillar va qanotlarda temuriy shahzodalar hujumga o'tib, Boyazidni Chatal tepaligida qurshovga oldi.",
                    "В знойном июле 1402 года на Чубукской равнине у Анкары сошлись два непобежденных полководца — Тимур и Баязид I. Тимур отвел русло ручья Чубук, лишив османов воды, и с боевыми слонами в первой линии сковал центр врага, замкнув кольцо вокруг холма Чатал."
                ),
                whatReallyHappened = LocalizedText(
                    "Many Anatolian beylik troops and Kara Tatar light horsemen defected to Timur's side during the battle. Though Stefan Lazarevic's heavy Serbian knights cut their way out thrice to urge Bayezid to escape, the Ottoman Sultan refused to leave his Janissaries and was captured at nightfall—beginning the Ottoman Interregnum.",
                    "Jang davomida Onado'li bekliklari va Qora tatar otliqlari Temur tomoniga o'tdi. Serb og'ir ritsarlari uch marta qurshovni yorib, Boyazidga chekinishni taklif qilsa-da, sulton o'z yanicharlari bilan Chatal tepaligida qoldi va oqshomda asir tushdi.",
                    "Анатолийские бейлики и кара-татары перешли на сторону Тимура. Несмотря на уговоры сербских рыцарей Стефана Лазаревича прорваться из кольца, Баязид остался с янычарами на холме Чатал и вечером попал в плен."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: Diversion of the Cubuk stream, presence of Indian war elephants in Timur's vanguard, Serbian heavy knights on the Ottoman wing, and Bayezid's stand on Catal Hill.",
                    "Tarixiy haqiqat: Chubuk suvining burib yuborilishi, Temur safidagi hind fillari, Boyazid qanotidagi serb zirhli ritsarlari va Chatal tepaligi mudofaasi.",
                    "Исторично: Отвод воды ручья Чубук, индийские слоны Тимура, сербские рыцари на фланге османов и последний бой на холме Чатал."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: When playing as Bayezid, the Kara Tatar wing remains loyal under your direct tactical command.",
                    "O'yin moslashuvi: Boyazid tomonida o'ynaganda barcha qanotlar sodiq qolib, o'yinchi boshqaruvida jang qiladi.",
                    "Игровое упрощение: При игре за Баязида все анатолийские корпуса остаются под полным контролем игрока."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Zafarnama",
                        authorOrPeriod = "Sharaf ad-Din Ali Yazdi (1425)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Details the diversion of the Cubuk water and the deployment of Timur's sons and grandsons.", "Chubuk suvining to'silishi va shahzodalarning qanotlardagi harakatini bayon etadi.", "Подробно описывает перекрытие ручья Чубук и действия корпусов Тимуридов.")
                    ),
                    HistoricalSource(
                        title = "Byzantine & Ottoman Chronicles (Doukas / Aşıkpaşazade)",
                        authorOrPeriod = "15th Century",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Corroborates the thirst of the Ottoman troops, the valor of the Serbian heavy cavalry, and Bayezid's capture on Catal Hill.", "Usmonli qo'shinining tashnaligi, serb ritsarlarining matonati va Chatal tepaligidagi jangni tasdiqlaydi.", "Подтверждает жажду османского войска, стойкость сербских латников и бой на холме Чатал.")
                    )
                ),
                reviewedBy = "Central Asian & Anatolian Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Bayezid Secured the Cubuk Stream First?", "Agar Boyazid Chubuk suv manbasini oldinroq egallaganida?", "Что если бы Баязид первым занял источники Чубука?"),
                whatIfDescription = LocalizedText("Hypothetical: Ottoman forces start refreshed with full morale and an additional corps of Kapikulu cavalry.", "Faraziy holat: Usmonli qo'shini suv manbasini egallagan holda yuqori ruhiyat va qo'shimcha otliqlar bilan jangni boshlaydi.", "Гипотеза: Османы начинают бой у источников воды с высоким духом и дополнительным полком сипахов."),
                whatIfSideBBonusUnits = listOf(
                    UnitSpawn("mamluk_cavalry", 6, 2),
                    UnitSpawn("ghazi_archers", 6, 5)
                ),
                mapWidth = 10,
                mapHeight = 8,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(3, 1, TerrainType.RIVER, "Diverted Cubuk Stream"),
                    TerrainTileSpec(3, 2, TerrainType.RIVER),
                    TerrainTileSpec(3, 3, TerrainType.ROAD, "Cubuk Wellhead"),
                    TerrainTileSpec(3, 4, TerrainType.RIVER),
                    TerrainTileSpec(7, 3, TerrainType.HILLS, "Catal Hill"),
                    TerrainTileSpec(7, 4, TerrainType.HILLS, "Catal Ridge"),
                    TerrainTileSpec(5, 1, TerrainType.HILLS),
                    TerrainTileSpec(5, 6, TerrainType.DESERT)
                ),
                sideASpawns = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 1, 3, isHero = true, heroId = "amir_timur"),
                    UnitSpawn("war_elephants", 3, 3),
                    UnitSpawn("timurid_heavy_cavalry", 2, 1),
                    UnitSpawn("timurid_heavy_cavalry", 2, 5),
                    UnitSpawn("steppe_horse_archer", 2, 2),
                    UnitSpawn("steppe_horse_archer", 2, 4),
                    UnitSpawn("sogdian_infantry", 1, 4)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("mamluk_cavalry", 7, 3, isHero = true, heroId = "bayezid_i", customName = "Sultan Bayezid I"),
                    UnitSpawn("european_knights", 6, 1, customName = "Serbian Heavy Knights"),
                    UnitSpawn("saracen_infantry", 7, 4, customName = "Janissary Guard"),
                    UnitSpawn("ghazi_archers", 6, 3),
                    UnitSpawn("ghazi_archers", 6, 4),
                    UnitSpawn("steppe_horse_archer", 6, 6, customName = "Anatolian Wing"),
                    UnitSpawn("common_spearmen", 7, 2)
                )
            ),

            // Mission 7: Epilogue — Samarkand, Gur-e-Amir & Ulugh Beg's Observatory
            MissionDefinition(
                id = "mission_samarkand_epilogue",
                campaignId = "campaign_timur_rise",
                orderIndex = 7,
                year = 1420,
                displayDate = "1405–1428 CE (Timurid Renaissance)",
                locationName = LocalizedText("Samarkand & Kuhak Observatory Hill", "Samarqand va Ko'hak rasadxonasi tepaligi", "Самарканд и холм обсерватории Кухак"),
                worldMapX = 0.64f,
                worldMapY = 0.48f,
                title = LocalizedText("7. Epilogue: Guardian of Samarkand", "7. Xotima: Samarqand va Ulug'bek rasadxonasi", "7. Эпилог: Страж Самарканда и обсерватория Улугбека"),
                subtitle = LocalizedText("Mirzo Ulugh Beg Defends the Zarafshan Capital and Kuhak Hill", "Mirzo Ulug'bekning Samarqand va Ko'hak tepaligini ko'chmanchi bosqinidan himoya qilishi", "Мирзо Улугбек защищает столицу и холм Кухак"),
                weather = WeatherType.NIGHT,
                maxTurns = 12,
                sideAFaction = FactionId.TIMURID,
                sideAName = LocalizedText("Samarkand Garrison of Mirzo Ulugh Beg", "Mirzo Ulug'bekning Samarqand qo'shini", "Самаркандское войско Мирзо Улугбека"),
                sideAHeroId = "ulugh_beg",
                sideAObjectiveType = ObjectiveType.PROTECT_HERO,
                sideAObjectiveDesc = LocalizedText("Protect Mirzo Ulugh Beg on Kuhak Observatory Hill (2,3) and repel the raiders.", "Ko'hak tepaligida (2,3) Mirzo Ulug'bekni asrang va bosqinchilarni qaytaring.", "Защитите Мирзо Улугбека на холме Кухак (2,3) и отразите набег."),
                sideBFaction = FactionId.CHAGATAI,
                sideBName = LocalizedText("Steppe Frontier Raiders", "Chegara bosqinchilari", "Степной корпус"),
                sideBHeroId = "tokhtamysh",
                sideBObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideBObjectiveDesc = LocalizedText("Seize Kuhak Hill (2,3) before dawn.", "Tong otguncha Ko'hak tepaligini (2,3) egallang.", "Захватите холм Кухак (2,3) до рассвета."),
                targetHex = HexCoord(2, 3),
                briefing = LocalizedText(
                    "Following Amir Timur's death at Otrar in February 1405 and his burial beneath the fluted azure dome of Gur-e-Amir in Samarkand, his grandson Mirzo Ulugh Beg transformed the city into an unrivalled beacon of astronomy and mathematics. In this special night engagement around Kuhak Hill beside the Zarafshan River, use Ulugh Beg's calculated trajectory volleys to defend the Observatory and Registan approaches.",
                    "1405-yil fevralda Amir Temur O'trorda vafot etib, Samarqanddagi Go'ri Amir maqbarasiga dafn etilgach, uning nabirasi Mirzo Ulug'bek poytaxtni jahon ilm-fan markaziga aylantirdi. Zarafshon bo'yidagi Ko'hak rasadxonasi tepaligi atrofida kechadigan ushbu maxsus tungi jangda Ulug'bekning aniq hisob-kitobli manjaniq zarbalari bilan Samarqand ostonalarini himoya qiling.",
                    "После кончины Тимура в Отраре в 1405 году и его погребения в мавзолее Гур-Эмир, его внук Мирзо Улугбек превратил Самарканд в столицу астрономии и математики. В этом особом ночном сражении у холма Кухак используйте выверенные залпы Улугбека для защиты обсерватории."
                ),
                whatReallyHappened = LocalizedText(
                    "Under Ulugh Beg (r. 1409–1449), Samarkand's scholars Jamshid al-Kashi and Ali Qushji compiled the Zij-i Sultani star catalog of 1,018 stars and determined the Earth's axial tilt as 23°30'17\"—a scientific legacy that endured across Asia and Europe for centuries.",
                    "Mirzo Ulug'bek davrida G'iyosiddin Jamshid Koshiy, Qozizoda Rumiy va Ali Qushchi kabi allomalar 1018 ta yulduzni o'z ichiga olgan «Ziji jadidi Ko'ragoniy» asarini yaratib, yil uzunligini zamonaviy hisobdan atigi bir necha soniya farq bilan aniqladilar.",
                    "Под руководством Улугбека, Кази-заде Руми, ал-Каши и Али Кушчи был создан каталог 1018 звезд «Зидж-и Гурагани», ставший вершиной астрономии эпохи Возрождения на Востоке."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The geography of Kuhak (Chupan-Ata) Hill beside the Obi-Rahmat canal and Zarafshan river, and Ulugh Beg's dual role as Timurid governor and astronomer.",
                    "Tarixiy haqiqat: Obi Rahmat arig'i va Zarafshon bo'yidagi Ko'hak (Cho'ponota) tepaligi joylashuvi hamda Ulug'bekning olim va hukmdor sifatidagi faoliyati.",
                    "Исторично: Расположение холма Кухак у канала Оби-Рахмат и Зеравшана, а также деятельность Улугбека как правителя и ученого."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Combines Ulugh Beg's frontier defense expeditions into a dramatic night battle directly below the Kuhak Observatory.",
                    "O'yin moslashuvi: Ulug'bekning chegara mudofaa yurishlari bevosita Ko'hak rasadxonasi etagidagi tungi jang sifatida taqdim etildi.",
                    "Игровое упрощение: Оборонительные походы Улугбека объединены в ночное сражение у подножия обсерватории."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Zij-i Sultani (Zij-i Jadid-i Kuragani)",
                        authorOrPeriod = "Mirzo Ulugh Beg, Qadi Zada al-Rumi, al-Kashi & Ali Qushji (1437)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Primary scientific treatise produced at the Samarkand Observatory.", "Samarqand rasadxonasida yaratilgan jahonshumul astronomik asar.", "Выдающийся астрономический труд Самаркандской школы.")
                    ),
                    HistoricalSource(
                        title = "Matla-i Sa'dayn wa Majma-i Bahrayn",
                        authorOrPeriod = "Abd al-Razzaq Samarqandi (c. 1470)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Primary Timurid chronicle of Shah Rukh and Ulugh Beg's governance of Samarkand.", "Ulug'bek davridagi Samarqand tarixi bo'yicha asosiy manba.", "Главная тимуридская хроника правления Шахруха и Улугбека.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Babur Joined His Forebears at Samarkand?", "Agar Mirzo Bobur Samarqand mudofaasiga qo'shilganida?", "Что если бы Бабур сражался рядом с Улугбеком у Самарканда?"),
                whatIfDescription = LocalizedText("Hypothetical across-time Timurid scenario: Young Prince Babur brings Fergana cavalry to reinforce Kuhak Hill.", "Faraziy davrlararo senariy: Yosh shahzoda Bobur Farg'ona suvorilari bilan Samarqand mudofaasiga yordamga keladi.", "Гипотетический сценарий: Молодой Бабур приводит ферганскую конницу к холму Кухак."),
                whatIfSideABonusUnits = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 2, 5, isHero = true, heroId = "babur")
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(2, 3, TerrainType.FORTRESS, "Kuhak Observatory"),
                    TerrainTileSpec(1, 2, TerrainType.CITY_WALLS, "Samarkand Turquoise Gate"),
                    TerrainTileSpec(1, 3, TerrainType.CITY_WALLS),
                    TerrainTileSpec(1, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(4, 0, TerrainType.RIVER, "Zarafshan River"),
                    TerrainTileSpec(4, 1, TerrainType.RIVER),
                    TerrainTileSpec(4, 2, TerrainType.ROAD, "Zarafshan Arch Bridge"),
                    TerrainTileSpec(4, 3, TerrainType.RIVER),
                    TerrainTileSpec(4, 4, TerrainType.ROAD, "Obi-Rahmat Crossing"),
                    TerrainTileSpec(4, 5, TerrainType.RIVER),
                    TerrainTileSpec(4, 6, TerrainType.RIVER),
                    TerrainTileSpec(3, 3, TerrainType.HILLS)
                ),
                sideASpawns = listOf(
                    UnitSpawn("timurid_heavy_cavalry", 2, 3, isHero = true, heroId = "ulugh_beg"),
                    UnitSpawn("central_asian_siege_crew", 2, 2),
                    UnitSpawn("sogdian_infantry", 3, 2),
                    UnitSpawn("sogdian_infantry", 3, 4),
                    UnitSpawn("common_archers", 2, 4),
                    UnitSpawn("timurid_heavy_cavalry", 3, 3)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 8, 3, isHero = true, heroId = "tokhtamysh"),
                    UnitSpawn("steppe_horse_archer", 6, 2),
                    UnitSpawn("steppe_horse_archer", 6, 4),
                    UnitSpawn("steppe_light_cavalry", 7, 1),
                    UnitSpawn("nomad_raiders", 7, 5),
                    UnitSpawn("common_spearmen", 6, 3)
                )
            )
        )
    )

    val campaignMongolKhwarazm = CampaignDefinition(
        id = "campaign_mongol_khwarazm",
        title = LocalizedText("Campaign II: Storm in Khwarazm", "II Yurish: Xorazm bo'roni (1219–1221)", "Кампания II: Буря в Хорезме (1219–1221)"),
        subtitle = LocalizedText("Otrar, Bukhara, Samarkand, Urgench, Parwan & the Indus (1219–1221)", "O'tror, Buxoro, Samarqand, Gurganj, Parvon va Sind daryosi (1219–1221)", "Отрар, Бухара, Самарканд, Гургандж, Парван и река Инд (1219–1221)"),
        eraRange = "1219–1221 CE",
        century = 13,
        description = LocalizedText(
            "Command the Khwarazmian defenders under Inalchuq, Temur Malik, and Sultan Jalal ad-Din Mangburni—or lead the Mongol tumens of Genghis Khan and Subutai across the Kyzylkum and Hindu Kush.",
            "Inolchiq Qayirxon, Temur Malik va Sulton Jaloliddin Manguberdi boshchiligida Vatan himoyasiga turing yoki Chingizxon va Subutoy tumanlarini boshqaring.",
            "Возглавьте оборону Хорезма под началом Каир-хана, Тимур-Малика и Джалал ад-Дина Мангуберди или ведите тумены Чингисхана и Субэдэя."
        ),
        musicThemeId = "theme_steppe_drums",
        primaryColorHex = 0xFF136F63,
        missions = listOf(
            // Mission 1: Fall of Otrar, 1219
            MissionDefinition(
                id = "mission_otrar_1219",
                campaignId = "campaign_mongol_khwarazm",
                orderIndex = 1,
                year = 1219,
                displayDate = "Autumn 1219 – Feb 1220",
                locationName = LocalizedText("Otrar Citadel on the Syr Darya", "Sirdaryo bo'yidagi O'tror qal'asi", "Крепость Отрар на Сырдарье"),
                worldMapX = 0.68f,
                worldMapY = 0.39f,
                title = LocalizedText("1. Siege of Otrar (1219)", "1. O'tror mudofaasi (1219)", "1. Оборона Отрара (1219)"),
                subtitle = LocalizedText("Governor Inalchuq Kair Khan's Five-Month Defiance", "Qayirxon (Inolchiq) boshchiligidagi besh oylik qahramonona mudofaa", "Пятимесячная оборона крепости под началом Иналчука Каир-хана"),
                weather = WeatherType.SNOW,
                maxTurns = 13,
                sideAFaction = FactionId.KHWARAZMIAN,
                sideAName = LocalizedText("Otrar Citadel Garrison (Kair Khan)", "O'tror garnizoni (Qayirxon)", "Гарнизон Отрара (Каир-хан)"),
                sideAHeroId = "muhammad_khwarazm",
                sideAObjectiveType = ObjectiveType.SURVIVE_TURNS,
                sideAObjectiveDesc = LocalizedText("Hold the Otrar Ark Citadel (2,3) for 9 turns or destroy the Mongol siege crews.", "O'tror arkini (2,3) 9 yurish davomida ushlab turing yoki qamal qurollarini yo'q qiling.", "Удерживайте цитадель Отрара (2,3) 9 ходов или разбейте осадные расчеты."),
                sideBFaction = FactionId.MONGOL,
                sideBName = LocalizedText("Chagatai & Ogedei's Siege Tumens", "Chig'atoy va O'qtoy qamal tumanlari", "Осадные тумены Чагатая и Угэдэя"),
                sideBHeroId = "genghis_subutai",
                sideBObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideBObjectiveDesc = LocalizedText("Breach the outer ramparts of Otrar and capture the Ark Citadel (2,3).", "O'tror devorlarini yorib o'ting va bosh qal'ani (2,3) egallang.", "Пробейте стены Отрара и захватите цитадель (2,3)."),
                targetHex = HexCoord(2, 3),
                surviveTurnsTarget = 9,
                briefing = LocalizedText(
                    "In 1218, Otrar's governor Inalchuq (Kair Khan) arrested and executed a 450-merchant caravan sent by Genghis Khan, suspecting spies among them. When Shah Muhammad II executed the Mongol envoy sent to demand Inalchuq's extradition, Genghis Khan marched west. Kair Khan fortified Otrar and held out for five grueling winter months—even throwing roof tiles from the citadel after arrows ran out.",
                    "1218-yilda O'tror hokimi Inolchiq (Qayirxon) Chingizxon yuborgan karvonni josuslikda gumon qilib qo'lga oldi. Elchilar o'ldirilgach, 1219-yil kuzida mo'g'ul tumanlari O'trorni qamal qildi. Qayirxon va shahar himoyachilari besh oy davomida qahramonona jang qilib, o'q-yoy tugaganda hatto qal'a g'ishtlarini dushman ustiga uloqtirib kurashdilar.",
                    "В 1218 году правитель Отрара Иналчук Каир-хан задержал караван Чингисхана по подозрению в шпионаже. Осенью 1219 года тумены Чагатая и Угэдэя осадили Отрар. Каир-хан оборонял город и внутреннюю цитадель пять месяцев до последней стрелы и кирпича."
                ),
                whatReallyHappened = LocalizedText(
                    "After five months, a subordinate commander named Qaracha opened the Sufi Gate in hope of mercy (he was executed by the Mongols for disloyalty). Kair Khan fought on atop the inner Ark for another month with 20,000 men until captured alive.",
                    "Besh oylik qamaldan so'ng Qoracha Xojib ismli lashkarboshi darvozani ochib berdi (xiyonati uchun mo'g'ullar tomonidan qatl etildi). Qayirxon esa ichki Arkda yana bir oy so'nggi nafasigacha jang qildi.",
                    "После предательства военачальника Караджи, открывшего ворота, Каир-хан еще месяц держался во внутренней цитадели."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The double fortification of Otrar (outer wall and inner Ark), the winter siege conditions, and Qaracha's gate sector.",
                    "Tarixiy haqiqat: O'trorning tashqi devori va ichki Ark qo'rg'oni, qishki qamal sharoiti va tarixiy manbalar ma'lumoti.",
                    "Исторично: Двойной пояс укреплений Отрара, зимние условия осады и хроника событий."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Playing as Khwarazm lets you prevent Qaracha's defection and launch sorties against the siege engines.",
                    "O'yin moslashuvi: Xorazm tomonida o'ynaganda darvoza xiyonatini oldini olib, manjaniqlarga qarshi hamla uyushtirish mumkin.",
                    "Игровое упрощение: Играя за гарнизон Отрара, вы можете сорвать сдачу ворот и уничтожить осадные орудия."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Tarikh-i Jahangushay (History of the World Conqueror)",
                        authorOrPeriod = "Ata-Malik Juvayni (c. 1260)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Detailed Persian account of the Otrar caravan incident and Kair Khan's five-month defense.", "O'tror voqeasi va Qayirxonning besh oylik mudofaasi haqidagi asosiy tarixiy manba.", "Подробный рассказ об отрарском инциденте и пятимесячной обороне Каир-хана.")
                    ),
                    HistoricalSource(
                        title = "Sirat al-Sultan Jalal ad-Din Mankburni",
                        authorOrPeriod = "Shihab al-Din Muhammad al-Nasawi (1241)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Written by Jalal ad-Din's personal secretary, offering inside perspective on the Khwarazmian court.", "Jaloliddinning shaxsiy kotibi Nasaviy tomonidan yozilgan birlamchi tarixiy asar.", "Труд личного секретаря Джалал ад-Дина ан-Насави о событиях в Хорезме.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Shah Muhammad II Marched His Field Army to Relieve Otrar?", "Agar Xorazmshoh Muhammad asosiy qo'shin bilan O'trorga yordamga kelganida?", "Что если бы хорезмшах Мухаммад привел полевую армию к Отрару?"),
                whatIfDescription = LocalizedText("Hypothetical: Heavy Khwarazmian royal cavalry reinforces Otrar's flanks to crush the siege lines.", "Faraziy holat: Xorazmshohning xos og'ir otliqlari qamalchilarga qanotdan zarba berish uchun yetib keladi.", "Гипотеза: Гвардейская хорезмийская конница атакует осадные порядки с фланга."),
                whatIfSideABonusUnits = listOf(
                    UnitSpawn("khwarazmian_cavalry", 1, 1),
                    UnitSpawn("khwarazmian_cavalry", 1, 5)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.STEPPE,
                terrainOverrides = listOf(
                    TerrainTileSpec(2, 3, TerrainType.FORTRESS, "Otrar Ark Citadel"),
                    TerrainTileSpec(3, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(3, 3, TerrainType.CITY_WALLS, "Sufi Gate"),
                    TerrainTileSpec(3, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(2, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(2, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(0, 0, TerrainType.RIVER, "Syr Darya"),
                    TerrainTileSpec(0, 1, TerrainType.RIVER),
                    TerrainTileSpec(0, 2, TerrainType.RIVER)
                ),
                sideASpawns = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 3, isHero = true, heroId = "muhammad_khwarazm", customName = "Inalchuq Kair Khan"),
                    UnitSpawn("sogdian_infantry", 3, 2),
                    UnitSpawn("sogdian_infantry", 3, 3),
                    UnitSpawn("sogdian_infantry", 3, 4),
                    UnitSpawn("common_archers", 2, 2),
                    UnitSpawn("common_archers", 2, 4)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_horse_archer", 8, 3, isHero = true, heroId = "genghis_subutai"),
                    UnitSpawn("common_trebuchet", 6, 2),
                    UnitSpawn("common_catapult", 6, 4),
                    UnitSpawn("steppe_heavy_cavalry", 7, 3),
                    UnitSpawn("steppe_light_cavalry", 7, 1),
                    UnitSpawn("common_engineers", 5, 3),
                    UnitSpawn("steppe_horse_archer", 7, 5)
                )
            ),

            // Mission 2: Siege of Bukhara, 1220
            MissionDefinition(
                id = "mission_bukhara_1220",
                campaignId = "campaign_mongol_khwarazm",
                orderIndex = 2,
                year = 1220,
                displayDate = "February 1220",
                locationName = LocalizedText("Bukhara & the Ark Citadel", "Buxoro va Ark qal'asi", "Бухара и цитадель Арк"),
                worldMapX = 0.58f,
                worldMapY = 0.49f,
                title = LocalizedText("2. Siege of Bukhara (1220)", "2. Buxoro mudofaasi (1220)", "2. Осада Бухары (1220)"),
                subtitle = LocalizedText("The Kyzylkum Desert Flanking March & Breakout at the Oxus", "Qizilqum orqali kutilmagan yurish va Ark qal'asi mudofaasi", "Переход через Кызылкум и оборона цитадели Арк"),
                weather = WeatherType.SANDSTORM,
                maxTurns = 12,
                sideAFaction = FactionId.KHWARAZMIAN,
                sideAName = LocalizedText("Bukhara Garrison (Inanch Khan & Kok-Khan)", "Buxoro garnizoni (Inonchxon va Ko'kxon)", "Гарнизон Бухары (Инанч-хан и Кок-хан)"),
                sideAHeroId = "muhammad_khwarazm",
                sideAObjectiveType = ObjectiveType.BREAK_SIEGE,
                sideAObjectiveDesc = LocalizedText("Defend the Ark Citadel (2,3) or break through the encirclement to the east.", "Ark qal'asini (2,3) himoya qiling yoki qurshovni yorib o'ting.", "Удержите цитадель Арк (2,3) или прорвите кольцо окружения."),
                sideBFaction = FactionId.MONGOL,
                sideBName = LocalizedText("Genghis Khan's Kyzylkum Column", "Chingizxonning Qizilqum qo'shini", "Западная колонна Чингисхана"),
                sideBHeroId = "genghis_subutai",
                sideBObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideBObjectiveDesc = LocalizedText("Emerge from the Kyzylkum sands and capture the Bukhara Ark Citadel (2,3).", "Qizilqum qumliklaridan chiqib, Buxoro Arkini (2,3) egallang.", "Выйдите из песков Кызылкума и возьмите цитадель Арк (2,3)."),
                targetHex = HexCoord(2, 3),
                surviveTurnsTarget = 8,
                briefing = LocalizedText(
                    "While Shah Muhammad II expected attacks only along the Syr Darya, Genghis Khan and Subutai led their main tumens across 300 miles of the supposedly impassable Kyzylkum desert—guided by local oasis wells—emerging behind Bukhara in February 1220. While Inanch Khan attempted a night cavalry breakout toward the Amu Darya, 400 defenders inside the Ark Citadel fought to the last man for twelve days.",
                    "Xorazmshoh asosiy zarbani faqat Sirdaryo bo'yida kutayotgan paytda, Chingizxon va Subutoy o'tib bo'lmas deb hisoblangan Qizilqum cho'lini kesib o'tib, 1220-yil fevralda kutilmaganda Buxoro darvozalari oldida paydo bo'ldi. Inonchxon tunda qurshovni yorib chiqishga uringan bo'lsa, Ark qal'asidagi 400 nafar fidoyi jangchi o'n ikki kun davomida so'nggi nafasigacha kurashdi.",
                    "Пока хорезмшах ждал удара на Сырдарье, Чингисхан и Субэдэй совершили скрытый переход через пустыню Кызылкум и в феврале 1220 года вышли к Бухаре с запада. Четыреста защитников цитадели Арк сражались двенадцать дней до последнего воина."
                ),
                whatReallyHappened = LocalizedText(
                    "Though much of Bukhara's wooden outer quarters burned during the assault on the Ark, the towering brick Kalyan Minaret (built 1127) so impressed Genghis Khan when his helmet fell off looking up at its crown that he ordered the minaret spared.",
                    "Ark qamali vaqtida shaharning ko'p qismi yong'inda zarar ko'rgan bo'lsa-da, 1127-yilda qurilgan ulug'vor Minorai Kalon omon qoldi va bugungi kungacha Buxoro ramzi bo'lib turibdi.",
                    "Несмотря на тяжелые разрушения при штурме Арка, величественный минарет Калян (1127 г.) уцелел и сохранился до наших дней."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The Kyzylkum desert approach from the west/northwest, the separation between the outer city and the Ark Citadel, and Inanch Khan's breakout sortie.",
                    "Tarixiy haqiqat: Qizilqum cho'li orqali g'arbdan kirib kelish, Ark qal'asi mudofaasi va Inonchxonning yorib chiqish harakati.",
                    "Исторично: Выход из песков Кызылкума с запада, оборона цитадели Арк и прорыв отряда Инанч-хана."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Condenses the desert approach, the breakout battle, and the Ark assault into a single tactical map.",
                    "O'yin moslashuvi: Cho'ldan chiqish, yorib o'tish va Ark jangi bitta taktik xaritada birlashtirildi.",
                    "Игровое упрощение: Выход из пустыни, попытка прорыва и штурм Арка объединены на одной карте."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Al-Kamil fi al-Tarikh (The Complete History)",
                        authorOrPeriod = "Ibn al-Athir (c. 1231)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Contemporary Arabic chronicle documenting the shock of the Kyzylkum maneuver and the defense of the Ark.", "Zamondosh arab tarixchisi Ibn al-Asirning Qizilqum yurishi va Ark mudofaasi haqidagi ma'lumotlari.", "Современная событиям арабская хроника Ибн аль-Асира об осаде Бухары.")
                    ),
                    HistoricalSource(
                        title = "Tarikh-i Jahangushay",
                        authorOrPeriod = "Ata-Malik Juvayni (c. 1260)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Describes the desert route via Zarnuq and Nurata to Bukhara.", "Zarnuq va Nurota orqali Buxoroga o'tgan cho'l yo'lini aniq ko'rsatadi.", "Описывает маршрут через Зарнук и Нурату к стенам Бухары.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Bukhara Scouts Detected the Kyzylkum Column at Nurata?", "Agar Buxoro kuzatuvchilari Nurota yaqinida cho'l yurishini payqaganida?", "Что если бы дозоры Бухары обнаружили колонну еще у Нураты?"),
                whatIfDescription = LocalizedText("Hypothetical: Clear weather and an ambush line of Khwarazmian cavalry catch the Mongol column emerging from the dunes.", "Faraziy holat: Buxoro suvorilari cho'ldan chiqib kelayotgan dushmanni pistirmada kutib oladi.", "Гипотеза: Хорезмийская конница встречает вышедшие из песков тумены подготовленной засадой."),
                whatIfWeatherOverride = WeatherType.CLEAR,
                whatIfSideABonusUnits = listOf(
                    UnitSpawn("khwarazmian_cavalry", 4, 2),
                    UnitSpawn("steppe_horse_archer", 4, 5)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.DESERT,
                terrainOverrides = listOf(
                    TerrainTileSpec(2, 3, TerrainType.FORTRESS, "Bukhara Ark Citadel"),
                    TerrainTileSpec(3, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(3, 3, TerrainType.CITY_WALLS),
                    TerrainTileSpec(3, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(1, 3, TerrainType.PLAINS, "Kalyan Square"),
                    TerrainTileSpec(4, 3, TerrainType.ROAD, "Zarafshan Oasis Road"),
                    TerrainTileSpec(5, 3, TerrainType.ROAD),
                    TerrainTileSpec(6, 2, TerrainType.HILLS)
                ),
                sideASpawns = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 3, isHero = true, heroId = "muhammad_khwarazm", customName = "Ark Commander Kok-Khan"),
                    UnitSpawn("khwarazmian_cavalry", 3, 2, customName = "Inanch Khan's Cavalry"),
                    UnitSpawn("sogdian_infantry", 3, 3),
                    UnitSpawn("sogdian_infantry", 3, 4),
                    UnitSpawn("common_archers", 1, 3)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_horse_archer", 8, 3, isHero = true, heroId = "genghis_subutai"),
                    UnitSpawn("steppe_heavy_cavalry", 7, 2),
                    UnitSpawn("steppe_heavy_cavalry", 7, 4),
                    UnitSpawn("steppe_horse_archer", 6, 1),
                    UnitSpawn("steppe_light_cavalry", 6, 5),
                    UnitSpawn("common_catapult", 7, 3)
                )
            ),

            // Mission 3: Siege of Samarkand, 1220
            MissionDefinition(
                id = "mission_samarkand_1220",
                campaignId = "campaign_mongol_khwarazm",
                orderIndex = 3,
                year = 1220,
                displayDate = "March 1220",
                locationName = LocalizedText("Samarkand (Afrasiab Ramparts & Lead Aqueduct)", "Samarqand (Afrosiyob va Qo'rg'oshin nov)", "Самарканд (Афрасиаб и Свинцовый акведук)"),
                worldMapX = 0.64f,
                worldMapY = 0.48f,
                title = LocalizedText("3. Siege of Samarkand (1220)", "3. Samarqand mudofaasi (1220)", "3. Осада Самарканда (1220)"),
                subtitle = LocalizedText("War Elephant Sortie & Alp Er Khan's Breakthrough on Afrasiab", "Afrosiyob devorlaridan jangovar fillar hamlasi va Alp Erxon jasorati", "Вылазка боевых слонов и прорыв Алп-Эр-хана на Афрасиабе"),
                weather = WeatherType.CLEAR,
                maxTurns = 13,
                sideAFaction = FactionId.KHWARAZMIAN,
                sideAName = LocalizedText("Samarkand Garrison (Tughai Khan & Alp Er Khan)", "Samarqand garnizoni (To'g'ayxon va Alp Erxon)", "Гарнизон Самарканда (Тугай-хан и Алп-Эр-хан)"),
                sideAHeroId = "jalal_ad_din",
                sideAObjectiveType = ObjectiveType.SURVIVE_TURNS,
                sideAObjectiveDesc = LocalizedText("Protect the Lead Aqueduct (Juy-i Arziz) and Afrasiab Citadel (2,3) for 9 turns.", "Qo'rg'oshin nov va Afrosiyob arkini (2,3) 9 yurish davomida himoya qiling.", "Удерживайте Свинцовый акведук и цитадель Афрасиаба (2,3) 9 ходов."),
                sideBFaction = FactionId.MONGOL,
                sideBName = LocalizedText("Combined Mongol Armies at Kok-Saray", "Ko'ksaroy qarorgohidagi Mo'g'ul qo'shini", "Объединенные тумены у Кок-Сарая"),
                sideBHeroId = "genghis_subutai",
                sideBObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideBObjectiveDesc = LocalizedText("Lure the Samarkand sortie into a feigned retreat and capture the Afrasiab Citadel (2,3).", "Yolg'on chekinish bilan garnizonni pistirmaga torting va Afrosiyob arkini (2,3) egallang.", "Заманите вылазку гарнизона в засаду и возьмите цитадель Афрасиаба (2,3)."),
                targetHex = HexCoord(2, 3),
                surviveTurnsTarget = 9,
                briefing = LocalizedText(
                    "In March 1220, the Mongol columns from Otrar and Bukhara converged on Samarkand, then the capital of Shah Muhammad II, defended by a deep moat, the high walls of Afrasiab, and 20 war elephants. On the third day, the defenders launched a massive sortie that drove back the Mongol vanguard—until a classic steppe feigned retreat closed around the pursuing infantry. Even after the outer gates opened, Alp Er Khan cut his way out of the citadel with 1,000 horsemen.",
                    "1220-yil martda O'tror va Buxorodan kelgan mo'g'ul qo'shinlari chuqur xandaq, Afrosiyobning baland devorlari va 20 ta jangovar fil bilan himoyalangan Samarqandni o'rab oldi. Qamalning uchinchi kuni shahar himoyachilari shiddatli qarshi hujumga o'tdi, ammo yolg'on chekinish pistirmasiga tushib qoldi. Shunga qaramay, Alp Erxon ming nafar saralangan yigit bilan ichki arkdan qurshovni yorib chiqib ketishga muvaffaq bo'ldi.",
                    "В марте 1220 года тумены сошлись у стен Самарканда, защищенного укреплениями Афрасиаба и 20 боевыми слонами. На третий день гарнизон предпринял мощную вылазку, попавшую в засаду притворного отступления, а позже Алп-Эр-хан с тысячей воинов прорвался сквозь кольцо осады."
                ),
                whatReallyHappened = LocalizedText(
                    "The destruction of the Lead Aqueduct (Juy-i Arziz) cut off water to the high plateau of Afrasiab, forcing the city's center to shift south in later decades—until Amir Timur rebuilt Samarkand into a grand imperial capital.",
                    "Qo'rg'oshin nov (Jo'yi Arziz) buzilgach, qadimiy Afrosiyob tepaligi suvsiz qoldi va shahar hayoti janubga ko'chdi; XIV asrda esa Amir Temur Samarqandni mislsiz darajada qayta tikladi.",
                    "Разрушение Свинцового акведука (Джуй-и-Арзиз) лишило воды плато Афрасиаб, и позже при Амире Тимуре новый центр Самарканда вырос южнее."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: Presence of 20 war elephants in Khwarazmian Samarkand, the third-day sortie ambush, Alp Er Khan's heroic breakout, and the Juy-i Arziz aqueduct.",
                    "Tarixiy haqiqat: Samarqandda 20 ta jangovar fil bo'lgani, uchinchi kundagi hamla, Alp Erxonning yorib chiqishi va Jo'yi Arziz suv inshooti.",
                    "Исторично: Наличие 20 боевых слонов в Самарканде, вылазка на третий день, прорыв Алп-Эр-хана и акведук Джуй-и-Арзиз."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Gives the Khwarazmian player direct control over the elephant sortie so you can avoid the overextension trap.",
                    "O'yin moslashuvi: Xorazm tomonida o'ynaganda jangovar fillar hamlasini pistirmaga tushirmasdan to'g'ri boshqarish imkonini beradi.",
                    "Игровое упрощение: Игрок за Самарканд может грамотно использовать слонов, не попадая в ловушку ложного отступления."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Tarikh-i Jahangushay",
                        authorOrPeriod = "Ata-Malik Juvayni (c. 1260)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Records the 20 war elephants at Samarkand, the ambush of the sortie, and the destruction of the Lead Aqueduct.", "Samarqanddagi 20 ta jangovar fil, uchinchi kunlik pistirma va Qo'rg'oshin nov haqida yozadi.", "Описывает 20 слонов в Самарканде, засаду на третий день и разрушение Свинцового акведука.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If the Samarkand Garrison Held the Aqueduct and Coordinated with Jalal ad-Din?", "Agar Jo'yi Arziz saqlab qolinib, Jaloliddin yordamga yetib kelganida?", "Что если бы акведук удержали до подхода Джалал ад-Дина?"),
                whatIfDescription = LocalizedText("Hypothetical: Reinforcements arrive along the Zarafshan while the war elephants smash the siege engines.", "Faraziy holat: Zarafshon bo'ylab Jaloliddin suvorilari yetib kelib, manjaniqlarni yakson qiladi.", "Гипотеза: Вдоль Зеравшана подходит конница Джалал ад-Дина, поддерживая удар боевых слонов."),
                whatIfSideABonusUnits = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 1),
                    UnitSpawn("war_elephants", 4, 3)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(2, 3, TerrainType.FORTRESS, "Afrasiab Citadel"),
                    TerrainTileSpec(3, 2, TerrainType.CITY_WALLS, "Namazgah Gate"),
                    TerrainTileSpec(3, 3, TerrainType.CITY_WALLS, "Iron Gate"),
                    TerrainTileSpec(3, 4, TerrainType.CITY_WALLS, "Lead Aqueduct (Juy-i Arziz)"),
                    TerrainTileSpec(4, 1, TerrainType.RIVER, "Zarafshan Channel"),
                    TerrainTileSpec(5, 1, TerrainType.RIVER),
                    TerrainTileSpec(5, 2, TerrainType.HILLS),
                    TerrainTileSpec(5, 4, TerrainType.HILLS)
                ),
                sideASpawns = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 3, isHero = true, heroId = "jalal_ad_din", customName = "Alp Er Khan"),
                    UnitSpawn("war_elephants", 3, 3, customName = "Samarkand War Elephants"),
                    UnitSpawn("sogdian_infantry", 3, 2),
                    UnitSpawn("sogdian_infantry", 3, 4),
                    UnitSpawn("common_archers", 2, 2),
                    UnitSpawn("khwarazmian_cavalry", 2, 4)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_horse_archer", 8, 3, isHero = true, heroId = "genghis_subutai"),
                    UnitSpawn("steppe_heavy_cavalry", 7, 2),
                    UnitSpawn("steppe_heavy_cavalry", 7, 4),
                    UnitSpawn("steppe_light_cavalry", 5, 3, customName = "Feigned Retreat Bait"),
                    UnitSpawn("common_trebuchet", 7, 3),
                    UnitSpawn("steppe_horse_archer", 6, 1),
                    UnitSpawn("steppe_horse_archer", 6, 5)
                )
            ),

            // Mission 4: Siege of Urgench (Gurganj), 1221
            MissionDefinition(
                id = "mission_urgench_1221",
                campaignId = "campaign_mongol_khwarazm",
                orderIndex = 4,
                year = 1221,
                displayDate = "Jan–April 1221",
                locationName = LocalizedText("Gurganj (Old Urgench), Amu Darya Delta", "Gurganj (Ko'hna Urganch), Amudaryo bo'yi", "Гургандж (Куня-Ургенч), дельта Амударьи"),
                worldMapX = 0.51f,
                worldMapY = 0.41f,
                title = LocalizedText("4. Defense of Gurganj (1221)", "4. Gurganj mudofaasi (1221)", "4. Оборона Гурганджа (1221)"),
                subtitle = LocalizedText("Khumar Tegin & Najm ad-Din Kubra's Street-by-Street Resistance", "Xumor Tekin va Najmiddin Kubro boshchiligidagi qahramonona kurash", "Уличные бои под началом Хумар-Тегина и шейха Наджм ад-Дина Кубра"),
                weather = WeatherType.CLEAR,
                maxTurns = 14,
                sideAFaction = FactionId.KHWARAZMIAN,
                sideAName = LocalizedText("Citizens & Garrison of Gurganj", "Gurganj mudofaachilari", "Защитники столицы Гурганджа"),
                sideAHeroId = "jalal_ad_din",
                sideAObjectiveType = ObjectiveType.SURVIVE_TURNS,
                sideAObjectiveDesc = LocalizedText("Hold the Oxus Bridge & Inner Quarters (2,3) for 10 turns against the three Mongol princes.", "Gurganj ko'priklari va mahallalarini (2,3) 10 yurish davomida himoya qiling.", "Удерживайте мосты и кварталы Гурганджа (2,3) в течение 10 ходов."),
                sideBFaction = FactionId.MONGOL,
                sideBName = LocalizedText("Armies of Jochi, Chagatai & Ogedei", "Jo'ji, Chig'atoy va O'qtoy qo'shinlari", "Войска Джучи, Чагатая и Угэдэя"),
                sideBHeroId = "genghis_subutai",
                sideBObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideBObjectiveDesc = LocalizedText("Unify the divided siege command and capture the heart of Gurganj (2,3).", "Qo'mondonlikni birlashtirib, Gurganj markazini (2,3) egallang.", "Преодолейте оборону каналов и займите центр Гурганджа (2,3)."),
                targetHex = HexCoord(2, 3),
                surviveTurnsTarget = 10,
                briefing = LocalizedText(
                    "In early 1221, the wealthy Khwarazmian capital of Gurganj withstood one of history's fiercest urban sieges. Lacking stones in the sandy Oxus delta, the Mongols cut mulberry tree trunks soaked in water to use as catapult projectiles. Inside the city, Khumar Tegin and the 76-year-old Sufi scholar Sheikh Najm ad-Din Kubra rallied the townsfolk— destroying the bridge on the Amu Darya to annihilate a 3,000-man Mongol vanguard and fighting for every street and quarter.",
                    "1221-yil boshida Xorazmshohlar poytaxti Gurganj tarixdagi eng shiddatli shahar mudofaalaridan birini ko'rsatdi. Cho'l hududida tosh topilmagani sababli qamalchilar tut daraxti g'o'lalarini suvga ivitib manjaniqlardan otdilar. Shahar ichida Xumor Tekin va 76 yoshli ulug' alloma Shayx Najmiddin Kubro xalq bilan birga ko'prikda 3000 kishilik ilg'or bo'linmani qirib tashlab, har bir ko'cha va mahalla uchun mardona jang qildilar.",
                    "В начале 1221 года столица Хорезма Гургандж выдержала многомесячную осаду. За неимением камней в дельте Амударьи осаждающие метали из катапульт вымоченные стволы тутовых деревьев. Горожане во главе с Хумар-Тегином и 76-летним шейхом Наджм ад-Дином Кубра уничтожили авангард на мосту и сражались за каждый квартал."
                ),
                whatReallyHappened = LocalizedText(
                    "Only after Genghis Khan appointed Ogedei as sole commander to end the quarrel between Jochi and Chagatai did the siege succeed after seven days of street-by-street fighting, followed by the breaching of the Oxus dam.",
                    "Jo'ji va Chig'atoy o'rtasidagi kelishmovchilik sababli qamal uzoqqa cho'zildi; faqat O'qtoy yagona qo'mondon etib tayinlangach, yetti kunlik ko'cha janglaridan so'ng shahar egallandi va Amudaryo to'g'oni ochildi.",
                    "Лишь после назначения Угэдэя единым командующим вместо споривших Джучи и Чагатая город пал после ожесточенных уличных боев, а плотина на Амударье была разрушена."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The mulberry-trunk catapult projectiles, the trap at the Oxus canal bridge, the dispute between Jochi and Chagatai, and Najm ad-Din Kubra's refusal to abandon his city.",
                    "Tarixiy haqiqat: Tut g'o'lalaridan manjaniq o'qi sifatida foydalanilgani, ko'prikdagi tuzoq, Jo'ji va Chig'atoy nizosi hamda Shayx Najmiddin Kubro jasorati.",
                    "Исторично: Снаряды из тутовых бревен, засада на мосту через канал, спор Джучи и Чагатая и подвиг Наджм ад-Дина Кубра."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation:Models the canal bridges and fortified urban quarters as layered hexes.",
                    "O'yin moslashuvi: Gurganj kanallari, ko'priklari va mahallalari qatlamli mudofaa kataklari sifatida berilgan.",
                    "Игровое упрощение: Каналы, мосты и городские кварталы представлены укрепленными гексами."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Jami al-Tawarikh (Compendium of Chronicles)",
                        authorOrPeriod = "Rashid al-Din Fazlullah (c. 1307)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Documents the rift between Jochi and Chagatai at Gurganj, the mulberry-wood catapult stones, and the street fighting.", "Gurganj qamalida Jo'ji va Chig'atoy nizosi hamda ko'cha janglarini batafsil yozadi.", "Описывает разногласия Джучи и Чагатая, снаряды из тутовника и бои за кварталы.")
                    ),
                    HistoricalSource(
                        title = "Al-Kamil fi al-Tarikh",
                        authorOrPeriod = "Ibn al-Athir (c. 1231)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Records the tenacity of Gurganj's defenders and the flooding from the Oxus dam.", "Gurganj aholisining matonati va Amudaryo to'g'oni haqida ma'lumot beradi.", "Свидетельствует о стойкости защитников Гурганджа и прорыве плотины.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Jochi and Chagatai Never Reconciled Outside Gurganj?", "Agar Jo'ji va Chig'atoy o'rtasidagi nizo bartaraf etilmaganida?", "Что если бы раздор между Джучи и Чагатаем сорвал штурм?"),
                whatIfDescription = LocalizedText("Hypothetical: Gurganj defenders launch a coordinated counter-offensive with intact Oxus dikes.", "Faraziy holat: Gurganj himoyachilari tarqoq qamal qanotlariga qarshi yoppasiga hujumga o'tadi.", "Гипотеза: Защитники Гурганджа переходят в решительное контрнаступление по мостам."),
                whatIfSideABonusUnits = listOf(
                    UnitSpawn("khwarazmian_cavalry", 3, 1),
                    UnitSpawn("sogdian_infantry", 4, 3)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.DESERT,
                terrainOverrides = listOf(
                    TerrainTileSpec(2, 3, TerrainType.FORTRESS, "Gurganj Palace Quarter"),
                    TerrainTileSpec(3, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(3, 3, TerrainType.CITY_WALLS),
                    TerrainTileSpec(3, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(4, 0, TerrainType.RIVER, "Oxus Main Channel"),
                    TerrainTileSpec(4, 1, TerrainType.RIVER),
                    TerrainTileSpec(4, 2, TerrainType.RIVER),
                    TerrainTileSpec(4, 3, TerrainType.ROAD, "Gurganj Canal Bridge"),
                    TerrainTileSpec(4, 4, TerrainType.RIVER),
                    TerrainTileSpec(4, 5, TerrainType.RIVER),
                    TerrainTileSpec(4, 6, TerrainType.RIVER)
                ),
                sideASpawns = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 3, isHero = true, heroId = "jalal_ad_din", customName = "Khumar Tegin"),
                    UnitSpawn("sogdian_infantry", 3, 2),
                    UnitSpawn("sogdian_infantry", 3, 3),
                    UnitSpawn("sogdian_infantry", 3, 4),
                    UnitSpawn("common_archers", 2, 2),
                    UnitSpawn("common_engineers", 2, 4)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 8, 3, isHero = true, heroId = "genghis_subutai", customName = "Ogedei's Command"),
                    UnitSpawn("common_catapult", 6, 2, customName = "Mulberry-Trunk Catapult"),
                    UnitSpawn("common_catapult", 6, 4, customName = "Mulberry-Trunk Catapult"),
                    UnitSpawn("steppe_heavy_cavalry", 5, 3),
                    UnitSpawn("steppe_horse_archer", 7, 1),
                    UnitSpawn("steppe_horse_archer", 7, 5),
                    UnitSpawn("common_spearmen", 6, 3)
                )
            ),

            // Mission 5: Battle of Parwan, 1221
            MissionDefinition(
                id = "mission_parwan_1221",
                campaignId = "campaign_mongol_khwarazm",
                orderIndex = 5,
                year = 1221,
                displayDate = "Spring 1221",
                locationName = LocalizedText("Parwan Valley, Hindu Kush Mountains", "Parvon vodiysi, Hindukush tog'lari", "Долина Парван, Гиндукуш"),
                worldMapX = 0.67f,
                worldMapY = 0.58f,
                title = LocalizedText("5. Battle of Parwan (1221)", "5. Parvon jangi (1221)", "5. Битва при Парване (1221)"),
                subtitle = LocalizedText("Sultan Jalal ad-Din Mangburni's Triumph in the Hindu Kush", "Sulton Jaloliddin Manguberdining mo'g'ul tumanlari ustidan yorqin g'alabasi", "Победа султана Джалал ад-Дина над корпусом Шиги-Кутуку"),
                weather = WeatherType.CLEAR,
                maxTurns = 13,
                sideAFaction = FactionId.KHWARAZMIAN,
                sideAName = LocalizedText("Jalal ad-Din, Saif ad-Din Ighraq & Timur Malik", "Sulton Jaloliddin Manguberdi qo'shini", "Армия Джалал ад-Дина Мангуберди"),
                sideAHeroId = "jalal_ad_din",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Hold the narrow mountain valley on foot, then charge and rout Shigi Qutuqu's tumens.", "Tog' vodiysida mudofaani ushlab, so'ngra yoppasiga otliq hamlasi bilan Shigi Qutuquni tor-mor eting.", "Удержите горное ущелье и контратакой разгромите тумены Шиги-Кутуку."),
                sideBFaction = FactionId.MONGOL,
                sideBName = LocalizedText("Shigi Qutuqu's Three Tumens", "Shigi Qutuquning 30 minglik qo'shini", "Корпус темника Шиги-Кутуку"),
                sideBHeroId = "genghis_subutai",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Use felt dummy riders to deceive the Khwarazmian flanks and break Jalal ad-Din's center.", "Kigiz qo'g'irchoqlar hiylasini qo'llab, Jaloliddin markazini yorib o'ting.", "Используйте ложные чучела на конях и пробейте центр Джалал ад-Дина."),
                targetHex = null,
                briefing = LocalizedText(
                    "In spring 1221, Sultan Jalal ad-Din Mangburni—joined by the legendary defender of Khujand, Timur Malik, and the Khalaj/Kipchak commanders Saif ad-Din Ighraq and Azam Malik—met Genghis Khan's adoptive brother Shigi Qutuqu in the narrow rocky valley of Parwan north of Kabul. Ordering his horsemen to dismount, tie their horse reins to their belts, and fight as a disciplined archer-and-shield wall on the steep slopes, Jalal ad-Din blunted every Mongol charge before ordering his men to remount when the battle horns sounded.",
                    "1221-yil bahorida Sulton Jaloliddin Manguberdi — Xo'jand qahramoni Temur Malik, Sayfiddin Ig'roq va A'zam Malik bilan birgalikda — Hindukush tog'laridagi tor Parvon darasida Chingizxonning tutingan ukasi Shigi Qutuqu boshchiligidagi 30 minglik qo'shin bilan to'qnashdi. Jaloliddin otliqlarni otdan tushirib, jilovni belga bog'lagan holda qoyalar ustida mustahkam saf tortishni buyurdi. Ikkian-chi kuni Shigi Qutuqu zaxira otlarga kigizdan yasalgan qo'g'irchoqlar bog'lab qo'rqitmoqchi bo'lganida, Jaloliddin buni payqab, karnay sadolari ostida yoppasiga otliq hujumiga o'tdi va dushmanni tor-mor keltirdi!",
                    "Весной 1221 года султан Джалал ад-Дин Мангуберди вместе с героем Ходжента Тимур-Маликом и эмирами Сайф ад-Дином Играком и Азам-Маликом встретил три тумена Шиги-Кутуку в горной долине Парван. Спешив воинов на крутых склонах и отразив все атаки (включая военную хитрость с войлочными чучелами на заводных конях), Джалал ад-Дин по звуку карная бросил войско в конную контратаку и одержал блестящую победу."
                ),
                whatReallyHappened = LocalizedText(
                    "Parwan was the only major field defeat suffered by a Mongol army during the Khwarazmian war. Tragically for Jalal ad-Din, immediately after the victory his commanders Saif ad-Din Ighraq and Azam Malik quarreled fiercely over a prized white Arabian horse from the spoils—causing half the army to depart before Genghis Khan marched south in person.",
                    "Parvon jangi Xorazm urushi davomida mo'g'ul qo'shini uchragan yagona yirik maydon mag'lubiyati bo'ldi. Afsuski, g'alabadan so'ng o'ljaga tushgan bitta arab tulpori ustida Sayfiddin Ig'roq va A'zam Malik o'rtasida nizo chiqib, qo'shinning yarmi Jaloliddinni tark etdi.",
                    "Битва при Парване стала единственным крупным полевым поражением монголов в среднеазиатской кампании. Однако сразу после победы спор эмиров Играка и Азам-Малика из-за трофейного арабского скакуна расколол армию Джалал ад-Дина."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The narrow rocky terrain of Parwan, dismounted cavalry tactics with reins tied to belts, Shigi Qutuqu's felt-dummy ruse on the second morning, and the post-battle dispute over the Arabian horse.",
                    "Tarixiy haqiqat: Parvonning toshloq darasi, ot jilovini belga bog'lab piyoda jang qilish usuli, Shigi Qutuquning kigiz qo'g'irchoqlari va o'lja ot ustidagi nizo.",
                    "Исторично: Узкая каменистая долина Парвана, бой спешенных всадников, чучела Шиги-Кутуку и роковой спор из-за коня."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Condenses the two-day engagement in the Parwan canyon into a single decisive battle.",
                    "O'yin moslashuvi: Ikki kunlik Parvon jangi bitta dinamik xaritada mujassamlashtirildi.",
                    "Игровое упрощение: Двухдневное сражение в ущелье Парвана сведено в один решающий бой."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Sirat al-Sultan Jalal ad-Din Mankburni",
                        authorOrPeriod = "Al-Nasawi (1241) & Juvayni (1260)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Both Nasawi and Juvayni corroborate the dismounted defense, the felt dummies on spare horses, and the quarrel over the horse after victory.", "Nasaviy va Juvayniy Parvon jangi tafsilotlarini hamda kigiz qo'g'irchoqlar voqeasini birdek tasdiqlaydi.", "И ан-Насави, и Джувейни подробно описывают ход битвы, чучела на конях и спор из-за добычи.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Saif ad-Din Ighraq Never Left After Parwan?", "Agar Parvon g'alabasidan keyin qo'shin birligi saqlab qolinganida?", "Что если бы войско сохранило единство после Парвана?"),
                whatIfDescription = LocalizedText("Hypothetical: Command the full united coalition of Khwarazmian, Khalaj, and Turkmen horsemen in the Hindu Kush.", "Faraziy holat: Xorazm, xalaj va turkman otliqlarining yagona qudratli ittifoqi bilan jang qiling.", "Гипотеза: Командуйте объединенным войском хорезмийцев, халаджей и туркмен."),
                whatIfSideABonusUnits = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 1),
                    UnitSpawn("steppe_horse_archer", 2, 5)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.HILLS,
                terrainOverrides = listOf(
                    TerrainTileSpec(4, 0, TerrainType.MOUNTAIN),
                    TerrainTileSpec(5, 0, TerrainType.MOUNTAIN),
                    TerrainTileSpec(4, 6, TerrainType.MOUNTAIN),
                    TerrainTileSpec(5, 6, TerrainType.MOUNTAIN),
                    TerrainTileSpec(3, 2, TerrainType.HILLS, "Parwan Ridge"),
                    TerrainTileSpec(3, 3, TerrainType.PLAINS, "Valley Floor"),
                    TerrainTileSpec(4, 3, TerrainType.PLAINS),
                    TerrainTileSpec(5, 3, TerrainType.PLAINS),
                    TerrainTileSpec(6, 3, TerrainType.PLAINS),
                    TerrainTileSpec(3, 4, TerrainType.HILLS, "South Slope")
                ),
                sideASpawns = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 3, isHero = true, heroId = "jalal_ad_din"),
                    UnitSpawn("khwarazmian_cavalry", 3, 2, customName = "Timur Malik's Wing"),
                    UnitSpawn("steppe_horse_archer", 3, 4, customName = "Saif ad-Din Ighraq"),
                    UnitSpawn("sogdian_infantry", 4, 3),
                    UnitSpawn("common_archers", 2, 2),
                    UnitSpawn("common_archers", 2, 4)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 8, 3, isHero = true, heroId = "genghis_subutai", customName = "Shigi Qutuqu"),
                    UnitSpawn("steppe_horse_archer", 6, 2),
                    UnitSpawn("steppe_horse_archer", 6, 4),
                    UnitSpawn("steppe_light_cavalry", 7, 1, customName = "Felt-Dummy Flank"),
                    UnitSpawn("steppe_light_cavalry", 7, 5, customName = "Felt-Dummy Flank"),
                    UnitSpawn("steppe_heavy_cavalry", 6, 3)
                )
            ),

            // Mission 6: Battle of the Indus, 1221
            MissionDefinition(
                id = "mission_indus_1221",
                campaignId = "campaign_mongol_khwarazm",
                orderIndex = 6,
                year = 1221,
                displayDate = "24 November 1221",
                locationName = LocalizedText("Banks of the Indus River (Nilab Cliffs)", "Sind (Hind) daryosi bo'yidagi qoyalar", "Утесы на берегу реки Инд"),
                worldMapX = 0.70f,
                worldMapY = 0.63f,
                title = LocalizedText("6. Battle of the Indus (1221)", "6. Sind daryosi bo'yidagi jang (1221)", "6. Битва на реке Инд (1221)"),
                subtitle = LocalizedText("Jalal ad-Din's Last Stand & Leap into the Indus Before Genghis Khan", "Sulton Jaloliddin Manguberdining Chingizxon qurshovida ko'rsatgan afsonaviy jasorati", "Последний рубеж и прыжок Джалал ад-Дина в Инд на глазах Чингисхана"),
                weather = WeatherType.CLEAR,
                maxTurns = 14,
                sideAFaction = FactionId.KHWARAZMIAN,
                sideAName = LocalizedText("Jalal ad-Din's Royal Rearguard", "Sulton Jaloliddin Manguberdi qo'shini", "Арьергард султана Джалал ад-Дина"),
                sideAHeroId = "jalal_ad_din",
                sideAObjectiveType = ObjectiveType.PROTECT_HERO,
                sideAObjectiveDesc = LocalizedText("Break the Mongol center or fight to the Indus crossing (1,3) with Jalal ad-Din alive!", "Mo'g'ul markazini yorib o'ting yoki Jaloliddinni Sind kechuvida (1,3) omon saqlab g'alaba qozoning!", "Прорвите центр противника и сохраните Джалал ад-Дина у переправы через Инд (1,3)!"),
                sideBFaction = FactionId.MONGOL,
                sideBName = LocalizedText("Genghis Khan's Imperial Guard (Keshig)", "Chingizxonning asosiy qo'shini", "Главная ставка Чингисхана"),
                sideBHeroId = "genghis_subutai",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Pin the Khwarazmian army in a crescent arc against the Indus cliffs.", "Xorazm qo'shinini yarim oy shaklida Sind qoyalariga qisib tor-mor eting.", "Прижмите хорезмийцев полумесяцем к обрывам Инда."),
                targetHex = HexCoord(1, 3),
                briefing = LocalizedText(
                    "After the split of his coalition following Parwan, Jalal ad-Din was overtaken at dawn on 24 November 1221 on the rocky west bank of the Indus River by Genghis Khan's forced march. Pinning the Khwarazmian left against the mountain ridge and the right against the river bend, Genghis Khan ordered Jalal ad-Din taken alive. Instead, Jalal ad-Din led a furious counter-charge led by Amin Malik that nearly pierced Genghis Khan's own command standard—until the elite 10,000-man Bahadurs descended a hidden mountain goat-track to strike the Khwarazmian rear.",
                    "Parvondagi bo'linishdan so'ng Chingizxon jadal yurish bilan 1221-yil 24-noyabr tongida Sind (Hind) daryosining qoyali qirg'og'ida Jaloliddin qo'shiniga yetib oldi. Orqada daryo, ikki qanotda yarim oy shaklidagi mo'g'ul tumanlari turganiga qaramay, Sulton Jaloliddin markazga shunday shiddatli hamla qildiki, Chingizxonning o'zi ot almashtirib chekinishga majbur bo'ldi. Faqat tog' so'qmog'idan aylanib o'tgan 10 minglik «Bahodirlar» zaxirasi zarbasidan so'ng, Jaloliddin qalqoni va tug'ini tashlamay, 20 futlik qoyadan tulpori bilan to'lqinli Sind daryosiga sakrab kechib o'tdi!",
                    "Утром 24 ноября 1221 года Чингисхан настиг Джалал ад-Дина на скалистом берегу Инда, зажав его войско полумесяцем к реке. В отчаянной контратаке Джалал ад-Дин проломил монгольский центр, и лишь обходной удар отборной тысячи бахадуров по горной тропе решил исход боя. Не желая сдаваться, Джалал ад-Дин в доспехах и со знаменем в руке прыгнул на коне с высокого обрыва в бурный Инд и переплыл на другой берег."
                ),
                whatReallyHappened = LocalizedText(
                    "Both Juvayni and Rashid al-Din record that Genghis Khan forbade his archers from shooting as Jalal ad-Din swam his horse across the Indus, turning to his sons Jochi, Chagatai, and Ogedei to say: 'Such a son should a father have!'",
                    "Juvayniy va Rashididdin yozishicha, Chingizxon daryodan suzib o'tayotgan Jaloliddinga o'q uzishni taqiqlab, o'g'illariga qarata: «Ota o'g'li shunday bo'lishi kerak!» — deya uning jasoratiga tan bergan.",
                    "По свидетельству Джувейни и Рашид ад-Дина, Чингисхан запретил лучникам стрелять вслед плывущему султану и сказал сыновьям: «Вот какой сын должен быть у отца!»"
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The crescent encirclement against the Indus river bend, Jalal ad-Din's charge against the Mongol center, the mountain-path flank attack by Bela Noyan's bahadurs, and Genghis Khan's tribute.",
                    "Tarixiy haqiqat: Sind daryosi bo'yidagi yarim oy qurshovi, Jaloliddinning markazni yorib o'tgan hamlasi, tog' so'qmog'idan berilgan zarba va Chingizxon e'tirofi.",
                    "Исторично: Окружение полумесяцем у излучины Инда, прорыв центра, обход бахадуров по горной тропе и слова Чингисхана."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Gives you the chance to guard the mountain pass and turn Jalal ad-Din's central counter-charge into a complete tactical victory—especially in the What-If scenario!",
                    "O'yin moslashuvi: Tog' so'qmog'ini to'sib, Jaloliddinning markaziy hamlasini to'liq g'alabaga aylantirish imkonini beradi.",
                    "Игровое упрощение: Позволяет перекрыть горную тропу и превратить прорыв центра в полную тактическую победу."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Tarikh-i Jahangushay",
                        authorOrPeriod = "Ata-Malik Juvayni (c. 1260)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Records Jalal ad-Din's charge, the leap into the Indus with shield and standard, and Genghis Khan's remark to his sons.", "Jaloliddinning Sindga sakrashi va Chingizxonning o'g'illariga aytgan mashhur so'zlarini keltiradi.", "Приводит описание прыжка со знаменем в Инд и знаменитые слова Чингисхана.")
                    ),
                    HistoricalSource(
                        title = "Sirat al-Sultan Jalal ad-Din Mankburni",
                        authorOrPeriod = "Shihab al-Din al-Nasawi (1241)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Primary account of Amin Malik's right wing, the mountain pass ambush, and the crossing into India.", "Amin Malik qanoti va tog' dovoni orqali berilgan zarba haqidagi birlamchi manba.", "Первоисточник о действиях крыла Амин-Малика и переправе в Индию.")
                    )
                ),
                reviewedBy = "Central Asian Medieval Studies Schema v1.0",
                whatIfTitle = LocalizedText("What If Jalal ad-Din Had Saif ad-Din Ighraq's 30,000 Cavalry at the Indus?", "Agar Sind bo'yida Sayfiddin Ig'roqning 30 ming otlig'i Jaloliddin safida bo'lganida?", "Что если бы у Джалал ад-Дина на Инде осталась конница Играка?"),
                whatIfDescription = LocalizedText("Featured Prompt What-If: Saif ad-Din Ighraq and Azam Malik remain united with Jalal ad-Din, guarding the mountain pass and matching Genghis Khan's numbers!", "Maxsus «Agar-chi?» senariysi: Parvondan keyin qo'shin bo'linmaydi — Sayfiddin Ig'roq va A'zam Malik otliqlari tog' dovonini to'sib, Chingizxonga teng kuch bilan qarshi chiqadi!", "Ключевой сценарий «Что если»: Войско не раскололось после Парвана — конница Играка прикрывает горную тропу и дает генеральное сражение на равных!"),
                whatIfSideABonusUnits = listOf(
                    UnitSpawn("khwarazmian_cavalry", 3, 1, customName = "Saif ad-Din Ighraq's Wing"),
                    UnitSpawn("steppe_horse_archer", 3, 5, customName = "Azam Malik's Horsemen"),
                    UnitSpawn("khwarazmian_cavalry", 2, 2, customName = "Timur Malik's Guard")
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(0, 0, TerrainType.RIVER, "Indus River"),
                    TerrainTileSpec(0, 1, TerrainType.RIVER),
                    TerrainTileSpec(0, 2, TerrainType.RIVER),
                    TerrainTileSpec(0, 3, TerrainType.RIVER, "Indus Cliff Leap"),
                    TerrainTileSpec(0, 4, TerrainType.RIVER),
                    TerrainTileSpec(0, 5, TerrainType.RIVER),
                    TerrainTileSpec(0, 6, TerrainType.RIVER),
                    TerrainTileSpec(1, 3, TerrainType.HILLS, "Nilab Cliffside"),
                    TerrainTileSpec(2, 0, TerrainType.MOUNTAIN, "Goat-Track Pass"),
                    TerrainTileSpec(3, 0, TerrainType.MOUNTAIN),
                    TerrainTileSpec(4, 0, TerrainType.HILLS)
                ),
                sideASpawns = listOf(
                    UnitSpawn("khwarazmian_cavalry", 2, 3, isHero = true, heroId = "jalal_ad_din"),
                    UnitSpawn("khwarazmian_cavalry", 3, 3, customName = "Amin Malik's Vanguard"),
                    UnitSpawn("sogdian_infantry", 2, 4),
                    UnitSpawn("steppe_horse_archer", 3, 4),
                    UnitSpawn("common_spearmen", 2, 2)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("steppe_heavy_cavalry", 8, 3, isHero = true, heroId = "genghis_subutai"),
                    UnitSpawn("steppe_heavy_cavalry", 5, 1, customName = "Bela Noyan's Bahadurs"),
                    UnitSpawn("steppe_heavy_cavalry", 6, 3, customName = "Keshig Center"),
                    UnitSpawn("steppe_horse_archer", 6, 2),
                    UnitSpawn("steppe_horse_archer", 6, 4),
                    UnitSpawn("steppe_light_cavalry", 5, 5),
                    UnitSpawn("nomad_raiders", 7, 4)
                )
            )
        )
    )
}
