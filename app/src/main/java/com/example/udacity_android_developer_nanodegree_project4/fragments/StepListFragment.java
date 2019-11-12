package com.example.udacity_android_developer_nanodegree_project4.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.adapters.StepListAdapter;
import com.example.udacity_android_developer_nanodegree_project4.databinding.FragmentStepListBinding;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.objects.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepListFragment extends Fragment {

    // Recipe Item
    private Recipe mRecipe;

    private RecyclerView mStepListRecyclerView;
    private StepListAdapter mStepListAdapter;

    FragmentStepListBinding mBinding;

    private StepListAdapter.OnStepSelectedListener mListener;


    public static StepListFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putParcelable(Recipe.RECIPE_TAG, recipe);
        StepListFragment f = new StepListFragment();
        f.setArguments(args);
        return f;
    }

    // Attach activity to fragment and set listener to step item click
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StepListAdapter.OnStepSelectedListener) {
            mListener = (StepListAdapter.OnStepSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get from bundle recipe object
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(Recipe.RECIPE_TAG);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_list, container, false);

        /* RecyclerView reference to set adapter to be used */
        mStepListRecyclerView = mBinding.stepsRv;

        // Set LinearLayoutManager for RecyclerView , and show two items per row
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        // Set layout manager to recyclerView
        mStepListRecyclerView.setLayoutManager(layoutManager);

        // Create adapter with an empty list on start
        mStepListAdapter = new StepListAdapter(getContext(), mListener, mRecipe);

        // Set adapter in RecyclerView
        mStepListRecyclerView.setAdapter(mStepListAdapter);

        return mBinding.getRoot();
    }

}
