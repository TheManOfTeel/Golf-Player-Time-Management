package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Game2Activity extends AppCompatActivity implements OnMapReadyCallback {
    String CourseName;
    String GameID;
    String Difficulty;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private TextView holenum;
    private FirebaseAuth mAuth;
    private TextView Par;
    private TextView Yards;
    private int holeNum = 1;
    private Button NextHole;
    private Button BackHole;
    private Button shot;
    private TextView score;
    int playerPar = 0;
    private GoogleMap mMap;
    private RelativeLayout mapLayout;
    private SlidingUpPanelLayout slidingUpPanelLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        slidingUpPanelLayout = (SlidingUpPanelLayout)findViewById(R.id.SlidePannel);





        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        String Uid = mAuth.getUid();



        if(!getIntent().getStringExtra("CourseName").equals(null)) {
            CourseName = getIntent().getStringExtra("CourseName");
        }
        if(!getIntent().getStringExtra("GameID").equals(null)) {
            GameID = getIntent().getStringExtra("GameID");
        }
        if(!getIntent().getStringExtra("Difficulty").equals(null)) {
            Difficulty = getIntent().getStringExtra("Difficulty");
        }
        Log.e("Game2Activity", "Activity started");

        MapHole2(holeNum, mMap);
        getHoleDetails(holeNum);
        NextHole = (Button) findViewById(R.id.nexthole);
        BackHole = (Button) findViewById(R.id.backhole);
        score = (TextView)findViewById(R.id.playerPar);
        shot = (Button)findViewById(R.id.ShotName);
        if(playerPar== 0){
            shot.setText("Take Tee Shot");
        }else{
            shot.setText("Take A Stroke");
        }
        score.setText("com.example.elijah.golfplayertimemanagement.UserAdapter.Score: " + playerPar);
        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerPar +=1;
                score.setText("com.example.elijah.golfplayertimemanagement.UserAdapter.Score: " + playerPar);
                shot.setText("Take A Stroke");
                myRef.child("Games").child(GameID).child("Players").child(Uid).child("com.example.elijah.golfplayertimemanagement.UserAdapter.Score").child("Hole").child("Hole"+holeNum).setValue(playerPar);
            }
        });

    }

    private void MapHole2(int Holenum, GoogleMap mMap){
        myRef.child("GolfCourse").child(CourseName).child("Holes").child("Hole"+Holenum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("Geofence").exists()) {
                        List<LatLng> lstLatLngRoute = new LinkedList<>();
                        ArrayList<String> points = new ArrayList<>();
                        int i = 0;
                        for (DataSnapshot ds : dataSnapshot.child("Geofence").getChildren()) {
                            if(dataSnapshot.exists()) {
                                points.add(String.valueOf(i));
                                i++;
                            }
                        }
                        Log.e("Number of points", points.toString());
                        for(int j = 0; j < points.size();j++){
                           double lat = Double.parseDouble(dataSnapshot.child("Geofence").child(points.get(j)).child("lat").getValue().toString());
                           double lng = Double.parseDouble(dataSnapshot.child("Geofence").child(points.get(j)).child("lng").getValue().toString());
                           LatLng latLng = new LatLng(lat, lng);
                            lstLatLngRoute.add(latLng);
                        }
                        Log.e("long lat", lstLatLngRoute.toString());

                            Polyline polyline = Game2Activity.this.mMap.addPolyline(new PolylineOptions().clickable(true).addAll(lstLatLngRoute));
                            polyline.setTag("Hole"+holeNum);
                            getHolebounds((LinkedList) lstLatLngRoute);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getHolebounds(LinkedList lstLatLngRoute){
        LatLngBounds.Builder build = new LatLngBounds.Builder();
        for(int l = 0; l< lstLatLngRoute.size();l++){
            build.include((LatLng) lstLatLngRoute.get(l));
        }
        LatLngBounds bounds = build.build();


                if(bounds.contains(bounds.getCenter())){
                    Log.e("Yes", "This is within the bounds");
                }else{
                    Log.e("Fuck", "Fuck it is not");
                }




        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,50));

    }


    //Get the hole number
    private void getHoleDetails(int Holenum){
        myRef.child("GolfCourse").child(CourseName).child("Holes").child("Hole"+Holenum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String hole = dataSnapshot.getKey().toString();
                    String par = dataSnapshot.child(Difficulty).child("Par").getValue().toString();
                    String yards= dataSnapshot.child(Difficulty).child("Yards").getValue().toString();
                    UIHoleNum(hole, par, yards);
                    UpdateCurrentHole(hole);
                    Toast.makeText(Game2Activity.this, hole, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Game2Activity.this, "Hole does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    //UpdateHoleUI
    private void UIHoleNum(String hole, String par, String yards){
        holenum = (TextView) findViewById(R.id.hole);
        Par = (TextView) findViewById(R.id.par);
        Yards = (TextView) findViewById(R.id.yards);
        holenum.setText(hole);
        if(!par.equals("No par set")) {
            Par.setText("Par: " + par);
        }
        if(!yards.equals("No distance set")) {
            Yards.setText("Yards: " + yards);
        }
    }
    //UpdateCurrent Hole in Firebase
    private void UpdateCurrentHole(String hole){
        myRef.child("Games").child(GameID).child("CurrentHole").setValue(hole);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.resetMinMaxZoomPreference();

        Log.e("Hole Num", String.valueOf(holeNum));
        MapHole2(holeNum, mMap);

        NextHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holeNum +=1;
                //Hole Number cant be greater than 18
                if (holeNum>18){
                    holeNum = 18;
                }
                getHoleDetails(holeNum);
                playerPar =0;

                score.setText("com.example.elijah.golfplayertimemanagement.UserAdapter.Score:" +playerPar);
                shot.setText("Take Tee Shot");
                mMap.clear();

                Log.e("Hole Num", String.valueOf(holeNum));
                MapHole2(holeNum, mMap);

            }
        });

        BackHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holeNum -=1;
                //Hole number cant be less than 1
                if(holeNum == 0 || holeNum < 0){
                    holeNum =1;
                }
                //Hole Number cant be greater than 18
                if (holeNum>18){
                    holeNum = 18;
                }
                getHoleDetails(holeNum);
                mMap.clear();
                MapHole2(holeNum, mMap);
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
    public LatLng midPoint(double lat1,double lon1,double lat2,double lon2){

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);
        LatLng latLng = new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));


        return latLng;
    }

    private void MapCurrentHole(GoogleMap mMap, int holeNum){
        myRef.child("GolfCourse").child(CourseName).child("Holes").child("Hole"+holeNum).child("Geofence").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.e("ButtonClicked", "DataSnapShotExists");
                    List<LatLng> lstLatLngRoute = new LinkedList<>();
                    double startLat = 0;
                    double startLng = 0;
                    double endLat = 0;
                    double endLng = 0;
                    LatLng Start = new LatLng(startLat, startLng);
                    LatLng End = new LatLng(endLat, endLng);
                    String holeNumber = dataSnapshot.getKey();

                        Log.e("Geofence", "Exist");
                        startLat = Double.parseDouble(String.valueOf(dataSnapshot.child(String.valueOf(0)).child("lat").getValue().toString()));
                        startLng = Double.parseDouble(String.valueOf(dataSnapshot.child(String.valueOf(0)).child("lng").getValue().toString()));
                        if(startLat != 0 && startLng != 0) {
                            Start = new LatLng(startLat, startLng);
                            Log.e("StartDetails", "Start = " + Start.toString());
                            mMap.addMarker(new MarkerOptions().position(Start).title(holeNumber + " Tee").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_nature_black_16dp)));
                        }


                        endLat = Double.parseDouble(String.valueOf(dataSnapshot.child(String.valueOf(8)).child("lat").getValue().toString()));
                        endLng = Double.parseDouble(String.valueOf(dataSnapshot.child(String.valueOf(8)).child("lng").getValue().toString()));
                        if(endLat != 0 && endLng != 0) {
                            End = new LatLng(endLat, endLng);
                            Log.e("EndDetails", "End = " + End.toString());
                            mMap.addMarker(new MarkerOptions().position(End).title(holeNumber + " Hole").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_golf_course_black_24dp)));
                        }



                    if(Start.latitude != 0 && End.latitude != 0 && Start.longitude != 0 && End.longitude != 0) {
                        double yards = CalculationByDistance(Start, End);
                        Log.e("Yards", String.valueOf(yards));
                        float zoomLevel = getZoomLevel(yards);
                        Log.e("Location", startLat + "  " + startLng + " " + endLat + " " + endLng);

                        lstLatLngRoute.add(Start);
                        lstLatLngRoute.add(End);

                        zoomRoute(mMap, lstLatLngRoute);
                    }




                }else{
                    Log.e("SnapShot", "DoesNot Exitst");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }








}
