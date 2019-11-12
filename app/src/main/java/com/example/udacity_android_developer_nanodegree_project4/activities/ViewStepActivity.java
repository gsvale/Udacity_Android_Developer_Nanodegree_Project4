package com.example.udacity_android_developer_nanodegree_project4.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.databinding.ActivityViewStepBinding;
import com.example.udacity_android_developer_nanodegree_project4.fragments.ViewStepFragment;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.objects.Step;

import static com.example.udacity_android_developer_nanodegree_project4.activities.ViewStepActivity.*;

public class ViewStepActivity extends AppCompatActivity implements ViewStepFragment.OnViewStepButtonClickListener {


    // Recipe Item
    private Recipe mRecipe;

    ActivityViewStepBinding mBinding;

    private ViewStepFragment viewStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set DataBinding instance
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_step);

        // If intent is null, finish activity and show toast message
        Intent intent = getIntent();
        if (intent == null) {
            closeActivity();
            return;
        }

        // If recipe is null, finish activity and show toast message
        mRecipe = intent.getParcelableExtra(Recipe.RECIPE_TAG);
        int stepPosition = intent.getIntExtra(Step.STEP_TAG, 0);
        if (mRecipe == null) {
            closeActivity();
            return;
        }

        // Set title as Recipe name
        setTitle(mRecipe.getName());

        // Create fragment if savedInstanceState ir fragment are null
        if (savedInstanceState == null
                || getSupportFragmentManager().findFragmentById(R.id.fragment_detail_container_fl) == null) {

            // Create a new ViewStepFragment
            viewStepFragment = ViewStepFragment.newInstance(mRecipe, stepPosition);

            // Get SupportFragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Add fragment to container in transaction
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_detail_container_fl, viewStepFragment)
                    .commit();

        }


    }

    // Method to close detail activity ans show error toast message
    private void closeActivity() {
        finish();
        Toast.makeText(this, R.string.step_data_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClickSelected(int position) {

        // Create a new ViewStepFragment
        viewStepFragment = ViewStepFragment.newInstance(mRecipe, position);

        // Get SupportFragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Replace current fragment, with new fragment to container in transaction
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_detail_container_fl, viewStepFragment)
                .commit();

    }
}
