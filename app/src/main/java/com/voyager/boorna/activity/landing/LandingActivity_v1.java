package com.voyager.boorna.activity.landing;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.appconfig.Helper;
import com.voyager.boorna.services.model.DriverDetails;
import com.voyager.boorna.webservices.ApiClient;
import com.voyager.boorna.webservices.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class LandingActivity_v1 extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, EasyPermissions.PermissionCallbacks{
    Bundle bundle;
    private Toolbar mTopToolbar;
    RecyclerView rvLandingMainList;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    UserDetails userDetails;
    int userID = 0;
    String getLevel_code = "";
    int vehicleId = 0;
    LinearLayout llMain;

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    String[] perms2 = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Intent intent = getIntent();
        bundle = new Bundle();
        mTopToolbar = findViewById(R.id.my_toolbar);
        llMain = findViewById(R.id.llMain);
        rvLandingMainList = findViewById(R.id.rvLandingMainList);
        setSupportActionBar(mTopToolbar);

        sharedPrefs = getSharedPreferences(Helper.UserDetails,
                Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        userDetails = new UserDetails();
        userDetails = (UserDetails) intent.getParcelableExtra("UserDetails");
        if (userDetails != null) {
            userID = userDetails.getUser_id();
            getLevel_code = userDetails.getLevel_code();
            vehicleId = userDetails.getVehicle_id();
            System.out.println("LandingPage -- UserDetail- name : " + userDetails.getEmail());
            System.out.println("LandingPage -- UserDetail- Id : " + userDetails.getUser_id());
            System.out.println("LandingPage -- UserDetail- Id : " + userDetails.getLevel_code());
            System.out.println("LandingPage -- UserDetail- fcm : " + userDetails.getFcm());
            System.out.println("LandingPage -- UserDetail- vehicleId : " + userDetails.getVehicle_id());
        } else {
            userDetails = getUserSDetails();
        }

        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, REQUEST_PERMISSIONS_REQUEST_CODE, perms2)
                        .setRationale(R.string.loc_perm)
                        .setPositiveButtonText(R.string.rationale_ask_ok)
                        .setNegativeButtonText(R.string.rationale_ask_cancel)
                        .setTheme(R.style.AppTheme)
                        .build());
        getLastLocation();

    }

    private UserDetails getUserSDetails() {
        Gson gson = new Gson();
        String json = sharedPrefs.getString("UserDetails", null);
        if (json != null) {
            System.out.println("-----------LandingPage uploadProfileName UserDetail" + json);
            userDetails = gson.fromJson(json, UserDetails.class);
            //emailAddress = userDetail.getEmail();
        }
        return userDetails;

    }




    @AfterPermissionGranted(REQUEST_PERMISSIONS_REQUEST_CODE)
    @Override
    public void onStart() {
        super.onStart();

        if (EasyPermissions.hasPermissions(this, perms2)) {
            // Already have permission, do the thing
            // ...
            getLastLocation();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.loc_perm),
                    REQUEST_PERMISSIONS_REQUEST_CODE, perms2);
        }

    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @AfterPermissionGranted(REQUEST_PERMISSIONS_REQUEST_CODE)
    private void getLastLocation() {

        //iLandingPresenter.getWeatherForecastWebService(String.valueOf(latitude), String.valueOf(longitude));
        System.out.println("LandingActivity getLastLocation");

        if (EasyPermissions.hasPermissions(this, perms2)) {
            // Already have permission, do the thing
            // ...

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // Permission is missing and must be requested.
                    requestLocationPermission();
                }
                // END_INCLUDE(startCamera)

            }else {
                requestLocationPermission();
            }


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.loc_perm),
                    REQUEST_PERMISSIONS_REQUEST_CODE, perms2);
        }


    }

    private void requestLocationPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // Display a SnackBar with cda button to request the missing permission.
                Snackbar.make(llMain, "Location permission required ", Snackbar.LENGTH_LONG).setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Request the permission
                        ActivityCompat.requestPermissions(getParent(),
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_PERMISSIONS_REQUEST_CODE);
                    }
                }).show();

            } else {
                Snackbar.make(llMain, "location ACCESS_COARSE_LOCATION", Snackbar.LENGTH_SHORT).show();
                // Request the permission. The result will be received in onRequestPermissionResult().
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }else {
            Snackbar.make(llMain, "location ACCESS_COARSE_LOCATION", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    @Override
    protected void onPause() {

        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
    }


    // override and call airLocation object's method by the same name
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            getLastLocation();
        }
    }

    // override and call airLocation object's method by the same name
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                // Request for camera permission.
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted. Start camera preview Activity.
                    Snackbar.make(llMain, "Permission Granted",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    getLastLocation();
                } else {
                    // Permission request was denied.
                    Snackbar.make(llMain, "Permission Denied",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        getLastLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
