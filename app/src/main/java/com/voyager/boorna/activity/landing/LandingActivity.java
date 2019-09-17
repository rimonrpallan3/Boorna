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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.voyager.boorna.BuildConfig;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.landing.adapter.LandingAdapter;
import com.voyager.boorna.activity.landing.helper.LocationHelper;
import com.voyager.boorna.activity.landing.model.CardList;
import com.voyager.boorna.activity.landing.model.TripOtherDetails;
import com.voyager.boorna.activity.landing.model.TripPickDropLoc;
import com.voyager.boorna.activity.landing.presenter.ILandingPresenter;
import com.voyager.boorna.activity.landing.presenter.LandingPresenter;
import com.voyager.boorna.activity.landing.receiver.LocationUpdatesBroadcastReceiver;
import com.voyager.boorna.activity.landing.view.ILandingView;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.appconfig.Helper;

import java.util.ArrayList;

import static com.voyager.boorna.activity.landing.helper.LocationHelper.REQUEST_PERMISSIONS_REQUEST_CODE;
import static com.voyager.boorna.activity.landing.helper.LocationHelper.checkPermissions;
import static com.voyager.boorna.activity.landing.receiver.LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES;


public class LandingActivity extends AppCompatActivity implements ILandingView ,SharedPreferences.OnSharedPreferenceChangeListener,NavigationView.OnNavigationItemSelectedListener{

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
    ArrayList<TripPickDropLoc> tripPickDropLocs = new ArrayList<>();
    ArrayList<TripOtherDetails> tripOtherDetails = new ArrayList<>();

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView tvNavHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent intent = getIntent();
        bundle = new Bundle();

        rvLandingMainList = findViewById(R.id.rvLandingMainList);

        setupNavigation();

        iLandingPresenter = new LandingPresenter(this);
        sharedPrefs = getSharedPreferences(Helper.UserDetails, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();


        userDetails = intent.getParcelableExtra("UserDetails");
        if (userDetails != null) {
            userID = userDetails.getUser_id();
            getLevel_code = userDetails.getLevel_code();
            vehicleId = userDetails.getVehicle_id();
            //tvNavHeader.setText(userDetails.getUser_employee_name());
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
            LocationHelper.requestPermissions(TAG,R.id.drawer_layout,this);
        }else {
            requestLocationUpdates();
        }
        orderVerticalList();
        intent = new Intent();
        intent.setAction(ACTION_PROCESS_UPDATES);
        Bundle b = new Bundle();
        b.putInt("userID", userID);
        b.putString("getLevel_code", getLevel_code);
        b.putInt("vehicleId", vehicleId);
        intent.putExtras(b);
        sendBroadcast(intent);


    }

    // Setting Up One Time Navigation
    private void setupNavigation() {

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);/*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvNavHeader =headerView.findViewById(R.id.tvNavHeader);*/


        tvNavHeader = findViewById(R.id.tvNavHeader);




        navigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,mTopToolbar ,  R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

    }


    private void populateOrderList(){
        String adminInst = getString(R.string.admin_instruction);
        String customerInst = getString(R.string.admin_instruction);
        System.out.println(TAG+" -- populateOrderList: ");
        TripPickDropLoc tripPickDropLoc1 = new TripPickDropLoc("pick",R.drawable.flag_2,"BUCHAREST","30-09-2019");
        TripPickDropLoc tripPickDropLoc2 = new TripPickDropLoc("pick",R.drawable.flag,"TIMISOARA","01-10-2019");
        TripPickDropLoc tripPickDropLoc3 = new TripPickDropLoc("drop",R.drawable.flag_3,"SLOVENIA","02-10-2019");
        TripPickDropLoc tripPickDropLoc4 = new TripPickDropLoc("drop",R.drawable.flag_2,"MILAN","04-10-2019");
        tripPickDropLocs.add(tripPickDropLoc1);
        tripPickDropLocs.add(tripPickDropLoc2);
        tripPickDropLocs.add(tripPickDropLoc3);
        tripPickDropLocs.add(tripPickDropLoc4);
        TripOtherDetails otherDetails1 = new TripOtherDetails("Flammable");
        TripOtherDetails otherDetails2 = new TripOtherDetails("Explosive");
        TripOtherDetails otherDetails3 = new TripOtherDetails("Maintain 53.6 degree F ");
        tripOtherDetails.add(otherDetails1);
        tripOtherDetails.add(otherDetails2);
        tripOtherDetails.add(otherDetails3);

        CardList cardList1 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_2,
                R.drawable.arrow_point_to_down,
                R.drawable.flag,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                "1234",
                "200KM",
                "In Transit",
                "2",
                "1",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m",
                adminInst,
                customerInst,
                tripOtherDetails,
                tripPickDropLocs);

        CardList cardList2 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_3,
                R.drawable.arrow_point_to_down,
                R.drawable.flag_3,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.boorna_package,
                "1234",
                "200KM",
                "Canceled",
                "2",
                "2",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "20",
                adminInst,
                customerInst,
                tripOtherDetails,
                tripPickDropLocs);
        CardList cardList3 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_2,
                R.drawable.arrow_point_to_down,
                R.drawable.flag_3,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                "1234",
                "200KM",
                "Delivered",
                "2",
                "2",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m",
                adminInst,
                customerInst,
                tripOtherDetails,
                tripPickDropLocs);

        CardList cardList4 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_3,
                R.drawable.arrow_point_to_down,
                R.drawable.flag_2,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.boorna_package,
                "1234",
                "200KM",
                "Canceled",
                "2",
                "2",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "20",
                adminInst,
                customerInst,
                tripOtherDetails,
                tripPickDropLocs);
        CardList cardList5 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag,
                R.drawable.arrow_point_to_down,
                R.drawable.flag_2,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                "1234",
                "200KM",
                "Delivered",
                "2",
                "2",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m",
                adminInst,
                customerInst,
                tripOtherDetails,
                tripPickDropLocs);

        CardList cardList6 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag_2,
                R.drawable.arrow_point_to_down,
                R.drawable.flag,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.boorna_package,
                "1234",
                "200KM",
                "In Transit",
                "2",
                "2",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "20",
                adminInst,
                customerInst,
                tripOtherDetails,
                tripPickDropLocs);
        CardList cardList7 = new CardList(R.drawable.hash_key,
                R.drawable.arrow_point_to_up,
                R.drawable.flag,
                R.drawable.arrow_point_to_down,
                R.drawable.flag_3,
                R.drawable.boorna_package,
                R.drawable.dumbell,
                R.drawable.height,
                R.drawable.width,
                R.drawable.length,
                "1234",
                "200KM",
                "Canceled",
                "2",
                "2",
                "MILANO",
                "BUCHAREST",
                "Steal",
                "2000kg",
                "6.5m",
                "33m",
                "22m",
                adminInst,
                customerInst,
                tripOtherDetails,
                tripPickDropLocs);
        cardLists.add(cardList1);
        cardLists.add(cardList2);
        cardLists.add(cardList3);
        cardLists.add(cardList4);
        cardLists.add(cardList5);
        cardLists.add(cardList6);
        cardLists.add(cardList7);
        landingAdapter.notifyDataSetChanged();

    }




    private void orderVerticalList(){
        System.out.println(TAG+" -- orderVerticalList: ");
        // add a divider after each item for more clarity
        //rvHorizontalView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL));
        landingAdapter = new LandingAdapter(cardLists);
        rvLandingMainList.setHasFixedSize(true);
        rvLandingMainList.setLayoutManager(new LinearLayoutManager(this));
        rvLandingMainList.setAdapter(landingAdapter);
        populateOrderList();
        Gson gson = new Gson();
        String jsonString = gson.toJson(cardLists);

        System.out.println(TAG+" -- orderVerticalList: jsonString : "+jsonString);
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
        intent.setAction(ACTION_PROCESS_UPDATES);
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
                        findViewById(R.id.drawer_layout),
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:

                if (mDrawerToggle.isDrawerIndicatorEnabled()) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    tvNavHeader = findViewById(R.id.tvNavHeader);

                    tvNavHeader.setText(userDetails.getUser_employee_name());


                } else {
                    onBackPressed();
                }
                return true;

            case R.id.action_favorite:
                Toast.makeText(this, "Action clicked", Toast.LENGTH_LONG).show();
                //noinspection SimplifiableIfStatement
                return true;


        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);

        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.navAccount:
                Toast.makeText(this,"Account ",Toast.LENGTH_LONG).show();
                break;

            case R.id.navSettings:
                Toast.makeText(this,"Settings ",Toast.LENGTH_LONG).show();
                break;

            case R.id.navTrips:
                Toast.makeText(this,"Trip Details ",Toast.LENGTH_LONG).show();
                break;

            case R.id.navLogout:
                Toast.makeText(this,"Logout ",Toast.LENGTH_LONG).show();
                break;

        }
        return true;

    }
}