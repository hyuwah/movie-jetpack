package dev.hyuwah.dicoding.moviejetpack

import android.app.Application
import org.koin.core.context.startKoin

class MovieJetpackApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {  }
    }

}