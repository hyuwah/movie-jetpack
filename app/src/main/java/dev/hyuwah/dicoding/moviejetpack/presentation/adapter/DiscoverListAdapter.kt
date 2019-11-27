package dev.hyuwah.dicoding.moviejetpack.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.load
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import kotlinx.android.synthetic.main.row_main_movies.view.*

class DiscoverListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {

        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_main_movies, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> { holder.bind(differ.currentList[position]) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<MovieItem>) {
        differ.submitList(list)
    }

    class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: MovieItem) = with(itemView) {
            cv_container.setOnClickListener { interaction?.onItemSelected(adapterPosition, movie) }
            tv_list_title.text = movie.title
            tv_list_title.isSelected = true
            iv_list_poster.load(movie.posterUrl, R.drawable.placeholder_poster_portrait)
            tv_list_rating.text = "${movie.voteAverage}"
            tv_list_genre.text = String.format(context.getString(R.string.count_voters), movie.voteCount)
            tv_list_release_date.text = movie.releaseDate
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: MovieItem)
    }
}