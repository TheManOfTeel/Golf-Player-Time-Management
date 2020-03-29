package com.example.elijah.golfplayertimemanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class OverviewFragment extends Fragment {
    private String Golfcourse;
    private String gameID;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private ListView list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        Bundle bundle = getArguments();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        Golfcourse = bundle.getString("courseName");
        gameID = bundle.getString("gameID");
        ArrayList<PlayerOverview> playerOverview = new ArrayList<>();

        list = (ListView)rootView.findViewById(R.id.playeroverviewlist);
        PlayerOverviewAdapter adapter = new PlayerOverviewAdapter(getContext(), playerOverview);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> playerIDs = new ArrayList<>();
                int holeNum = Integer.parseInt(dataSnapshot.child("Games").child(Golfcourse).child(gameID).child("Location").getValue().toString());
                Log.e("OverviewCurrent hole", String.valueOf(holeNum));
                String numberOfHoles = "";

                for(DataSnapshot ds: dataSnapshot.child("Games").child(Golfcourse).child(gameID).getChildren()){
                   String key = ds.getKey();
                   if(key.equals("Location") ||key.equals("TimeStarted") || key.equals("GroupLeader")){
                       Log.e("key", "Not what we are looking for");
                   }else{
                       Log.e("Key", key);
                       playerIDs.add(key);
                   }
                }
                Log.e("playerIDs", playerIDs.toString());

                for(int i = 0; i<playerIDs.size(); i++){
                    int totalscore = 0;
                    int currentholescore = 0;
                    String email = "";
                    if( dataSnapshot.child("Games").child(Golfcourse).child(gameID).child(playerIDs.get(i)).child("score").child("holes").child("hole"+holeNum).exists()) {
                        currentholescore = Integer.parseInt(dataSnapshot.child("Games").child(Golfcourse).child(gameID).child(playerIDs.get(i)).child("score").child("holes").child("hole" + holeNum).getValue().toString());
                    }
                    Log.e("currentholescore", String.valueOf(currentholescore));
                  for(DataSnapshot ds: dataSnapshot.child("Games").child(Golfcourse).child(gameID).child(playerIDs.get(i)).child("score").child("holes").getChildren()){
                      int score = Integer.parseInt(ds.getValue().toString());
                      totalscore += score;
                  }

                  email = dataSnapshot.child("Users").child(playerIDs.get(i)).child("email").getValue().toString();
                  Log.e("Total score", String.valueOf(totalscore));
                  PlayerOverview playerOverview1 = new PlayerOverview(email, currentholescore, totalscore);
                  playerOverview.add(playerOverview1);
                }




                Log.e("Player Overview", playerOverview.toString());
                list.setAdapter(adapter);





            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }
}
