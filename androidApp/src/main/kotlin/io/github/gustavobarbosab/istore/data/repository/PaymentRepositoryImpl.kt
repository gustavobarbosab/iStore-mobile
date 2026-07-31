package io.github.gustavobarbosab.istore.data.repository

import io.github.gustavobarbosab.istore.data.local.OrderLocalDataSource
import io.github.gustavobarbosab.istore.data.remote.PaymentRemoteDataSource
import io.github.gustavobarbosab.istore.domain.model.Payment
import io.github.gustavobarbosab.istore.domain.model.Product
import io.github.gustavobarbosab.istore.domain.repository.PaymentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PaymentRepositoryImpl(
    private val remoteDataSource: PaymentRemoteDataSource,
    private val orderLocalDataSource: OrderLocalDataSource,
) : PaymentRepository {

    // Escopo próprio (não atrelado a nenhuma tela): o "worker" precisa continuar
    // rodando em background mesmo se o usuário sair da tela de Confirmation,
    // já que ele não fica esperando resposta — só atualiza o cache depois.
    private val workerScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override suspend fun checkout(product: Product): Payment {
        val payment = remoteDataSource.submitCheckout(product)
        simulateWorkerProcessing(paymentId = payment.paymentId)
        return payment
    }

    private fun simulateWorkerProcessing(paymentId: String) {
        workerScope.launch {
            val status = remoteDataSource.awaitWorkerResult()
            orderLocalDataSource.updateStatus(paymentId, status)
        }
    }
}
