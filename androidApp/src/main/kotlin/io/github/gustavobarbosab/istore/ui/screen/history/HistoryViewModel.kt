package io.github.gustavobarbosab.istore.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gustavobarbosab.istore.common.MviDelegate
import io.github.gustavobarbosab.istore.common.MviEventHandler
import io.github.gustavobarbosab.istore.domain.usecase.GetOrdersUseCase
import io.github.gustavobarbosab.istore.ui.screen.history.mapper.OrderUiModelMapper
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val orderUiModelMapper: OrderUiModelMapper,
    private val mvi: HistoryMvi,
) : ViewModel(),
    MviDelegate<HistoryUiState, HistorySideEffect> by mvi,
    MviEventHandler<HistoryEvent> {

    init {
        loadOrders()
    }

    override fun onEvent(event: HistoryEvent) {
        when (event) {
            HistoryEvent.OnRefresh -> loadOrders()
        }
    }

    private fun loadOrders() {
        // É essa consulta, feita quando o usuário abre esta tela, que resolve
        // o status final do pagamento — sem polling nem WebSocket.
        viewModelScope.launch {
            val orders = orderUiModelMapper.map(getOrdersUseCase())
            onState(if (orders.isEmpty()) HistoryUiState.Empty else HistoryUiState.Ready(orders))
        }
    }
}
