package dev.hyuwah.dicoding.moviejetpack.presentation

import dev.hyuwah.dicoding.moviejetpack.data.DummySource
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.presentation.main.TvShowListViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TvShowListViewModelTest {

    @MockK
    private lateinit var repository: Repository

    private lateinit var viewModel: TvShowListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should success get all tv show & first item is valid`() {
        every { repository.getTvShows() }.returns(DummySource.tvShowList)
        viewModel = TvShowListViewModel(repository)
        val result = viewModel.tvShows
        assert(result.isNotEmpty())
        assertEquals(DummySource.tvShowList.first(), result.first())
    }

    @Test
    fun `Should return empty tv show list`() {
        every { repository.getTvShows() }.returns(listOf())
        viewModel = TvShowListViewModel(repository)
        val result = viewModel.tvShows
        assert(result.isEmpty())
    }
}