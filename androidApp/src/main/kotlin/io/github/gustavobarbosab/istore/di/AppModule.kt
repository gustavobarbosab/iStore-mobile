package io.github.gustavobarbosab.istore.di

import org.koin.dsl.module

/**
 * Dependências transversais do app (ex: cliente HTTP pro API Gateway,
 * dispatchers, etc.). Vazio por enquanto — só esqueleto de telas e navegação.
 */
object AppModule {
    val module = module {
        // TODO: prover aqui o client do BFF/API Gateway quando a integração real existir.
    }
}
