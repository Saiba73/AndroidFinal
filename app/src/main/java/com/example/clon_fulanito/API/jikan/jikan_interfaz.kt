package com.example.clon_fulanito.API.jikan

import com.example.clon_fulanito.modelos.jikan_modelos.AnimeRespuesta
import retrofit2.http.GET
import retrofit2.http.Query


interface JIKANAPIServicio{
    //Obtiene una lista de TopAnimes
    @GET("top/anime")
    suspend fun obtener_TopAnime(): AnimeRespuesta

    //Esta se usa para obtener una lista de animes en la pantalla buscar
    @GET("anime")
    suspend fun buscar_Anime(@Query("q") query: String): AnimeRespuesta

    //Obtiene una lista de animes de la temporada actual
    @GET("seasons/now")
    suspend fun animesTemporadaActual(): AnimeRespuesta
}