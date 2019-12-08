package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;

import org.w3c.dom.Text;

public class ProfileAdderWriteActivity extends BaseActivity {

    ImageView kindImg;
    TextView kindName;

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
        setContentView(R.layout.profile_adder_write);

        kindImg = findViewById(R.id.prf_kindImg);
        kindName = findViewById(R.id.prf_kindStr);
        name = findViewById(R.id.prf_name);
        bday = findViewById(R.id.prf_bday);
        gender = findViewById(R.id.prf_gender);
        weight = findViewById(R.id.prf_weight);
        height = findViewById(R.id.prf_height);
        symptom = findViewById(R.id.prf_symptom);
        careful = findViewById(R.id.prf_careful);

        //Bundle extras=getIntent().getExtras();
        final Intent intent = getIntent();
        String s = intent.getStringExtra("string");
        kindName.setText(s);
        switch (s) {
            case ("고양이"):
                kindImg.setImageResource(R.drawable.cat);
                break;
            case ("강아지"):
                kindImg.setImageResource(R.drawable.dog);
                break;
            case ("물고기"):
                kindImg.setImageResource(R.drawable.fish);
                break;
            case ("토끼"):
                kindImg.setImageResource(R.drawable.rabbit);
                break;
            case ("파충류"):
                kindImg.setImageResource(R.drawable.snake);
                break;
            case ("설치류"):
                kindImg.setImageResource(R.drawable.rat);
                break;
            case ("기타"):
                kindImg.setImageResource(R.drawable.person);
                break;

        }
    }

    public void onClick(View v) {

        // 저장하기 버튼누르면 db에 프로필 정보가 저장되어야함
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into tb_pet (name, kind, birthDay, gender, weight, height, symptom, careful) values (?, ?, ?, ?, ?, ?, ?, ?)"
                , new String[]{name.getText().toString(), kindName.getText().toString(), bday.getText().toString(), gender.getText().toString(),
                        weight.getText().toString(), height.getText().toString(), symptom.getText().toString(), careful.getText().toString()});
        db.close();

        setResult(RESULT_OK);
        finish();
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
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }
}
