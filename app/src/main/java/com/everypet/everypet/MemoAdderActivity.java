package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everypet.everypet.data.MemoData;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.realm.Realm;

public class MemoAdderActivity extends BaseActivity implements View.OnClickListener {

    EditText titleEditText;
    EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_adder);

        Button addMemoButton = findViewById(R.id.button_write_memo);
        addMemoButton.setOnClickListener(this);

        titleEditText = findViewById(R.id.edit_text_memo_title);
        contentEditText = findViewById(R.id.edit_text_memo_content);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                new AlertDialog.Builder(MemoAdderActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("저장하지 않고 종료")
                        .setMessage("게시물을 만들지 않고 종료하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (menuItem.getItemId()) {
                                    case R.id.todo:
                                        Intent todoIntent = new Intent(getApplicationContext(), ToDoActivity.class);
                                        startActivityForResult(todoIntent, 100);
                                        finish();
                                        break;
                                    case R.id.memo:
                                        Intent memoIntent = new Intent(getApplicationContext(), MemoActivity.class);
                                        startActivityForResult(memoIntent, 100);
                                        finish();
                                        break;
                                    case R.id.diary:
                                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                                        startActivityForResult(diaryIntent, 100);
                                        finish();
                                        break;
                                    case R.id.community:
                                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                                        startActivityForResult(communityIntent, 100);
                                        finish();
                                        break;
                                    case R.id.profile:
                                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                                        startActivityForResult(profileIntent, 100);
                                        finish();
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("저장하지 않고 종료")
                .setMessage("게시물을 만들지 않고 종료하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_write_memo) {
            final String title = titleEditText.getText().toString();
            final String content = contentEditText.getText().toString();

            Realm.init(this);
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    MemoData data = realm.createObject(MemoData.class);
                    data.id = "Memo";
                    data.memoTitle = title;
                    data.memoContent = content;
                }
            });

            Toast.makeText(getApplicationContext(), "메모가 저장되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
