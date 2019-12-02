package dev.hyuwah.dicoding.moviejetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import dev.hyuwah.dicoding.moviejetpack.data.helper.DummyData
import dev.hyuwah.dicoding.moviejetpack.data.helper.PagedListUtil
import dev.hyuwah.dicoding.moviejetpack.data.local.FavoritesDao
import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem
import dev.hyuwah.dicoding.moviejetpack.data.remote.TheMovieDbApiService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var service: TheMovieDbApiService

    @MockK
    private lateinit var favoriteDao: FavoritesDao

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = Repository(service, favoriteDao)
    }

    @Test
    fun `Should successfully get discover movies`() {
        val dummyData = DummyData.DiscoverMovie.normalResponse()
        coEvery { service.getDiscoverMovies(any(), any(), any()) }
            .returns(dummyData)

        runBlocking {
            val result = repository.fetchDiscoverMovies()
            assert(result.size == 1)
            assert(result.first().title == dummyData.results.first().title)
        }
    }

    @Test
    fun `Should successfully get discover tv show`() {
        val dummyData = DummyData.DiscoverTv.normalResponse()
        coEvery { service.getDiscoverTvShow(any(), any()) }
            .returns(dummyData)

        runBlocking {
            val result = repository.fetchDiscoverTvShow()
            assert(result.size == 1)
            assert(result.first().title == dummyData.results.first().name)
        }
    }

    @Test
    fun `Should successfully get tv show detail by id`() {
        val tvShowId = 101
        val dummyData = DummyData.Detail.tvShowNormalResponse()
        coEvery { service.getTvShowDetail(any()) }
            .returns(dummyData)

        runBlocking {
            val result = repository.fetchTvShowDetailById(tvShowId)
            assert(result.title == dummyData.name)
        }
    }

    @Test
    fun `Should successfully get movie detail by id`() {
        val movieId = 1
        val dummyData = DummyData.Detail.movieNormalResponse()
        coEvery { service.getMovieDetail(any()) }
            .returns(dummyData)

        runBlocking {
            val result = repository.fetchMovieDetailById(movieId)
            assert(result.title == dummyData.title)
        }
    }

    @Test
    fun `Should successfully get list of favorites`() {
        val dataSource = mockk<DataSource.Factory<Int, FavoriteItem>>()
        val mockObserver = spyk<Observer<PagedList<FavoriteItem>>>(Observer { })
        every { favoriteDao.getAllFavorite() }.returns(dataSource)

        repository.getAllFavorite().testObserver()
        var result = PagedListUtil.mockPagedList(DummyData.MovieList.normal().map {
            FavoriteItem(
                it.id,
                it.title,
                it.posterUrl,
                it.backdropUrl,
                it.releaseDate,
                it.overview,
                it.voteAverage,
                it.voteCount,
                "MOVIE"
            )
        })

        verify { favoriteDao.getAllFavorite() }
        assert(DummyData.MovieList.normal().size == result.size)
    }

    fun `Should successfully get favorite item by id`() {

    }

    fun `Should successfully add item to favorite db`() {

    }

    fun `Should successfully remove item from favorite db`() {

    }
}

open class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also { observeForever(it) }