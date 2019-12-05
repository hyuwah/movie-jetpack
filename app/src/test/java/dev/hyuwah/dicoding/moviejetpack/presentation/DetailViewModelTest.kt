package dev.hyuwah.dicoding.moviejetpack.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.hyuwah.dicoding.moviejetpack.data.Constant
import dev.hyuwah.dicoding.moviejetpack.data.Repository
import dev.hyuwah.dicoding.moviejetpack.data.helper.DummyData
import dev.hyuwah.dicoding.moviejetpack.presentation.detail.DetailViewModel
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: Repository

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(repository)
        coEvery { repository.getFavoriteById(any()) }
            .returns(null)
    }

    private fun createMockObserver(): Observer<Resource<MovieItem>> = spyk(Observer { })

    @Test
    fun `Should successfully get movie by id`() {
        // Arrange
        val movieId = 1
        val mockObserver = createMockObserver()
        val dummyData = DummyData.MovieList.normal().first()
        coEvery { repository.fetchMovieDetailById(any()) }
            .returns(dummyData)

        // Act
        viewModel.state.observeForever(mockObserver)
        viewModel.load(Constant.TYPES.MOVIE.name, movieId)

        // Assert
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(dummyData)) }

    }

    @Test
    fun `Should failed to get movie by id`() {
        // Arrange
        val movieId = 1
        val mockObserver = createMockObserver()
        val dummyData = MovieItem()
        coEvery { repository.fetchMovieDetailById(any()) }
            .returns(dummyData)

        // Act
        viewModel.state.observeForever(mockObserver)
        viewModel.load(Constant.TYPES.MOVIE.name, movieId)

        // Assert
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(dummyData)) }

        val movieResult = (viewModel.state.value as Resource.Success).data
        assert(movieResult.title.isEmpty())
    }

    @Test
    fun `Should successfully get tv show by id`() {
        // Arrange
        val tvShowId = 101
        val mockObserver = createMockObserver()
        val dummyData = DummyData.MovieList.normal().first()
        coEvery { repository.fetchTvShowDetailById(any()) }
            .returns(dummyData)

        // Act
        viewModel.state.observeForever(mockObserver)
        viewModel.load(Constant.TYPES.TVSHOW.name, tvShowId)

        // Assert
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(dummyData)) }
    }

    @Test
    fun `Should failed to get tv show by id`() {
        // Arrange
        val tvShowId = 101
        val mockObserver = createMockObserver()
        val dummyData = MovieItem()
        coEvery { repository.fetchTvShowDetailById(any()) }
            .returns(dummyData)

        // Act
        viewModel.state.observeForever(mockObserver)
        viewModel.load(Constant.TYPES.TVSHOW.name, tvShowId)

        // Assert
        verify { mockObserver.onChanged(Resource.Loading) }
        verify { mockObserver.onChanged(Resource.Success(dummyData)) }
        val movieResult = (viewModel.state.value as Resource.Success).data
        assert(movieResult.title.isEmpty())
    }
}