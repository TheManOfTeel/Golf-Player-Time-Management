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
public class PlayerHistoryAdapter extends  ArrayAdapter<PlayerHistory> {

    public PlayerHistoryAdapter(@NonNull Context context, ArrayList<PlayerHistory>  playerHistories) {
        super(context, 0, playerHistories);
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
                    R.layout.playerhistory, parent, false
            );
        }
        TextView textView1 = converView.findViewById(R.id.historyemail);
        TextView textView2 = converView.findViewById(R.id.historytotalScore);
        TextView textView3 = converView.findViewById(R.id.averagescore);

        PlayerHistory currentPlayer = getItem(position);

        if(currentPlayer != null) {

            textView1.setText(currentPlayer.id);
            textView2.setText("Total com.example.elijah.golfplayertimemanagement.UserAdapter.Score: " +currentPlayer.totalscore);
            textView3.setText("Average com.example.elijah.golfplayertimemanagement.UserAdapter.Score: " + currentPlayer.average);

        }

        return converView;

    }
}
