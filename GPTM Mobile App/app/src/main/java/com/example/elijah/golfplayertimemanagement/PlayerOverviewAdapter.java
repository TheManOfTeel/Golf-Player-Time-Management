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

public class PlayerOverviewAdapter extends ArrayAdapter<PlayerOverview> {

    public PlayerOverviewAdapter(@NonNull Context context, ArrayList<PlayerOverview> playerOverviews) {
        super(context, 0, playerOverviews);
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
                    R.layout.playeroverview, parent, false
            );
        }
        TextView textView1 = converView.findViewById(R.id.playeremail);
        TextView textView2 = converView.findViewById(R.id.currentholescore);
        TextView textView3 = converView.findViewById(R.id.totalholescore);

       PlayerOverview playerOverview = getItem(position);

        if(playerOverview != null) {
            textView1.setText(playerOverview.playerID);
            textView2.setText("Score at current Hole: "+String.valueOf(playerOverview.currentHoleScore));
            textView3.setText("Total score: "+String.valueOf(playerOverview.totalScore));

        }

        return converView;

    }
}
