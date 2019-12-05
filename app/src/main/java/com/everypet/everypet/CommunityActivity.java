package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.everypet.everypet.adapter.RecyclerAdapter;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CommunityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        //Recycler View implementation
        RecyclerView recyclerView = findViewById(R.id.recycler_community);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(manager);

        ArrayList<RecyclerInfo> recyclerInfoArrayList = new ArrayList<>();
        recyclerInfoArrayList.add(new RecyclerInfo("1번", R.drawable.cat));
        recyclerInfoArrayList.add(new RecyclerInfo("2번", R.drawable.dog));
        recyclerInfoArrayList.add(new RecyclerInfo("3번", R.drawable.fish));
        recyclerInfoArrayList.add(new RecyclerInfo("4번", R.drawable.hedgehog));
        recyclerInfoArrayList.add(new RecyclerInfo("5번", R.drawable.person));
        recyclerInfoArrayList.add(new RecyclerInfo("6번", R.drawable.rabbit));
        recyclerInfoArrayList.add(new RecyclerInfo("7번", R.drawable.rat));
        recyclerInfoArrayList.add(new RecyclerInfo("8번", R.drawable.snake));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(recyclerInfoArrayList);
        recyclerView.setAdapter(recyclerAdapter);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.todo:
                        Intent todoIntent = new Intent(getApplicationContext(), ToDoActivity.class);
                        startActivityForResult(todoIntent, 100);
                        finish();
                        return true;
                    case R.id.memo:
                        Intent memoIntent = new Intent(getApplicationContext(), MemoActivity.class);
                        startActivityForResult(memoIntent, 100);
                        finish();
                        return true;
                    case R.id.diary:
                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivityForResult(diaryIntent, 100);
                        finish();
                        return true;
                    case R.id.community:
                        return true;
                    case R.id.profile:
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivityForResult(profileIntent, 100);
                        finish();
                        return true;
                }
                return false;
            }
        });

        ImageButton addButton = findViewById(R.id.button_add_community);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommunityAdderChooseAnimalActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}