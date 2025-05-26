package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.clon_fulanito.vista_modelos.jikan.RecomendacionViewModel


@Composable
fun PantallaRecomendar(viewModel: RecomendacionViewModel = viewModel()) {
    //Aqui se guardan los valores que obtuvo el viewmodel de la api.
    //Cada vez que haiga un vambio jetpack compose actualizara la interfaz
    val cargando    by viewModel.cargando.collectAsState()
    val recomendado by viewModel.recomendado.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        //si esta cargando o si la recomendacion es nula muestra el indicador de cargar
        if (cargando) {
            CircularProgressIndicator()
        }
        else {
            //!! este operador forza a que anime no sea nulo para prevenir que la app no crashee
            val anime = recomendado!!
            //En esta columna se muestra la informacion del anime recomendado
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp))
            {
                Text("Te recomiendo:", style = MaterialTheme.typography.headlineMedium)
                Image(painter = rememberAsyncImagePainter(anime.images.jpg.image_url), contentDescription = anime.title, modifier = Modifier.fillMaxWidth().height(250.dp))
                Text(anime.title, style = MaterialTheme.typography.titleLarge)
                anime.synopsis?.let { sinopsis ->
                    Text(text = sinopsis, style = MaterialTheme.typography.bodyMedium, maxLines = 5,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                }
                //Este boton llama la funcion recomendar del viewmodel para hacer
                //una nueva recomendacion
                Button(onClick = { viewModel.recomendar() }) {
                    Text("Otra recomendación")
                }
            }
        }
    }
}