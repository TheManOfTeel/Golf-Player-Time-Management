package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class GameSetUpActivity extends AppCompatActivity {
    private TextView mangeGroups;
    private String GolfCourse = "";
    private String groupID = "";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private ArrayList<Users> groupList;
    private Button startRound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_set_up);
        mangeGroups = (TextView)findViewById(R.id.manageGroup);
        ListView list = (ListView)findViewById(R.id.GroupList);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        groupList = new ArrayList<Users>();



        GolfCourse = getIntent().getStringExtra("courseName");
        if(!GolfCourse.isEmpty()) {
            Log.e("GameSetUp", GolfCourse.toString());
        }else{
            Log.e("GameSetUp", "CourseName equals Null");
        }
        if(getIntent() != null) {
            groupID = getIntent().getStringExtra("group");
        }



        //Goes to ManageGroupActivity where users can select their group
        mangeGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameSetUpActivity.this, ManageGroupActivity.class);
                if(!GolfCourse.equals(null)) {
                    intent.putExtra("courseName", GolfCourse);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Would not be empty if comming from the ManageGroupActivity. This populates the ListView with the group members
        if(groupID != null){
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Groups").child(GolfCourse).child(groupID).exists()){
                        for(DataSnapshot ds: dataSnapshot.child("Groups").child(GolfCourse).child(groupID).getChildren()){
                            String id = ds.getKey();
                            String email = dataSnapshot.child("Users").child(id).child("email").getValue().toString();
                            Users user = new Users(id, email);
                            groupList.add(user);
                            Log.e("emails", email);

                        }
                        UserAdapter  userAdapter = new UserAdapter(getApplicationContext(), groupList);
                        list.setAdapter(userAdapter);
                        list.setTextFilterEnabled(true);
                        Log.e("Users", groupList.toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            Toast.makeText(this, "Group ID does not exist", Toast.LENGTH_SHORT).show();
        }

    }
}
