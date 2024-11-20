package com.example.unit4_navigationexercise.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unit4_navigationexercise.data.DataSource
import com.example.unit4_navigationexercise.ui.presentation.order.AccompanimentMenuScreen
import com.example.unit4_navigationexercise.ui.presentation.order.CheckoutScreen
import com.example.unit4_navigationexercise.ui.presentation.order.EntreeMenuScreen
import com.example.unit4_navigationexercise.ui.presentation.order.SideDishMenuScreen
import com.example.unit4_navigationexercise.ui.presentation.order.StartOrderScreen
import com.example.unit4_navigationexercise.ui.presentation.order.state.OrderUiState
import com.example.unit4_navigationexercise.ui.presentation.order.viewmodel.OrderViewModel
import com.example.unit4_navigationexercise.R

@Composable
fun LaunchTrayNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    orderState: OrderUiState,
    viewModel: OrderViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = LaunchTrayDestination.START.name,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = LaunchTrayDestination.START.name) {
            StartOrderScreen(
                onStartOrderButtonClicked = {
                    navController.navigate(LaunchTrayDestination.ENTREE_MENU.name)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
        composable(route= LaunchTrayDestination.ENTREE_MENU.name){
            EntreeMenuScreen(
                options = DataSource.entreeMenuItems,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(LaunchTrayDestination.START.name, inclusive = false)
                },
                onNextButtonClicked = {
                    navController.navigate(LaunchTrayDestination.SIDE_DISH_MENU.name)
                },
                onSelectionChanged = { item ->
                    viewModel.updateEntree(item)
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            )
        }


        composable(route = LaunchTrayDestination.SIDE_DISH_MENU.name) {
            SideDishMenuScreen(
                options = DataSource.sideDishMenuItems,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(LaunchTrayDestination.START.name, inclusive = false)
                },
                onNextButtonClicked = {
                    navController.navigate(LaunchTrayDestination.ACCOMPANIMENT_MENU.name)
                },
                onSelectionChanged = { item ->
                    viewModel.updateSideDish(item)
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            )
        }

        composable(route = LaunchTrayDestination.ACCOMPANIMENT_MENU.name) {
            AccompanimentMenuScreen(
                options = DataSource.accompanimentMenuItems,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(LaunchTrayDestination.START.name, inclusive = false)
                },
                onNextButtonClicked = {
                    navController.navigate(LaunchTrayDestination.CHECKOUT.name)
                },
                onSelectionChanged = { item ->
                    viewModel.updateAccompaniment(item)
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            )
        }

        composable(route = LaunchTrayDestination.CHECKOUT.name) {
            CheckoutScreen(
                orderUiState = orderState,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(LaunchTrayDestination.START.name, inclusive = false)
                },
                onNextButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(LaunchTrayDestination.START.name, inclusive = false)
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium),
                    )
            )
        }
    }
}