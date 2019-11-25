package dev.hyuwah.dicoding.moviejetpack.presentation

import dev.hyuwah.dicoding.moviejetpack.data.DummySource
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.presentation.detail.DetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    @MockK
    private lateinit var repository: Repository

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun `Should successfully get movie by id`() {
        val movieId = 1
        every { repository.getMovieById(any()) }.returns(DummySource.movieList[0])
        val result = viewModel.getMovieById(movieId)
        assert(result != null)
        assertEquals(DummySource.movieList[0], result)
    }

    @Test
    fun `Should failed to get movie by id`() {
        val movieId = 1
        every { repository.getMovieById(any()) }.returns(null)
        val result = viewModel.getMovieById(movieId)
        assert(result == null)
    }

    @Test
    fun `Should successfully get tv show by id`() {
        val tvShowId = 101
        every { repository.getTvShowById(any()) }.returns(DummySource.tvShowList[0])
        val result = viewModel.getTvShowById(tvShowId)
        assert(result != null)
        assertEquals(DummySource.tvShowList[0], result)
    }

    @Test
    fun `Should failed to get tv show by id`() {
        val tvShowId = 101
        every { repository.getTvShowById(any()) }.returns(null)
        val result = viewModel.getTvShowById(tvShowId)
        assert(result == null)
    }
}