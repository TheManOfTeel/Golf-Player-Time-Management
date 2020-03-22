package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment implements OnMapReadyCallback {
    String CourseName;
    String GameID;
    String Difficulty;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private TextView holenum;
    private FirebaseAuth mAuth;
    private TextView Par;
    private TextView Yards;
    public int holeNum = 1;
    private Button NextHole;
    private Button BackHole;
    private Button shot;
    private TextView score;
    int playerPar = 0;
    private GoogleMap mMap;
    private Fragment myFragment;
    private GPS myGPS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
        setRetainInstance(true);


        Bundle bundle = getArguments();
        if(bundle != null) {
            CourseName = bundle.getString("courseName");
            GameID = bundle.getString("gameID");
            Difficulty = bundle.getString("Difficulty");

        }else{
            Log.e("Bundle", "Bundle is null");
        }


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        String Uid = mAuth.getUid();


        NextHole = (Button) rootView.findViewById(R.id.fragnexthole);
        BackHole = (Button) rootView.findViewById(R.id.fragbackhole);
        score = (TextView)rootView.findViewById(R.id.fragplayerPar);
        shot = (Button)rootView.findViewById(R.id.fragShotName);
        holenum = (TextView) rootView.findViewById(R.id.fraghole);
        Par = (TextView) rootView.findViewById(R.id.fragpar);
        Yards = (TextView) rootView.findViewById(R.id.fragyards);
        getHoleDetails2();


        if(playerPar== 0){
            shot.setText("Take Tee Shot");
        }else{
            shot.setText("Take A Stroke");
        }
        score.setText("Score: " + playerPar);
        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerPar +=1;
                score.setText("Score: " + playerPar);
                shot.setText("Take A Stroke");
                myRef.child("Games").child(CourseName).child(GameID).child(Uid).child("score").child("holes").child("hole"+holeNum).setValue(playerPar);
            }
        });

        return rootView;
    }







    @Override
    public void onPause() {
        super.onPause();
        Log.e("paused hole Num", String.valueOf(holeNum));
    }





    private void getHoleDetails2(){
        myRef.child("Games").child(CourseName).child(GameID).child("Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String holeNum = dataSnapshot.getValue().toString();
                Log.e("getHoleDetails","Hole"+ holeNum );
                myRef.child("GolfCourse").child(CourseName).child("Holes").child("Hole"+holeNum).child(Difficulty).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String yards = dataSnapshot.child("Par").getValue().toString();
                            String Par = dataSnapshot.child("Yards").getValue().toString();
                            Log.e("getHoleDetails", holeNum + Par + yards);
                            UIHoleNum(holeNum, Par, yards);
                            if(mMap!=null) {
                                MapCurrentHole(mMap, Integer.parseInt(holeNum));
                                MapHole2(Integer.parseInt(holeNum), mMap);
                            }

                        }else{
                            Log.e("getHoleDetails", "Snapshot doesnt exist");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                    UpdateCurrentHole(Holenum);

                    Toast.makeText(getContext(), hole, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Hole does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    //UpdateHoleUI
    private void UIHoleNum(String hole, String par, String yards){


        holenum.setText("Hole"+hole);
        if(!par.equals("No par set")) {
            Par.setText("Par: " + par);
        }
        if(!yards.equals("No distance set")) {
            Yards.setText("Yards: " + yards);
        }
    }
    //UpdateCurrent Hole in Firebase
    private void UpdateCurrentHole(int hole){
        myRef.child("Games").child(CourseName).child(GameID).child("Location").setValue(String.valueOf(hole));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.resetMinMaxZoomPreference();
      


        myRef.child("Games").child(CourseName).child(GameID).child("Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    holeNum = Integer.parseInt(dataSnapshot.getValue().toString());
                    Log.e("Hole Num", String.valueOf(holeNum));
                    getHoleDetails2();


                    NextHole.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holeNum +=1;
                            //Hole Number cant be greater than 18
                            if (holeNum>18){
                                holeNum = 18;
                            }
                            myRef.child("Games").child(CourseName).child(GameID).child("Location").setValue(holeNum).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        getHoleDetails2();
                                        playerPar =0;

                                        score.setText("Score:" +playerPar);
                                        shot.setText("Take Tee Shot");
                                        mMap.clear();

                                        Log.e("Hole Num", String.valueOf(holeNum));

                                    }
                                }
                            });



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
                            getHoleDetails2();
                            mMap.clear();
                            MapHole2(holeNum, mMap);
                            MapCurrentHole(mMap, holeNum);
                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                        if(dataSnapshot.child("Geofence").child("CourseOutline").exists()) {
                            int i = 0;
                            for (DataSnapshot ds : dataSnapshot.child("Geofence").child("CourseOutline").getChildren()) {
                                if (dataSnapshot.exists()) {
                                    points.add(String.valueOf(i));
                                    i++;
                                }
                            }
                            Log.e("Number of points", points.toString());
                            for (int j = 0; j < points.size(); j++) {
                                double lat = Double.parseDouble(dataSnapshot.child("Geofence").child("CourseOutline").child(points.get(j)).child("lat").getValue().toString());
                                double lng = Double.parseDouble(dataSnapshot.child("Geofence").child("CourseOutline").child(points.get(j)).child("lng").getValue().toString());
                                LatLng latLng = new LatLng(lat, lng);
                                lstLatLngRoute.add(latLng);
                            }
                            Log.e("long lat", lstLatLngRoute.toString());

                            Polyline polyline = GameFragment.this.mMap.addPolyline(new PolylineOptions().clickable(true).addAll(lstLatLngRoute));
                            polyline.setTag("Hole" + holeNum);
                            getHolebounds((LinkedList) lstLatLngRoute);
                            mapTap(mMap, (LinkedList) lstLatLngRoute);
                        }


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



    //Being called in the MapHole2 method
    private void mapTap(GoogleMap mMap, LinkedList lstLatLngRoute){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LatLngBounds.Builder build = new LatLngBounds.Builder();
                for(int l = 0; l< lstLatLngRoute.size();l++){
                    build.include((LatLng) lstLatLngRoute.get(l));
                }
                LatLngBounds bounds = build.build();
                if(bounds.contains(latLng)){
                    myRef.child("GolfCourse").child(CourseName).child("Holes").child("Hole"+holeNum).child("Geofence").child("Hole").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                double lat = Double.valueOf(dataSnapshot.child("lat").getValue().toString());
                                double lng = Double.valueOf(dataSnapshot.child("lng").getValue().toString());
                                LatLng holelatlng = new LatLng(lat,lng);
                                double yards =  CalculationByDistance(holelatlng, latLng);
                                Toast.makeText(getContext(),  yards+" yards away from the hole", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(getContext(), "That is not in the correct bounds", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context,@DrawableRes int vectorDrawableResourceId) {
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

    private void MapCurrentHole(GoogleMap mMap, int holeNum){
        myRef.child("GolfCourse").child(CourseName).child("Holes").child("Hole"+holeNum).child("Geofence").child("Hole").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.e("ButtonClicked", "DataSnapShotExists");
                    List<LatLng> lstLatLngRoute = new LinkedList<>();
                    double startLat = 0;
                    double startLng = 0;

                    LatLng Start = new LatLng(startLat, startLng);

                    Log.e("Geofence", "Exist");
                    startLat = Double.parseDouble(String.valueOf(dataSnapshot.child("lat").getValue().toString()));
                    startLng = Double.parseDouble(String.valueOf(dataSnapshot.child("lng").getValue().toString()));

                    Start = new LatLng(startLat, startLng);
                    Log.e("StartDetails", "Start = " + Start.toString());
                    if(getContext() != null) {
                        mMap.addMarker(new MarkerOptions().position(Start).title("Hole" + holeNum).icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_golf_course_black_24dp)));
                    }


//
//                        double yards = CalculationByDistance(Start, End);
//                        Log.e("Yards", String.valueOf(yards));
//                        float zoomLevel = getZoomLevel(yards);
//                        Log.e("Location", startLat + "  " + startLng + " " + endLat + " " + endLng);
//
//                        lstLatLngRoute.add(Start);
//                        lstLatLngRoute.add(End);
//
//                        zoomRoute(mMap, lstLatLngRoute);


                }else{
                    Log.e("SnapShot", "DoesNot Exitst");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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


}
