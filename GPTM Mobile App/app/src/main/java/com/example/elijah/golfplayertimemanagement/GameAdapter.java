package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameAdapter extends ArrayAdapter<Game> {

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
            grouleader.setText("Added by:" + currentGame.getGroupLeader());
            location.setText("Hole" + currentGame.getLocation());
            time.setText("Started at:" + currentGame.getTimeStarted());
        }
        return converView;

    }
}
