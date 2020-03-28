package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CurrentGamesActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private String GolfCourse;
    private ArrayList<Game> games;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_games);
        listView = (ListView)findViewById(R.id.gamelist);
        GolfCourse = getIntent().getStringExtra("courseName");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        String Uid = mAuth.getUid();

        games = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.child("Games").child(GolfCourse).getChildren()){
                    String gameID = ds.getKey();
                    if(ds.child(Uid).exists()) {
                        String playerID = ds.child(Uid).getKey().toString();
                        String GroupLeader = ds.child("GroupLeader").getValue().toString();
                        int Location = Integer.parseInt(ds.child("Location").getValue().toString());
                        String TimeStarted = ds.child("TimeStarted").getValue().toString();
                        Game game = new Game(gameID, GolfCourse, playerID,GroupLeader, Location, TimeStarted, 0);
                        games.add(game);
                        Log.e("CurrentGamesActivity", game.toString());
                    }else{
                        Log.e("currentGamesActivity", "Does Not exist");
                    }
                }
                GameAdapter gameAdapter = new GameAdapter(getApplicationContext(), games);
                listView.setAdapter(gameAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("ItemClicked", games.get(i).toString());
                Intent intent = new Intent(CurrentGamesActivity.this, JoinGameSetUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("gameID", games.get(i).getGameID());
                bundle.putString("courseName", games.get(i).getCourseID());
                bundle.putInt("location", games.get(i).getLocation());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                finish();

            }
        });


    }
}
