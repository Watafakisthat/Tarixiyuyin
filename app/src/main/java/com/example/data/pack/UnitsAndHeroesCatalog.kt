package com.example.data.pack

import com.example.model.ChronicleCityEntry
import com.example.model.ChronicleTimelineEvent
import com.example.model.Era
import com.example.model.FactionId
import com.example.model.HeroActiveEffectType
import com.example.model.HeroDefinition
import com.example.model.LocalizedText
import com.example.model.SpecialAbilityType
import com.example.model.UnitArchetype
import com.example.model.UnitCategory

object UnitsAndHeroesCatalog {

    val allUnits: List<UnitArchetype> = listOf(
        // STEPPE ERA UNITS
        UnitArchetype(
            id = "steppe_horse_archer",
            name = LocalizedText("Steppe Horse Archers", "Dasht otliq kamondorlari", "Степные конные лучники"),
            era = Era.STEPPE,
            category = UnitCategory.HORSE_ARCHER,
            maxHp = 85,
            attack = 28,
            defense = 14,
            movement = 4,
            range = 2,
            specialAbility = SpecialAbilityType.PARTHIAN_SHOT,
            cost = 120,
            historicalNote = LocalizedText(
                "Armed with recurve composite bows of horn, wood, and sinew; capable of loosing arrows at full gallop and retreating before heavy cavalry can close.",
                "Shox va paydan yasalgan murakkab kamon bilan qurollangan; chopib ketayotib o'q uzadi va dushman yetib kelmasdan chekinadi.",
                "Вооружены композитными луками из рога и сухожилий; стреляют на полном скаку и уходят от удара тяжелой конницы."
            )
        ),
        UnitArchetype(
            id = "steppe_heavy_cavalry",
            name = LocalizedText("Nokor / Keshig Heavy Lancers", "Navkar og'ir otliqlari", "Тяжелые уланы нукеры"),
            era = Era.STEPPE,
            category = UnitCategory.HEAVY_CAVALRY,
            maxHp = 115,
            attack = 34,
            defense = 24,
            movement = 3,
            range = 1,
            specialAbility = SpecialAbilityType.LANCE_CHARGE,
            cost = 150,
            historicalNote = LocalizedText(
                "Armored in iron or hardened leather lamellar (khatangu degel), delivering the decisive shock charge after horse archers disorder the enemy line.",
                "Temir va qalin charm sovut kiygan saralangan navkarlar; kamondorlar dushman safini buzganidan so'ng hal qiluvchi zarba beradi.",
                "Облаченные в ламеллярные доспехи всадники, наносящие решающий копейный удар после обстрела."
            )
        ),
        UnitArchetype(
            id = "steppe_light_cavalry",
            name = LocalizedText("Steppe Light Lancers", "Yengil dasht otliqlari", "Легкая степная конница"),
            era = Era.STEPPE,
            category = UnitCategory.LIGHT_CAVALRY,
            maxHp = 90,
            attack = 26,
            defense = 16,
            movement = 4,
            range = 1,
            specialAbility = SpecialAbilityType.FEIGNED_RETREAT,
            cost = 100,
            historicalNote = LocalizedText(
                "Swift outriders on hardy steppe horses, masters of the feigned retreat (mangudai) that draws disciplined formations into ambushes.",
                "Chidamli dasht otlariga mingan chaqqon otliqlar; yolg'on chekinish bilan dushmanni pistirmaga tortadi.",
                "Быстрые всадники на выносливых степных конях, мастера ложного отступления."
            )
        ),
        UnitArchetype(
            id = "nomad_raiders",
            name = LocalizedText("Nomad Vanguard Raiders", "Ko'chmanchi ilg'orlar", "Кочевые налетчики"),
            era = Era.STEPPE,
            category = UnitCategory.LIGHT_CAVALRY,
            maxHp = 85,
            attack = 29,
            defense = 13,
            movement = 4,
            range = 1,
            specialAbility = SpecialAbilityType.FEIGNED_RETREAT,
            cost = 95,
            historicalNote = LocalizedText(
                "Fast-moving irregular cavalry skilled at crossing rivers on inflated hides and striking supply flanks.",
                "Daryolardan tez kechib o'tib, dushmanning orqa qismlariga qo'qqisdan zarba beruvchi ilg'or bo'linma.",
                "Стремительные отряды, умеющие переправляться через реки на бурдюках и бить по тылам."
            )
        ),

        // CENTRAL ASIAN ERA UNITS
        UnitArchetype(
            id = "khwarazmian_cavalry",
            name = LocalizedText("Khwarazmian Royal Cavalry", "Xorazmshoh xos otliqlari", "Хорезмийская гвардейская конница"),
            era = Era.CENTRAL_ASIAN,
            category = UnitCategory.HEAVY_CAVALRY,
            maxHp = 120,
            attack = 35,
            defense = 26,
            movement = 3,
            range = 1,
            specialAbility = SpecialAbilityType.LANCE_CHARGE,
            cost = 160,
            historicalNote = LocalizedText(
                "Elite armored horsemen of Gurganj and Transoxiana, famed from the Indus to the Levant for their mail-and-plate barding and fierce counter-charges.",
                "Gurganj va Movarounnahrning og'ir zirhli otliqlari; Sind daryosidan Shomgacha o'zining shiddatli hamlasi bilan tanilgan.",
                "Элитная панцирная конница Гурганджа и Мавераннахра, славившаяся мощным таранным ударом."
            )
        ),
        UnitArchetype(
            id = "sogdian_infantry",
            name = LocalizedText("Transoxianian / Sogdian Shield Infantry", "Movarounnahr qalqonli piyodalari", "Согдийская щитоносная пехота"),
            era = Era.CENTRAL_ASIAN,
            category = UnitCategory.INFANTRY,
            maxHp = 110,
            attack = 25,
            defense = 28,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.SHIELD_WALL,
            cost = 105,
            historicalNote = LocalizedText(
                "Civic militia and oasis citadel guards of Samarkand and Bukhara, trained to hold mud-brick ramparts and mountain defiles.",
                "Samarqand va Buxoro qal'alarini himoya qiluvchi qalqonli piyodalar; shahar devorlari va tog' daralarida mustahkam turadi.",
                "Ополчение и стража цитаделей Самарканда и Бухары, стойко обороняющая стены и перевалы."
            )
        ),
        UnitArchetype(
            id = "timurid_heavy_cavalry",
            name = LocalizedText("Timurid Jawshan Cataphracts", "Temuriy javshanli suvorilar", "Тимуридские катафракты в джавшанах"),
            era = Era.CENTRAL_ASIAN,
            category = UnitCategory.HEAVY_CAVALRY,
            maxHp = 130,
            attack = 38,
            defense = 29,
            movement = 3,
            range = 1,
            specialAbility = SpecialAbilityType.LANCE_CHARGE,
            cost = 175,
            historicalNote = LocalizedText(
                "Heavy household cavalry of Amir Timur wearing steel plate-and-mail jawshan armor, equally adept with the lance, mace (shashpar), and composite bow.",
                "Amir Temurning po'lat javshan kiygan saralangan suvorilari; nayza, shashpar va kamonni birdek mahorat bilan ishlatgan.",
                "Гвардейская конница Амира Тимура в стальных пластинчато-кольчужных доспехах джавшан."
            )
        ),
        UnitArchetype(
            id = "war_elephants",
            name = LocalizedText("Armored War Elephants", "Zirhli jangovar fillar", "Бронированные боевые слоны"),
            era = Era.CENTRAL_ASIAN,
            category = UnitCategory.ELEPHANT,
            maxHp = 165,
            attack = 42,
            defense = 30,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.ELEPHANT_TRAMPLE,
            cost = 210,
            historicalNote = LocalizedText(
                "Towering battle elephants carrying archers and naphtha throwers in wooden howdahs; deployed at Delhi (1398) and later by Timur at Ankara (1402).",
                "Ustiga kamondorlar joylashgan zirhli jangovar fillar; Dehli (1398) va Anqara (1402) janglarida dushman safini yorib o'tishda qo'llangan.",
                "Могучие боевые слоны с башенками для стрелков; применялись при Дели (1398) и Анкаре (1402)."
            )
        ),
        UnitArchetype(
            id = "central_asian_siege_crew",
            name = LocalizedText("Manjaniq & Naftandaz Siege Crew", "Manjaniq va naftandoz qamalchilari", "Расчеты манджаников и нафтандозов"),
            era = Era.CENTRAL_ASIAN,
            category = UnitCategory.SIEGE,
            maxHp = 80,
            attack = 36,
            defense = 12,
            movement = 2,
            range = 3,
            specialAbility = SpecialAbilityType.SAPPING,
            cost = 140,
            historicalNote = LocalizedText(
                "Specialist engineers operating traction and counterweight trebuchets (manjaniq) alongside ceramic naphtha incendiaries to breach citadel walls.",
                "Tosh otuvchi manjaniq va yonuvchi naft idishlari bilan shahar devorlarini yorib o'tuvchi muhandislar guruhi.",
                "Инженеры с камнеметами-манджаниками и зажигательной нефтью для разрушения крепостных стен."
            )
        ),

        // MIDDLE EAST ERA UNITS
        UnitArchetype(
            id = "mamluk_cavalry",
            name = LocalizedText("Mamluk Tawashi Heavy Cavalry", "Mamluk xos suvorilari", "Тяжелая конница мамлюков"),
            era = Era.MIDDLE_EAST,
            category = UnitCategory.HEAVY_CAVALRY,
            maxHp = 120,
            attack = 36,
            defense = 27,
            movement = 3,
            range = 1,
            specialAbility = SpecialAbilityType.LANCE_CHARGE,
            cost = 165,
            historicalNote = LocalizedText(
                "Rigorously trained Kipchak and Circassian elite horsemen in Ayyubid and Mamluk service, combining disciplined close-order lance charges with mounted archery.",
                "Ayyubiylar va Mamluklar xizmatidagi qipchoq hamda cherkas saralangan otliqlari; intizomli nayza zarbasi va kamon otishni uyg'unlashtirgan.",
                "Профессиональная гвардейская конница, сочетавшая сомкнутый копейный удар со стрельбой из лука."
            )
        ),
        UnitArchetype(
            id = "ghazi_archers",
            name = LocalizedText("Ghazi Composite Bowmen", "G'oziy kamondorlar", "Лучники-гази"),
            era = Era.MIDDLE_EAST,
            category = UnitCategory.RANGED,
            maxHp = 85,
            attack = 27,
            defense = 15,
            movement = 3,
            range = 2,
            specialAbility = SpecialAbilityType.VOLLEY_FIRE,
            cost = 100,
            historicalNote = LocalizedText(
                "Agile Anatolian and Levantine foot archers skilled in rapid volleys from broken ground and hillslopes.",
                "Tepalik va notekis joylardan tez o'q yomg'iri yog'diruvchi chaqqon kamondorlar.",
                "Подвижные стрелки Анатолии и Леванта, ведущие плотный обстрел с холмов."
            )
        ),
        UnitArchetype(
            id = "camel_riders",
            name = LocalizedText("Bedouin Dromedary Raiders", "Badaviy tuyakash suvorilari", "Верблюжья кавалерия"),
            era = Era.MIDDLE_EAST,
            category = UnitCategory.LIGHT_CAVALRY,
            maxHp = 95,
            attack = 27,
            defense = 18,
            movement = 4,
            range = 1,
            specialAbility = SpecialAbilityType.FEIGNED_RETREAT,
            cost = 110,
            historicalNote = LocalizedText(
                "Desert cavalry impervious to arid terrain penalties; the scent and silhouette of camels unsettles enemy horses not accustomed to them.",
                "Sahroda charchamaydigan tuyakash jangchilar; dushman otlarini hurkitib, qanotlardan zarba beradi.",
                "Пустынные всадники, не знающие усталости в песках и расстраивающие ряды вражеской конницы."
            )
        ),
        UnitArchetype(
            id = "saracen_infantry",
            name = LocalizedText("Levantine Jund & Mutatawwi'a Infantry", "Shom qalqonli piyodalari", "Пехота джунда Леванта"),
            era = Era.MIDDLE_EAST,
            category = UnitCategory.INFANTRY,
            maxHp = 105,
            attack = 26,
            defense = 24,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.SHIELD_WALL,
            cost = 95,
            historicalNote = LocalizedText(
                "Garrison and field infantry equipped with round tariqa shields, javelins, and straight or slightly curved blades.",
                "Dumaloq qalqon, nayza va qilich bilan qurollangan shahar hamda dala piyodalari.",
                "Пехота с круглыми щитами-тариками, дротиками и мечами."
            )
        ),

        // EUROPE ERA UNITS
        UnitArchetype(
            id = "european_knights",
            name = LocalizedText("Feudal Chivalric Knights", "Zirhli ritsarlar", "Феодальные рыцари"),
            era = Era.EUROPE,
            category = UnitCategory.HEAVY_CAVALRY,
            maxHp = 125,
            attack = 39,
            defense = 28,
            movement = 3,
            range = 1,
            specialAbility = SpecialAbilityType.LANCE_CHARGE,
            cost = 170,
            historicalNote = LocalizedText(
                "Heavily armored noble horsemen riding destriers; devastating in a frontal couched-lance charge on level ground, though vulnerable when bogged in mud or lured out of formation.",
                "Og'ir zirhli ritsarlar; tekis maydonda to'g'ridan-to'g'ri nayza zarbasi juda dahshatli, ammo loyda yoki saf buzilganda zaiflashadi.",
                "Тяжеловооруженные всадники на боевых конях; сокрушительны в лобовой атаке на равнине, но уязвимы в грязи."
            )
        ),
        UnitArchetype(
            id = "crossbowmen",
            name = LocalizedText("Genoese & Frankish Crossbowmen", "Arbaletchilar (Pavas qalqonli)", "Арбалетчики с павезами"),
            era = Era.EUROPE,
            category = UnitCategory.RANGED,
            maxHp = 90,
            attack = 30,
            defense = 20,
            movement = 2,
            range = 2,
            specialAbility = SpecialAbilityType.VOLLEY_FIRE,
            cost = 115,
            historicalNote = LocalizedText(
                "Protected by tall wooden pavise shields, crossbowmen fire heavy bolts that punch through mail and plate armor at medium range.",
                "Katta yog'och qalqon (paveza) ortidan zirhni teshib o'tuvchi arbalet o'qlarini uzuvchi merganlar.",
                "Прикрываясь высокими щитами-павезами, пробивают тяжелые доспехи мощными болтами."
            )
        ),
        UnitArchetype(
            id = "longbowmen",
            name = LocalizedText("English Yeoman Longbowmen", "Ingliz uzun kamonchilari", "Английские лучники (лонгбоу)"),
            era = Era.EUROPE,
            category = UnitCategory.RANGED,
            maxHp = 85,
            attack = 31,
            defense = 16,
            movement = 2,
            range = 3,
            specialAbility = SpecialAbilityType.VOLLEY_FIRE,
            cost = 125,
            historicalNote = LocalizedText(
                "Drawing six-foot yew bows behind sharpened wooden stakes, launching clouds of bodkin arrows at extreme range.",
                "O'tkir yog'och qoziqlar ortida turib, uzoq masofadan zirh teshuvchi o'qlar yog'diruvchi kamonchilar.",
                "Стрелки с тисовыми луками, ведущие дальнобойный обстрел из-за вбитых кольев."
            )
        ),
        UnitArchetype(
            id = "men_at_arms",
            name = LocalizedText("Dismounted Men-at-Arms / Huscarls", "Zirhli piyoda ritsarlar / Xuskarllar", "Спешенные латники / Хускарлы"),
            era = Era.EUROPE,
            category = UnitCategory.INFANTRY,
            maxHp = 120,
            attack = 32,
            defense = 31,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.SHIELD_WALL,
            cost = 140,
            historicalNote = LocalizedText(
                "Elite heavy foot soldiers wielding pollaxes, two-handed Dane axes, or broadswords; the unyielding anchor of European battle lines.",
                "Ikki qo'lli oybolta va og'ir qilich bilan qurollangan zirhli piyodalar; mudofaa markazining tayanchi.",
                "Отборная тяжелая пехота с двуручными секирами и полэксами — опора оборонительного строя."
            )
        ),
        UnitArchetype(
            id = "pikemen",
            name = LocalizedText("Ranked Pikemen", "Uzun nayzali piyodalar", "Пикинеры"),
            era = Era.EUROPE,
            category = UnitCategory.SPEARMEN,
            maxHp = 105,
            attack = 27,
            defense = 26,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.PIKE_PHALANX,
            cost = 105,
            historicalNote = LocalizedText(
                "Dense blocks of infantry presenting a forest of long ash pikes that halt charging cavalry in their tracks.",
                "Uzun nayzalar bilan zich saf tortib, har qanday otliq hujumini to'xtatib qoluvchi bo'linma.",
                "Сомкнутый строй с длинными пиками, способный остановить любой конный натиск."
            )
        ),

        // COMMON ERA UNITS
        UnitArchetype(
            id = "common_spearmen",
            name = LocalizedText("Levied Spearmen", "Nayzador piyodalar", "Копейщики"),
            era = Era.COMMON,
            category = UnitCategory.SPEARMEN,
            maxHp = 95,
            attack = 22,
            defense = 22,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.PIKE_PHALANX,
            cost = 75,
            historicalNote = LocalizedText(
                "The backbone of medieval infantry across Eurasia, reliable at holding river fords and protecting archers from horsemen.",
                "O'rta asr qo'shinlarining asosiy tayanchi; kechuvlarni ushlash va kamondorlarni otliqlardan himoya qilishda samarali.",
                "Основа средневековой пехоты, надежно прикрывающая переправы и лучников от конницы."
            )
        ),
        UnitArchetype(
            id = "common_archers",
            name = LocalizedText("Foot Archers", "Piyoda kamondorlar", "Пешие лучники"),
            era = Era.COMMON,
            category = UnitCategory.RANGED,
            maxHp = 80,
            attack = 24,
            defense = 13,
            movement = 2,
            range = 2,
            specialAbility = SpecialAbilityType.VOLLEY_FIRE,
            cost = 80,
            historicalNote = LocalizedText(
                "Massed bowmen providing supporting fire from walls, hills, and behind shield lines.",
                "Devorlar, tepaliklar va qalqonli saflar ortidan o'q uzuvchi kamondorlar.",
                "Стрелки поддержки, ведущие огонь со стен, холмов и из-за щитов пехоты."
            )
        ),
        UnitArchetype(
            id = "common_swordsmen",
            name = LocalizedText("Armored Swordsmen", "Qilichboz piyodalar", "Мечники"),
            era = Era.COMMON,
            category = UnitCategory.INFANTRY,
            maxHp = 100,
            attack = 28,
            defense = 23,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.SHIELD_WALL,
            cost = 90,
            historicalNote = LocalizedText(
                "Versatile close-combat infantry suited for storming breaches and fighting in forested or broken ground.",
                "Devor yoriqlariga hujum qilish va o'rmon-t회aliklarda yaqin jang olib borishga moslashgan piyodalar.",
                "Универсальная пехота ближнего боя для штурма проломов и схваток на пересеченной местности."
            )
        ),
        UnitArchetype(
            id = "common_catapult",
            name = LocalizedText("Traction Catapult (Mangonel)", "Tosh otar katapulta", "Катапульта (Мангонель)"),
            era = Era.COMMON,
            category = UnitCategory.SIEGE,
            maxHp = 75,
            attack = 32,
            defense = 10,
            movement = 2,
            range = 3,
            specialAbility = SpecialAbilityType.SAPPING,
            cost = 130,
            historicalNote = LocalizedText(
                "Mobile siege engine hurling stone projectiles against fortifications and dense troop clusters.",
                "Qal'a devorlari va zich joylashgan dushman saflariga og'ir toshlar uloqtiruvchi qamal quroli.",
                "Подвижное осадное орудие, метающее камни по укреплениям и скоплениям войск."
            )
        ),
        UnitArchetype(
            id = "common_trebuchet",
            name = LocalizedText("Counterweight Trebuchet", "Og'ir manjaniq (Trebuchet)", "Требюше с противовесом"),
            era = Era.COMMON,
            category = UnitCategory.SIEGE,
            maxHp = 70,
            attack = 40,
            defense = 9,
            movement = 1,
            range = 3,
            specialAbility = SpecialAbilityType.SAPPING,
            cost = 165,
            historicalNote = LocalizedText(
                "Heavy siege artillery capable of shattering stone and mud-brick curtain walls from afar.",
                "Uzoq masofadan qalin paxsadevorni va tosh qo'rg'onlarni qulatuvchi ulkan manjaniq.",
                "Тяжелая осадная артиллерия, разрушающая каменные и глинобитные стены издалека."
            )
        ),
        UnitArchetype(
            id = "common_scouts",
            name = LocalizedText("Qazaq / Vanguard Scouts", "Qorovul / Kuzatuvchilar", "Разведчики-караулы"),
            era = Era.COMMON,
            category = UnitCategory.SUPPORT,
            maxHp = 80,
            attack = 20,
            defense = 15,
            movement = 4,
            range = 1,
            specialAbility = SpecialAbilityType.RALLY_TROOPS,
            cost = 75,
            historicalNote = LocalizedText(
                "Fast reconnaissance riders carrying signal horns and standards to coordinate wings and restore wavering morale.",
                "Signal karnaylari va tug'lar bilan qo'shin qanotlarini muvofiqlashtiruvchi hamda ruhiyatni ko'taruvchi kuzatuvchilar.",
                "Быстрые дозорные со знаменами и горнами, координирующие фланги и поднимающие дух войск."
            )
        ),
        UnitArchetype(
            id = "common_engineers",
            name = LocalizedText("Siege Sappers & Pontoniers", "Laqimchi va ko'prikchi muhandislar", "Саперы и инженеры"),
            era = Era.COMMON,
            category = UnitCategory.SUPPORT,
            maxHp = 85,
            attack = 25,
            defense = 18,
            movement = 2,
            range = 1,
            specialAbility = SpecialAbilityType.SAPPING,
            cost = 95,
            historicalNote = LocalizedText(
                "Specialists in mining beneath fortress towers, filling moats (hashar), and forcing river crossings.",
                "Qal'a burjlari ostidan lahim qazish, xandaqlarni to'ldirish va kechuv ochish bo'yicha ustalar.",
                "Специалисты по подкопам под башни, засыпке рвов (хашар) и наведению переправ."
            )
        )
    )

