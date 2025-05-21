package com.example.clon_fulanito.ui.pantallas.jikan_pantallas

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.clon_fulanito.modelos.jikan_modelos.Anime

@Composable
fun PantallaBuscar(
    navController: NavController,
    viewModel: BuscarViewModel = viewModel()
) {

    val query by viewModel.query.collectAsState()
    val resultados by viewModel.resultados.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(value = query, onValueChange = viewModel::onQueryChanged, modifier = Modifier.fillMaxWidth(), label = { Text("Buscar anime") }, singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search), keyboardActions = KeyboardActions(
                onSearch = { viewModel.search()
                    focusManager.clearFocus()
                }
            )
        )

        Button(
            onClick = { viewModel.search()
                focusManager.clearFocus() }, modifier = Modifier.align(Alignment.End)
        ) { Text("Buscar") }

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        if (cargando) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (!cargando) {
            if (resultados.isEmpty()) {
                Text(
                    "No se encontraron resultados",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(resultados) { anime ->
                        objetoAnime(anime = anime, navController = navController)
                    }
                }
            }
        }
    }
}