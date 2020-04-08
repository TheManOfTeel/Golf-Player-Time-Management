package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import androidx.recyclerview.widget.LinearLayoutManager;

public class HistoryListActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private ListView list;
    private String Uid = "";
    private String CourseName = "";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        list = (ListView) findViewById(R.id.historyList);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getUid();
        CourseName = getIntent().getStringExtra("courseName");
        getSupportActionBar().setTitle("Game Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myRef.child("GameHistory").child(CourseName).addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    ArrayList<Game> games = new ArrayList<Game>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.child(Uid).exists()){
                            String gameID = ds.getKey();
                            String leader = ds.child("GroupLeader").getValue().toString();
                            int location = Integer.parseInt(ds.child("Location").getValue().toString());
                            String time = ds.child("TimeStarted").getValue().toString();
                            String date = "";
                            int playerCount = 0;
                            if(ds.child("Date").exists()){
                                date = ds.child("Date").getValue().toString();
                                playerCount = Math.toIntExact(ds.getChildrenCount() - 4);
                            }else{
                                playerCount = Math.toIntExact(ds.getChildrenCount() - 3);
                            }
                            Game game = new Game(gameID, CourseName, Uid, leader, location, time, playerCount, date);
                            games.add(game);

                        }else{
                            Log.e("GameID", "Doesnt Exist");
                        }
                    }
                    GameHistoryAdapter adapter = new GameHistoryAdapter(getApplicationContext(), games);
                    LinearLayoutManager layoutManager= new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
                    list.setAdapter(adapter);


                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(HistoryListActivity.this, StatsActivity.class);
                            intent.putExtra("courseName", CourseName);
                            intent.putExtra("gameID", games.get(i).GameID);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else{
                    Log.e("Datasapshot", "Doesnt Exist");
                }
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
            Intent intent = new Intent(HistoryListActivity.this, GolfCourseHomeActivity.class);
            intent.putExtra("GolfCourseID", CourseName);
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
        Intent intent = new Intent(HistoryListActivity.this, PresentationActivity.class);
        startActivity(intent);
        finish();
    }
}
