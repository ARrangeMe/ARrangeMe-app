package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse {
    @SerializedName(value = "first_name")
    private String firstName;
    @SerializedName(value = "last_name")
    private String lastName;
    private String email;
    private String username;
    private int id;
}
