package dev.hyuwah.dicoding.moviejetpack

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dev.hyuwah.dicoding.moviejetpack.data.Constant
import java.text.SimpleDateFormat
import java.util.*

fun String.asPosterUrl() =
    if (this.isEmpty()) Constant.DEFAULT_POSTER_URL else Constant.IMG_BASE_URL + "w185" + this

fun String.asBackdropUrl() =
    if (this.isEmpty()) Constant.DEFAULT_BACKDROP_URL else Constant.IMG_BASE_URL + "w780" + this

fun String.toNormalDateFormat(): String {
    if (this.isEmpty() || this.isBlank()) return ""
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun ImageView.load(url: String, @DrawableRes placeholder: Int) {
    Glide.with(this).load(url).placeholder(placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.load(gradientDrawable: GradientDrawable){
    Glide.with(this).load(gradientDrawable)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}