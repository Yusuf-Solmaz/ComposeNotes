package com.example.unit4_navigationandapparchitecture

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.unit4_navigationandapparchitecture.data.Datasource
import com.example.unit4_navigationandapparchitecture.presentation.desert_screen.DessertClickerApp
import com.example.unit4_navigationandapparchitecture.ui.theme.Unit4_NavigationAndAppArchitectureTheme


/**
 Bir Android uygulamasında birden fazla aktivite olabilir. Ancak, tek bir aktivite kullanılması önerilmektedir

 -------------------------------

 onCreate() metodu yalnızca bir kez, aktivite ilk kez oluşturulduğunda çağrılır. onStart() metodu ise,
 aktivite her görünür hale geldiğinde çağrılabilir.

 -------------------------------

 onStart ve onResume'da activity görünür haldedir. Sadece onResume'da kullanıcı activity'i ile etkileşime
 girebilir.

 -------------------------------

 onCreate() ve onDestroy(), bir aktivite örneğinin yaşam süresi boyunca yalnızca bir kez çağrılır:
 onCreate() uygulamayı ilk kez başlatmak için ve onDestroy() aktivitenin kullanmakta olduğu nesneleri
 boşaltmak, kapatmak veya yok etmek için çağrılır, böylece bu nesneler kaynakları (örneğin belleği)
 kullanmaya devam etmez.

 -------------------------------

 Uygulamanız arka planda iken, sistem kaynaklarını ve pil ömrünü korumak için aktif olarak çalışmamalıdır.
 Uygulamanız arka plana geçtiğinde devam eden işlemleri duraklatmak, ön plana geçtiğinde ise bu işlemleri
 yeniden başlatmak için Aktivite yaşam döngüsü ve geri çağrılarını kullanırsınız.

 -------------------------------

 Uygulama arka plana alındığında onPause() (focus kaybı) ve onStop() (UI'ın ekranda görünmemesi) metodu çağrılır.
 Aktivite durdurulmuş olsa da Aktivite nesnesi arka planda bellekte tutulur. Android işletim sistemi aktiviteyi kapatmamıştır.
 Kullanıcı uygulamaya geri dönebileceği için Android aktivite kaynaklarını bellekte tutar.

 -------------------------------

 Uygulama tekrar açıldığında onRestart(), onStart() ve onResume() metodu çağrılır. onRestart() metodu,
 aktivitenizin ilk kez başlatılmadığı durumlarda çalıştırmak istediğiniz kodları koyabileceğiniz bir yerdir.

 Aktivite tekrar ön plana döndüğünde onCreate() metodu yeniden çağrılmaz. Aktivite nesnesi yok edilmediği
 için tekrar oluşturulması gerekmez.

 -------------------------------

 Bir uygulama başlatıldığında ve onStart() çağrıldığında, uygulama ekranda görünür hale gelir. onResume()
 çağrıldığında, uygulama kullanıcı odağını kazanır

 Uygulama arka plana geçtiğinde, onPause()’dan sonra odağını kaybeder ve onStop()’tan sonra artık görünür
 olmaz.

 Odağı ve görünürlüğü ayırt etmek önemlidir. Bir aktivite ekranda kısmen görünür olabilir ancak kullanıcı
 odağına sahip olmayabilir.

 -------------------------------

 onPause() içinde çalışan herhangi bir kod diğer şeylerin görüntülenmesini engeller, bu yüzden onPause()
 içindeki kodu hafif tutmak önemlidir. Örneğin, bir telefon araması geldiğinde, onPause()'daki kod gelen
 arama bildirimini geciktirebilir.

 -------------------------------

 Configuration change olduğunda onPause(), onStop() ve onDestroy() çalışır.
 */

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")

        setContent {
            Unit4_NavigationAndAppArchitectureTheme() {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                ) {
                    DessertClickerApp(desserts = Datasource.dessertList)
                }
            }
        }
    }
}
