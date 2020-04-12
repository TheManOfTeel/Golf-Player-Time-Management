package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class GameAdapter extends ArrayAdapter<Game> {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private String Uid;

    public GameAdapter(@NonNull Context context, ArrayList<Game> games) {
        super(context, 0, games);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }



    private View initView(int position, View converView, ViewGroup parent){
        if(converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(
                    R.layout.gameslist, parent, false
            );
        }
        TextView grouleader = converView.findViewById(R.id.leader);
        TextView location = converView.findViewById(R.id.Location);
        TextView time = converView.findViewById(R.id.time);


        Game currentGame = getItem(position);

        if(currentGame != null) {

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
            mAuth = FirebaseAuth.getInstance();
            if(mAuth.getUid()== null){
                Uid = "Anon";
            }
            else {
                Uid = mAuth.getUid();
            }

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String email = "";
                    String groupleader = "";
                    if(dataSnapshot.child("Users").child(Uid).child("email").exists()){
                        email = dataSnapshot.child("Users").child(Uid).child("email").getValue().toString();
                    }
                    if(dataSnapshot.child("Games").child(currentGame.CourseID).child(currentGame.GameID).child("GroupLeader").exists()){
                        groupleader = dataSnapshot.child("Games").child(currentGame.CourseID).child(currentGame.GameID).child("GroupLeader").getValue().toString();
                    }
                    Log.e("Leader", groupleader);
                    Log.e("Email", email);

                    if(email.trim().equals(groupleader.trim())){
                        grouleader.setText("Started By You");
                        location.setText("Currently On Hole" + currentGame.getLocation());
                        time.setText("Started at: " + currentGame.getTimeStarted());
                    }else{
                        grouleader.setText("Added by: " + currentGame.getGroupLeader());
                        location.setText("Currently On Hole" + currentGame.getLocation());
                        time.setText("Started at: " + currentGame.getTimeStarted());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        return converView;

    }
}
