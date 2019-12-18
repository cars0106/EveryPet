package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.everypet.everypet.data.RecyclerData;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CommunityAdderWriteActivity extends BaseActivity implements View.OnClickListener {
    private final int REQUEST_CODE = 0;

    ImageView imageView;
    EditText editText;

    String type;

    Uri uri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    uri = data.getData();

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
        setContentView(R.layout.community_adder_write);

        Intent intent = getIntent();
        type = intent.getStringExtra("Type");
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

        Button addImageButton = findViewById(R.id.button_add_image_community_adder_write);
        addImageButton.setOnClickListener(this);

        Button addCommunityButton = findViewById(R.id.button_add_community_write);
        addCommunityButton.setOnClickListener(this);

        imageView = findViewById(R.id.image_view_community_adder);
        editText = findViewById(R.id.edit_text_pet_name);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                new AlertDialog.Builder(CommunityAdderWriteActivity.this)
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
                        Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_add_image_community_adder_write) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CommunityAdderWriteActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else {
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imageIntent, REQUEST_CODE);
            }
        }
        else if (view.getId() == R.id.button_add_community_write) {
            if (uri != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("업로드중...");
                progressDialog.show();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                final StorageReference riverRef = storageRef.child("image/" + uri.getLastPathSegment());

                riverRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                riverRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final Uri downloadUrl = uri;
                                        Log.d("CommunityAdderWriteActivity", "downloadUri" + downloadUrl);

                                        String petName = editText.getText().toString();
                                        writeNewPost(petName, type, downloadUrl.toString());
                                    }
                                });
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "업로드 완료", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                            }
                        });
            }
            else {
                Toast.makeText(getApplicationContext(), "이미지를 선택해주세요!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void writeNewPost(String petname, String type, String imageurl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        RecyclerData data = new RecyclerData(petname, type, imageurl);
        postValues = data.toMap();

        childUpdates.put("/photo_list/" + petname, postValues);
        databaseReference.updateChildren(childUpdates);
    }
}
