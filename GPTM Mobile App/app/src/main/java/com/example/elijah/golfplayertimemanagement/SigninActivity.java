package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class SigninActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;
//    private EditText email;
//    private EditText password;
//    private Button Signupbtn;
//    private TextView ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.signinTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Player Signin"));
        tabLayout.addTab(tabLayout.newTab().setText("Admin Signin"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = (ViewPager) findViewById(R.id.signinPager);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });












//        getSupportActionBar().setTitle("SigninActivity");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//        email = (EditText) findViewById(R.id.signinEmail);
//        password = (EditText) findViewById(R.id.signinPassword);
//        Signupbtn = (Button) findViewById(R.id.Signupbtn);
//        ForgotPassword = (TextView) findViewById(R.id.ForGotPassword);
//
//        mAuth=FirebaseAuth.getInstance();
//
//
//        ForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ForgotPasswordIntent();
//            }
//        });
//
//
//
//        Signupbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String Email = email.getText().toString();
//                String Password = password.getText().toString();
//
//                if(Email.isEmpty()){
//                    email.setError("Please enter an email");
//                    Toast.makeText(getApplicationContext(), "SIGN-IN FAILED", Toast.LENGTH_SHORT).show();
//                }
//                else if (Password.isEmpty() ){
//                    password.setError("Enter a password");
//                    Toast.makeText(getApplicationContext(), "SIGNIN FAILED", Toast.LENGTH_SHORT).show();
//                }
//                else if (!(Email.isEmpty() && Password.isEmpty())){
//                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(getApplicationContext(), "LOGGED IN", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(SigninActivity.this, ProfileActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//
//
//
//
//                }
//
//            }
//        });

    }

    public void ForgotPasswordIntent(){
        Intent intent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
