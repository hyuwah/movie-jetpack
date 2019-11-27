package dev.hyuwah.dicoding.moviejetpack.presentation.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import dev.hyuwah.dicoding.moviejetpack.R
import dev.hyuwah.dicoding.moviejetpack.data.Constant
import dev.hyuwah.dicoding.moviejetpack.presentation.adapter.DiscoverListAdapter
import dev.hyuwah.dicoding.moviejetpack.presentation.detail.DetailActivity
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import dev.hyuwah.dicoding.moviejetpack.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_tvshow_list.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowListFragment : Fragment(), DiscoverListAdapter.Interaction {

    private val viewModel  : TvShowListViewModel by viewModel()
    private lateinit var adapter: DiscoverListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshow_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DiscoverListAdapter(this)

        rv_tvshow_list.layoutManager = LinearLayoutManager(requireContext())
        rv_tvshow_list.adapter = adapter

        viewModel.state.observe(this, ::updateUI)
        if (savedInstanceState==null) viewModel.load()

        srl_tvshow_list.setOnRefreshListener { viewModel.load() }
    }

    private fun updateUI(resource: Resource<List<MovieItem>>){
        showNoInternetView(false)
        EspressoIdlingResource.increment()
        when(resource){
            is Resource.Loading -> {
                showLoading(true)
            }
            is Resource.Success -> {
                showLoading(false)
                adapter.submitList(resource.data)
                EspressoIdlingResource.decrement()
            }
            is Resource.Failure -> {
                showLoading(false)
                showNoInternetView(true)
                toast("Error ${resource.throwable.localizedMessage}")
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun showNoInternetView(toggle: Boolean){
        tv_no_internet.visibility = if(toggle) View.VISIBLE else View.GONE
    }

    private fun showLoading(toggle: Boolean){
        srl_tvshow_list.isRefreshing = toggle
    }

    override fun onItemSelected(position: Int, item: MovieItem) {
        startActivity(intentFor<DetailActivity>(
            DetailActivity.ID to item.id,
            DetailActivity.TYPE to Constant.TYPES.TVSHOW.name
        ))
    }

}
