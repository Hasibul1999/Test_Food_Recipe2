package com.example.test_food_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_food_recipe.Adapters.IngradientsAdapter;
import com.example.test_food_recipe.Listeners.RecipeDetailsListener;
import com.example.test_food_recipe.Models.RecipeDetailsResponse;
import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name,textView_meal_source,textView_meal_summary;
    ImageView ImageView_meal_image;
    RecyclerView recycler_meal_ingradients;
    RequestManager manager;
    ProgressDialog dialog;
    IngradientsAdapter ingradientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        ImageView_meal_image = findViewById(R.id.ImageView_meal_image);
        recycler_meal_ingradients = findViewById(R.id.recycler_meal_ingradients);

    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(ImageView_meal_image);

            recycler_meal_ingradients.setHasFixedSize(true);
            recycler_meal_ingradients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false));
            ingradientsAdapter = new IngradientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recycler_meal_ingradients.setAdapter(ingradientsAdapter);
        }

        @Override
        public void didErorr(String message) {
            Toast.makeText(RecipeDetailsActivity.this,message, Toast.LENGTH_SHORT).show();
        }
    };
}