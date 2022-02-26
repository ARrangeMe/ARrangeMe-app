package com.google.ar.sceneform.samples.src.model;


import com.google.gson.annotations.SerializedName;
import com.threed.jpct.SimpleVector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Item {
    @SerializedName(value="item_id")
    private int itemID;
    @SerializedName(value="qr_img")
    private String qrCode;

    private double width;
    private double length;
    private double height;
    private double weight;
    private boolean isFragile;
    @SerializedName(value="item_name")
    private String name;
    private String description;


    private int orientation;

    private List<Integer> pivot;

    public Item() {}

    public Item(int itemID) {
        this.setItemID(itemID);
    }

    public Item(int itemID, double width, double length, double height, double weight, boolean isFragile, String name, String description) {
        this.setItemID(itemID);
        this.setWidth(width);
        this.setLength(length);
        this.setHeight(height);
        this.setWeight(weight);
        this.setFragile(isFragile);
        this.setName(name);
        this.setDescription(description);
    }


    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SimpleVector getPivot(){
        return new SimpleVector(pivot.get(0),pivot.get(1),pivot.get(2)); //i hope this is the right ordering  (x,y,z)
    }

    public void setPivot(List<Integer> pivot){
        this.pivot = pivot;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("item_id", itemID);
            json.put("item_name", name);
            json.put("description", description);
            json.put("width", width);
            json.put("length", length);
            json.put("height", height);
            json.put("weight", weight);
            json.put("is_fragile", isFragile ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
