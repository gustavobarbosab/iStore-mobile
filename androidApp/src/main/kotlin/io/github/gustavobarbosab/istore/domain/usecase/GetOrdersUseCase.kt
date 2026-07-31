package io.github.gustavobarbosab.istore.domain.usecase

import io.github.gustavobarbosab.istore.domain.model.Order
import io.github.gustavobarbosab.istore.domain.repository.OrderRepository

class GetOrdersUseCase(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(): List<Order> = orderRepository.getOrders()
}
