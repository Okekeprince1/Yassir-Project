package com.example.yassirproject.domain.repository.home

import com.example.yassirproject.network.model.movie.MovieResponse
import retrofit2.Response

interface HomeRepository {
    suspend fun getMovies(page: String): Response<MovieResponse>
}