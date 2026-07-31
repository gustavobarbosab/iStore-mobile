package io.github.gustavobarbosab.istore.ui.screen.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gustavobarbosab.istore.common.MviDelegate
import io.github.gustavobarbosab.istore.common.MviEventHandler
import io.github.gustavobarbosab.istore.domain.usecase.CheckoutUseCase
import io.github.gustavobarbosab.istore.domain.usecase.GetProductByIdUseCase
import io.github.gustavobarbosab.istore.ui.screen.checkout.mapper.OrderSummaryUiModelMapper
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val productId: String,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val orderSummaryUiModelMapper: OrderSummaryUiModelMapper,
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
        viewModelScope.launch {
            val product = getProductByIdUseCase(productId)
            if (product != null) {
                onState(CheckoutUiState.Ready(orderSummaryUiModelMapper.map(product)))
            }
        }
    }

    private fun confirmPayment() {
        viewModelScope.launch {
            onState(CheckoutUiState.Confirming)

            // Chama o BFF via API Gateway (mock): recebe o "202 Accepted" com o
            // paymentId e já cria o pedido em Meus Pedidos como PROCESSING.
            val payment = checkoutUseCase(productId)

            onSideEffect(CheckoutSideEffect.NavigateToConfirmation(payment.paymentId))
        }
    }
}
