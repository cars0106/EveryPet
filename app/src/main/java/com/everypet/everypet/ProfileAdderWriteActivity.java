package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;

public class ProfileAdderWriteActivity extends BaseActivity {

    Button saveNew = new Button(this);
    ImageView kindImg = new ImageView(this);
    TextView kindName = new TextView(this);

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_adder_write);

        kindImg= findViewById(R.id.prf_kindImg);
        kindName= findViewById(R.id.prf_kindStr);

        Bundle extras=getIntent().getExtras();
        String s= extras.getString("string");
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        kindName.setText(s);
        kindImg.setImageBitmap(bitmap);

        sp=getSharedPreferences("profile", MODE_PRIVATE);
        saveNew = findViewById(R.id.save_newPet);

        saveNew.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                // 저장하기 버튼누르면 db에 프로필 정보가 저장되어야함
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("종류", String.valueOf(R.id.prf_kindStr));
                editor.putString("이름", String.valueOf(R.id.prf_name));
                editor.putString("생일", String.valueOf(R.id.prf_name));
                editor.putString("성별", String.valueOf(R.id.prf_name));
                editor.putString("체중", String.valueOf(R.id.prf_name));
                editor.putString("신장", String.valueOf(R.id.prf_name));
                editor.putString("질병", String.valueOf(R.id.prf_name));
                editor.putString("조심", String.valueOf(R.id.prf_name));
                editor.commit();

                //값 저장뒤에 ProfileActivity로 넘어가야함
                Intent intent = new Intent();
                ComponentName write2Profile = new ComponentName("this","this.ProfileActivity");
                intent.setComponent(write2Profile);
                startActivity(intent);
            }
        });
    }
}
