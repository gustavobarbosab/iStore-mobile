package io.github.gustavobarbosab.istore.data.di

import io.github.gustavobarbosab.istore.data.local.OrderLocalDataSource
import io.github.gustavobarbosab.istore.data.local.ProductLocalDataSource
import io.github.gustavobarbosab.istore.data.remote.PaymentRemoteDataSource
import io.github.gustavobarbosab.istore.data.remote.ProductRemoteDataSource
import io.github.gustavobarbosab.istore.data.repository.OrderRepositoryImpl
import io.github.gustavobarbosab.istore.data.repository.PaymentRepositoryImpl
import io.github.gustavobarbosab.istore.data.repository.ProductRepositoryImpl
import io.github.gustavobarbosab.istore.domain.repository.OrderRepository
import io.github.gustavobarbosab.istore.domain.repository.PaymentRepository
import io.github.gustavobarbosab.istore.domain.repository.ProductRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object DataModule {
    val module = module {
        // Data sources locais são singletons: o cache em memória precisa
        // sobreviver entre telas (senão "esquece" a cada navegação).
        singleOf(::ProductLocalDataSource)
        singleOf(::OrderLocalDataSource)

        // Data sources remotas não guardam estado, então podem ser factory.
        factoryOf(::ProductRemoteDataSource)
        factoryOf(::PaymentRemoteDataSource)

        singleOf(::ProductRepositoryImpl) bind ProductRepository::class
        singleOf(::OrderRepositoryImpl) bind OrderRepository::class
        singleOf(::PaymentRepositoryImpl) bind PaymentRepository::class
    }
}
