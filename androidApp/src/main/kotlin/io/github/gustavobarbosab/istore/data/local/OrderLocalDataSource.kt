package io.github.gustavobarbosab.istore.data.local

import io.github.gustavobarbosab.istore.domain.model.Order
import io.github.gustavobarbosab.istore.domain.model.OrderStatus

/**
 * Cache em memória dos pedidos. Como não existe um BFF de verdade ainda,
 * essa é a única "fonte de verdade" de Meus Pedidos: guarda os pedidos já
 * existentes (seed) e recebe os novos pedidos criados no checkout, além das
 * atualizações de status simuladas pelo "worker" (ver PaymentRemoteDataSource).
 *
 * Não é thread-safe de verdade (sem Mutex) — está OK pro esqueleto, já que o
 * volume de acesso concorrente aqui é mínimo. Numa implementação real, um
 * Mutex ou um MutableStateFlow protegido resolveria isso.
 */
class OrderLocalDataSource {
    private val orders = mutableListOf(
        Order(
            id = "o1",
            productId = "p1",
            productName = "Fone Bluetooth",
            date = "28/07/2026",
            price = 249.90,
            status = OrderStatus.APPROVED,
        ),
        Order(
            id = "o2",
            productId = "p2",
            productName = "Smartwatch",
            date = "30/07/2026",
            price = 599.00,
            status = OrderStatus.PROCESSING,
        ),
        Order(
            id = "o3",
            productId = "p4",
            productName = "Mouse Gamer",
            date = "15/07/2026",
            price = 189.90,
            status = OrderStatus.DECLINED,
        ),
    )

    fun getAll(): List<Order> = orders.toList()

    fun add(order: Order) {
        orders.add(0, order)
    }

    fun updateStatus(orderId: String, status: OrderStatus) {
        val index = orders.indexOfFirst { it.id == orderId }
        if (index != -1) {
            orders[index] = orders[index].copy(status = status)
        }
    }
}
