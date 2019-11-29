package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends BaseActivity {

    ImageButton pls;
    ImageButton pet1Kind;
    TextView pet1Name;
    EditText name;
    EditText bday;
    EditText gender;
    EditText weight;
    EditText height;
    EditText symptom;
    EditText careful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        pet1Kind = findViewById(R.id.prf_pic);
        pet1Name = findViewById(R.id.prf);
        name = findViewById(R.id.prf_name);
        bday = findViewById(R.id.prf_bday);
        gender = findViewById(R.id.prf_gender);
        weight = findViewById(R.id.prf_weight);
        height = findViewById(R.id.prf_height);
        symptom = findViewById(R.id.prf_symptom);
        careful = findViewById(R.id.prf_careful);

        SharedPreferences sp;
        sp=getSharedPreferences("profile", MODE_PRIVATE);
        String kind = sp.getString("종류","");
        pet1Name.setText(sp.getString("이름",""));
        name.setText(sp.getString("이름",""));
        bday.setText(sp.getString("생일",""));
        gender.setText(sp.getString("성별",""));
        weight.setText(sp.getString("체중",""));
        height.setText(sp.getString("신장",""));
        symptom.setText(sp.getString("질병",""));
        careful.setText(sp.getString("조심",""));

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
        pls = findViewById(R.id.btn_addProfile);

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
                        return true;
                    case R.id.memo:
                        Intent memoIntent = new Intent(getApplicationContext(), MemoActivity.class);
                        startActivityForResult(memoIntent, 100);
                        return true;
                    case R.id.diary:
                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivityForResult(diaryIntent, 100);
                        return true;
                    case R.id.community:
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }

    // +버튼 클릭시 ProfileAdderChooseAnimalActivity로 이동
    public void onPlusBtn(View v){
        Intent intent = new Intent(this, ProfileAdderChooseAnimalActivity.class);
        startActivityForResult(intent,123);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(4).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
