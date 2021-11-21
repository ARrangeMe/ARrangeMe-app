package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Container {
    @SerializedName(value = "container_id")
    private final int containerId;
    private double width;
    private double height;
    private double depth;
    @SerializedName(value = "used_space")
    private int usedSpace;
    @SerializedName(value = "used_weight")
    private int usedWeight;
    @SerializedName(value = "packed_items")
    private List<PackedItem> packedItems;

    public Container(int containerId, double width, double height, double depth) {
        this.containerId = containerId;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.usedSpace = 0;
        this.usedWeight = 0;
        this.packedItems = new ArrayList<>();
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

    public int getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(int usedSpace) {
        this.usedSpace = usedSpace;
    }

    public int getUsedWeight() {
        return usedWeight;
    }

    public void setUsedWeight(int usedWeight) {
        this.usedWeight = usedWeight;
    }

    public List<PackedItem> getPackedItems() {
        return packedItems;
    }

    public void setPackedItems(List<PackedItem> packedItems) {
        this.packedItems = packedItems;
    }

    public void addPackedItem(PackedItem p) {
        this.packedItems.add(p);
    }

    public boolean isEmpty() {
        return this.packedItems.isEmpty();
    }
}
