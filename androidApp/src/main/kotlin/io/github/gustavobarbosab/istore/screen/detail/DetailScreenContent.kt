package io.github.gustavobarbosab.istore.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.gustavobarbosab.istore.screen.detail.model.ProductDetailUiModel

@Composable
fun DetailScreenContent(
    state: DetailUiState,
    onEvent: (DetailEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TextButton(onClick = onBack, modifier = Modifier.padding(8.dp)) {
            Text("← Voltar")
        }
        when (state) {
            DetailUiState.Loading -> Loading()
            is DetailUiState.Ready -> ProductDetail(product = state.product, onEvent = onEvent)
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

@Composable
private fun ProductDetail(
    product: ProductDetailUiModel,
    onEvent: (DetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = product.emoji, fontSize = 96.sp)
        Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
        Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = product.priceLabel, style = MaterialTheme.typography.titleLarge)
        Button(
            onClick = { onEvent(DetailEvent.OnBuyClicked) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Comprar")
        }
    }
}
