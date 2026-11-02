package com.example.data.pack

import android.content.Context
import com.example.model.CampaignDefinition
import com.example.model.ChronicleCityEntry
import com.example.model.ChronicleTimelineEvent
import com.example.model.HeroDefinition
import com.example.model.LocalizedText
import com.example.model.MissionDefinition
import com.example.model.UnitArchetype
import org.json.JSONObject

data class LoadedContentBundle(
    val schemaVersion: Int,
    val campaigns: List<CampaignDefinition>,
    val units: List<UnitArchetype>,
    val heroes: List<HeroDefinition>,
    val cities: List<ChronicleCityEntry>,
    val timeline: List<ChronicleTimelineEvent>,
    val reviewerCredits: List<Pair<String, String>>
)

/**
 * Loads bundled JSON content packs from `assets/packs/` and merges any historian text overrides
 * or additional mission packs without touching Kotlin code.
 */
object ContentPackLoader {

    private var cachedBundle: LoadedContentBundle? = null

    fun loadAll(context: Context? = null): LoadedContentBundle {
        cachedBundle?.let { return it }

        val baseCampaigns = listOf(
            CampaignsCatalogCentralAsia.campaignTimurRise,
            CampaignsCatalogCentralAsia.campaignMongolKhwarazm,
            CampaignsCatalogWest.campaignCrusades,
            CampaignsCatalogWest.campaignEuropeBonus
        )

        var reviewerCredits = listOf(
            "Central Asia & Transoxiana" to "Cross-referenced with Yazdi (Zafarnama), Ibn al-Athir, Juvayni, al-Nasawi & Rashid al-Din",
            "Levant & Crusades" to "Cross-referenced with Baha ad-Din ibn Shaddad, Imad ad-Din & William of Tyre",
            "Medieval Europe" to "Cross-referenced with Anglo-Saxon Chronicle, Bayeux Tapestry & Gesta Henrici Quinti"
        )

        val overridesMap = mutableMapOf<String, JSONObject>()

        if (context != null) {
            try {
                val reviewJsonText = context.assets.open("packs/historian_review_pack.json")
                    .bufferedReader().use { it.readText() }
                val root = JSONObject(reviewJsonText)
                val creditsArr = root.optJSONArray("reviewerCredits")
                if (creditsArr != null && creditsArr.length() > 0) {
                    val parsedCredits = mutableListOf<Pair<String, String>>()
                    for (i in 0 until creditsArr.length()) {
                        val obj = creditsArr.getJSONObject(i)
                        parsedCredits.add(
                            obj.optString("region", "General") to obj.optString("status", "")
                        )
                    }
                    reviewerCredits = parsedCredits
                }
                val overridesArr = root.optJSONArray("missionTextOverrides")
                if (overridesArr != null) {
                    for (i in 0 until overridesArr.length()) {
                        val obj = overridesArr.getJSONObject(i)
                        val missionId = obj.optString("missionId")
                        if (missionId.isNotBlank()) {
                            overridesMap[missionId] = obj
                        }
                    }
                }
            } catch (_: Exception) {
                // Fallback gracefully to bundled catalog if asset stream is unavailable in JVM unit test
            }
        }

        val mergedCampaigns = if (overridesMap.isEmpty()) {
            baseCampaigns
        } else {
            baseCampaigns.map { campaign ->
                campaign.copy(
                    missions = campaign.missions.map { mission ->
                        val ov = overridesMap[mission.id]
                        if (ov != null) {
                            mission.copy(
                                briefing = parseLocalizedOverride(ov.optJSONObject("briefing"), mission.briefing),
                                whatReallyHappened = parseLocalizedOverride(ov.optJSONObject("whatReallyHappened"), mission.whatReallyHappened),
                                reviewedBy = ov.optString("reviewedBy", mission.reviewedBy)
                            )
                        } else {
                            mission
                        }
                    }
                )
            }
        }

        val bundle = LoadedContentBundle(
            schemaVersion = 1,
            campaigns = mergedCampaigns,
            units = UnitsAndHeroesCatalog.allUnits,
            heroes = UnitsAndHeroesCatalog.allHeroes,
            cities = UnitsAndHeroesCatalog.allCities,
            timeline = UnitsAndHeroesCatalog.timelineEvents,
            reviewerCredits = reviewerCredits
        )
        cachedBundle = bundle
        return bundle
    }

    private fun parseLocalizedOverride(json: JSONObject?, fallback: LocalizedText): LocalizedText {
        if (json == null) return fallback
        return LocalizedText(
            en = json.optString("en", fallback.en),
            uz = json.optString("uz", fallback.uz),
            ru = json.optString("ru", fallback.ru)
        )
    }

    fun findMission(missionId: String, context: Context? = null): MissionDefinition? {
        return loadAll(context).campaigns.asSequence()
            .flatMap { it.missions.asSequence() }
            .firstOrNull { it.id == missionId }
    }
}
