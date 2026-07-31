package io.github.gustavobarbosab.istore.screen.checkout.di

import io.github.gustavobarbosab.istore.screen.checkout.CheckoutMvi
import io.github.gustavobarbosab.istore.screen.checkout.CheckoutViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object CheckoutModule {
    val module = module {
        factoryOf(::CheckoutMvi)
        // productId chega via parametersOf() no koinViewModel() da CheckoutScreen.
        viewModelOf(::CheckoutViewModel)
    }
}
