package io.github.gustavobarbosab.istore.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.gustavobarbosab.istore.screen.checkout.CheckoutScreen
import io.github.gustavobarbosab.istore.screen.confirmation.ConfirmationScreen
import io.github.gustavobarbosab.istore.screen.detail.DetailScreen
import io.github.gustavobarbosab.istore.screen.history.HistoryScreen
import io.github.gustavobarbosab.istore.screen.home.HomeScreen
import io.github.gustavobarbosab.istore.screen.profile.ProfileScreen

/**
 * NavHost do app. Home / History / Profile são as telas de topo (bottom bar).
 * Detail / Checkout / Confirmation são empilhadas por cima, sem bottom bar.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            if (currentDestination.isTopLevel()) {
                AppBottomBar(
                    currentDestination = currentDestination,
                    onNavigate = { destination ->
                        navController.navigate(destination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination,
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
        ) {
            composable<HomeDestination> {
                HomeScreen(
                    onNavigateToDetail = { productId ->
                        navController.navigate(DetailDestination(productId))
                    },
                )
            }

            composable<HistoryDestination> {
                HistoryScreen()
            }

            composable<ProfileDestination> {
                ProfileScreen()
            }

            composable<DetailDestination> { entry ->
                val destination = entry.toRoute<DetailDestination>()
                DetailScreen(
                    productId = destination.productId,
                    onBack = { navController.popBackStack() },
                    onNavigateToCheckout = { productId ->
                        navController.navigate(CheckoutDestination(productId))
                    },
                )
            }

            composable<CheckoutDestination> { entry ->
                val destination = entry.toRoute<CheckoutDestination>()
                CheckoutScreen(
                    productId = destination.productId,
                    onBack = { navController.popBackStack() },
                    onNavigateToConfirmation = { paymentId ->
                        navController.navigate(ConfirmationDestination(paymentId)) {
                            popUpTo<HomeDestination> { inclusive = false }
                        }
                    },
                )
            }

            composable<ConfirmationDestination> { entry ->
                val destination = entry.toRoute<ConfirmationDestination>()
                ConfirmationScreen(
                    paymentId = destination.paymentId,
                    onNavigateToHome = {
                        navController.navigate(HomeDestination) {
                            popUpTo<HomeDestination> { inclusive = true }
                        }
                    },
                    onNavigateToHistory = {
                        navController.navigate(HistoryDestination) {
                            popUpTo<HomeDestination> { inclusive = false }
                        }
                    },
                )
            }
        }
    }
}

private fun NavDestination?.isTopLevel(): Boolean {
    if (this == null) return false
    return hasRoute(HomeDestination::class) ||
        hasRoute(HistoryDestination::class) ||
        hasRoute(ProfileDestination::class)
}

@Composable
private fun AppBottomBar(
    currentDestination: NavDestination?,
    onNavigate: (Destination) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination?.hasRoute(HomeDestination::class) == true,
            onClick = { onNavigate(HomeDestination) },
            icon = { Text("🏠") },
            label = { Text("Home") },
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute(HistoryDestination::class) == true,
            onClick = { onNavigate(HistoryDestination) },
            icon = { Text("📦") },
            label = { Text("Pedidos") },
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute(ProfileDestination::class) == true,
            onClick = { onNavigate(ProfileDestination) },
            icon = { Text("👤") },
            label = { Text("Perfil") },
        )
    }
}
