package com.example.elijah.golfplayertimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminFragment extends Fragment {


    private Button reqs;
    String CourseName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        Bundle bundle = getArguments();

        reqs = (Button) rootView.findViewById(R.id.req);

       // Bundle bundle = getArguments();

        if(bundle != null) {
            CourseName = bundle.getString("courseName");
           // GameID = bundle.getString("gameID");
            //Difficulty = bundle.getString("Difficulty");

        }

        reqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ReqsAssistActivity.class);
                intent.putExtra("courseName", CourseName);
                //intent.putExtra("holeNum", holeNum);
                startActivity(intent);
                //finish();

            }
        });



        return rootView;
    }
}
