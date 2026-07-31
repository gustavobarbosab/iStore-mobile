package io.github.gustavobarbosab.istore.domain.repository

import io.github.gustavobarbosab.istore.domain.model.Payment
import io.github.gustavobarbosab.istore.domain.model.Product

interface PaymentRepository {
    /**
     * Envia o pedido de pagamento (equivalente ao POST /checkout no BFF via
     * API Gateway). Retorna assim que o pagamento é aceito para processamento
     * — não espera o resultado final (aprovado/recusado).
     */
    suspend fun checkout(product: Product): Payment
}
