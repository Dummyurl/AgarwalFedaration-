package com.LeelaGroup.AgrawalFedration.Business_Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by USer on 11-09-2017.
 */

public class ProfileImageFetchPojo {
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("message")
    @Expose
    private String message;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
