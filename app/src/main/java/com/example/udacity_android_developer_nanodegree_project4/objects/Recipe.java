package com.example.udacity_android_developer_nanodegree_project4.objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * The type Recipe.
 */
public class Recipe implements Parcelable {

    /**
     * The constant RECIPE_TAG.
     */
    public static final String RECIPE_TAG = Recipe.class.getSimpleName();

    private int mId;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private int mServings;
    private String mImage;


    /**
     * Instantiates a new Recipe.
     */
    public Recipe() {
    }

    /**
     * Instantiates a new Recipe.
     *
     * @param id          the id
     * @param name        the name
     * @param ingredients the ingredients
     * @param steps       the steps
     * @param servings    the servings
     * @param image       the image
     */
    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.mId = id;
        this.mName = name;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mServings = servings;
        this.mImage = image;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return mId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Gets ingredients.
     *
     * @return the ingredients
     */
    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    /**
     * Sets ingredients.
     *
     * @param ingredients the ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    /**
     * Gets steps.
     *
     * @return the steps
     */
    public List<Step> getSteps() {
        return mSteps;
    }

    /**
     * Sets steps.
     *
     * @param steps the steps
     */
    public void setSteps(List<Step> steps) {
        this.mSteps = steps;
    }

    /**
     * Gets servings.
     *
     * @return the servings
     */
    public int getServings() {
        return mServings;
    }

    /**
     * Sets servings.
     *
     * @param servings the servings
     */
    public void setServings(int servings) {
        this.mServings = servings;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        if(TextUtils.isEmpty(mImage)){
            return null;
        }
        return mImage;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.mImage = image;
    }

    /**
     * Instantiates a new Recipe.
     *
     * @param in the in
     */
    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        mSteps = in.createTypedArrayList(Step.CREATOR);
        mServings = in.readInt();
        mImage = in.readString();
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mSteps);
        dest.writeInt(mServings);
        dest.writeString(mImage);
    }
}
