package com.example.elijah.golfplayertimemanagement;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener {

    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fused;
    private Marker currentUser;
    private DatabaseReference myLocation;
    private GeoFire geoFire;
    private List<LatLng> fence;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);


        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        buildLocationRequest();
                        buildLocationCallback();
                        fused = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);

                        initArea();


                    settingGeoFire();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MapsActivity.this, "You must enable map perms", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


    }


    private void buildLocationCallback(){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                if(mMap != null){

                        geoFire.setLocation("You", new GeoLocation(locationResult.getLastLocation().getLatitude(),
                                locationResult.getLastLocation().getLongitude()), new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                if(currentUser!=null) currentUser.remove();
                                currentUser = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(locationResult.getLastLocation().getLatitude(),
                                                locationResult.getLastLocation().getLongitude())).title("You")

                                );
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUser.getPosition(), 12.0f));

                            }
                        });
                }
            }
        };
    }


    private void settingGeoFire(){
        myLocation = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(myLocation);
    }

    private void buildLocationRequest(){
        locationRequest=new LocationRequest();
        locationRequest.setPriority((LocationRequest.PRIORITY_HIGH_ACCURACY));
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }


    private void initArea(){
        fence = new ArrayList<>();
        fence.add(new LatLng(37.422, -122.044));
        //37.4220° N, 122.0841° W
        fence.add (new LatLng(37.422, -122.0841));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if(fused !=null){
            fused.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }


        for(LatLng latLng : fence){
            mMap.addCircle(new CircleOptions().center(latLng)
            .radius(500) //500m
                    .strokeColor(Color.BLUE)
            .fillColor(0x220000FF)
            .strokeWidth(5.0f)
            );

            GeoQuery gQuery = geoFire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude),
                    .50f);
            gQuery.addGeoQueryEventListener(MapsActivity.this);
        }
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onStop(){
        fused.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        sendNotification("Test", String.format("%s ENTERED ZONE", key));
        //sends user straight to player activity since user will always be at
        // this location, change location in emulator then test!!
        Intent intent = new Intent(MapsActivity.this,MainActivity.class);
        startActivity(intent);

    }


    @Override
    public void onKeyExited(String key) {
        sendNotification("Test", String.format("%s EXITED", key));
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        sendNotification("Test", String.format("%s MOVE TO AREA", key));
    }

    @Override
    public void onGeoQueryReady() {

    }


    @Override
    public void onGeoQueryError(DatabaseError error) {
            Toast.makeText(MapsActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            
    }

    private void sendNotification(String title, String content) {

        Toast.makeText(this, ""+content, Toast.LENGTH_SHORT).show();

        String NOTIFICATION_CHANNEL_ID = "edmt_multiple_location";
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My notifs",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("desc");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);




        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MapsActivity.this, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(),notification);
    }
}
