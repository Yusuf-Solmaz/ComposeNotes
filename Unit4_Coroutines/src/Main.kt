import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/*
 Yazılan kodların synchronous yani eşzamanlı çalışma eğilimindedir. Bir kod bloğu çalışmadan altındaki çalışmaz.
 Fakat coroutine ile asynchronous kod yazılabilir.

 launch(): Bir işlemi asynch çalışmasını sağlar.
 async(): Bir işlemi arka planda başlatır ve bir sonuç döndüreceğini taahhüt eder. ir Deferred nesnesidir. Bu nesne,
 işlemin sonucunu hazır olduğunda sağlar.
 await(): Bir Deferred nesnesinin sonucuna ulaşmak için kullanılır. Eğer işlem henüz tamamlanmadıysa, await() çağrısı o
 işlemin tamamlanmasını bekler.

 suspend: Kotlin'deki suspend anahtar kelimesi, askıya alınabilir (suspending) fonksiyonları tanımlamak için kullanılır.
 Bir fonksiyon suspend olarak işaretlendiğinde, bu fonksiyon normalde çalışırken başka bir coroutine tarafından askıya
 alınabilir ve daha sonra kaldığı yerden devam edebilir.
*/


fun main() {
    runBlocking {
        println("Hava durumu tahmini")
        val forecast: Deferred<String> = async {
            getForecast()
        }
        val temperature: Deferred<String> = async {
            getTemperature()
        }
        println("${forecast.await()} ${temperature.await()}")
        println("İyi günler!")
    }
}

suspend fun getForecast(): String {
    delay(1000)
    return "Güneşli"
}

suspend fun getTemperature(): String {
    delay(1000)
    return "30°C"
}