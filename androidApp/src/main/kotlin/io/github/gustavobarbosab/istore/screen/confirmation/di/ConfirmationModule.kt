package io.github.gustavobarbosab.istore.screen.confirmation.di

import io.github.gustavobarbosab.istore.screen.confirmation.ConfirmationMvi
import io.github.gustavobarbosab.istore.screen.confirmation.ConfirmationViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object ConfirmationModule {
    val module = module {
        factoryOf(::ConfirmationMvi)
        // paymentId chega via parametersOf() no koinViewModel() da ConfirmationScreen.
        viewModelOf(::ConfirmationViewModel)
    }
}
