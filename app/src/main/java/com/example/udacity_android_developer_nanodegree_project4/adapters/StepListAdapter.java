package com.example.udacity_android_developer_nanodegree_project4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.databinding.StepItemLayoutBinding;
import com.example.udacity_android_developer_nanodegree_project4.objects.Ingredient;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.objects.Step;

import java.text.DecimalFormat;
import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepListAdapterViewHolder> {

    private Context mContext;

    private Recipe mRecipe;

    private List<Step> mSteps;

    private OnStepSelectedListener mListener;

    // Step listener click interface
    public interface OnStepSelectedListener {
        void onStepSelected(int position);
    }

    // Constructor receive a clickHandler to be called in RecipesActivity
    public StepListAdapter(Context context, OnStepSelectedListener listener, Recipe recipe) {
        mContext = context;
        mListener = listener;
        mRecipe = recipe;
        mSteps = recipe.getSteps();
    }

    @NonNull
    @Override
    public StepListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        StepItemLayoutBinding binding =
                StepItemLayoutBinding.inflate(layoutInflater, parent, false);
        return new StepListAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepListAdapterViewHolder holder, int position) {

        // If first item, show list of recipe ingredients item, else show list of steps
        if (position == 0) {
            // DecimalFormat for Quantity values in ingredients items
            DecimalFormat format = new DecimalFormat();
            format.setDecimalSeparatorAlwaysShown(false);

            List<Ingredient> ingredients = mRecipe.getIngredients();
            holder.mBinding.recipeIngredientsValueTv.setText("");
            for (Ingredient ingredient : ingredients) {
                holder.mBinding.recipeIngredientsValueTv.append(ingredient.getIngredient() + "\n");
                holder.mBinding.recipeIngredientsValueTv.append(mContext.getString(R.string.quantity, format.format(ingredient.getQuantity())) + "\n");
                holder.mBinding.recipeIngredientsValueTv.append(mContext.getString(R.string.measure, ingredient.getMeasure()) + "\n");
                holder.mBinding.recipeIngredientsValueTv.append("\n\n");
            }
            holder.mBinding.recipeStepLl.setVisibility(View.GONE);
            holder.mBinding.recipeIngredientsLl.setVisibility(View.VISIBLE);
        } else {
            Step stepItem = mSteps.get(position - 1);
            holder.mBinding.recipeStepTv.setText(stepItem.getShortDescription());
            holder.mBinding.recipeIngredientsLl.setVisibility(View.GONE);
            holder.mBinding.recipeStepLl.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size() + 1;
    }

    public class StepListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        StepItemLayoutBinding mBinding;

        StepListAdapterViewHolder(StepItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Verify if click item is not ingredients view
            int position = getAdapterPosition();
            if (position != 0) {
                mListener.onStepSelected(position - 1);
            }
        }
    }

}
