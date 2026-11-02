package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

/**
 * 100% Offline Procedural Audio Synthesizer, Ambient Campaign Music Player, Haptics & Optional TTS Hook.
 * Synthesizes authentic modal plucked strings (Dutar/Rubab/Oud), steppe drums, battle horns (Karnay),
 * arrow volleys, hooves, sword clashes, and siege impacts directly in memory using PCM AudioTrack.
 */
class SoundEngine(private val context: Context?) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var musicJob: Job? = null
    private var currentThemeId: String = "theme_central_asia_dutar"

    var musicVolume: Float = 0.65f
    var sfxVolume: Float = 0.80f
    var hapticsEnabled: Boolean = true
    var ttsEnabled: Boolean = false

    private var tts: TextToSpeech? = null
    private var ttsReady: Boolean = false

    fun setCampaignTheme(themeId: String) {
        currentThemeId = themeId
    }

    fun startAmbientMusicIfEnabled() {
        if (musicJob?.isActive == true) return
        musicJob = scope.launch {
            var step = 0
            while (isActive) {
                if (musicVolume > 0.05f && context != null) {
                    val freqs = when (currentThemeId) {
                        "theme_steppe_drums" -> doubleArrayOf(110.0, 130.81, 146.83, 164.81, 196.0, 220.0)
                        "theme_middle_east_oud" -> doubleArrayOf(146.83, 155.56, 185.00, 196.00, 220.00, 233.08, 277.18)
                        "theme_europe_strings" -> doubleArrayOf(146.83, 164.81, 174.61, 196.00, 220.00, 246.94, 261.63)
                        else -> doubleArrayOf(146.83, 164.81, 196.00, 220.00, 261.63, 293.66) // Dutar / Rubab Silk Road scale
                    }
                    val noteFreq = freqs[step % freqs.size]
                    val droneFreq = freqs.first() * 0.5
                    playPluckedMotifNote(noteFreq, droneFreq, (musicVolume * 0.22f).coerceIn(0f, 0.35f))
                    step++
                }
                delay(1800L)
            }
        }
    }

    fun stopAmbientMusic() {
        musicJob?.cancel()
        musicJob = null
    }

    fun playTap() {
        if (sfxVolume <= 0.02f || context == null) return
        scope.launch {
            playSynthBurst(durationMs = 45, volume = sfxVolume * 0.35f) { t, _ ->
                sin(2.0 * PI * 520.0 * t) * exp(-t * 48.0)
            }
        }
    }

    fun playHooves() {
        vibrate(18)
        if (sfxVolume <= 0.02f || context == null) return
        scope.launch {
            playSynthBurst(durationMs = 240, volume = sfxVolume * 0.55f) { t, rng ->
                val pulse1 = exp(-((t - 0.03) * (t - 0.03)) * 1800.0)
                val pulse2 = exp(-((t - 0.11) * (t - 0.11)) * 1800.0)
                val pulse3 = exp(-((t - 0.18) * (t - 0.18)) * 1800.0)
                (sin(2.0 * PI * 135.0 * t) + (rng.nextDouble() - 0.5) * 0.6) * (pulse1 + pulse2 + pulse3)
            }
        }
    }

    fun playArrowVolley() {
        vibrate(25)
        if (sfxVolume <= 0.02f || context == null) return
        scope.launch {
            playSynthBurst(durationMs = 280, volume = sfxVolume * 0.6f) { t, rng ->
                val envelope = sin(PI * (t / 0.28)).coerceAtLeast(0.0)
                val whistle = sin(2.0 * PI * (900.0 - 1600.0 * t) * t) * 0.35
                val noise = (rng.nextDouble() - 0.5) * 0.9
                (whistle + noise) * envelope
            }
        }
    }

    fun playSwordClash() {
        vibrate(40)
        if (sfxVolume <= 0.02f || context == null) return
        scope.launch {
            playSynthBurst(durationMs = 260, volume = sfxVolume * 0.7f) { t, rng ->
                val ring = (sin(2.0 * PI * 1180.0 * t) + 0.6 * sin(2.0 * PI * 1790.0 * t) + 0.4 * sin(2.0 * PI * 2420.0 * t))
                val burst = (rng.nextDouble() - 0.5) * exp(-t * 28.0)
                (ring * 0.45 * exp(-t * 14.0)) + burst
            }
        }
    }

    fun playSiegeImpact() {
        vibrate(65)
        if (sfxVolume <= 0.02f || context == null) return
        scope.launch {
            playSynthBurst(durationMs = 380, volume = sfxVolume * 0.8f) { t, rng ->
                val boom = sin(2.0 * PI * (85.0 - 90.0 * t).coerceAtLeast(30.0) * t) * exp(-t * 7.5)
                val debris = (rng.nextDouble() - 0.5) * exp(-t * 9.0)
                (boom * 0.7 + debris * 0.5)
            }
        }
    }

    fun playBattleHorn() {
        vibrate(50)
        if (sfxVolume <= 0.02f || context == null) return
        scope.launch {
            playSynthBurst(durationMs = 520, volume = sfxVolume * 0.65f) { t, _ ->
                val env = (t * 8.0).coerceAtMost(1.0) * exp(-t * 2.5)
                val f = 196.0 // G3 Karnay / War horn fundamental
                (sin(2.0 * PI * f * t) + 0.55 * sin(2.0 * PI * f * 2.0 * t) + 0.35 * sin(2.0 * PI * f * 3.0 * t)) * env * 0.5
            }
        }
    }

    fun playVictoryFanfare() {
        vibrate(60)
        if (sfxVolume <= 0.02f || context == null) return
        scope.launch {
            playSynthBurst(durationMs = 600, volume = sfxVolume * 0.65f) { t, _ ->
                val f = if (t < 0.2) 293.66 else if (t < 0.4) 369.99 else 440.0
                val env = exp(-(t % 0.2) * 5.0)
                (sin(2.0 * PI * f * t) + 0.4 * sin(2.0 * PI * f * 2.0 * t)) * env * 0.6
            }
        }
    }

    fun narrateBriefingIfEnabled(text: String, langCode: String) {
        if (!ttsEnabled || context == null) return
        if (tts == null) {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    ttsReady = true
                    speakNow(text, langCode)
                }
            }
        } else if (ttsReady) {
            speakNow(text, langCode)
        }
    }

    fun stopNarration() {
        try {
            tts?.stop()
        } catch (_: Exception) {
        }
    }

    private fun speakNow(text: String, langCode: String) {
        try {
            val locale = when (langCode) {
                "ru" -> Locale("ru")
                "uz" -> Locale("uz")
                else -> Locale.US
            }
            tts?.language = locale
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "centuries_briefing")
        } catch (_: Exception) {
        }
    }

    private fun vibrate(durationMs: Long) {
        if (!hapticsEnabled || context == null) return
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(durationMs)
            }
        } catch (_: Exception) {
        }
    }

    private fun playPluckedMotifNote(freq: Double, droneFreq: Double, volume: Float) {
        playSynthBurst(durationMs = 650, volume = volume) { t, _ ->
            val pluckEnv = exp(-t * 4.8)
            val droneEnv = sin(PI * (t / 0.65)).coerceAtLeast(0.0)
            val pluck = (sin(2.0 * PI * freq * t) + 0.45 * sin(2.0 * PI * freq * 2.0 * t) + 0.2 * sin(2.0 * PI * freq * 3.0 * t)) * pluckEnv
            val drone = sin(2.0 * PI * droneFreq * t) * 0.3 * droneEnv
            (pluck * 0.6 + drone * 0.4)
        }
    }

    private fun playSynthBurst(
        durationMs: Int,
        volume: Float,
        sampleFn: (timeSec: Double, rng: Random) -> Double
    ) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * durationMs) / 1000
            val pcm = ShortArray(numSamples)
            val rng = Random(42)
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate.toDouble()
                val raw = sampleFn(t, rng).coerceIn(-1.0, 1.0)
                pcm[i] = (raw * volume.coerceIn(0f, 1f) * Short.MAX_VALUE).toInt().toShort()
            }

            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(pcm.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(pcm, 0, pcm.size)
            track.play()
            Thread.sleep(durationMs.toLong() + 20L)
            track.stop()
            track.release()
        } catch (_: Throwable) {
            // AudioTrack may be uninitialized in headless unit test environments; ignore safely
        }
    }
}
