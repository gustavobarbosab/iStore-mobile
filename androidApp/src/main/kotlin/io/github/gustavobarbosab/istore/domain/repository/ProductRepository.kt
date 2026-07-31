package io.github.gustavobarbosab.istore.domain.repository

import io.github.gustavobarbosab.istore.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
}
