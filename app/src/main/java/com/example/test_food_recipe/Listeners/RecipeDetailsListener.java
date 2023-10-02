package com.example.test_food_recipe.Listeners;

import com.example.test_food_recipe.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didErorr(String message);
}
