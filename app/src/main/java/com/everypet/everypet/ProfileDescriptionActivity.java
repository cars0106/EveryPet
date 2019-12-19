package com.everypet.everypet;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProfileDescriptionActivity extends Activity implements View.OnClickListener {
    Button modify;
    Button back;
    Bitmap img;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_description);

        name = getIntent().getStringExtra("name");
        System.out.println("이름: "+name);

        modify=findViewById(R.id.modify);
        back=findViewById(R.id.back);

        modify.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    db.execSQL("update tb_pet set picture = '"+BitmapToString(img)+"' where name = '"+name + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        db.close();
        finish();
    }

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.modify){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
        else finish();
    }
}