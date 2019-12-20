package com.everypet.everypet.adapter;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.everypet.everypet.R;
import com.everypet.everypet.data.DiaryData;

import java.io.InputStream;
import java.util.ArrayList;

public class RecyclerDiaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class RecyclerDiaryHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView dateTextView;
        ImageView imageView;

        public RecyclerDiaryHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.diary_title);
            contentTextView = itemView.findViewById(R.id.diary_content);
            dateTextView = itemView.findViewById(R.id.diary_date);
            imageView = itemView.findViewById(R.id.diary_custom_image_view);
        }
    }

    ArrayList<DiaryData> diaryDataArrayList;
    ContentResolver contentResolver;

    public RecyclerDiaryAdapter(ArrayList<DiaryData> diaryDataArrayList, ContentResolver contentResolver) {
        this.diaryDataArrayList = diaryDataArrayList;
        this.contentResolver = contentResolver;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_diary, parent, false);
        return new RecyclerDiaryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerDiaryHolder diaryHolder = (RecyclerDiaryHolder) holder;
        diaryHolder.titleTextView.setText(diaryDataArrayList.get(position).diaryTitle);
        diaryHolder.contentTextView.setText(diaryDataArrayList.get(position).diaryContent);
        diaryHolder.dateTextView.setText(diaryDataArrayList.get(position).diaryDate);
        try {
            InputStream inputStream = contentResolver.openInputStream(Uri.parse(diaryDataArrayList.get(position).diaryUri));

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            diaryHolder.imageView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return diaryDataArrayList.size();
    }
}
