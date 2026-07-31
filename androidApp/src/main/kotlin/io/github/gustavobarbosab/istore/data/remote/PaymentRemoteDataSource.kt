package io.github.gustavobarbosab.istore.data.remote

import io.github.gustavobarbosab.istore.domain.model.OrderStatus
import io.github.gustavobarbosab.istore.domain.model.Payment
import io.github.gustavobarbosab.istore.domain.model.Product
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Simula o client HTTP que bateria no BFF/API Gateway em POST /checkout.
 * TODO: trocar por uma implementação Ktor real quando o backend existir.
 */
class PaymentRemoteDataSource {

    /** Equivalente ao 202 Accepted: aceita o pedido, não espera o resultado. */
    suspend fun submitCheckout(product: Product): Payment {
        delay(800)
        return Payment(
            paymentId = "pay_${Random.nextInt(100_000, 999_999)}",
            productId = product.id,
        )
    }

    /**
     * Não existe num client real — aqui simula o Worker consumindo a fila e
     * processando o pagamento em background, só pra dar um resultado final
     * pra Meus Pedidos sem precisar de um backend de verdade. Um app real
     * nunca "pergunta" isso; ele só lê o status já resolvido depois.
     */
    suspend fun awaitWorkerResult(): OrderStatus {
        delay(5_000)
        return if (Random.nextInt(100) < 80) OrderStatus.APPROVED else OrderStatus.DECLINED
    }
}
