package dev.hyuwah.dicoding.moviejetpack.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hyuwah.dicoding.moviejetpack.data.Constant
import dev.hyuwah.dicoding.moviejetpack.data.IRepository
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import dev.hyuwah.dicoding.moviejetpack.presentation.model.toFavoriteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: IRepository) : ViewModel() {

    private var currentItem: MovieItem? = null
    private var type: String = ""
    private var _state = MutableLiveData<Resource<MovieItem>>()
    val state: LiveData<Resource<MovieItem>> = _state
    private var _isFavorite = MutableLiveData(false)
    val isFavorite = _isFavorite as LiveData<Boolean>

    fun load(type: String, id: Int) {
        _state.postValue(Resource.Loading)
        this.type = type
        currentItem = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = if (type == Constant.TYPES.MOVIE.name)
                    repository.fetchMovieDetailById(id)
                else
                    repository.fetchTvShowDetailById(id)
                currentItem = result
                _isFavorite.postValue(checkIsFavorite(result.id))
                _state.postValue(Resource.Success(result))
            } catch (t: Throwable) {
                _state.postValue(Resource.Failure(t))
            }
        }
    }

    private suspend fun checkIsFavorite(id: Int): Boolean {
        return repository.getFavoriteById(id) != null
    }

    fun saveToFavorite() {
        currentItem?.let {
            viewModelScope.launch {
                repository.addFavorite(it.toFavoriteItem(type))
                _isFavorite.value = true
            }
        }
    }

    fun removeFromFavorite() {
        currentItem?.let {
            viewModelScope.launch {
                repository.removeFavorite(it.id)
                _isFavorite.value = false
            }
        }
    }


}