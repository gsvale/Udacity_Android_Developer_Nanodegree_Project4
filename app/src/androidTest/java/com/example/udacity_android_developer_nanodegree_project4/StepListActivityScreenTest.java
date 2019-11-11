package com.example.udacity_android_developer_nanodegree_project4;


import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.udacity_android_developer_nanodegree_project4.activities.StepListActivity;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.utils.JsonUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepListActivityScreenTest {

    private static final String RECIPE_STEP = "Recipe Ingredients:";

    @Rule
    public ActivityTestRule<StepListActivity> mStepListActivityTestRule = new ActivityTestRule<>(StepListActivity.class);

    @Before
    public void startActivity() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent recipeIntent = new Intent(appContext, StepListActivity.class);
        recipeIntent.putExtra(Recipe.RECIPE_TAG, JsonUtils.fetchAllRecipes(appContext).get(0));
        mStepListActivityTestRule.launchActivity(recipeIntent);
    }

    @Test
    public void checkRecyclerViewItem_StepListActivity() {

        // Check if steps view list is displayed
        onView(withId(R.id.steps_rv)).check(matches(isDisplayed()));

        // Check that there is a item with the step name
        onView(withId(R.id.steps_rv)).check(matches(hasDescendant(withText(RECIPE_STEP))));

        // Check if click listener is working on item
        onView(withId(R.id.steps_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

    }


}
