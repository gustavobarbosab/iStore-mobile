package io.github.gustavobarbosab.istore.navigation

import kotlinx.serialization.Serializable

/**
 * Todas as rotas do app. Navegação type-safe via Navigation-Compose (2.8+).
 *
 * Telas de nível superior (aparecem na bottom bar): Home, History, Profile.
 * Telas de fluxo (empilhadas sobre a bottom bar): Detail, Checkout, Confirmation.
 */
@Serializable
sealed interface Destination

@Serializable
data object HomeDestination : Destination

@Serializable
data object HistoryDestination : Destination

@Serializable
data object ProfileDestination : Destination

@Serializable
data class DetailDestination(val productId: String) : Destination

@Serializable
data class CheckoutDestination(val productId: String) : Destination

@Serializable
data class ConfirmationDestination(val paymentId: String) : Destination
