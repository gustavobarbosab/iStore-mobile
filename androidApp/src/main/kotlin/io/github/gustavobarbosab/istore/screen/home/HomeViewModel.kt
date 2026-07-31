package io.github.gustavobarbosab.istore.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gustavobarbosab.istore.common.MviDelegate
import io.github.gustavobarbosab.istore.common.MviEventHandler
import io.github.gustavobarbosab.istore.data.mock.MockCatalog
import io.github.gustavobarbosab.istore.screen.home.model.ProductUiModel
import kotlinx.coroutines.launch

class HomeViewModel(
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
        // TODO: substituir por GET /produtos no BFF (via API Gateway).
        val products = MockCatalog.products.map {
            ProductUiModel(
                id = it.id,
                name = it.name,
                description = it.description,
                priceLabel = "R$ %.2f".format(it.price),
                emoji = it.emoji,
            )
        }
        onState(HomeUiState.Ready(products))
    }
}
