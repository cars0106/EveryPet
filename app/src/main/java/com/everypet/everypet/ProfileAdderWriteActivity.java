package com.everypet.everypet;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.everypet.everypet.data.ProfileData;
import com.everypet.everypet.font.BaseActivity;

import java.io.InputStream;

import io.realm.Realm;

public class ProfileAdderWriteActivity extends BaseActivity implements View.OnClickListener {
    final int REQUEST_CODE = 100;

    String type;

    EditText nameEditText;
    EditText birthdayEditText;
    EditText genderEditText;
    EditText weightEditText;
    EditText heightEditText;
    EditText sickListEditText;
    EditText watchOutEditText;

    ImageView imageView;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_adder_write);

        Intent intent = getIntent();
        type = intent.getStringExtra("Type");
        Log.d("ProfileAdderWriteActivity", "타입 : " + type);

        ImageView typeImageView = findViewById(R.id.image_view_profile_type);
        TextView typeTextView = findViewById(R.id.text_view_profile_type);
        switch (type) {
            case "cat" :
                typeImageView.setImageResource(R.drawable.cat);
                typeTextView.setText("고양이");
                break;
            case "dog" :
                typeImageView.setImageResource(R.drawable.dog);
                typeTextView.setText("강아지");
                break;
            case "fish" :
                typeImageView.setImageResource(R.drawable.fish);
                typeTextView.setText("물고기");
                break;
            case "rabbit" :
                typeImageView.setImageResource(R.drawable.rabbit);
                typeTextView.setText("토끼");
                break;
            case "snake" :
                typeImageView.setImageResource(R.drawable.snake);
                typeTextView.setText("파충류");
                break;
            case "rat" :
                typeImageView.setImageResource(R.drawable.rat);
                typeTextView.setText("설치류");
                break;
            case "etc" :
                typeTextView.setText("기타");
                break;
        }

        imageView = findViewById(R.id.image_view_profile);

        nameEditText = findViewById(R.id.edit_text_profile_name);
        birthdayEditText = findViewById(R.id.edit_text_profile_date);
        genderEditText = findViewById(R.id.edit_text_profile_gender);
        weightEditText = findViewById(R.id.edit_text_profile_weight);
        heightEditText = findViewById(R.id.edit_text_profile_height);
        sickListEditText = findViewById(R.id.edit_text_profile_sick_list);
        watchOutEditText = findViewById(R.id.edit_text_profile_watch_out);

        Button imageButton = findViewById(R.id.button_profile_add_image);
        imageButton.setOnClickListener(this);
        Button savedButton = findViewById(R.id.button_save_pet);
        savedButton.setOnClickListener(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    imageUri = data.getData().toString();

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();

                    imageView.setImageBitmap(bitmap);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_profile_add_image) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileAdderWriteActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else {
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imageIntent, REQUEST_CODE);
            }
        }
        else if (view.getId() == R.id.button_save_pet) {
            final String name = nameEditText.getText().toString();
            final String birthday = birthdayEditText.getText().toString();
            final String gender = genderEditText.getText().toString();
            final double weight = Double.parseDouble(weightEditText.getText().toString());
            final double height = Double.parseDouble(heightEditText.getText().toString());
            final String sickList = sickListEditText.getText().toString();
            final String watchOut = watchOutEditText.getText().toString();

            if (weight != 0 && height != 0) {
                Realm.init(getApplicationContext());
                Realm mRealm = Realm.getDefaultInstance();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        ProfileData profileData = realm.createObject(ProfileData.class);
                        profileData.profileName = name;
                        profileData.profileBirthday = birthday;
                        profileData.profileGender = gender;
                        profileData.profileWeight = weight;
                        profileData.profileHeight = height;
                        profileData.profileSickList = sickList;
                        profileData.profileWatchOut = watchOut;
                        profileData.profileImageUri = imageUri;
                        profileData.profileType = type;
                    }
                });

                Toast.makeText(getApplicationContext(), "프로필 등록이 완료되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
            else if (weight <= 0) {
                Toast.makeText(getApplicationContext(), "체중을 입력해주세요!", Toast.LENGTH_LONG).show();
            }
            else if (imageUri == null) {
                Toast.makeText(getApplicationContext(), "체중을 입력해주세요!", Toast.LENGTH_LONG).show();
            }
            else if (height <= 0) {
                Toast.makeText(getApplicationContext(), "신장을 입력해주세요!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
}
