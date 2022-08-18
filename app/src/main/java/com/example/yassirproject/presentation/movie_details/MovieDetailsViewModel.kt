package com.example.yassirproject.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yassirproject.api.MovieApi
import com.example.yassirproject.domain.model.movie_details.MovieDetails
import com.example.yassirproject.mapper.movie.MovieDetailsMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class MovieDetailsViewModel(
    private val movieApi: MovieApi,
    private val movieDetailsMapper: MovieDetailsMapper
): ViewModel() {

    private val _uiState = MutableLiveData<MovieDetailsUiState>()
    val uiState: LiveData<MovieDetailsUiState> = _uiState

    private var movieId: String? = null

    private val exceptionHandler = CoroutineExceptionHandler{_, e ->
        val error = when (e) {
            is UnknownHostException -> "Network Problem Detected. Please check your internet and try again"
            else -> {
                e.message
            }
        }
        _uiState.postValue(
            MovieDetailsUiState.Error(error)
        )
    }


    fun getMovieDetails(id: String) {
        if (movieId != id) {
            movieId = id
            _uiState.postValue(MovieDetailsUiState.Loading)
            viewModelScope.launch(exceptionHandler){
                val response = withContext(Dispatchers.IO) {
                    movieApi.getMovie(movie_Id = movieId!!)
                }
                if (response.isSuccessful) {
                    val mappedUiState = movieDetailsMapper.mapTo(response.body()!!)
                    _uiState.postValue(MovieDetailsUiState.Content(mappedUiState))
                }
            }
        }
    }
}

sealed class MovieDetailsUiState {
    object Loading: MovieDetailsUiState()
    data class Content(val movieDetails: MovieDetails): MovieDetailsUiState()
    data class Error(val message: Any? = "An Error Occurred!"): MovieDetailsUiState()
}