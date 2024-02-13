package com.Muhaimen.i210888

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class Testcase {

    @Test
    fun testLoginToHomeActivity() {
        // Launch the MainActivity
        ActivityScenario.launch(Main3Activity::class.java)

        // Perform a click on the button
        onView(withId(R.id.loginButton)).perform(click())

        // Check if SecondActivity is displayed
        onView(withId(R.id.notifications)).check(matches(isDisplayed()))
    }

    @Test
    fun NotifyNavigation() {
        // Launch the MainActivity
        ActivityScenario.launch(MainActivity8::class.java)

        // Perform a click on the button
        onView(withId(R.id.notifications)).perform(click())

        // Check if SecondActivity is displayed
        onView(withId(R.id.home)).check(matches(isDisplayed()))
    }
}