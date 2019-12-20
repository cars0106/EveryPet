package com.everypet.everypet.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everypet.everypet.R;
import com.everypet.everypet.data.ToDoData;

import java.util.ArrayList;

public class RecyclerToDoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class RecyclerToDoHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView timeTextView;
        TextView toDoTextView;
        CheckBox checkBox;

        public RecyclerToDoHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.image_view_to_do);
            timeTextView = itemView.findViewById(R.id.text_view_to_do_time);
            toDoTextView = itemView.findViewById(R.id.text_view_to_do);
            checkBox = itemView.findViewById(R.id.checkbox_to_do);
        }
    }

    ArrayList<ToDoData> toDoDataArrayList;

    public RecyclerToDoAdapter(ArrayList<ToDoData> toDoDataArrayList) {
        this.toDoDataArrayList = toDoDataArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_to_do, parent, false);
        return new RecyclerToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerToDoHolder toDoHolder = (RecyclerToDoHolder) holder;
        toDoHolder.timeTextView.setText(toDoDataArrayList.get(position).toDoTime);
        toDoHolder.toDoTextView.setText(toDoDataArrayList.get(position).toDoWhat);
        if (toDoDataArrayList.get(position).toDoPetType.equals("cat")) {
            toDoHolder.iconImageView.setImageResource(R.drawable.cat);
        }
        else if (toDoDataArrayList.get(position).toDoPetType.equals("dog")) {
            toDoHolder.iconImageView.setImageResource(R.drawable.dog);
        }
        else if (toDoDataArrayList.get(position).toDoPetType.equals("fish")) {
            toDoHolder.iconImageView.setImageResource(R.drawable.fish);
        }
        else if (toDoDataArrayList.get(position).toDoPetType.equals("rabbit")) {
            toDoHolder.iconImageView.setImageResource(R.drawable.rabbit);
        }
        else if (toDoDataArrayList.get(position).toDoPetType.equals("rat")) {
            toDoHolder.iconImageView.setImageResource(R.drawable.rat);
        }
        else if (toDoDataArrayList.get(position).toDoPetType.equals("snake")) {
            toDoHolder.iconImageView.setImageResource(R.drawable.snake);
        }
        toDoHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    toDoHolder.toDoTextView.setPaintFlags(toDoHolder.toDoTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    toDoHolder.timeTextView.setPaintFlags(toDoHolder.timeTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    toDoHolder.toDoTextView.setPaintFlags(0);
                    toDoHolder.timeTextView.setPaintFlags(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoDataArrayList.size();
    }
}
