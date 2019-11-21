package dev.hyuwah.dicoding.moviejetpack.presentation.main

import androidx.lifecycle.ViewModel
import dev.hyuwah.dicoding.moviejetpack.data.Repository

class MovieListViewModel(repository: Repository) : ViewModel() {
    val movies = repository.getMovies()
}