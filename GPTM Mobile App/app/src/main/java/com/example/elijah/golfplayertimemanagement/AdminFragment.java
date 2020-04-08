package com.example.elijah.golfplayertimemanagement;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminFragment extends Fragment {



    public String CourseName;
    private DatabaseReference myRef;
    private DatabaseReference Ref2;
    private FirebaseDatabase database;
    private FirebaseDatabase database2;
    private String holenum;
    private FirebaseAuth mAuth;
    private String GolfCourse;
    public int holeNum;
    public int hole;
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

    private Button reset;

    //private Chronometer mChrono;



    public String email;
    public Date currentTime;
    public String tLocation;
    public Map<String, Object> taskMap;
    private DateFormat df;
    private String currentTime1;
    private long elaspsed;
    public String gameID;
    public String anonNum;
    public String emailTrun;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        Bundle bundle = getArguments();

        //reqs = (Button) rootView.rootView.findViewById(R.id.req);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        database2 = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Ref2 = database2.getReference();

        CourseName = bundle.getString("courseName");
        gameID = bundle.getString("gameID");
        anonNum = bundle.getString("anonNum");


        //client = LocationServices.getFusedLocationProviderClient(this);
        //requestPermission();

        //email = mAuth.getCurrentUser().getEmail();
        if(mAuth.getCurrentUser()==null){
            email = anonNum;
        }
        else{
            email = mAuth.getCurrentUser().getEmail();
        }

        taskMap = new HashMap<>();
        df = new SimpleDateFormat("h:mm a");

       holeNum = 1;

        Ref2.child("Games").child(CourseName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holenum = dataSnapshot.child(gameID).child("Location").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        assist1 = (Button)rootView.findViewById(R.id.assist);
        food1 = (Button)rootView.findViewById(R.id.food);
        drink1 = (Button)rootView.findViewById(R.id.drink);
        balls1 = (Button)rootView.findViewById(R.id.balls);
        club1 = (Button)rootView.findViewById(R.id.club);

        rDor1 = (Button)rootView.findViewById(R.id.rDor);
        twiz1 = (Button)rootView.findViewById(R.id.twiz);
        frit1 = (Button)rootView.findViewById(R.id.frit);

        stella1 = (Button)rootView.findViewById(R.id.stella);
        busch1 = (Button)rootView.findViewById(R.id.busch);
        coke1 = (Button)rootView.findViewById(R.id.coke);
        dew1 = (Button)rootView.findViewById(R.id.dew);
        pep1 = (Button)rootView.findViewById(R.id.pep);

        iron1 = (Button)rootView.findViewById(R.id.iron);
        putt1 = (Button)rootView.findViewById(R.id.putt);
        hyb1 = (Button)rootView.findViewById(R.id.hyb);
        wood1 = (Button)rootView.findViewById(R.id.wood);
        wedge1 = (Button) rootView.findViewById(R.id.wedge);

        reset = (Button) rootView.findViewById(R.id.resetB);



        assist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDB("hi", "requires assistance!");

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

                reset.setVisibility(View.VISIBLE);
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
                reset.setVisibility(View.VISIBLE);

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
                reset.setVisibility(View.VISIBLE);


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

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetButtons();

            }
        });


        return rootView;
    }

    private void setDB(String type1, String type2) {
        myRef.child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //String email = dataSnapshot.child(Uid).child("email").getValue(String.class);


                currentTime1 = df.format(Calendar.getInstance().getTime());


               // String emailTrun = email.split("@")[0];
                if(email!=anonNum){
                    emailTrun = email.split("@")[0];
                }
                else{
                    emailTrun = email;
                }

                holeNum = Integer.decode(holenum);

                taskMap.put("User", emailTrun);
                taskMap.put("Request", type2);
                taskMap.put("Location", "Hole " + holeNum );
                taskMap.put("Time", currentTime1);
                //taskMap.put("Location", tLocation);



                myRef.child("Request").child(CourseName).push().setValue(taskMap);

                Toast.makeText(getActivity(), "Request Sent!", Toast.LENGTH_SHORT).show();

                resetButtons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Request Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void resetButtons(){
        food1.setVisibility(View.VISIBLE);
        drink1.setVisibility(View.VISIBLE);
        club1.setVisibility(View.VISIBLE);
        assist1.setVisibility(View.VISIBLE);
        balls1.setVisibility(View.VISIBLE);

        stella1.setVisibility(View.INVISIBLE);
        busch1.setVisibility(View.INVISIBLE);
        coke1.setVisibility(View.INVISIBLE);
        dew1.setVisibility(View.INVISIBLE);
        pep1.setVisibility(View.INVISIBLE);

        iron1.setVisibility(View.INVISIBLE);
        putt1.setVisibility(View.INVISIBLE);
        hyb1.setVisibility(View.INVISIBLE);
        wood1.setVisibility(View.INVISIBLE);
        wedge1.setVisibility(View.INVISIBLE);

        rDor1.setVisibility(View.INVISIBLE);
        twiz1.setVisibility(View.INVISIBLE);
        frit1.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);

    }





}
