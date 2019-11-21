package dev.hyuwah.dicoding.moviejetpack.presentation.detail

import androidx.lifecycle.ViewModel
import dev.hyuwah.dicoding.moviejetpack.data.MovieModel
import dev.hyuwah.dicoding.moviejetpack.data.Repository

class DetailViewModel(private val repository: Repository): ViewModel() {

    fun getMovieById(id: Int) : MovieModel? {
        return repository.getMovieById(id)
    }

    fun getTvShowById(id: Int) : MovieModel? {
        return repository.getTvShowById(id)
    }
}