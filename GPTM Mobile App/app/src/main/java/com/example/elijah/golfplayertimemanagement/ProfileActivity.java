package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private Button SelectGolfCourse;
    private Button GameStats;
    private TextView greeting;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Welcome " + mAuth.getCurrentUser().getEmail());



        SelectGolfCourse = (Button)findViewById(R.id.ProfileSelectCourse);
        SelectGolfCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SelectGolfCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //loadInfo();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()){
            case R.id.signout:
                Toast.makeText(this, "Sign out selected", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(ProfileActivity.this, PresentationActivity.class);
                startActivity(intent);
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
