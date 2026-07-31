package io.github.gustavobarbosab.istore.screen.history.di

import io.github.gustavobarbosab.istore.screen.history.HistoryMvi
import io.github.gustavobarbosab.istore.screen.history.HistoryViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object HistoryModule {
    val module = module {
        factoryOf(::HistoryMvi)
        viewModelOf(::HistoryViewModel)
    }
}
