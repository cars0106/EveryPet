package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.everypet.everypet.font.BaseActivity;

import java.io.ByteArrayOutputStream;

public class ProfileAdderChooseAnimalActivity extends BaseActivity {
    String kindStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_adder_choose_animal);
    }

    public void onClickKind(View v) {

        //버튼 아이디로 종류에 맞는 사진으로 세팅해야함 => ProfileAdderChooseAnimalActivity로 해당 종류 정보 전달 후 화면전환
        switch (v.getId()) {
            case (R.id.prf_cat): {
                kindStr = "고양이";
                break;
            }
            case (R.id.prf_dog): {
                kindStr = "강아지";
                break;
            }
            case (R.id.prf_fish): {
                kindStr = "물고기";
                break;
            }
            case (R.id.prf_rabbit): {
                kindStr = "토끼";
                break;
            }
            case (R.id.prf_snake): {
                kindStr = "파충류";
                break;
            }
            case (R.id.prf_rat): {
                kindStr = "설치류";
                break;
            }
            case (R.id.prf_etc): {
                kindStr = "기타";
                break;
            }
        }


        Intent intent = new Intent(this,ProfileAdderWriteActivity.class);
        intent.putExtra("string", kindStr);

        startActivityForResult(intent,124);
        finish();
    }
}