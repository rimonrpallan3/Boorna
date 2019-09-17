package com.voyager.boorna.activity.landing.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CardList implements Parcelable {

    int ivTripCode;
    int ivFrom ;
    int ivFromFlag ;
    int ivTo;
    int ivToFlag ;
    int ivProductLoad;
    int ivProductWeight ;
    int ivProductPallets ;
    int ivProductHeight ;
    int ivProductWidth;
    int ivProductLength ;
    String tvTripCode;
    String tvTripTotDistance;
    String tvTripStatus;
    String tvLoadCnt;
    String tvUnLoadCnt ;
    String tvToPlace;
    String tvFromPlace;
    String tvProductName;
    String tvProductWeight;
    String tvProductPallets;
    String tvProductHeight;
    String tvProductWidth;
    String tvProductLength;
    String adminInstruction;
    String customerInstruction;

    public CardList(int ivTripCode, int ivFrom, int ivFromFlag, int ivTo, int ivToFlag, int ivProductLoad, int ivProductWeight, int ivProductHeight, int ivProductWidth, int ivProductLength, String tvTripCode, String tvTripTotDistance, String tvTripStatus, String tvLoadCnt, String tvUnLoadCnt, String tvToPlace, String tvFromPlace, String tvProductName, String tvProductWeight, String tvProductHeight, String tvProductWidth, String tvProductLength,String adminInstruction,String customerInstruction) {
        this.ivTripCode = ivTripCode;
        this.ivFrom = ivFrom;
        this.ivFromFlag = ivFromFlag;
        this.ivTo = ivTo;
        this.ivToFlag = ivToFlag;
        this.ivProductLoad = ivProductLoad;
        this.ivProductWeight = ivProductWeight;
        this.ivProductHeight = ivProductHeight;
        this.ivProductWidth = ivProductWidth;
        this.ivProductLength = ivProductLength;
        this.tvTripCode = tvTripCode;
        this.tvTripTotDistance = tvTripTotDistance;
        this.tvTripStatus = tvTripStatus;
        this.tvLoadCnt = tvLoadCnt;
        this.tvUnLoadCnt = tvUnLoadCnt;
        this.tvToPlace = tvToPlace;
        this.tvFromPlace = tvFromPlace;
        this.tvProductName = tvProductName;
        this.tvProductWeight = tvProductWeight;
        this.tvProductHeight = tvProductHeight;
        this.tvProductWidth = tvProductWidth;
        this.tvProductLength = tvProductLength;
        this.adminInstruction = adminInstruction;
        this.customerInstruction = customerInstruction;
    }

    public CardList(int ivTripCode, int ivFrom, int ivFromFlag, int ivTo, int ivToFlag, int ivProductLoad, int ivProductWeight, int ivProductPallets, String tvTripCode, String tvTripTotDistance, String tvTripStatus, String tvLoadCnt, String tvUnLoadCnt, String tvToPlace, String tvFromPlace, String tvProductName, String tvProductWeight, String tvProductPallets,String adminInstruction,String customerInstruction) {
        this.ivTripCode = ivTripCode;
        this.ivFrom = ivFrom;
        this.ivFromFlag = ivFromFlag;
        this.ivTo = ivTo;
        this.ivToFlag = ivToFlag;
        this.ivProductLoad = ivProductLoad;
        this.ivProductWeight = ivProductWeight;
        this.ivProductPallets = ivProductPallets;
        this.tvTripCode = tvTripCode;
        this.tvTripTotDistance = tvTripTotDistance;
        this.tvTripStatus = tvTripStatus;
        this.tvLoadCnt = tvLoadCnt;
        this.tvUnLoadCnt = tvUnLoadCnt;
        this.tvToPlace = tvToPlace;
        this.tvFromPlace = tvFromPlace;
        this.tvProductName = tvProductName;
        this.tvProductWeight = tvProductWeight;
        this.tvProductPallets = tvProductPallets;
        this.adminInstruction = adminInstruction;
        this.customerInstruction = customerInstruction;
    }

    public String getAdminInstruction() {
        return adminInstruction;
    }

    public void setAdminInstruction(String adminInstruction) {
        this.adminInstruction = adminInstruction;
    }

    public String getCustomerInstruction() {
        return customerInstruction;
    }

    public void setCustomerInstruction(String customerInstruction) {
        this.customerInstruction = customerInstruction;
    }

    public int getIvTripCode() {
        return ivTripCode;
    }

    public void setIvTripCode(int ivTripCode) {
        this.ivTripCode = ivTripCode;
    }

    public int getIvFrom() {
        return ivFrom;
    }

    public void setIvFrom(int ivFrom) {
        this.ivFrom = ivFrom;
    }

    public int getIvFromFlag() {
        return ivFromFlag;
    }

    public void setIvFromFlag(int ivFromFlag) {
        this.ivFromFlag = ivFromFlag;
    }

    public int getIvTo() {
        return ivTo;
    }

    public void setIvTo(int ivTo) {
        this.ivTo = ivTo;
    }

    public int getIvToFlag() {
        return ivToFlag;
    }

    public void setIvToFlag(int ivToFlag) {
        this.ivToFlag = ivToFlag;
    }

    public int getIvProductLoad() {
        return ivProductLoad;
    }

    public void setIvProductLoad(int ivProductLoad) {
        this.ivProductLoad = ivProductLoad;
    }

    public int getIvProductWeight() {
        return ivProductWeight;
    }

    public void setIvProductWeight(int ivProductWeight) {
        this.ivProductWeight = ivProductWeight;
    }

    public int getIvProductPallets() {
        return ivProductPallets;
    }

    public void setIvProductPallets(int ivProductPallets) {
        this.ivProductPallets = ivProductPallets;
    }

    public int getIvProductHeight() {
        return ivProductHeight;
    }

    public void setIvProductHeight(int ivProductHeight) {
        this.ivProductHeight = ivProductHeight;
    }

    public int getIvProductWidth() {
        return ivProductWidth;
    }

    public void setIvProductWidth(int ivProductWidth) {
        this.ivProductWidth = ivProductWidth;
    }

    public int getIvProductLength() {
        return ivProductLength;
    }

    public void setIvProductLength(int ivProductLength) {
        this.ivProductLength = ivProductLength;
    }

    public String getTvTripCode() {
        return tvTripCode;
    }

    public void setTvTripCode(String tvTripCode) {
        this.tvTripCode = tvTripCode;
    }

    public String getTvTripTotDistance() {
        return tvTripTotDistance;
    }

    public void setTvTripTotDistance(String tvTripTotDistance) {
        this.tvTripTotDistance = tvTripTotDistance;
    }

    public String getTvTripStatus() {
        return tvTripStatus;
    }

    public void setTvTripStatus(String tvTripStatus) {
        this.tvTripStatus = tvTripStatus;
    }

    public String getTvLoadCnt() {
        return tvLoadCnt;
    }

    public void setTvLoadCnt(String tvLoadCnt) {
        this.tvLoadCnt = tvLoadCnt;
    }

    public String getTvUnLoadCnt() {
        return tvUnLoadCnt;
    }

    public void setTvUnLoadCnt(String tvUnLoadCnt) {
        this.tvUnLoadCnt = tvUnLoadCnt;
    }

    public String getTvToPlace() {
        return tvToPlace;
    }

    public void setTvToPlace(String tvToPlace) {
        this.tvToPlace = tvToPlace;
    }

    public String getTvFromPlace() {
        return tvFromPlace;
    }

    public void setTvFromPlace(String tvFromPlace) {
        this.tvFromPlace = tvFromPlace;
    }

    public String getTvProductName() {
        return tvProductName;
    }

    public void setTvProductName(String tvProductName) {
        this.tvProductName = tvProductName;
    }

    public String getTvProductWeight() {
        return tvProductWeight;
    }

    public void setTvProductWeight(String tvProductWeight) {
        this.tvProductWeight = tvProductWeight;
    }

    public String getTvProductPallets() {
        return tvProductPallets;
    }

    public void setTvProductPallets(String tvProductPallets) {
        this.tvProductPallets = tvProductPallets;
    }

    public String getTvProductHeight() {
        return tvProductHeight;
    }

    public void setTvProductHeight(String tvProductHeight) {
        this.tvProductHeight = tvProductHeight;
    }

    public String getTvProductWidth() {
        return tvProductWidth;
    }

    public void setTvProductWidth(String tvProductWidth) {
        this.tvProductWidth = tvProductWidth;
    }

    public String getTvProductLength() {
        return tvProductLength;
    }

    public void setTvProductLength(String tvProductLength) {
        this.tvProductLength = tvProductLength;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ivTripCode);
        dest.writeInt(this.ivFrom);
        dest.writeInt(this.ivFromFlag);
        dest.writeInt(this.ivTo);
        dest.writeInt(this.ivToFlag);
        dest.writeInt(this.ivProductLoad);
        dest.writeInt(this.ivProductWeight);
        dest.writeInt(this.ivProductPallets);
        dest.writeInt(this.ivProductHeight);
        dest.writeInt(this.ivProductWidth);
        dest.writeInt(this.ivProductLength);
        dest.writeString(this.tvTripCode);
        dest.writeString(this.tvTripTotDistance);
        dest.writeString(this.tvTripStatus);
        dest.writeString(this.tvLoadCnt);
        dest.writeString(this.tvUnLoadCnt);
        dest.writeString(this.tvToPlace);
        dest.writeString(this.tvFromPlace);
        dest.writeString(this.tvProductName);
        dest.writeString(this.tvProductWeight);
        dest.writeString(this.tvProductPallets);
        dest.writeString(this.tvProductHeight);
        dest.writeString(this.tvProductWidth);
        dest.writeString(this.tvProductLength);
        dest.writeString(this.adminInstruction);
        dest.writeString(this.customerInstruction);
    }

    protected CardList(Parcel in) {
        this.ivTripCode = in.readInt();
        this.ivFrom = in.readInt();
        this.ivFromFlag = in.readInt();
        this.ivTo = in.readInt();
        this.ivToFlag = in.readInt();
        this.ivProductLoad = in.readInt();
        this.ivProductWeight = in.readInt();
        this.ivProductPallets = in.readInt();
        this.ivProductHeight = in.readInt();
        this.ivProductWidth = in.readInt();
        this.ivProductLength = in.readInt();
        this.tvTripCode = in.readString();
        this.tvTripTotDistance = in.readString();
        this.tvTripStatus = in.readString();
        this.tvLoadCnt = in.readString();
        this.tvUnLoadCnt = in.readString();
        this.tvToPlace = in.readString();
        this.tvFromPlace = in.readString();
        this.tvProductName = in.readString();
        this.tvProductWeight = in.readString();
        this.tvProductPallets = in.readString();
        this.tvProductHeight = in.readString();
        this.tvProductWidth = in.readString();
        this.tvProductLength = in.readString();
        this.adminInstruction = in.readString();
        this.customerInstruction = in.readString();
    }

    public static final Creator<CardList> CREATOR = new Creator<CardList>() {
        @Override
        public CardList createFromParcel(Parcel source) {
            return new CardList(source);
        }

        @Override
        public CardList[] newArray(int size) {
            return new CardList[size];
        }
    };
}
