package com.example.udacity_android_developer_nanodegree_project4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.databinding.RecipeItemLayoutBinding;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder> {

    private Context mContext;

    private final RecipeListAdapterClickHandler mHandler;

    private List<Recipe> mRecipes;

    // Interface RecipeListAdapterClickHandler
    public interface RecipeListAdapterClickHandler {
        void onClick(Recipe recipe);
    }

    // Constructor receive a clickHandler to be called in RecipesActivity
    public RecipeListAdapter(Context context, RecipeListAdapterClickHandler handler) {
        mContext = context;
        mHandler = handler;
        mRecipes = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        RecipeItemLayoutBinding binding =
                RecipeItemLayoutBinding.inflate(layoutInflater, parent, false);
        return new RecipeListAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapterViewHolder holder, int position) {
        Recipe recipeItem = mRecipes.get(position);

        // Show image from recipe with aid of Picasso library
        Picasso.get()
                .load(recipeItem.getImage())
                .placeholder(R.drawable.ic_recipe_icon)
                .error(R.drawable.ic_recipe_icon)
                .into(holder.mBinding.recipeImageIv);

        // Set recipe name
        holder.mBinding.recipeNameTv.setText(recipeItem.getName());

        // Set recipe servings
        holder.mBinding.recipeServingsTv.setText(mContext.getString(R.string.servings, recipeItem.getServings()));
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    // Clear data, and set new data then notify that the data has changed
    public void setRecipesData(List<Recipe> recipesData) {
        mRecipes.clear();
        mRecipes.addAll(recipesData);
        notifyDataSetChanged();
    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeItemLayoutBinding mBinding;

        RecipeListAdapterViewHolder(RecipeItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        // Notify when item view is clicked
        @Override
        public void onClick(View v) {
            mHandler.onClick(mRecipes.get(getAdapterPosition()));
        }
    }

}
