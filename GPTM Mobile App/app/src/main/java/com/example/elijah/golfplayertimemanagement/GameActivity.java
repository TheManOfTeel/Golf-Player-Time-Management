package com.example.elijah.golfplayertimemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {


       private Button nextHole;
       private Button strokeA;
       private Button strokeR;
       private TextView strokes;
       private int hole;
       private ImageView map1;
       private ImageView map2;
       private ImageView map3;
       private RadioButton holeID1;
       private RadioButton holeID2;
       private RadioButton holeID3;
       public int strokesTaken;
       private TextView parS1;
       private TextView parS2;
       private TextView parS3;
       private int off;
       private Button joinGame;
       private EditText find;
       private TextView userEmail;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference newRef;

    //private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        hole = 1;
        strokesTaken = 0;
        off =0;

        strokes = (TextView) findViewById(R.id.strokeNum);
        parS1 = (TextView) findViewById(R.id.par1);
        parS2 = (TextView) findViewById(R.id.par2);
        parS3 = (TextView) findViewById(R.id.par3);
        userEmail = (TextView) findViewById(R.id.email);

        find = (EditText) findViewById(R.id.search);

        nextHole = (Button) findViewById(R.id.next);
        strokeR = (Button) findViewById(R.id.rem);
        strokeA = (Button) findViewById(R.id.stroke);
        joinGame = (Button) findViewById(R.id.join);

        map1 = (ImageView) findViewById(R.id.map_1);
        map2 = (ImageView) findViewById(R.id.map_2);
        map3 = (ImageView) findViewById(R.id.map_3);

        holeID1 = (RadioButton) findViewById(R.id.hole_1);
        holeID2 = (RadioButton) findViewById(R.id.hole_2);
        holeID3 = (RadioButton) findViewById(R.id.hole_3);

        mAuth = FirebaseAuth.getInstance();
        //user = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        newRef = myRef;
        //myRef = database.getReference("Users").child("3Na4nFlaG5O55uVpPSkLTjgJTAa2");


        Query query = myRef;

        map1.setVisibility(View.VISIBLE);
        map2.setVisibility(View.INVISIBLE);
        map3.setVisibility(View.INVISIBLE);

        parS1.setVisibility(View.VISIBLE);
        parS2.setVisibility(View.INVISIBLE);
        parS3.setVisibility(View.INVISIBLE);

        holeID1.setChecked(true);
        holeID2.setChecked(false);
        holeID3.setChecked(false);

        holeID1.setEnabled(false);
        holeID2.setEnabled(false);
        holeID3.setEnabled(false);

        String Find = "";


        nextHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(hole>=3){
                    hole=1;
                    map1.setVisibility(View.VISIBLE);
                    map3.setVisibility(View.INVISIBLE);

                    holeID1.setChecked(true);
                    holeID2.setChecked(false);
                    holeID3.setChecked(false);

                    parS1.setVisibility(View.VISIBLE);
                    parS2.setVisibility(View.INVISIBLE);
                    parS3.setVisibility(View.INVISIBLE);

                    if(strokesTaken >7){
                        off = strokesTaken - 7;
                        Toast.makeText(getApplicationContext(), "Over par by: "+off+", better luck next time!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        off = 7 - strokesTaken ;
                        Toast.makeText(getApplicationContext(), "Below par by: "+off+"! Great work!", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    hole++;
                }




               if(hole == 2){
                   map2.setVisibility(View.VISIBLE);
                   map1.setVisibility(View.INVISIBLE);
                   map3.setVisibility(View.INVISIBLE);

                   holeID2.setChecked(true);
                   holeID1.setChecked(false);
                   holeID3.setChecked(false);

                   parS1.setVisibility(View.INVISIBLE);
                   parS2.setVisibility(View.VISIBLE);
                   parS3.setVisibility(View.INVISIBLE);

                   if(strokesTaken >4){
                       off = strokesTaken - 4;
                       Toast.makeText(getApplicationContext(), "Over par by: "+off+", better luck next time!", Toast.LENGTH_SHORT).show();
                   }
                   else{
                       off = 4 - strokesTaken ;
                       Toast.makeText(getApplicationContext(), "Below par by: "+off+"! Great work!", Toast.LENGTH_SHORT).show();
                   }

               }

               else if(hole == 3){
                    map3.setVisibility(View.VISIBLE);
                    map2.setVisibility(View.INVISIBLE);
                    map1.setVisibility(View.INVISIBLE);

                   holeID3.setChecked(true);
                   holeID1.setChecked(false);
                   holeID2.setChecked(false);

                   parS1.setVisibility(View.INVISIBLE);
                   parS2.setVisibility(View.INVISIBLE);
                   parS3.setVisibility(View.VISIBLE);


                   if(strokesTaken > 3){
                       off = strokesTaken - 3;
                       Toast.makeText(getApplicationContext(), "Over par by: "+off+", better luck next time!", Toast.LENGTH_SHORT).show();
                   }
                   else{
                       off = 3 - strokesTaken ;
                       Toast.makeText(getApplicationContext(), "Below par by: "+off+"! Great work!", Toast.LENGTH_SHORT).show();
                   }


               }

                strokesTaken = 0;
                strokes.setText(Integer.toString(strokesTaken));

            }
        });

        strokeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strokesTaken++;
                strokes.setText(Integer.toString(strokesTaken));
            }
        });
        strokeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strokesTaken--;
                if(strokesTaken<0){
                    strokesTaken = 0;
                }
                strokes.setText(Integer.toString(strokesTaken));
            }
        });

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Find = find.getText().toString();

                newRef = myRef.child(Find);


                newRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //String name = String.valueOf(dataSnapshot.child("isPlaying"));
                            String name = dataSnapshot.child("isPlaying").getValue().toString();
                            String joinedEmail = dataSnapshot.child("email").getValue().toString();
                            int host = dataSnapshot.child("hosting").getValue(Integer.class);
                            if(name.equals("true")){

                                if(host<4){
                                    userEmail.setText("Connected to " + joinedEmail + "!");
                                    find.setVisibility(View.INVISIBLE);
                                    joinGame.setVisibility(View.INVISIBLE);
                                    host++;
                                    newRef.child("hosting").setValue(host);
                                    Toast.makeText(getApplicationContext(), "Joined!", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Too many users in that game!", Toast.LENGTH_SHORT).show();
                                }



                            }
                            else{
                                Toast.makeText(getApplicationContext(), "That user is not currently playing.", Toast.LENGTH_SHORT).show();
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "That user does not exist.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GameActivity.this,PresentationActivity.class);
                        startActivity(intent);
                    }
                });



            }
        });



    }
}
