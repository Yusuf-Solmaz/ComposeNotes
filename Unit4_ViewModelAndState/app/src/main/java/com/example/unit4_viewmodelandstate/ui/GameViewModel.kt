package com.example.unit4_viewmodelandstate.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unit4_viewmodelandstate.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.unit4_viewmodelandstate.data.DEFAULT_REMAINING_COUNT
import com.example.unit4_viewmodelandstate.data.MAX_NO_OF_WORDS
import com.example.unit4_viewmodelandstate.data.SCORE_INCREASE
import kotlinx.coroutines.flow.update


data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    val score: Int = 0,
    val currentWordCount: Int = 1,
    val isGameOver: Boolean = false,
    val remainingCount: Int = DEFAULT_REMAINING_COUNT
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

    var userGuess by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    // pickRandomWordAndShuffle fonksiyonu, rastgele bir kelime seçer ve bu kelimeyi karıştırarak geri döner
    private fun pickRandomWordAndShuffle(): String {
        // Tüm kelimeler listesinden rastgele bir kelime seçilir
        currentWord = allWords.random()
        Log.d("GameFragment", "currentWord is $currentWord")

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

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true, remainingCount = currentState.remainingCount.dec())
            }
            if (_uiState.value.remainingCount == 0){
                _uiState.update { currentState ->
                    currentState.copy(isGameOver = true)
                }
            }
        }
        // Reset user guess
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            Log.d("GameFragment", "remainingCount: ${_uiState.value.remainingCount},isGameOver: ${_uiState.value.isGameOver}")
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
}