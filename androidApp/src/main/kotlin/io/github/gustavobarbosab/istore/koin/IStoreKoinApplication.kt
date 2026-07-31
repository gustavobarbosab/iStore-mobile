package io.github.gustavobarbosab.istore.koin

import androidx.compose.runtime.Composable
import io.github.gustavobarbosab.istore.di.AppModule
import io.github.gustavobarbosab.istore.screen.checkout.di.CheckoutModule
import io.github.gustavobarbosab.istore.screen.confirmation.di.ConfirmationModule
import io.github.gustavobarbosab.istore.screen.detail.di.DetailModule
import io.github.gustavobarbosab.istore.screen.history.di.HistoryModule
import io.github.gustavobarbosab.istore.screen.home.di.HomeModule
import io.github.gustavobarbosab.istore.screen.profile.di.ProfileModule
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration
import org.koin.mp.KoinPlatformTools

fun isKoinStarted() = KoinPlatformTools.defaultContext().getOrNull() != null

@OptIn(KoinExperimentalAPI::class)
@Composable
fun IStoreKoinApplication(
    appContent: @Composable () -> Unit,
) {
    if (isKoinStarted()) {
        appContent()
        return
    }

    KoinMultiplatformApplication(
        config = koinConfiguration {
            modules(
                listOf(
                    AppModule.module,
                    HomeModule.module,
                    HistoryModule.module,
                    ProfileModule.module,
                    DetailModule.module,
                    CheckoutModule.module,
                    ConfirmationModule.module,
                )
            )
        }
    ) {
        appContent()
    }
}
