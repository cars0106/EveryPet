package com.everypet.everypet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CommunityDescriptionActivity extends Activity implements View.OnClickListener {
    ImageView imageView;

    Button deleteButton;
    Button backButton;

    String petName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_description);

        // Intent 처리
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageurl");
        String userEmail = intent.getStringExtra("useremail");
        petName = intent.getStringExtra("petname");

        imageView = findViewById(R.id.image_view_community_description);
        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);

        deleteButton = findViewById(R.id.button_community_delete);
        deleteButton.setOnClickListener(this);

        backButton = findViewById(R.id.button_community_back);
        backButton.setOnClickListener(this);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String currentUserEmail = account.getEmail();

        if (!userEmail.equals(currentUserEmail)) {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_community_delete) {
            deleteNewPost(petName);
            finish();
        }
        else if (view.getId() == R.id.button_community_back) {
            finish();
        }
    }

    private void deleteNewPost(String petname) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        childUpdates.put("/photo_list/" + petname, postValues);
        databaseReference.updateChildren(childUpdates);
    }
}
