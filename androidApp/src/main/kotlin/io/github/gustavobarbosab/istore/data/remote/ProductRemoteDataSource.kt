package io.github.gustavobarbosab.istore.data.remote

import io.github.gustavobarbosab.istore.domain.model.Product
import kotlinx.coroutines.delay

/**
 * Simula o client HTTP que bateria no BFF (via API Gateway) em GET /produtos
 * e GET /produtos/{id}. TODO: trocar por uma implementação Ktor real quando
 * o backend existir.
 */
class ProductRemoteDataSource {

    suspend fun fetchProducts(): List<Product> {
        delay(600)
        return FAKE_SERVER_PRODUCTS
    }

    companion object {
        private val FAKE_SERVER_PRODUCTS = listOf(
            Product(
                id = "p1",
                name = "Fone Bluetooth",
                description = "Fone sem fio com cancelamento de ruído ativo e 30h de bateria.",
                price = 249.90,
                emoji = "🎧",
            ),
            Product(
                id = "p2",
                name = "Smartwatch",
                description = "Relógio inteligente com monitor cardíaco e GPS integrado.",
                price = 599.00,
                emoji = "⌚",
            ),
            Product(
                id = "p3",
                name = "Teclado Mecânico",
                description = "Teclado mecânico compacto, switches hot-swap e RGB.",
                price = 349.50,
                emoji = "⌨️",
            ),
            Product(
                id = "p4",
                name = "Mouse Gamer",
                description = "Mouse sem fio de alta precisão, 26000 DPI.",
                price = 189.90,
                emoji = "🖱️",
            ),
        )
    }
}
