package dev.hyuwah.dicoding.moviejetpack.di

import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.presentation.detail.DetailViewModel
import dev.hyuwah.dicoding.moviejetpack.presentation.main.MovieListViewModel
import dev.hyuwah.dicoding.moviejetpack.presentation.main.TvShowListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single { Repository() }
}

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { TvShowListViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}