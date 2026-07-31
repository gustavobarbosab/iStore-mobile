package io.github.gustavobarbosab.istore.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.gustavobarbosab.istore.ui.screen.detail.component.ProductDetail

@Composable
fun DetailScreenContent(
    state: DetailUiState,
    onEvent: (DetailEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        IconButton(onClick = onBack, modifier = Modifier.padding(4.dp)) {
            Text("←", style = MaterialTheme.typography.titleLarge)
        }
        when (state) {
            DetailUiState.Loading -> Loading()
            is DetailUiState.Ready -> ProductDetail(
                product = state.product,
                onBuyClick = { onEvent(DetailEvent.OnBuyClicked) },
            )
            DetailUiState.NotFound -> NotFound()
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun NotFound(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Produto não encontrado.")
    }
}
