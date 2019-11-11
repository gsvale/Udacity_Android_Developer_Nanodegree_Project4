package com.example.udacity_android_developer_nanodegree_project4.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.JsonReader;
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


    // Method to fetch all recipes, with context as parameter
    public static List<Recipe> fetchAllRecipes(Context context){
        JsonReader reader;
        List<Recipe> recipes = new ArrayList<>();
        try {
            // Get JsonReader from Json file
            reader = readJSONFile(context);
            if(reader == null){
                return null;
            }
            reader.beginArray();
            while (reader.hasNext()) {
                recipes.add(readRecipeEntry(reader));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }


    // Method to get JsonReader file, to get data about recipes from assets , with context as parameter
    private static JsonReader readJSONFile(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        String uri = null;

        try {

            // Check assets for json file, or else show toast error

            String[] assetsList = assetManager.list("");

            if(assetsList == null || assetsList.length == 0){
                return null;
            }

            for (String asset : assetsList) {
                if (asset.equals(JSON_FILE_TAG)) {
                    uri = "asset:///" + asset;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, R.string.error_loading, Toast.LENGTH_LONG)
                    .show();
        }

        String userAgent = Util.getUserAgent(context, BAKING_APP_TAG);
        DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);

        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(inputStream, UTF8_TAG));
        } finally {
            Util.closeQuietly(dataSource);
        }

        return reader;
    }

    // Method to get json data to build Recipe items/objects, with JsonReader obtained
    private static Recipe readRecipeEntry(JsonReader reader) {

        int id = -1;
        String name = null;
        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        int servings = -1;
        String image = null;

        // Get recipe from reader data
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String recipeItem = reader.nextName();
                switch (recipeItem) {
                    case ID_TAG:
                        id = reader.nextInt();
                        break;
                    case NAME_TAG:
                        name = reader.nextString();
                        break;
                    case INGREDIENTS_TAG:
                        reader.beginArray();
                        while (reader.hasNext()) {
                            ingredients.add(readIngredientEntry(reader));
                        }
                        reader.endArray();
                        break;
                    case STEPS_TAG:
                        reader.beginArray();
                        while (reader.hasNext()) {
                            steps.add(readStepEntry(reader));
                        }
                        reader.endArray();
                        break;
                    case SERVINGS_TAG:
                        servings = reader.nextInt();
                        break;
                    case IMAGE_TAG:
                        image = reader.nextString();
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return new recipe object with data extracted
        return new Recipe(id, name , ingredients, steps, servings, image);
    }

    // Method to read Json reader from Ingredients data, and return a Ingredient object
    private static Ingredient readIngredientEntry(JsonReader reader) {

        String ingredient = null;
        Double quantity = null;
        String measure = null;

        // Get ingredient from reader data
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String ingredientItem = reader.nextName();
                switch (ingredientItem) {
                    case INGREDIENT_TAG:
                        ingredient = reader.nextString();
                        break;
                    case QUANTITY_TAG:
                        quantity = reader.nextDouble();
                        break;
                    case MEASURE_TAG:
                        measure = reader.nextString();
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return new ingredient object with data extracted
        return new Ingredient(ingredient, quantity, measure);
    }

    // Method to read Json reader from Steps data, and return a Step object
    private static Step readStepEntry(JsonReader reader) {

        int id = -1;
        String shortDescription = null;
        String description = null;
        String videoURL = null;
        String thumbnailURLURL = null;

        // Get step from reader data
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String stepItem = reader.nextName();
                switch (stepItem) {
                    case ID_TAG:
                        id = reader.nextInt();
                        break;
                    case SHORT_DESCRIPTION_TAG:
                        shortDescription = reader.nextString();
                        break;
                    case DESCRIPTION_TAG:
                        description = reader.nextString();
                        break;
                    case VIDEO_URL_TAG:
                        videoURL = reader.nextString();
                        break;
                    case THUMBNAIL_TAG:
                        thumbnailURLURL = reader.nextString();
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return new step object with data extracted
        return new Step(id, shortDescription, description, videoURL , thumbnailURLURL);
    }

}
