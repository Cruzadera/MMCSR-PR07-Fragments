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
    private int imgResId;

    public User(long id, String name, String email, String phoneNumber, String web, String address, int imgResId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.web = web;
        this.address = address;
        this.imgResId = imgResId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
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
        dest.writeInt(this.imgResId);
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.web = in.readString();
        this.address = in.readString();
        this.imgResId = in.readInt();
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
