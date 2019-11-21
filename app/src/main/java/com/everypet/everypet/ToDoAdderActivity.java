package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.everypet.everypet.font.BaseActivity;

public class ToDoAdderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_adder);
    }
}
