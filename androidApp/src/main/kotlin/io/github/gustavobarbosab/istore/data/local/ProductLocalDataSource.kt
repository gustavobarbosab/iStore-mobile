package io.github.gustavobarbosab.istore.data.local

import io.github.gustavobarbosab.istore.domain.model.Product

/**
 * Cache em memória simples: vive enquanto o processo do app estiver vivo,
 * some quando o app é encerrado. Suficiente pro esqueleto — numa versão real
 * daria pra trocar por um cache com TTL, ou por persistência em disco.
 */
class ProductLocalDataSource {
    private var cachedProducts: List<Product>? = null

    fun getCachedProducts(): List<Product>? = cachedProducts

    fun getCachedProduct(id: String): Product? = cachedProducts?.firstOrNull { it.id == id }

    fun cacheProducts(products: List<Product>) {
        cachedProducts = products
    }
}
