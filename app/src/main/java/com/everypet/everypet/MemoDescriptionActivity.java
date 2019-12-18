package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MemoDescriptionActivity extends Activity implements View.OnClickListener {

    Button deleteButton;
    Button modifiedButton;
    Button backButton;

    EditText titleEditText;
    EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_description);

        deleteButton = findViewById(R.id.button_memo_delete);
        deleteButton.setOnClickListener(this);

        modifiedButton = findViewById(R.id.button_memo_modified);
        modifiedButton.setOnClickListener(this);

        backButton = findViewById(R.id.button_memo_back);
        backButton.setOnClickListener(this);

        //Intent 처리
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        titleEditText = findViewById(R.id.edit_text_memo_description_title);
        titleEditText.setText(title);
        contentEditText = findViewById(R.id.edit_text_memo_description_content);
        contentEditText.setText(content);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_memo_back) {
            finish();
        }
        else if (view.getId() == R.id.button_memo_modified) {
            Toast.makeText(getApplicationContext(), "메모가 수정되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (view.getId() == R.id.button_memo_delete) {
            Toast.makeText(getApplicationContext(), "메모가 삭제되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
