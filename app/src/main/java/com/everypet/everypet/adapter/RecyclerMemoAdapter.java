package com.everypet.everypet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everypet.everypet.R;
import com.everypet.everypet.data.MemoData;

import java.util.ArrayList;

public class RecyclerMemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class RecyclerMemoHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView dateTextView;

        public RecyclerMemoHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_memo_title);
            contentTextView = itemView.findViewById(R.id.text_view_memo_content);
            dateTextView = itemView.findViewById(R.id.text_view_memo_date);
        }
    }

    ArrayList<MemoData> memoDataArrayList;

    public RecyclerMemoAdapter(ArrayList<MemoData> memoDataArrayList) {
        this.memoDataArrayList = memoDataArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_memo, parent, false);
        return new RecyclerMemoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerMemoHolder recyclerMemoHolder = (RecyclerMemoHolder) holder;
        recyclerMemoHolder.titleTextView.setText(memoDataArrayList.get(position).memoTitle);
        recyclerMemoHolder.contentTextView.setText(memoDataArrayList.get(position).memoContent);
        recyclerMemoHolder.dateTextView.setText(memoDataArrayList.get(position).date);
    }

    @Override
    public int getItemCount() {
        return memoDataArrayList.size();
    }
}
