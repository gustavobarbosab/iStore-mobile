package io.github.gustavobarbosab.istore.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gustavobarbosab.istore.common.MviDelegate
import io.github.gustavobarbosab.istore.common.MviEventHandler
import io.github.gustavobarbosab.istore.domain.usecase.GetProductsUseCase
import io.github.gustavobarbosab.istore.ui.screen.home.mapper.ProductUiModelMapper
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val productUiModelMapper: ProductUiModelMapper,
    private val mvi: HomeMvi,
) : ViewModel(),
    MviDelegate<HomeUiState, HomeSideEffect> by mvi,
    MviEventHandler<HomeEvent> {

    init {
        loadProducts()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProductClicked -> viewModelScope.launch {
                onSideEffect(HomeSideEffect.NavigateToDetail(event.productId))
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val products = productUiModelMapper.map(getProductsUseCase())
            onState(HomeUiState.Ready(products))
        }
    }
}
