package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ToDoActivity extends BaseActivity implements View.OnClickListener {

    private Button button_sign_out;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do);

        button_sign_out = findViewById(R.id.btn_googleSignOut);
        button_sign_out.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

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
                        return true;
                    case R.id.diary:
                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivityForResult(diaryIntent, 100);
                        return true;
                    case R.id.community:
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        return true;
                    case R.id.profile:
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivityForResult(profileIntent, 100);
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
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void signOut() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LogInActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_googleSignOut) {
            signOut();
        }
    }
}
