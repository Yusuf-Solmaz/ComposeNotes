package com.example.ui_test

import com.example.data.MAX_NO_OF_WORDS
import com.example.data.SCORE_INCREASE
import com.example.data.getUnscrambledWord
import com.example.unit4_viewmodelandstate.ui.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class GameViewModelTest {
    private val viewModel = GameViewModel()

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        /**
         *  Uyarı: uiState'i bu şekilde almak, MutableStateFlow kullanıldığı için çalışır.
         *  Gelecek ünitelerde, veri akışı oluşturacak ve bu akışa tepki vermeniz gereken
         *  StateFlow'un gelişmiş kullanımını öğreneceksiniz. Bu senaryolar için birim testlerini
         *  farklı yöntemler/yaklaşımlar kullanarak yazmanız gerekecek. Bu, bu sınıftaki
         *  viewModel.uiState.value kullanımının tümüne uygulanır.
         **/
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // Mevcut kelimenin karıştırılmış olduğundan emin olun.
        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        // Mevcut kelime sayısının 1 olarak ayarlandığından emin olun.
        assertTrue(gameUiState.currentWordCount == 1)
        // Başlangıçta puanın 0 olduğundan emin olun.
        assertTrue(gameUiState.score == 0)
        // Yanlış kelime tahmini yapılmadığından emin olun.
        assertFalse(gameUiState.isGuessedWordWrong)
        // Oyunun bitmediğinden emin olun.
        assertFalse(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // Yanlış bir kelime girdisi verildiğinde
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value
        // Puanın değişmediğinden emin olun
        assertEquals(0, currentGameUiState.score)
        // checkUserGuess() metodunun isGuessedWordWrong bayrağını doğru şekilde güncellediğinden emin olun
        assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()
        currentGameUiState = viewModel.uiState.value

        // checkUserGuess() metodunun isGuessedWordWrong bayrağını doğru şekilde güncellediğinden emin olun.
        assertFalse(currentGameUiState.isGuessedWordWrong)
        // Puanın doğru şekilde güncellendiğinden emin olun.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()
        currentGameUiState = viewModel.uiState.value
        val lastWordCount = currentGameUiState.currentWordCount

        viewModel.skipWord()
        currentGameUiState = viewModel.uiState.value
        // Kelime atlandığında puanın değişmediğinden emin olun.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
        // Kelime atlandığında kelime sayısının 1 arttığından emin olun.
        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            // Her doğru cevaptan sonra, puanın doğru şekilde güncellendiğinden emin olun.
            assertEquals(expectedScore, currentGameUiState.score)
        }
        // Tüm sorular cevaplandıktan sonra, mevcut kelime sayısının güncel olduğundan emin olun.
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // 10 soru cevaplandıktan sonra oyunun bittiğinden emin olun.
        assertTrue(currentGameUiState.isGameOver)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}
