package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<PlayerHistory> playerHistories;
    private Context context;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView1, textView2,textView3;
        public ListView list;

        public MyViewHolder(View v) {
            super(v);
            textView1 = v.findViewById(R.id.historyemail);
            textView2 = v.findViewById(R.id.historytotalScore);
            textView3 = v.findViewById(R.id.averagescore);
            list = v.findViewById(R.id.scorelist);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context applicationContext, List<PlayerHistory> playerHistories) {

        this.playerHistories = playerHistories;
        this.context = applicationContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playerhistory, parent, false);
        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        String Uid = mAuth.getUid();
        PlayerHistory playerHistory = playerHistories.get(position);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Score> scores = new ArrayList<>();
                if(dataSnapshot.child("Users").child(playerHistory.id).child("email").exists()){
                    String email = dataSnapshot.child("Users").child(playerHistory.id).child("email").getValue().toString();
                    holder.textView1.setText(email);
                    holder.textView2.setText("Total Score: "+playerHistory.totalscore);
                    holder.textView3.setText("Average Score: "+playerHistory.average);
                }else{
                    holder.textView1.setText(playerHistory.id);
                    holder.textView2.setText("Total Score: "+playerHistory.totalscore);
                    holder.textView3.setText("Average Score: "+playerHistory.average);
                }
                if(dataSnapshot.child("GameHistory").child(playerHistory.CourseName).child(playerHistory.gameID).child(playerHistory.id).child("score").child("holes").exists()) {
                    for (DataSnapshot ds : dataSnapshot.child("GameHistory").child(playerHistory.CourseName).child(playerHistory.gameID).child(playerHistory.id).child("score").child("holes").getChildren()) {
                        String key = ds.getKey();
                        String score = ds.getValue().toString();
                        Score playerScore = new Score(key, score);
                        scores.add(playerScore);
                    }
                    ScoreAdapter scoreAdapter = new ScoreAdapter( context, scores);
                    holder.list.setAdapter(scoreAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return playerHistories.size();
    }
}