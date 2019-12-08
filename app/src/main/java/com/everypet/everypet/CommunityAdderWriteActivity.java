package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;

public class CommunityAdderWriteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_adder_write);

        Intent intent = getIntent();
        String type = intent.getStringExtra("Type");
        Log.d("CommunityAdderWriteActivity", "타입 : " + type);

        ImageView animalTypeImageView = findViewById(R.id.image_view_community_adder_write_animal_type);
        TextView animalTypeTextView = findViewById(R.id.text_view_community_adder_write_animal_type);
        switch (type) {
            case "cat" :
                animalTypeImageView.setImageResource(R.drawable.cat);
                animalTypeTextView.setText("고양이");
                break;
            case "dog" :
                animalTypeImageView.setImageResource(R.drawable.dog);
                animalTypeTextView.setText("강아지");
                break;
            case "fish" :
                animalTypeImageView.setImageResource(R.drawable.fish);
                animalTypeTextView.setText("물고기");
                break;
            case "rabbit" :
                animalTypeImageView.setImageResource(R.drawable.rabbit);
                animalTypeTextView.setText("토끼");
                break;
            case "snake" :
                animalTypeImageView.setImageResource(R.drawable.snake);
                animalTypeTextView.setText("파충류");
                break;
            case "rat" :
                animalTypeImageView.setImageResource(R.drawable.rat);
                animalTypeTextView.setText("설치류");
                break;
            case "etc" :
                animalTypeTextView.setText("기타");
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
                        Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }
}
