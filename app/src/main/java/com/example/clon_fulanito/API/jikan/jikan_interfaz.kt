package com.example.clon_fulanito.API.jikan

import com.example.clon_fulanito.modelos.jikan_modelos.Anime
import com.example.clon_fulanito.modelos.jikan_modelos.AnimeRespuesta
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JIKANAPIServicio{
    @GET("top/anime")
    suspend fun obtener_TopAnime(): AnimeRespuesta

    @GET("anime")
    suspend fun buscar_Anime(@Query("q") query: String): AnimeRespuesta

    @GET("anime/{id}")
    suspend fun obtener_AnimePorId(@Path("id") id: Int): Anime
}