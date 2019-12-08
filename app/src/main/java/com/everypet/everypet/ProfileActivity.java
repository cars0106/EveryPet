package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileActivity extends BaseActivity {

    ImageButton pet1Kind;
    EditText name;
    EditText bday;
    EditText gender;
    EditText weight;
    EditText height;
    EditText symptom;
    EditText careful;
    long count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        setProfile();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name, kind, birthDay, gender, weight, height, symptom, careful from tb_pet order by _id asc limit 1", null);
        if(cursor != null)  count = DatabaseUtils.queryNumEntries(db,"tb_pet");

        int resourceId;

        for (int i=2; i<=count; i++){
            resourceId = getResources().getIdentifier("profile"+i, "id",getPackageName());
            (findViewById(resourceId)).setVisibility(View.VISIBLE);
        }

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);
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
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        finish();
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }

    // +버튼 클릭시 ProfileAdderChooseAnimalActivity로 이동
    public void onPlusBtn(View v) {
        Intent intent = new Intent(this, ProfileAdderChooseAnimalActivity.class);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(4).setChecked(true);
        }
    }

    @Override
    protected void onRestart() {
        setProfile();

        int resourceId;
        for (int i=2; i<=count; i++){
            resourceId = getResources().getIdentifier("profile"+i, "id",getPackageName());
            (findViewById(resourceId)).setVisibility(View.VISIBLE);
        }

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);
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
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        finish();
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
        super.onRestart();
    }
//     하려고 했던 것: 현재 액티비티에서 동물들 탭버튼 누르면 그 동물에 맞는 프로필 띄우기
//    public void onClickPrf(View v){
//        switch(v.getId()){
//            case (R.id.prf_pic1):
//                setProfile(1);
//                break;
//            case (R.id.prf_pic2):
//                setProfile(2);
//                break;
//            case (R.id.prf_pic3):
//                setProfile(3);
//                break;
//            case (R.id.prf_pic4):
//                setProfile(4);
//                break;
//            case (R.id.prf_pic5):
//                setProfile(5);
//                break;
//            case (R.id.prf_pic6):
//                setProfile(6);
//                break;
//            case (R.id.prf_pic7):
//                setProfile(7);
//                break;
//            case (R.id.prf_pic8):
//                setProfile(8);
//                break;
//        }
//    }

    public void setProfile(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        name = findViewById(R.id.prf_name);
        bday = findViewById(R.id.prf_bday);
        gender = findViewById(R.id.prf_gender);
        weight = findViewById(R.id.prf_weight);
        height = findViewById(R.id.prf_height);
        symptom = findViewById(R.id.prf_symptom);
        careful = findViewById(R.id.prf_careful);

        String kind;

        Cursor cursor = db.rawQuery("select name, kind, birthDay, gender, weight, height, symptom, careful from tb_pet order by _id asc limit 1", null);
        if(cursor != null) {
            count = DatabaseUtils.queryNumEntries(db,"tb_pet");
        }System.out.println("/////////////count값은\t"+count);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            //for(int i=1;i<count;i++) cursor.moveToNext(); 이 부분에서 자꾸 이상한 곳 가리키는 듯
            name.setText(cursor.getString(0));
            kind = cursor.getString(1);
            bday.setText(cursor.getString(2));
            gender.setText(cursor.getString(3));
            weight.setText(cursor.getString(4));
            height.setText(cursor.getString(5));
            symptom.setText(cursor.getString(6));
            careful.setText(cursor.getString(7));
            db.close();
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