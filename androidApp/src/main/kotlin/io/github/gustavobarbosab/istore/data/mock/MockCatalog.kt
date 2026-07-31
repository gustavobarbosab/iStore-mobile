package io.github.gustavobarbosab.istore.data.mock

/**
 * Dados fake usados só para dar vida ao esqueleto de telas.
 * TODO: substituir por chamadas reais ao BFF (via API Gateway) quando o
 * backend descrito na arquitetura estiver implementado.
 */
data class MockProduct(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val emoji: String,
)

object MockCatalog {
    val products = listOf(
        MockProduct(
            id = "p1",
            name = "Fone Bluetooth",
            description = "Fone sem fio com cancelamento de ruído ativo e 30h de bateria.",
            price = 249.90,
            emoji = "🎧",
        ),
        MockProduct(
            id = "p2",
            name = "Smartwatch",
            description = "Relógio inteligente com monitor cardíaco e GPS integrado.",
            price = 599.00,
            emoji = "⌚",
        ),
        MockProduct(
            id = "p3",
            name = "Teclado Mecânico",
            description = "Teclado mecânico compacto, switches hot-swap e RGB.",
            price = 349.50,
            emoji = "⌨️",
        ),
        MockProduct(
            id = "p4",
            name = "Mouse Gamer",
            description = "Mouse sem fio de alta precisão, 26000 DPI.",
            price = 189.90,
            emoji = "🖱️",
        ),
    )

    fun findById(id: String): MockProduct? = products.firstOrNull { it.id == id }
}

enum class MockOrderStatus { APPROVED, PROCESSING, DECLINED }

data class MockOrder(
    val id: String,
    val productName: String,
    val date: String,
    val priceLabel: String,
    val status: MockOrderStatus,
)

object MockOrders {
    val all = listOf(
        MockOrder(
            id = "o1",
            productName = "Fone Bluetooth",
            date = "28/07/2026",
            priceLabel = "R$ 249,90",
            status = MockOrderStatus.APPROVED,
        ),
        MockOrder(
            id = "o2",
            productName = "Smartwatch",
            date = "30/07/2026",
            priceLabel = "R$ 599,00",
            status = MockOrderStatus.PROCESSING,
        ),
        MockOrder(
            id = "o3",
            productName = "Mouse Gamer",
            date = "15/07/2026",
            priceLabel = "R$ 189,90",
            status = MockOrderStatus.DECLINED,
        ),
    )
}
