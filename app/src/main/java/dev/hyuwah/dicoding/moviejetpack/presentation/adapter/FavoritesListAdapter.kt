package dev.hyuwah.dicoding.moviejetpack.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem
import dev.hyuwah.dicoding.moviejetpack.load
import dev.hyuwah.dicoding.moviejetpack.setVisible
import kotlinx.android.synthetic.main.row_main_movies.view.*

class FavoritesListAdapter(private val interaction: Interaction? = null) :
    PagedListAdapter<FavoriteItem, FavoritesListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteItem>() {

            override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_main_movies, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: FavoriteItem) = with(itemView) {
            cv_container.setOnClickListener { interaction?.onItemSelected(adapterPosition, movie) }
            tv_list_title.text = movie.title
            tv_list_title.isSelected = true
            iv_list_poster.load(movie.posterUrl, R.drawable.placeholder_poster_portrait)
            tv_list_rating.text = "${movie.voteAverage}"
            tv_list_genre.text =
                String.format(context.getString(R.string.count_voters), movie.voteCount)
            tv_list_release_date.text = movie.releaseDate

            tv_favorite_category.setVisible()
            tv_favorite_category.text = movie.category
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: FavoriteItem)
    }
}