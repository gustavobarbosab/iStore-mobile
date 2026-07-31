package io.github.gustavobarbosab.istore.screen.history

import androidx.lifecycle.ViewModel
import io.github.gustavobarbosab.istore.common.MviDelegate
import io.github.gustavobarbosab.istore.common.MviEventHandler
import io.github.gustavobarbosab.istore.data.mock.MockOrderStatus
import io.github.gustavobarbosab.istore.data.mock.MockOrders
import io.github.gustavobarbosab.istore.screen.history.model.OrderStatusUiModel
import io.github.gustavobarbosab.istore.screen.history.model.OrderUiModel

class HistoryViewModel(
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
        // TODO: substituir por GET /pedidos no BFF (via API Gateway).
        // É essa consulta, feita quando o usuário abre esta tela, que resolve
        // o status final do pagamento — sem polling nem WebSocket.
        val orders = MockOrders.all.map {
            OrderUiModel(
                id = it.id,
                productName = it.productName,
                date = it.date,
                priceLabel = it.priceLabel,
                status = when (it.status) {
                    MockOrderStatus.APPROVED -> OrderStatusUiModel.APPROVED
                    MockOrderStatus.PROCESSING -> OrderStatusUiModel.PROCESSING
                    MockOrderStatus.DECLINED -> OrderStatusUiModel.DECLINED
                },
            )
        }
        onState(if (orders.isEmpty()) HistoryUiState.Empty else HistoryUiState.Ready(orders))
    }
}
