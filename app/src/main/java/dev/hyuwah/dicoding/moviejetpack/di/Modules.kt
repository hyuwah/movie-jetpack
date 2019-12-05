package dev.hyuwah.dicoding.moviejetpack.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.hyuwah.dicoding.moviejetpack.BuildConfig
import dev.hyuwah.dicoding.moviejetpack.data.IRepository
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.data.local.AppDatabase
import dev.hyuwah.dicoding.moviejetpack.data.remote.TheMovieDbApiService
import dev.hyuwah.dicoding.moviejetpack.presentation.detail.DetailViewModel
import dev.hyuwah.dicoding.moviejetpack.presentation.favorite.FavoritesViewModel
import dev.hyuwah.dicoding.moviejetpack.presentation.main.MovieListViewModel
import dev.hyuwah.dicoding.moviejetpack.presentation.main.TvShowListViewModel
import dev.hyuwah.dicoding.moviejetpack.utils.EspressoIdlingResource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/"

val networkModule = module {
    single<Gson> { GsonBuilder().create() }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor {
                val original = it.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIE_DB_KEY)
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                it.proceed(request)
            }
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build() as Retrofit
    }
    single { get<Retrofit>().create(TheMovieDbApiService::class.java) as TheMovieDbApiService
    }
}

val applicationModule = module {
    single { AppDatabase.getInstance(get()).favoritesDao() }
    single {
        Repository(
            get(),
            get(),
            EspressoIdlingResource.getEspressoIdlingResource()
        ) as IRepository
    }
}

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { TvShowListViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}