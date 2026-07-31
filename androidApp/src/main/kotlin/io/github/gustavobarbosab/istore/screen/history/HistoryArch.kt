package io.github.gustavobarbosab.istore.screen.history

import io.github.gustavobarbosab.istore.common.MviDelegateImpl
import io.github.gustavobarbosab.istore.screen.history.model.OrderUiModel

class HistoryMvi : MviDelegateImpl<HistoryUiState, HistorySideEffect>(
    initialState = HistoryUiState.Loading
)

sealed class HistoryUiState {
    data object Loading : HistoryUiState()
    data class Ready(val orders: List<OrderUiModel>) : HistoryUiState()
    data object Empty : HistoryUiState()
}

sealed class HistorySideEffect

sealed class HistoryEvent {
    data object OnRefresh : HistoryEvent()
}
