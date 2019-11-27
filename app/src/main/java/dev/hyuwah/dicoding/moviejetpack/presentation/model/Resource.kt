package dev.hyuwah.dicoding.moviejetpack.presentation.model

/**
 * Represents a network bound resource. Each subclass represents the resource's   state:
 * - [Loading]: the resource is being retrieved from network.
 * - [Success]: the resource has been retrieved (available in [Success.data] field)
 * - [Failure]: the resource retrieving has failed (throwable available in [Failure.throwable]
 * field)
 *  https://www.prolificinteractive.com/2019/02/22/the-power-of-livedata-kotlin-sealed-classes/
 */
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val throwable: Throwable): Resource<Nothing>()
}