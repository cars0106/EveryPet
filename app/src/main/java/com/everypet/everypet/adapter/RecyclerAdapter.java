package com.everypet.everypet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.everypet.everypet.R;
import com.everypet.everypet.data.RecyclerData;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView typeTextView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.community_custom_image_view);
            nameTextView = itemView.findViewById(R.id.community_text_view_name);
            typeTextView = itemView.findViewById(R.id.community_text_view_type);
        }
    }

    private ArrayList<RecyclerData> recyclerDataArrayList;

    public RecyclerAdapter(ArrayList<RecyclerData> recyclerDataArrayList) {
        this.recyclerDataArrayList = recyclerDataArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.nameTextView.setText(recyclerDataArrayList.get(position).petname);
        recyclerViewHolder.typeTextView.setText(recyclerDataArrayList.get(position).type);
        Glide.with(((RecyclerViewHolder) holder).imageView)
                .load(recyclerDataArrayList.get(position).imageurl)
                .into(((RecyclerViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return recyclerDataArrayList.size();
    }
}
