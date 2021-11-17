package com.google.ar.sceneform.samples.src.model;

public class PackedItem {
    private Item item;
    private int x;
    private int y;
    private int z;
    private Pivot pivot;

    public PackedItem(Item item, int x, int y, int z, Pivot pivot) {
        this.item = item;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pivot = pivot;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
}
