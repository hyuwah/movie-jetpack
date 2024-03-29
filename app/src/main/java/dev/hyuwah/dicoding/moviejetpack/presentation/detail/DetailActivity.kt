package dev.hyuwah.dicoding.moviejetpack.presentation.detail

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.observe
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.data.Constant.TYPES
import dev.hyuwah.dicoding.moviejetpack.load
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.view_detail_description.*
import kotlinx.android.synthetic.main.view_detail_rounded_poster_with_shadow.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ID = "id"
        const val TYPE = "type"
    }

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val id = intent.getIntExtra(ID, 0)
        val type = intent.getStringExtra(TYPE)

        viewModel.state.observe(this, ::updateUI)
        viewModel.isFavorite.observe(this, ::isFavorite)

        type?.let {
            viewModel.load(type, id)
            if (type == TYPES.MOVIE.name) {
                tv_detail_title.text = TYPES.MOVIE.value
            } else {
                tv_detail_title.text = TYPES.TVSHOW.value
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI(resource: Resource<MovieItem>) {
        fab_favorite.hide()
//        showNoInternetView(false)
        when (resource) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                setupView(resource.data)
            }
            is Resource.Failure -> {
//                showNoInternetView(true)
                toast("Error ${resource.throwable.localizedMessage}")
            }
        }
    }

    private fun setupView(movieItem: MovieItem) {
        fab_favorite.show()
        collapsing_toolbar.title = movieItem.title
        iv_detail_poster.load(movieItem.posterUrl, R.drawable.placeholder_poster_portrait)
        iv_backdrop.load(movieItem.backdropUrl, R.drawable.placeholder_poster_landscape)
        tv_detail_genre.text = String.format(getString(R.string.count_voters), movieItem.voteCount)
        tv_detail_runtime.text = movieItem.releaseDate
        tv_detail_rating.text = movieItem.voteAverage.toString()
        tv_detail_overview.text = movieItem.overview
        createBlurBg(movieItem.posterUrl)
    }

    private fun isFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            fab_favorite.setImageResource(R.drawable.ic_favorite_24dp)
            fab_favorite.setOnClickListener {
                toast("Removed from favorite")
                viewModel.removeFromFavorite()
            }
        } else {
            fab_favorite.setImageResource(R.drawable.ic_favorite_border_24dp)
            fab_favorite.setOnClickListener {
                toast("Added to favorite")
                viewModel.saveToFavorite()
            }
        }
    }

    private fun createBlurBg(posterUrl: String) {
        doAsync {
            val drawable =
                Glide.with(this@DetailActivity).load(posterUrl).submit().get()
            runOnUiThread {
                Palette.from(drawable.toBitmap()).generate { palette ->
                    val defValue = 0x000000
                    val dominant = palette?.getDominantColor(defValue) ?: defValue
                    val muted = palette?.getMutedColor(defValue) ?: defValue
                    val darkVibrant = palette?.getDarkMutedColor(defValue) ?: defValue
                    val vibrant = palette?.getVibrantColor(defValue) ?: defValue
                    val gradient = GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        intArrayOf(darkVibrant, vibrant, muted, dominant)
                    )
                    iv_bg_blur.load(gradient)
                }
            }
        }
    }
}
