package keita.`as`.lab3

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import keita.`as`.lab3.task2.FirstActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test


class Task2Test {
    @get:Rule
    val activityRule = ActivityScenarioRule(FirstActivity::class.java)
    private fun activityChecker(
        butExist1: Matcher<View>, butExist2: Matcher<View>,
        butNot1: Matcher<View>, butNot2: Matcher<View>,
        butNot3: Matcher<View>, isFirst: Boolean = false
    ) {

        onView(butExist1).check(matches(isDisplayed()))
        if (!isFirst) onView(butExist2).check(matches(isDisplayed()))
        else onView(butExist2).check(doesNotExist())


        onView(butNot1).check(doesNotExist())
        onView(butNot2).check(doesNotExist())
        onView(butNot3).check(doesNotExist())
        toAboutFromMenu()
    }

    private fun toAboutFromMenu() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(ViewMatchers.withText("About")).perform(ViewActions.click())
        pressBack()
    }

    @Test
    fun checkActivities() {

        val but1To2: Matcher<View> = withId(R.id.activity_first_toSecond_btn)
        val but2To1: Matcher<View> = withId(R.id.activity_second_toFirst_btn)
        val but2to3: Matcher<View> = withId(R.id.activity_second_toThird_btn)
        val but3To1: Matcher<View> = withId(R.id.activity_third_toFirst_btn)
        val but3To2: Matcher<View> = withId(R.id.activity_third_toSecond_btn)

        //check firstActivity with about
        activityChecker(but1To2, but2To1, but2to3, but3To1, but3To2, true)

        //check secondActivity with about
        onView(but1To2).perform(ViewActions.click())
        activityChecker(but2To1, but2to3, but3To1, but3To2, but1To2)

        //check thirdActivity with about
        onView(but2to3).perform(ViewActions.click())
        activityChecker(but3To1, but3To2, but1To2, but2To1, but2to3)

        /**  go back an check */
        //check secondActivity with about
        onView(but3To2).perform(ViewActions.click())
        activityChecker(but2To1, but2to3, but3To1, but3To2, but1To2)

        //check firstActivity with about
        onView(but2To1).perform(ViewActions.click())
        activityChecker(but1To2, but2To1, but2to3, but3To1, but3To2, true)

        /** let's check backStack and but3to1 */
        onView(but1To2).perform(ViewActions.click())
        onView(but2to3).perform(ViewActions.click())
        onView(but3To1).perform(ViewActions.click())
        activityChecker(but1To2, but2To1, but2to3, but3To1, but3To2, true)

        onView(but1To2).perform(ViewActions.click())
        onView(but2to3).perform(ViewActions.click())
        onView(but3To1).perform(ViewActions.click())

        onView(but1To2).perform(ViewActions.click())
        onView(but2to3).perform(ViewActions.click())
        onView(but3To1).perform(ViewActions.click())
        //pressBack()
    }
}