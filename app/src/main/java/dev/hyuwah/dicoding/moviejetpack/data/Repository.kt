package dev.hyuwah.dicoding.moviejetpack.data

class Repository {

    fun getMovies(): List<MovieModel> {
        return DummySource.movieList
    }

    fun getTvShows(): List<MovieModel> {
        return DummySource.tvShowList
    }

    fun getMovieById(id: Int) : MovieModel? {
        return DummySource.movieList.find { it.id == id }
    }

    fun getTvShowById(id: Int) : MovieModel? {
        return DummySource.tvShowList.find { it.id == id }
    }

}