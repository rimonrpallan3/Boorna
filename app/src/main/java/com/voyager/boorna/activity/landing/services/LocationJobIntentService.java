package com.voyager.boorna.activity.landing.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class LocationJobIntentService extends JobIntentService {
    private static final String TAG =  LocationJobIntentService.class.getSimpleName();
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 2;
    final Handler mHandler = new Handler();
    Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());


    private LocationRequest mLocationRequest;
    private static final long UPDATE_INTERVAL = 5 * 1000;
    private LocationCallback mLocationCallback;
    private Location mLocation;
    private int mCount = 1;
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
    private boolean isStarting = false;
    private boolean shouldStop = false;


    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, LocationJobIntentService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //showToast("Job Execution Started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // showToast("Job Execution Finished");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {


        final String input = intent.getStringExtra("maxCountValue");
        createLocationRequest();

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.i(TAG, "ENTERED LocationCallback::onLocationResult");
                super.onLocationResult(locationResult);
                mLocation = locationResult.getLastLocation();
                //mTextView.setText("" + mCount + ": " + mLocation.toString());
                System.out.println(TAG +", count: "+ mCount + ": " + mLocation.toString());
                System.out.println(TAG + ", Intent input" + ": " + input);

                Date today = new Date();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                String dateToStr = format.format(today);
                System.out.println(TAG +", count: "+mCount + ", Date : "+dateToStr +", Location: " + mLocation.getLongitude()+","+mLocation.getLatitude());
                mCount++;


            }
        };



        requestLocationUpdates();

         MAIN_HANDLER = new Handler(Looper.getMainLooper());



        /*int maxCount = intent.getIntExtra("maxCountValue", -1);
        *//**
         * Suppose we want to print 1 to 1000 number with one-second interval, Each task will take time 1 sec, So here now sleeping thread for one second.
         *//*
        for (int i = 0; i < maxCount; i++) {
            Log.d(TAG, "onHandleWork: The number is: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

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



    /**
     * Handles the Request Updates button and requests start of location updates.
     */
    public void requestLocationUpdates() {
        try {
            Log.i(TAG, "Starting location updates");
            //LocationHelper.setRequesting(this, true);
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest,mLocationCallback,Looper.getMainLooper() );
        } catch (SecurityException e) {
            // LocationHelper.setRequesting(this, false);
            e.printStackTrace();
        }
    }

    // Helper for showing tests
    void showToast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LocationJobIntentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
