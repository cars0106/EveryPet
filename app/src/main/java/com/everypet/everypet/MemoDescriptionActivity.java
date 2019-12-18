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

import com.everypet.everypet.data.MemoData;

import io.realm.Realm;

public class MemoDescriptionActivity extends Activity implements View.OnClickListener {

    Button deleteButton;
    Button modifiedButton;
    Button backButton;

    EditText titleEditText;
    EditText contentEditText;

    String title;

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
        title = intent.getStringExtra("title");
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
            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    MemoData modifiedData = realm.where(MemoData.class).equalTo("memoTitle", title).findFirst();
                    modifiedData.memoTitle = titleEditText.getText().toString();
                    modifiedData.memoContent = contentEditText.getText().toString();
                }
            });

            Toast.makeText(getApplicationContext(), "메모가 수정되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (view.getId() == R.id.button_memo_delete) {
            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    MemoData deleteData = realm.where(MemoData.class).equalTo("memoTitle", title).findFirst();
                    if(deleteData != null) {
                        deleteData.deleteFromRealm();
                    }
                }
            });

            Toast.makeText(getApplicationContext(), "메모가 삭제되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}