package com.example.elijah.golfplayertimemanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Game3Activity extends AppCompatActivity  {
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private GoogleMap mMap;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private Bundle bundle;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bundle = getIntent().getBundleExtra("bundle");
        GameFragment defaultFragment = new GameFragment();

        if(bundle != null) {
            defaultFragment.setArguments(bundle);
        }else{
            defaultFragment.setArguments(savedInstanceState);
        }


        Log.e("Game2Activity", bundle.toString());





        mAuth = FirebaseAuth.getInstance();
        String Uid = mAuth.getUid();

        for(int i = 0; i<getSupportFragmentManager().getFragments().size();i++){
            getSupportFragmentManager().getFragments().get(i).setArguments(bundle);

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, defaultFragment).commit();



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;


            switch (menuItem.getItemId()){
                case R.id.NavHome:
                    selectedFragment = new GameFragment();
                    Toast.makeText(Game3Activity.this, bundle.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.NavAdmin:
                    selectedFragment = new AdminFragment();
                    Toast.makeText(Game3Activity.this, bundle.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.Navgame:
                    selectedFragment = new OverviewFragment();
                    Toast.makeText(Game3Activity.this, bundle.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.NavNext:
                    selectedFragment = new NextHoleFragment();
                    Toast.makeText(Game3Activity.this, bundle.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
            selectedFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;

        }
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

}
