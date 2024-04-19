package com.Muhaimen.i210888


import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.firebase.auth.FirebaseAuth
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestCase2 {

    private val countingIdlingResource = CountingIdlingResource("Authentication")

    private fun withItemCount(expectedCount: Int): ViewAssertion {
        return ViewAssertion { view, noViewFoundException ->
            if (view !is RecyclerView) {
                throw (noViewFoundException
                    ?: IllegalStateException("The view is not a RecyclerView"))
            }
            val adapter = view.adapter
            assertThat("RecyclerView item count", adapter?.itemCount, `is`(expectedCount))

        }
    }

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(1500)

        countingIdlingResource.increment();

        onView(withId(R.id.emailEditText))
            .perform(typeText("i210888@nu.edu.pk"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("asdf1234"), closeSoftKeyboard())

        onView(allOf(withId(R.id.loginBtn), withText("Login"), isDisplayed())).perform(click())

        countingIdlingResource.decrement()
    }



        @Test
        fun viewVisibilityTest() {
            // Wait for the activity to load
            Thread.sleep(10000)

            // Verify that the specific View in MainActivity10 is displayed
            onView(withId(R.id.searchResult)).check(matches(isDisplayed()))
        }

    @Test
    fun myProfileNameTest() {
        Thread.sleep(10000)
        ActivityScenario.launch(MainActivity21::class.java)
        Thread.sleep(2000)
        onView(withId(R.id.username)).check(matches(withText("muhaimen")))
    }
    @Test
    fun recyclerViewItemCountTest() {
        Thread.sleep(10000)
        // Assert that the RecyclerView has an item count of 1
        onView(withId(R.id.mentorRow)).check(withItemCount(1))
    }



    @After
    fun tearDown() {
        FirebaseAuth.getInstance().signOut()
    }


}