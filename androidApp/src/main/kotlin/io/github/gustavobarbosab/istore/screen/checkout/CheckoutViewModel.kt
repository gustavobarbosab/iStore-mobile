package io.github.gustavobarbosab.istore.screen.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gustavobarbosab.istore.common.MviDelegate
import io.github.gustavobarbosab.istore.common.MviEventHandler
import io.github.gustavobarbosab.istore.data.mock.MockCatalog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class CheckoutViewModel(
    private val productId: String,
    private val mvi: CheckoutMvi,
) : ViewModel(),
    MviDelegate<CheckoutUiState, CheckoutSideEffect> by mvi,
    MviEventHandler<CheckoutEvent> {

    init {
        loadOrderSummary()
    }

    override fun onEvent(event: CheckoutEvent) {
        when (event) {
            CheckoutEvent.OnConfirmClicked -> confirmPayment()
        }
    }

    private fun loadOrderSummary() {
        val product = MockCatalog.findById(productId)
        if (product != null) {
            onState(
                CheckoutUiState.Ready(
                    productName = product.name,
                    priceLabel = "R$ %.2f".format(product.price),
                )
            )
        }
    }

    private fun confirmPayment() {
        viewModelScope.launch {
            onState(CheckoutUiState.Confirming)

            // TODO: substituir por POST /checkout no BFF (via API Gateway).
            // Resposta real é 202 Accepted com um paymentId; o worker processa
            // assincronamente e o resultado só aparece depois em Meus Pedidos.
            delay(800)
            val paymentId = "pay_${Random.nextInt(100_000, 999_999)}"

            onSideEffect(CheckoutSideEffect.NavigateToConfirmation(paymentId))
        }
    }
}
