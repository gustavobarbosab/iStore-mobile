package io.github.gustavobarbosab.istore.domain.usecase

import io.github.gustavobarbosab.istore.domain.model.Order
import io.github.gustavobarbosab.istore.domain.model.OrderStatus
import io.github.gustavobarbosab.istore.domain.model.Payment
import io.github.gustavobarbosab.istore.domain.repository.OrderRepository
import io.github.gustavobarbosab.istore.domain.repository.PaymentRepository
import io.github.gustavobarbosab.istore.domain.repository.ProductRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Confirma o checkout de um produto.
 *
 * Espelha o fluxo real desenhado na arquitetura: envia o pagamento (BFF via
 * API Gateway), recebe um "202 Accepted" com o paymentId, e já cria o pedido
 * localmente como PROCESSING — sem esperar o resultado final. O resultado
 * (aprovado/recusado) só aparece depois, quando o usuário abre Meus Pedidos.
 */
class CheckoutUseCase(
    private val productRepository: ProductRepository,
    private val paymentRepository: PaymentRepository,
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(productId: String): Payment {
        val product = requireNotNull(productRepository.getProductById(productId)) {
            "Produto $productId não encontrado"
        }

        val payment = paymentRepository.checkout(product)

        orderRepository.addOrder(
            Order(
                id = payment.paymentId,
                productId = product.id,
                productName = product.name,
                date = today(),
                price = product.price,
                status = OrderStatus.PROCESSING,
            )
        )

        return payment
    }

    private fun today(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date())
}
