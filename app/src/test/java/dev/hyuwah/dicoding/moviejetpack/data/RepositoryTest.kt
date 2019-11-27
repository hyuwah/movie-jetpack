package dev.hyuwah.dicoding.moviejetpack.data

import dev.hyuwah.dicoding.moviejetpack.data.helper.DummyData
import dev.hyuwah.dicoding.moviejetpack.data.remote.TheMovieDbApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    @MockK
    private lateinit var service: TheMovieDbApiService

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = Repository(service)
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
}