package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ToDoAdderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_adder);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                new AlertDialog.Builder(ToDoAdderActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("저장하지 않고 종료")
                        .setMessage("게시물을 만들지 않고 종료하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (menuItem.getItemId()) {
                                    case R.id.todo:
                                        Intent todoIntent = new Intent(getApplicationContext(), ToDoActivity.class);
                                        startActivityForResult(todoIntent, 100);
                                        finish();
                                        break;
                                    case R.id.memo:
                                        Intent memoIntent = new Intent(getApplicationContext(), MemoActivity.class);
                                        startActivityForResult(memoIntent, 100);
                                        finish();
                                        break;
                                    case R.id.diary:
                                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                                        startActivityForResult(diaryIntent, 100);
                                        finish();
                                        break;
                                    case R.id.community:
                                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                                        startActivityForResult(communityIntent, 100);
                                        finish();
                                        break;
                                    case R.id.profile:
                                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                                        startActivityForResult(profileIntent, 100);
                                        finish();
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("저장하지 않고 종료")
                .setMessage("게시물을 만들지 않고 종료하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }
}
