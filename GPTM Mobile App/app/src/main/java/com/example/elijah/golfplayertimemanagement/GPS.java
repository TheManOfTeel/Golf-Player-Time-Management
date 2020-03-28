package com.example.elijah.golfplayertimemanagement;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class GPS {

    Context context;
    Location mylocation;
    LocationManager locationManager;
    String provider = LocationManager.GPS_PROVIDER;

    public GPS(Context context) {
        this.context = context;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        || (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            return;
        }

       locationManager.requestLocationUpdates(provider, 10 * 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                mylocation = location;
                Log.e("OnLocationChange", location.getLatitude() + " " +location.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }


    Location getMylocation(){
        if(mylocation == null)
            if((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    || (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                Log.e("getMyLocation", locationManager.getLastKnownLocation(provider).toString());

                mylocation = locationManager.getLastKnownLocation(provider);

//            if(mylocation == null){
//                mylocation = new Location(provider);
//                mylocation.setAltitude(0);
//                mylocation.setLatitude(0);
//                mylocation.setLongitude(0);
//
//            }

        return mylocation;
    }
}
