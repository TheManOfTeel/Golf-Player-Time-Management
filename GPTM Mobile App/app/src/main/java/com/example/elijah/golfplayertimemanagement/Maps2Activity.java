package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;


public class Maps2Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double startLat;
    private double endLat;
    private double startLng;
    private double endLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        List<LatLng> lstLatLngRoute = new LinkedList<>();
        BitmapDescriptor bitmap = bitmapDescriptorFromVector(this, R.drawable.ic_golf_course_black_24dp);


        String holeNum = getIntent().getStringExtra("holeNum");
        getIntent().getStringExtra("StartLat");
        getIntent().getStringExtra("StartLng");
        getIntent().getStringExtra("EndLat");
        getIntent().getStringExtra("EndLng");


        startLat = Double.valueOf(getIntent().getStringExtra("StartLat"));
        startLng = Double.valueOf(getIntent().getStringExtra("StartLng"));
        endLat = Double.valueOf(getIntent().getStringExtra("EndLat"));
        endLng = Double.valueOf(getIntent().getStringExtra("EndLng"));

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.resetMinMaxZoomPreference();
        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.ic_golf_course_black_24dp, null);


        // Add a marker in Sydney and move the camera
        LatLng Start = new LatLng(startLat, startLng);
        mMap.addMarker(new MarkerOptions().position(Start).title(holeNum+" Tee").icon(bitmapDescriptorFromVector(this, R.drawable.ic_nature_black_16dp)));

        LatLng End = new LatLng(endLat, endLng);
        mMap.addMarker(new MarkerOptions().position(End).title(holeNum).icon(bitmapDescriptorFromVector(this,R.drawable.ic_golf_course_black_24dp)));

        double yards = CalculationByDistance(Start,End);
        Log.e("Yards", String.valueOf(yards));
        float zoomLevel = getZoomLevel(yards);

        lstLatLngRoute.add(Start);
        lstLatLngRoute.add(End);

        zoomRoute(mMap,lstLatLngRoute);




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

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius =6967488;// radius of earth in yards
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double yards = Radius * c;
        double feet = yards*3;

        Log.e("Radius Value", String.valueOf(yards) + " " + String.valueOf(feet));

        return yards;
    }

    private int getZoomLevel(double radius){
        double scale = radius / 2000;
        return ((int) (16 - Math.log(scale) / Math.log(2)));
    }

    public void zoomRoute(GoogleMap googleMap, List<LatLng> lstLatLngRoute) {

        if (googleMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : lstLatLngRoute)
            boundsBuilder.include(latLngPoint);

        int routePadding = 100;
        LatLngBounds latLngBounds = boundsBuilder.build();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding));
    }

}
