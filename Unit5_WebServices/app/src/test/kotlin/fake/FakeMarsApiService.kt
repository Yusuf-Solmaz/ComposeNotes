package fake

import com.example.marsphotos.data.network.mars_photos.datasource.MarsApiService
import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto

class FakeMarsApiService : MarsApiService {
    override suspend fun getPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}