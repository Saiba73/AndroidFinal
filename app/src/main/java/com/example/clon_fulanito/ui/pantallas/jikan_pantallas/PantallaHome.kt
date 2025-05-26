package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clon_fulanito.vista_modelos.jikan.PrincipalViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.example.clon_fulanito.modelos.jikan_modelos.Anime
import com.example.clon_fulanito.vista_modelos.jikan.TopAnimeViewModel


@Composable
fun PantallaHome(viewModel: PrincipalViewModel = viewModel(), viewModelTop: TopAnimeViewModel = viewModel()) {
    //Aqui se guardan los valores que obtuvo el viewmodel de la api.
    //Cada vez que haiga un vambio jetpack compose actualizara la interfaz
    val animes by viewModel.temporadaActual.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    val animesTop by viewModelTop.listaAnime.collectAsState()
    val cargandoTop by viewModelTop.estaCargando.collectAsState()
    val errorTop by viewModelTop.error.collectAsState()


    //Se crea una columna que abarca toda la pantalla
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(30.dp))

        //Aqui se muestan los animes de TopAnime
        Text(text = "Mejores Animes", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
        //Si esta cargando muestra el circulo de carga
        if (cargandoTop) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        //Si el mensaje de error no es nulo entonses muesta el mensaje de error
        else if (errorTop != null) {
            Text(text = errorTop ?: "", color = MaterialTheme.colorScheme.error)
        }
        //Cuando termina de caragar crea un cuadro donde se muestra la informacion del anime
        //dentro de un LazyRow. Usa la funcion objetoAnimeHorizontal
        else {
            LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 8.dp)) {
                items(animesTop) { anime ->
                    objetoAnimeHorizontal(anime = anime)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        //Aqui se muestan los animes de la temporada actual
        Text(text = "Animes de temporada", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
        //Si esta cargando muestra el circulo de carga
        if (cargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        //Si el mensaje de error no es nulo entonses muesta el mensaje de error
        else if (error != null) {
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
        //Cuando termina de caragar crea un cuadro donde se muestra la informacion del anime
        //dentro de un LazyRow. Usa la funcion objetoAnimeHorizontal
        else {
            LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 8.dp)
            ) { items(animes) { anime ->
                    objetoAnimeHorizontal(anime = anime)
                }
            }
        }
    }
}


//Esta funcion crea un cuadro para mostrar los animes de manera horizontal
@Composable
fun objetoAnimeHorizontal(anime: Anime) {
    Card(modifier = Modifier.width(100.dp).height(225.dp), shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column  {
            Image(painter = rememberAsyncImagePainter(anime.images.jpg.image_url), contentDescription = anime.title, modifier = Modifier.height(150.dp).padding(start = 5.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = anime.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                anime.score?.let { score ->
                    Text(text = "Score: $score", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}