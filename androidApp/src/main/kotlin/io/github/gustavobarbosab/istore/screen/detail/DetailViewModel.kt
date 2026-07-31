package io.github.gustavobarbosab.istore.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.gustavobarbosab.istore.common.MviDelegate
import io.github.gustavobarbosab.istore.common.MviEventHandler
import io.github.gustavobarbosab.istore.data.mock.MockCatalog
import io.github.gustavobarbosab.istore.screen.detail.model.ProductDetailUiModel
import kotlinx.coroutines.launch

class DetailViewModel(
    private val productId: String,
    private val mvi: DetailMvi,
) : ViewModel(),
    MviDelegate<DetailUiState, DetailSideEffect> by mvi,
    MviEventHandler<DetailEvent> {

    init {
        loadProduct()
    }

    override fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.OnBuyClicked -> viewModelScope.launch {
                onSideEffect(DetailSideEffect.NavigateToCheckout(productId))
            }
        }
    }

    private fun loadProduct() {
        // TODO: substituir por GET /produtos/{id} no BFF (via API Gateway).
        val product = MockCatalog.findById(productId)
        onState(
            if (product == null) {
                DetailUiState.NotFound
            } else {
                DetailUiState.Ready(
                    ProductDetailUiModel(
                        id = product.id,
                        name = product.name,
                        description = product.description,
                        priceLabel = "R$ %.2f".format(product.price),
                        emoji = product.emoji,
                    )
                )
            }
        )
    }
}
