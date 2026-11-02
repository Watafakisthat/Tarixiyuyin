# Centuries — Offline Historical Tactics Game for Android

> *"Fight history. Rewrite it."*
> *«Tarixda jang qiling. Uni qayta yozing.»*
> *«Сражайся в истории. Перепиши её.»*

## Tech Stack Choice
Built as a **100% Native Android Application** using **Kotlin, Jetpack Compose, Custom Compose Canvas (Flat-Top Hex Engine), Room (SQLite), and Kotlin Coroutines**.
- **100% Offline:** Zero internet permissions (`android.permission.INTERNET` and `ACCESS_NETWORK_STATE` are explicitly stripped via `tools:node="remove"` in `AndroidManifest.xml`). No ads, no analytics, no cloud dependencies, no paywalls.

---

## How to Build
1. **Debug APK:**
   ```bash
   gradle :app:assembleDebug
   ```
   Output: `app/build/outputs/apk/debug/app-debug.apk`
2. **Release AAB / APK:**
   ```bash
   gradle :app:bundleRelease
   gradle :app:assembleRelease
   ```
3. **Run Unit & Robolectric Tests:**
   ```bash
   gradle :app:testDebugUnitTest
   ```

---

## How to Add a New Campaign Pack or Edit Historical Text
All campaigns, missions, units, heroes, Silk Road cities, and historian review credits are managed through the data-driven content loader (`app/src/main/assets/packs/` and `com.example.data.pack`):
1. Open `app/src/main/assets/packs/pack_manifest.json` and `app/src/main/assets/packs/historian_review_pack.json`.
2. To override any mission's historical briefing, aftermath debrief, or reviewer credit without touching Kotlin code, add an entry to `"missionTextOverrides"` in `app/src/main/assets/packs/historian_review_pack.json`:
   ```json
   {
     "missionId": "mission_kondurcha_1391",
     "reviewedBy": "Dr. Example Historian, Institute of History",
     "briefing": {
       "en": "Updated English briefing...",
       "uz": "Yangilangan o'zbekcha ma'lumot...",
       "ru": "Обновленная русская сводка..."
     }
   }
   ```
3. To add a new campaign with custom hex maps, terrain overrides, and unit spawns, add a `CampaignDefinition` entry in `com/example/data/pack/` and register its ID in `pack_manifest.json`.

---

## How to Add a New Language
1. Add a new `res/values-<lang>/strings.xml` resource file (currently ships with `values/strings.xml` [English], `values-uz/strings.xml` [Uzbek Latin], and `values-ru/strings.xml` [Russian]).
2. Add the language code to `LanguageCode` in `com/example/model/GameModels.kt` and `UiStrings.kt`.
3. Every `LocalizedText` object automatically falls back to `en` if a translation field is blank.

---

## Bundled Assets & Licenses
| Asset | Path | License |
| :--- | :--- | :--- |
| **Cinzel Font** | `app/src/main/res/font/cinzel.ttf` | SIL Open Font License 1.1 (Google Fonts) |
| **Lora Font** | `app/src/main/res/font/lora.ttf` | SIL Open Font License 1.1 (Google Fonts) |
| **Adaptive Launcher Icon** | `res/drawable/ic_launcher_foreground.xml`, `ic_launcher_background.xml`, `res/mipmap-*/` | Original Vector & Raster Art (CC0 / Project Owned) |
| **Hero Portraits & Unit Sprites** | `com/example/ui/components/HistoricalArtwork.kt` | 100% Original Procedural Compose Canvas Art (Project Owned) |
| **Procedural Audio & SFX** | `com/example/audio/SoundEngine.kt` | 100% Original Real-Time PCM Waveform Synthesis (Project Owned) |
| **Historical Content Packs** | `app/src/main/assets/packs/*.json` | Original Encyclopedic Text & Public Domain Primary Chronicle Citations |
