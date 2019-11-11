package com.example.udacity_android_developer_nanodegree_project4.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.databinding.FragmentViewStepBinding;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.objects.Step;
import com.example.udacity_android_developer_nanodegree_project4.utils.JsonUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewStepFragment extends Fragment implements ExoPlayer.EventListener {

    // Recipe Item
    private Recipe mRecipe;

    private int mStepPosition;

    FragmentViewStepBinding mBinding;

    private OnViewStepButtonClickListener mListener;

    private SimpleExoPlayer mExoPlayer = null;

    private boolean isTablet = false;

    // View Step button listener click interface
    public interface OnViewStepButtonClickListener {
        void onButtonClickSelected(int position);
    }


    public static ViewStepFragment newInstance(Recipe recipe, int position) {
        Bundle args = new Bundle();
        args.putParcelable(Recipe.RECIPE_TAG, recipe);
        args.putInt(Step.STEP_TAG, position);
        ViewStepFragment f = new ViewStepFragment();
        f.setArguments(args);
        return f;
    }


    // Attach activity to fragment and set listener to view step button item click
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnViewStepButtonClickListener) {
            mListener = (OnViewStepButtonClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get from bundle recipe object and the position of the item selected
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(Recipe.RECIPE_TAG);
            mStepPosition = getArguments().getInt(Step.STEP_TAG);
        }

        // Check if its tablet or phone
        isTablet = getResources().getBoolean(R.bool.isTablet);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_step, container, false);

        // Hide/Show buttons depending of step position
        if(!isTablet) {

            if (mStepPosition == 0) {
                mBinding.previousStepBt.setVisibility(View.INVISIBLE);
                mBinding.nextStepBt.setVisibility(View.VISIBLE);
            } else if (mStepPosition == mRecipe.getSteps().size() - 1) {
                mBinding.previousStepBt.setVisibility(View.VISIBLE);
                mBinding.nextStepBt.setVisibility(View.INVISIBLE);
            } else {
                mBinding.previousStepBt.setVisibility(View.VISIBLE);
                mBinding.nextStepBt.setVisibility(View.VISIBLE);
            }

            // Set buttons click listeners

            mBinding.previousStepBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releasePlayer();
                    mListener.onButtonClickSelected(mStepPosition - 1);
                }
            });

            mBinding.nextStepBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releasePlayer();
                    mListener.onButtonClickSelected(mStepPosition + 1);
                }
            });

        }

        // Set description from step
        mBinding.stepDescriptionTv.setText(mRecipe.getSteps().get(mStepPosition).getDescription());

        // Get Step video url
        String stepUri = "";
        String videoUrl = mRecipe.getSteps().get(mStepPosition).getVideoURL();
        String thumbnailUrl = mRecipe.getSteps().get(mStepPosition).getThumbnailURL();

        mBinding.playerView.setVisibility(View.VISIBLE);
        mBinding.playerDefaultErrorIv.setVisibility(View.GONE);

        // Use videoIri, if null, use Thumbnail uri, else step uri is null
        if (!TextUtils.isEmpty(videoUrl)) {
            stepUri = videoUrl;
        } else if (!TextUtils.isEmpty(thumbnailUrl)) {
            stepUri = thumbnailUrl;
        }

        // Initialize the player.
        initPlayer(Uri.parse(stepUri));

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        releasePlayer();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // If tablet, just return
        if(isTablet){
           return;
        }

        // Checking the orientation of the screen
        // Set fullscreen player if landscape orientation, and portrait view if orientation portrait
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewGroup.LayoutParams params = mBinding.playerViewFl.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mBinding.playerViewFl.setLayoutParams(params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ViewGroup.LayoutParams params = mBinding.playerViewFl.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 0;
            mBinding.playerViewFl.setLayoutParams(params);
        }

    }

    private void initPlayer(Uri uri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mBinding.playerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), JsonUtils.BAKING_APP_TAG);
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    // Release ExoPlayer
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    // ExoPlayer listeners

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    }


    // If error loading video, show default image instead
    @Override
    public void onPlayerError(ExoPlaybackException error) {
        mBinding.playerView.setVisibility(View.GONE);
        mBinding.playerDefaultErrorIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPositionDiscontinuity() {
    }

}
