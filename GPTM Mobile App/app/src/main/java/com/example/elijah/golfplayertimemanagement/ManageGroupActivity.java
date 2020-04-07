package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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
    private String Difficulty;
    private FirebaseAuth mAuth;
    private Bundle extras;
    public ArrayList<String> Group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);
        getSupportActionBar().setTitle("Select Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Group = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.groupList);
        createGroup = (Button)findViewById(R.id.createGroup);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        String Uid = mAuth.getUid();
        String myEmail = mAuth.getCurrentUser().getEmail();
        Log.e("MyEmail", myEmail);

        Intent intent = getIntent();
        extras = intent.getBundleExtra("bundle");
        if(extras != null) {
            if (extras.containsKey("courseName")) {
                GolfCourse = extras.getString("courseName");
                Log.e("ManageGroupActivity", GolfCourse);
            } else {
                Log.e("ManageGroupActivity", "Doesnt Exist");
            }
        }else{
            Log.e("ManageGroupActivity", "extras is null");
        }

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Button push", Group.toString());

                Intent intent = new Intent(ManageGroupActivity.this, GameSetUpActivity.class);
                extras.putStringArrayList("group", Group);
                intent.putExtras(extras);
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

                    Log.e("Users", users.toString());
                    icon = findViewById(R.id.addIcon);
                   if(extras.containsKey("group")){
                       Group = extras.getStringArrayList("group");
                       UserAdapter  userAdapter = new UserAdapter(getApplicationContext(), users);
                       listView.setAdapter(userAdapter);
                       listView.setTextFilterEnabled(true);
                       Log.e("Group", Group.toString());
                       for(int i = 0; i < userAdapter.getCount();i++){
                           if(Group.contains(userAdapter.getItem(i).Uid)){

                           }
                       }
                   }else{
                       UserAdapter  userAdapter = new UserAdapter(getApplicationContext(), users);
                       listView.setAdapter(userAdapter);
                       listView.setTextFilterEnabled(true);
                   }
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
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Backarrow pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ManageGroupActivity.this, GameSetUpActivity.class);
            if(extras !=null){
                intent.putExtras(extras);
            }
            intent.putExtra("courseName", GolfCourse);
            startActivity(intent);
            finish();
            return true;
        }else if(item.getItemId() == R.id.signout){
            Toast.makeText(this, "Signout pressed", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            PresentationActivityIntent();
            return true;
        }
        return false;
    }

    public void PresentationActivityIntent(){
        Intent intent = new Intent(ManageGroupActivity.this, PresentationActivity.class);
        startActivity(intent);
        finish();
    }
}
