package dev.hyuwah.dicoding.moviejetpack.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"
    private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() = espressoTestIdlingResource.increment()

    fun decrement() = espressoTestIdlingResource.decrement()

    fun getEspressoIdlingResource(): IdlingResource = espressoTestIdlingResource
}