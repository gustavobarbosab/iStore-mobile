package io.github.gustavobarbosab.istore.domain.model

/** Resultado do POST /checkout no BFF: um "202 Accepted" com o id do pagamento. */
data class Payment(
    val paymentId: String,
    val productId: String,
)
