package com.example.elijah.golfplayertimemanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NextHoleFragment extends Fragment {
    private String GolfCourse;
    private String gameID;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private int hole;
    private TextView holenum;
    private TextView gamestext;
    private TextView players;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nexthole, container, false);
        Bundle bundle = getArguments();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        GolfCourse = bundle.getString("courseName");
        gameID = bundle.getString("gameID");

        holenum = (TextView)rootView.findViewById(R.id.nextholenum);
        gamestext = (TextView)rootView.findViewById(R.id.nextholenumofgames);
        players = (TextView)rootView.findViewById(R.id.nextholenumofplayers);


        myRef.child("Games").child(GolfCourse).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Game> games = new ArrayList<>();
                ArrayList<String> gameIDs = new ArrayList<>();
                long numberOfPlayersonHole = 0;
                long numberOfGamesOnHole = 0;
                int hole = Integer.parseInt(dataSnapshot.child(gameID).child("Location").getValue().toString());
                int nexthole = hole+1;
                //get all hole locations
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String gameID = ds.getKey();
                    gameIDs.add(gameID);

                }
                Log.e("Games", gameIDs.toString());

                String groupleader = "";
                int location = 0;
                String timeStarted = "";
                int playerNum;
                for(int i = 0; i<gameIDs.size(); i++){
                    if(!gameIDs.get(i).equals(gameID)) {
                        groupleader = dataSnapshot.child(gameIDs.get(i)).child("GroupLeader").getValue().toString();
                        location = Integer.parseInt(dataSnapshot.child(gameIDs.get(i)).child("Location").getValue().toString());
                        if (dataSnapshot.child(gameIDs.get(i)).child("TimeStarted").exists()) {
                            timeStarted = dataSnapshot.child(gameIDs.get(i)).child("TimeStarted").getValue().toString();
                        } else {
                            timeStarted = "unknown";
                        }
                        long playernum = dataSnapshot.child(gameIDs.get(i)).getChildrenCount() - 3;
                        Game game = new Game(gameIDs.get(i), GolfCourse, "", groupleader, location, timeStarted, playernum);
                        games.add(game);
                    }else{
                        Log.e("NextHoleFragment", "GameID equals my game ID");
                    }
                }
                Log.e("Game Info", games.toString());
                for(int i = 0; i<games.size();i++){
                    if(games.get(i).getLocation() == nexthole){
                        numberOfPlayersonHole =+ games.get(i).numOfPlayers;
                        numberOfGamesOnHole++;
                    }
                }
                Log.e("Next Hole Info", "Number Of Game:" + numberOfGamesOnHole + "number Of Players " + numberOfPlayersonHole);
                holenum.setText("Hole " + nexthole);
                gamestext.setText(numberOfGamesOnHole +  " Games On This Hole");
                players.setText(numberOfPlayersonHole + " Players On This Hole");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return rootView;
    }
}
