package com.everypet.everypet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("저장하지 않고 종료")
                .setMessage("게시물을 만들지 않고 종료하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }
}
