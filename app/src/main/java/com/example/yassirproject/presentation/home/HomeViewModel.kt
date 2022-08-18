package com.example.yassirproject.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.yassirproject.domain.model.home.Movie
import com.example.yassirproject.domain.repository.home.HomeRepository
import com.example.yassirproject.mapper.movie.MovieMapper
import com.example.yassirproject.network.model.movie.MovieDto
import com.example.yassirproject.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class HomeViewModel(
    private val homeRepo: HomeRepository,
    private val movieMapper: MovieMapper
): ViewModel() {
    private val _uiState = Event<HomeUiState>()
    val uiState: Event<HomeUiState> = _uiState

    private val _interactions = Event<HomeActions>()
    val interactions: Event<HomeActions> = _interactions

    var pageCount: Int = 1

    private val exceptionHandler = CoroutineExceptionHandler{ _, e ->
        val error = when (e) {
            is UnknownHostException -> "Network Problem Detected. Please check your internet and try again"
            else -> {
                e.message
            }
        }
        _uiState.postValue(
            HomeUiState.Error(error)
        )
    }

    init {
        loadMovies()
    }

    fun loadMovies() {
        if (pageCount == 1){
            _uiState.postValue(HomeUiState.Loading)
        }else{
            _uiState.postValue(HomeUiState.LoadingMore(true))
        }
        viewModelScope.launch(exceptionHandler) {
            val response = withContext(Dispatchers.IO){
                homeRepo.getMovies(page = pageCount.toString())
            }
            if (response.isSuccessful) {
                val results = response.body()!!.results
                val mappedUiState = results.map {
                    movieMapper.mapToWithClickCallback(
                        it,
                        clickCallback = { navigateToMovieDetails(it) })
                }
                _uiState.postValue(HomeUiState.Content(mappedUiState))
                pageCount += 1
            }
        }
    }

    private fun navigateToMovieDetails(it: MovieDto) {
        val destination = HomeFragmentDirections.toMovieDetailsFragment(it.id, it.title)
        _interactions.postValue(HomeActions.Navigate(destination))
    }
}

sealed class HomeActions{
    data class Navigate(val destination: NavDirections): HomeActions()
}

sealed class HomeUiState{
    object Loading: HomeUiState()
    data class LoadingMore(val value: Boolean): HomeUiState()
    data class Content(val movies: List<Movie>): HomeUiState()
    data class Error(val message: Any? = "An Error Occurred!"): HomeUiState()
}