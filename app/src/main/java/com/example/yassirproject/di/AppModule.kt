package com.example.yassirproject.di

import com.example.yassirproject.api.AuthorizationInterceptor
import com.example.yassirproject.api.MovieApi
import com.example.yassirproject.constants.Constants
import com.example.yassirproject.domain.model.movie_details.MovieDetails
import com.example.yassirproject.domain.repository.home.HomeRepository
import com.example.yassirproject.domain.repository.movie_details.MovieDetailsRepository
import com.example.yassirproject.mapper.movie.MovieDetailsMapper
import com.example.yassirproject.mapper.movie.MovieMapper
import com.example.yassirproject.network.repository.home.HomeRepositoryImpl
import com.example.yassirproject.network.repository.movie_details.MovieDetailsRepositoryImpl
import com.example.yassirproject.presentation.home.HomeViewModel
import com.example.yassirproject.presentation.movie_details.MovieDetailsViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient.Builder().apply {
                    addInterceptor(get<AuthorizationInterceptor>())
                }.build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    single {
        AuthorizationInterceptor()
    } bind Interceptor::class

    single<HomeRepository>{
        HomeRepositoryImpl(get())
    }

    single<MovieDetailsRepository>{
        MovieDetailsRepositoryImpl(get())
    }

    single { MovieMapper() }

    single { MovieDetailsMapper() }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { MovieDetailsViewModel(get(), get()) }

}