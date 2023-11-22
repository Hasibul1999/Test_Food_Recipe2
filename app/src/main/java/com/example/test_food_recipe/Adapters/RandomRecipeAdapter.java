package com.example.test_food_recipe.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_food_recipe.Listeners.RecipeClickListener;
import com.example.test_food_recipe.Models.Recipe;
import com.example.test_food_recipe.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {

    public static final String KEY_RECIPE = "KEY_RECIPE";
    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).title);
        holder.textView_title.setSelected(true);
        holder.textView_likes.setText(list.get(position).aggregateLikes+" Likes");
        holder.textView_servings.setText(list.get(position).servings+ " Servings");
        holder.textView_time.setText(list.get(position).readyInMinutes+ " Minutes");
        Picasso.get().load(list.get(position).image).into(holder.imageView_food);

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });

        holder.save_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecipe(position);
            }
        });
    }

    private void saveRecipe(int p) {

        Recipe saveRecipe = list.get(p);
        SharedPreferences mPrefs = context.getSharedPreferences(KEY_RECIPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(saveRecipe);

        Log.d("SAVE", "saveRecipe: " + json);
        prefsEditor.putString("recipe", json);
        prefsEditor.commit();

        Toast.makeText(context, "Recipe Saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView random_list_container;
    TextView textView_title,textView_servings,textView_likes,textView_time;
    ImageView imageView_food;
    LinearLayout save_layout;


    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_likes = itemView.findViewById(R.id.textView_likes);
        textView_time = itemView.findViewById(R.id.textView_time);
        imageView_food = itemView.findViewById(R.id.imageView_food);
        save_layout = itemView.findViewById(R.id.save);
    }
}