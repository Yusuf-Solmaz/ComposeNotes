import kotlinx.coroutines.*

/*
 Yazılan kodların synchronous yani eşzamanlı çalışma eğilimindedir. Bir kod bloğu çalışmadan altındaki çalışmaz.
 Fakat coroutine ile asynchronous kod yazılabilir.

 runBlocking: bir coroutine başlatmak için senkron bir yol sağlar. runBlocking, bir suspend fonksiyonunu bloklayarak çalıştıran,
 kendi coroutine'ini başlatan ve çalışmasını tamamlayana kadar bekleyen bir fonksiyondur. Genellikle ana thread üzerinde çalışırken,
 coroutine'ler başlatılmak istendiğinde veya testlerde, örneğin main() fonksiyonunda, kullanılır.

 launch(): Bir işlemi asynch çalışmasını sağlar. Bir Job döndürür.Job, Kotlin Coroutines'da bir coroutine'in çalışma
 durumunu temsil eden bir nesnedir. Yani, Job bir coroutine'in yürütülüp yürütülmediği, tamamlanıp tamamlanmadığı gibi bilgileri tutar.

 async(): Bir işlemi arka planda başlatır ve bir sonuç döndüreceğini taahhüt eder. ir Deferred nesnesidir. Bu nesne,
 işlemin sonucunu hazır olduğunda sağlar.

 Not: async() fonksiyonu ile başlatılan bir coroutine'den dönen Deferred nesnesi de bir Job'tur ve coroutine'in gelecekteki sonucunu tutar.

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
