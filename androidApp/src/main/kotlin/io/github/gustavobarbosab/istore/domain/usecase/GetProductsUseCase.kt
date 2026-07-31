package io.github.gustavobarbosab.istore.domain.usecase

import io.github.gustavobarbosab.istore.domain.model.Product
import io.github.gustavobarbosab.istore.domain.repository.ProductRepository

class GetProductsUseCase(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(): List<Product> = productRepository.getProducts()
}
