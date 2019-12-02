package dev.hyuwah.dicoding.moviejetpack.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.presentation.adapter.MainViewPagerAdapter
import dev.hyuwah.dicoding.moviejetpack.presentation.favorite.FavoriteActivity
import dev.hyuwah.dicoding.moviejetpack.presentation.main.MovieListFragment
import dev.hyuwah.dicoding.moviejetpack.presentation.main.TvShowListFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = "Muvilog Jetpack"

        tl_category.setupWithViewPager(vp_category)
        vp_category.adapter = MainViewPagerAdapter(supportFragmentManager).apply {
            addFragment(
                MovieListFragment().apply { retainInstance = true },
                getString(R.string.tab_title_movie)
            )
            addFragment(
                TvShowListFragment().apply { retainInstance = true },
                getString(R.string.tab_title_tv_show)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(intentFor<FavoriteActivity>())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
