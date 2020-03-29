package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class JoinGameSetUpActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game_set_up);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        String Uid = mAuth.getUid();


        //get all my views
        TextView joinselectedDifficulty = (TextView)findViewById(R.id.joinDifficulty);
        TextView course = (TextView)findViewById(R.id.joinCourse);
        TextView date = (TextView)findViewById(R.id.joindate);
        TextView day = (TextView)findViewById(R.id.joinday);
        ListView list = (ListView)findViewById(R.id.joinGroupList);
        TextView difficultybtn = (TextView)findViewById(R.id.joinSelectDifficulty);
        Button startRound = (Button)findViewById(R.id.joinRoundbtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBarsetup);

        Bundle bundle = getIntent().getBundleExtra("bundle");

        String gameID =bundle.getString("gameID");
        String GolfCourse = bundle.getString("courseName");
        int location = bundle.getInt("location");

        if(bundle.containsKey("difficulty")){
            String difficulty = bundle.getString("difficulty");
            joinselectedDifficulty.setText(difficulty);
        }
        Log.e("JoinGameSetUpActivity", gameID + " " + GolfCourse + " " + location);
        course.setText(GolfCourse);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                ArrayList<String> playerIDs = new ArrayList<>();
                ArrayList<Users> users = new ArrayList<>();
                Log.e("JoinGameActivity", "Inside datasnapshot");
                if (dataSnapshot.exists()){
                    Log.e("JoinGameActivity", "Snapshot exists");
                    for (DataSnapshot ds : dataSnapshot.child("Games").child(GolfCourse).child(gameID).getChildren()) {
                            String playerID = ds.getKey();
                            if(!playerID.equals("Location") || !playerID.equals("GroupLeader")|| !playerID.equals("TimeStarted")) {
                                playerIDs.add(playerID);
                                Log.e("JoinGameActivity", "playerID List: " + playerID);
                            }
                    }

                    Log.e("JoinGameActivity", playerIDs.toString());

                    for(int i = 0; i < playerIDs.size();i++){
                        if(dataSnapshot.child("Users").child(playerIDs.get(i)).child("email").exists()) {
                            String email = dataSnapshot.child("Users").child(playerIDs.get(i)).child("email").getValue().toString();
                            Users user = new Users(playerIDs.get(i), email);
                            users.add(user);
                        }
                    }
                    UserAdapter userAdapter = new UserAdapter(getApplicationContext(), users);
                    list.setAdapter(userAdapter);
                    progressBar.setVisibility(View.GONE);

            }else{
                    Log.e("JoinGameActivity", "Doesnt exist3");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //get current day
        try {
            date.setText(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Set text to current date and time
        day.setText(getDayOfTheWeek() + " " + getTimeFromAndroid());


        difficultybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinGameSetUpActivity.this, JoinSelectDifficultyActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });



        startRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(JoinGameSetUpActivity.this, Game3Activity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                finish();
            }
        });
    }


    private String getDate() throws ParseException {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    private String getDayOfTheWeek(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayoftheweek = "";

        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                dayoftheweek = "Sunday";
                return dayoftheweek;

            case Calendar.MONDAY:
                // Current day is Monday
                dayoftheweek = "Monday";
                return dayoftheweek;

            case Calendar.TUESDAY:
                dayoftheweek = "Tuesday";
                return dayoftheweek;

            case Calendar.WEDNESDAY:
                // Current day is Sunday
                dayoftheweek = "Wednesday";
                return dayoftheweek;

            case Calendar.THURSDAY:
                // Current day is Sunday
                dayoftheweek = "Thursday";
                return dayoftheweek;

            case Calendar.FRIDAY:
                // Current day is Sunday
                dayoftheweek = "Friday";
                return dayoftheweek;

            case Calendar.SATURDAY:
                // Current day is Sunday
                dayoftheweek = "Saturday";
                return dayoftheweek;
        }
        return dayoftheweek;
    }

    private String  getTimeFromAndroid() {
        Date dt = new Date();
        int hours = dt.getHours();
        int min = dt.getMinutes();
        String TimeOFDay = "";

        if(hours>=1 || hours<=12){
            TimeOFDay = "Morning";
        }else if(hours>=12 || hours<=16){
            TimeOFDay = "Afternoon";

        }else if(hours>=16 || hours<=21){
            TimeOFDay = "Evening";

        }else if(hours>=21 || hours<=24){
            TimeOFDay = "Night";

        }
        return TimeOFDay;
    }

    private String currentTime(){
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String time = sdf.format(dt);
        int hours = dt.getHours();
        int min = dt.getMinutes();

        return time;
    }
}
