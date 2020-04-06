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

public class ScoreAdapter extends ArrayAdapter<Score> {

    public ScoreAdapter(@NonNull Context context, ArrayList<Score> scores) {
        super(context, 0, scores);
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
                    R.layout.scores, parent, false
            );
        }
        TextView textView = converView.findViewById(R.id.historyScores);

        Score currentScores = getItem(position);

        if(currentScores != null) {
            textView.setText(currentScores.hole + ": " +currentScores.score);
        }

        return converView;

    }
}
