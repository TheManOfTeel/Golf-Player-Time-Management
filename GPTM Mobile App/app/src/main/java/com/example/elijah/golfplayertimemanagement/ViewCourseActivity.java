package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ViewCourseActivity extends  AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String GolfCourse = "";
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        getSupportActionBar().setTitle("View Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GolfCourse = getIntent().getStringExtra("courseName");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();





        Log.e("ViewCourse", "Course Name" + GolfCourse);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);



        myRef.child("GolfCourse").child(GolfCourse).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("latitude").exists() && dataSnapshot.child("longitude").exists()) {
                  double latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                  double longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                    // Add a marker in Sydney and move the camera
                    LatLng course = new LatLng(latitude, longitude);
//                  mMap.moveCamera(CameraUpdateFactory.newLatLng(course));
                    moveToCurrentLocation(course, mMap);
                }

                int numOfHoles = 1;
                for(DataSnapshot ds: dataSnapshot.child("Holes").getChildren()){
                    List<LatLng> lstLatLngRoute = new LinkedList<>();
                    if(ds.child("Geofence").child("Hole").child("lat").exists() && ds.child("Geofence").child("Hole").child("lng").exists()) {
                        String holeNum = ds.getKey();
                        double lat = Double.parseDouble(ds.child("Geofence").child("Hole").child("lat").getValue().toString());
                        double lng = Double.parseDouble(ds.child("Geofence").child("Hole").child("lng").getValue().toString());
                        LatLng hole = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(hole).title(holeNum).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_golf_course_black_24dp)));
                        Log.e("hole", hole.toString());
                        numOfHoles++;
                    }

                }
                Log.e("NumberofHoles", String.valueOf(numOfHoles));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private void moveToCurrentLocation(LatLng currentLocation, GoogleMap mMap)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //loadInfo();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Backarrow pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ViewCourseActivity.this, GolfCourseHomeActivity.class);
            intent.putExtra("GolfCourseID", GolfCourse);
            startActivity(intent);
            finish();
            return true;
        }else if(item.getItemId() == R.id.signout){
            Toast.makeText(this, "Signout pressed", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            PresentationActivityIntent();
            return true;
        }
        return false;
    }




    public void PresentationActivityIntent(){
        Intent intent = new Intent(ViewCourseActivity.this, PresentationActivity.class);
        startActivity(intent);
        finish();
    }
}
