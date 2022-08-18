package com.example.yassirproject.domain.repository.movie_details

import com.example.yassirproject.network.model.movie_details.MovieDetailsDto
import retrofit2.Response

interface MovieDetailsRepository {
    suspend fun getMovieDetails(move_Id: String): Response<MovieDetailsDto>
}