package com.example.digitalevidence.models;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String username, email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(username);
        dest.writeString(email);
    }

    public User(Parcel parcel){
        username = parcel.readString();
        email = parcel.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };

    public int describeContents() {
        return hashCode();
    }

    public String getUsername() {
        return  username;
    }
    public String getEmail() {
        return  email;
    }
}