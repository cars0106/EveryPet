package com.everypet.everypet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everypet.everypet.R;
import com.everypet.everypet.RecyclerInfo;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.community_custom_image_view);
            textView = itemView.findViewById(R.id.community_text_view);
        }
    }

    private ArrayList<RecyclerInfo> recyclerInfoArrayList;

    public RecyclerAdapter(ArrayList<RecyclerInfo> recyclerInfoArrayList) {
        this.recyclerInfoArrayList = recyclerInfoArrayList;
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
        recyclerViewHolder.imageView.setImageResource(recyclerInfoArrayList.get(position).drawableId);
        recyclerViewHolder.textView.setText(recyclerInfoArrayList.get(position).petName);
    }

    @Override
    public int getItemCount() {
        return recyclerInfoArrayList.size();
    }
}
