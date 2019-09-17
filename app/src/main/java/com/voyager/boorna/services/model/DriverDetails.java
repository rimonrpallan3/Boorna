package com.voyager.boorna.services.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DriverDetails implements Parcelable {


    /**
     * driver_location_id : 1
     * driver_id : 1183
     * level_code : 1310
     * latitude : 1.00000000
     * longitude : 1.00000000
     * vehicle_id : null
     * date_time : 2019-09-05 10:00:00
     */

    private int driver_location_id;
    private int driver_id;
    private String level_code;
    private String latitude;
    private String longitude;
    private String date_time;
    /**
     * error : false
     * message : Location updated successfully
     */

    private String error;
    private String message;




    public int getDriver_location_id() {
        return driver_location_id;
    }

    public void setDriver_location_id(int driver_location_id) {
        this.driver_location_id = driver_location_id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public String getLevel_code() {
        return level_code;
    }

    public void setLevel_code(String level_code) {
        this.level_code = level_code;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }


    public DriverDetails() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.driver_location_id);
        dest.writeInt(this.driver_id);
        dest.writeString(this.level_code);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.date_time);
        dest.writeString(this.error);
        dest.writeString(this.message);
    }

    protected DriverDetails(Parcel in) {
        this.driver_location_id = in.readInt();
        this.driver_id = in.readInt();
        this.level_code = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.date_time = in.readString();
        this.error = in.readString();
        this.message = in.readString();
    }

    public static final Creator<DriverDetails> CREATOR = new Creator<DriverDetails>() {
        @Override
        public DriverDetails createFromParcel(Parcel source) {
            return new DriverDetails(source);
        }

        @Override
        public DriverDetails[] newArray(int size) {
            return new DriverDetails[size];
        }
    };
}
