package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.everypet.everypet.data.DiaryData;
import com.everypet.everypet.data.ProfileData;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class DiaryAdderActivity extends BaseActivity implements View.OnClickListener {
    final int REQUEST_CODE = 100;

    ImageButton diaryCatImageButton;
    ImageButton diaryDogImageButton;
    ImageButton diaryFishImageButton;
    ImageButton diaryRabbitImageButton;
    ImageButton diaryRatImageButton;
    ImageButton diarySnakeImageButton;

    TextView diaryCatTextView;
    TextView diaryDogTextView;
    TextView diaryFishTextView;
    TextView diaryRabbitTextView;
    TextView diaryRatTextView;
    TextView diarySnakeTextView;

    ProfileData catData;
    ProfileData dogData;
    ProfileData fishData;
    ProfileData rabbitData;
    ProfileData ratData;
    ProfileData snakeData;

    String imageUri;
    String date;

    ImageView imageView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_adder);

        diaryCatImageButton = findViewById(R.id.diary_add_cat);
        diaryDogImageButton = findViewById(R.id.diary_add_dog);
        diaryFishImageButton = findViewById(R.id.diary_add_fish);
        diaryRabbitImageButton = findViewById(R.id.diary_add_rabbit);
        diaryRatImageButton = findViewById(R.id.diary_add_rat);
        diarySnakeImageButton = findViewById(R.id.diary_add_snake);

        diaryCatImageButton.setOnClickListener(this);
        diaryDogImageButton.setOnClickListener(this);
        diaryFishImageButton.setOnClickListener(this);
        diaryRabbitImageButton.setOnClickListener(this);
        diaryRatImageButton.setOnClickListener(this);
        diarySnakeImageButton.setOnClickListener(this);

        diaryCatImageButton.setVisibility(View.GONE);
        diaryDogImageButton.setVisibility(View.GONE);
        diaryFishImageButton.setVisibility(View.GONE);
        diaryRabbitImageButton.setVisibility(View.GONE);
        diaryRatImageButton.setVisibility(View.GONE);
        diarySnakeImageButton.setVisibility(View.GONE);

        diaryCatTextView = findViewById(R.id.text_view_diary_add_cat);
        diaryDogTextView = findViewById(R.id.text_view_diary_add_dog);
        diaryFishTextView = findViewById(R.id.text_view_diary_add_fish);
        diaryRabbitTextView = findViewById(R.id.text_view_diary_add_rabbit);
        diaryRatTextView = findViewById(R.id.text_view_diary_add_rat);
        diarySnakeTextView = findViewById(R.id.text_view_diary_add_snake);

        diaryCatTextView.setVisibility(View.GONE);
        diaryDogTextView.setVisibility(View.GONE);
        diaryFishTextView.setVisibility(View.GONE);
        diaryRabbitTextView.setVisibility(View.GONE);
        diaryRatTextView.setVisibility(View.GONE);
        diarySnakeTextView.setVisibility(View.GONE);

        getInitProfileFromRealm();

        Button imageButton = findViewById(R.id.button_diary_add_picture);
        imageButton.setOnClickListener(this);

        imageView = findViewById(R.id.image_view_diary_sample);

        Date currentTime = Calendar.getInstance().getTime();
        date = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                new AlertDialog.Builder(DiaryAdderActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("저장하지 않고 종료")
                        .setMessage("게시물을 만들지 않고 종료하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (menuItem.getItemId()) {
                                    case R.id.todo:
                                        Intent todoIntent = new Intent(getApplicationContext(), ToDoActivity.class);
                                        startActivityForResult(todoIntent, 100);
                                        finish();
                                        break;
                                    case R.id.memo:
                                        Intent memoIntent = new Intent(getApplicationContext(), MemoActivity.class);
                                        startActivityForResult(memoIntent, 100);
                                        finish();
                                        break;
                                    case R.id.diary:
                                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                                        startActivityForResult(diaryIntent, 100);
                                        finish();
                                        break;
                                    case R.id.community:
                                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                                        startActivityForResult(communityIntent, 100);
                                        finish();
                                        break;
                                    case R.id.profile:
                                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                                        startActivityForResult(profileIntent, 100);
                                        finish();
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
                return true;
            }
        });
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_diary_add_picture) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DiaryAdderActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else {
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imageIntent, REQUEST_CODE);
            }
        }
        else if (view.getId() == R.id.diary_add_cat) {
            EditText titleEditText = findViewById(R.id.edit_text_diary_title);
            EditText contentEditText = findViewById(R.id.edit_text_diary_content);

            final String title = titleEditText.getText().toString();
            final String content = contentEditText.getText().toString();

            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DiaryData data = realm.createObject(DiaryData.class);
                    data.petName = catData.profileName;
                    data.diaryUri = imageUri;
                    data.diaryTitle = title;
                    data.diaryContent = content;
                    data.diaryDate = date;
                }
            });

            Toast.makeText(getApplicationContext(), "일기 저장이 완료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (view.getId() == R.id.diary_add_dog) {
            EditText titleEditText = findViewById(R.id.edit_text_diary_title);
            EditText contentEditText = findViewById(R.id.edit_text_diary_content);

            final String title = titleEditText.getText().toString();
            final String content = contentEditText.getText().toString();

            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DiaryData data = realm.createObject(DiaryData.class);
                    data.petName = catData.profileName;
                    data.diaryUri = imageUri;
                    data.diaryTitle = title;
                    data.diaryContent = content;
                    data.diaryDate = date;
                }
            });

            Toast.makeText(getApplicationContext(), "일기 저장이 완료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (view.getId() == R.id.diary_add_fish) {
            EditText titleEditText = findViewById(R.id.edit_text_diary_title);
            EditText contentEditText = findViewById(R.id.edit_text_diary_content);

            final String title = titleEditText.getText().toString();
            final String content = contentEditText.getText().toString();

            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DiaryData data = realm.createObject(DiaryData.class);
                    data.petName = catData.profileName;
                    data.diaryUri = imageUri;
                    data.diaryTitle = title;
                    data.diaryContent = content;
                    data.diaryDate = date;
                }
            });

            Toast.makeText(getApplicationContext(), "일기 저장이 완료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (view.getId() == R.id.diary_add_rabbit) {
            EditText titleEditText = findViewById(R.id.edit_text_diary_title);
            EditText contentEditText = findViewById(R.id.edit_text_diary_content);

            final String title = titleEditText.getText().toString();
            final String content = contentEditText.getText().toString();

            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DiaryData data = realm.createObject(DiaryData.class);
                    data.petName = catData.profileName;
                    data.diaryUri = imageUri;
                    data.diaryTitle = title;
                    data.diaryContent = content;
                    data.diaryDate = date;
                }
            });

            Toast.makeText(getApplicationContext(), "일기 저장이 완료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (view.getId() == R.id.diary_add_rat) {
            EditText titleEditText = findViewById(R.id.edit_text_diary_title);
            EditText contentEditText = findViewById(R.id.edit_text_diary_content);

            final String title = titleEditText.getText().toString();
            final String content = contentEditText.getText().toString();

            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DiaryData data = realm.createObject(DiaryData.class);
                    data.petName = catData.profileName;
                    data.diaryUri = imageUri;
                    data.diaryTitle = title;
                    data.diaryContent = content;
                    data.diaryDate = date;
                }
            });

            Toast.makeText(getApplicationContext(), "일기 저장이 완료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (view.getId() == R.id.diary_add_snake) {
            EditText titleEditText = findViewById(R.id.edit_text_diary_title);
            EditText contentEditText = findViewById(R.id.edit_text_diary_content);

            final String title = titleEditText.getText().toString();
            final String content = contentEditText.getText().toString();

            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DiaryData data = realm.createObject(DiaryData.class);
                    data.petName = catData.profileName;
                    data.diaryUri = imageUri;
                    data.diaryTitle = title;
                    data.diaryContent = content;
                    data.diaryDate = date;
                }
            });

            Toast.makeText(getApplicationContext(), "일기 저장이 완료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void getInitProfileFromRealm() {
        Realm.init(getApplicationContext());
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<ProfileData> realmResults = mRealm.where(ProfileData.class).findAll();

        final ArrayList<ProfileData> profileDataArrayList = new ArrayList<>();
        profileDataArrayList.addAll(realmResults);

        for (int i = 0; i < profileDataArrayList.size(); i++) {
            ProfileData tmp = profileDataArrayList.get(i);
            if (tmp.profileType.equals("cat")) {
                catData = tmp;
                diaryCatImageButton.setVisibility(View.VISIBLE);
                diaryCatTextView.setVisibility(View.VISIBLE);
                diaryCatTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("dog")) {
                dogData = tmp;
                diaryDogImageButton.setVisibility(View.VISIBLE);
                diaryDogTextView.setVisibility(View.VISIBLE);
                diaryDogTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("fish")) {
                fishData = tmp;
                diaryFishImageButton.setVisibility(View.VISIBLE);
                diaryFishTextView.setVisibility(View.VISIBLE);
                diaryFishTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("rabbit")) {
                rabbitData = tmp;
                diaryRabbitImageButton.setVisibility(View.VISIBLE);
                diaryRabbitTextView.setVisibility(View.VISIBLE);
                diaryRabbitTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("rat")) {
                ratData = tmp;
                diaryRatImageButton.setVisibility(View.VISIBLE);
                diaryRatTextView.setVisibility(View.VISIBLE);
                diaryRatTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("snake")) {
                snakeData = tmp;
                diarySnakeImageButton.setVisibility(View.VISIBLE);
                diarySnakeTextView.setVisibility(View.VISIBLE);
                diarySnakeTextView.setText(tmp.profileName);
            }
        }
    }
}
