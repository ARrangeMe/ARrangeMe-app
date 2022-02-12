package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Container {
    @SerializedName(value = "container_id", alternate={"id"})
    private final int containerId;
    private double width;
    private double height;
    private double depth;
    @SerializedName(value = "used_space")
    private double usedSpace;
    @SerializedName(value = "used_weight")
    private double usedWeight;
    @SerializedName(value = "max_weight")
    private double maxWeight;


    public Container(int containerId, double width, double height, double depth) {
        this.containerId = containerId;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.usedSpace = 0;
        this.usedWeight = 0;
    }

    public int getContainerId() {
        return containerId;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(int usedSpace) {
        this.usedSpace = usedSpace;
    }

    public double getUsedWeight() {
        return usedWeight;
    }

    public void setUsedWeight(int usedWeight) {
        this.usedWeight = usedWeight;
    }


}
