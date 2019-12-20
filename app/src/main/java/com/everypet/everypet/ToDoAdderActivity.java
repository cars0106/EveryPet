package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.everypet.everypet.data.ProfileData;
import com.everypet.everypet.data.ToDoData;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class ToDoAdderActivity extends BaseActivity implements View.OnClickListener {

    EditText EditTimeHour;
    EditText EditTimeMinute;
    EditText contentEditData;

    CalendarView calendarView;

    String year,month,day;

    Spinner whoSpinner;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_adder);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        year= yearFormat.format(currentTime);
        month= monthFormat.format(currentTime);
        day= dayFormat.format(currentTime);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int cyear, int cmonth, int cdayOfMonth) {
                cmonth++;
                year = Integer.toString(cyear);
                month = Integer.toString(cmonth).length()==1?"0"+cmonth:Integer.toString(cmonth);
                day = Integer.toString(cdayOfMonth).length()==1?"0"+cdayOfMonth:Integer.toString(cdayOfMonth);
            }
        });

        EditTimeHour = findViewById(R.id.edit_start_hour);
        EditTimeMinute = findViewById(R.id.edit_start_minute);

        EditTimeHour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(EditTimeHour.getText().toString().length() == 1)
                        EditTimeHour.setText("0"+EditTimeHour.getText().toString());
                }
            }
        });
        EditTimeMinute.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(EditTimeMinute.getText().toString().length() == 1)
                        EditTimeMinute.setText("0"+EditTimeMinute.getText().toString());
                }
            }
        });

        whoSpinner = findViewById(R.id.spinner_to_do_who);
        List<String> spinnerArray = getInitProfileFromRealm();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        whoSpinner.setAdapter(adapter);

        Button saveButton = findViewById(R.id.button_save_to_do);
        saveButton.setOnClickListener(this);

        checkBox = findViewById(R.id.to_do_checkbox);

        contentEditData = findViewById(R.id.edit_text_to_do_what);

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                new AlertDialog.Builder(ToDoAdderActivity.this)
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

    private List<String> getInitProfileFromRealm() {
        Realm.init(getApplicationContext());
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<ProfileData> realmResults = mRealm.where(ProfileData.class).findAll();

        final ArrayList<ProfileData> profileDataArrayList = new ArrayList<>();
        profileDataArrayList.addAll(realmResults);

        List<String> results = new ArrayList<String>();

        for (int i = 0; i < profileDataArrayList.size(); i++) {
            ProfileData tmp = profileDataArrayList.get(i);
            if (tmp.profileType.equals("cat")) {
                results.add(tmp.profileName);
            } else if (tmp.profileType.equals("dog")) {
                results.add(tmp.profileName);
            } else if (tmp.profileType.equals("fish")) {
                results.add(tmp.profileName);
            } else if (tmp.profileType.equals("rabbit")) {
                results.add(tmp.profileName);
            } else if (tmp.profileType.equals("rat")) {
                results.add(tmp.profileName);
            } else if (tmp.profileType.equals("snake")) {
                results.add(tmp.profileName);
            }
        }
        return results;
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
        if (view.getId() == R.id.button_save_to_do) {
            String petName = whoSpinner.getSelectedItem().toString();
            Realm.init(getApplicationContext());
            Realm realm = Realm.getDefaultInstance();
            ProfileData tmp = realm.where(ProfileData.class).equalTo("profileName", petName).findFirst();
            final String type = tmp.profileType;
            final boolean alarm = checkBox.isChecked();
            final String content = contentEditData.getText().toString();
            final String time = EditTimeHour.getText().toString() + "시 " + EditTimeMinute.getText().toString() + "분";

            Realm.init(getApplicationContext());
            Realm mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ToDoData data = realm.createObject(ToDoData.class);
                    data.toDoPetType = type;
                    data.toDoAlarm = alarm;
                    data.toDoWhat = content;
                    data.toDoDate = year + "년 " + month + "월 " + day + "일";
                    data.toDoTime = time;
                }
            });
            Toast.makeText(getApplicationContext(), "할일이 저장되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
