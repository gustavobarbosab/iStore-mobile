package io.github.gustavobarbosab.istore.ui.screen.confirmation.di

import io.github.gustavobarbosab.istore.ui.screen.confirmation.ConfirmationMvi
import io.github.gustavobarbosab.istore.ui.screen.confirmation.ConfirmationViewModel
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
