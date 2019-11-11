package com.example.udacity_android_developer_nanodegree_project4.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.adapters.RecipeListAdapter;
import com.example.udacity_android_developer_nanodegree_project4.databinding.ActivityRecipesBinding;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.utils.JsonUtils;

import java.util.List;

public class RecipesActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListAdapterClickHandler {

    // Number of items in grid per row
    private static final int GRID_LAYOUT_SPAN_COUNT = 3;

    private RecyclerView mRecipeListRecyclerView;
    private RecipeListAdapter mRecipeListAdapter;

    ActivityRecipesBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set DataBinding instance
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);

        // Check if its tablet or phone
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        // Get current screen orientation
        int orientation = getResources().getConfiguration().orientation;

        /* RecyclerView reference to set adapter to be used */
        mRecipeListRecyclerView = mBinding.recipesRv;

        // Set Recycler View layout manager
        LinearLayoutManager layoutManager;

        // If tablet and landscape, show recycler view in grid layout, or else use linear layout
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && isTablet) {
            layoutManager = new GridLayoutManager(this, GRID_LAYOUT_SPAN_COUNT);
        } else {
            // Set LinearLayoutManager for RecyclerView , and show two items per row
            layoutManager = new LinearLayoutManager(this);
        }

        // Set layout manager to recyclerView
        mRecipeListRecyclerView.setLayoutManager(layoutManager);

        // Create adapter with an empty list on start
        mRecipeListAdapter = new RecipeListAdapter(this, this);

        // Set adapter in RecyclerView
        mRecipeListRecyclerView.setAdapter(mRecipeListAdapter);

        // Fetch recipes data method call
        loadRecipesData();

    }

    // Method to retrieve recipes data from json file in assets
    private void loadRecipesData() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Recipe> recipes = JsonUtils.fetchAllRecipes(RecipesActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecipeListAdapter.setRecipesData(recipes);
                    }
                });
            }
        });
    }

    // This method handle click events on RecyclerView items, receiving a Recipe object
    @Override
    public void onClick(Recipe recipe) {
        Intent recipeIntent = new Intent(this, StepListActivity.class);
        recipeIntent.putExtra(Recipe.RECIPE_TAG, recipe);
        startActivity(recipeIntent);
    }
}
