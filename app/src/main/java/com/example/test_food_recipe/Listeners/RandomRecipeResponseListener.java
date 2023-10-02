package com.example.test_food_recipe.Listeners;

import com.example.test_food_recipe.Models.RandomRecipeAPIResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeAPIResponse response, String message);
    void didError(String message);
}
