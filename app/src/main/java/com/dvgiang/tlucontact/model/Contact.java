package com.dvgiang.tlucontact.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Contact implements Parcelable {
    private int image;
    private String name;
    private String phone;
    private String email;

    public Contact() {
    }

    public Contact(int image, String name, String phone, String email) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.phone = phone;
    }

    protected Contact(Parcel in) {
        image = in.readInt();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
    }
}
