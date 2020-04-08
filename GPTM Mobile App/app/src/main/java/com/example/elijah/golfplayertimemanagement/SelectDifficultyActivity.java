package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import androidx.appcompat.app.AppCompatActivity;

public class SelectDifficultyActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private String GolfCourse;
    private String groupID = "";
    private ListView list;
    private ArrayList<HoleDifficulty> holeDifficulties = new ArrayList<HoleDifficulty>();
    private FirebaseAuth mAuth;
    private Intent intent;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        getSupportActionBar().setTitle("Select Difficulty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
         extras = intent.getBundleExtra("bundle");
        Log.e("SelectDifficulty", extras.toString());
        if(extras != null) {
            if (extras.containsKey("courseName")) {
                GolfCourse = extras.getString("courseName");
            } else {
                Log.e("SelectDifficulty", "courseName not available");
            }
        }



        list = (ListView)findViewById(R.id.difficultyList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String difficulty = holeDifficulties.get(i).Difficulty;
                Intent intent = new Intent(SelectDifficultyActivity.this, GameSetUpActivity.class);
                extras.putString("Difficulty", difficulty);
                intent.putExtras(extras);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //loadInfo();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Backarrow pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectDifficultyActivity.this, GameSetUpActivity.class);
            if(extras != null){
                intent.putExtras(extras);
                intent.putExtra("courseName", GolfCourse);
            }
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
        Intent intent = new Intent(SelectDifficultyActivity.this, GameSetUpActivity.class);
        startActivity(intent);
        finish();
    }
}
