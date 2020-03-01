package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        greeting = (TextView)findViewById(R.id.ProfileGreeting);

        greeting.setText("Hello" + "\n" + mAuth.getCurrentUser().getEmail());

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
}
