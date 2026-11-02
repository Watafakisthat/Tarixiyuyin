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

object CampaignsCatalogWest {

    val campaignCrusades = CampaignDefinition(
        id = "campaign_crusades",
        title = LocalizedText("Campaign III: The Crusades", "III Yurish: Salib yurishlari (1099–1192)", "Кампания III: Крестовые походы (1099–1192)"),
        subtitle = LocalizedText("Jerusalem, Hattin, Acre & Arsuf (1099–1192)", "Quddus, Xattin, Akka va Arsuf (1099–1192)", "Иерусалим, Хаттин, Акра и Арсуф (1099–1192)"),
        eraRange = "1099–1192 CE",
        century = 12,
        description = LocalizedText(
            "Command either the Ayyubid & Levantine armies under Saladin or the Crusader hosts under Godfrey of Bouillon and Richard the Lionheart across the hills and coastal plains of the Levant.",
            "Salohiddin Ayyubiy boshchiligidagi Shom va Misr qo'shinlarini yoki Rixard Sheryurak boshchiligidagi salibchilarni boshqaring.",
            "Возглавьте войска Айюбидов под началом Салах ад-Дина или армии крестоносцев Ричарда Львиное Сердце."
        ),
        musicThemeId = "theme_middle_east_oud",
        primaryColorHex = 0xFF9E6B18,
        missions = listOf(
            // Mission 1: Siege of Jerusalem, 1099
            MissionDefinition(
                id = "mission_jerusalem_1099",
                campaignId = "campaign_crusades",
                orderIndex = 1,
                year = 1099,
                displayDate = "June–15 July 1099",
                locationName = LocalizedText("Walls of Jerusalem & Tower of David", "Quddus devorlari va Dovud minorasi", "Стены Иерусалима и Башня Давида"),
                worldMapX = 0.28f,
                worldMapY = 0.58f,
                title = LocalizedText("1. Siege of Jerusalem (1099)", "1. Quddus qamali (1099)", "1. Осада Иерусалима (1099)"),
                subtitle = LocalizedText("Siege Towers at the Northern Wall vs. Iftikhar ad-Dawla's Garrison", "Shimoliy devordagi qamal minoralari va Iftixor ad-Davla mudofaasi", "Осадные башни у северной стены против гарнизона Ифтихар ад-Даулы"),
                weather = WeatherType.CLEAR,
                maxTurns = 13,
                sideAFaction = FactionId.CRUSADER,
                sideAName = LocalizedText("First Crusade Host (Godfrey & Raymond)", "Birinchi salib yurishi qo'shini", "Войско Первого крестового похода"),
                sideAHeroId = "richard_lionheart",
                sideAObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideAObjectiveDesc = LocalizedText("Breach the Jerusalem ramparts and capture the Citadel (7,3) before Fatimid relief arrives.", "Quddus devorlarini yorib o'ting va qal'ani (7,3) egallang.", "Пробейте стены Иерусалима и займите цитадель (7,3)."),
                sideBFaction = FactionId.AYYUBID,
                sideBName = LocalizedText("Fatimid Garrison (Iftikhar ad-Dawla)", "Quddus garnizoni (Iftixor ad-Davla)", "Гарнизон Иерусалима (Ифтихар ад-Даула)"),
                sideBHeroId = "saladin",
                sideBObjectiveType = ObjectiveType.SURVIVE_TURNS,
                sideBObjectiveDesc = LocalizedText("Hold the Jerusalem Citadel (7,3) for 9 turns and burn the approaching siege engines.", "Quddus qal'asini (7,3) 9 yurish davomida ushlab turing va qamal qurollarini yo'q qiling.", "Удерживайте цитадель (7,3) 9 ходов и уничтожьте осадные орудия."),
                targetHex = HexCoord(7, 3),
                surviveTurnsTarget = 9,
                briefing = LocalizedText(
                    "In June 1099, the survivors of the First Crusade reached Jerusalem, defended by the Fatimid governor Iftikhar ad-Dawla, who had poisoned or sealed the outer wells and strengthened the ramparts. Using timber salvaged from Genoese ships at Jaffa, the Crusaders built two wheeled siege towers, shifting Godfrey of Bouillon's tower under cover of night to a weaker section of the northern wall.",
                    "1099-yil iyunida Birinchi salib yurishi qo'shini Iftixor ad-Davla himoya qilayotgan Quddusga yetib keldi. Tashqi quduqlar yopilgani sababli qamalchilar suvsizlikdan qiynaldi, biroq Yaffadagi genuyalik kemalar yog'ochlaridan ikkita g'ildirakli qamal minorasi yasab, tunda shimoliy devorning kuchsizroq qismiga surib o'tdilar.",
                    "В июне 1099 года крестоносцы подошли к Иерусалиму, который оборонял фатимидский наместник Ифтихар ад-Даула. Из древесины генуэзских кораблей осаждающие построили две передвижные башни, тайно перебросив одну из них ночью к северо-восточному участку стены."
                ),
                whatReallyHappened = LocalizedText(
                    "On 15 July 1099, Godfrey's siege tower bridged the northern wall while Iftikhar ad-Dawla negotiated safe passage from the Tower of David. Both Christian and Muslim chroniclers recorded the terrible massacre of the city's inhabitants that followed—a stark contrast to Saladin's peaceful recovery of the city in 1187.",
                    "1099-yil 15-iyulda shimoliy devor yorib o'tildi. Ham sharq, ham g'arb solnomachilari shahar olingach tinch aholiga nisbatan qilingan og'ir xunrezlikni qayd etganlar — bu holat 1187-yilda Salohiddin Ayyubiyning Quddusni sulh va omonlik bilan qaytarib olganidan keskin farq qiladi.",
                    "15 июля 1099 года северная стена была взята. И восточные, и западные хронисты с ужасом описали последовавшую расправу над жителями — в разительном контрасте с мирной сдачей города Саладину в 1187 году."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The double siege push on the northern wall and Mount Zion, the scarcity of water outside the walls, and the neutral chronicle contrast between 1099 and 1187.",
                    "Tarixiy haqiqat: Shimoliy va janubiy devorlarga qilingan ikki tomonlama hujum, suv tanqisligi va manbalarning xolis qiyosi.",
                    "Исторично: Двусторонний штурм укреплений, нехватка воды у осаждающих и объективное освещение хроник."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Represents the wheeled siege towers via engineers and trebuchets/catapults assaulting the wall hexes.",
                    "O'yin moslashuvi: Qamal minoralari va tosh otarlar muhandislar hamda katapulta bo'linmalari orqali ifodalangan.",
                    "Игровое упрощение: Осадные башни и камнеметы представлены отрядами инженеров и катапульт."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Historia rerum in partibus transmarinis gestarum",
                        authorOrPeriod = "William of Tyre (c. 1184) & Gesta Francorum (c. 1101)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Latin chronicles describing the construction and night repositioning of the siege towers.", "Qamal minoralarining yasalishi va tunda ko'chirilishini tasvirlovchi lotin manbalari.", "Латинские хроники о постройке и ночной переброске осадных башен.")
                    ),
                    HistoricalSource(
                        title = "Al-Kamil fi al-Tarikh",
                        authorOrPeriod = "Ibn al-Athir (c. 1231) & Ibn al-Qalanisi",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Damascus and Mosul chronicles documenting the defense and aftermath of July 1099.", "Damashq va Mosul solnomalarida 1099-yil iyul voqealarining bayoni.", "Дамасские и мосульские хроники об обороне города в 1099 году.")
                    )
                ),
                reviewedBy = "Levant & Crusades Historical Schema v1.0",
                whatIfTitle = LocalizedText("What If the Fatimid Relief Army from Ascalon Arrived Two Weeks Earlier?", "Agar Asqalondan Misr yordam qo'shini ikki hafta oldin yetib kelganida?", "Что если бы фатимидская армия из Аскалона подошла на две недели раньше?"),
                whatIfDescription = LocalizedText("Hypothetical: Heavy cavalry and camel raiders strike the Crusader rear while the garrison holds the walls.", "Faraziy holat: Qamalchilarning orqa tomonidan yordamchi otliq va tuyakash bo'linmalar zarba beradi.", "Гипотеза: Подкрепления атакуют тыл осаждающих, пока гарнизон удерживает стены."),
                whatIfSideBBonusUnits = listOf(
                    UnitSpawn("mamluk_cavalry", 1, 1),
                    UnitSpawn("camel_riders", 1, 5)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.HILLS,
                terrainOverrides = listOf(
                    TerrainTileSpec(7, 3, TerrainType.FORTRESS, "Tower of David Citadel"),
                    TerrainTileSpec(6, 2, TerrainType.CITY_WALLS, "North Wall"),
                    TerrainTileSpec(6, 3, TerrainType.CITY_WALLS, "Damascus Gate"),
                    TerrainTileSpec(6, 4, TerrainType.CITY_WALLS, "Mount Zion Wall"),
                    TerrainTileSpec(7, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(7, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(4, 3, TerrainType.PLAINS),
                    TerrainTileSpec(5, 3, TerrainType.PLAINS)
                ),
                sideASpawns = listOf(
                    UnitSpawn("european_knights", 2, 3, isHero = true, heroId = "richard_lionheart", customName = "Godfrey of Bouillon"),
                    UnitSpawn("common_trebuchet", 4, 2),
                    UnitSpawn("common_catapult", 4, 4),
                    UnitSpawn("men_at_arms", 5, 2),
                    UnitSpawn("men_at_arms", 5, 4),
                    UnitSpawn("crossbowmen", 3, 3),
                    UnitSpawn("common_engineers", 5, 3)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("mamluk_cavalry", 7, 3, isHero = true, heroId = "saladin", customName = "Iftikhar ad-Dawla"),
                    UnitSpawn("saracen_infantry", 6, 2),
                    UnitSpawn("saracen_infantry", 6, 3),
                    UnitSpawn("saracen_infantry", 6, 4),
                    UnitSpawn("ghazi_archers", 7, 2),
                    UnitSpawn("ghazi_archers", 7, 4)
                )
            ),

            // Mission 2: Battle of Hattin, 1187
            MissionDefinition(
                id = "mission_hattin_1187",
                campaignId = "campaign_crusades",
                orderIndex = 2,
                year = 1187,
                displayDate = "4 July 1187",
                locationName = LocalizedText("Horns of Hattin near Lake Tiberias", "Tiberiya ko'li yaqinidagi Xattin tepaligi", "Рога Хаттина у Тивериадского озера"),
                worldMapX = 0.29f,
                worldMapY = 0.55f,
                title = LocalizedText("2. Battle of Hattin (1187)", "2. Xattin jangi (1187)", "2. Битва при Хаттине (1187)"),
                subtitle = LocalizedText("Saladin Traps the Crusader Royal Host on the Arid Ridge", "Salohiddin Ayyubiyning suvsiz tekislikda salibchilar qo'shinini qurshab olishi", "Салах ад-Дин окружает войско короля Ги де Лузиньяна"),
                weather = WeatherType.SANDSTORM,
                maxTurns = 13,
                sideAFaction = FactionId.AYYUBID,
                sideAName = LocalizedText("Saladin's United Ayyubid Army", "Salohiddin Ayyubiy qo'shini", "Армия султана Салах ад-Дина"),
                sideAHeroId = "saladin",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Block the Lake Tiberias springs and crush the encircled Crusader host on the Horns of Hattin.", "Tiberiya suv yo'lini to'sib, Xattin tepaligida dushmanni tor-mor eting.", "Перекройте путь к воде и разгромите окруженное войско на холме Хаттин."),
                sideBFaction = FactionId.CRUSADER,
                sideBName = LocalizedText("Kingdom of Jerusalem (Guy of Lusignan & Raymond III)", "Quddus qirolligi qo'shini", "Армия Иерусалимского королевства"),
                sideBHeroId = "richard_lionheart",
                sideBObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideBObjectiveDesc = LocalizedText("Cut through Saladin's encirclement to reach the water of Lake Tiberias (2,3).", "Qurshovni yorib o'tib, Tiberiya ko'li suv manbasiga (2,3) yetib boring.", "Прорвите окружение и выйдите к воде Тивериадского озера (2,3)."),
                targetHex = HexCoord(2, 3),
                briefing = LocalizedText(
                    "In July 1187, Saladin besieged Tiberias to lure King Guy of Lusignan away from the springs of La Sephorie. Marching across an arid plateau in summer heat, the Crusaders were surrounded overnight and choked by brushfires lit upwind by Saladin's troops. On 4 July, the thirsty infantry retreated up the twin volcanic peaks of the Horns of Hattin while Raymond of Tripoli and King Guy launched desperate charges.",
                    "1187-yil iyulda Salohiddin Ayyubiy salibchilar qo'shinini Saffuriya buloqlaridan uzoqlashtirish uchun Tiberiyani qamal qildi. Jazirama yozda suvsiz tekislikdan yurib charchagan qirol Gi de Luzinyan qo'shini tunda qurshab olindi va quruq o'tlar yoqilib tutun bilan bo'g'ildi. 4-iyul kuni Xattin tepaligi etagida hal qiluvchi jang bo'lib o'tdi.",
                    "В июле 1187 года Салах ад-Дин выманил армию короля Ги де Лузиньяна от источников Сефории на безводное плато. Окруженные и измученные жаждой и дымом степных пожаров, крестоносцы попытались пробиться к воде у двурогого холма Хаттин."
                ),
                whatReallyHappened = LocalizedText(
                    "When Raymond of Tripoli's vanguard charged, Saladin's troops parted to let them pass and then closed the gap behind them. King Guy's red royal tent on the Horns of Hattin collapsed after three final charges, leading to the recovery of Jerusalem three months later.",
                    "Tripoli grafi Raymond hujum qilganda, Salohiddin jangchilari safni ochib uni o'tkazib yubordi va darhol orqadan halqani yopdi. Xattin tepaligidagi qirol chodiri qulagach, uch oydan so'ng Quddus Ayyubiylar qo'liga o'tdi.",
                    "Пропустив сквозь разомкнутый строй конницу графа Раймунда, воины Саладина вновь сомкнули кольцо. Падение королевского шатра на Хаттине предрешило возвращение Иерусалима."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The strategic role of the springs and Lake Tiberias, the brushfire smoke hazard, and the twin hills of Hattin.",
                    "Tarixiy haqiqat: Suv manbalarining hal qiluvchi ahamiyati, tutun usuli va Xattin qo'shaloq tepaligi.",
                    "Исторично: Ключевая роль источников воды, дымовая завеса и рельеф Рогов Хаттина."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Represents the brushfire smoke and heat through the Sandstorm/Heat weather modifier.",
                    "O'yin moslashuvi: Jazirama va tutun ta'siri ob-havo jarimasi orqali aks ettirilgan.",
                    "Игровое упрощение: Зной и дым костров отражены через погодный модификатор."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Al-Nawadir al-Sultaniyya (Rare and Excellent History of Saladin)",
                        authorOrPeriod = "Baha ad-Din ibn Shaddad (c. 1228)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Eyewitness biographer of Saladin describing the collapse of the royal tent on Hattin.", "Salohiddinning yaqin maslakdoshi Bahovuddin ibn Shaddodning Xattin jangi haqidagi guvohligi.", "Свидетельство биографа и соратника Саладина Баха ад-Дина ибн Шаддада.")
                    ),
                    HistoricalSource(
                        title = "Chronique d'Ernoul",
                        authorOrPeriod = "Late 12th Century",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Frankish account of the debate at La Sephorie between Raymond III and Gerard de Ridefort.", "Saffuriya qarorgohidagi harbiy kengash va suvsiz yurish haqidagi frank solnomasi.", "Франкская хроника о военном совете в Сефории и роковом марше.")
                    )
                ),
                reviewedBy = "Levant & Crusades Historical Schema v1.0",
                whatIfTitle = LocalizedText("What If King Guy Listened to Raymond III and Marched With Full Water Trains?", "Agar qirol Gi suv zaxirasi bilan yurish qilganida?", "Что если бы крестоносцы сохранили снабжение водой?"),
                whatIfDescription = LocalizedText("Hypothetical: Clear weather without thirst morale drain and an extra squadron of Knights Hospitalier & Templar.", "Faraziy holat: Tashnalik jarimasi olib tashlanadi va salibchilar qo'shimcha og'ir ritsarlar bilan jangga kiradi.", "Гипотеза: Бой без штрафов жажды с дополнительным отрядом рыцарей."),
                whatIfWeatherOverride = WeatherType.CLEAR,
                whatIfSideBBonusUnits = listOf(UnitSpawn("european_knights", 6, 2)),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.DESERT,
                terrainOverrides = listOf(
                    TerrainTileSpec(1, 2, TerrainType.RIVER, "Lake Tiberias"),
                    TerrainTileSpec(1, 3, TerrainType.RIVER, "Lake Tiberias"),
                    TerrainTileSpec(1, 4, TerrainType.RIVER, "Lake Tiberias"),
                    TerrainTileSpec(2, 3, TerrainType.PLAINS, "Tiberias Shore"),
                    TerrainTileSpec(6, 2, TerrainType.HILLS, "North Horn of Hattin"),
                    TerrainTileSpec(6, 4, TerrainType.HILLS, "South Horn of Hattin"),
                    TerrainTileSpec(7, 3, TerrainType.HILLS, "Royal Tent Ridge")
                ),
                sideASpawns = listOf(
                    UnitSpawn("mamluk_cavalry", 2, 3, isHero = true, heroId = "saladin"),
                    UnitSpawn("mamluk_cavalry", 3, 2, customName = "Taqi ad-Din's Right Wing"),
                    UnitSpawn("mamluk_cavalry", 3, 4, customName = "Gokbori's Left Wing"),
                    UnitSpawn("ghazi_archers", 4, 1),
                    UnitSpawn("ghazi_archers", 4, 5),
                    UnitSpawn("camel_riders", 5, 1),
                    UnitSpawn("saracen_infantry", 3, 3)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("european_knights", 7, 3, isHero = true, heroId = "richard_lionheart", customName = "King Guy & True Cross Guard"),
                    UnitSpawn("european_knights", 6, 2, customName = "Raymond of Tripoli"),
                    UnitSpawn("european_knights", 6, 4, customName = "Balian of Ibelin"),
                    UnitSpawn("men_at_arms", 5, 3),
                    UnitSpawn("crossbowmen", 7, 2),
                    UnitSpawn("common_spearmen", 7, 4)
                )
            ),

            // Mission 3: Siege of Acre, 1189–1191
            MissionDefinition(
                id = "mission_acre_1191",
                campaignId = "campaign_crusades",
                orderIndex = 3,
                year = 1191,
                displayDate = "1189–12 July 1191",
                locationName = LocalizedText("Port Fortress of Acre & Hill of Toron", "Akka qal'asi va Toron tepaligi", "Крепость Акра и холм Торон"),
                worldMapX = 0.28f,
                worldMapY = 0.54f,
                title = LocalizedText("3. Double Siege of Acre (1189–1191)", "3. Akka ikki qavatli qamali (1189–1191)", "3. Двойная осада Акры (1189–1191)"),
                subtitle = LocalizedText("Besiegers Besieged Between the Accursed Tower and Saladin's Camp", "Qamalchilarning o'zi Salohiddin qo'shini qurshovida qolgan jang", "Осаждающие в кольце между Проклятой башней и лагерем Саладина"),
                weather = WeatherType.CLEAR,
                maxTurns = 14,
                sideAFaction = FactionId.CRUSADER,
                sideAName = LocalizedText("Third Crusade Siege Ring (Richard I & Philip II)", "Uchinchi salib yurishi qo'shini", "Осадная армия Ричарда I и Филиппа II"),
                sideAHeroId = "richard_lionheart",
                sideAObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideAObjectiveDesc = LocalizedText("Breach the Accursed Tower of Acre (2,3) while holding off Saladin's relief army in the east!", "Sharqdan Salohiddin hujumini qaytarib turgan holda Akka qal'asini (2,3) egallang!", "Возьмите Проклятую башню Акры (2,3), сдерживая армию Саладина с востока!"),
                sideBFaction = FactionId.AYYUBID,
                sideBName = LocalizedText("Saladin's Field Army & Qaraqosh's Acre Garrison", "Salohiddin qo'shini va Qoraqush garnizoni", "Армия Саладина и гарнизон Каракуша"),
                sideBHeroId = "saladin",
                sideBObjectiveType = ObjectiveType.BREAK_SIEGE,
                sideBObjectiveDesc = LocalizedText("Link Saladin's eastern relief army with the Acre Citadel (2,3) or rout the Crusaders.", "Salohiddin qo'shinini Akka qal'asi (2,3) bilan tutashtiring yoki qamalchilarni yanching.", "Деблокируйте гарнизон Акры (2,3) ударом с двух сторон."),
                targetHex = HexCoord(2, 3),
                surviveTurnsTarget = 10,
                briefing = LocalizedText(
                    "For nearly two years outside the coastal port of Acre, a unique tactical situation unfolded: the Crusaders besieged the Ayyubid garrison under Baha ad-Din Qaraqosh inside Acre, while Saladin camped on the hills to the east and besieged the Crusaders in turn! With the arrival of King Philip II of France and King Richard I of England in 1191, massive counterweight trebuchets ('God's Own Sling' and 'Bad Neighbor') pounded the Accursed Tower while sappers dug beneath the walls.",
                    "Ikki yil davomida Akka bandargohi oldida noyob harbiy holat yuzaga keldi: salibchilar shahar ichidagi Bahovuddin Qoraqush garnizonini qamal qilgan bo'lsa, sharqdagi tepaliklarda Salohiddin Ayyubiy salibchilarning o'zini tashqi halqada qamalga oldi! 1191-yilda Rixard Sheryurak va Filipp II yetib kelgach, ulkan manjaniqlar va lahimchilar «La'natlangan minora»ni nishonga oldi.",
                    "Почти два года у прибрежной Акры длилась уникальная двойная осада: крестоносцы осаждали гарнизон Баха ад-Дина Каракуша в городе, а Салах ад-Дин с восточных холмов осаждал лагерь самих крестоносцев."
                ),
                whatReallyHappened = LocalizedText(
                    "Despite Ali ibn Ahmad al-Jazari's naphtha recipes burning the first siege towers, the combined bombardment and sapping breached the Accursed Tower in July 1191 before Saladin could break the outer trench line.",
                    "Shahar ichidan naft olovi bilan dastlabki qamal minoralari yoqib yuborilganiga qaramay, 1191-yil iyulda og'ir manjaniqlar va lahimchilar devorni yorib o'tdi.",
                    "Несмотря на сожжение первых осадных башен горючей нефтью, в июле 1191 года подкопы и требюше разрушили угловую башню до прорыва внешнего вала."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The two-front geometry (Acre garrison on the west coast, Crusader trench ring in the middle, Saladin's relief army on the eastern hills).",
                    "Tarixiy haqiqat: Ikki frontli joylashuv — g'arbda Akka qal'asi, o'rtada qamalchilar xandaqi va sharqda Salohiddin qo'shini.",
                    "Исторично: Геометрия боя на два фронта — крепость Акра на западе, осадный лагерь в центре и войска Саладина на восточных холмах."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Places both the Acre garrison and Saladin's field force under Side B's unified turn control for a gripping two-front battle.",
                    "O'yin moslashuvi: Ham qal'a garnizoni, ham Salohiddin yordamchi qo'shini yagona taktik boshqaruvda harakat qiladi.",
                    "Игровое упрощение: Гарнизон Акры и полевая армия Саладина управляются совместно с двух направлений."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Al-Nawadir al-Sultaniyya & Itinerarium Peregrinorum",
                        authorOrPeriod = "Baha ad-Din ibn Shaddad / Anonymous Third Crusade Chronicle",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Both eyewitness chronicles detail the double trench system, naphtha fire, and named trebuchets at Acre.", "Har ikki guvoh manba Akkadagi ikki qavatli xandaqlar va nomdor manjaniqlarni tasvirlaydi.", "Оба очевидца описывают двойные линии окопов, нефтяной огонь и огромные требюше.")
                    )
                ),
                reviewedBy = "Levant & Crusades Historical Schema v1.0",
                whatIfTitle = LocalizedText("What If Saladin's Heavy Mamluks Breached the Outer Trench Simultaneously with Qaraqosh's Sortie?", "Agar Salohiddin va Qoraqush bir vaqtda ikki tomondan yorib kirganida?", "Что если бы Саладин и гарнизон Акры ударили одновременно?"),
                whatIfDescription = LocalizedText("Hypothetical: Coordinated Ayyubid pincer with bonus Mamluk cavalry and siege sappers.", "Faraziy holat: Qal'a ichidan va sharqiy tepaliklardan bir vaqtda kuchaytirilgan Mamluk suvorilari zarba beradi.", "Гипотеза: Одновременный встречный удар усиленной конницы мамлюков и гарнизона."),
                whatIfSideBBonusUnits = listOf(UnitSpawn("mamluk_cavalry", 7, 3)),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(0, 0, TerrainType.RIVER, "Mediterranean Sea"),
                    TerrainTileSpec(0, 1, TerrainType.RIVER),
                    TerrainTileSpec(0, 2, TerrainType.RIVER),
                    TerrainTileSpec(0, 3, TerrainType.RIVER),
                    TerrainTileSpec(1, 3, TerrainType.FORTRESS, "Acre Port Citadel"),
                    TerrainTileSpec(2, 2, TerrainType.CITY_WALLS),
                    TerrainTileSpec(2, 3, TerrainType.CITY_WALLS, "Accursed Tower"),
                    TerrainTileSpec(2, 4, TerrainType.CITY_WALLS),
                    TerrainTileSpec(6, 2, TerrainType.HILLS, "Crusader Counter-Vallation"),
                    TerrainTileSpec(6, 4, TerrainType.HILLS, "Crusader Counter-Vallation"),
                    TerrainTileSpec(8, 3, TerrainType.HILLS, "Hill of Toron")
                ),
                sideASpawns = listOf(
                    UnitSpawn("european_knights", 5, 3, isHero = true, heroId = "richard_lionheart"),
                    UnitSpawn("common_trebuchet", 4, 2, customName = "Bad Neighbor Trebuchet"),
                    UnitSpawn("common_engineers", 3, 3),
                    UnitSpawn("men_at_arms", 4, 4),
                    UnitSpawn("pikemen", 6, 2),
                    UnitSpawn("crossbowmen", 6, 4),
                    UnitSpawn("european_knights", 5, 2)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("mamluk_cavalry", 8, 3, isHero = true, heroId = "saladin"),
                    UnitSpawn("saracen_infantry", 1, 3, customName = "Qaraqosh's Citadel Guard"),
                    UnitSpawn("ghazi_archers", 2, 2),
                    UnitSpawn("saracen_infantry", 2, 4),
                    UnitSpawn("mamluk_cavalry", 8, 2),
                    UnitSpawn("steppe_horse_archer", 7, 4, customName = "Turcoman Horse Archers"),
                    UnitSpawn("camel_riders", 8, 5)
                )
            ),

