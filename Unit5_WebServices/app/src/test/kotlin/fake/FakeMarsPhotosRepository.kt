package fake

import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto
import com.example.marsphotos.data.network.mars_photos.repository.MarsPhotosRepository

class FakeMarsPhotosRepository : MarsPhotosRepository{
    override suspend fun getPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}