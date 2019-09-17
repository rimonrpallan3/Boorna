package com.voyager.boorna.activity.landing.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TripOtherDetails implements Parcelable {
    String otherDetail1;

    public TripOtherDetails() {
    }

    public TripOtherDetails(String otherDetail1) {
        this.otherDetail1 = otherDetail1;
    }

    public String getOtherDetail1() {
        return otherDetail1;
    }

    public void setOtherDetail1(String otherDetail1) {
        this.otherDetail1 = otherDetail1;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.otherDetail1);
    }

    protected TripOtherDetails(Parcel in) {
        this.otherDetail1 = in.readString();
    }

    public static final Parcelable.Creator<TripOtherDetails> CREATOR = new Parcelable.Creator<TripOtherDetails>() {
        @Override
        public TripOtherDetails createFromParcel(Parcel source) {
            return new TripOtherDetails(source);
        }

        @Override
        public TripOtherDetails[] newArray(int size) {
            return new TripOtherDetails[size];
        }
    };
}
