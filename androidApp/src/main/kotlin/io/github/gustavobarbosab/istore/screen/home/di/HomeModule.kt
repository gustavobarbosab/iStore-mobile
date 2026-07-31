package io.github.gustavobarbosab.istore.screen.home.di

import io.github.gustavobarbosab.istore.screen.home.HomeMvi
import io.github.gustavobarbosab.istore.screen.home.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object HomeModule {
    val module = module {
        factoryOf(::HomeMvi)
        viewModelOf(::HomeViewModel)
    }
}
