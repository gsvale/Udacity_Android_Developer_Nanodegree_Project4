package com.example.udacity_android_developer_nanodegree_project4.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.objects.Ingredient;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.objects.Step;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String UTF8_TAG = "UTF-8";
    public static final String BAKING_APP_TAG = "BakingApp";

    // Json file name
    private static final String JSON_FILE_TAG = "baking_json.json";

    // RECIPE -> JSON TAGS
    private static final String ID_TAG = "id";
    private static final String NAME_TAG = "name";
    private static final String INGREDIENTS_TAG = "ingredients";
    private static final String STEPS_TAG = "steps";
    private static final String SERVINGS_TAG = "servings";
    private static final String IMAGE_TAG = "image";

    // INGREDIENT -> JSON TAGS
    private static final String INGREDIENT_TAG = "ingredient";
    private static final String QUANTITY_TAG = "quantity";
    private static final String MEASURE_TAG = "measure";

    // STEP -> JSON TAGS
    private static final String SHORT_DESCRIPTION_TAG = "shortDescription";
    private static final String DESCRIPTION_TAG = "description";
    private static final String VIDEO_URL_TAG = "videoURL";
    private static final String THUMBNAIL_TAG = "thumbnailURL";

    private static final String DATA_NOT_AVAILABLE = "Data not available";

    // Parse Json data and create Recipe item objects from that information
    static List<Recipe> extractRecipesDataFromJson(String jsonString) {

        // If Json string is empty or null, return null
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        List<Recipe> recipes = new ArrayList<>();

        try {


            // Create Recipes Json Array
            JSONArray recipesJsonArray = new JSONArray(jsonString);

            int recipesJsonArraySize = recipesJsonArray.length();

            // Parse all items of array results to create Recipe items
            for (int i = 0; i < recipesJsonArraySize; i++) {

                JSONObject recipeJsonObject = recipesJsonArray.getJSONObject(i);

                int id = -1;
                String name = null;
                List<Ingredient> ingredients = new ArrayList<>();
                List<Step> steps = new ArrayList<>();
                int servings = -1;
                String image = null;



                // If no data is received, display a default message DATA_NOT_AVAILABLE
                id = recipeJsonObject.optInt(ID_TAG, -1);
                name = recipeJsonObject.optString(NAME_TAG, DATA_NOT_AVAILABLE);

                JSONArray ingredientsArray = recipeJsonObject.getJSONArray(INGREDIENTS_TAG);
                int ingredientsArraySize = ingredientsArray.length();

                for(int j = 0; j < ingredientsArraySize; j++){

                    JSONObject ingredientJsonObject = ingredientsArray.getJSONObject(j);

                    String ingredient = null;
                    Double quantity = null;
                    String measure = null;

                    ingredient = ingredientJsonObject.optString(INGREDIENT_TAG,DATA_NOT_AVAILABLE);
                    quantity = ingredientJsonObject.optDouble(QUANTITY_TAG, -1);
                    measure = ingredientJsonObject.optString(MEASURE_TAG,DATA_NOT_AVAILABLE);

                    ingredients.add(new Ingredient(ingredient, quantity, measure));
                }

                JSONArray stepsArray = recipeJsonObject.getJSONArray(STEPS_TAG);
                int stepsArraySize = stepsArray.length();

                for(int k = 0; k < stepsArraySize; k++){

                    JSONObject stepJsonObject = stepsArray.getJSONObject(k);

                    int stepId = -1;
                    String shortDescription = null;
                    String description = null;
                    String videoURL = null;
                    String thumbnailURLURL = null;

                    stepId = stepJsonObject.optInt(ID_TAG, -1);
                    shortDescription = stepJsonObject.optString(SHORT_DESCRIPTION_TAG, DATA_NOT_AVAILABLE);
                    description = stepJsonObject.optString(DESCRIPTION_TAG, DATA_NOT_AVAILABLE);
                    videoURL = stepJsonObject.optString(VIDEO_URL_TAG, DATA_NOT_AVAILABLE);
                    thumbnailURLURL = stepJsonObject.optString(THUMBNAIL_TAG, DATA_NOT_AVAILABLE);

                    steps.add(new Step(stepId, shortDescription, description, videoURL, thumbnailURLURL));
                }

                servings = recipeJsonObject.optInt(SERVINGS_TAG, -1);
                image = recipeJsonObject.optString(IMAGE_TAG, DATA_NOT_AVAILABLE);

                // Create Recipe item with values parsed and extracted from Json data
                Recipe recipeItem = new Recipe(id, name, ingredients, steps, servings, image);
                recipes.add(recipeItem);
            }

        } catch (JSONException e) {
            Log.e(HttpUtils.LOG_TAG, "Problem parsing the movies JSON results", e);
        }

        return recipes;

    }


}
