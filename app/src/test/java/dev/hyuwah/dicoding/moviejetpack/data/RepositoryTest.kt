package dev.hyuwah.dicoding.moviejetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.idling.CountingIdlingResource
import dev.hyuwah.dicoding.moviejetpack.data.helper.*
import dev.hyuwah.dicoding.moviejetpack.data.local.FavoritesDao
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

    @MockK
    lateinit var idlingResource: CountingIdlingResource

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = Repository(service, favoriteDao, idlingResource)
        every { idlingResource.increment() }.answers { }
        every { idlingResource.decrement() }.answers { }
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
        //Arrange
        val dataSource = DummyData.Favorite.movieNormalResponse().asMockDataSourceFactory()
        every { favoriteDao.getAllFavorite() }.returns(dataSource)

        //Act
        repository.getAllFavorite().testObserver()

        //Assert
        val expected = DummyData.Favorite.movieNormalResponse().asPagedList()
        verify { favoriteDao.getAllFavorite() }
        verify { repository.getAllFavorite().hasObservers() }
        assert(repository.getAllFavorite().getOrAwaitValue().size == expected.size)
    }

    @Test
    fun `Should successfully get favorite item by id`() {
        //Arrange
        val id = 1
        coEvery { favoriteDao.getFavoriteById(any()) }.returns(DummyData.Favorite.movieNormalResponse().first())

        runBlocking {
            //Act
            val result = repository.getFavoriteById(id)

            //Assert
            val expected = DummyData.Favorite.movieNormalResponse().first()
            verify { favoriteDao.getFavoriteById(any()) }
            assert(result != null)
            assert(result?.id == expected.id)
        }
    }

    @Test
    fun `Should successfully add item to favorite db`() {
        //Arrange
        val item = DummyData.Favorite.movieNormalResponse().first()
        coEvery { favoriteDao.insert(any()) }.answers { println("Favorite item inserted to db") }

        runBlocking {
            //Act
            repository.addFavorite(item)

            //Assert
            coVerify { favoriteDao.insert(any()) }
        }
    }

    @Test
    fun `Should successfully remove item from favorite db`() {
        //Arrange
        val id = 1
        coEvery { favoriteDao.delete(any()) }.answers { println("id:${args.first()} removed from db") }

        runBlocking {
            //Act
            repository.removeFavorite(id)

            //Assert
            coVerify { favoriteDao.delete(any()) }
        }
    }
}