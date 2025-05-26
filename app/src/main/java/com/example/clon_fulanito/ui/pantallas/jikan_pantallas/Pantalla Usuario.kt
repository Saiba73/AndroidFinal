package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

//Queria crear una pantalla que mostrar un usuario de la pagina MyAnimeList usando la api,
//pero no pude averiguar que se cargara al usuario haci que cree una pantalla de usuario
//falsa

//se crea una clase de para contener los titulos y imagen de animes
data class AnimeFake(
    val title: String,
    val imageUrl: String
)

@Composable
fun PantallaUsuarioFalso() {
    //se inicializa una lista de AnimeFake llamada animesFavoritos
    val animesFavoritos = listOf(
        AnimeFake(
            title = "Naruto",
            imageUrl = "https://sm.ign.com/ign_es/cover/n/naruto/naruto_e4bv.jpg"
        ),
        AnimeFake(
            title = "One Piece",
            imageUrl = "https://cdn.myanimelist.net/images/anime/6/73245.jpg"
        ),
        AnimeFake(
            title = "Attack on Titan",
            imageUrl = "https://cdn.myanimelist.net/images/anime/10/47347.jpg"
        ),
        AnimeFake(
            title = "My Hero Academia",
            imageUrl = "https://cdn.myanimelist.net/images/anime/10/78745.jpg"
        ),
        AnimeFake(
            title = "Fullmetal Alchemist",
            imageUrl = "https://cdn.myanimelist.net/images/anime/1223/96541.jpg"
        )
    )

    //Se crea una columna que abarca toda la pantalla
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        //Se agrega la imagen de perfil y se recorta en la figura de un circulo
        AsyncImage(model = "https://yt3.googleusercontent.com/ytc/AIdro_mpD9q4_wAnEJdAvnhzYfVrE1s9ak-Cli-QVjEsXzJv7a8=s900-c-k-c0x00ffffff-no-rj", contentDescription = "Avatar de TheAnimeMan",
            modifier = Modifier.size(100.dp).clip(CircleShape))

        //Se agrega la informacion de usuario
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "TheAnimeMan", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Animes vistos: ${120}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Animes favoritos", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        //Aquise muestran los animes que se guardaron en la lista animesFavoritos.
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 8.dp)) {
            //Se crea un nuevo cuadro por cada elemento en la lista.
            items(animesFavoritos) { anime ->
                Card(modifier = Modifier.width(120.dp).height(180.dp), shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainer)) {
                        AsyncImage(model = anime.imageUrl, contentDescription = anime.title, modifier = Modifier.fillMaxWidth().height(100.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = anime.title, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp).fillMaxWidth())
                    }
                }
            }
        }
    }
}