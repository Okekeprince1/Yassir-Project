package com.example.yassirproject.domain.model.movie_details

data class MovieDetails(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val year: String,
    val overview: String,
    val genres: List<String>
)
