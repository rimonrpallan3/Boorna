package com.voyager.boorna.activity.TripDetail.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.voyager.boorna.R;

import java.util.ArrayList;

/**
 * Created by User on 11-Dec-18.
 */

public class MapFrg extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    MapView mvTripDetail;
    View rootView;


    private static final String TAG = MapFrg.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    Bundle bundle;


    double lat = 26.2285;
    double log = 50.5860;
    String address = "";


    LatLng pickPosition;
    LatLng otherPosition1;
    LatLng otherPosition2;
    LatLng dropPosition;
    private GoogleMap mMap;


    public MapFrg() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        bundle = this.getArguments();
        mvTripDetail = rootView.findViewById(R.id.mvTripDetail);


        pickPosition = new LatLng(44.4268, 26.1025);
        otherPosition1 = new LatLng(45.7489, 21.2087);
        otherPosition2 = new LatLng(46.1512, 14.9955);
        dropPosition = new LatLng(45.4642, 9.1900);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());
        mvTripDetail.onCreate(savedInstanceState);
        mvTripDetail.getMapAsync(this);


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        mvTripDetail.onStart();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
        }
    }
    
    

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(rootView.findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mvTripDetail.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mvTripDetail.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mvTripDetail.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mvTripDetail.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        if (mMap != null) {


            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(pickPosition,
                            otherPosition1,
                            otherPosition2,
                            dropPosition));
            mMap.addMarker(new MarkerOptions().position(pickPosition).title("BUCHAREST"));
            mMap.addMarker(new MarkerOptions().position(otherPosition1).title("TIMISOARA"));
            mMap.addMarker(new MarkerOptions().position(otherPosition2).title("SLOVENIA"));
            mMap.addMarker(new MarkerOptions().position(dropPosition).title("MILAN"));

            // Position the map's camera near Alice Springs in the center of Australia,
            // and set the zoom factor so most of Australia shows on the screen.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46.1512, 14.9955), 4));

            // Set listeners for click events.
            googleMap.setOnPolylineClickListener(this);
            googleMap.setOnPolygonClickListener(this);
        }
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}