package com.example.elijah.golfplayertimemanagement;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class AdminTab extends Fragment {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button Signupbtn;
    private TextView ForgotPassword;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.adminsigninfragment_two, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = (EditText) view.findViewById(R.id.AdminsigninEmail);
        password = (EditText) view.findViewById(R.id.AdminsigninPassword);
        Signupbtn = (Button) view.findViewById(R.id.AdminSignupbtn);
        ForgotPassword = (TextView) view.findViewById(R.id.AdminForGotPassword);

        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");



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
                    Toast.makeText(getContext(), "SIGN-IN FAILED", Toast.LENGTH_SHORT).show();
                }
                else if (Password.isEmpty() ){
                    password.setError("Enter a password");
                    Toast.makeText(getContext(), "SIGNIN FAILED", Toast.LENGTH_SHORT).show();
                }
                else if (!(Email.isEmpty() && Password.isEmpty())){
                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                myRef.child("isAdmin");
                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            // HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                                            String isAdmin = "";
                                            //String isAdmin = dataSnapshot.child("isAdmin").getValue(String.class);
                                            /*
                                            for(String key: dataMap.keySet()){
                                                Object data = dataMap.get(key);
                                                try{
                                                    HashMap<String, Object> userData = (HashMap<String, Object>) data;
                                                    isAdmin = userData.get("isAdmin").toString();
                                                    Log.e("Hello", isAdmin);

                                                }catch (ClassCastException cce){

                                                }
                                            }

                                             */
                                            if(isAdmin.equals("true")) {
                                                Intent intent = new Intent(getActivity(), AdminActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                Toast.makeText(getContext(), "You are Logging in as an admin", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(getContext(), "You are not an admin. Log in as a player", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{
                                Toast.makeText(getContext(), "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





                }

            }
        });




    }
    public void ForgotPasswordIntent(){
        Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

}