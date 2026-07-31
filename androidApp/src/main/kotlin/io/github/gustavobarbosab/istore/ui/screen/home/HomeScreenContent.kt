package io.github.gustavobarbosab.istore.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.gustavobarbosab.istore.R
import io.github.gustavobarbosab.istore.ui.screen.home.component.ProductList

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        HomeHeader()
        when (state) {
            HomeUiState.Loading -> HomeLoading()
            is HomeUiState.Ready -> ProductList(
                products = state.products,
                onProductClick = { productId -> onEvent(HomeEvent.OnProductClicked(productId)) },
            )
        }
    }
}

@Composable
private fun HomeHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "iStore logo",
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = "iStore",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
private fun HomeLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
