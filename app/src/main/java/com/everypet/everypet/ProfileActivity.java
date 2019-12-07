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

    LinearLayout prfBtnSets;
    ImageButton pet1Kind;
    TextView pet1Name;
    EditText name;
    EditText bday;
    EditText gender;
    EditText weight;
    EditText height;
    EditText symptom;
    EditText careful;
    long count;

    LayoutInflater layoutInflater;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        setProfile();

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

    @Override
    protected void onRestart() {
        setProfile();
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

    public void setProfile(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        pet1Kind = findViewById(R.id.prf_pic);
        pet1Name = findViewById(R.id.prf);
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
            name.setText(cursor.getString(0));
            pet1Name.setText(cursor.getString(0));
            kind = cursor.getString(1);
            bday.setText(cursor.getString(2));
            gender.setText(cursor.getString(3));
            weight.setText(cursor.getString(4));
            height.setText(cursor.getString(5));
            symptom.setText(cursor.getString(6));
            careful.setText(cursor.getString(7));
            db.close();

            switch (kind) {
                case ("고양이"):
                    pet1Kind.setImageResource(R.drawable.cat);
                    break;
                case ("강아지"):
                    pet1Kind.setImageResource(R.drawable.dog);
                    break;
                case ("물고기"):
                    pet1Kind.setImageResource(R.drawable.fish);
                    break;
                case ("토끼"):
                    pet1Kind.setImageResource(R.drawable.rabbit);
                    break;
                case ("파충류"):
                    pet1Kind.setImageResource(R.drawable.snake);
                    break;
                case ("설치류"):
                    pet1Kind.setImageResource(R.drawable.rat);
                    break;
                case ("기타"):
                    pet1Kind.setImageResource(R.drawable.person);
                    break;
            }
        }
    }
}
