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
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;
import com.voyager.boorna.activity.landing.helper.LocationHelper;
import com.voyager.boorna.activity.login.helper.Helper;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.appconfig.AppConfig;
import com.voyager.boorna.services.model.DriverDetails;
import com.voyager.boorna.webservices.ApiClient;
import com.voyager.boorna.webservices.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
    public   Integer userID = 0;
    public   String getLevel_code = "";
    public   Integer vehicleId = 0;





    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                getLocDetails(context, intent);
            }

            if(action.equals(Intent.ACTION_SCREEN_OFF)){
                Toast.makeText(context,TAG+"Screen OFF",Toast.LENGTH_LONG).show();
                System.out.println(TAG+" --Screen OFF : " + userDetails.getUser_id());
            }
            else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Toast.makeText(context,TAG+"Screen ON",Toast.LENGTH_LONG).show();
                System.out.println(TAG+" --Screen OFF : " + userDetails.getUser_id());
            }
        }
    }

    private void getLocDetails(Context context,Intent intent){
        LocationResult result = LocationResult.extractResult(intent);
        if (result != null) {

            LocationHelper.getUserRequesting(context);
            Gson gson = new Gson();
            userDetails = gson.fromJson(LocationHelper.getUserResult(context), UserDetails.class);
            if(userDetails!=null){
                userID = userDetails.getUser_id();
                getLevel_code = userDetails.getLevel_code();
                vehicleId = userDetails.getVehicle_id();
                System.out.println(TAG+" --LocationHelper UserDetail- userID : " + userDetails.getUser_id());
                System.out.println(TAG+" --LocationHelper UserDetail- LevelCode : " + userDetails.getLevel_code());
                System.out.println(TAG+" --LocationHelper UserDetail- vehicleId : " + userDetails.getVehicle_id());
            }


            System.out.println(TAG+" -- UserDetail- userID : " + userID);
            System.out.println(TAG+" -- UserDetail- getLevel_code : " + getLevel_code);
            System.out.println(TAG+" -- UserDetail- vehicleId : " + vehicleId);


            List<Location> locations = result.getLocations();
            Location location2 = result.getLastLocation();
            String locString = "";
            StringBuilder sb = new StringBuilder();
            System.out.println("getLocationResultText getLongitude : "+location2.getLatitude()+" ,getLongitude : "+location2.getLongitude());
            sb.append("(");
            sb.append(location2.getLatitude());
            sb.append(", ");
            sb.append(location2.getLongitude());
            sb.append(")");
            sb.append("\n");
            locString = sb.toString();
            Toast.makeText(context,TAG+" "+locString,Toast.LENGTH_LONG).show();

            Date today = new Date();


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String dateToStr = format.format(today);
            System.out.println(dateToStr);
            Retrofit retrofit = new ApiClient().getRetrofitClient();
            final WebServices webServices = retrofit.create(WebServices.class);
            Observable<DriverDetails> getLoginObservable;

            getLoginObservable = webServices.driverProfileStatus2(userID,vehicleId,getLevel_code,location2.getLatitude(), location2.getLongitude(),dateToStr);

            getLoginObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getLocResult());

            LocationHelper locationResultHelper = new LocationHelper(context, locations);
            // Save the location data to SharedPreferences.
            locationResultHelper.saveResults();
            // Show notification with the location data.
             locationResultHelper.showNotification();
            Log.i(TAG, LocationHelper.getSavedLocationResult(context));
        }
    }


    private Observer<DriverDetails> getLocResult() {
        final Disposable[] disposable = new Disposable[1];
        return new Observer<DriverDetails>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable[0] = d;
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(DriverDetails value) {
                disposable[0].dispose();
            }

            @Override
            public void onError(Throwable e) {
                disposable[0].dispose();
                System.out.println(TAG+" --------- onError : "+e.getMessage());
            }

            @Override
            public void onComplete() {
                disposable[0].dispose();
                Log.d(TAG, " onComplete");
            }
        };
    }

}
