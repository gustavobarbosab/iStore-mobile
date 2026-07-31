package io.github.gustavobarbosab.istore.screen.checkout

import io.github.gustavobarbosab.istore.common.MviDelegateImpl

class CheckoutMvi : MviDelegateImpl<CheckoutUiState, CheckoutSideEffect>(
    initialState = CheckoutUiState.Loading
)

sealed class CheckoutUiState {
    data object Loading : CheckoutUiState()
    data class Ready(val productName: String, val priceLabel: String) : CheckoutUiState()

    /** Aguardando resposta do POST /checkout (BFF via API Gateway). */
    data object Confirming : CheckoutUiState()
}

sealed class CheckoutSideEffect {
    data class NavigateToConfirmation(val paymentId: String) : CheckoutSideEffect()
}

sealed class CheckoutEvent {
    data object OnConfirmClicked : CheckoutEvent()
}
