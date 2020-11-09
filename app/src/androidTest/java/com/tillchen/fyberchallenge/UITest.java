package com.tillchen.fyberchallenge;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tillchen.fyberchallenge.activities.MainActivity;
import com.tillchen.fyberchallenge.activities.OfferListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        return targetContext.getResources().getString(id);
    }

    @Test
    public void testTextFieldsArePrePopulated() {
        onView(withId(R.id.editTextAppId)).check(matches(withText(R.string.test_app_id)));
        onView(withId(R.id.editTextUserId)).check(matches(withText(R.string.test_user_id)));
        onView(withId(R.id.editTextSecurityToken))
                .check(matches(withText(R.string.test_security_token)));
    }

    @Test
    public void testEmptyAppId() {
        onView(withId(R.id.editTextAppId)).perform(clearText());
        onView(withId(R.id.buttonShowOffers)).perform(click());
        onView(withId(R.id.editTextAppId))
                .check(matches(hasErrorText(getResourceString(R.string.empty_app_id))));
    }

    @Test
    public void testEmptyUserId() {
        onView(withId(R.id.editTextUserId)).perform(clearText());
        onView(withId(R.id.buttonShowOffers)).perform(click());
        onView(withId(R.id.editTextUserId))
                .check(matches(hasErrorText(getResourceString(R.string.empty_user_id))));
    }

    @Test
    public void testEmptySecurityToken() {
        onView(withId(R.id.editTextSecurityToken)).perform(clearText());
        onView(withId(R.id.buttonShowOffers)).perform(click());
        onView(withId(R.id.editTextSecurityToken))
                .check(matches(hasErrorText(getResourceString(R.string.empty_security_token))));
    }

    @Test
    public void testShowOffers() {
        Intents.init();
        onView(withId(R.id.buttonShowOffers)).perform(click());
        // We can improve this with: https://developer.android.com/training/testing/espresso/idling-resource
        SystemClock.sleep(5000);
        intended(hasComponent(OfferListActivity.class.getName()));
    }

}