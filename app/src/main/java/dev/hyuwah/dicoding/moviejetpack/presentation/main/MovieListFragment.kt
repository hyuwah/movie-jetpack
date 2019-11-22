package dev.hyuwah.dicoding.moviejetpack.presentation.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.data.Constant
import dev.hyuwah.dicoding.moviejetpack.data.MovieModel
import dev.hyuwah.dicoding.moviejetpack.presentation.adapter.DiscoverListAdapter
import dev.hyuwah.dicoding.moviejetpack.presentation.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.jetbrains.anko.support.v4.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : Fragment(), DiscoverListAdapter.Interaction {

    private val viewModel  : MovieListViewModel by viewModel()
    private lateinit var adapter: DiscoverListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srl_movie_list.isEnabled = false
        adapter = DiscoverListAdapter(this)
        rv_movie_list.layoutManager = LinearLayoutManager(requireContext())
        rv_movie_list.adapter = adapter
        adapter.submitList(viewModel.movies)
    }


    override fun onItemSelected(position: Int, item: MovieModel) {
        startActivity(intentFor<DetailActivity>(
            DetailActivity.ID to item.id,
            DetailActivity.TYPE to Constant.TYPES.MOVIE.name
        ))
    }

}
