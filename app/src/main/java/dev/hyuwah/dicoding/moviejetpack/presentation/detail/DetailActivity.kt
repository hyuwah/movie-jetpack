package dev.hyuwah.dicoding.moviejetpack.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.data.Constant.TYPES
import dev.hyuwah.dicoding.moviejetpack.data.MovieModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.view_detail_description.*
import kotlinx.android.synthetic.main.view_detail_rounded_poster_with_shadow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ID = "id"
        const val TYPE = "type"
    }


    private val viewModel: DetailViewModel by viewModel()
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val id = intent.getIntExtra(ID, 0)
        val type = intent.getStringExtra(TYPE)

        type?.let {
            if (type == TYPES.MOVIE.name) {
                tv_detail_title.text = TYPES.MOVIE.value
                viewModel.getMovieById(id)?.let { movie ->
                    setupView(movie)
                }
            } else {
                tv_detail_title.text = TYPES.TVSHOW.value
                viewModel.getTvShowById(id)?.let { tvShow ->
                    setupView(tvShow)
                }
            }
        }
    }

    private fun setupView(movieItem: MovieModel) {
        supportActionBar?.title = movieItem.title
        iv_detail_poster.setImageResource(movieItem.posterPath)
        tv_detail_genre.text = String.format(getString(R.string.count_voters), movieItem.voteCount)
        tv_detail_runtime.text = movieItem.releaseDate
        tv_detail_rating.text = movieItem.voteAverage.toString()
        tv_detail_overview.text = movieItem.overview
    }
}
