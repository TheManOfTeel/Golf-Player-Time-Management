package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GolfCourseHomeActivity extends AppCompatActivity {
    private TextView header;
    private FirebaseAuth mAuth;
    private Button requestbtn;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private String CourseName;
    private Button startgameButton;
    private Button checkStatus;
    private String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golf_course_home);
        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getUid();
        header = (TextView)findViewById(R.id.WelcomeLabel);
        requestbtn = (Button)findViewById(R.id.RequestGamebtn);

        checkStatus = (Button) findViewById(R.id.CheckStatus);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        if(getIntent().getExtras() != null){
            CourseName = getIntent().getStringExtra("GolfCourseID");
            header.setText("Welcome to " + CourseName);
        }








        requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uid = mAuth.getUid();
                if(!CourseName.equals(null)) {
                    myRef.child("GameRequests").child(CourseName).child(Uid).child("request").setValue("pending");
                    requestbtn.setVisibility(View.INVISIBLE);
                    checkStatus.setText("Check Status");
                    checkStatus.setVisibility(View.VISIBLE);

                }
            }
        });

        checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CheckRequestStatus();
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //loadInfo();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()){
            case R.id.signout:
                Toast.makeText(this, "Sign out selected", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                PresentationActivityIntent();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void PresentationActivityIntent(){
        Intent intent = new Intent(GolfCourseHomeActivity.this, PresentationActivity.class);
        startActivity(intent);
        finish();
    }

    private void showUpdateDialog(String request){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflate = getLayoutInflater();

        final View dialogView = inflate.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        Button cancelbtn = (Button) dialogView.findViewById(R.id.cancelRequest);
        TextView status = (TextView)dialogView.findViewById(R.id.status);
        Button startgameButton = (Button)dialogView.findViewById(R.id.StartGame);
        Log.e("dialog", CourseName );
        Log.e("dialog", Uid );

        if(request.equals("pending")){
            status.setText("Your request is " + request);
        }else if(request.equals("true")){
            status.setText("your request has been Accepted!. Click Button Below to start your game.");
            cancelbtn.setVisibility(View.INVISIBLE);
            startgameButton.setVisibility(View.VISIBLE);
        }else {
            status.setText("your request has been denied. Golf course maybe full.");
        }




        dialogBuilder.setTitle("Request Status");
        dialogBuilder.setCancelable(true);


        AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        startgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String currentDate = sdf.format(new Date());
                String UniqueKey = myRef.child("Games").push().getKey();

                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                String currentTime= time.format(new Date());

                myRef.child("Games").child(UniqueKey).child("CurrentHole").setValue("Hole1");
                myRef.child("Games").child(UniqueKey).child("CourseID").setValue(CourseName);
                myRef.child("Games").child(UniqueKey).child("Date").setValue(currentDate);
                myRef.child("Games").child(UniqueKey).child("Time Started").setValue(currentTime);
                myRef.child("Games").child(UniqueKey).child("CurrentHole").setValue("Hole1");
                myRef.child("Games").child(UniqueKey).child("Players").child(Uid).child("Score").child("Hole").child("Hole1").setValue(0);


                alertDialog.dismiss();
                Intent intent = new Intent(GolfCourseHomeActivity.this, GameActivity.class);
                startActivity(intent);
                finish();


            }
        });


    }

    public void CheckRequestStatus(){

        myRef.child("GameRequests").child(CourseName).child(mAuth.getUid()).child("request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    String request = dataSnapshot.getValue().toString();
                    showUpdateDialog(request);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
