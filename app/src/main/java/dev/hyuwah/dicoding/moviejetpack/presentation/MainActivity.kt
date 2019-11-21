package dev.hyuwah.dicoding.moviejetpack.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.presentation.adapter.MainViewPagerAdapter
import dev.hyuwah.dicoding.moviejetpack.presentation.main.MovieListFragment
import dev.hyuwah.dicoding.moviejetpack.presentation.main.TvShowListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = "Muvilog Jetpack"

        tl_category.setupWithViewPager(vp_category)
        vp_category.adapter = MainViewPagerAdapter(supportFragmentManager).apply {
            addFragment(MovieListFragment().apply { retainInstance = true }, getString(R.string.tab_title_movie))
            addFragment(TvShowListFragment().apply { retainInstance = true }, getString(R.string.tab_title_tv_show))
        }
    }
}
