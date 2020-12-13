package keita.`as`.lab3

import android.content.pm.ActivityInfo
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test


class MyActivityTest {
    @get:Rule
    val mActivityRule = ActivityScenarioRule(MyActivity::class.java)
    private val but1To2: Matcher<View> =  withId(R.id.activity_main_btn)

    @Test
    fun mainTest() {

        onView(but1To2).check(matches(withText("Let's play")))

        onView(withId(R.id.activity_main_text_input)).perform(typeText("Jack"), closeSoftKeyboard())
        onView(withId(R.id.activity_main_text_input)).check(matches(withText("Jack")))

        onView(withId(R.id.activity_main_btn)).perform(click())
        onView(withId(R.id.activity_main_btn)).check(matches(withText(R.string.won)))

        mActivityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onView(withId(R.id.activity_main_btn)).check(matches(withText("Let's play")))
        onView(withId(R.id.activity_main_text_input)).check(matches(withText("Jack")))

    }
}