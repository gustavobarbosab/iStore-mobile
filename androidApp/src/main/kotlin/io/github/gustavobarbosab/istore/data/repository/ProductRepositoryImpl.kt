package io.github.gustavobarbosab.istore.data.repository

import io.github.gustavobarbosab.istore.data.local.ProductLocalDataSource
import io.github.gustavobarbosab.istore.data.remote.ProductRemoteDataSource
import io.github.gustavobarbosab.istore.domain.model.Product
import io.github.gustavobarbosab.istore.domain.repository.ProductRepository

/**
 * Estratégia de cache simples: cache-first. Se já tiver produtos em memória,
 * usa o cache; senão busca no "remoto" (mock) e guarda pra próxima chamada.
 */
class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        localDataSource.getCachedProducts()?.let { return it }

        val products = remoteDataSource.fetchProducts()
        localDataSource.cacheProducts(products)
        return products
    }

    override suspend fun getProductById(id: String): Product? {
        localDataSource.getCachedProduct(id)?.let { return it }

        // Cache ainda vazio (ex: usuário abriu o Detail direto, sem passar pela
        // Home primeiro) — popula o cache buscando a lista completa.
        return getProducts().firstOrNull { it.id == id }
    }
}
