package com.example.test_food_recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_food_recipe.Adapters.IngradientsAdapter;
import com.example.test_food_recipe.Adapters.SimilarRecipeAdapter;
import com.example.test_food_recipe.Listeners.RecipeClickListener;
import com.example.test_food_recipe.Listeners.RecipeDetailsListener;
import com.example.test_food_recipe.Listeners.SimilarRecipesListener;
import com.example.test_food_recipe.Models.RecipeDetailsResponse;
import com.example.test_food_recipe.Models.SimilarRecipeResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name,textView_meal_source,textView_meal_summary;
    ImageView ImageView_meal_image;
    RecyclerView recycler_meal_ingradients,recycler_meal_similar;
    RequestManager manager;
    ProgressDialog dialog;
    IngradientsAdapter ingradientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    ImageView imageMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imageMenu = findViewById(R.id.imageMenu1);

        toggle = new ActionBarDrawerToggle(RecipeDetailsActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.mHome:
                        startActivity(new Intent(RecipeDetailsActivity.this, MainActivity.class));
                        Toast.makeText(RecipeDetailsActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.mSave:
                        startActivity(new Intent(RecipeDetailsActivity.this, SaveActivity.class));
                        Toast.makeText(RecipeDetailsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.mLogout:
                        Toast.makeText(RecipeDetailsActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                        break;

                }

                return false;
            }
        });
        imageMenu = findViewById(R.id.imageMenu1);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code Here
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener,id);
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
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);

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

    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this,response,recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void OnRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this,RecipeDetailsActivity.class).putExtra("id",id));

        }
    };
}