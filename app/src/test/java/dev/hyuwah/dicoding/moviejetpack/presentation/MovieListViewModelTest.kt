package dev.hyuwah.dicoding.moviejetpack.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.hyuwah.dicoding.moviejetpack.data.IRepository
import dev.hyuwah.dicoding.moviejetpack.data.helper.DummyData
import dev.hyuwah.dicoding.moviejetpack.presentation.main.MovieListViewModel
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

class MovieListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: IRepository

    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MovieListViewModel(repository)
    }

    @After
    fun tearDown() {
    }

    private fun createMockObserver() : Observer<Resource<List<MovieItem>>> = spyk(Observer {  })

    @Test
    fun `fetch movies happy flow empty`(){
        val mockObserver = createMockObserver()
        viewModel.state.observeForever(mockObserver)
        coEvery { repository.fetchDiscoverMovies() } returns listOf()

        // When
        viewModel.load()

        // Then
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(emptyList())) }

    }

    @Test
    fun `fetch movies happy flow`(){
        val mockObserver = createMockObserver()
        viewModel.state.observeForever(mockObserver)
        coEvery { repository.fetchDiscoverMovies() } returns DummyData.MovieList.normal()

        // When
        viewModel.load()

        // Then
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(DummyData.MovieList.normal())) }

    }
}