package com.example.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class SpeechHelper(context: Context) {
    private val appContext = context.applicationContext

    private var tts: TextToSpeech? = null
    private var ttsReady = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _speechError = MutableStateFlow<String?>(null)
    val speechError: StateFlow<String?> = _speechError.asStateFlow()

    private var speechRecognizer: SpeechRecognizer? = null

    init {
        tts = TextToSpeech(appContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsReady = true
                tts?.language = Locale.US
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isSpeaking.value = true
                    }

                    override fun onDone(utteranceId: String?) {
                        _isSpeaking.value = false
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        _isSpeaking.value = false
                    }
                })
            }
        }
    }

    fun speakEnglish(text: String, rate: Float = 0.92f) {
        if (!ttsReady || text.isBlank()) return
        tts?.language = Locale.US
        tts?.setSpeechRate(rate.coerceIn(0.65f, 1.35f))
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tt_utterance_${System.currentTimeMillis()}")
    }

    fun stopSpeaking() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun isRecognitionAvailable(): Boolean = SpeechRecognizer.isRecognitionAvailable(appContext)

    /**
     * Starts microphone recognition ONLY when called explicitly after RECORD_AUDIO permission is granted.
     * Never stores audio recordings.
     */
    fun startListening(onResult: (String) -> Unit) {
        _speechError.value = null
        if (!SpeechRecognizer.isRecognitionAvailable(appContext)) {
            _speechError.value = "Speech recognition service is not available on this device. You can type your answer below."
            return
        }
        stopListening()
        val recognizer = SpeechRecognizer.createSpeechRecognizer(appContext)
        speechRecognizer = recognizer
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                _isListening.value = true
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                _isListening.value = false
            }

            override fun onError(error: Int) {
                _isListening.value = false
                _speechError.value = when (error) {
                    SpeechRecognizer.ERROR_NO_MATCH -> "Could not hear clearly. Please speak again or type below."
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Listening timed out. Tap the mic when ready."
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission is required for voice practice."
                    else -> "Voice recognition unavailable right now. Please type your sentence."
                }
            }

            override fun onResults(results: Bundle?) {
                _isListening.value = false
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val best = matches?.firstOrNull().orEmpty()
                if (best.isNotBlank()) {
                    onResult(best)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }
        _isListening.value = true
        recognizer.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        speechRecognizer?.destroy()
        speechRecognizer = null
        _isListening.value = false
    }

    fun clearError() {
        _speechError.value = null
    }

    fun release() {
        stopSpeaking()
        stopListening()
        tts?.shutdown()
        tts = null
    }
}
