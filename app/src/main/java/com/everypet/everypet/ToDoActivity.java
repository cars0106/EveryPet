package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.TextView;

import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ToDoActivity extends BaseActivity implements View.OnClickListener {

    private Button button_sign_out;
    private FirebaseAuth firebaseAuth;
    long count;
    private ListView dataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do);

        button_sign_out = findViewById(R.id.btn_googleSignOut);
        button_sign_out.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        setBtnImg();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name, kind, birthDay, gender, weight, height, symptom, careful from tb_pet order by _id asc", null);
        if(cursor != null && cursor.getCount() != 0)  count = DatabaseUtils.queryNumEntries(db,"tb_pet");
        db.close();

        int resourceId;

        for (int i=1; i<=count; i++){
            resourceId = getResources().getIdentifier("profile"+i, "id",getPackageName());
            (findViewById(resourceId)).setVisibility(View.VISIBLE);
        }

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
                        finish();
                        return true;
                    case R.id.diary:
                        Intent diaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivityForResult(diaryIntent, 100);
                        finish();
                        return true;
                    case R.id.community:
                        Intent communityIntent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivityForResult(communityIntent, 100);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivityForResult(profileIntent, 100);
                        finish();
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
                startActivityForResult(intent,200);
            }
        });

        dataListView = findViewById(R.id.todolist);
    }

//    할일 세팅하는 부분
//    리스트 보여줘야 되는데
//    tb_todo에서 date 오름차순으로ㅇㅇㅇ
//    public void setToDoList(int num) {
//        ToDoHelper helper = new ToDoHelper(this);
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM tb_todo order by date asc", null);
//        long dataCnt = DatabaseUtils.queryNumEntries(db, "tb_todo");//db에 있는 데이터 개수
//        ArrayList<ToDoData> todo = new ArrayList<>();
//
//        for (int i = 0; i < dataCnt; i++) {
//            ToDoData list = new ToDoData();
//            if (num == 0) {
//0
//                list.name=cursor.getString(1);
//                list.kind=cursor.getString(2);
//                list.date=cursor.getString(3);
//                list.time=cursor.getString(4);
//                list.what=cursor.getString(5);
//                list.isChecked=cursor.getString(6);
//
//                todo.add(list);
//            } else {
//                ;
//            }
//        }
//
//        ListAdapter adapter = new ListAdapter(todo);
//        dataListView.setAdapter(adapter);
//    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
    }
    @Override
    protected void onRestart() {
        setBtnImg();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
