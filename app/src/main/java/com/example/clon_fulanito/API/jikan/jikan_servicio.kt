package com.example.clon_fulanito.API.jikan

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object instanciaRetrofitJSONJIKAN {
    private const val url_base = "https://api.jikan.moe/v4/"

    private val servicioAPI: Retrofit by lazy {
        Retrofit.Builder().baseUrl(url_base).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val usar_servicio: JIKANAPIServicio by lazy {
        servicioAPI.create(JIKANAPIServicio::class.java)
    }
}