            // Mission 4: Battle of Arsuf, 1191
            MissionDefinition(
                id = "mission_arsuf_1191",
                campaignId = "campaign_crusades",
                orderIndex = 4,
                year = 1191,
                displayDate = "7 September 1191",
                locationName = LocalizedText("Coastal Plain & Forest of Arsuf", "Arsuf o'rmoni va dengiz sohili", "Прибрежная равнина и лес Арсуфа"),
                worldMapX = 0.27f,
                worldMapY = 0.56f,
                title = LocalizedText("4. Battle of Arsuf (1191)", "4. Arsuf jangi (1191)", "4. Битва при Арсуфе (1191)"),
                subtitle = LocalizedText("Richard the Lionheart's Fighting March vs. Saladin's Skirmishers", "Rixard Sheryurakning intizomli yurishi Salohiddinning otliq kamondorlariga qarshi", "Боевой марш Ричарда Львиное Сердце против конных стрелков Саладина"),
                weather = WeatherType.CLEAR,
                maxTurns = 13,
                sideAFaction = FactionId.CRUSADER,
                sideAName = LocalizedText("Crusader Coastal Column (Richard I)", "Rixard I ning sohil kolonnasi", "Прибрежная колонна Ричарда I"),
                sideAHeroId = "richard_lionheart",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Maintain formation against horse-archer volleys and deliver a decisive cavalry counter-charge.", "Otliq kamondorlar zarbasiga dosh berib, og'ir ritsarlar bilan qarshi hujumga o'ting.", "Выдержите обстрел конных лучников и нанесите сокрушительный контрудар рыцарями."),
                sideBFaction = FactionId.AYYUBID,
                sideBName = LocalizedText("Saladin's Mobile Field Army", "Salohiddinning harakatchan qo'shini", "Мобильная армия Салах ад-Дина"),
                sideBHeroId = "saladin",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Lure the Hospitaller knights out of their infantry shield screen and destroy the column in detail.", "Ritsarlarni piyodalar qalqoni ortidan chiqishga majbur qilib, yakka-yakka tor-mor eting.", "Выманите рыцарей из-под прикрытия пехоты и разбейте колонну по частям."),
                targetHex = null,
                briefing = LocalizedText(
                    "Marching south from Acre toward Jaffa with the Mediterranean Sea protecting his right flank, Richard I placed a screen of spearmen and crossbowmen on the landward side to shield his knights' horses from Saladin's mounted archers. Near the Forest of Arsuf on 7 September 1191, Saladin intensified the pressure on the Hospitaller rearguard, hoping to provoke an uncoordinated charge that he could surround.",
                    "O'rta yer dengizi sohilini o'ng qanot qalqoni sifatida ushlab Yaffa tomon harakatlangan Rixard I otlarni o'qdan asrash uchun tashqi qatorga nayzadorlar va arbaletchilarni joylashtirdi. 1191-yil 7-sentabrda Arsuf o'rmoni yaqinida Salohiddin Ayyubiy orqa safdagi gospitalyer ritsarlariga bosimni kuchaytirib, ularni tartibsiz hujumga chorlashga urindi.",
                    "Двигаясь вдоль берега моря к Яффе, Ричард I прикрыл конницу плотной завесой копейщиков и арбалетчиков. 7 сентября 1191 года у леса Арсуф Салах ад-Дин усилил натиск на арьергард госпитальеров, стремясь спровоцировать преждевременную атаку."
                ),
                whatReallyHappened = LocalizedText(
                    "When the hard-pressed Hospitallers finally charged slightly ahead of the six-trumpet signal, Richard immediately rode forward to turn the premature sally into a unified, three-wave heavy cavalry charge—driving Saladin's army back into the Forest of Arsuf while both commanders preserved their main forces.",
                    "Gospitalyerlar karnay signalidan biroz oldin ot qo'yganda, Rixard darhol butun safni yagona zarbaga yo'naltirib, tarqoqlikning oldini oldi va Salohiddin suvorilarini Arsuf o'rmoniga chekinishga majbur qildi.",
                    "Когда госпитальеры не выдержали и бросились в атаку чуть раньше сигнала труб, Ричард мгновенно поддержал их общим ударом всей тяжелой конницы, оттеснив войска Саладина к лесу Арсуфа."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The sea-flank geometry, the crossbow-and-spear screen protecting the knights, and the Woods of Arsuf on the eastern flank.",
                    "Tarixiy haqiqat: Dengiz bo'yi joylashuvi, ritsarlarni himoyalovchi arbaletchi-nayzadorlar qatori va sharqdagi Arsuf o'rmoni.",
                    "Исторично: Прижатый к морю фланг, пехотный заслон арбалетчиков и лес Арсуфа на востоке."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Lets either player execute the historic discipline test—hold the box as Richard or break the rearguard as Saladin.",
                    "O'yin moslashuvi: Har ikki tomon uchun o'z davrining asosiy taktik vazifasini bajarish imkonini beradi.",
                    "Игровое упрощение: Позволяет отыграть тактическую дуэль с любой из двух сторон."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Itinerarium Peregrinorum et Gesta Regis Ricardi",
                        authorOrPeriod = "Early 13th Century",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Describes the marching box formation, the Hospitallers' plea to charge, and the six trumpets signal.", "Intizomli yurish qutisi va olti karnay signali haqidagi batafsil ma'lumot.", "Подробно описывает походный строй и сигнал шести труб к общей атаке.")
                    ),
                    HistoricalSource(
                        title = "Al-Nawadir al-Sultaniyya",
                        authorOrPeriod = "Baha ad-Din ibn Shaddad (c. 1228)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Praises the iron-like discipline of the Frankish infantry marching under constant arrow fire and the ferocity of the cavalry charge.", "Tinimsiz o'q yomg'iri ostida yurgan piyodalarning intizomi va otliq hamlasini xolis tasvirlaydi.", "Отмечает железную выдержку пехоты под обстрелом и мощь конного удара.")
                    )
                ),
                reviewedBy = "Levant & Crusades Historical Schema v1.0",
                whatIfTitle = LocalizedText("What If the Hospitallers Charged Alone Without Richard's Support?", "Agar gospitalyerlar Rixard yordamisiz yolg'iz o'zi pistirmaga tushganida?", "Что если бы арьергард госпитальеров оторвался от главных сил?"),
                whatIfDescription = LocalizedText("Hypothetical: The Crusader rearguard starts extended forward while Saladin brings extra Mamluk squadrons from the forest.", "Faraziy holat: Salibchilar orqa qismi oldinga uzilib chiqib qoladi, Salohiddin esa o'rmondan qo'shimcha Mamluk suvorilarini tashlaydi.", "Гипотеза: Арьергард крестоносцев выдвинут вперед под фланговый удар мамлюков из леса."),
                whatIfSideBBonusUnits = listOf(
                    UnitSpawn("mamluk_cavalry", 6, 1),
                    UnitSpawn("steppe_horse_archer", 6, 5)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(0, 0, TerrainType.RIVER, "Mediterranean Sea"),
                    TerrainTileSpec(0, 1, TerrainType.RIVER),
                    TerrainTileSpec(0, 2, TerrainType.RIVER),
                    TerrainTileSpec(0, 3, TerrainType.RIVER),
                    TerrainTileSpec(0, 4, TerrainType.RIVER),
                    TerrainTileSpec(0, 5, TerrainType.RIVER),
                    TerrainTileSpec(0, 6, TerrainType.RIVER),
                    TerrainTileSpec(1, 1, TerrainType.ROAD, "Coastal Road to Jaffa"),
                    TerrainTileSpec(1, 2, TerrainType.ROAD),
                    TerrainTileSpec(1, 3, TerrainType.ROAD),
                    TerrainTileSpec(1, 4, TerrainType.ROAD),
                    TerrainTileSpec(1, 5, TerrainType.ROAD),
                    TerrainTileSpec(7, 1, TerrainType.FOREST, "Forest of Arsuf"),
                    TerrainTileSpec(7, 2, TerrainType.FOREST),
                    TerrainTileSpec(7, 3, TerrainType.FOREST),
                    TerrainTileSpec(7, 4, TerrainType.FOREST),
                    TerrainTileSpec(8, 2, TerrainType.HILLS),
                    TerrainTileSpec(8, 4, TerrainType.HILLS)
                ),
                sideASpawns = listOf(
                    UnitSpawn("european_knights", 2, 3, isHero = true, heroId = "richard_lionheart"),
                    UnitSpawn("european_knights", 2, 1, customName = "Knights Hospitaller Rearguard"),
                    UnitSpawn("european_knights", 2, 5, customName = "Knights Templar Vanguard"),
                    UnitSpawn("crossbowmen", 3, 2),
                    UnitSpawn("crossbowmen", 3, 4),
                    UnitSpawn("pikemen", 3, 3)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("mamluk_cavalry", 8, 3, isHero = true, heroId = "saladin"),
                    UnitSpawn("steppe_horse_archer", 5, 2, customName = "Turcoman Mounted Archers"),
                    UnitSpawn("steppe_horse_archer", 5, 4, customName = "Mounted Skirmishers"),
                    UnitSpawn("mamluk_cavalry", 6, 3),
                    UnitSpawn("camel_riders", 5, 1),
                    UnitSpawn("ghazi_archers", 6, 4),
                    UnitSpawn("saracen_infantry", 7, 3)
                )
            )
        )
    )

    val campaignEuropeBonus = CampaignDefinition(
        id = "campaign_europe_bonus",
        title = LocalizedText("Bonus Campaign: European Clashes", "Bonus janglar: Yevropa (1066 va 1415)", "Бонусные битвы: Европа (1066 и 1415)"),
        subtitle = LocalizedText("Senlac Hill at Hastings (1066) & the Mud of Agincourt (1415)", "Gastings (1066) va Azinkur (1415) tarixiy janglari", "Гастингс (1066) и Азенкур (1415)"),
        eraRange = "1066 & 1415 CE",
        century = 11,
        description = LocalizedText(
            "Two iconic tactical showdowns of medieval Europe: test the Anglo-Saxon shield wall against Norman feigned retreats at Hastings (1066), and pit English longbowmen against French heavy chivalry in the mud of Agincourt (1415).",
            "O'rta asr Yevropasining ikki mashhur taktik to'qnashuvi: Gastingsdagi (1066) qalqon devori va Azinkurdagi (1415) uzun kamonchilar jangi.",
            "Два знаменитых тактических сражения средневековой Европы: стена щитов при Гастингсе (1066) и длинные луки при Азенкуре (1415)."
        ),
        musicThemeId = "theme_europe_strings",
        primaryColorHex = 0xFF7C2E1E,
        missions = listOf(
            // Bonus 1: Hastings 1066
            MissionDefinition(
                id = "mission_hastings_1066",
                campaignId = "campaign_europe_bonus",
                orderIndex = 1,
                year = 1066,
                displayDate = "14 October 1066",
                locationName = LocalizedText("Senlac Hill near Hastings, Sussex", "Gastings yaqinidagi Senlak tepaligi", "Сенлакский холм у Гастингса"),
                worldMapX = 0.08f,
                worldMapY = 0.31f,
                title = LocalizedText("1. Battle of Hastings (1066)", "1. Gastings jangi (1066)", "1. Битва при Гастингсе (1066)"),
                subtitle = LocalizedText("Harold Godwinson's Shield Wall vs. William of Normandy's Feigned Retreats", "Qirol Garoldning qalqon devori va gersog Vilgelmning yolg'on chekinish usuli", "Стена щитов короля Гарольда против притворных отступлений Вильгельма"),
                weather = WeatherType.CLEAR,
                maxTurns = 13,
                sideAFaction = FactionId.NORMAN,
                sideAName = LocalizedText("Duke William's Franco-Norman Army", "Gersog Vilgelmning normand qo'shini", "Нормандское войско герцога Вильгельма"),
                sideAHeroId = "william_harold",
                sideAObjectiveType = ObjectiveType.CAPTURE_HEX,
                sideAObjectiveDesc = LocalizedText("Use archers and feigned cavalry retreats to lure the Huscarls off Senlac Ridge (3,3).", "Kamondorlar va yolg'on chekinish bilan Senlak tepaligidagi (3,3) qalqon devorini yorib o'ting.", "С помощью стрелков и ложных отступлений возьмите вершину Сенлакского холма (3,3)."),
                sideBFaction = FactionId.ANGLO_SAXON,
                sideBName = LocalizedText("King Harold II's Huscarls & Fyrd", "Qirol Garold II ning anglo-sakson qo'shini", "Англосаксонская рать короля Гарольда II"),
                sideBHeroId = "william_harold",
                sideBObjectiveType = ObjectiveType.SURVIVE_TURNS,
                sideBObjectiveDesc = LocalizedText("Hold the Senlac Hill Shield Wall (3,3) for 10 turns or rout Duke William's army!", "Senlak tepaligidagi (3,3) qalqon devorini 10 yurish davomida buzmang yoki normandlarni tor-mor eting!", "Удерживайте стену щитов на Сенлакском холме (3,3) 10 ходов или разбейте нормандцев!"),
                targetHex = HexCoord(3, 3),
                surviveTurnsTarget = 10,
                briefing = LocalizedText(
                    "Just nineteen days after defeating Harald Hardrada at Stamford Bridge in Yorkshire, King Harold Godwinson marched south and formed a dense shield wall of axe-wielding Huscarls atop Senlac Hill. For hours on 14 October 1066, Duke William's uphill infantry and cavalry assaults were repelled—and a rumor arose that William had been slain, forcing the Duke to push back his nasal helmet and rally his troops before ordering two deliberate feigned retreats.",
                    "Stamford-Brij jangidan atigi 19 kun o'tib janubga yetib kelgan qirol Garold II Senlak tepaligida oyboltali xuskarllardan zich «qalqon devori» tuzdi. 1066-yil 14-oktabrda gersog Vilgelmning tepalikka qilgan dastlabki hujumlari qaytarildi; hatto Vilgelm halok bo'ldi degan mish-mish tarqalgach, gersog dubulg'asini ko'tarib tirikligini ko'rsatdi va «yolg'on chekinish» hiylasi bilan anglo-saksonlarni tepalikdan pastga tushishga undadi.",
                    "Всего через девятнадцать дней после победы при Стамфорд-Бридже король Гарольд выстроил плотную стену щитов на вершине Сенлакского холма. Отразив первые штурмы и развеяв слух о своей гибели снятием шлема, герцог Вильгельм применил притворные отступления конницы, выманив часть саксов с высоты."
                ),
                whatReallyHappened = LocalizedText(
                    "Tempted down the hill to pursue the retreating Norman horsemen, segments of the Anglo-Saxon fyrd were surrounded in the valley, thinning the shield wall on the crest until Harold fell late in the afternoon.",
                    "Chekinayotgan normand otliqlarini quvib tepalikdan pastga tushgan bo'linmalar tekislikda o'rab olindi; natijada tepalikdagi qalqon devori siyraklashib, oqshomga yaqin qirol Garold halok bo'ldi.",
                    "Спустившиеся с холма в погоню за конницей отряды ополчения были окружены на равнине, что ослабило стену щитов на гребне, где к вечеру пал король Гарольд."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The steep terrain advantage of Senlac Hill, the Anglo-Saxon lack of cavalry, and the Norman combination of archers and feigned retreats.",
                    "Tarixiy haqiqat: Senlak tepaligining balandlik ustunligi, anglo-saksonlarning piyoda qalqon devori va normandlarning yolg'on chekinish usuli.",
                    "Исторично: Преимущество высоты Сенлакского холма, пеший строй хускарлов и ложные отступления нормандской конницы."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Playing as Harold lets you enforce strict discipline on the hill crest and refuse the bait.",
                    "O'yin moslashuvi: Garold tomonida o'ynaganda qalqon devori intizomini saqlab, tuzoqqa tushmaslik sizning qo'lingizda.",
                    "Игровое упрощение: Играя за Гарольда, вы можете удержать строй на высоте, не поддаваясь на уловку."
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Bayeux Tapestry & Gesta Guillelmi",
                        authorOrPeriod = "c. 1070s / William of Poitiers",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Depicts the shield wall on the hill, William raising his helmet, and the fall of Harold.", "Qalqon devori, Vilgelmning dubulg'asini ko'tarishi va Garoldning halokatini aks ettiradi.", "Изображает стену щитов, жест Вильгельма со шлемом и гибель Гарольда.")
                    ),
                    HistoricalSource(
                        title = "Anglo-Saxon Chronicle (Worcester Manuscript D)",
                        authorOrPeriod = "11th Century",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Records the exhausting forced march from Stamford Bridge before meeting William.", "Stamford-Brijdan Gastingsgacha bo'lgan mashaqqatli yurishni qayd etadi.", "Описывает стремительный переход войска Гарольда с севера перед битвой.")
                    )
                ),
                reviewedBy = "Medieval European Tactical Schema v1.0",
                whatIfTitle = LocalizedText("What If Edwin and Morcar's Northern Earldoms Reached Senlac Hill in Time?", "Agar Edvin va Morkarning shimoliy qo'shini o'z vaqtida yetib kelganida?", "Что если бы северные эрлы Эдвин и Моркар успели к Гастингсу?"),
                whatIfDescription = LocalizedText("Hypothetical: Fresh Anglo-Saxon huscarls and archers reinforce Harold's flanks on Senlac Ridge.", "Faraziy holat: Qirol Garold qanotlariga qo'shimcha dam olgan xuskarllar va kamondorlar qo'shiladi.", "Гипотеза: Свежие отряды хускарлов и лучников усиливают фланги Гарольда на холме."),
                whatIfSideBBonusUnits = listOf(
                    UnitSpawn("men_at_arms", 3, 1),
                    UnitSpawn("common_archers", 2, 5)
                ),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(3, 1, TerrainType.HILLS, "Senlac North Crest"),
                    TerrainTileSpec(3, 2, TerrainType.HILLS, "Senlac Shield Wall"),
                    TerrainTileSpec(3, 3, TerrainType.HILLS, "Dragon Banner Crest"),
                    TerrainTileSpec(3, 4, TerrainType.HILLS, "Senlac Shield Wall"),
                    TerrainTileSpec(3, 5, TerrainType.HILLS, "Senlac South Crest"),
                    TerrainTileSpec(2, 3, TerrainType.HILLS),
                    TerrainTileSpec(4, 0, TerrainType.FOREST),
                    TerrainTileSpec(4, 6, TerrainType.FOREST)
                ),
                sideASpawns = listOf(
                    UnitSpawn("european_knights", 7, 3, isHero = true, heroId = "william_harold", customName = "Duke William of Normandy"),
                    UnitSpawn("european_knights", 6, 2, customName = "Franco-Norman Knights"),
                    UnitSpawn("steppe_light_cavalry", 6, 4, customName = "Breton Mounted Flank"),
                    UnitSpawn("common_archers", 5, 2),
                    UnitSpawn("crossbowmen", 5, 4),
                    UnitSpawn("common_swordsmen", 6, 3)
                ),
                sideBSpawns = listOf(
                    UnitSpawn("men_at_arms", 3, 3, isHero = true, heroId = "william_harold", customName = "King Harold Godwinson"),
                    UnitSpawn("men_at_arms", 3, 2, customName = "Royal Huscarls"),
                    UnitSpawn("men_at_arms", 3, 4, customName = "Royal Huscarls"),
                    UnitSpawn("common_spearmen", 3, 1, customName = "Anglo-Saxon Fyrd"),
                    UnitSpawn("common_spearmen", 3, 5, customName = "Anglo-Saxon Fyrd"),
                    UnitSpawn("common_archers", 2, 3)
                )
            ),

            // Bonus 2: Agincourt 1415
            MissionDefinition(
                id = "mission_agincourt_1415",
                campaignId = "campaign_europe_bonus",
                orderIndex = 2,
                year = 1415,
                displayDate = "25 October 1415",
                locationName = LocalizedText("Muddy Defile between Agincourt & Tramecourt Woods", "Azinkur va Tramekuro'rmonlari oralig'i", "Поле между лесами Азенкур и Трамекур"),
                worldMapX = 0.11f,
                worldMapY = 0.34f,
                title = LocalizedText("2. Battle of Agincourt (1415)", "2. Azinkur jangi (1415)", "2. Битва при Азенкуре (1415)"),
                subtitle = LocalizedText("Henry V's Longbowmen & Wooden Stakes in Deep Autumn Mud", "Genrix V ning uzun kamonchilari va loyqa tor yo'lakdagi jang", "Английские лучники Генриха V против французской конницы в грязи"),
                weather = WeatherType.MUD,
                maxTurns = 13,
                sideAFaction = FactionId.ENGLAND,
                sideAName = LocalizedText("King Henry V's English Army", "Qirol Genrix V ning ingliz qo'shini", "Английская армия короля Генриха V"),
                sideAHeroId = "henry_v",
                sideAObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideAObjectiveDesc = LocalizedText("Funnel the French heavy cavalry between the woods and decimate them with longbow volleys.", "Ikki o'rmon oralig'ida fransuz ritsarlarini uzun kamon o'qlari bilan to'xtating.", "Зажмите французскую конницу между лесами и расстреляйте из длинных луков."),
                sideBFaction = FactionId.FRANCE,
                sideBName = LocalizedText("French Royal Host (Constable d'Albret & Boucicaut)", "Fransiya qirollik qo'shini", "Французская королевская армия"),
                sideBHeroId = "richard_lionheart",
                sideBObjectiveType = ObjectiveType.DESTROY_ARMY,
                sideBObjectiveDesc = LocalizedText("Coordinate your crossbows and men-at-arms to overrun the outnumbered English line.", "Arbaletchilar va zirhli piyodalarni muvofiqlashtirib, ingliz safini yorib o'ting.", "Скоординируйте удар арбалетчиков и латников, чтобы смять английский строй."),
                targetHex = null,
                briefing = LocalizedText(
                    "Intercepted on his march to Calais after the siege of Harfleur, King Henry V deployed his tired, outnumbered army across a freshly plowed field narrowed on both flanks by the dense woods of Agincourt and Tramecourt. Overnight torrential rain turned the clay soil into treacherous mud. Driving six-foot sharpened stakes into the ground at an angle, English and Welsh longbowmen provoked the French vanguard into charging down the muddy funnel.",
                    "Kaledagi port tomon ketayotgan qirol Genrix V ning charchagan va oz sonli qo'shini Azinkur hamda Tramekur o'rmonlari orasidagi tor, yangi haydalgan maydonda yo'li to'sildi. Tungi jala tuproqni botqoq loyga aylantirdi. Ingliz va uelslik uzun kamonchilar yerga uchli yog'och qoziqlar qoqib, og'ir zirhli fransuz ritsarlarini loyqa yo'lakdan hujum qilishga majbur etdi.",
                    "Перехваченный на пути к Кале король Генрих V выстроил малочисленное войско на вспаханном поле, зажатом между лесами Азенкур и Трамекур. Ночной ливень превратил пашню в вязкую грязь, где английские лучники за вбитыми кольями встретили лобовую атаку французской тяжелой конницы и спешенных латников."
                ),
                whatReallyHappened = LocalizedText(
                    "Crowded together by the narrowing woods and weighed down by 60 pounds of plate armor in knee-deep mud, the French men-at-arms could barely raise their weapons when the lightly armored English archers dropped their bows and struck the flanks with mallets, axes, and swords.",
                    "Torayib boruvchi o'rmon yo'lagida tiqilib qolgan va og'ir zirhda loyga botgan fransuz ritsarlari harakatlana olmay qoldi; shunda yengil qurollangan kamonchilar yon qanotlardan bolta va qilichlar bilan hamla qilib g'alabani ta'minladi.",
                    "Сжатые узким проходом между лесами и увязшие по колено в грязи в тяжелых латах, французские рыцари попали под фланговый удар легких английских лучников с топорами и мечами."
                ),
                factVsGameAccurate = LocalizedText(
                    "Accurate: The wooded funnel on both flanks, the deep mud penalty on heavy armor/cavalry, and the range superiority of the yew longbow.",
                    "Tarixiy haqiqat: Ikki qanotdagi qalin o'rmon, chuqur loyning og'ir zirhlilarga ta'siri va uzun kamonlarning uzoq masofali zarbasi.",
                    "Исторично: Узкое дефиле между двумя лесами, штраф глубокой грязи и дальнобойность тисовых луков."
                ),
                factVsGameChanged = LocalizedText(
                    "Game Adaptation: Playing as France allows you to bring your crossbowmen forward first as Constable d'Albret originally intended!",
                    "O'yin moslashuvi: Fransiya tomonida o'ynaganda dastlab arbaletchilarni oldinga chiqarib, shoshma-shosharlik bilan hujum qilish xatosidan qochish mumkin!",
                    "Игровое упрощение: Играя за Францию, вы можете сначала выдвинуть арбалетчиков, как изначально планировал коннетабль д'Альбре!"
                ),
                sources = listOf(
                    HistoricalSource(
                        title = "Gesta Henrici Quinti",
                        authorOrPeriod = "Royal Chaplain Eyewitness (c. 1417)",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("Written by a priest present in the English baggage train at Agincourt.", "Azinkur jangida bevosita qatnashgan muallifning guvohlik asari.", "Хроника очевидца, находившегося в английском лагере при Азенкуре.")
                    ),
                    HistoricalSource(
                        title = "Chronique d'Enguerrand de Monstrelet & Jean Juvénal des Ursins",
                        authorOrPeriod = "15th Century",
                        reliability = SourceReliability.DOCUMENTED,
                        note = LocalizedText("French chronicles explaining how the noble vanguard pushed ahead of the crossbowmen in the deep mud.", "Fransuz ritsarlari arbaletchilarni ortda qoldirib loyga botib qolganini tushuntiruvchi manbalar.", "Французские хроники о том, как рыцарский авангард потеснил собственных стрелков в грязи.")
                    )
                ),
                reviewedBy = "Medieval European Tactical Schema v1.0",
                whatIfTitle = LocalizedText("What If Marshal Boucicaut's Battle Plan Was Followed on Firm Ground?", "Agar marshal Busikoning ehtiyotkor rejasi quruq yerda amalga oshirilganida?", "Что если бы план маршала Бусико был выполнен на сухом поле?"),
                whatIfDescription = LocalizedText("Hypothetical: Clear weather with Genoese crossbowmen deployed ahead of the French knights.", "Faraziy holat: Quruq havoda genuyalik arbaletchilar fransuz ritsarlari oldida tartibli saf tortadi.", "Гипотеза: Сухая погода и развернутые в первой линии генуэзские арбалетчики."),
                whatIfWeatherOverride = WeatherType.CLEAR,
                whatIfSideBBonusUnits = listOf(UnitSpawn("crossbowmen", 5, 3)),
                mapWidth = 10,
                mapHeight = 7,
                defaultTerrain = TerrainType.PLAINS,
                terrainOverrides = listOf(
                    TerrainTileSpec(2, 0, TerrainType.FOREST, "Agincourt Woods"),
                    TerrainTileSpec(3, 0, TerrainType.FOREST),
                    TerrainTileSpec(4, 0, TerrainType.FOREST),
                    TerrainTileSpec(5, 0, TerrainType.FOREST),
                    TerrainTileSpec(3, 1, TerrainType.FOREST),
                    TerrainTileSpec(4, 1, TerrainType.FOREST),
                    TerrainTileSpec(2, 6, TerrainType.FOREST, "Tramecourt Woods"),
                    TerrainTileSpec(3, 6, TerrainType.FOREST),
                    TerrainTileSpec(4, 6, TerrainType.FOREST),
                    TerrainTileSpec(5, 6, TerrainType.FOREST),
                    TerrainTileSpec(3, 5, TerrainType.FOREST),
                    TerrainTileSpec(4, 5, TerrainType.FOREST),
                    TerrainTileSpec(2, 2, TerrainType.HILLS, "Wooden Stakes"),
                    TerrainTileSpec(2, 4, TerrainType.HILLS, "Wooden Stakes")
                ),
                sideASpawns = listOf(
                    UnitSpawn("men_at_arms", 2, 3, isHero = true, heroId = "henry_v"),
                    UnitSpawn("longbowmen", 2, 2, customName = "Welsh & English Longbowmen"),
                    UnitSpawn("longbowmen", 2, 4, customName = "Staked Longbow Wing"),
                    UnitSpawn("longbowmen", 1, 2),
                    UnitSpawn("longbowmen", 1, 4),
                    UnitSpawn("men_at_arms", 1, 3, customName = "Duke of York's Vanguard")
                ),
                sideBSpawns = listOf(
                    UnitSpawn("european_knights", 7, 3, isHero = true, heroId = "richard_lionheart", customName = "Constable d'Albret"),
                    UnitSpawn("european_knights", 6, 2, customName = "French Mounted Chivalry"),
                    UnitSpawn("european_knights", 6, 4, customName = "Vendome Wing"),
                    UnitSpawn("men_at_arms", 6, 3, customName = "Dismounted French Nobles"),
                    UnitSpawn("men_at_arms", 7, 2),
                    UnitSpawn("pikemen", 7, 4),
                    UnitSpawn("crossbowmen", 8, 3, customName = "Rear Crossbowmen")
                )
            )
        )
    )
}
