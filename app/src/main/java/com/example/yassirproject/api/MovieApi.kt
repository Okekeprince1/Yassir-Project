package com.example.yassirproject.api

import com.example.yassirproject.network.model.movie.MovieResponse
import com.example.yassirproject.network.model.movie_details.MovieDetailsDto
import retrofit2.Response
import retrofit2.http.*

interface MovieApi {
    @GET("3/discover/movie")
    suspend fun getMovies(
        @Query("page") page: String
    ): Response<MovieResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movie_Id: String
    ): Response<MovieDetailsDto>
}