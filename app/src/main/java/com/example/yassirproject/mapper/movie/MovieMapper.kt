package com.example.yassirproject.mapper.movie

import com.example.yassirproject.domain.model.home.Movie
import com.example.yassirproject.mapper.BaseMapper
import com.example.yassirproject.network.model.movie.MovieDto

class MovieMapper: BaseMapper<MovieDto, Movie> {
    override fun mapFrom(to: Movie): MovieDto {
        TODO("Not yet implemented")
    }

    override fun mapTo(from: MovieDto): Movie {
        return Movie(
            id = from.id,
            title = from.title,
            imageUrl = from.poster_path,
            year = from.release_date,
            vote_average = from.vote_average,
            vote_count = from.vote_count,
            onClick = {}
        )
    }

    fun mapToWithClickCallback(from: MovieDto, clickCallback: () -> Unit): Movie {
        return Movie(
            id = from.id,
            title = from.title,
            imageUrl = from.poster_path,
            year = from.release_date,
            vote_average = from.vote_average,
            vote_count = from.vote_count,
            onClick = {
                clickCallback()
            }
        )
    }
}