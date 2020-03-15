package com.example.elijah.golfplayertimemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ReqsAssistActivity extends AppCompatActivity {


    private DatabaseReference myRef;
    private DatabaseReference Ref2;
    private FirebaseDatabase database;
    private String holenum;
    private FirebaseAuth mAuth;
    private String GolfCourse;
    private int holeNum;
    private FusedLocationProviderClient client;



    private Button assist1;
    private Button food1;
    private Button drink1;
    private Button balls1;
    private Button club1;

    private Button rDor1;
    private Button frit1;
    private Button twiz1;
    private Button stella1;
    private Button busch1;
    private Button coke1;
    private Button dew1;
    private Button pep1;
    private Button wood1;
    private Button hyb1;
    private Button iron1;
    private Button putt1;
    private Button wedge1;
    private Chronometer mChrono;



    public String email;
    public Date currentTime;
    public String tLocation;
    public Map<String, Object> taskMap;
    private DateFormat df;
    private String currentTime1;
    private long elaspsed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqs_assist);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        String Uid = mAuth.getUid();
        String myEmail = mAuth.getCurrentUser().getEmail();
        Log.e("MyEmail", myEmail);
        mChrono = (Chronometer) findViewById(R.id.chrono);


        client = LocationServices.getFusedLocationProviderClient(this);
        requestPermission();

        email = mAuth.getCurrentUser().getEmail();

        taskMap = new HashMap<>();
        df = new SimpleDateFormat("h:mm a");

        holeNum = 1;


        assist1 = (Button)findViewById(R.id.assist);
        food1 = (Button)findViewById(R.id.food);
        drink1 = (Button)findViewById(R.id.drink);
        balls1 = (Button)findViewById(R.id.balls);
        club1 = (Button)findViewById(R.id.club);

        rDor1 = (Button)findViewById(R.id.rDor);
        twiz1 = (Button)findViewById(R.id.twiz);
        frit1 = (Button)findViewById(R.id.frit);

        stella1 = (Button)findViewById(R.id.stella);
        busch1 = (Button)findViewById(R.id.busch);
        coke1 = (Button)findViewById(R.id.coke);
        dew1 = (Button)findViewById(R.id.dew);
        pep1 = (Button)findViewById(R.id.pep);

        iron1 = (Button)findViewById(R.id.iron);
        putt1 = (Button)findViewById(R.id.putt);
        hyb1 = (Button)findViewById(R.id.hyb);
        wood1 = (Button)findViewById(R.id.wood);
        wedge1 = (Button)findViewById(R.id.wedge);


        if(!getIntent().getStringExtra("courseName").equals(null)) {
            GolfCourse = getIntent().getStringExtra("courseName");
            Log.e("CourseName", GolfCourse);

        }