    val allHeroes: List<HeroDefinition> = listOf(
        // 1. AMIR TIMUR
        HeroDefinition(
            id = "amir_timur",
            name = LocalizedText("Amir Timur (Tamerlane)", "Amir Temur Ko'ragon", "Амир Тимур (Тамерлан)"),
            title = LocalizedText("Sahibqiran — Lord of the Auspicious Conjunction", "Sohibqiron", "Сахибкиран"),
            lifespan = "1336–1405",
            era = Era.CENTRAL_ASIAN,
            factionId = FactionId.TIMURID,
            biography = LocalizedText(
                "Born near Kesh (Shahrisabz) in the Barlas confederation, Timur rose from a wounded outlaw to master of Transoxiana and builder of an empire stretching from Delhi to Smyrna. Making Samarkand his gleaming capital of turquoise domes and gardens, he combined Turco-Mongol steppe maneuver with methodical siegecraft, chess-like reserve deployment, and psychological mastery.",
                "Kesh (Shahrisabz) yaqinida Barlos beklaridan tug'ilgan Amir Temur Movarounnahrni birlashtirib, Dehlidan Izmirgacha cho'zilgan qudratli saltanat barpo etdi. Samarqandni ilm-fan va me'morchilik poytaxtiga aylantirgan Sohibqiron o'zining yetti qismli jang tartibi (Qo'l, Barangar, Juvangar, Qorovul,ung'ul) va zaxira kuchlarini o'z vaqtida tashlash san'ati bilan tarixda yengilmas sarkarda sifatida qoldi.",
                "Уроженец Кеша (Шахрисабза) из рода барласов, Тимур объединил Мавераннахр и создал империю от Дели до Измира, превратив Самарканд в жемчужину Востока. В военном искусстве сочетал степной маневр, глубокое эшелонирование резервов и безупречную организацию осад."
            ),
            passiveAuraName = LocalizedText("Seven-Corps Order (Tuzuk)", "Temur Tuzuklari tartibi", "Строй семи корпусов"),
            passiveAuraDesc = LocalizedText("Friendly units within 2 hexes gain +15% Attack, +10% Defense, and +8 Morale per turn.", "2 katak atrofdagi ittifoqchilarga +15% Hujum, +10% Himoya va har yurishda +8 Ruhiyat beradi.", "Союзники в радиусе 2 гексов получают +15% АТК, +10% ЗАЩ и +8 к духу за ход."),
            passiveAttackBonus = 15,
            passiveDefenseBonus = 10,
            passiveMoraleAura = 8,
            activeAbilityName = LocalizedText("Qol Reserve Commitment", "Sohibqiron zaxira zarbasi", "Удар личного резерва"),
            activeAbilityDesc = LocalizedText("Restores +25 Morale and grants +20% Attack to all friendly units within 2 hexes for this turn.", "2 katak radiusdagi barcha bo'linmalarga +25 Ruhiyat va +20% Hujum quvvati beradi.", "Восстанавливает +25 духа и дает +20% к атаке союзникам в радиусе 2 гексов."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.TIMURID_ENCIRCLEMENT,
            baseUnitArchetypeId = "timurid_heavy_cavalry"
        ),
        // 2. GENGHIS KHAN & SUBUTAI
        HeroDefinition(
            id = "genghis_subutai",
            name = LocalizedText("Genghis Khan & Subutai", "Chingizxon va Subutoy bahodir", "Чингисхан и Субэдэй"),
            title = LocalizedText("Khagan of the Khamag Mongol & Master of the Tumen", "Mo'g'ul xoqoni va Tumanboshi", "Великий хан и темник Субэдэй"),
            lifespan = "1162–1227 / 1175–1248",
            era = Era.STEPPE,
            factionId = FactionId.MONGOL,
            biography = LocalizedText(
                "Uniting the nomadic tribes of the Mongolian steppe under the decimal military system (arban, jagun, mingghan, tumen) and the Yassa code, Genghis Khan and his chief strategist Subutai executed wide-ranging multi-column encirclements—most famously crossing the freezing Kyzylkum desert to appear behind Bukhara in 1220.",
                "Ko'chmanchi qabilalarni o'nlik harbiy tizim (o'nlik, yuzlik, minglik, tuman) asosida birlashtirgan Chingizxon va uning bosh lashkarboshisi Subutoy uzoq masofali aylanma yurishlar, jumladan 1220-yilda Qizilqum cho'lini kesib o'tib Buxoro ortidan chiqish operatsiyasini amalga oshirgan.",
                "Объединив кочевые племена по десятичной системе туменов, Чингисхан и его полководец Субэдэй прославились глубокими стратегическими охватами, включая переход через пустыню Кызылкум к Бухаре в 1220 году."
            ),
            passiveAuraName = LocalizedText("Decimal Command Signals", "O'nlik tizim intizomi", "Сигналы туменов"),
            passiveAuraDesc = LocalizedText("Friendly cavalry and horse archers within 2 hexes gain +15% Attack and +6 Morale.", "2 katak atrofdagi otliq va kamondorlarga +15% Hujum va +6 Ruhiyat.", "Конница и конные лучники в радиусе 2 гексов получают +15% АТК и +6 к духу."),
            passiveAttackBonus = 15,
            passiveDefenseBonus = 5,
            passiveMoraleAura = 6,
            activeAbilityName = LocalizedText("Nerge Great Hunt Volley", "Katta ov (Nerge) qurshovi", "Облава Нэргэ"),
            activeAbilityDesc = LocalizedText("Shocks all enemy units within 2 hexes for -20 Morale and 15 volley damage.", "2 katak atrofdagi dushmanlarga 15 zarar va -20 ruhiyat zarbasi beradi.", "Наносит 15 ед. урона стрелами и снижает дух врагов в радиусе 2 гексов на 20."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.ARROW_STORM,
            baseUnitArchetypeId = "steppe_horse_archer"
        ),
        // 3. JALAL AD-DIN MANGBURNI
        HeroDefinition(
            id = "jalal_ad_din",
            name = LocalizedText("Jalal ad-Din Mangburni", "Jaloliddin Manguberdi", "Джалал ад-Дин Мангуберди"),
            title = LocalizedText("Last Khwarazmshah & Lion of Parwan", "So'nggi Xorazmshoh", "Последний Хорезмшах"),
            lifespan = "1199–1231",
            era = Era.CENTRAL_ASIAN,
            factionId = FactionId.KHWARAZMIAN,
            biography = LocalizedText(
                "Inheriting a shattered empire after the fall of Gurganj and Samarkand, young Sultan Jalal ad-Din rallied Khwarazmian, Turkmen, and Afghan horsemen to inflict a rare field defeat on a Mongol army at Parwan (1221). Cornered at the Indus River by Genghis Khan himself, he led desperate counter-charges before leaping on horseback from a cliff into the rushing river with banner in hand.",
                "Saltanat parchalangan og'ir damda taxtga chiqqan yosh sulton Jaloliddin Manguberdi Parvon jangida (1221) mo'g'ul qo'shinini tor-mor etdi. Sind (Hind) daryosi bo'yida qurshovda qolgach, so'nggi nafasgacha mardona jang qilib, oti bilan qoyadan daryoga sakrab kechib o'tdi va hatto raqibi Chingizxonning ham tahsiniga sazovor bo'ldi.",
                "Приняв командование в час гибели державы, молодой султан Джалал ад-Дин разбил монгольский корпус при Парване (1221), а в битве на реке Инд после отчаянного сопротивления переплыл бурную реку на коне со знаменем в руках."
            ),
            passiveAuraName = LocalizedText("Unbroken Standard", "Egimas tug' shijoati", "Несломленное знамя"),
            passiveAuraDesc = LocalizedText("Friendly units within 2 hexes cannot rout above 0 HP and gain +12% Attack & +15% Defense.", "2 katak atrofdagi bo'linmalar +12% Hujum, +15% Himoya va +10 Ruhiyat oladi.", "Союзники в радиусе 2 гексов получают +12% АТК, +15% ЗАЩ и +10 к духу."),
            passiveAttackBonus = 12,
            passiveDefenseBonus = 15,
            passiveMoraleAura = 10,
            activeAbilityName = LocalizedText("Charge of Parwan & the Indus", "Parvon va Sind hamlası", "Порыв Парвана и Инда"),
            activeAbilityDesc = LocalizedText("Heals 20 HP and grants +25% Attack & +30 Morale to Jalal ad-Din and adjacent allies.", "Jaloliddin va yonidagi ittifoqchilarga +20 HP, +30 Ruhiyat va +25% Hujum beradi.", "Восстанавливает 20 ОЗ, +30 к духу и дает +25% АТК себе и соседним отрядам."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.INDUS_DEFIANCE,
            baseUnitArchetypeId = "khwarazmian_cavalry"
        ),
        // 4. MUHAMMAD II OF KHWARAZM
        HeroDefinition(
            id = "muhammad_khwarazm",
            name = LocalizedText("Ala ad-Din Muhammad II", "Alouddin Muhammad Xorazmshoh", "Ала ад-Дин Мухаммад II"),
            title = LocalizedText("Shah of Khwarazm & Transoxiana", "Xorazmshoh", "Шах Хорезма"),
            lifespan = "1169–1220",
            era = Era.CENTRAL_ASIAN,
            factionId = FactionId.KHWARAZMIAN,
            biography = LocalizedText(
                "Ruler of a vast empire spanning Khwarazm, Transoxiana, and Khorasan. Though commanding hundreds of thousands of troops, deep political mistrust between his Kipchak kinsmen and civic elites led him to disperse his army across fortified cities rather than risk a single field battle—a strategic choice scrutinized in our 'What If' scenarios.",
                "Xorazm, Movarounnahr va Xurosonni birlashtirgan ulkan davlat hukmdori. Katta qo'shinga ega bo'lsa-da, saroydagi ichki ixtiloflar sababli kuchni yagona maydonga jamlash o'rniga shahar qal'alariga bo'lib yuborgan. «Agar-chi?» rejimida ushbu qarorni o'zgartirib ko'rish mumkin.",
                "Правитель обширной державы от Арала до Персидского залива. Из-за внутренних противоречий разделил огромную армию по гарнизонам городов вместо генерального сражения."
            ),
            passiveAuraName = LocalizedText("Garrison Citadel Decree", "Qal'a mudofaasi farmoni", "Гарнизонный указ"),
            passiveAuraDesc = LocalizedText("Friendly units on or adjacent to walls/fortresses gain +20% Defense.", "Devor va qal'a atrofidagi ittifoqchilarga +20% Himoya beradi.", "Союзники у стен и крепостей получают +20% к защите."),
            passiveAttackBonus = 5,
            passiveDefenseBonus = 20,
            passiveMoraleAura = 5,
            activeAbilityName = LocalizedText("Imperial Levy Muster", "Saltanat safarbarligi", "Сбор ополчения державы"),
            activeAbilityDesc = LocalizedText("Restores 15 HP and +20 Morale to friendly units within 2 hexes.", "2 katak atrofdagi ittifoqchilarga 15 HP va +20 Ruhiyat tiklaydi.", "Восстанавливает 15 ОЗ и +20 к духу союзникам в радиусе 2 гексов."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.ROYAL_GUARD,
            baseUnitArchetypeId = "khwarazmian_cavalry"
        ),
        // 5. SALADIN
        HeroDefinition(
            id = "saladin",
            name = LocalizedText("Saladin (Salah ad-Din)", "Salohiddin Ayyubiy", "Салах ад-Дин (Саладин)"),
            title = LocalizedText("Sultan of Egypt and Syria", "Misr va Shom sultoni", "Султан Египта и Сирии"),
            lifespan = "1137–1193",
            era = Era.MIDDLE_EAST,
            factionId = FactionId.AYYUBID,
            biography = LocalizedText(
                "Founder of the Ayyubid dynasty who unified Egypt and Syria through diplomacy and persistent campaigning. At the Horns of Hattin (1187), he cut the Crusader host off from the water of Lake Tiberias before retaking Jerusalem—remembered in both Muslim and European chronicles for his strategic patience and chivalric conduct.",
                "Misr va Shomni birlashtirgan Ayyubiylar sulolasi asoschisi. Xattin jangida (1187) dushmanni suv manbalaridan uzib qo'yib, Quddusni qaytarib olgan; Sharq va G'arb solnomalarida o'zining adolati, sabr-toqati va oliyjanobligi bilan mashhur.",
                "Основатель династии Айюбидов, объединивший Египет и Сирию. Одержал решающую победу при Хаттине (1187) и прославился как в восточных, так и в западных хрониках своим благородством."
            ),
            passiveAuraName = LocalizedText("Chivalric Composure", "Sulton salobati", "Благородная выдержка"),
            passiveAuraDesc = LocalizedText("Friendly units within 2 hexes gain +12% Attack, +12% Defense, and +8 Morale.", "2 katak atrofdagi ittifoqchilarga +12% Hujum, +12% Himoya va +8 Ruhiyat.", "Союзники в радиусе 2 гексов получают +12% АТК, +12% ЗАЩ и +8 к духу."),
            passiveAttackBonus = 12,
            passiveDefenseBonus = 12,
            passiveMoraleAura = 8,
            activeAbilityName = LocalizedText("Encirclement of Hattin", "Xattin qurshovi", "Кольцо Хаттина"),
            activeAbilityDesc = LocalizedText("Drains -25 Morale from nearby enemies and boosts friendly attack by +20%.", "Atrofdagi dushman ruhiyatini -25 ga tushirib, ittifoqchilar hujumini +20% oshiradi.", "Снижает дух соседних врагов на 25 и повышает атаку союзников на +20%."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.HORNS_OF_HATTIN,
            baseUnitArchetypeId = "mamluk_cavalry"
        ),
        // 6. RICHARD THE LIONHEART
        HeroDefinition(
            id = "richard_lionheart",
            name = LocalizedText("Richard I the Lionheart", "Rixard Sheryurak", "Ричард I Львиное Сердце"),
            title = LocalizedText("King of England & Duke of Normandy", "Angliya qiroli", "Король Англии"),
            lifespan = "1157–1199",
            era = Era.EUROPE,
            factionId = FactionId.CRUSADER,
            biography = LocalizedText(
                "Commander of the Third Crusade, celebrated for his logistical discipline during the coastal march from Acre to Jaffa. At the Battle of Arsuf (1191), Richard maintained a tight defensive box of infantry and crossbowmen against constant horse-archer harassment until sounding the trumpet for a synchronized heavy cavalry charge.",
                "Uchinchi salib yurishi yetakchisi. Arsuf jangida (1191) piyodalar va arbaletchilarning zich mudofaa safini uzoq ushlab turib, eng qulay lahzada og'ir ritsarlarning yoppasiga qarshi hujumini uyushtirgan.",
                "Предводитель Третьего крестового похода. В битве при Арсуфе (1191) провел образцовый марш под обстрелом, завершив его сокрушительной контратакой рыцарей."
            ),
            passiveAuraName = LocalizedText("Disciplined Coastal March", "Intizomli yurish safi", "Дисциплина марша"),
            passiveAuraDesc = LocalizedText("Friendly units within 2 hexes gain +15% Attack and +10% Defense.", "2 katak atrofdagi ittifoqchilarga +15% Hujum va +10% Himoya.", "Союзники в радиусе 2 гексов получают +15% АТК и +10% ЗАЩ."),
            passiveAttackBonus = 15,
            passiveDefenseBonus = 10,
            passiveMoraleAura = 7,
            activeAbilityName = LocalizedText("Six Trumpets of Arsuf", "Arsuf karnaylari hamlası", "Трубы Арсуфа"),
            activeAbilityDesc = LocalizedText("Grants +25% Melee Attack and +20 Morale to friendly units within 2 hexes.", "2 katak atrofdagi bo'linmalarga +25% Hujum va +20 Ruhiyat beradi.", "Дает +25% к ближней атаке и +20 к духу союзникам в радиусе 2 гексов."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.LIONHEART_VANGUARD,
            baseUnitArchetypeId = "european_knights"
        ),
        // 7. WILLIAM THE CONQUEROR & HAROLD GODWINSON
        HeroDefinition(
            id = "william_harold",
            name = LocalizedText("William of Normandy / Harold II", "Vilgelm Fotih / Qirol Garold II", "Вильгельм Завоеватель / Гарольд II"),
            title = LocalizedText("Contenders of Senlac Hill (1066)", "Gastings raqiblari (1066)", "Соперники при Гастингсе (1066)"),
            lifespan = "c. 1028–1087 / c. 1022–1066",
            era = Era.EUROPE,
            factionId = FactionId.NORMAN,
            biography = LocalizedText(
                "At Hastings in October 1066, King Harold Godwinson's Anglo-Saxon huscarls locked shields atop Senlac Ridge after marching south from Stamford Bridge, while Duke William of Normandy combined archers, heavy infantry, and feigned cavalry retreats to pry open the shield wall.",
                "1066-yil oktabrda Gastings tepaligida qirol Garoldning qalqon devori va gersog Vilgelmning kamondor hamda otliqlar uyg'unligidagi ayyorona chekinish taktikasi to'qnashgan.",
                "В октябре 1066 года при Гастингсе стена щитов хускарлов короля Гарольда сошлась с комбинированными атаками лучников и конницы герцога Вильгельма."
            ),
            passiveAuraName = LocalizedText("Banner of the Dragon & Papal Standard", "Senlak tepaligi qasami", "Знамя Сенлакского холма"),
            passiveAuraDesc = LocalizedText("Friendly units within 2 hexes gain +10% Attack and +15% Defense.", "2 katak atrofdagi ittifoqchilarga +10% Hujum va +15% Himoya.", "Союзники в радиусе 2 гексов получают +10% АТК и +15% ЗАЩ."),
            passiveAttackBonus = 10,
            passiveDefenseBonus = 15,
            passiveMoraleAura = 7,
            activeAbilityName = LocalizedText("Raise the Helmet — I Still Live!", "Dubulg'ani ko'taring — Men tirikman!", "Поднять шлем — Я жив!"),
            activeAbilityDesc = LocalizedText("Restores +30 Morale and +15% Defense to all friendly units within 2 hexes.", "Atrofdagi barcha bo'linmalarga +30 Ruhiyat va +15% Himoya qaytaradi.", "Восстанавливает +30 духа и дает +15% ЗАЩ союзникам в радиусе 2 гексов."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.SHIELD_WALL_OATH,
            baseUnitArchetypeId = "men_at_arms"
        ),
        // 8. HENRY V
        HeroDefinition(
            id = "henry_v",
            name = LocalizedText("King Henry V", "Qirol Genrix V", "Король Генрих V"),
            title = LocalizedText("Victor of Agincourt", "azinkur g'olibi", "Победитель при Азенкуре"),
            lifespan = "1386–1422",
            era = Era.EUROPE,
            factionId = FactionId.ENGLAND,
            biography = LocalizedText(
                "Trapped on a narrow, freshly plowed strip of mud between two woods on Saint Crispin's Day (1415), Henry V anchored his outnumbered army with longbowmen behind sharpened stakes, provoking the French vanguard into a fatal charge across deep mud.",
                "1415-yilda Azinkur yaqinida ikki o'rmon oralig'idagi loyqa maydonda Genrix V kam sonli qo'shinini o'tkir qoziqlar va uzun kamonchilar bilan himoyalab, og'ir zirhli dushman hujumini tor-mor etgan.",
                "Оказавшись с малочисленным войском на узком размокшем поле между двумя лесами при Азенкуре (1415), Генрих V умело использовал длинные луки и колья против тяжелой конницы."
            ),
            passiveAuraName = LocalizedText("Band of Brothers", "aka-ukalar birodarligi", "Братство по оружию"),
            passiveAuraDesc = LocalizedText("Friendly ranged and infantry units within 2 hexes gain +15% Attack and +10% Defense.", "2 katak atrofdagi kamondor va piyodalarga +15% Hujum va +10% Himoya.", "Пехота и стрелки в радиусе 2 гексов получают +15% АТК и +10% ЗАЩ."),
            passiveAttackBonus = 15,
            passiveDefenseBonus = 10,
            passiveMoraleAura = 8,
            activeAbilityName = LocalizedText("Stakes & Bodkin Storm", "Qoziqlar va o'q bo'roni", "Колья и ливень стрел"),
            activeAbilityDesc = LocalizedText("Grants +25% Ranged Attack and slows adjacent enemies.", "Kamondorlar hujumini +25% oshiradi va yaqin dushman ruhiyatini tushiradi.", "Повышает атаку стрелков на +25% и ослабляет соседних врагов."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.AGINCOURT_STAKES,
            baseUnitArchetypeId = "men_at_arms"
        ),
        // 9. BAYEZID I
        HeroDefinition(
            id = "bayezid_i",
            name = LocalizedText("Sultan Bayezid I", "Sulton Boyazid I Yildirim", "Султан Баязид I Молниеносный"),
            title = LocalizedText("Yildirim — The Thunderbolt", "Yildirim (Chaqmoq)", "Йылдырым (Молниеносный)"),
            lifespan = "1354–1403",
            era = Era.MIDDLE_EAST,
            factionId = FactionId.OTTOMAN,
            biography = LocalizedText(
                "Earned the epithet 'Thunderbolt' for the speed with which he shifted armies between the Balkans and Anatolia, crushing the Crusader coalition at Nicopolis (1396). At Ankara (1402), he fought fiercely on Catal Hill with his Janissary and Serbian heavy guard until overwhelmed by Timur's encirclement.",
                "Bolqon va Onado'li orasida chaqmoqdek tez yurishlari uchun «Yildirim» nomini olgan; Nikopol jangida (1396) g'alaba qozongan. 1402-yil Anqara jangida Amir Temurga qarshi so'nggi nafasgacha Chatal tepaligida jang qilgan.",
                "Прозванный «Молниеносным» за стремительные переходы между Балканами и Анатолией. В битве при Анкаре (1402) до последнего сражался на холме Чатал против войск Тимура."
            ),
            passiveAuraName = LocalizedText("Yildirim Forced March", "Yildirim shiddati", "Молниеносный натиск"),
            passiveAuraDesc = LocalizedText("Friendly units within 2 hexes gain +15% Attack and +10% Defense.", "2 katak atrofdagi ittifoqchilarga +15% Hujum va +10% Himoya.", "Союзники в радиусе 2 гексов получают +15% АТК и +10% ЗАЩ."),
            passiveAttackBonus = 15,
            passiveDefenseBonus = 10,
            passiveMoraleAura = 6,
            activeAbilityName = LocalizedText("Last Stand of Catal Hill", "Chatal tepaligi matonati", "Стойкость холма Чатал"),
            activeAbilityDesc = LocalizedText("Boosts Defense by +30% and Morale by +20 for nearby friendly units.", "Atrofdagi bo'linmalarga +30% Himoya va +20 Ruhiyat beradi.", "Повышает защиту на +30% и боевой дух на +20 соседним союзникам."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.YILDIRIM_BLITZ,
            baseUnitArchetypeId = "mamluk_cavalry"
        ),
        // 10. BABUR
        HeroDefinition(
            id = "babur",
            name = LocalizedText("Zahir al-Din Muhammad Babur", "Zahiriddin Muhammad Bobur", "Захир ад-Дин Мухаммад Бабур"),
            title = LocalizedText("Prince of Fergana & Author of the Baburnama", "Farg'ona shahzodasi va buyuk sarkarda", "Правитель Ферганы и полководец"),
            lifespan = "1483–1530",
            era = Era.CENTRAL_ASIAN,
            factionId = FactionId.TIMURID,
            biography = LocalizedText(
                "Born in Andijan in the Fergana Valley, the Timurid prince Babur fought from age twelve to hold Samarkand before turning south to Kabul and India. In his candid Chagatai Turkic memoir, the Baburnama, he recorded how he combined Uzbek/Steppe flanking maneuvers (tulughma) with Ottoman-style wagon barriers.",
                "Andijonda tug'ilgan temuriy shahzoda Bobur yoshligidan Samarqand uchun kurashlar maktabida toblandi. O'zining o'lmas «Boburnoma» asarida o'zbek/dasht «to'lg'ama» qanot zarbasini aravalar mudofaasi bilan qanday uyg'unlashtirganini yozib qoldirgan.",
                "Уроженец Андижана в Ферганской долине, потомок Тимура и автор «Бабур-наме», блестяще сочетавший степной охват «тулгама» с полевыми укреплениями."
            ),
            passiveAuraName = LocalizedText("Tulughma Wing Envelopment", "To'lg'ama qanot usuli", "Фланговый охват Тулгама"),
            passiveAuraDesc = LocalizedText("Friendly units within 2 hexes gain +15% Flanking Attack and +8 Morale.", "2 katak atrofdagi bo'linmalarga qanot zarbasida +15% kuch va +8 Ruhiyat.", "Союзники в радиусе 2 гексов получают +15% к атаке и +8 к духу."),
            passiveAttackBonus = 15,
            passiveDefenseBonus = 10,
            passiveMoraleAura = 8,
            activeAbilityName = LocalizedText("Fergana Vanguard Surge", "Farg'ona ilg'or hamlası", "Рывок ферганского авангарда"),
            activeAbilityDesc = LocalizedText("Restores +20 Morale and grants +20% Attack to friendly units within 2 hexes.", "Atrofdagi ittifoqchilarga +20 Ruhiyat va +20% Hujum beradi.", "Восстанавливает +20 духа и дает +20% АТК союзникам в радиусе 2 гексов."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.TULUGHMA_FLANK,
            baseUnitArchetypeId = "timurid_heavy_cavalry"
        ),
        // 11. ULUGH BEG
        HeroDefinition(
            id = "ulugh_beg",
            name = LocalizedText("Mirzo Ulugh Beg", "Mirzo Ulug'bek", "Мирзо Улугбек"),
            title = LocalizedText("Scholar-Ruler of Samarkand & Astronomer", "Samarqand hukmdori va buyuk munajjim", "Правитель Самарканда и астроном"),
            lifespan = "1394–1449",
            era = Era.CENTRAL_ASIAN,
            factionId = FactionId.TIMURID,
            biography = LocalizedText(
                "Grandson of Amir Timur who governed Samarkand for nearly forty years, building the famous Madrasah on the Registan and the great Observatory on Kuhak Hill where the Zij-i Sultani star catalog was compiled—calculating the length of the sidereal year to within seconds.",
                "Amir Temurning nabirasi, Samarqandni qariyb qirq yil boshqargan olim va hukmdor. Registon maydonidagi madrasa hamda Ko'hak tepaligidagi rasadxonani bunyod etib, «Ziji jadidi Ko'ragoniy» yulduzlar jadvalini tuzgan.",
                "Внук Амира Тимура, превративший Самарканд в мировой научный центр; создатель обсерватории на холме Кухак и звездного каталога «Зидж-и Гурагани»."
            ),
            passiveAuraName = LocalizedText("Geometric Precision of Kuhak", "Rasdxona aniq hisobi", "Геометрическая точность Кухака"),
            passiveAuraDesc = LocalizedText("Friendly ranged and siege units within 2 hexes gain +20% Attack and +15% Defense.", "2 katak atrofdagi kamondor va manjaniqlarga +20% Hujum va +15% Himoya.", "Стрелки и осадные орудия в радиусе 2 гексов получают +20% АТК и +15% ЗАЩ."),
            passiveAttackBonus = 18,
            passiveDefenseBonus = 15,
            passiveMoraleAura = 9,
            activeAbilityName = LocalizedText("Zij-i Sultani Calculated Barrage", "Aniq mo'ljalli manjaniq zarbasi", "Выверенный залп Самарканда"),
            activeAbilityDesc = LocalizedText("Grants +25% Ranged/Siege damage and +20% Defense around Samarkand's defenses.", "2 katak atrofdagi bo'linmalarga +25% Hujum va +20% Himoya beradi.", "Дает +25% к урону и +20% к защите обороняющимся отрядам."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.ZIJ_ASTRONOMY_VOLLEY,
            baseUnitArchetypeId = "timurid_heavy_cavalry"
        ),
        // 12. TOKHTAMYSH
        HeroDefinition(
            id = "tokhtamysh",
            name = LocalizedText("Khan Tokhtamysh", "To'xtamishxon", "Хан Тохтамыш"),
            title = LocalizedText("Khan of the White and Golden Horde", "Oltin O'rda xoni", "Хан Золотой Орды"),
            lifespan = "d. 1406",
            era = Era.STEPPE,
            factionId = FactionId.GOLDEN_HORDE,
            biography = LocalizedText(
                "A descendant of Jochi initially backed by Amir Timur to reunite the White and Blue wings of the Golden Horde. Once entrenched on the Volga, Tokhtamysh challenged his former patron across the Caucasus and Transoxiana, culminating in the massive steppe confrontations at the Kondurcha River (1391) and Terek (1395).",
                "Jo'ji naslidan bo'lgan To'xtamishxon dastlab Amir Temur ko'magida Oq O'rda va Oltin O'rdani birlashtirdi. So'ngra sobiq homiysiga qarshi chiqib, 1391-yil Qunduzcha (Kondurcha) va 1395-yil Terek daryosi bo'yida ulkan dasht janglarida to'qnashdi.",
                "Потомок Джучи, при поддержке Тимура объединивший Золотую Орду, а затем бросивший вызов своему покровителю в грандиозных битвах на Кондурче (1391) и Тереке (1395)."
            ),
            passiveAuraName = LocalizedText("Wings of the Kipchak Steppe", "Dashti Qipchoq qanotlari", "Крылья Дешт-и-Кипчака"),
            passiveAuraDesc = LocalizedText("Friendly steppe cavalry within 2 hexes gain +15% Attack and +6 Morale.", "2 katak atrofdagi dasht otliqlariga +15% Hujum va +6 Ruhiyat.", "Степная конница в радиусе 2 гексов получает +15% АТК и +6 к духу."),
            passiveAttackBonus = 15,
            passiveDefenseBonus = 8,
            passiveMoraleAura = 6,
            activeAbilityName = LocalizedText("Volga Outflanking Sweep", "Qanotdan keng aylanib o'tish", "Охват туменов Поволжья"),
            activeAbilityDesc = LocalizedText("Boosts Attack by +20% and restores +20 Morale to friendly steppe forces.", "Atrofdagi otliqlarga +20% Hujum va +20 Ruhiyat beradi.", "Дает +20% к атаке и +20 к боевому духу соседним отрядам."),
            activeCooldownTurns = 3,
            activeEffectType = HeroActiveEffectType.GOLDEN_HORDE_SWARM,
            baseUnitArchetypeId = "steppe_horse_archer"
        )
    )

    val allCities: List<ChronicleCityEntry> = listOf(
        ChronicleCityEntry(
            id = "city_samarkand",
            name = LocalizedText("Samarkand (Marakanda)", "Samarqand (Afrosiyob)", "Самарканд (Мараканда)"),
            region = LocalizedText("Zarafshan Valley, Uzbekistan", "Zarafshon vodiysi, O'zbekiston", "Зеравшанская долина, Узбекистан"),
            goldenAgeEra = "Sogdian Antiquity, Khwarazmian Era & Timurid Renaissance (14th–15th C.)",
            worldMapX = 0.64f,
            worldMapY = 0.48f,
            summary = LocalizedText(
                "Crossroads of the Silk Road watered by the Zarafshan River. Defended in 1220 on the ancient plateau of Afrasiab, and later rebuilt by Amir Timur and Ulugh Beg into the architectural and scientific heart of Central Asia.",
                "Zarafshon daryosi bo'yidagi Buyuk Ipak yo'lining yuragi. 1220-yilda Afrosiyob tepaligida qattiq mudofaa qilingan, Amir Temur va Mirzo Ulug'bek davrida esa dunyoning ilm-fan hamda me'morchilik poytaxtiga aylangan.",
                "Сердце Великого шелкового пути в долине Зеравшана. Столица державы Амира Тимура и научный центр эпохи Мирзо Улугбека."
            ),
            monumentsAndLegacy = LocalizedText(
                "Registan Square (Ulugh Beg Madrasah, 1417–1420), Gur-e-Amir Mausoleum, Bibi-Khanym Mosque, Shah-i-Zinda necropolis, and the Kuhak Observatory.",
                "Registon maydoni (Ulug'bek madrasasi, 1417–1420), Go'ri Amir maqbarasi, Bibixonim jome masjidi, Shohi Zinda majmuasi va Ulug'bek rasadxonasi.",
                "Площадь Регистан (медресе Улугбека), мавзолей Гур-Эмир, мечеть Биби-Ханым, ансамбль Шахи-Зинда и обсерватория Улугбека."
            )
        ),
        ChronicleCityEntry(
            id = "city_bukhara",
            name = LocalizedText("Bukhara-i Sharif", "Buxoroyi Sharif", "Священная Бухара"),
            region = LocalizedText("Lower Zarafshan Oasis, Uzbekistan", "Quyi Zarafshon vohasi, O'zbekiston", "Бухарский оазис, Узбекистан"),
            goldenAgeEra = "Samanid, Karakhanid, Khwarazmian & Timurid Eras (9th–15th C.)",
            worldMapX = 0.58f,
            worldMapY = 0.49f,
            summary = LocalizedText(
                "Renowned center of Islamic scholarship, poetry, and commerce, anchored by the ancient Ark Fortress. In February 1220, Genghis Khan's unexpected march across the Kyzylkum desert brought Mongol forces to Bukhara's western gates.",
                "Qadimiy Ark qal'asi atrofida shakllangan ilm-ma'rifat va savdo markazi. 1220-yilda Qizilqum orqali amalga oshirilgan yurish natijasida og'ir qamalni boshdan kechirgan, biroq Kalon minorasi asrlar osha saqlanib qolgan.",
                "Древний центр науки, поэзии и торговли вокруг цитадели Арк и минарета Калян (1127 г.)."
            ),
            monumentsAndLegacy = LocalizedText(
                "The Ark Citadel, Kalyan Minaret (built 1127 by Arslan Khan), Samanid Mausoleum, and Chashma-Ayub.",
                "Ark qal'asi, Minorai Kalon (1127-yilda Arslonxon qurdirgan), Somoniylar maqbarasi va Chashmai Ayyub.",
                "Цитадель Арк, минарет Калян (1127), мавзолей Саманидов и Чашма-Айюб."
            )
        ),
        ChronicleCityEntry(
            id = "city_urgench",
            name = LocalizedText("Gurganj (Old Urgench)", "Gurganj (Ko'hna Urganch)", "Гургандж (Куня-Ургенч)"),
            region = LocalizedText("Amu Darya Delta, Khwarazm", "Amudaryo deltasi, Xorazm", "Дельта Амударьи, Хорезм"),
            goldenAgeEra = "Capital of the Khwarazmshahs (12th–Early 13th C.) & Golden Horde/Timurid Hub",
            worldMapX = 0.51f,
            worldMapY = 0.42f,
            summary = LocalizedText(
                "Imperial capital of the Khwarazmian Empire astride the channels of the Oxus (Amu Darya), home to the Ma'mun Academy where al-Biruni and Ibn Sina worked. Its determined citizens fought house-to-house during the 1221 siege.",
                "Amudaryo bo'yidagi Xorazmshohlar poytaxti, Beruniy va Ibn Sino ijod qilgan Ma'mun akademiyasi maskani. 1221-yilgi qamalda shahar aholisi har bir ko'cha va mahalla uchun qahramonona kurashgan.",
                "Столица Хорезмшахов в дельте Амударьи, родина Академии Мамуна, где трудились Беруни и Ибн Сина."
            ),
            monumentsAndLegacy = LocalizedText(
                "Kutlug-Timur Minaret, Turabek Khanum Mausoleum, and Sultan Tekesh Mausoleum.",
                "Qutlug' Temur minorasi, To'rabekxonim maqbarasi va Sulton Tekesh maqbarasi.",
                "Минарет Кутлуг-Тимура, мавзолей Тюрабек-ханым и мавзолей султана Текеша."
            )
        ),
        ChronicleCityEntry(
            id = "city_shahrisabz",
            name = LocalizedText("Kesh (Shahrisabz)", "Kesh (Shahrisabz)", "Кеш (Шахрисабз)"),
            region = LocalizedText("Qashqadarya Valley, Uzbekistan", "Qashqadaryo vodiysi, O'zbekiston", "Кашкадарьинская долина, Узбекистан"),
            goldenAgeEra = "Timurid Homeland (14th–15th C.)",
            worldMapX = 0.63f,
            worldMapY = 0.53f,
            summary = LocalizedText(
                "Known as the 'Green City' south of the Zarafshan mountains, birthplace of Amir Timur and cradle of his early campaigns in the 1360s.",
                "Zarafshon tog'lari janubidagi «Yashil shahar» — Amir Temur tavallud topgan va 1360-yillardagi dastlabki ozodlik kurashlarini boshlagan maskan.",
                "«Зеленый город» к югу от Зеравшанского хребта — родина Амира Тимура и база его первых походов 1360-х годов."
            ),
            monumentsAndLegacy = LocalizedText(
                "Ak-Saray ('White Palace', 1380–1404) and the Dorut Tilovat & Dorus Saodat memorial complexes.",
                "Oqsaroy majmuasi (1380–1404), Dorut-Tilovat va Dorus-Saodat yodgorliklari.",
                "Дворец Ак-Сарай (1380–1404), комплексы Дорут-Тиловат и Дорус-Саодат."
            )
        ),
        ChronicleCityEntry(
            id = "city_otrar",
            name = LocalizedText("Otrar (Farab)", "O'tror (Forob)", "Отрар (Фараб)"),
            region = LocalizedText("Syr Darya (Sayhun) Frontier, Southern Kazakhstan", "Sirdaryo bo'yi", "Средняя Сырдарья"),
            goldenAgeEra = "Silk Road Oasis & Khwarazmian Frontier Fortress (10th–15th C.)",
            worldMapX = 0.68f,
            worldMapY = 0.39f,
            summary = LocalizedText(
                "Birthplace of the philosopher al-Farabi and the frontier city where Governor Inalchuq's seizure of a Mongol caravan in 1218 triggered the invasion of Central Asia; also the city where Amir Timur died in February 1405.",
                "Buyuk mutafakkir Abu Nasr Forobiy vatani. 1219-yilda besh oy davomida qamalga bardosh bergan chegara qal'asi; shuningdek, 1405-yil fevralda Amir Temur vafot etgan shahar.",
                "Родина философа аль-Фараби и пограничная твердыня на Сырдарье, выдержавшая пятимесячную осаду в 1219 году."
            ),
            monumentsAndLegacy = LocalizedText(
                "Otrar Tobe citadel mounds, Arystan-Bab Mausoleum, and nearby Turkistan (Hazrat Sultan / Khoja Ahmed Yasawi Mausoleum commissioned by Timur).",
                "O'trortepa qal'a qoldiqlari, Arslonbob va Amir Temur bunyod ettirgan Xoja Ahmad Yassaviy maqbarasi.",
                "Городище Отрар-тобе и возведенный по указу Тимура мавзолей Ходжи Ахмеда Ясави в соседнем Туркестане."
            )
        ),
        ChronicleCityEntry(
            id = "city_jerusalem",
            name = LocalizedText("Jerusalem (Al-Quds)", "Quddus (Al-Quds)", "Иерусалим (Аль-Кудс)"),
            region = LocalizedText("Judean Hills, Levant", "Falastin / Shom", "Иудейские горы, Левант"),
            goldenAgeEra = "Crucible of the Crusades & Ayyubid Era (1099–1192)",
            worldMapX = 0.28f,
            worldMapY = 0.58f,
            summary = LocalizedText(
                "Sacred city to Judaism, Christianity, and Islam. Stormed by the First Crusade in July 1099 and recovered via negotiated surrender by Saladin in October 1187 following his victory at Hattin.",
                "Uch din uchun muqaddas shahar. 1099-yilda Birinchi salib yurishi davrida egallangan va 1187-yil Xattin g'alabasidan so'ng Salohiddin Ayyubiy tomonidan sulh yo'li bilan qaytarib olingan.",
                "Священный город трех религий, взятый крестоносцами в 1099 году и возвращенный Саладином в 1187 году."
            ),
            monumentsAndLegacy = LocalizedText(
                "Old City Ramparts, Tower of David Citadel, Dome of the Rock, Al-Aqsa Mosque, and Church of the Holy Sepulchre.",
                "Eski shahar devorlari, Dovud minorasi, Qubbat as-Saxra, Al-Aqso masjidi va Muqaddas Qabr ibodatxonasi.",
                "Стены Старого города, Цитадель Давида, Купол Скалы, мечеть Аль-Акса и Храм Гроба Господня."
            )
        )
    )

    val timelineEvents: List<ChronicleTimelineEvent> = listOf(
        ChronicleTimelineEvent(
            year = 1066,
            title = LocalizedText("Battle of Hastings", "Gastings jangi", "Битва при Гастингсе"),
            region = LocalizedText("Sussex, England", "Angliya", "Англия"),
            summary = LocalizedText("Duke William of Normandy defeats King Harold Godwinson's Anglo-Saxon shield wall on Senlac Hill.", "Normand gersogi Vilgelm Senlak tepaligida qirol Garoldning qalqon devorini yorib o'tadi.", "Герцог Вильгельм Нормандский прорывает стену щитов короля Гарольда на Сенлакском холме."),
            linkedMissionId = "mission_hastings_1066"
        ),
        ChronicleTimelineEvent(
            year = 1099,
            title = LocalizedText("Siege of Jerusalem", "Quddus qamali", "Осада Иерусалима"),
            region = LocalizedText("Levant", "Shom", "Левант"),
            summary = LocalizedText("Crusader armies construct wooden siege towers to breach the northern and southern walls of Fatimid-held Jerusalem.", "Salibchilar qamal minoralari yordamida Quddus devorlariga hujum uyushtiradi.", "Крестоносцы строят осадные башни для штурма укреплений Иерусалима."),
            linkedMissionId = "mission_jerusalem_1099"
        ),
        ChronicleTimelineEvent(
            year = 1187,
            title = LocalizedText("Battle of Hattin", "Xattin jangi", "Битва при Хаттине"),
            region = LocalizedText("Galilee, Levant", "Jalila, Shom", "Галилея, Левант"),
            summary = LocalizedText("Saladin traps the thirsty Crusader royal army on the twin volcanic peaks of Hattin.", "Salohiddin Ayyubiy suvsiz qolgan salibchilar qo'shinini Xattin tepaligida qurshab oladi.", "Салах ад-Дин окружает лишенное воды войско крестоносцев у Рогов Хаттина."),
            linkedMissionId = "mission_hattin_1187"
        ),
        ChronicleTimelineEvent(
            year = 1191,
            title = LocalizedText("Siege of Acre & Battle of Arsuf", "Akka qamali va Arsuf jangi", "Осада Акры и битва при Арсуфе"),
            region = LocalizedText("Levantine Coast", "O'rta yer dengizi sohili", "Побережье Леванта"),
            summary = LocalizedText("Richard the Lionheart and Saladin duel across coastal plains and fortified ports.", "Rixard Sheryurak va Salohiddin Ayyubiy dengiz bo'yi qal'alari hamda tekisliklarida to'qnashadi.", "Противостояние Ричарда Львиное Сердце и Саладина у стен Акры и на полях Арсуфа."),
            linkedMissionId = "mission_arsuf_1191"
        ),
        ChronicleTimelineEvent(
            year = 1219,
            title = LocalizedText("Fall of Otrar", "O'tror mudofaasi", "Осада Отрара"),
            region = LocalizedText("Syr Darya, Central Asia", "Sirdaryo bo'yi", "Сырдарья, Центральная Азия"),
            summary = LocalizedText("The five-month defense of the frontier city of Otrar marks the opening of the Mongol campaign in Khwarazm.", "Chegara qal'asi O'trorning besh oylik qahramonona mudofaasi.", "Пятимесячная оборона приграничного Отрара в начале монгольского похода."),
            linkedMissionId = "mission_otrar_1219"
        ),
        ChronicleTimelineEvent(
            year = 1220,
            title = LocalizedText("Sieges of Bukhara & Samarkand", "Buxoro va Samarqand qamallari", "Осады Бухары и Самарканда"),
            region = LocalizedText("Transoxiana (Movarounnahr)", "Movarounnahr", "Мавераннахр"),
            summary = LocalizedText("Genghis Khan crosses the Kyzylkum desert to outflank Bukhara before converging on the walls of Samarkand.", "Chingizxon Qizilqumni kesib o'tib Buxoro va Samarqand qal'alarini qamal qiladi.", "Обходной марш через Кызылкум к Бухаре и осада укреплений Самарканда."),
            linkedMissionId = "mission_samarkand_1220"
        ),
        ChronicleTimelineEvent(
            year = 1221,
            title = LocalizedText("Urgench, Parwan & the Indus", "Gurganj, Parvon va Sind janglari", "Гургандж, Парван и битва на Инде"),
            region = LocalizedText("Khwarazm & Hindu Kush", "Xorazm va Hindukush", "Хорезм и Гиндукуш"),
            summary = LocalizedText("Gurganj fights block by block while Sultan Jalal ad-Din wins at Parwan and makes his legendary stand at the Indus River.", "Gurganj mudofaasi hamda Sulton Jaloliddin Manguberdining Parvon g'alabasi va Sind daryosi bo'yidagi jasorati.", "Оборона Гурганджа, победа Джалал ад-Дина при Парване и легендарный бой на реке Инд."),
            linkedMissionId = "mission_indus_1221"
        ),
        ChronicleTimelineEvent(
            year = 1365,
            title = LocalizedText("Rise in Transoxiana & Battle of the Mud", "Movarounnahr kurashi va Loy jangi", "Возвышение Тимура и Битва в грязи"),
            region = LocalizedText("Chirchiq / Chinaz, Uzbekistan", "Chirchiq / Chinoz, O'zbekiston", "Чирчик / Чиназ, Узбекистан"),
            summary = LocalizedText("Early campaigns of Amir Timur in Kesh and along the Syr Darya during torrential spring rains.", "Amir Temurning Kesh atrofidagi kurashi va Chinoz yaqinidagi mashhur «Loy jangi».", "Ранние походы Амира Тимура и знаменитая «Грязевая битва» у Чиназа."),
            linkedMissionId = "mission_mud_1365"
        ),
        ChronicleTimelineEvent(
            year = 1391,
            title = LocalizedText("Battle of the Kondurcha River", "Qunduzcha (Kondurcha) daryosi jangi", "Битва на реке Кондурче"),
            region = LocalizedText("Volga-Ural Steppe", "Idil-Ural dashtlari", "Поволжская степь"),
            summary = LocalizedText("Amir Timur marches deep into the steppe and defeats Khan Tokhtamysh of the Golden Horde using a seven-corps order.", "Amir Temur Dashti Qipchoq ichkarisiga yurish qilib, yetti qo'l tartibida To'xtamishxon qo'shinini yengadi.", "Глубокий степной поход Тимура и разгром хана Тохтамыша строем семи корпусов."),
            linkedMissionId = "mission_kondurcha_1391"
        ),
        ChronicleTimelineEvent(
            year = 1402,
            title = LocalizedText("Battle of Ankara", "Anqara jangi", "Ангорская битва"),
            region = LocalizedText("Cubuk Plain, Anatolia", "Chubuk tekisligi, Onado'li", "Чубукская равнина, Анатолия"),
            summary = LocalizedText("Timurid cavalry and war elephants clash with Ottoman Sultan Bayezid I in one of the largest battles of the medieval world.", "Amir Temur va Usmonli sultoni Boyazid Yildirim o'rtasidagi tarixiy to'qnashuv.", "Грандиозное сражение между армиями Амира Тимура и османского султана Баязида I."),
            linkedMissionId = "mission_ankara_1402"
        ),
        ChronicleTimelineEvent(
            year = 1415,
            title = LocalizedText("Battle of Agincourt", "Azinkur jangi", "Битва при Азенкуре"),
            region = LocalizedText("Artois, France", "Shimoliy Fransiya", "Северная Франция"),
            summary = LocalizedText("King Henry V's English longbowmen defeat heavy French cavalry charges across a rain-soaked battlefield.", "Qirol Genrix V ning uzun kamonchilari loyqa maydonda og'ir fransuz ritsarlarini to'xtatadi.", "Английские лучники Генриха V отражают натиск тяжелой французской конницы в грязи."),
            linkedMissionId = "mission_agincourt_1415"
        )
    )

    fun unitById(id: String): UnitArchetype =
        allUnits.firstOrNull { it.id == id } ?: allUnits.first()

    fun heroById(id: String): HeroDefinition =
        allHeroes.firstOrNull { it.id == id } ?: allHeroes.first()
}
