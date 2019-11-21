package dev.hyuwah.dicoding.moviejetpack.presentation.main

import androidx.lifecycle.ViewModel
import dev.hyuwah.dicoding.moviejetpack.data.Repository

class TvShowListViewModel(repository: Repository) : ViewModel() {
    val tvShows = repository.getTvShows()
}