/*
        if(getIntent().getIntExtra("holeNum", holeNum) != 0) {
            holeNum = getIntent().getIntExtra("holeNum", holeNum);
            Log.e("holeNum", holenum);

        }
*/

        assist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB(tLocation, "requires assistance!");

            }
        });

        balls1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDB("Golf Balls", "a basket of golf balls");
            }
        });

        food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                food1.setVisibility(View.INVISIBLE);
                drink1.setVisibility(View.INVISIBLE);
                club1.setVisibility(View.INVISIBLE);
                assist1.setVisibility(View.INVISIBLE);
                balls1.setVisibility(View.INVISIBLE);

                rDor1.setVisibility(View.VISIBLE);
                twiz1.setVisibility(View.VISIBLE);
                frit1.setVisibility(View.VISIBLE);

            }
        });

        rDor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Food", "cool ranch doritos");

            }
        });

        frit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Food", "fritos");

            }
        });

        twiz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Food", "twizzlers");

            }
        });



        drink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                food1.setVisibility(View.INVISIBLE);
                drink1.setVisibility(View.INVISIBLE);
                club1.setVisibility(View.INVISIBLE);
                assist1.setVisibility(View.INVISIBLE);
                balls1.setVisibility(View.INVISIBLE);

                stella1.setVisibility(View.VISIBLE);
                busch1.setVisibility(View.VISIBLE);
                coke1.setVisibility(View.VISIBLE);
                dew1.setVisibility(View.VISIBLE);
                pep1.setVisibility(View.VISIBLE);

            }
        });

        stella1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Drink", "a Stella Artois");

            }
        });

        busch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Drink", "a Busch Lite");

            }
        });

        pep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Drink", "a Dr.Pepper");

            }
        });

        coke1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Drink", "a Coke-Cola");

            }
        });

        dew1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Drink", "a Mountain Dew");

            }
        });

        club1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                food1.setVisibility(View.INVISIBLE);
                drink1.setVisibility(View.INVISIBLE);
                club1.setVisibility(View.INVISIBLE);
                assist1.setVisibility(View.INVISIBLE);
                balls1.setVisibility(View.INVISIBLE);

                iron1.setVisibility(View.VISIBLE);
                putt1.setVisibility(View.VISIBLE);
                hyb1.setVisibility(View.VISIBLE);
                wood1.setVisibility(View.VISIBLE);
                wedge1.setVisibility(View.VISIBLE);


            }
        });

        wood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Club", "a wood club");

            }
        });

        iron1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Club", "a 9 iron");

            }
        });

        wedge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Club", "a wedge");

            }
        });

        putt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Club", "a putter");

            }
        });

        hyb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("Club", "a hybrid club");

            }
        });

        if(ActivityCompat.checkSelfPermission(ReqsAssistActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        client.getLastLocation().addOnSuccessListener(ReqsAssistActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    tLocation =  location.toString().substring(14,37);
                    //Toast.makeText(ReqsAssistActivity.this, ""+tLocation, Toast.LENGTH_SHORT).show();
                    System.out.println(tLocation);
                }
            }
        });

        start();

        (new Handler()).postDelayed(this::showElapsed, 10000);

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void setDB(String type1, String type2) {
        myRef.child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override


            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //String email = dataSnapshot.child(Uid).child("email").getValue(String.class);

                currentTime1 = df.format(Calendar.getInstance().getTime());

                String emailTrun = email.split("@")[0];
                taskMap.put("User", emailTrun);
                taskMap.put("Request", type2);
                taskMap.put("Hole", holeNum );
                taskMap.put("Time", currentTime1);
                taskMap.put("Location", tLocation);


                myRef.child("Request").child(GolfCourse).push().setValue(taskMap);

                Toast.makeText(ReqsAssistActivity.this, "Request Sent!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ReqsAssistActivity.this, Game3Activity.class);

                intent.putExtra("courseName", GolfCourse);
                intent.putExtra("holeNum", holeNum);
                startActivity(intent);
                finish();

                //request.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ReqsAssistActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void start(){
        mChrono.start();
        //Toast.makeText(ReqsAssistActivity.this, mChrono.toString(), Toast.LENGTH_SHORT).show();
    }
    private void showElapsed() {
        long elapsed= SystemClock.elapsedRealtime() - mChrono.getBase();
        if( elapsed >= 10000){
            Toast.makeText(ReqsAssistActivity.this, "Your time is up!: ",
                    Toast.LENGTH_SHORT).show();
            mChrono.stop();
            //UNCOMMENT THIS IF WE WANT OVERDUE PLAYERS TO GET REPORTED
/*
            myRef.child("Overdue").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override


                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String emailTrun = email.split("@")[0];
                taskMap.put("User", emailTrun);

                taskMap.put("Hole", holeNum );
                taskMap.put("Time", currentTime1);
                taskMap.put("Location", tLocation);

                    myRef.child("Overdue").child(GolfCourse).push().setValue(taskMap);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(ReqsAssistActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                }
            });

 */

        }
        Toast.makeText(ReqsAssistActivity.this, "Elapsed milliseconds: " + elapsed,
                Toast.LENGTH_SHORT).show();
    }

}
