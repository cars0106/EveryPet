package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class CommunityDescriptionActivity extends Activity implements View.OnClickListener {
    ImageView imageView;

    Button deleteButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_description);

        imageView = findViewById(R.id.image_view_community_description);

        deleteButton = findViewById(R.id.button_community_delete);
        deleteButton.setOnClickListener(this);

        backButton = findViewById(R.id.button_community_back);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_community_delete) {

        }
        else if (view.getId() == R.id.button_community_back) {
            finish();
        }
    }
}
