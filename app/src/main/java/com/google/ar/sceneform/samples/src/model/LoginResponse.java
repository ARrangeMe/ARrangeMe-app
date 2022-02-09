package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String message;
    @SerializedName(value = "user_id")
    private int userId;

    public int getUserId() {
        return userId;
    }
}
