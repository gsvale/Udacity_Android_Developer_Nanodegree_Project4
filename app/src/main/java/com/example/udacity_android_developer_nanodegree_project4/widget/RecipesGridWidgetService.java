package com.example.udacity_android_developer_nanodegree_project4.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.udacity_android_developer_nanodegree_project4.R;
import com.example.udacity_android_developer_nanodegree_project4.objects.Recipe;
import com.example.udacity_android_developer_nanodegree_project4.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class RecipesGridWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    // GridRemoteViewsFactory class that receives context as parameter
    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        List<Recipe> mRecipes;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {
        }

        //This method is called on start after notifyAppWidgetViewDataChanged
        @Override
        public void onDataSetChanged() {

            // Get recipes from Json file
            mRecipes = JsonUtils.fetchAllRecipes(mContext);
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if (null == mRecipes) return 0;
            return mRecipes.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            Recipe recipe = mRecipes.get(position);

            // Get view layout
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);

            // Show image from recipe with aid of Picasso library, or if error or no image, show default
            try {
                Bitmap imageBitmap = Picasso.get().load(recipe.getImage()).get();
                if (imageBitmap != null) {
                    views.setImageViewBitmap(R.id.recipe_image_iv, imageBitmap);
                } else {
                    views.setImageViewResource(R.id.recipe_image_iv, R.drawable.ic_recipe_icon);
                }
            } catch (IOException e) {
                e.printStackTrace();
                views.setImageViewResource(R.id.recipe_image_iv, R.drawable.ic_recipe_icon);
            }

            // Set recipe name
            views.setTextViewText(R.id.recipe_name_tv, recipe.getName());

            // Set ClickFillInIntent to go to StepListActivity with recipe item
            // Set click event when clicked on item view
            Bundle extras = new Bundle();
            extras.putParcelable(Recipe.RECIPE_TAG, recipe);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.recipe_ll, fillInIntent);

            return views;
        }


        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }

}
