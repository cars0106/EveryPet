package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
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
                        return true;
                    case R.id.diary:
                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivityForResult(diaryIntent, 100);
                        finish();
                        return true;
                    case R.id.community:
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        finish();
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

        ImageButton addButton = findViewById(R.id.button_add_memo);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MemoAdderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
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
