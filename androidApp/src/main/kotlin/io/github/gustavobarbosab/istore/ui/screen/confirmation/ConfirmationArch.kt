package io.github.gustavobarbosab.istore.ui.screen.confirmation

import io.github.gustavobarbosab.istore.common.MviDelegateImpl

class ConfirmationMvi : MviDelegateImpl<ConfirmationUiState, ConfirmationSideEffect>(
    initialState = ConfirmationUiState.Processing(paymentId = "")
)

sealed class ConfirmationUiState {
    /**
     * Único estado desta tela: sem polling nem WebSocket, o app só informa que
     * o pagamento está sendo processado. O resultado final (aprovado/recusado)
     * aparece depois na tela Meus Pedidos.
     */
    data class Processing(val paymentId: String) : ConfirmationUiState()
}

sealed class ConfirmationSideEffect {
    data object NavigateToHome : ConfirmationSideEffect()
    data object NavigateToHistory : ConfirmationSideEffect()
}

sealed class ConfirmationEvent {
    data object OnViewOrdersClicked : ConfirmationEvent()
    data object OnBackToHomeClicked : ConfirmationEvent()
}
