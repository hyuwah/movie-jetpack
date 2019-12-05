package dev.hyuwah.dicoding.moviejetpack.data.helper

import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.DiscoverMoviesResponse
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.DiscoverTvResponse
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.MovieDetailResponse
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.TvShowDetailResponse
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem

object DummyData {

    object MovieList {
        fun normal(): List<MovieItem> {
            return listOf(
                MovieItem(
                    id = 1,
                    title = "Movie Test",
                    voteCount = 1,
                    voteAverage = 10.0,
                    overview = "Overview",
                    backdropUrl = "",
                    posterUrl = "",
                    releaseDate = ""
                )
            )
        }
    }

    object DiscoverMovie {
        fun emptyResponse(): DiscoverMoviesResponse {
            return DiscoverMoviesResponse()
        }

        fun normalResponse(): DiscoverMoviesResponse {
            return DiscoverMoviesResponse(
                results = listOf(
                    DiscoverMoviesResponse.Result(
                        title = "Movie 1"
                    )
                )
            )
        }
    }

    object DiscoverTv {
        fun emptyResponse(): DiscoverTvResponse {
            return DiscoverTvResponse()
        }

        fun normalResponse(): DiscoverTvResponse {
            return DiscoverTvResponse(
                results = listOf(
                    DiscoverTvResponse.Result(
                        name = "TV Show 1"
                    )
                )
            )
        }
    }

    object Detail {
        fun tvShowNormalResponse(): TvShowDetailResponse {
            return TvShowDetailResponse(
                name = "TV Show 1"
            )
        }

        fun movieNormalResponse(): MovieDetailResponse {
            return MovieDetailResponse(
                title = "Movie 1"
            )
        }
    }

    object Favorite {
        fun movieNormalResponse(): List<FavoriteItem> {
            return DummyData.MovieList.normal().map {
                FavoriteItem(
                    it.id,
                    it.title,
                    it.posterUrl,
                    it.backdropUrl,
                    it.releaseDate,
                    it.overview,
                    it.voteAverage,
                    it.voteCount,
                    "MOVIE"
                )
            }
        }
    }


}