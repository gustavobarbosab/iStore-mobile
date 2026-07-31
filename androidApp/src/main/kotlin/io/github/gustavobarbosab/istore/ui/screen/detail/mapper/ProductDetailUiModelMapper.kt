package io.github.gustavobarbosab.istore.ui.screen.detail.mapper

import io.github.gustavobarbosab.istore.domain.model.Product
import io.github.gustavobarbosab.istore.ui.screen.detail.model.ProductDetailUiModel

class ProductDetailUiModelMapper {

    fun map(product: Product): ProductDetailUiModel = ProductDetailUiModel(
        id = product.id,
        name = product.name,
        description = product.description,
        priceLabel = "R$ %.2f".format(product.price),
        emoji = product.emoji,
    )
}
