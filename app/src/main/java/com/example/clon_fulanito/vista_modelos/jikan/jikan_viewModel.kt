package com.example.clon_fulanito.vista_modelos.jikan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clon_fulanito.API.jikan.instanciaRetrofitJSONJIKAN
import com.example.clon_fulanito.modelos.jikan_modelos.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random


//ViewModel Para la pantalla de top animes para mostrar los animes con mejor calificacion por los usuarios
class TopAnimeViewModel: ViewModel() {

    //Esta es una variable privada que solo se puede modificar dentro del view model
    //para evitar que se hagan cambios en la interfaz
    private val _listaAnime = MutableStateFlow(emptyList<Anime>())

    //Esta es una variable publica que solo muestra una lista de animes
    //Esta varaible es la que se envia al metodo de la interfaz para mostrar informacion
    val listaAnime: StateFlow<List<Anime>> = _listaAnime

    private val _estaCargando = MutableStateFlow(false)
    val estaCargando: StateFlow<Boolean> = _estaCargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    //Cuando se crea una instancia de este view model automaticamente se lanza
    //este init y corre la funcion obtenerTopAnime
    init {
        obtenerTopAnime()
    }

    private fun obtenerTopAnime() {
        //viewModelScope se usa para poder operar sin para el funcionamiento de la interfaz
        viewModelScope.launch {
            _estaCargando.value = true
            _error.value = null
            try {
                //Aqui se llama a la api usando retrofit para obtener los top Anime
                //y se guardan los valores en _listaAnime.value
                val respuesta = instanciaRetrofitJSONJIKAN.usar_servicio.obtener_TopAnime()
                _listaAnime.value = respuesta.data
            }
            catch (e: Exception) {
                //Si hay algun error se guarda el mensaje de error
                _error.value = "No se pudo cargar la temporada: ${e.localizedMessage}"
            }
            finally {
                //Sin importar que pase a _estaCargando se pondra como falso
                //esto es para no mostrar el circulo de carga
                _estaCargando.value = false
            }
        }
    }
}

//ViewModel para la pantalla de busqueda
class BuscarViewModel: ViewModel() {

    //Esta variable guarda el texto que el usuario escribe en la busqueda
    //de manera privada para usarla
    private val _query = MutableStateFlow("")
    //Esta variable guarda el texto que el usuario escribe en la busqueda
    //en caso que se quiera usar para mostrar
    val query: StateFlow<String> = _query

    //Esta variable guarda la lista de animes que la api retorna de
    //manera privada
    private val _resultados = MutableStateFlow<List<Anime>>(emptyList())
    //Esta variable guarda la lista de animes publicamente y es
    //no mutable para que solo se pueda usara para mostar la lista
    val resultados: StateFlow<List<Anime>> = _resultados

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    //Esta funcion se llama cuando el usuario escribe en la caja de busqueda
    //para que cambie el valor de la variable _query
    fun cambioDeQuery(nueva: String) {
        _query.value = nueva
    }

    fun busacar() {
        //Esta varaible "q" guarda lo que escribio el usuario en la caja de busqueda
        //se le quitan todos los espasios
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
                //Aqui se llama a retrofit para guardar la lista de animes que retorna la api
                val respuesta = instanciaRetrofitJSONJIKAN.usar_servicio.buscar_Anime(q)
                _resultados.value = respuesta.data
            }
            catch (e: Exception) {
                _error.value = "Error de red: ${e.localizedMessage}"
            }
            finally {
                _cargando.value = false
            }
        }
    }
}


//ViewModel para la pantalla de recomendaciones
//Es muy similar al primer view model de TopAnime porque usa esa misma funcion del retrofit
//pero esta devueve un valor al azar de un anime en la lista.
//Creo que puede haber una manera de convinarlo con el viewmodel de TopAnime
//pero preferi crear un viewmodel individual para cada pantalla.
class RecomendacionViewModel: ViewModel(){
    private val _listaTop = MutableStateFlow<List<Anime>>(emptyList())
    //val listaTop: StateFlow<List<Anime>> = _listaTop

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

    //Esta funcion escoge una valor aleatorio de la lista de top animes
    //para recomendar alguno.
    fun recomendar() {
        val lista = _listaTop.value
        if(lista.isNotEmpty()){
            _recomendado.value = lista[Random.nextInt(lista.size)]
        }
    }
}

// Modelo para la pantalla principal llama la api para mostrar los animes de la temporada actual.
class PrincipalViewModel: ViewModel() {

    private val _temporadaActual = MutableStateFlow<List<Anime>>(emptyList())
    val temporadaActual: StateFlow<List<Anime>> = _temporadaActual

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarTemporadaActual()
    }

    private fun cargarTemporadaActual() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                val respuesta = instanciaRetrofitJSONJIKAN.usar_servicio.animesTemporadaActual()
                _temporadaActual.value = respuesta.data }
            catch (e: Exception) {
                _error.value = "No se pudo cargar los top animes: ${e.localizedMessage}"
            }
            finally {
                _cargando.value = false
            }
        }
    }
}

