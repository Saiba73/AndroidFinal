package com.example.clon_fulanito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.clon_fulanito.ui.pantallas.jikan_pantallas.MenuPrincipal
import com.example.clon_fulanito.ui.theme.Clon_fulanitoTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Clon_fulanitoTheme {
                MenuPrincipal(modifier = Modifier.fillMaxSize())
            }
        }
    }
}