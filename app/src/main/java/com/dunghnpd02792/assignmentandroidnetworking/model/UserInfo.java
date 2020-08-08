package com.dunghnpd02792.assignmentandroidnetworking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 7/30/2020
 */
public class UserInfo {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("permission")
    @Expose
    private Boolean permission;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("data")
    @Expose
    private List<UserData> userData = null;

    public UserInfo(String id, String fullName, String email, String hash, String address, String avatar, Boolean permission, List<UserData> userData) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.hash = hash;
        this.address = address;
        this.avatar = avatar;
        this.permission = permission;
        this.userData = userData;
    }

    public UserInfo(String fullName, String email, String address, String avatar) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<UserData> getUserData() {
        return userData;
    }

    public void setUserData(List<UserData> userData) {
        this.userData = userData;
    }
}
