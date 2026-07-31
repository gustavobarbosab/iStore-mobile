package io.github.gustavobarbosab.istore.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutScreenContent(
    state: CheckoutUiState,
    onEvent: (CheckoutEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TextButton(onClick = onBack, modifier = Modifier.padding(8.dp)) {
            Text("← Voltar")
        }
        Text(
            text = "Confirmar Pedido",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        when (state) {
            CheckoutUiState.Loading -> Loading()
            is CheckoutUiState.Ready -> OrderSummary(state = state, onEvent = onEvent)
            CheckoutUiState.Confirming -> Confirming()
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
private fun Confirming(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CircularProgressIndicator()
            Text("Enviando pagamento...")
        }
    }
}

@Composable
private fun OrderSummary(
    state: CheckoutUiState.Ready,
    onEvent: (CheckoutEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(text = "Resumo do pedido", style = MaterialTheme.typography.titleMedium)
                Text(text = state.productName, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Total: ${state.priceLabel}", style = MaterialTheme.typography.titleMedium)
            }
        }
        Button(
            onClick = { onEvent(CheckoutEvent.OnConfirmClicked) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Confirmar pagamento")
        }
    }
}
