package dev.hyuwah.dicoding.moviejetpack.data

import dev.hyuwah.dicoding.moviejetpack.asBackdropUrl
import dev.hyuwah.dicoding.moviejetpack.asPosterUrl
import dev.hyuwah.dicoding.moviejetpack.data.remote.TheMovieDbApiService
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.toNormalDateFormat

class Repository(private val theMovieDbApiService: TheMovieDbApiService) : IRepository {

    override suspend fun fetchDiscoverMovies(): List<MovieItem> {
        return try {
            theMovieDbApiService
                .getDiscoverMovies("en", 1, "ID")
                .results.map {
                MovieItem(
                    it.id,
                    it.title,
                    it.posterPath.orEmpty().asPosterUrl(),
                    it.backdropPath.orEmpty().asBackdropUrl(),
                    it.releaseDate.toNormalDateFormat(),
                    it.overview,
                    it.voteAverage,
                    it.voteCount
                )
            }
        } catch (e: Throwable) {
            listOf()
        }
    }

    override suspend fun fetchDiscoverTvShow(): List<MovieItem> {
        return try {
            theMovieDbApiService
                .getDiscoverTvShow("en", 1)
                .results.map {
                MovieItem(
                    it.id,
                    it.name,
                    it.posterPath.orEmpty().asPosterUrl(),
                    it.backdropPath.orEmpty().asBackdropUrl(),
                    it.firstAirDate.toNormalDateFormat(),
                    it.overview,
                    it.voteAverage,
                    it.voteCount
                )
            }
        } catch (e: Throwable) {
            listOf()
        }
    }

    override suspend fun fetchTvShowDetailById(id: Int): MovieItem {
        return try {
            val response = theMovieDbApiService.getTvShowDetail(id)
            MovieItem(
                id = response.id,
                title = response.name,
                overview = response.overview,
                voteCount = response.voteCount,
                voteAverage = response.voteAverage,
                releaseDate = response.firstAirDate.toNormalDateFormat(),
                backdropUrl = response.backdropPath.asBackdropUrl(),
                posterUrl = response.posterPath.asPosterUrl()
            )
        }catch (e: Throwable){
            println("ERROR: ${e.localizedMessage}")
            MovieItem()
        }
    }

    override suspend fun fetchMovieDetailById(id: Int): MovieItem {
        return try {
            val response = theMovieDbApiService.getMovieDetail(id)
            MovieItem(
                id = response.id,
                title = response.title,
                overview = response.overview,
                voteCount = response.voteCount,
                voteAverage = response.voteAverage,
                releaseDate = response.releaseDate.toNormalDateFormat(),
                backdropUrl = response.backdropPath.asBackdropUrl(),
                posterUrl = response.posterPath.asPosterUrl()
            )
        }catch (e: Throwable){
            println("ERROR: ${e.localizedMessage}")
            MovieItem()
        }
    }

}