package com.tillchen.fyberchallengesample

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tillchen.fyberchallenge.activities.OfferListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private fun getResourceString(id: Int): String {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        return targetContext.resources.getString(id)
    }

    @Test
    fun testTextFieldsArePrePopulated() {
        Espresso.onView(withId(R.id.editTextAppId)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.test_app_id)))
        Espresso.onView(withId(R.id.editTextUserId)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.test_user_id)))
        Espresso.onView(withId(R.id.editTextSecurityToken))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.test_security_token)))
    }

    @Test
    fun testEmptyAppId() {
        Espresso.onView(withId(R.id.editTextAppId)).perform(ViewActions.clearText())
        Espresso.onView(withId(R.id.buttonShowOffers)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.editTextAppId))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(getResourceString(R.string.empty_app_id))))
    }

    @Test
    fun testEmptyUserId() {
        Espresso.onView(withId(R.id.editTextUserId)).perform(ViewActions.clearText())
        Espresso.onView(withId(R.id.buttonShowOffers)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.editTextUserId))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(getResourceString(R.string.empty_user_id))))
    }

    @Test
    fun testEmptySecurityToken() {
        Espresso.onView(withId(R.id.editTextSecurityToken)).perform(ViewActions.clearText())
        Espresso.onView(withId(R.id.buttonShowOffers)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.editTextSecurityToken))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(getResourceString(R.string.empty_security_token))))
    }

    @Test
    fun testShowOffers() {
        Intents.init()
        Espresso.onView(withId(R.id.buttonShowOffers)).perform(ViewActions.click())
        // We can improve this with: https://developer.android.com/training/testing/espresso/idling-resource
        SystemClock.sleep(5000)
        Intents.intended(IntentMatchers.hasComponent(OfferListActivity::class.java.name))
    }
}
