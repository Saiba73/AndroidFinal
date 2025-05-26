package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.clon_fulanito.modelos.jikan_modelos.Anime
import com.example.clon_fulanito.vista_modelos.jikan.TopAnimeViewModel

@Composable
fun pantallaTopAnime(viewModel: TopAnimeViewModel = viewModel()) {
    //Aqui se guardan los valores que obtuvo el viewmodel de la api.
    //Cada vez que haiga un vambio jetpack compose actualizara la interfaz
    val animeLista by viewModel.listaAnime.collectAsState()
    val cargando by viewModel.estaCargando.collectAsState()
    val error by viewModel.error.collectAsState()

    //Crea un box que abarca toda la pantalla
    Box(modifier = Modifier.fillMaxSize().background(Color.LightGray)) {

        //en caso de que el error no sea nulo se mostrara un mensaje de error
        error?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }

        //Si esta cargando muestra el circulo de carga
        if (cargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        else {
            //Crea un lazy column para mostara los animes de la lista TopAnime
            //usa la funcion objetoAnime para acomodar cada anime
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(animeLista) { anime ->
                    objetoAnime(anime = anime)
                }
            }
        }
    }
}

//Esta funcion crea un Row para guardar cada anime. Muestra una imagen, el titulo y la calificacion
//del anime.
@Composable
fun objetoAnime(anime: Anime) {
    Card(modifier = Modifier.fillMaxWidth().height(100.dp), shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(painter = rememberAsyncImagePainter(model = anime.images.jpg.image_url), contentDescription = anime.title, modifier = Modifier.width(100.dp).fillMaxHeight())
            Spacer(modifier = Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight().weight(1f).padding(end = 8.dp)) {
                Text(text = anime.title, style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
                anime.score?.let { score ->
                    Text(text = "Score: $score", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}