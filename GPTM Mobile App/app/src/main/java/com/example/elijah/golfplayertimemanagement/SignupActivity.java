package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private EditText email;
    private EditText password1;
    private EditText password2;
    private Button Signupbtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("SignupActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        email = (EditText) findViewById(R.id.signupEmail);
        password1 = (EditText) findViewById(R.id.signupPassword1);
        password2 = (EditText) findViewById(R.id.signupPassword2);
        Signupbtn = (Button) findViewById(R.id.Signupbtn);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        String Email = "";
        String Password1 = "";
        String Password2 = "";


        //User clicks sign up button and creates their account
        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password1 = password1.getText().toString();
                String Password2 = password2.getText().toString();
                if(Email.isEmpty()){
                    email.setError("Please enter an email");
                    Toast.makeText(getApplicationContext(), "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                }
                else if (Password1.isEmpty() || Password2.isEmpty()){
                    password1.setError("Enter a password");
                    Toast.makeText(getApplicationContext(), "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                }
                else if (!(Password1.equals(Password2))){
                    password1.setError("Your passwords do not match");
                    Toast.makeText(getApplicationContext(), "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                }

                else if (!(Email.isEmpty() && Password1.isEmpty() && Password2.isEmpty())){
                    mAuth.createUserWithEmailAndPassword(Email, Password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                myRef.child(mAuth.getUid()).child("email").setValue(Email);
                                myRef.child(mAuth.getUid()).child("isAdmin").setValue("false");
                                ProfileActivityIntent();

                            }
                            else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "USER ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




                }

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), PresentationActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void ProfileActivityIntent(){
        Intent intent = new Intent(SignupActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
