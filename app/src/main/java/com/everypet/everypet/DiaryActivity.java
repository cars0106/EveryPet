package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DiaryActivity extends BaseActivity {
    long count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        setBtnImg();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name, kind, birthDay, gender, weight, height, symptom, careful from tb_pet order by _id asc", null);
        if(cursor != null && cursor.getCount() != 0)  count = DatabaseUtils.queryNumEntries(db,"tb_pet");
        db.close();

        int resourceId;

        for (int i=2; i<=count; i++){
            resourceId = getResources().getIdentifier("profile"+i, "id",getPackageName());
            (findViewById(resourceId)).setVisibility(View.VISIBLE);
        }

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
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

        ImageButton addButton = findViewById(R.id.button_add_diary);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiaryAdderActivity.class);
                startActivityForResult(intent,110);
            }
        });
    }

    public void setBtnImg(){
        int btnId;
        int btnText;
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_pet order by _id asc", null);
        if(cursor != null && cursor.getCount() != 0)
            cursor.moveToNext();
        //System.out.println("커서아이디"+cursor.getInt(0));
        count = DatabaseUtils.queryNumEntries(db, "tb_pet");//db에 있는 데이터 개수
        //개수만큼 루프를 돌려야함 종류 string을 db에서 받아서 종류대로 버튼 이미지를 세팅해야함
        for(int i=1;i<=count;i++){
            btnId=getResources().getIdentifier("prf_pic"+i,"id",this.getPackageName());
            btnText=getResources().getIdentifier("prf"+i,"id",this.getPackageName());

            switch(cursor.getString(2)){
                case ("고양이"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.cat);
                    break;
                case ("강아지"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.dog);
                    break;
                case ("물고기"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.fish);
                    break;
                case ("토끼"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.rabbit);
                    break;
                case ("파충류"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.snake);
                    break;
                case ("설치류"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.rat);
                    break;
                case ("기타"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.person);
                    break;
            }
            ((TextView)findViewById(btnText)).setText(cursor.getString(1));
            cursor.moveToNext();
        }
        db.close();
    }

    public void onRestart(){
        setBtnImg();
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
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
