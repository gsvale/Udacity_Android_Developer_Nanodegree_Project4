package com.example.udacity_android_developer_nanodegree_project4.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Step.
 */
public class Step implements Parcelable {

    /**
     * The constant STEP_TAG.
     */
    public static final String STEP_TAG = Step.class.getSimpleName();

    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    /**
     * Instantiates a new Step.
     */
    public Step(){ }

    /**
     * Instantiates a new Step.
     *
     * @param id               the id
     * @param shortDescription the short description
     * @param description      the description
     * @param videoURL         the video url
     * @param thumbnailURL         the image url
     */
    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.mId = id;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoURL = videoURL;
        this.mThumbnailURL = thumbnailURL;
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
     * Gets short description.
     *
     * @return the short description
     */
    public String getShortDescription() {
        return mShortDescription;
    }

    /**
     * Sets short description.
     *
     * @param shortDescription the short description
     */
    public void setShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.mDescription = description;
    }

    /**
     * Gets video url.
     *
     * @return the video url
     */
    public String getVideoURL() {
        return mVideoURL;
    }

    /**
     * Sets video url.
     *
     * @param videoURL the video url
     */
    public void setVideoURL(String videoURL) {
        this.mVideoURL = videoURL;
    }

    /**
     * Gets image url.
     *
     * @return the image url
     */
    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    /**
     * Sets image url.
     *
     * @param thumbnailURL the image url
     */
    public void setImageURL(String thumbnailURL) {
        this.mThumbnailURL = thumbnailURL;
    }

    /**
     * Instantiates a new Step.
     *
     * @param in the in
     */
    protected Step(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoURL = in.readString();
        mThumbnailURL = in.readString();
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoURL);
        dest.writeString(mThumbnailURL);
    }
}
