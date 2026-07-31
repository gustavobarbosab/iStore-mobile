package io.github.gustavobarbosab.istore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.gustavobarbosab.istore.koin.IStoreKoinApplication
import io.github.gustavobarbosab.istore.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            IStoreApp()
        }
    }
}

@Composable
private fun IStoreApp() {
    IStoreKoinApplication {
        MaterialTheme {
            AppNavigation()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    IStoreApp()
}