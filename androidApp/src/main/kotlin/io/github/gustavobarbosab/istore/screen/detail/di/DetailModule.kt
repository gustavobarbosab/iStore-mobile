package io.github.gustavobarbosab.istore.screen.detail.di

import io.github.gustavobarbosab.istore.screen.detail.DetailMvi
import io.github.gustavobarbosab.istore.screen.detail.DetailViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object DetailModule {
    val module = module {
        factoryOf(::DetailMvi)
        // productId chega via parametersOf() no koinViewModel() da DetailScreen.
        viewModelOf(::DetailViewModel)
    }
}
