package com.example.yassirproject.network.model.movie

data class MovieResponse(
    val page: Int,
    val results: List<MovieDto>
)
