package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayerTab extends Fragment {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button Signupbtn;
    private TextView ForgotPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playersigninfragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = (EditText) view.findViewById(R.id.PlayersigninEmail);
        password = (EditText) view.findViewById(R.id.PlayersigninPassword);
        Signupbtn = (Button) view.findViewById(R.id.PlayerSignupbtn);
        ForgotPassword = (TextView) view.findViewById(R.id.PlayerForGotPassword);

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
                                Toast.makeText(getContext(), "LOGGED IN", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
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
