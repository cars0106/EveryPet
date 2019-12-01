package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.everypet.everypet.font.BaseActivity;

public class CommunityAdderWriteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_adder_write);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
