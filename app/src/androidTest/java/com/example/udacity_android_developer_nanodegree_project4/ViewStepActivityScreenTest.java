package com.example.udacity_android_developer_nanodegree_project4;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.udacity_android_developer_nanodegree_project4.activities.ViewStepActivity;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.objects.Step;
import com.example.udacity_android_developer_nanodegree_project4.utils.JsonUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ViewStepActivityScreenTest {

    private static final String STEP_INSTRUCTIONS = "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.";

    @Rule
    public ActivityTestRule<ViewStepActivity> mViewStepActivityTestRule = new ActivityTestRule<>(ViewStepActivity.class);

    @Before
    public void startActivity() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent viewStepIntent = new Intent(appContext, ViewStepActivity.class);
        viewStepIntent.putExtra(Recipe.RECIPE_TAG, JsonUtils.fetchAllRecipes(appContext).get(0));
        viewStepIntent.putExtra(Step.STEP_TAG, 2);
        mViewStepActivityTestRule.launchActivity(viewStepIntent);
    }

    @Test
    public void checkStepItem_ViewStepActivity() {

        // Check if player view is displayed
        onView(allOf(withId(R.id.player_view),isDisplayed()));

        // Check if recipe instructions are displayed, and have correct data
        onView(withId(R.id.step_description_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.step_description_tv)).check(matches(withText(STEP_INSTRUCTIONS)));

        // Check View step buttons are displayed and clickable
        onView(withId(R.id.previous_step_bt)).check(matches(isDisplayed()));
        onView(withId(R.id.next_step_bt)).check(matches(isDisplayed()));
        onView(withId(R.id.previous_step_bt)).perform(click());
        onView(withId(R.id.next_step_bt)).perform(click());

    }

}
