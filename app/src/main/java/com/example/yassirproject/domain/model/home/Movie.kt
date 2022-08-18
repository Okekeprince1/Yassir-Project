package com.example.yassirproject.domain.model.home

data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val year: String,
    val vote_average: Double,
    val vote_count: Int,
    val onClick: () -> Unit
)
