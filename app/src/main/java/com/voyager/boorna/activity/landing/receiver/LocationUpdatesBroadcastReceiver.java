/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.voyager.boorna.activity.landing.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;
import com.voyager.boorna.activity.landing.helper.LocationHelper;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.appconfig.Helper;
import com.voyager.boorna.services.model.DriverDetails;
import com.voyager.boorna.webservices.ApiClient;
import com.voyager.boorna.webservices.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Receiver for handling location updates.
 *
 * For apps targeting API level O
 * {@link android.app.PendingIntent#getBroadcast(Context, int, Intent, int)} should be used when
 * requesting location updates. Due to limits on background services,
 * {@link android.app.PendingIntent#getService(Context, int, Intent, int)} should not be used.
 *
 *  Note: Apps running on "O" devices (regardless of targetSdkVersion) may receive updates
 *  less frequently than the interval specified in the
 *  {@link com.google.android.gms.location.LocationRequest} when the app is no longer in the
 *  foreground.
 */
public class LocationUpdatesBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = LocationUpdatesBroadcastReceiver.class.getSimpleName();

    public static final String ACTION_PROCESS_UPDATES =
            "com.voyager.boorna.activity.landing.reciver.action" +
                    ".PROCESS_UPDATES";
    SharedPreferences sharedPrefs;
    UserDetails userDetails;
    public static  Integer userID = 0;
    public static  String getLevel_code = "";
    public static  Integer vehicleId = 0;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {

                    userID = LocationUpdatesBroadcastReceiver.userID;
                    getLevel_code = LocationUpdatesBroadcastReceiver.getLevel_code ;
                    vehicleId = LocationUpdatesBroadcastReceiver.vehicleId ;

                    System.out.println(TAG+" -- UserDetail- userID : " + userID);
                    System.out.println(TAG+" -- UserDetail- getLevel_code : " + getLevel_code);
                    System.out.println(TAG+" -- UserDetail- vehicleId : " + vehicleId);


                    List<Location> locations = result.getLocations();
                    Location location2 = result.getLastLocation();
                    String locString = "";
                    StringBuilder sb = new StringBuilder();
                    System.out.println("getLocationResultText getLongitude : "+location2.getLatitude()+"getLongitude : "+location2.getLongitude());
                    sb.append("(");
                    sb.append(location2.getLatitude());
                    sb.append(", ");
                    sb.append(location2.getLongitude());
                    sb.append(")");
                    sb.append("\n");
                    locString = sb.toString();
                    Toast.makeText(context,TAG+" "+locString,Toast.LENGTH_LONG).show();

                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    String dateToStr = format.format(today);
                    System.out.println(dateToStr);
                    Retrofit retrofit;
                    WebServices webServices;
                    retrofit = ApiClient.getRetrofitClient();
                    webServices = retrofit.create(WebServices.class);
                    Call<ArrayList<DriverDetails>> call = webServices.driverProfileStatus(userID,vehicleId,getLevel_code,location2.getLatitude(), location2.getLongitude(),dateToStr);
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

                    LocationHelper locationResultHelper = new LocationHelper(context, locations);
                    // Save the location data to SharedPreferences.
                    locationResultHelper.saveResults();
                    // Show notification with the location data.
                    //locationResultHelper.showNotification();
                    Log.i(TAG, LocationHelper.getSavedLocationResult(context));
                }
            }
        }
    }
}
