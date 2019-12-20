package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.everypet.everypet.adapter.RecyclerDiaryAdapter;
import com.everypet.everypet.adapter.RecyclerToDoAdapter;
import com.everypet.everypet.data.ProfileData;
import com.everypet.everypet.data.ToDoData;
import com.everypet.everypet.decoration.RecyclerDecoration;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class ToDoActivity extends BaseActivity implements View.OnClickListener {

    ImageButton toDoAllImageButton;
    ImageButton toDoCatImageButton;
    ImageButton toDoDogImageButton;
    ImageButton toDoFishImageButton;
    ImageButton toDoRabbitImageButton;
    ImageButton toDoRatImageButton;
    ImageButton toDoSnakeImageButton;

    TextView toDoCatTextView;
    TextView toDoDogTextView;
    TextView toDoFishTextView;
    TextView toDoRabbitTextView;
    TextView toDoRatTextView;
    TextView toDoSnakeTextView;

    ProfileData catData;
    ProfileData dogData;
    ProfileData fishData;
    ProfileData rabbitData;
    ProfileData ratData;
    ProfileData snakeData;

    @Override
    protected void onStart() {
        super.onStart();
        getListFromRealm();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do);

        toDoAllImageButton = findViewById(R.id.image_button_to_do_all);
        toDoCatImageButton = findViewById(R.id.image_button_to_do_cat);
        toDoDogImageButton = findViewById(R.id.image_button_to_do_dog);
        toDoFishImageButton = findViewById(R.id.image_button_to_do_fish);
        toDoRabbitImageButton = findViewById(R.id.image_button_to_do_rabbit);
        toDoRatImageButton = findViewById(R.id.image_button_to_do_rat);
        toDoSnakeImageButton = findViewById(R.id.image_button_to_do_snake);

        toDoAllImageButton.setOnClickListener(this);
        toDoCatImageButton.setOnClickListener(this);
        toDoDogImageButton.setOnClickListener(this);
        toDoFishImageButton.setOnClickListener(this);
        toDoRabbitImageButton.setOnClickListener(this);
        toDoRatImageButton.setOnClickListener(this);
        toDoSnakeImageButton.setOnClickListener(this);

        toDoCatImageButton.setVisibility(View.GONE);
        toDoDogImageButton.setVisibility(View.GONE);
        toDoFishImageButton.setVisibility(View.GONE);
        toDoRabbitImageButton.setVisibility(View.GONE);
        toDoRatImageButton.setVisibility(View.GONE);
        toDoSnakeImageButton.setVisibility(View.GONE);

        toDoCatTextView = findViewById(R.id.text_view_to_do_cat);
        toDoDogTextView = findViewById(R.id.text_view_to_do_dog);
        toDoFishTextView = findViewById(R.id.text_view_to_do_fish);
        toDoRabbitTextView = findViewById(R.id.text_view_to_do_rabbit);
        toDoRatTextView = findViewById(R.id.text_view_to_do_rat);
        toDoSnakeTextView = findViewById(R.id.text_view_to_do_snake);

        toDoCatTextView.setVisibility(View.GONE);
        toDoDogTextView.setVisibility(View.GONE);
        toDoFishTextView.setVisibility(View.GONE);
        toDoRabbitTextView.setVisibility(View.GONE);
        toDoRatTextView.setVisibility(View.GONE);
        toDoSnakeTextView.setVisibility(View.GONE);

        getInitProfileFromRealm();
        getListFromRealm();

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.todo:
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
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivityForResult(profileIntent, 100);
                        finish();
                        return true;
                }
                return false;
            }
        });

        ImageButton addButton = findViewById(R.id.button_add_to_do);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToDoAdderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
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
                toDoCatImageButton.setVisibility(View.VISIBLE);
                toDoCatTextView.setVisibility(View.VISIBLE);
                toDoCatTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("dog")) {
                dogData = tmp;
                toDoDogImageButton.setVisibility(View.VISIBLE);
                toDoDogTextView.setVisibility(View.VISIBLE);
                toDoDogTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("fish")) {
                fishData = tmp;
                toDoFishImageButton.setVisibility(View.VISIBLE);
                toDoFishTextView.setVisibility(View.VISIBLE);
                toDoFishTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("rabbit")) {
                rabbitData = tmp;
                toDoRabbitImageButton.setVisibility(View.VISIBLE);
                toDoRabbitTextView.setVisibility(View.VISIBLE);
                toDoRabbitTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("rat")) {
                ratData = tmp;
                toDoRatImageButton.setVisibility(View.VISIBLE);
                toDoRatTextView.setVisibility(View.VISIBLE);
                toDoRatTextView.setText(tmp.profileName);
            } else if (tmp.profileType.equals("snake")) {
                snakeData = tmp;
                toDoSnakeImageButton.setVisibility(View.VISIBLE);
                toDoSnakeTextView.setVisibility(View.VISIBLE);
                toDoSnakeTextView.setText(tmp.profileName);
            }
        }
    }

    private void getListFromRealm() {
        Realm.init(getApplicationContext());
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<ToDoData> realmResults = mRealm.where(ToDoData.class).findAll();

        final ArrayList<ToDoData> toDoDataArrayList = new ArrayList<>();
        toDoDataArrayList.addAll(realmResults);

        final RecyclerView recyclerView = findViewById(R.id.recycler_to_do);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

        RecyclerDecoration recyclerDecoration = new RecyclerDecoration(10);
        recyclerView.addItemDecoration(recyclerDecoration);

        if(toDoDataArrayList.size() != 0) {
            RecyclerToDoAdapter recyclerToDoAdapter = new RecyclerToDoAdapter(toDoDataArrayList);
            recyclerView.setAdapter(recyclerToDoAdapter);

            ToDoData tmp = toDoDataArrayList.get(0);
            String date = tmp.toDoDate;
            TextView textView = findViewById(R.id.text_view_to_do_date);
            textView.setText(date);
        }
        else {
            LinearLayout layout = findViewById(R.id.linearLayout_to_do);
            layout.setVisibility(View.GONE);
        }
    }
}
