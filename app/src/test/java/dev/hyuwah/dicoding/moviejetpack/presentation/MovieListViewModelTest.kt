package dev.hyuwah.dicoding.moviejetpack.presentation

import dev.hyuwah.dicoding.moviejetpack.data.DummySource
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.presentation.main.MovieListViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieListViewModelTest {

    @MockK
    private lateinit var repository: Repository

    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should success get all movie & first item is valid`() {
        every { repository.getMovies() }.returns(DummySource.movieList)
        viewModel = MovieListViewModel(repository)
        val result = viewModel.movies
        assert(result.isNotEmpty())
        assertEquals(DummySource.movieList.first(), result.first())
    }

    @Test
    fun `Should return empty movie list`() {
        every { repository.getMovies() }.returns(listOf())
        viewModel = MovieListViewModel(repository)
        val result = viewModel.movies
        assert(result.isEmpty())
    }
}