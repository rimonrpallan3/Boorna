package com.voyager.boorna.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 25-Apr-18.
 */

public class DriverLocDetails implements Parcelable {

    @SerializedName("driver_id")
    public int driverId;

    @SerializedName("driver_latitude")
    public double driverLat;
    @SerializedName("driver_longitude")
    public double driverLog;
    @SerializedName("error")
    public boolean isError   = Boolean.parseBoolean("");
    @SerializedName("error_msg")
    public String error_msg="";

    public DriverLocDetails() {
    }



    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public double getDriverLat() {
        return driverLat;
    }

    public void setDriverLat(double driverLat) {
        this.driverLat = driverLat;
    }

    public double getDriverLog() {
        return driverLog;
    }

    public void setDriverLog(double driverLog) {
        this.driverLog = driverLog;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.driverId);
        dest.writeDouble(this.driverLat);
        dest.writeDouble(this.driverLog);
        dest.writeByte(this.isError ? (byte) 1 : (byte) 0);
        dest.writeString(this.error_msg);
    }

    protected DriverLocDetails(Parcel in) {
        this.driverId = in.readInt();
        this.driverLat = in.readDouble();
        this.driverLog = in.readDouble();
        this.isError = in.readByte() != 0;
        this.error_msg = in.readString();
    }

    public static final Creator<DriverLocDetails> CREATOR = new Creator<DriverLocDetails>() {
        @Override
        public DriverLocDetails createFromParcel(Parcel source) {
            return new DriverLocDetails(source);
        }

        @Override
        public DriverLocDetails[] newArray(int size) {
            return new DriverLocDetails[size];
        }
    };
}
