package fake

import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto

object FakeDataSource {

    const val idOne = "img1"
    const val idTwo = "img2"
    const val imgOne = "url.1"
    const val imgTwo = "url.2"
    val photosList = listOf(
        MarsPhoto(
            id = idOne,
            image = imgOne
        ),
        MarsPhoto(
            id = idTwo,
            image = imgTwo
        )
    )
}