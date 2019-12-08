package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CommunityAdderChooseAnimalActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_adder_choose_animal);

        ImageView imageCat = findViewById(R.id.comu_cat);
        imageCat.setOnClickListener(this);
        ImageView imageDog = findViewById(R.id.comu_dog);
        imageDog.setOnClickListener(this);
        ImageView imageFish = findViewById(R.id.comu_fish);
        imageFish.setOnClickListener(this);
        ImageView imageRabbit = findViewById(R.id.comu_rabbit);
        imageRabbit.setOnClickListener(this);
        ImageView imageSnake = findViewById(R.id.comu_snake);
        imageSnake.setOnClickListener(this);
        ImageView imageRat = findViewById(R.id.comu_rat);
        imageRat.setOnClickListener(this);
        ImageView imageEtc = findViewById(R.id.comu_etc);
        imageEtc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), CommunityAdderWriteActivity.class);

        switch (view.getId()) {
            case R.id.comu_cat :
                intent.putExtra("Type", "cat");
                startActivity(intent);
                finish();
                break;
            case R.id.comu_dog :
                intent.putExtra("Type", "dog");
                startActivity(intent);
                finish();
                break;
            case R.id.comu_fish :
                intent.putExtra("Type", "fish");
                startActivity(intent);
                finish();
                break;
            case R.id.comu_rabbit :
                intent.putExtra("Type", "rabbit");
                startActivity(intent);
                finish();
                break;
            case R.id.comu_snake :
                intent.putExtra("Type", "snake");
                startActivity(intent);
                finish();
                break;
            case R.id.comu_rat :
                intent.putExtra("Type", "rat");
                startActivity(intent);
                finish();
                break;
            case R.id.comu_etc :
                intent.putExtra("Type", "etc");
                startActivity(intent);
                finish();
                break;
        }
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
