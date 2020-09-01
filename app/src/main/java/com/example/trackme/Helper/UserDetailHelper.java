package com.example.trackme.Helper;

public class UserDetailHelper {
    String name,email,password,code,issharing,lng,lat,imageUri;

    public UserDetailHelper(String name, String email, String password, String code, String issharing, String lng, String lat, String imageUri) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.issharing = issharing;
        this.lng = lng;
        this.lat = lat;
        this.imageUri = imageUri;
    }

    public UserDetailHelper() {
    }

}
