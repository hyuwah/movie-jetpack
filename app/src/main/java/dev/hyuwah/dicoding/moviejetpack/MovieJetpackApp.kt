package dev.hyuwah.dicoding.moviejetpack

import android.app.Application
import dev.hyuwah.dicoding.moviejetpack.di.applicationModule
import dev.hyuwah.dicoding.moviejetpack.di.networkModule
import dev.hyuwah.dicoding.moviejetpack.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieJetpackApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieJetpackApp)
            modules(listOf(networkModule, applicationModule, viewModelModule))
        }
    }

}