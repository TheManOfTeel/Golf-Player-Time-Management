package com.example.elijah.golfplayertimemanagement;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SelectGolfCourseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String uid;
    private TextView email;
    private ListView listView;
    private ArrayList<GolfCourse> courses = new ArrayList<>();
    private CourseAdapter courseAdapter;
    private EditText search;
    private ProgressBar progressBar;
    private SearchManager searchManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectgolfcourse);

        getSupportActionBar().setTitle("Choose your course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        search = (EditText) findViewById(R.id.GolfCourseSearch);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        listView = (ListView)findViewById(R.id.CourseList);
        progressBar.setVisibility(View.VISIBLE);
        ReadCourse();
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

       // courseAdapter = new ArrayAdapter<String>(this, android.R.layout.course_spinner, courses);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("ItemClicked", courses.get(i).toString());
                Intent intent = new Intent(SelectGolfCourseActivity.this, GolfCourseHomeActivity.class);
                intent.putExtra("Activity", "SelectGolfCourseActivity");
                intent.putExtra("GolfCourseID", courses.get(i).getID());
                startActivity(intent);
                finish();
            }
        });
    }

    public void ReadCourse(){
        myRef = database.getInstance().getReference().child("GolfCourse");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String ID = ds.getKey();
                        String name = ds.child("Name").getValue(String.class);
                        GolfCourse course = new GolfCourse(ID, name);
                        courses.add(course);
                    }

                    courseAdapter = new CourseAdapter(getApplicationContext(), courses);
                    listView.setAdapter(courseAdapter);
                    listView.setTextFilterEnabled(true);
                    search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                           courseAdapter.getFilter().filter(charSequence);




                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });



                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
           // Intent intent = new Intent(SelectGolfCourseActivity.this, PresentationActivity.class);
            //startActivity(intent);
            //finish();
        }

         */



    }

    @Override
    protected void onStop() {
        super.onStop();
        courseAdapter.clear();
    }

    //    private void loadInfo(){
//
//    myRef.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            String display = dataSnapshot.child(uid).child("email").getValue(String.class);
//            email.setText(display);
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    });
//
//    }


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
        if (item.getItemId() == android.R.id.home && mAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Backarrow pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectGolfCourseActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home && mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Backarrow pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectGolfCourseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if(item.getItemId() == R.id.signout){
            Toast.makeText(this, "Signout pressed", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            PresentationActivityIntent();
            return true;
        }
        return false;
    }




    public void PresentationActivityIntent(){
        Intent intent = new Intent(SelectGolfCourseActivity.this, PresentationActivity.class);
        startActivity(intent);
        finish();
    }

    public void GolfCourseHomeIntent(){
        Intent intent = new Intent(SelectGolfCourseActivity.this, GolfCourseHomeActivity.class);
        startActivity(intent);
        finish();
    }



}


