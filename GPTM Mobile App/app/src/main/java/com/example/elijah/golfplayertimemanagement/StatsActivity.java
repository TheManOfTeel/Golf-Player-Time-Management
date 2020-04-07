package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StatsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private ListView list;
    String CourseName = "";
    String gameID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        getSupportActionBar().setTitle("Game Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent().hasExtra("courseName") && getIntent().hasExtra("gameID")) {
            CourseName = getIntent().getStringExtra("courseName");
            gameID = getIntent().getStringExtra("gameID");
        }
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        String Uid = mAuth.getUid();
        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<PlayerHistory> playerHistories = new ArrayList<>();

                if(dataSnapshot.child("GameHistory").child(CourseName).child(gameID).exists()) {
                    for (DataSnapshot ds : dataSnapshot.child("GameHistory").child(CourseName).child(gameID).getChildren()) {
                        if (ds.child("score").child("holes").exists()) {
                            double number = Math.toIntExact(ds.child("score").child("holes").getChildrenCount());
                            String userID = ds.getKey();
                            double totalScore = 0;
                            for (DataSnapshot ds2 : ds.child("score").child("holes").getChildren()) {
                                double score = Integer.parseInt(ds2.getValue().toString());
                                totalScore += score;
                            }
                            double Average = totalScore / number;
                            Average = Math.round(Average * 100.0) / 100.0;
                            Log.e("Details", userID + " TotalScore: " + String.valueOf(totalScore) + "AverageScore: " + String.valueOf(Average));
                            PlayerHistory playerHistory = new PlayerHistory(userID, totalScore, Average, gameID, CourseName);
                            playerHistories.add(playerHistory);
                        }
                    }
                }
                Log.e("playerHistory", playerHistories.toString());
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                MyAdapter myAdapter = new MyAdapter(getApplicationContext(), playerHistories);
                recyclerView.setAdapter(myAdapter);

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Toast.makeText(getApplicationContext(), "Hey", Toast.LENGTH_LONG).show();

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Backarrow pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StatsActivity.this, HistoryListActivity.class);
            intent.putExtra("courseName", CourseName);
            startActivity(intent);
            finish();
            return true;
        }else if(item.getItemId() == R.id.signout){
            Toast.makeText(this, "Signout pressed", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            PresentationActivityIntent();
            return true;
        }
        return false;
    }




    public void PresentationActivityIntent(){
        Intent intent = new Intent(StatsActivity.this, PresentationActivity.class);
        startActivity(intent);
        finish();
    }
}
