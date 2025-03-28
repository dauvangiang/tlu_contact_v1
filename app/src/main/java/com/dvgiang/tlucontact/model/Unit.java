package com.dvgiang.tlucontact.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Unit extends Contact implements Parcelable {
    private String address;

    public Unit() {
    }

    public Unit(int image, String name, String phone, String email, String address) {
        super(image, name, phone, email);
        this.address = address;
    }

    protected Unit(Parcel in) {
        super(in);
        address = in.readString();
    }

    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(address);
    }
}
