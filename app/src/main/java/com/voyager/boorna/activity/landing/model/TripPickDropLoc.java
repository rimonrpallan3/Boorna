package com.voyager.boorna.activity.landing.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TripPickDropLoc implements Parcelable {

    String tripCharacter;
    int mapIcon;
    String tripPlace;
    String tripDate;

    public TripPickDropLoc(String tripCharacter, int mapIcon, String tripPlace, String tripDate) {
        this.tripCharacter = tripCharacter;
        this.mapIcon = mapIcon;
        this.tripPlace = tripPlace;
        this.tripDate = tripDate;
    }

    public String getTripCharacter() {
        return tripCharacter;
    }

    public void setTripCharacter(String tripCharacter) {
        this.tripCharacter = tripCharacter;
    }

    public int getMapIcon() {
        return mapIcon;
    }

    public void setMapIcon(int mapIcon) {
        this.mapIcon = mapIcon;
    }

    public String getTripPlace() {
        return tripPlace;
    }

    public void setTripPlace(String tripPlace) {
        this.tripPlace = tripPlace;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tripCharacter);
        dest.writeInt(this.mapIcon);
        dest.writeString(this.tripPlace);
        dest.writeString(this.tripDate);
    }

    protected TripPickDropLoc(Parcel in) {
        this.tripCharacter = in.readString();
        this.mapIcon = in.readInt();
        this.tripPlace = in.readString();
        this.tripDate = in.readString();
    }

    public static final Parcelable.Creator<TripPickDropLoc> CREATOR = new Parcelable.Creator<TripPickDropLoc>() {
        @Override
        public TripPickDropLoc createFromParcel(Parcel source) {
            return new TripPickDropLoc(source);
        }

        @Override
        public TripPickDropLoc[] newArray(int size) {
            return new TripPickDropLoc[size];
        }
    };
}
