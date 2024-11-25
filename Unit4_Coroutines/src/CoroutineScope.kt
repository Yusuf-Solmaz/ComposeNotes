import kotlinx.coroutines.*

/*
 coroutineScope: bir kapsam (scope) oluşturur ve bu kapsamda başlatılan tüm coroutine'ler birbiriyle ilişkili olarak çalışır.
 coroutineScope blok içindeki coroutine'lerin bitmesini bekler. Yani, bu scope içinde başlatılan tüm coroutine'ler eşzamanlı çalışır,
 ancak dışarı çıkmak için hepsinin tamamlanması gerekir. Yani, coroutine'ler birbirini beklemeden çalışır ve sonuçlar elde
 edildiğinde birleştirilir.

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


