package com.example.udacity_android_developer_nanodegree_project4.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Ingredient.
 */
public class Ingredient implements Parcelable {

    private String mIngredient;
    private Double mQuantity;
    private String mMeasure;

    /**
     * Instantiates a new Ingredient.
     */
    public Ingredient(){ }

    /**
     * Instantiates a new Ingredient.
     *
     * @param ingredient the ingredient
     * @param quantity   the quantity
     * @param measure    the measure
     */
    public Ingredient(String ingredient, Double quantity, String measure) {
        this.mIngredient = ingredient;
        this.mQuantity = quantity;
        this.mMeasure = measure;
    }

    /**
     * Gets ingredient.
     *
     * @return the ingredient
     */
    public String getIngredient() {
        return mIngredient;
    }

    /**
     * Sets ingredient.
     *
     * @param ingredient the ingredient
     */
    public void setIngredient(String ingredient) {
        this.mIngredient = ingredient;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Double getQuantity() {
        return mQuantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(Double quantity) {
        this.mQuantity = quantity;
    }

    /**
     * Gets measure.
     *
     * @return the measure
     */
    public String getMeasure() {
        return mMeasure;
    }

    /**
     * Sets measure.
     *
     * @param measure the measure
     */
    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    /**
     * Instantiates a new Ingredient.
     *
     * @param in the in
     */
    protected Ingredient(Parcel in) {
        mIngredient = in.readString();
        if (in.readByte() == 0) {
            mQuantity = null;
        } else {
            mQuantity = in.readDouble();
        }
        mMeasure = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mIngredient);
        if (mQuantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mQuantity);
        }
        dest.writeString(mMeasure);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

}
