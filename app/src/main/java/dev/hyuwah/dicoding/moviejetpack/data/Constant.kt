package dev.hyuwah.dicoding.moviejetpack.data

object Constant {

    enum class TYPES(val value: String) {
        MOVIE("Movie"),
        TVSHOW("TV Show"),
    }

    const val IMG_BASE_URL = "https://image.tmdb.org/t/p/"
    const val DEFAULT_BACKDROP_URL = "http://placehold.jp/36/cccccc/aaaaaa/480x270.png?text=Awesome%20Poster%20Here"
    const val DEFAULT_POSTER_URL = "http://placehold.jp/48/cccccc/aaaaaa/320x480.png?text=Awesome%20Poster%20Here"

}