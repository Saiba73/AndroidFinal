package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

import androidx.compose.foundation.background
import com.example.clon_fulanito.vista_modelos.jikan.BuscarViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun PantallaBuscar(viewModel: BuscarViewModel = viewModel()) {
    //Aqui se guardan los valores que obtuvo el viewmodel de la api.
    //Cada vez que haiga un vambio jetpack compose actualizara la interfaz
    val query by viewModel.query.collectAsState()
    val resultados by viewModel.resultados.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    //Este ayuda para controlar el teclado como para cerrarlo cuando se haga la busqueda
    val focusManager = LocalFocusManager.current

    //Se crea una columna que ocupa toda la pantalla
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        //Aqui se crea el campo para escribir el texto

        OutlinedTextField(value = query /*El valor actual sera query*/, onValueChange = viewModel::cambioDeQuery /*se actualiza el valor de busqueda*/,
            modifier = Modifier.fillMaxWidth(), label = { Text("Buscar anime") /*Esto es una etiqueta para que el usuario sepa que hacer*/ }, singleLine = true,
            //Aqui se cambio el boton "Enter" del teclado para que sea un boton de "Buscar
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            //En esta parte se llama a la funcion buscar del viewmodel y se esconde el teclado.
            //esto ocurre cuando el usuario presiona buscar
            keyboardActions = KeyboardActions(onSearch = { viewModel.busacar()
                    focusManager.clearFocus()
                }
            )
        )

        //se crea un boton adicional para buscar dentro de la aplicacion
        Button(onClick = { viewModel.busacar()
            focusManager.clearFocus() }, modifier = Modifier.align(Alignment.End)) { Text("Buscar") }
        // Esta linea solo se ejecuta si el error no es nulo. En  caso
        //de algun error se mostrara algun mensaje
        error?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }

        //si esta cargando se muestra un circulo de cargando
        if (cargando) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        //si ya no esta cargando y la lista es vacia se mostrara que no se encontaron resultados.
        //si para de cargar y si tiene resultados se muestan los resultados en un lazyColumn
        //y se llama la funcion objetoAnime que muestra los animes de manera ordenada.
        //objetoAnime se creo en otro archivo originalmnte para la pantalla TopAnime pero quedaba
        //bien para esta pantalla tambien.
        else if (!cargando) {
            if (resultados.isEmpty()) {
                Text("No se encontraron resultados", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            else {
                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(resultados) { anime ->
                        objetoAnime(anime = anime)
                    }
                }
            }
        }
    }
}