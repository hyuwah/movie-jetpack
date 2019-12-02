package dev.hyuwah.dicoding.moviejetpack.presentation.favorite

import androidx.lifecycle.ViewModel
import dev.hyuwah.dicoding.moviejetpack.data.IRepository

class FavoritesViewModel(repository: IRepository) : ViewModel() {

    val pagedList = repository.getAllFavorite()

}