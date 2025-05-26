package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

//Esta clase representa cada pantalla que se muestra en la barra inferiro de la
//pantalla. Se guarda una ruta la etiqueta que muestra en pantalla y un icono
sealed class PantallaMenuPrincipal(val ruta: String, val etiqueta: String, val icono: ImageVector) {
    object Home :PantallaMenuPrincipal("home", "Home", Icons.Default.Home)
    object TopAnime :PantallaMenuPrincipal("topAnime", "Top Anime", Icons.Default.Star)
    object Buscar :PantallaMenuPrincipal("buscar", "Buscar", Icons.Default.Search)
    object Recomendar :PantallaMenuPrincipal("recomendar", "Recomendacion", Icons.Default.ThumbUp)
    object Usuario :PantallaMenuPrincipal("usuario", "Usuario", Icons.Default.AddCircle)
}

//Esta funcion devuelve una lista de botones para mostrarse en la parte inferior de la pantalla
fun obtenerBotnesInferiores() = listOf(
    PantallaMenuPrincipal.Home,
    PantallaMenuPrincipal.TopAnime,
    PantallaMenuPrincipal.Buscar,
    PantallaMenuPrincipal.Recomendar,
    PantallaMenuPrincipal.Usuario
)

@Composable
fun MenuPrincipal(modifier: Modifier = Modifier){
    //Esta variable guarda la pantalla actual
    var pantallaActual by remember {mutableStateOf(0)}
    //El navcontroller para controlar la navegacion
    var controlNavegacion = rememberNavController()

    //Este es el Scaffold que uso para la barra inferior
    Scaffold (modifier = modifier, bottomBar = {
            NavigationBar {
                obtenerBotnesInferiores().forEachIndexed { indice, pantalla ->
                    NavigationBarItem(
                        selected = indice == pantallaActual,
                        onClick = {
                            pantallaActual = indice
                            controlNavegacion.navigate(pantalla.ruta) {
                                //Evita la duplicacion de otras pantallas
                                popUpTo(controlNavegacion.graph.startDestinationId){
                                    saveState = true
                                }
                                //Evita que se abra la misma pantalla 2 veces
                                launchSingleTop = true
                                //Guarda el estado de una pantalla ya visitada
                                restoreState = true
                            }
                        },
                        //Muestra el icono y la etiqueta de la pantalla
                        icon = { Icon(pantalla.icono, contentDescription = pantalla.etiqueta) },
                        label = { Text(pantalla.etiqueta,style = MaterialTheme.typography.bodySmall) }
                    )
                }
            }
        }
    ){ innerPadding ->
        //El navhost establece que pantalla pertenece a cual ruta.
        NavHost(navController = controlNavegacion, startDestination = PantallaMenuPrincipal.Home.ruta, modifier = Modifier.padding(innerPadding)){
            composable (PantallaMenuPrincipal.Home.ruta) {
                PantallaHome()
            }

            composable(PantallaMenuPrincipal.TopAnime.ruta) {
                pantallaTopAnime()
            }

            composable (PantallaMenuPrincipal.Buscar.ruta) {
                PantallaBuscar()
            }

            composable (PantallaMenuPrincipal.Recomendar.ruta){
                PantallaRecomendar()
            }

            composable (PantallaMenuPrincipal.Usuario.ruta){
                PantallaUsuarioFalso()
            }
        }
    }
}

//Esta funcion es la que uso para mostrar la app en el MainActivity
@Preview(showBackground = true)
@Composable
fun VistaPreviaMenu() {
    MenuPrincipal(Modifier.fillMaxSize())
}