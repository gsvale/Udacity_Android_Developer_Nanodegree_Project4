package com.example.udacity_android_developer_nanodegree_project4.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.Nullable;

import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.utils.ApiUtils;
import com.example.udacity_android_developer_nanodegree_project4.utils.HttpUtils;

import java.util.List;

public class RecipesLoader extends AsyncTaskLoader<List<Recipe>> {

    // Variable to save recipe data results
    private List<Recipe> mJsonRecipeData;

    public RecipesLoader(Context context) {
        super(context);
    }

    // First is executed OnStartLoading() that will call forceLoad() method which triggers the loadInBackground() method to execute
    @Override
    protected void onStartLoading() {

        // If there is cached data, deliver it, or else force load is called
        if (mJsonRecipeData != null) {
            deliverResult(mJsonRecipeData);
        } else {
            forceLoad();
        }
    }

    // Return cached data from loader
    @Override
    public void deliverResult(List<Recipe> recipes) {
        mJsonRecipeData = recipes;
        super.deliverResult(recipes);
    }


    // The background thread, that will fetch the Recipes data
    @Nullable
    @Override
    public List<Recipe> loadInBackground() {
        return fetchRecipesDataFromAPI();
    }

    private List<Recipe> fetchRecipesDataFromAPI() {
        return HttpUtils.fetchRecipesData(ApiUtils.API_URL);
    }

}
