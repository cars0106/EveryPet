package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DiaryAdderActivity extends BaseActivity {

    Button save;
    long count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_adder);

        save=findViewById(R.id.upload_diary);
        setBtnImg();
        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                new AlertDialog.Builder(DiaryAdderActivity.this)
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DBHelper helper = new DBHelper(getApplicationContext());
//                SQLiteDatabase db = helper.getWritableDatabase();
//                db.execSQL("insert into tb_todo (name, date, time, what, notice) values (?, ?, ?, ?, ?)"
//                        , new String[]{who.getText().toString(), date.getText().toString(), time.getText().toString(),
//                                what.getText().toString(), String.valueOf(notice.isChecked())});
//                db.close();

                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void setBtnImg(){
        int btnId;
        int btnText;
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_pet order by _id asc", null);
        if(cursor != null && cursor.getCount() != 0)
            cursor.moveToNext();
        //System.out.println("커서아이디"+cursor.getInt(0));
        count = DatabaseUtils.queryNumEntries(db, "tb_pet");//db에 있는 데이터 개수
        //개수만큼 루프를 돌려야함 종류 string을 db에서 받아서 종류대로 버튼 이미지를 세팅해야함
        for(int i=1;i<=count;i++){
            btnId=getResources().getIdentifier("prf_pic"+i,"id",this.getPackageName());
            btnText=getResources().getIdentifier("prf"+i,"id",this.getPackageName());

            switch(cursor.getString(2)){
                case ("고양이"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.cat);
                    break;
                case ("강아지"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.dog);
                    break;
                case ("물고기"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.fish);
                    break;
                case ("토끼"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.rabbit);
                    break;
                case ("파충류"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.snake);
                    break;
                case ("설치류"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.rat);
                    break;
                case ("기타"):
                    ((ImageButton)findViewById(btnId)).setImageResource(R.drawable.person);
                    break;
            }
            ((TextView)findViewById(btnText)).setText(cursor.getString(1));
            cursor.moveToNext();
        }
        db.close();
    }

    public void onRestart(){

        setBtnImg();
        super.onRestart();
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
}
