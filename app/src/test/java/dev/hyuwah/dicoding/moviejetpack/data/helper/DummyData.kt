package dev.hyuwah.dicoding.moviejetpack.data.helper

import dev.hyuwah.dicoding.moviejetpack.data.remote.response.DiscoverMoviesResponse
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.DiscoverTvResponse
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


}