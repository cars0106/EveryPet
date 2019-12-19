package com.everypet.everypet;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.everypet.everypet.data.ProfileData;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    ImageButton picCatImageButton;
    ImageButton picDogImageButton;
    ImageButton picFishImageButton;
    ImageButton picRabbitImageButton;
    ImageButton picRatImageButton;
    ImageButton picSnakeImageButton;

    TextView picCatTextView;
    TextView picDogTextView;
    TextView picFishTextView;
    TextView picRabbitTextView;
    TextView picRatTextView;
    TextView picSnakeTextView;

    ProfileData catData;
    ProfileData dogData;
    ProfileData fishData;
    ProfileData rabbitData;
    ProfileData ratData;
    ProfileData snakeData;

    ImageView imageView;

    Button signOutButton;

    @Override
    protected void onStart() {
        super.onStart();
        getInitProfileFromRealm();
        setUserInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        ImageButton addButton = findViewById(R.id.button_add_profile);
        addButton.setOnClickListener(this);

        picCatImageButton = findViewById(R.id.profile_cat);
        picDogImageButton = findViewById(R.id.profile_dog);
        picFishImageButton = findViewById(R.id.profile_fish);
        picRabbitImageButton = findViewById(R.id.profile_rabbit);
        picRatImageButton = findViewById(R.id.profile_rat);
        picSnakeImageButton = findViewById(R.id.profile_snake);

        picCatImageButton.setOnClickListener(this);
        picDogImageButton.setOnClickListener(this);
        picFishImageButton.setOnClickListener(this);
        picRabbitImageButton.setOnClickListener(this);
        picRatImageButton.setOnClickListener(this);
        picSnakeImageButton.setOnClickListener(this);

        picCatImageButton.setVisibility(View.GONE);
        picDogImageButton.setVisibility(View.GONE);
        picFishImageButton.setVisibility(View.GONE);
        picRabbitImageButton.setVisibility(View.GONE);
        picRatImageButton.setVisibility(View.GONE);
        picSnakeImageButton.setVisibility(View.GONE);

        picCatTextView = findViewById(R.id.text_view_profile_cat);
        picDogTextView = findViewById(R.id.text_view_profile_dog);
        picFishTextView = findViewById(R.id.text_view_profile_fish);
        picRabbitTextView = findViewById(R.id.text_view_profile_rabbit);
        picRatTextView = findViewById(R.id.text_view_profile_rat);
        picSnakeTextView = findViewById(R.id.text_view_profile_snake);

        picCatTextView.setVisibility(View.GONE);
        picDogTextView.setVisibility(View.GONE);
        picFishTextView.setVisibility(View.GONE);
        picRabbitTextView.setVisibility(View.GONE);
        picRatTextView.setVisibility(View.GONE);
        picSnakeTextView.setVisibility(View.GONE);

        imageView = findViewById(R.id.image_view_profile_pet);

        getInitProfileFromRealm();
        setUserInfo();

        signOutButton = findViewById(R.id.button_sign_out);
        signOutButton.setOnClickListener(this);

        ImageButton personImageButton = findViewById(R.id.image_button_profile_person);
        personImageButton.setOnClickListener(this);

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
                        finish();
                        return true;
                    case R.id.memo:
                        Intent memoIntent = new Intent(getApplicationContext(), MemoActivity.class);
                        startActivityForResult(memoIntent, 100);
                        finish();
                        return true;
                    case R.id.diary:
                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivityForResult(diaryIntent, 100);
                        finish();
                        return true;
                    case R.id.community:
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        finish();
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_add_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileAdderChooseAnimalActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.profile_cat) {
            signOutButton.setVisibility(View.GONE);
            applyProfile("cat");
        }
        else if (view.getId() == R.id.profile_dog) {
            signOutButton.setVisibility(View.GONE);
            applyProfile("dog");
        }
        else if (view.getId() == R.id.profile_fish) {
            signOutButton.setVisibility(View.GONE);
            applyProfile("fish");
        }
        else if (view.getId() == R.id.profile_rabbit) {
            signOutButton.setVisibility(View.GONE);
            applyProfile("rabbit");
        }
        else if (view.getId() == R.id.profile_rat) {
            signOutButton.setVisibility(View.GONE);
            applyProfile("rat");
        }
        else if (view.getId() == R.id.profile_snake) {
            signOutButton.setVisibility(View.GONE);
            applyProfile("snake");
        }
        else if (view.getId() == R.id.button_sign_out) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        }
        else if (view.getId() == R.id.image_button_profile_person) {
            setUserInfo();
            signOutButton.setVisibility(View.VISIBLE);
        }
    }

    private void getInitProfileFromRealm() {
        Realm.init(getApplicationContext());
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<ProfileData> realmResults = mRealm.where(ProfileData.class).findAll();

        final ArrayList<ProfileData> profileDataArrayList = new ArrayList<>();
        profileDataArrayList.addAll(realmResults);

        for(int i = 0; i < profileDataArrayList.size(); i++) {
            ProfileData tmp = profileDataArrayList.get(i);
            if (tmp.profileType.equals("cat")) {
                catData = tmp;
                picCatImageButton.setVisibility(View.VISIBLE);
                picCatTextView.setVisibility(View.VISIBLE);
                picCatTextView.setText(tmp.profileName);
            }
            else if (tmp.profileType.equals("dog")) {
                dogData = tmp;
                picDogImageButton.setVisibility(View.VISIBLE);
                picDogTextView.setVisibility(View.VISIBLE);
                picDogTextView.setText(tmp.profileName);
            }
            else if (tmp.profileType.equals("fish")) {
                fishData = tmp;
                picFishImageButton.setVisibility(View.VISIBLE);
                picFishTextView.setVisibility(View.VISIBLE);
                picFishTextView.setText(tmp.profileName);
            }
            else if (tmp.profileType.equals("rabbit")) {
                rabbitData = tmp;
                picRabbitImageButton.setVisibility(View.VISIBLE);
                picRabbitTextView.setVisibility(View.VISIBLE);
                picRabbitTextView.setText(tmp.profileName);
            }
            else if (tmp.profileType.equals("rat")) {
                ratData = tmp;
                picRatImageButton.setVisibility(View.VISIBLE);
                picRatTextView.setVisibility(View.VISIBLE);
                picRatTextView.setText(tmp.profileName);
            }
            else if (tmp.profileType.equals("snake")) {
                snakeData = tmp;
                picSnakeImageButton.setVisibility(View.VISIBLE);
                picSnakeTextView.setVisibility(View.VISIBLE);
                picSnakeTextView.setText(tmp.profileName);
            }
        }
    }

    private void applyProfile(String type) {
        if (type.equals("cat")) {
            TextView nameTextView = findViewById(R.id.prf_name);
            nameTextView.setText(catData.profileName);
            TextView birthdayTextView = findViewById(R.id.prf_bday);
            birthdayTextView.setText(catData.profileBirthday);
            TextView genderTextView = findViewById(R.id.prf_gender);
            genderTextView.setText(catData.profileGender);
            TextView weightTextView = findViewById(R.id.prf_weight);
            weightTextView.setText(catData.profileWeight + "");
            TextView heightTextView = findViewById(R.id.prf_height);
            heightTextView.setText(catData.profileHeight + "");
            TextView sickListTextView = findViewById(R.id.prf_symptom);
            sickListTextView.setText(catData.profileSickList);
            TextView watchOutTextView = findViewById(R.id.prf_careful);
            watchOutTextView.setText(catData.profileWatchOut);

            Uri imageUri = Uri.parse(catData.profileImageUri);
            imageView.setImageURI(imageUri);
        }
        else if (type.equals("dog")) {
            TextView nameTextView = findViewById(R.id.prf_name);
            nameTextView.setText(dogData.profileName);
            TextView birthdayTextView = findViewById(R.id.prf_bday);
            birthdayTextView.setText(dogData.profileBirthday);
            TextView genderTextView = findViewById(R.id.prf_gender);
            genderTextView.setText(dogData.profileGender);
            TextView weightTextView = findViewById(R.id.prf_weight);
            weightTextView.setText(dogData.profileWeight + "");
            TextView heightTextView = findViewById(R.id.prf_height);
            heightTextView.setText(dogData.profileHeight + "");
            TextView sickListTextView = findViewById(R.id.prf_symptom);
            sickListTextView.setText(dogData.profileSickList);
            TextView watchOutTextView = findViewById(R.id.prf_careful);
            watchOutTextView.setText(dogData.profileWatchOut);

            Uri imageUri = Uri.parse(dogData.profileImageUri);
            imageView.setImageURI(imageUri);
        }
        else if (type.equals("fish")) {
            TextView nameTextView = findViewById(R.id.prf_name);
            nameTextView.setText(fishData.profileName);
            TextView birthdayTextView = findViewById(R.id.prf_bday);
            birthdayTextView.setText(fishData.profileBirthday);
            TextView genderTextView = findViewById(R.id.prf_gender);
            genderTextView.setText(fishData.profileGender);
            TextView weightTextView = findViewById(R.id.prf_weight);
            weightTextView.setText(fishData.profileWeight + "");
            TextView heightTextView = findViewById(R.id.prf_height);
            heightTextView.setText(fishData.profileHeight + "");
            TextView sickListTextView = findViewById(R.id.prf_symptom);
            sickListTextView.setText(fishData.profileSickList);
            TextView watchOutTextView = findViewById(R.id.prf_careful);
            watchOutTextView.setText(fishData.profileWatchOut);

            Uri imageUri = Uri.parse(fishData.profileImageUri);
            imageView.setImageURI(imageUri);
        }
        else if (type.equals("rabbit")) {
            TextView nameTextView = findViewById(R.id.prf_name);
            nameTextView.setText(rabbitData.profileName);
            TextView birthdayTextView = findViewById(R.id.prf_bday);
            birthdayTextView.setText(rabbitData.profileBirthday);
            TextView genderTextView = findViewById(R.id.prf_gender);
            genderTextView.setText(rabbitData.profileGender);
            TextView weightTextView = findViewById(R.id.prf_weight);
            weightTextView.setText(rabbitData.profileWeight + "");
            TextView heightTextView = findViewById(R.id.prf_height);
            heightTextView.setText(rabbitData.profileHeight + "");
            TextView sickListTextView = findViewById(R.id.prf_symptom);
            sickListTextView.setText(rabbitData.profileSickList);
            TextView watchOutTextView = findViewById(R.id.prf_careful);
            watchOutTextView.setText(rabbitData.profileWatchOut);

            Uri imageUri = Uri.parse(rabbitData.profileImageUri);
            imageView.setImageURI(imageUri);
        }
        else if (type.equals("rat")) {
            TextView nameTextView = findViewById(R.id.prf_name);
            nameTextView.setText(ratData.profileName);
            TextView birthdayTextView = findViewById(R.id.prf_bday);
            birthdayTextView.setText(ratData.profileBirthday);
            TextView genderTextView = findViewById(R.id.prf_gender);
            genderTextView.setText(ratData.profileGender);
            TextView weightTextView = findViewById(R.id.prf_weight);
            weightTextView.setText(ratData.profileWeight + "");
            TextView heightTextView = findViewById(R.id.prf_height);
            heightTextView.setText(ratData.profileHeight + "");
            TextView sickListTextView = findViewById(R.id.prf_symptom);
            sickListTextView.setText(ratData.profileSickList);
            TextView watchOutTextView = findViewById(R.id.prf_careful);
            watchOutTextView.setText(ratData.profileWatchOut);

            Uri imageUri = Uri.parse(ratData.profileImageUri);
            imageView.setImageURI(imageUri);
        }
        else if (type.equals("snake")) {
            TextView nameTextView = findViewById(R.id.prf_name);
            nameTextView.setText(snakeData.profileName);
            TextView birthdayTextView = findViewById(R.id.prf_bday);
            birthdayTextView.setText(snakeData.profileBirthday);
            TextView genderTextView = findViewById(R.id.prf_gender);
            genderTextView.setText(snakeData.profileGender);
            TextView weightTextView = findViewById(R.id.prf_weight);
            weightTextView.setText(snakeData.profileWeight + "");
            TextView heightTextView = findViewById(R.id.prf_height);
            heightTextView.setText(snakeData.profileHeight + "");
            TextView sickListTextView = findViewById(R.id.prf_symptom);
            sickListTextView.setText(snakeData.profileSickList);
            TextView watchOutTextView = findViewById(R.id.prf_careful);
            watchOutTextView.setText(snakeData.profileWatchOut);

            Uri imageUri = Uri.parse(snakeData.profileImageUri);
            imageView.setImageURI(imageUri);
        }
    }

    private void setUserInfo() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String email = account.getEmail();
        String userUrl = account.getPhotoUrl().toString();

        TextView nameTextView = findViewById(R.id.prf_name);
        nameTextView.setText(email);
        Glide.with(imageView.getContext()).load(userUrl).into(imageView);
    }
}