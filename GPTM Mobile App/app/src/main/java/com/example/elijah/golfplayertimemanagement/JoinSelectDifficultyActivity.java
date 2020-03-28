package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class JoinSelectDifficultyActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private String GolfCourse;
    private String groupID = "";
    private ListView list;
    private ArrayList<HoleDifficulty> holeDifficulties = new ArrayList<HoleDifficulty>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_select_difficulty);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        GolfCourse = bundle.getString("courseName");




        list = (ListView)findViewById(R.id.joindifficultyList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String difficulty = holeDifficulties.get(i).Difficulty;
                Intent intent = new Intent(JoinSelectDifficultyActivity.this, JoinGameSetUpActivity.class);
                bundle.putString("difficulty", difficulty);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                finish();

            }
        });

        myRef.child("GolfCourse").child(GolfCourse).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.child("Holes").child("Hole1").getChildren()){
                            String difficulties = ds.getKey();
                            if(!difficulties.equals("Geofence") && !difficulties.equals("Description") && !difficulties.equals("Tips")) {
                                String description = dataSnapshot.child("Holes").child("Hole1").child(difficulties).child("Description").getValue().toString();
                                String par = dataSnapshot.child("Holes").child("Hole1").child(difficulties).child("Par").getValue().toString();
                                String tips = dataSnapshot.child("Holes").child("Hole1").child(difficulties).child("Tips").getValue().toString();
                                String yards = dataSnapshot.child("Holes").child("Hole1").child(difficulties).child("Yards").getValue().toString();
                                HoleDifficulty holeDifficulty = new HoleDifficulty(difficulties, description,par,tips,yards);
                                holeDifficulties.add(holeDifficulty);

                            }
                    }
                    DifficultyAdapter  difficultyAdapter = new DifficultyAdapter(getApplicationContext(), holeDifficulties);
                    list.setAdapter(difficultyAdapter);
                    list.setTextFilterEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
