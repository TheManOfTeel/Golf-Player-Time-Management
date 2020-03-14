package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class GameSetUpActivity extends AppCompatActivity {
    private TextView mangeGroups;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private ArrayList<Users> groupList;
    ArrayList<String> groupIDs;
    private Button startRound;
    private TextView course;
    private TextView date;
    private Date currentDate;
    private TextView day;
    private TextView difficultybtn;
    private TextView selectedDifficulty;
    private String activity;
    private String Difficulty = "";
    private String GolfCourse = "";
    private String groupID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_set_up);
        activity = getIntent().getStringExtra("Activity");

        //database references
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //Array list of emails to display to the user
        groupList = new ArrayList<Users>();

        //get all my views
        selectedDifficulty = (TextView)findViewById(R.id.Difficulty);
        course = (TextView)findViewById(R.id.Course);
        date = (TextView)findViewById(R.id.date);
        day = (TextView)findViewById(R.id.day);
        mangeGroups = (TextView)findViewById(R.id.manageGroup);
        ListView list = (ListView)findViewById(R.id.GroupList);
        difficultybtn = (TextView)findViewById(R.id.SelectDifficulty);
        startRound = (Button)findViewById(R.id.StartRoundbtn);


        //Will probably never be null
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //Get the extras if there are any
        if(extras != null){
            Log.e("Extras", extras.toString());
            if(extras.containsKey("Difficulty")){
                Difficulty = extras.get("Difficulty").toString();
                selectedDifficulty.setText(Difficulty);
                Log.e("GameSetUpActivity", Difficulty);
            }else{
                Log.e("GameSetUpActivity", "Difficulty Does Not exist yet");
            }
            if(extras.containsKey("courseName")){
                GolfCourse = extras.get("courseName").toString();
                //set the text to the course name
                course.setText(GolfCourse);
                Log.e("GameSetUpActivity", GolfCourse);
            }else{
                Log.e("GameSetUpActivity", "GOlfCourse Does Not exist yet");
            }

            if(extras.containsKey("group")){
                groupIDs = extras.getStringArrayList("group");
                Log.e("GameSetUpActivity", groupIDs.toString());

                myRef.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(int i=0;i<groupIDs.size();i++){
                            String id = dataSnapshot.child(groupIDs.get(i)).getKey();
                            String email = dataSnapshot.child(groupIDs.get(i)).child("email").getValue().toString();
                            Users user = new Users(id, email);
                            groupList.add(user);
                        }
                        Log.e("GameSetUpActivity", groupList.toString());
                        UserAdapter  userAdapter = new UserAdapter(getApplicationContext(), groupList);
                        list.setAdapter(userAdapter);
                        list.setTextFilterEnabled(true);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else{
                Log.e("GameSetUpActivity", "GroupIDs Does Not exist yet");
            }

        }

        //get current day
        try {
            date.setText(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Set text to current date and time
        day.setText(getDayOfTheWeek() + " " + getTimeFromAndroid());



        //Goes to ManageGroupActivity where users can select their group
        mangeGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameSetUpActivity.this, ManageGroupActivity.class);
                if(!GolfCourse.equals(null)) {
                    intent.putExtra("bundle",extras);
                    startActivity(intent);
                }
            }
        });
        //Goes To SelectDifficultyActivity
        difficultybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameSetUpActivity.this, SelectDifficultyActivity.class);
                intent.putExtra("bundle", extras);
                startActivity(intent);
            }
        });

        startRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameSetUpActivity.this, Game2Activity.class);
                
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
}
