package viewmodel_test

import com.example.marsphotos.ui.screens.state.MarsUIState
import com.example.marsphotos.ui.screens.viewmodel.MarsViewModel
import fake.FakeDataSource
import fake.FakeMarsPhotosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MarsViewModelTest {

    //Eğer viewmodel'de kullanılan method Dispatchers.Main'de çalışmıyorsa bunu yapmaya gerek yoktur.
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() =
        runTest {

            val marsViewModel = MarsViewModel(
                photoRepo = FakeMarsPhotosRepository()
            )

            //coroutine tamamlanması beklenir, böylece loading durumu sonlandıktan sonra test gerçekleşir.
            advanceUntilIdle()

            val currentState = marsViewModel.marsUiState.value

            assertEquals(
                MarsUIState.Success("Success: ${FakeDataSource.photosList.size} Mars photos retrieved"),
                currentState
            )
        }

}

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}