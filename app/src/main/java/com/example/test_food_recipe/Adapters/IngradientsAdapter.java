package com.example.test_food_recipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_food_recipe.Models.ExtendedIngredient;
import com.example.test_food_recipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngradientsAdapter extends RecyclerView.Adapter<IngradientsViewHolder>{

    Context context;
    List<ExtendedIngredient> list;

    public IngradientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngradientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngradientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingradients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngradientsViewHolder holder, int position) {
        holder.textView_ingradients_name.setText(list.get(position).name);
        holder.textView_ingradients_name.setSelected(true);
        holder.textView_ingradients_quantity.setText(list.get(position).original);
        holder.textView_ingradients_quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.imageView_ingradients);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class IngradientsViewHolder extends RecyclerView.ViewHolder{

    TextView textView_ingradients_quantity,textView_ingradients_name;
    ImageView imageView_ingradients;
    public IngradientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_ingradients_quantity = itemView.findViewById(R.id.textView_ingradients_quantity);
        textView_ingradients_name = itemView.findViewById(R.id.textView_ingradients_name);
        imageView_ingradients = itemView.findViewById(R.id.imageView_ingradients);
    }
}
