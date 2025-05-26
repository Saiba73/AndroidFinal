package com.example.clon_fulanito.modelos.jikan_modelos

data class Anime(
    val mal_id: Int,
    val title: String,
    val images: Images,
    val synopsis: String?,
    val score: Float?,
    val episodes: Int?,
    val year: Int?
)
