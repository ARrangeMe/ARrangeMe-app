package com.google.ar.sceneform.samples.src.model;

public class PackedItem {
    private int packageId;
    private int width;
    private int height;
    private int depth;
    private int weight;
    private Pivot pivot;

    public PackedItem(int packageId, int width, int height, int depth, int weight, Pivot pivot) {
        this.packageId = packageId;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.pivot = pivot;
    }

    public int getPackageId() {
        return packageId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getWeight() {
        return weight;
    }

    public Pivot getPivot() {
        return pivot;
    }
}
