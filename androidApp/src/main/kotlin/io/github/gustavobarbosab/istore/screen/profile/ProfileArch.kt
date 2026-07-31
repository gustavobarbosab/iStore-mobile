package io.github.gustavobarbosab.istore.screen.profile

import io.github.gustavobarbosab.istore.common.MviDelegateImpl

class ProfileMvi : MviDelegateImpl<ProfileUiState, ProfileSideEffect>(
    initialState = ProfileUiState(
        name = "Gustavo Barbosa",
        email = "gustavobarbosabarreto@gmail.com",
    )
)

data class ProfileUiState(
    val name: String,
    val email: String,
)

sealed class ProfileSideEffect
