package io.github.gustavobarbosab.istore.domain.usecase

import io.github.gustavobarbosab.istore.domain.model.Product
import io.github.gustavobarbosab.istore.domain.repository.ProductRepository

class GetProductByIdUseCase(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(productId: String): Product? =
        productRepository.getProductById(productId)
}
