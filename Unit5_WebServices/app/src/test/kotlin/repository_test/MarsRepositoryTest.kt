package repository_test

import com.example.marsphotos.data.network.mars_photos.repo_impl.MarsPhotosRepositoryImpl
import fake.FakeDataSource
import fake.FakeMarsApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MarsRepositoryTest {
    @Test
    fun marsPhotosRepository_getMarsPhotos_verifyPhotoList() {
        runTest{
            val repository = MarsPhotosRepositoryImpl(
                marsApiService = FakeMarsApiService()
            )
            assertEquals (FakeDataSource.photosList, repository.getPhotos())
        }
    }

}