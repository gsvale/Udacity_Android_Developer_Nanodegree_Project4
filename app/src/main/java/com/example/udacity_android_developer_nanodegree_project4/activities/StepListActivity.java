package com.example.udacity_android_developer_nanodegree_project4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.widget.Toast;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.adapters.StepListAdapter;
import com.example.udacity_android_developer_nanodegree_project4.databinding.ActivityStepListBinding;
import com.example.udacity_android_developer_nanodegree_project4.fragments.StepListFragment;
import com.example.udacity_android_developer_nanodegree_project4.fragments.ViewStepFragment;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.objects.Step;

public class StepListActivity extends AppCompatActivity implements StepListAdapter.OnStepSelectedListener {


    // Recipe Item
    private Recipe mRecipe;

    ActivityStepListBinding mBinding;

    private boolean isTablet = false;

    private StepListFragment stepListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if its tablet or phone, if tablet change orientation to landscape
        isTablet = getResources().getBoolean(R.bool.isTablet);

        // Get current screen orientation
        int orientation = getResources().getConfiguration().orientation;

        // If tablet and orientation portrait, force to use landscape
        if (isTablet && (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            return;
        }

        // Manage orientation on tablet device
        if(isTablet){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            OrientationEventListener listener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
                @Override
                public void onOrientationChanged(int orientation) {
                    if(orientation == 90){
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    }
                }
            };
            listener.canDetectOrientation();
            listener.enable();
        }

        // Set DataBinding instance
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_list);

        // If intent is null, finish activity and show toast message
        Intent intent = getIntent();
        if (intent == null) {
            closeActivity();
            return;
        }

        // If recipe is null, finish activity and show toast message
        mRecipe = intent.getParcelableExtra(Recipe.RECIPE_TAG);
        if (mRecipe == null) {
            closeActivity();
            return;
        }

        // Set title as Recipe name
        setTitle(mRecipe.getName());

        // Create fragment if savedInstanceState ir fragment are null
        if (savedInstanceState == null
                || getSupportFragmentManager().findFragmentById(R.id.fragment_list_container_fl) == null) {

            // Create a new StepListFragment
            stepListFragment = StepListFragment.newInstance(mRecipe);

            // Get SupportFragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Add fragment to container in transaction
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_list_container_fl, stepListFragment)
                    .commit();
        }

    }


    // Method to close detail activity ans show error toast message
    private void closeActivity() {
        finish();
        Toast.makeText(this, R.string.recipe_data_error_message, Toast.LENGTH_SHORT).show();
    }

    // Method called when step item is clicked
    @Override
    public void onStepSelected(int position) {

        // If  tablet show in this activity ViewStepFragment on the right, or else init ViewStepActivity if phone being used
        if (isTablet) {
            // Create a new ViewStepFragment
            ViewStepFragment viewStepFragment = ViewStepFragment.newInstance(mRecipe, position);
            // Get SupportFragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            // Replace current fragment, with new fragment to container in transaction
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_detail_container_fl, viewStepFragment)
                    .commit();
        } else {
            Intent recipeIntent = new Intent(this, ViewStepActivity.class);
            recipeIntent.putExtra(Recipe.RECIPE_TAG, mRecipe);
            recipeIntent.putExtra(Step.STEP_TAG, position);
            startActivity(recipeIntent);
        }
    }


}
