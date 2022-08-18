package com.example.yassirproject.network.repository.movie_details

import com.example.yassirproject.api.MovieApi
import com.example.yassirproject.domain.repository.movie_details.MovieDetailsRepository
import com.example.yassirproject.network.model.movie_details.MovieDetailsDto
import retrofit2.Response

class MovieDetailsRepositoryImpl(
    private val movieApi: MovieApi
): MovieDetailsRepository {
    override suspend fun getMovieDetails(movie_Id: String): Response<MovieDetailsDto> {
        return movieApi.getMovie(movie_Id)
    }

}