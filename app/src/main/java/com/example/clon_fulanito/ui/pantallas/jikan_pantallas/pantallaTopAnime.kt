package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.clon_fulanito.modelos.jikan_modelos.Anime
import com.example.clon_fulanito.modelos.jikan_modelos.Images
import com.example.clon_fulanito.vista_modelos.jikan.classAnimeViewModel

@Composable
fun pantallaTopAnime(
    navController: NavController,
    viewModel: classAnimeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val animeLista = viewModel.listaAnime.collectAsState().value
    val cargando = viewModel.estaCargando.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        if (cargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(animeLista) { anime ->
                    objetoAnime(anime = anime, navController = navController)
                }
            }
        }
    }
}

@Composable
fun objetoAnime(anime: Anime, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(painter = rememberAsyncImagePainter(model = anime.images.jpg.image_url), contentDescription = anime.title,
                modifier = Modifier.width(100.dp).fillMaxHeight() )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight().weight(1f).padding(end = 8.dp)) {
                Text(
                    text = anime.title, style = MaterialTheme.typography.titleMedium,
                    maxLines = 2, overflow = TextOverflow.Ellipsis
                )
                anime.score?.let { score ->
                    Text(
                        text = "Score: $score", style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}