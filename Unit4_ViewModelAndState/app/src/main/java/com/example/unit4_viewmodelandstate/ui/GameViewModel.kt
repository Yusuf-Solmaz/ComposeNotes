package com.example.unit4_viewmodelandstate.ui

import androidx.lifecycle.ViewModel
import com.example.unit4_viewmodelandstate.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GameUiState(
    val currentScrambledWord: String = ""
)

/**
 ViewModel, UI’nin tükettiği durumu tutar ve açığa çıkarır.
 UI durumu, ViewModel tarafından dönüştürülen uygulama verileridir.
 UI, kullanıcı olaylarını ViewModel’e bildirir.
 ViewModel, kullanıcı eylemlerini işler ve durumu günceller.
 Güncellenen durum, UI’ye geri beslenir ve UI bunu render eder.
 */

class GameViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState>
        get() = _uiState.asStateFlow()
        // MutableStateFlow'un bir StateFlow versiyonunu döndürür.

    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()


    init {
        resetGame()
    }

    // pickRandomWordAndShuffle fonksiyonu, rastgele bir kelime seçer ve bu kelimeyi karıştırarak geri döner
    private fun pickRandomWordAndShuffle(): String {
        // Tüm kelimeler listesinden rastgele bir kelime seçilir
        currentWord = allWords.random()

        // Eğer seçilen kelime daha önce kullanıldıysa, yeniden rastgele kelime seçmeye çalışır
        if (usedWords.contains(currentWord)) {
            // Bu durumda, tekrar aynı işlemi yaparak yeni bir kelime seçilir
            return pickRandomWordAndShuffle()
        } else {
            // Eğer kelime daha önce kullanılmadıysa, bu kelime kullanılacak kelimeler listesine eklenir
            usedWords.add(currentWord)

            // Seçilen kelime karıştırılır ve geri döndürülür
            return shuffleCurrentWord(currentWord)
        }
    }

    // shuffleCurrentWord fonksiyonu, verilen kelimeyi karıştırarak geri döndürür
    private fun shuffleCurrentWord(word: String): String {
        // Kelimeyi karakter dizisine dönüştürür
        val tempWord = word.toCharArray()

        // Kelimenin karakterlerini karıştırır
        tempWord.shuffle()

        // Eğer karıştırılan kelime orijinal kelimeyle aynı ise, tekrar karıştırılır
        // Bu adım, kelimenin değiştiğinden emin olmak için yapılır
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }

        // Karıştırılmış kelimeyi string formatında geri döndürür
        return String(tempWord)
    }


    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
}