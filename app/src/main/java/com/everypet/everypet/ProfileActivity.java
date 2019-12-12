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
import android.provider.Settings;
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

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileActivity extends BaseActivity {
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

        setBtnImg();
        setProfile(1);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name, kind, birthDay, gender, weight, height, symptom, careful from tb_pet order by _id asc", null);
        if(cursor != null)  count = DatabaseUtils.queryNumEntries(db,"tb_pet");
        db.close();

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
        setBtnImg();
        setProfile((int)count);

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
    public void onClickPrf(View v){
        switch(v.getId()){
            case (R.id.prf_pic1):
                setProfile(1);
                break;
            case (R.id.prf_pic2):
                setProfile(2);
                break;
            case (R.id.prf_pic3):
                setProfile(3);
                break;
            case (R.id.prf_pic4):
                setProfile(4);
                break;
            case (R.id.prf_pic5):
                setProfile(5);
                break;
            case (R.id.prf_pic6):
                setProfile(6);
                break;
            case (R.id.prf_pic7):
                setProfile(7);
                break;
            case (R.id.prf_pic8):
                setProfile(8);
                break;
        }
    }

    public void setProfile(int num){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        name = findViewById(R.id.prf_name);
        bday = findViewById(R.id.prf_bday);
        gender = findViewById(R.id.prf_gender);
        weight = findViewById(R.id.prf_weight);
        height = findViewById(R.id.prf_height);
        symptom = findViewById(R.id.prf_symptom);
        careful = findViewById(R.id.prf_careful);

        Cursor cursor = db.rawQuery("SELECT * FROM tb_pet order by _id asc", null);
//        if(cursor != null) {
//            count = DatabaseUtils.queryNumEntries(db,"tb_pet");//db에 있는 데이터 개수
//        }System.out.println("/////////////count값은\t"+count);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            for(int i=1;i<num;i++) cursor.moveToNext(); //이 부분에서 자꾸 이상한 곳 가리키는 듯
            name.setText(cursor.getString(1));
            bday.setText(cursor.getString(3));
            gender.setText(cursor.getString(4));
            weight.setText(cursor.getString(5));
            height.setText(cursor.getString(6));
            symptom.setText(cursor.getString(7));
            careful.setText(cursor.getString(8));
        }
        db.close();
    }

    public void setBtnImg(){
        int btnId;
        int btnText;
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_pet order by _id asc", null);
        if(cursor != null && cursor.getCount() != 0)
            cursor.moveToNext();
        System.out.println("커서아이디"+cursor.getInt(0));
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