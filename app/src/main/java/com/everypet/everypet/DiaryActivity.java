package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.everypet.everypet.adapter.RecyclerAdapter;
import com.everypet.everypet.adapter.RecyclerDiaryAdapter;
import com.everypet.everypet.adapter.RecyclerMemoAdapter;
import com.everypet.everypet.data.DiaryData;
import com.everypet.everypet.data.ProfileData;
import com.everypet.everypet.decoration.RecyclerDecoration;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class DiaryActivity extends BaseActivity implements View.OnClickListener {

    ImageButton diaryAllImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        diaryAllImageButton = findViewById(R.id.image_button_diary_all);
        diaryCatImageButton = findViewById(R.id.diary_cat);
        diaryDogImageButton = findViewById(R.id.diary_dog);
        diaryFishImageButton = findViewById(R.id.diary_fish);
        diaryRabbitImageButton = findViewById(R.id.diary_rabbit);
        diaryRatImageButton = findViewById(R.id.diary_rat);
        diarySnakeImageButton = findViewById(R.id.diary_snake);

        diaryAllImageButton.setOnClickListener(this);
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

        diaryCatTextView = findViewById(R.id.text_view_diary_cat);
        diaryDogTextView = findViewById(R.id.text_view_diary_dog);
        diaryFishTextView = findViewById(R.id.text_view_diary_fish);
        diaryRabbitTextView = findViewById(R.id.text_view_diary_rabbit);
        diaryRatTextView = findViewById(R.id.text_view_diary_rat);
        diarySnakeTextView = findViewById(R.id.text_view_diary_snake);

        diaryCatTextView.setVisibility(View.GONE);
        diaryDogTextView.setVisibility(View.GONE);
        diaryFishTextView.setVisibility(View.GONE);
        diaryRabbitTextView.setVisibility(View.GONE);
        diaryRatTextView.setVisibility(View.GONE);
        diarySnakeTextView.setVisibility(View.GONE);

        getInitProfileFromRealm();
        getDiaryFromRealm(null);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
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
                        return true;
                    case R.id.community:
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivityForResult(profileIntent, 100);
                        finish();
                        return true;
                }
                return false;
            }
        });

        ImageButton addButton = findViewById(R.id.button_add_diary);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiaryAdderActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
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
        if (view.getId() == R.id.diary_cat) {
            getDiaryFromRealm(catData.profileName);
        }
        else if (view.getId() == R.id.diary_dog) {
            getDiaryFromRealm(dogData.profileName);
        }
        else if (view.getId() == R.id.diary_fish) {
            getDiaryFromRealm(fishData.profileName);
        }
        else if (view.getId() == R.id.diary_rabbit) {
            getDiaryFromRealm(rabbitData.profileName);
        }
        else if (view.getId() == R.id.diary_rat) {
            getDiaryFromRealm(ratData.profileName);
        }
        else if (view.getId() == R.id.diary_snake) {
            getDiaryFromRealm(snakeData.profileName);
        }
        else if (view.getId() == R.id.image_button_diary_all) {
            getDiaryFromRealm(null);
        }
    }

    private void getDiaryFromRealm(String name) {
        Realm.init(getApplicationContext());
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<DiaryData> realmResults = mRealm.where(DiaryData.class).findAll();

        final ArrayList<DiaryData> diaryDataArrayList = new ArrayList<>();
        if (name == null) {
            diaryDataArrayList.addAll(realmResults);
        }
        else {
            for(int i = 0; i < realmResults.size(); i++) {
                DiaryData tmp = realmResults.get(i);
                if (tmp.petName.equals(name)) {
                    diaryDataArrayList.add(tmp);
                }
            }
        }

        final RecyclerView recyclerView = findViewById(R.id.recycler_diary);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

        RecyclerDecoration recyclerDecoration = new RecyclerDecoration(10);
        recyclerView.addItemDecoration(recyclerDecoration);

        if(diaryDataArrayList.size() != 0) {
            RecyclerDiaryAdapter recyclerDiaryAdapter = new RecyclerDiaryAdapter(diaryDataArrayList);
            recyclerView.setAdapter(recyclerDiaryAdapter);
        }
    }
}
