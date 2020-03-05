package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ManageGroupActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private ArrayList<Users> users;
    private ListView listView;
    private ImageView icon;
    private Button createGroup;
    private String GolfCourse;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);
        ArrayList<String> Group = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.groupList);
        createGroup = (Button)findViewById(R.id.createGroup);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        String Uid = mAuth.getUid();
        String myEmail = mAuth.getCurrentUser().getEmail();
        Log.e("MyEmail", myEmail);


        if(!getIntent().getStringExtra("courseName").equals(null)) {
            GolfCourse = getIntent().getStringExtra("courseName");
            Log.e("CourseName", GolfCourse);
        }
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("Button push", Group.toString());
              String uniqueID =  myRef.child("Groups").child(GolfCourse).push().getKey();
                if(!Group.isEmpty()) {
                    myRef.child("Groups").child(GolfCourse).child(uniqueID).child(Uid).child("Score").child("Hole").child("Hole1").child("Score").setValue(0);
                    for (int i = 0; i < Group.size(); i++) {
                        myRef.child("Groups").child(GolfCourse).child(uniqueID).child(Group.get(i)).child("Score").child("Hole").child("Hole1").child("Score").setValue(0);

                    }
                }

                Intent intent = new Intent(ManageGroupActivity.this, GameSetUpActivity.class);
                intent.putExtra("group", uniqueID);
                intent.putExtra("courseName", GolfCourse);
                startActivity(intent);
                finish();
            }
        });








        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    users = new ArrayList<Users>();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String userId = ds.getKey();
                        String email = dataSnapshot.child(userId).child("email").getValue().toString();
                        if(userId.equals(Uid)){
                            Log.e("Not adding", "Not adding my ID");
                        }else {
                            Users user = new Users();
                            user.setEmail(email);
                            user.setUid(userId);
                            users.add(user);
                        }
                    }
                    UserAdapter  userAdapter = new UserAdapter(getApplicationContext(), users);
                    listView.setAdapter(userAdapter);
                    listView.setTextFilterEnabled(true);
                    Log.e("Users", users.toString());


                }else{
                    Log.e("ManageGroupActivity", "Datasnapshot doesnt exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                icon =  view.findViewById(R.id.addIcon);
                String id =  users.get(i).getUid();
                String email = users.get(i).getEmail();
                Users user = new Users(id, email);
              if(Group.contains(id)){
                  Group.remove(id);
                  icon.setImageResource(R.drawable.ic_add_black_24dp);
              }else if (!Group.contains(id)){
                  Group.add(id);
                  icon.setImageResource(R.drawable.ic_check_black_24dp);
              }
              Log.e("Id", Group.toString());
            }
        });







    }
}
