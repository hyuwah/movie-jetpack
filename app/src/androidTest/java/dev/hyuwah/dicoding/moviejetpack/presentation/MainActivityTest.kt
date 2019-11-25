package dev.hyuwah.dicoding.moviejetpack.presentation


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import dev.hyuwah.dicoding.moviejetpack.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        openMovieItemAndBack()
        onView(withId(R.id.vp_category)).perform(swipeLeft())
        openTvShowItemAndBack()
    }

    private fun openMovieItemAndBack() {
        val movieList =
            onView(withId(R.id.rv_movie_list))
        movieList.check(matches(isDisplayed()))
        movieList.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        pressBack()
    }

    private fun openTvShowItemAndBack() {
        val tvShowList = onView(withId(R.id.rv_tvshow_list))
        tvShowList.check(matches(isDisplayed()))
        tvShowList.perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)
        )
        Thread.sleep(300) // wait for scroll
        tvShowList.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        pressBack()
    }

}
