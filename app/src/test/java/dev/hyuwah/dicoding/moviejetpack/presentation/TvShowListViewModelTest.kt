package dev.hyuwah.dicoding.moviejetpack.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.data.helper.DummyData
import dev.hyuwah.dicoding.moviejetpack.presentation.main.TvShowListViewModel
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvShowListViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: Repository

    private lateinit var viewModel: TvShowListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TvShowListViewModel(repository)
    }

    @After
    fun tearDown() {
    }

    private fun createMockObserver() : Observer<Resource<List<MovieItem>>> = spyk(Observer {  })

    @Test
    fun `fetch tv show happy flow empty`(){
        val mockObserver = createMockObserver()
        viewModel.state.observeForever(mockObserver)
        coEvery { repository.fetchDiscoverTvShow() } returns listOf()

        // When
        viewModel.load()

        // Then
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(emptyList())) }

    }

    @Test
    fun `fetch tv show happy flow`(){
        val mockObserver = createMockObserver()
        viewModel.state.observeForever(mockObserver)
        coEvery { repository.fetchDiscoverTvShow() } returns DummyData.MovieList.normal()

        // When
        viewModel.load()

        // Then
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(DummyData.MovieList.normal())) }

    }
}