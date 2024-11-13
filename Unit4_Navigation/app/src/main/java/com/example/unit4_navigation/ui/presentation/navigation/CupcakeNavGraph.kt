package com.example.unit4_navigation.ui.presentation.navigation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unit4_navigation.data.DataSource
import com.example.unit4_navigation.ui.presentation.order.StartOrderScreen
import com.example.unit4_navigation.ui.presentation.order.state.OrderUiState
import com.example.unit4_navigation.R
import com.example.unit4_navigation.ui.presentation.option.SelectOptionScreen
import com.example.unit4_navigation.ui.presentation.order.viewmodel.OrderViewModel
import com.example.unit4_navigation.ui.presentation.summary.OrderSummaryScreen

/**
NavController: Uygulamanızdaki ekranlar arasında geçiş yapmaktan sorumludur.
NavGraph: Geçiş yapılacak bileşenleri eşleştirir.
NavHost: NavGraph'ın geçerli ekranını göstermek için kapsayıcı olarak işlev gören bir composable'dır.
 */

/* Bu kodda OrderViewModel üzerindeki metodlar ile sadece StartOrderScreen'de değil ayrıca diğer screen'lerde de
   higher-order fonksiyonlar yardımıyla kullanılmıştır. Böylece OrderUiState'deki değişimleri diğer screen'lerde
   observe edebiliriz.
 */

/**
 Ekranlar arası geçişte best practice navController'ı screen'de argüman olarak vermek yerine, screende higher order ile
 fonksiyonu alıp NavHost içinde navController işlemlerini yapmak daha doğru olacaktır.

 ------------------------
 Intent Nedir?

 Intent, sistemin bir eylem gerçekleştirmesini istemek için kullanılan bir istektir ve genellikle yeni
 bir aktivite sunulmasını sağlar.
 */

@Composable
fun CupcakeNavigation(
    navController: NavHostController,
    cupcakeUiState: OrderUiState,
    viewModel: OrderViewModel = viewModel(),
    modifier: Modifier = Modifier,
    title: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = CupcakeDestination.START.name,
        modifier = modifier.fillMaxSize()
    ) {


        composable(route = CupcakeDestination.START.name) {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {
                    viewModel.setQuantity(it)
                    navController.navigate(CupcakeDestination.FLAVOR.name)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
            title(stringResource(CupcakeDestination.START.title))
        }
        composable(route = CupcakeDestination.FLAVOR.name) {
            val context = LocalContext.current

            SelectOptionScreen(
                subtotal = cupcakeUiState.price,
                onNextButtonClicked = { navController.navigate(CupcakeDestination.PICKUP.name) },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                options = DataSource.flavors.map { id -> context.resources.getString(id) },
                onSelectionChanged = { viewModel.setFlavor(it) },
                modifier = Modifier.fillMaxHeight()
            )
            title(stringResource(CupcakeDestination.FLAVOR.title))
        }
        composable(route = CupcakeDestination.PICKUP.name) {
            SelectOptionScreen(
                subtotal = cupcakeUiState.price,
                onNextButtonClicked = { navController.navigate(CupcakeDestination.SUMMARY.name) },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                options = cupcakeUiState.pickupOptions,
                onSelectionChanged = { viewModel.setDate(it) },
                modifier = Modifier.fillMaxHeight()
            )
            title(stringResource(CupcakeDestination.PICKUP.title))
        }
        composable(route = CupcakeDestination.SUMMARY.name) {

            val context = LocalContext.current

            OrderSummaryScreen(
                orderUiState = cupcakeUiState,
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                onSendButtonClicked = { subject: String, summary: String ->
                    shareOrder(context= context, subject = subject, summary = summary)
                },
                modifier = Modifier.fillMaxHeight()
            )
            title(stringResource(CupcakeDestination.SUMMARY.title))
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeDestination.START.name, inclusive = false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}

