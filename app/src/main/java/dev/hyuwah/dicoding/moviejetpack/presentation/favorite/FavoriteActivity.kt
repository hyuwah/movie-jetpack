package dev.hyuwah.dicoding.moviejetpack.presentation.favorite

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem
import dev.hyuwah.dicoding.moviejetpack.presentation.adapter.FavoritesListAdapter
import dev.hyuwah.dicoding.moviejetpack.presentation.detail.DetailActivity
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import kotlinx.android.synthetic.main.activity_favorite.*
import org.jetbrains.anko.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity(), FavoritesListAdapter.Interaction {

    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var adapter: FavoritesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        title = "Favorites"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        adapter = FavoritesListAdapter(this)

        rv_favorites.adapter = adapter

        viewModel.state.observe(this, ::updateUI)
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI(resource: Resource<List<FavoriteItem>>) {
        when (resource) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                if (resource.data.isNotEmpty())
                    showEmptyView(false)
                else
                    showEmptyView(true)
                adapter.submitList(resource.data)
            }
            is Resource.Failure -> {
                showEmptyView(true)
            }
        }
    }

    override fun onItemSelected(position: Int, item: FavoriteItem) {
        startActivity(
            intentFor<DetailActivity>(
                DetailActivity.ID to item.id,
                DetailActivity.TYPE to item.category
            )
        )
    }

    private fun showEmptyView(toggle: Boolean) {
        rv_favorites.visibility = if (toggle) View.GONE else View.VISIBLE
        tv_empty_view.visibility = if (toggle) View.VISIBLE else View.GONE
    }
}
