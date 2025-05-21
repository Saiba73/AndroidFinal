package com.example.clon_fulanito.vista_modelos.jikan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clon_fulanito.API.jikan.JIKANAPIServicio
import com.example.clon_fulanito.API.jikan.instanciaRetrofitJSONJIKAN
import com.example.clon_fulanito.modelos.jikan_modelos.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random


//ViewModel Para la pantalla de top animes
class classAnimeViewModel : ViewModel() {

    private val _listaAnime = MutableStateFlow(emptyList<Anime>())
    val listaAnime: StateFlow<List<Anime>> = _listaAnime

    private val _estaCargando = MutableStateFlow(false)
    val estaCargando: StateFlow<Boolean> = _estaCargando

    init {
        obtenerTopAnime()
    }

    private fun obtenerTopAnime() {
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                val respuesta = instanciaRetrofitJSONJIKAN.usar_servicio.obtener_TopAnime()
                _listaAnime.value = respuesta.data
            }catch (e: Exception) {
                e.printStackTrace()
            }finally {
                _estaCargando.value = false
            }
        }
    }
}


//ViewModel para la pantalla de busqueda
class BuscarViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _resultados = MutableStateFlow<List<Anime>>(emptyList())
    val resultados: StateFlow<List<Anime>> = _resultados

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onQueryChanged(nueva: String) {
        _query.value = nueva
    }

    fun search() {
        val q = _query.value.trim()
        if (q.isEmpty()) {
            _error.value = "Debes ingresar una palabra clave"
            _resultados.value = emptyList()
            return
        }

        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                val respuesta = instanciaRetrofitJSONJIKAN.usar_servicio.buscar_Anime(q)
                _resultados.value = respuesta.data
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.localizedMessage}"
            } finally {
                _cargando.value = false
            }
        }
    }
}


//ViewModel para la pantalla de recomendaciones
class RecomendacionViewModel : ViewModel(){
    private val _listaTop = MutableStateFlow<List<Anime>>(emptyList())
    val listaTop: StateFlow<List<Anime>> = _listaTop

    private val _recomendado = MutableStateFlow<Anime?>(null)
    val recomendado: StateFlow<Anime?> = _recomendado

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    init {
        cargarTop()
    }

    private fun cargarTop(){
        viewModelScope.launch {
            _cargando.value = true
            try{
                val respuesta = instanciaRetrofitJSONJIKAN.usar_servicio.obtener_TopAnime()
                _listaTop.value = respuesta.data
                recomendar()
            }catch (e: Exception) {
                e.printStackTrace()
            }finally {
                _cargando.value = false
            }
        }
    }

    fun recomendar() {
        val lista = _listaTop.value
        if(lista.isNotEmpty()){
            _recomendado.value = lista[Random.nextInt(lista.size)]
        }
    }
}

