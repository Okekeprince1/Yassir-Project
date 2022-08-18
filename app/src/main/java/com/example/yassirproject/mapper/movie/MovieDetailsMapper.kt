package com.example.yassirproject.mapper.movie

import com.example.yassirproject.domain.model.movie_details.MovieDetails
import com.example.yassirproject.mapper.BaseMapper
import com.example.yassirproject.network.model.movie_details.MovieDetailsDto

class MovieDetailsMapper: BaseMapper<MovieDetailsDto, MovieDetails> {
    override fun mapFrom(to: MovieDetails): MovieDetailsDto {
        TODO("Not yet implemented")
    }

    override fun mapTo(from: MovieDetailsDto): MovieDetails {
        return MovieDetails(
            id = from.id,
            title = from.title,
            imageUrl = from.poster_path,
            year = from.release_date,
            overview = from.overview,
            genres = from.genres.map { it.name }
        )
    }
}