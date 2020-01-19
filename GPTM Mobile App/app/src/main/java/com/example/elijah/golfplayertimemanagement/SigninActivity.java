package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button Signupbtn;
    private TextView ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().setTitle("SigninActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        email = (EditText) findViewById(R.id.signinEmail);
        password = (EditText) findViewById(R.id.signinPassword);
        Signupbtn = (Button) findViewById(R.id.Signupbtn);
        ForgotPassword = (TextView) findViewById(R.id.ForGotPassword);

        mAuth=FirebaseAuth.getInstance();


        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordIntent();
            }
        });



        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(Email.isEmpty()){
                    email.setError("Please enter an email");
                    Toast.makeText(getApplicationContext(), "SIGN-IN FAILED", Toast.LENGTH_SHORT).show();
                }
                else if (Password.isEmpty() ){
                    password.setError("Enter a password");
                    Toast.makeText(getApplicationContext(), "SIGNIN FAILED", Toast.LENGTH_SHORT).show();
                }
                else if (!(Email.isEmpty() && Password.isEmpty())){
                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "LOGGED IN", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SigninActivity.this, ProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





                }

            }
        });

    }

    public void ForgotPasswordIntent(){
        Intent intent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
