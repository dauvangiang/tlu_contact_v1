package com.dvgiang.tlucontact.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Staff extends Contact implements Parcelable {
    private String position;
    private String currUnit;
    public Staff(Parcel in) {
        super(in);
        position = in.readString();
        currUnit = in.readString();
    }

    public Staff(int image, String name, String phone, String email, String currUnit, String position) {
        super(image, name, phone, email);
        this.currUnit = currUnit;
        this.position = position;
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(position);
        dest.writeString(currUnit);
    }

    public String getCurrUnit() {
        return currUnit;
    }

    public void setCurrUnit(String currUnit) {
        this.currUnit = currUnit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
