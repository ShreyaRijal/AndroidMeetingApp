package com.example.hosanna.meetingsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback {
    private GoogleMap mMap;
    String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                LatLng placeSearched = place.getLatLng();
                placeName=place.getName().toString();
               Marker m = mMap.addMarker(new MarkerOptions().position(placeSearched).title(placeName));
               m.showInfoWindow();
                CameraPosition pos = new CameraPosition.Builder()
                        .target(placeSearched)
                        .zoom(16)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent i = new Intent(MapsActivity.this, CreateMeetingsActivity.class);
                        i.putExtra("place_name",placeName);
                        setResult(MapsActivity.RESULT_OK,i);
                        finish();
                        return false;
                    }
                });

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }

        });

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

        LatLng bayCampus = new LatLng(51.619267, -3.879294);
        mMap.addMarker(new MarkerOptions().position(bayCampus).title("Bay Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bayCampus));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

    }

}
