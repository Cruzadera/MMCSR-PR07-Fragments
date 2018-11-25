package es.iessaladillo.pedrojoya.pr05.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String web;
    private String address;
    private Avatar avatar;
    public static long NUM_USERS = 0;

    public User(String name, String email, String phoneNumber, String web, String address, Avatar avatar) {
        this.id = NUM_USERS++;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.web = web;
        this.address = address;
        this.avatar = avatar;
    }

    public User() {
        this.id = NUM_USERS++;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.web);
        dest.writeString(this.address);
        dest.writeParcelable(this.avatar, flags);
    }

    private User(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.web = in.readString();
        this.address = in.readString();
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
