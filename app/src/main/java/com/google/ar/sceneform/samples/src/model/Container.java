package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Container {
    @SerializedName(value = "container_id")
    public int containerId=0;
    public int width=0;
    public int height=0;
    public int depth=0;
    public int weight=0;
    @SerializedName(value = "used_space")
    public int usedSpace=0;
    @SerializedName(value = "used_weight")
    public int usedWeight=0;

}
