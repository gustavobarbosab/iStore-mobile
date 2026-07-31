package io.github.gustavobarbosab.istore.screen.detail

import io.github.gustavobarbosab.istore.common.MviDelegateImpl
import io.github.gustavobarbosab.istore.screen.detail.model.ProductDetailUiModel

class DetailMvi : MviDelegateImpl<DetailUiState, DetailSideEffect>(
    initialState = DetailUiState.Loading
)

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Ready(val product: ProductDetailUiModel) : DetailUiState()
    data object NotFound : DetailUiState()
}

sealed class DetailSideEffect {
    data class NavigateToCheckout(val productId: String) : DetailSideEffect()
}

sealed class DetailEvent {
    data object OnBuyClicked : DetailEvent()
}
