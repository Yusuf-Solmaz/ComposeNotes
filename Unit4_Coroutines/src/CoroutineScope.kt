import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/*
 coroutineScope: bir kapsam (scope) oluşturur ve bu kapsamda başlatılan tüm coroutine'ler birbiriyle ilişkili olarak çalışır.
 coroutineScope blok içindeki coroutine'lerin bitmesini bekler. Yani, bu scope içinde başlatılan tüm coroutine'ler eşzamanlı çalışır,
 ancak dışarı çıkmak için hepsinin tamamlanması gerekir. Yani, coroutine'ler birbirini beklemeden çalışır ve sonuçlar elde
 edildiğinde birleştirilir.

 */

/*
 Activity'de sağlanan coroutine scope olan lifecycleScope içinde bir coroutine başlatırsanız, eğer activity yok edilirse,
 lifecycleScope iptal edilir ve içindeki tüm çocuk coroutin'ler de otomatik olarak iptal edilir. Activity'nin yaşam döngüsünü
 takip eden bir coroutine'in bu davranışın istediğiniz bir şey olup olmadığına karar vermeniz gerekir. Bu durum viewModelScope
 için de aynıdır.
*/

/*
 launch() ve async() CoroutineScope interface'sinin extension fonksiyonlarıdır. CoroutineScope içinde CoroutineContext barındırır.
 CoroutineContext, coroutine'in çalışacağı bağlam hakkında bilgi sağlar. CoroutineContext, her öğesinin benzersiz bir anahtarı olan
 bir harita (map) gibidir. Bunlar zorunlu alanlar değildir, ancak bağlamda bulunabilecek bazı örnekler şunlardır:

 name - coroutine'i benzersiz bir şekilde tanımlamak için kullanılan isim
 job - coroutine'in yaşam döngüsünü kontrol eder
 dispatcher - işi uygun thread'e yönlendirir
 exception handler - coroutine içinde çalıştırılan kod tarafından fırlatılan istisnaları işler

 Not: Bunlar, CoroutineContext'in varsayılan değerleridir ve eğer bunlar için değer sağlanmazsa kullanılacaklardır:

 "coroutine" coroutine ismi olarak
 parent job yok
 Dispatchers.Default coroutine dispatcher'ı olarak
 exception handler yok

 Bir coroutine içinde yeni bir coroutine başlattığınızda, çocuk coroutine, ebeveyn coroutine'den CoroutineContext'i miras alır,
 ancak sadece yeni oluşturulan coroutine için job'ı özel olarak değiştirir. Ayrıca, launch() veya async() fonksiyonlarına,
 bağlamın farklı olmasını istediğiniz bölümleri geçirmek suretiyle, ebeveyn context'ten miras alınan öğeleri de geçersiz
 kılabilirsiniz.

 scope.launch(Dispatchers.Default) {
    ...
 }


*/

fun main() {
    runBlocking {
        println("Weather forecast")
        println(getWeatherReport())
        println("Have a good day!")
    }

}

suspend fun getWeatherReport() = coroutineScope {


    val forecast = async { getForecast1() }
    val temperature = async { getTemperature1() }

    //temperature.cancel()
    //coroutine iptal edilebilir


    return@coroutineScope "${forecast.await()} ${temperature.await()}"
}

suspend fun getForecast1(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature1(): String {
    delay(1000)
    return "30\u00b0C"
}


