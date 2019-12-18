package com.everypet.everypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.everypet.everypet.adapter.RecyclerAdapter;
import com.everypet.everypet.data.RecyclerData;
import com.everypet.everypet.dialog.CustomDialog;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunityActivity extends BaseActivity {

    private CustomDialog customDialog;

    private ArrayList<RecyclerData> recyclerDataArrayList = new ArrayList<>();

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "추천 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show();
            customDialog.dismiss();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show();
            customDialog.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        getFirebaseDatabase();

        // BottomNavigationBar implementation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.todo:
                        Intent todoIntent = new Intent(getApplicationContext(), ToDoActivity.class);
                        startActivityForResult(todoIntent, 100);
                        finish();
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

        ImageButton addButton = findViewById(R.id.button_add_community);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommunityAdderChooseAnimalActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
        }
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

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        private OnItemClickListener mListener;
        private GestureDetector mGestureDetector;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {return true;}
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View clickedChildView = view.findChildViewUnder(e.getX(),e.getY());

            if(clickedChildView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(clickedChildView, view.getChildAdapterPosition(clickedChildView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
    }

    public void getFirebaseDatabase() {
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerDataArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecyclerData get = snapshot.getValue(RecyclerData.class);
                    recyclerDataArrayList.add(get);
                }

                for(int i = 0; i < recyclerDataArrayList.size(); i++) {
                    RecyclerData data = recyclerDataArrayList.get(i);
                    Log.d("CommunityActivity", "result : " + "petname = " + data.petname + "type = " + data.type + "imageurl = " + data.imageurl);
                }

                final RecyclerView recyclerView = findViewById(R.id.recycler_community);
                RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);

                recyclerView.setLayoutManager(manager);

                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(recyclerDataArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(CommunityActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                customDialog = new CustomDialog(CommunityActivity.this, positiveListener, negativeListener);
                                customDialog.show();
                            }
                        })
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("photo_list");
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}