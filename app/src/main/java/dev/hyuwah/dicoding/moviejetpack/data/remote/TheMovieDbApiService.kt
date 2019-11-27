package dev.hyuwah.dicoding.moviejetpack.data.remote

import dev.hyuwah.dicoding.moviejetpack.data.remote.response.DiscoverMoviesResponse
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.DiscoverTvResponse
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.MovieDetailResponse
import dev.hyuwah.dicoding.moviejetpack.data.remote.response.TvShowDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApiService {

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("language") lang: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): DiscoverMoviesResponse

    @GET("discover/tv")
    suspend fun getDiscoverTvShow(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): DiscoverTvResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int
    ): MovieDetailResponse

    @GET("tv/{id}")
    suspend fun getTvShowDetail(
        @Path("id") id: Int
    ): TvShowDetailResponse

}