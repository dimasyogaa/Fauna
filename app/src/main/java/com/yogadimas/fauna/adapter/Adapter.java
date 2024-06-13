package com.yogadimas.fauna.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yogadimas.fauna.R;
import com.yogadimas.fauna.model.Animals;
import com.yogadimas.fauna.view.DetailActivity;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    final ArrayList<Animals> animalsArrayList;

    public Adapter(ArrayList<Animals> animalsArrayList) {
        this.animalsArrayList = animalsArrayList;
    }

    @NonNull
    @Override
    public Adapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_animal, parent, false);
        return new AdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter.AdapterViewHolder holder, int position) {
        Animals animals = animalsArrayList.get(position);
        holder.ivPhoto.setImageResource(animals.getPhoto());
        holder.tvName.setText(animals.getName());
        holder.tvDesc.setText(animals.getDesc());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.ID_ANIMAL, animals.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animalsArrayList.size();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPhoto;
        private final TextView tvName, tvDesc;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDesc = itemView.findViewById(R.id.tv_item_description);

        }
    }
}
