package dev.hyuwah.dicoding.moviejetpack.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hyuwah.dicoding.moviejetpack.data.IRepository
import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: IRepository) : ViewModel() {
    private var _state = MutableLiveData<Resource<List<FavoriteItem>>>()
    val state: LiveData<Resource<List<FavoriteItem>>> = _state

    fun load() {
        _state.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(
                    Resource.Success(repository.getAllFavorite())
                )
            } catch (t: Throwable) {
                _state.postValue(Resource.Failure(t))
            }
        }
    }
}