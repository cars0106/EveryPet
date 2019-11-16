package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_sign_out;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do);

        button_sign_out = findViewById(R.id.btn_googleSignOut);
        button_sign_out.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
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
