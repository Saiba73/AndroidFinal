package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clon_fulanito.vista_modelos.jikan.classAnimeViewModel

sealed class PantallaMenuPrincipal(val ruta: String, val etiqueta: String, val icono: ImageVector) {
    object Home :PantallaMenuPrincipal("home", "inicio", Icons.Default.Home)
    object TopAnime :PantallaMenuPrincipal("topAnime", "top anime", Icons.Default.Star)
    object Buscar :PantallaMenuPrincipal("buscar", "buscar", Icons.Default.Search)
    object Recomendar :PantallaMenuPrincipal("recomendar", "Recomendar", Icons.Default.ThumbUp)
    object Perfil :PantallaMenuPrincipal("perfil", "perfil", Icons.Default.Person)
}


fun obtenerBotnesInferiores() = listOf(
    PantallaMenuPrincipal.Home,
    PantallaMenuPrincipal.TopAnime,
    PantallaMenuPrincipal.Buscar,
    PantallaMenuPrincipal.Recomendar,
    PantallaMenuPrincipal.Perfil
)

@Composable
fun MenuPrincipal(modifier: Modifier = Modifier){
    var pantallaActual by remember {mutableStateOf(0)}
    var controlNavegacion = rememberNavController()

    val AnimeViewModel = classAnimeViewModel()

    Scaffold (
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                obtenerBotnesInferiores().forEachIndexed { indice, pantalla ->
                    NavigationBarItem(
                        selected = indice == pantallaActual,
                        onClick = {
                            pantallaActual = indice
                            controlNavegacion.navigate(pantalla.ruta) {
                                popUpTo(controlNavegacion.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(pantalla.icono, contentDescription = pantalla.etiqueta) },
                        label = { Text(pantalla.etiqueta) }
                    )
                }
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = controlNavegacion,
            startDestination = PantallaMenuPrincipal.Home.ruta,
            modifier = Modifier.padding(innerPadding)
        ){
            composable (PantallaMenuPrincipal.Home.ruta)
            {Text("Pantalla de inicio")}

            composable(PantallaMenuPrincipal.TopAnime.ruta) {
                pantallaTopAnime(navController = controlNavegacion, viewModel = AnimeViewModel)
            }

            composable (PantallaMenuPrincipal.Buscar.ruta) {
                PantallaBuscar(navController = controlNavegacion)
            }

            composable (PantallaMenuPrincipal.Recomendar.ruta){
                PantallaRecomendar()
            }

            composable (PantallaMenuPrincipal.Perfil.ruta){
                Text("Pantalla de perfil")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VistaPreviaMenu() {
    MenuPrincipal(Modifier.fillMaxSize())
}