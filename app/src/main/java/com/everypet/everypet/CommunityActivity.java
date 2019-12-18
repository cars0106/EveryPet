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
import com.everypet.everypet.decoration.RecyclerDecoration;
import com.everypet.everypet.font.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunityActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<RecyclerData> recyclerDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        getFirebaseDatabase(null);

        ImageButton picAllImageButton = findViewById(R.id.prf_pic);
        ImageButton picCatImageButton = findViewById(R.id.cat_pic);
        ImageButton picDogImageButton = findViewById(R.id.dog_pic);
        ImageButton picFishImageButton = findViewById(R.id.fish_pic);
        ImageButton picRabbitImageButton = findViewById(R.id.rabbit_pic);
        ImageButton picRatImageButton = findViewById(R.id.rat_pic);
        ImageButton picSnakeImageButton = findViewById(R.id.snake_pic);

        picAllImageButton.setOnClickListener(this);
        picCatImageButton.setOnClickListener(this);
        picDogImageButton.setOnClickListener(this);
        picFishImageButton.setOnClickListener(this);
        picRabbitImageButton.setOnClickListener(this);
        picRatImageButton.setOnClickListener(this);
        picSnakeImageButton.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.prf_pic) {
            getFirebaseDatabase(null);
        }
        else if (view.getId() == R.id.cat_pic) {
            getFirebaseDatabase("cat");
        }
        else if (view.getId() == R.id.dog_pic) {
            getFirebaseDatabase("dog");
        }
        else if (view.getId() == R.id.fish_pic) {
            getFirebaseDatabase("fish");
        }
        else if (view.getId() == R.id.rabbit_pic) {
            getFirebaseDatabase("rabbit");
        }
        else if (view.getId() == R.id.rat_pic) {
            getFirebaseDatabase("rat");
        }
        else if (view.getId() == R.id.snake_pic) {
            getFirebaseDatabase("snake");
        }
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

    public void getFirebaseDatabase(final String toFind) {
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerDataArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecyclerData get = snapshot.getValue(RecyclerData.class);

                    if (toFind == null) {
                    recyclerDataArrayList.add(get);
                    }
                    else {
                        if (get.type.equals(toFind)) {
                            recyclerDataArrayList.add(get);
                        }
                    }
                }

                for(int i = 0; i < recyclerDataArrayList.size(); i++) {
                    RecyclerData data = recyclerDataArrayList.get(i);

                    Log.d("CommunityActivity", "result : " + "petname = " + data.petname + "type = " + data.type + "imageurl = " + data.imageurl);
                }

                final RecyclerView recyclerView = findViewById(R.id.recycler_community);
                RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
                recyclerView.setLayoutManager(manager);

                RecyclerDecoration recyclerDecoration = new RecyclerDecoration(5);
                recyclerView.addItemDecoration(recyclerDecoration);

                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(recyclerDataArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(CommunityActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(), CommunityDescriptionActivity.class);

                                RecyclerData tmp = recyclerDataArrayList.get(position);
                                intent.putExtra("imageurl", tmp.imageurl);
                                intent.putExtra("useremail", tmp.useremail);
                                intent.putExtra("petname", tmp.petname);

                                startActivity(intent);
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