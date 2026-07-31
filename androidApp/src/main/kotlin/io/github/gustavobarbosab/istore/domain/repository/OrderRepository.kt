package io.github.gustavobarbosab.istore.domain.repository

import io.github.gustavobarbosab.istore.domain.model.Order

interface OrderRepository {
    suspend fun getOrders(): List<Order>
    suspend fun addOrder(order: Order)
}
