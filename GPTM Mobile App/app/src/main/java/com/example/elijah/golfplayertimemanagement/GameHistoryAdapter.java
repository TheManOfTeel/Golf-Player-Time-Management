package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameHistoryAdapter extends ArrayAdapter<Game> {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    public GameHistoryAdapter(@NonNull Context context, ArrayList<Game> games) {
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
                    R.layout.gamehistoryadapter, parent, false
            );
        }
        TextView textView1 = converView.findViewById(R.id.endedleader);
        TextView textView2 = converView.findViewById(R.id.endedtime);
        TextView textView3 = converView.findViewById(R.id.endedlocation);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();


        Game currentGame = getItem(position);

        if(currentGame != null) {

            textView1.setText("Group Leader: " + currentGame.GroupLeader);
            textView2.setText("Game Started On: "+currentGame.date + " " + currentGame.TimeStarted);
            textView3.setText("Game Ended On Hole"+currentGame.Location);
        }

        return converView;

    }
}
