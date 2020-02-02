package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PresentationActivity extends AppCompatActivity {
    private Button signinbtn;
    private Button signupbtn;
    private Button toMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        signinbtn = (Button) findViewById(R.id.signin);
        signupbtn = (Button) findViewById(R.id.signup);
        toMap = (Button) findViewById(R.id.button2);

        toMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PresentationActivity.this,MapsActivity.class);
                startActivity(intent);

            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSignupActivity();

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSigninActivity();

            }
        });


    }

    public void OpenSigninActivity(){
        Intent intent = new Intent(PresentationActivity.this, SigninActivity.class);
        startActivity(intent);

    }

    public void OpenSignupActivity(){
        Intent intent = new Intent(PresentationActivity.this, SignupActivity.class);
        startActivity(intent);
    }

}
