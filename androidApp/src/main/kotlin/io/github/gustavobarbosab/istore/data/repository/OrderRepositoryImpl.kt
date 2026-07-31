package io.github.gustavobarbosab.istore.data.repository

import io.github.gustavobarbosab.istore.data.local.OrderLocalDataSource
import io.github.gustavobarbosab.istore.domain.model.Order
import io.github.gustavobarbosab.istore.domain.repository.OrderRepository

class OrderRepositoryImpl(
    private val localDataSource: OrderLocalDataSource,
) : OrderRepository {

    override suspend fun getOrders(): List<Order> = localDataSource.getAll()

    override suspend fun addOrder(order: Order) = localDataSource.add(order)
}
