package com.example.test_food_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.test_food_recipe.Models.Recipe;
import com.google.gson.Gson;

public class SaveActivity extends AppCompatActivity {

    public static final String KEY_RECIPE = "KEY_RECIPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        getRecipe();

    }

    private void getRecipe() {

        SharedPreferences mPrefs = getSharedPreferences(KEY_RECIPE, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("recipe", "");
        Recipe recipe = gson.fromJson(json, Recipe.class);


        TextView title = findViewById(R.id.name);
        title.setText(recipe.title);
    }
}