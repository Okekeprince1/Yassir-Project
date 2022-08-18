package com.example.yassirproject.network.repository.home

import com.example.yassirproject.api.MovieApi
import com.example.yassirproject.domain.repository.home.HomeRepository
import com.example.yassirproject.mapper.movie.MovieMapper
import com.example.yassirproject.network.model.movie.MovieResponse
import retrofit2.Response

class HomeRepositoryImpl(
    private val movieApi: MovieApi
): HomeRepository {
    override suspend fun getMovies(page: String): Response<MovieResponse> {
        return movieApi.getMovies(page)
    }
}