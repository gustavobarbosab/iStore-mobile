package io.github.gustavobarbosab.istore.ui.screen.checkout

import androidx.compose.foundation.layout.Arrangement
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
import io.github.gustavobarbosab.istore.ui.screen.checkout.component.OrderSummary

@Composable
fun CheckoutScreenContent(
    state: CheckoutUiState,
    onEvent: (CheckoutEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        IconButton(onClick = onBack, modifier = Modifier.padding(4.dp)) {
            Text("←", style = MaterialTheme.typography.titleLarge)
        }
        Text(
            text = "Confirmar Pedido",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        when (state) {
            CheckoutUiState.Loading -> Loading()
            is CheckoutUiState.Ready -> OrderSummary(
                summary = state.summary,
                onConfirmClick = { onEvent(CheckoutEvent.OnConfirmClicked) },
            )
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
