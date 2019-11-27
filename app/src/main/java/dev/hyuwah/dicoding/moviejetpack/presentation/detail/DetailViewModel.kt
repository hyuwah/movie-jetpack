package dev.hyuwah.dicoding.moviejetpack.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hyuwah.dicoding.moviejetpack.data.Constant
import dev.hyuwah.dicoding.moviejetpack.data.IRepository
import dev.hyuwah.dicoding.moviejetpack.data.MovieModel
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: IRepository) : ViewModel() {
    private var _state = MutableLiveData<Resource<MovieItem>>()
    val state: LiveData<Resource<MovieItem>> = _state

    fun load(type: String, id: Int) {
        _state.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = if (type == Constant.TYPES.MOVIE.name)
                    repository.fetchMovieDetailById(id)
                else
                    repository.fetchTvShowDetailById(id)
                _state.postValue(Resource.Success(result))
            } catch (t: Throwable) {
                _state.postValue(Resource.Failure(t))
            }
        }
    }


}