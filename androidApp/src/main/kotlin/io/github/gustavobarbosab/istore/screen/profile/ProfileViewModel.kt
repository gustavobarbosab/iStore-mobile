package io.github.gustavobarbosab.istore.screen.profile

import androidx.lifecycle.ViewModel
import io.github.gustavobarbosab.istore.common.MviDelegate

class ProfileViewModel(
    private val mvi: ProfileMvi,
) : ViewModel(), MviDelegate<ProfileUiState, ProfileSideEffect> by mvi
