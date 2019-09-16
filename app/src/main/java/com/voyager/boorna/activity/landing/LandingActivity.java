package com.voyager.boorna.activity.landing;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.voyager.boorna.BuildConfig;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.landing.adapter.LandingAdapter;
import com.voyager.boorna.activity.landing.helper.LocationHelper;
import com.voyager.boorna.activity.landing.model.CardList;
import com.voyager.boorna.activity.landing.presenter.ILandingPresenter;
import com.voyager.boorna.activity.landing.presenter.LandingPresenter;
import com.voyager.boorna.activity.landing.receiver.LocationUpdatesBroadcastReceiver;
import com.voyager.boorna.activity.landing.view.ILandingView;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.appconfig.Helper;

import java.util.ArrayList;

import static com.voyager.boorna.activity.landing.helper.LocationHelper.REQUEST_PERMISSIONS_REQUEST_CODE;
import static com.voyager.boorna.activity.landing.helper.LocationHelper.checkPermissions;


public class LandingActivity extends AppCompatActivity implements ILandingView ,SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = LandingActivity.class.getSimpleName();
    UserDetails userDetails;
    Bundle bundle;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    int userID = 0;
    String getLevel_code = "";
    int vehicleId = 0;
    private Toolbar mTopToolbar;
    String fireBaseToken;

    ILandingPresenter iLandingPresenter;
    RecyclerView rvLandingMainList;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    // FIXME: 5/16/17
    private static final long UPDATE_INTERVAL = 10 * 1000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    // FIXME: 5/14/17
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;

    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3;

    /**
     * The entry point to Google Play Services.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    private LocationCallback locationCallback;

    public double wayLatitude = 0;
    public double wayLongitude = 0;

    LandingAdapter landingAdapter;
    ArrayList<CardList> cardLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent intent = getIntent();
        bundle = new Bundle();
        mTopToolbar = findViewById(R.id.my_toolbar);
        rvLandingMainList = findViewById(R.id.rvLandingMainList);
        setSupportActionBar(mTopToolbar);

        iLandingPresenter = new LandingPresenter(this);
        sharedPrefs = getSharedPreferences(Helper.UserDetails,
                Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        userDetails = (UserDetails) intent.getParcelableExtra("UserDetails");
        if (userDetails != null) {
            userID = userDetails.getUser_id();
            getLevel_code = userDetails.getLevel_code();
            vehicleId = userDetails.getVehicle_id();
            LocationUpdatesBroadcastReceiver.userID = userDetails.getUser_id();
            LocationUpdatesBroadcastReceiver.getLevel_code = userDetails.getLevel_code();
            LocationUpdatesBroadcastReceiver.vehicleId = userDetails.getVehicle_id();
            System.out.println(TAG+" -- UserDetail- email : " + userDetails.getEmail());
            System.out.println(TAG+" -- UserDetail- userID : " + userDetails.getUser_id());
            System.out.println(TAG+" -- UserDetail- LevelCode : " + userDetails.getLevel_code());
            System.out.println(TAG+" -- UserDetail- fcm : " + userDetails.getFcm());
            System.out.println(TAG+" -- UserDetail- vehicleId : " + userDetails.getVehicle_id());
        } else {
            userDetails = getUserSDetails();
            userID = userDetails.getUser_id();
            getLevel_code = userDetails.getLevel_code();
            vehicleId = userDetails.getVehicle_id();
            LocationUpdatesBroadcastReceiver.userID = userDetails.getUser_id();
            LocationUpdatesBroadcastReceiver.getLevel_code = userDetails.getLevel_code();
            LocationUpdatesBroadcastReceiver.vehicleId = userDetails.getVehicle_id();
        }

        createLocationRequest();
        if (!checkPermissions(getApplicationContext())) {
            LocationHelper.requestPermissions(TAG,R.id.activity_main,this);
        }else {
            requestLocationUpdates();
        }
        orderVerticalList();


    }


    private void populateOrderList(){
        CardList cardList1 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_2,
                R.drawable.flag_2,
                R.drawable.arrow_point_to_down,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                "1234",
                "200KM",
                "Status",
                "LoadCount : 2",
                "UnLoadCount : 1",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m");

        CardList cardList2 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_2,
                R.drawable.arrow_point_to_down,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                R.drawable.length,
                "1234",
                "200KM",
                "Status",
                "LoadCount : 2",
                "UnLoadCount : 1",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m");
        CardList cardList3 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_2,
                R.drawable.arrow_point_to_down,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                R.drawable.length,
                "1234",
                "200KM",
                "Status",
                "LoadCount : 2",
                "UnLoadCount : 1",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m");
        CardList cardList4 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_2,
                R.drawable.arrow_point_to_down,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                R.drawable.length,
                "1234",
                "200KM",
                "Status",
                "LoadCount : 2",
                "UnLoadCount : 1",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m");
        cardLists.add(cardList1);
        cardLists.add(cardList2);
        cardLists.add(cardList3);
        cardLists.add(cardList4);
        landingAdapter.notifyDataSetChanged();

    }




    private void orderVerticalList(){
        // add a divider after each item for more clarity
        //rvHorizontalView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL));
        landingAdapter = new LandingAdapter(cardLists);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLandingMainList.setLayoutManager(horizontalLayoutManager);
        rvLandingMainList.setAdapter(landingAdapter);
        rvLandingMainList.setLayoutFrozen(true);
        populateOrderList();
    }




    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Ensures that only one button is enabled at any time. The Start Updates button is enabled
     * if the user is not requesting location updates. The Stop Updates button is enabled if the
     * user is requesting location updates.
     */
    private void updateButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            //mRequestUpdatesButton.setEnabled(false);
            //mRemoveUpdatesButton.setEnabled(true);
        } else {
            //mRequestUpdatesButton.setEnabled(true);
            //mRemoveUpdatesButton.setEnabled(false);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateButtonsState(LocationHelper.getRequesting(this));
        //mLocationUpdatesResultView.setText(LocationHelper.getSavedLocationResult(this));
    }

    @Override
    protected void onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Toast.makeText(this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();

        mLocationRequest.setInterval(UPDATE_INTERVAL);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Sets the maximum time when batched location updates are delivered. Updates may be
        // delivered sooner than this interval.
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }


    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Handles the Remove Updates button, and requests removal of location updates.
     */
    public void removeLocationUpdates(View view) {
        Log.i(TAG, "Removing location updates");
        LocationHelper.setRequesting(this, false);
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(getPendingIntent());
    }


    /**
     * Handles the Request Updates button and requests start of location updates.
     */
    public void requestLocationUpdates() {
        try {
            Log.i(TAG, "Starting location updates");
            LocationHelper.setRequesting(this, true);
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, getPendingIntent());
        } catch (SecurityException e) {
            LocationHelper.setRequesting(this, false);
            e.printStackTrace();
        }
    }





    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Kick off the process of building and connecting
                // GoogleApiClient.
                requestLocationUpdates();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(LocationHelper.KEY_LOCATION_UPDATES_RESULT)) {
            //mLocationUpdatesResultView.setText(LocationHelper.getSavedLocationResult(this));
        } else if (s.equals(LocationHelper.KEY_LOCATION_UPDATES_REQUESTED)) {
            updateButtonsState(LocationHelper.getRequesting(this));
        }
    }
}