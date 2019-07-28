package com.example.root.presensi;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    Geocoder geocoder;
    List<Address> address;
    TomtomMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getAsyncMap(this);
    }

    @Override
    public void onMapReady(@NonNull TomtomMap tomtomMap) {
        Intent intent = getIntent();
        String[] latitude = intent.getStringArrayExtra("latitude");
        String[] longitude = intent.getStringArrayExtra("longitude");

        for (int i=0;i<latitude.length;i++) {
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                address = geocoder.getFromLocation(Double.valueOf(longitude[i]), Double.valueOf(latitude[i]), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            LatLng latLng = new LatLng(Double.valueOf(longitude[i]), Double.valueOf(latitude[i]));
            SimpleMarkerBalloon balloon = new SimpleMarkerBalloon(address.get(0).getAddressLine(0));
            tomtomMap.addMarker(new MarkerBuilder(latLng).markerBalloon(balloon));
            tomtomMap.centerOn(CameraPosition.builder(latLng).zoom(10).build());
        }
        tomtomMap.setMyLocationEnabled(true);
    }
}
