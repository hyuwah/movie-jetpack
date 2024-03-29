package dev.hyuwah.dicoding.moviejetpack.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hyuwah.dicoding.moviejetpack.data.IRepository
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowListViewModel(private val repository: IRepository) : ViewModel() {
    private var _state = MutableLiveData<Resource<List<MovieItem>>>()
    val state: LiveData<Resource<List<MovieItem>>> = _state

    fun load(){
        _state.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(Resource.Success(repository.fetchDiscoverTvShow()))
            } catch (t: Throwable) {
                _state.postValue(Resource.Failure(t))
            }
        }
    }
}