package com.voyager.boorna.activity.landing;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.appconfig.Helper;
import com.voyager.boorna.appconfig.NetworkDetector;
import com.voyager.boorna.services.LocationService;
import com.voyager.boorna.services.model.DriverDetails;
import com.voyager.boorna.webservices.ApiClient;
import com.voyager.boorna.webservices.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LandingActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    String[] perms2 = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    private static final String TAG = "LandingActivity";
    private static final int RC_STORAGE_AND_LOCATION = 122;
    UserDetails userDetails;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    Bundle bundle;
    private Handler handler = new Handler();

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    int userID = 0;
    String getLevel_code = "";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;

    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private static final int MY_PERMISSIONS_REQUEST_READ_FINE_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent intent = getIntent();
        bundle = new Bundle();

        sharedPrefs = getSharedPreferences(Helper.UserDetails,
                Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        String hiddenBtn = intent.getStringExtra("btnHiddenBtn");
        userDetails = (UserDetails) intent.getParcelableExtra("UserDetails");
        if (userDetails != null) {
            userID = userDetails.getUser_id();
            getLevel_code = userDetails.getLevel_code();
            System.out.println("LandingPage -- UserDetail- name : " + userDetails.getEmail());
            System.out.println("LandingPage -- UserDetail- Id : " + userDetails.getUser_id());
            System.out.println("LandingPage -- UserDetail- Id : " + userDetails.getLevel_code());
            System.out.println("LandingPage -- UserDetail- fcm : " + userDetails.getFcm());
        } else {
            userDetails = getUserSDetails();
        }

        userDetails = new UserDetails();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // methodRequiresTwoPermission();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.getBestProvider(criteria, true);
        // Here, thisActivity is the current activity

        checkPermissions();


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


    @AfterPermissionGranted(RC_STORAGE_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        if (EasyPermissions.hasPermissions(this, perms2)) {
            // Already have permission, do the thing
            // ...
            System.out.println("methodRequiresTwoPermission ");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();
                                System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                                Toast.makeText(getApplicationContext(), mLastLocation.getLatitude() + "," + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                                Date today = new Date();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                String dateToStr = format.format(today);
                                System.out.println(dateToStr);
                                Retrofit retrofit;
                                WebServices webServices;
                                retrofit = ApiClient.getRetrofitClient();
                                webServices = retrofit.create(WebServices.class);

                                Log.d("LoginPresenter", " validateLoginDataBaseApi : ");
                                Call<ArrayList<DriverDetails>> call = webServices.driverProfileStatus(userID, getLevel_code, mLastLocation.getLatitude(), mLastLocation.getLongitude(), dateToStr);
                                call.enqueue(new Callback<ArrayList<DriverDetails>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<DriverDetails>> call, Response<ArrayList<DriverDetails>> response) {
                                        ArrayList<DriverDetails> model = response.body();
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<DriverDetails>> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            } else {
                                //Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();
                                //showError("");
                            }
                        }
                    });

            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.putExtra("DriverUserModel", userDetails);
            startService(intent);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.storage_and_location_rationale),
                    RC_STORAGE_AND_LOCATION, perms2);
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

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {

            Snackbar.make(findViewById(android.R.id.content),
                    getResources().getString(R.string.permission_rationale),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(android.R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startLocationPermissionRequest();
                        }
                    }).show();

        } else {

            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getLastLocation();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLastLocation() {

        //iLandingPresenter.getWeatherForecastWebService(String.valueOf(latitude), String.valueOf(longitude));
        System.out.println("LandingActivity getLastLocation");

        if (NetworkDetector.haveNetworkConnection(this)) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                Intent intent = new Intent(getApplicationContext(), LocationService.class);
                                intent.putExtra("DriverUserModel", userDetails);
                                startService(intent);
                                mLastLocation = task.getResult();
                                System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                                Date today = new Date();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                String dateToStr = format.format(today);
                                System.out.println(dateToStr);
                                Retrofit retrofit;
                                WebServices webServices;
                                retrofit = ApiClient.getRetrofitClient();
                                webServices = retrofit.create(WebServices.class);

                                Log.d("LoginPresenter", " validateLoginDataBaseApi : ");
                                Call<ArrayList<DriverDetails>> call = webServices.driverProfileStatus(userID, getLevel_code, mLastLocation.getLatitude(), mLastLocation.getLongitude(), dateToStr);
                                call.enqueue(new Callback<ArrayList<DriverDetails>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<DriverDetails>> call, Response<ArrayList<DriverDetails>> response) {
                                        ArrayList<DriverDetails> model = response.body();
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<DriverDetails>> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });


                                //iLandingPresenter.getWeatherForecastWebService(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()),apikey,apiForecastCount);
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();
                                //showError("");
                            }
                        }
                    });

        } else {
            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network_available), Snackbar.LENGTH_LONG).show();
            //showError("");
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                            Toast.makeText(getApplicationContext(), mLastLocation.getLatitude() + "," + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                            Date today = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                            String dateToStr = format.format(today);
                            System.out.println(dateToStr);
                            Retrofit retrofit;
                            WebServices webServices;
                            retrofit = ApiClient.getRetrofitClient();
                            webServices = retrofit.create(WebServices.class);

                            Log.d("LoginPresenter", " validateLoginDataBaseApi : ");
                            Call<ArrayList<DriverDetails>> call = webServices.driverProfileStatus(userID, getLevel_code, mLastLocation.getLatitude(), mLastLocation.getLongitude(), dateToStr);
                            call.enqueue(new Callback<ArrayList<DriverDetails>>() {
                                @Override
                                public void onResponse(Call<ArrayList<DriverDetails>> call, Response<ArrayList<DriverDetails>> response) {
                                    ArrayList<DriverDetails> model = response.body();
                                }

                                @Override
                                public void onFailure(Call<ArrayList<DriverDetails>> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            //Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();
                            //showError("");
                        }
                    }
                });

        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        intent.putExtra("DriverUserModel", userDetails);
        startService(intent);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("Driver long:" + location.getLongitude() + ",lat : " + location.getLatitude());
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String dateToStr = format.format(today);
        System.out.println(dateToStr);
        Retrofit retrofit;
        WebServices webServices;
        retrofit = ApiClient.getRetrofitClient();
        webServices = retrofit.create(WebServices.class);

        Log.d("LoginPresenter", " validateLoginDataBaseApi : ");
        Call<ArrayList<DriverDetails>> call = webServices.driverProfileStatus(userID, getLevel_code, mLastLocation.getLatitude(), mLastLocation.getLongitude(), dateToStr);
        call.enqueue(new Callback<ArrayList<DriverDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<DriverDetails>> call, Response<ArrayList<DriverDetails>> response) {
                ArrayList<DriverDetails> model = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<DriverDetails>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                if (mLocation == null) {
                    startLocationUpdates();
                }
                if (mLocation != null) {

                    // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
                    //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
                } else {
                    Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
                }

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_FINE_LOCATION);

                // MY_PERMISSION_REQUEST_READ_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


    }


    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
            Log.d("reque", "--->>>>");

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_FINE_LOCATION);

            // MY_PERMISSION_REQUEST_READ_FINE_LOCATION is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    mFusedLocationClient.getLastLocation()
                            .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        Intent intent = new Intent(getApplicationContext(), LocationService.class);
                                        intent.putExtra("DriverUserModel", userDetails);
                                        startService(intent);
                                        mLastLocation = task.getResult();
                                        System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                                        Date today = new Date();
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                        String dateToStr = format.format(today);
                                        System.out.println(dateToStr);
                                        Retrofit retrofit;
                                        WebServices webServices;
                                        retrofit = ApiClient.getRetrofitClient();
                                        webServices = retrofit.create(WebServices.class);

                                        Log.d("LoginPresenter", " validateLoginDataBaseApi : ");
                                        Call<ArrayList<DriverDetails>> call = webServices.driverProfileStatus(userID, getLevel_code, mLastLocation.getLatitude(), mLastLocation.getLongitude(), dateToStr);
                                        call.enqueue(new Callback<ArrayList<DriverDetails>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<DriverDetails>> call, Response<ArrayList<DriverDetails>> response) {
                                                ArrayList<DriverDetails> model = response.body();
                                            }

                                            @Override
                                            public void onFailure(Call<ArrayList<DriverDetails>> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });


                                        //iLandingPresenter.getWeatherForecastWebService(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()),apikey,apiForecastCount);
                                    } else {
                                        Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();
                                        //showError("");
                                    }
                                }
                            });
                    Intent intent = new Intent(getApplicationContext(), LocationService.class);
                    intent.putExtra("DriverUserModel", userDetails);
                    startService(intent);

                    // permission was granted, yay! Do the contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}