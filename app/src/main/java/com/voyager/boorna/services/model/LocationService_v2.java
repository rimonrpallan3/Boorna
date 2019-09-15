package com.voyager.boorna.services.model;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.webservices.ApiClient;
import com.voyager.boorna.webservices.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationService_v2 extends Service  {
    LocationManager locationManager;
    Retrofit retrofit;
    WebServices webServices;
    UserDetails driverUserModel;
    int driverId=89;

    public LocationService_v2() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("----------------- LocationService");
        try {
            if(intent!=null) {
                driverUserModel = (UserDetails) intent.getParcelableExtra("DriverUserModel");
                if (driverUserModel != null) {
                    driverId = driverUserModel.getUser_id();
                    //System.out.println("LocationService -- DriverUserModel- name : " + driverUserModel.getDriv);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        retrofit = ApiClient.getRetrofitClient();
        webServices = retrofit.create(WebServices.class);
        //username = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE).getString(AppConstants.USER_NAME, "");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // You need to ask the user to enable the permissions
        } else {

        }
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12000, 3, LocationService.this);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

  /*  @Override
    public void onLocationChanged(Location location){
        if (location != null) {
            DriverLocDetails driverLocDetails = new DriverLocDetails();
            driverLocDetails.setDriverId(driverId);
            driverLocDetails.setDriverLat(location.getLatitude());
            driverLocDetails.setDriverLog(location.getLongitude());
            Toast.makeText(getApplicationContext(), location.getLatitude() + ","  + location.getLongitude(), Toast.LENGTH_SHORT).show();
            Log.e("Driver long: ", location.getLongitude() + "");
            System.out.println("Driver long:"+location.getLongitude()+",lat : "+location.getLatitude());
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            String dateToStr = format.format(today);
            System.out.println(dateToStr);

            Log.d("LoginPresenter", " validateLoginDataBaseApi : ");
            Call<ArrayList<DriverDetails>> call = webServices.driverProfileStatus(driverUserModel.getUser_id(),driverUserModel.getVehicle_id(),driverUserModel.getLevel_code(),location.getLatitude(), location.getLongitude(),dateToStr);
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
    }
    */
    @Override
    public void onDestroy() {
       // locationManager.removeUpdates(LocationService.this);
        super.onDestroy();
    }
}
