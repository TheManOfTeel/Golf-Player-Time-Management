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

public class DifficultyAdapter extends ArrayAdapter<HoleDifficulty> {

    public DifficultyAdapter(@NonNull Context context, ArrayList<HoleDifficulty> holeDifficulties) {
        super(context, 0, holeDifficulties);
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
                    R.layout.holedifficultylist, parent, false
            );
        }

        TextView Heading = converView.findViewById(R.id.difficultyHeading);
        TextView par= converView.findViewById(R.id.parDifficulty);
        TextView yards = converView.findViewById(R.id.yardsDifficulty);
        TextView description = converView.findViewById(R.id.DifficultyDescription);


        HoleDifficulty currentdifficulty = getItem(position);

        if(currentdifficulty  != null) {
            Heading.setText(currentdifficulty.getDifficulty());

            if(!currentdifficulty.getPar().equals("No par set")) {
                par.setText("Par: "+currentdifficulty.getPar());
            }else{
                par.setText("Par: 0");
            }

            if (!currentdifficulty.getYards().equals("No distance set")){
                yards.setText("Yards: "+currentdifficulty.getYards());
            }else {
                yards.setText("Yards: 0");
            }


            description.setText(currentdifficulty.getDesctiption());
        }

        return converView;

    }
}
