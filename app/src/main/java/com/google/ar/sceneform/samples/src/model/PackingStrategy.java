package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackingStrategy {
    public Container container = null;
    @SerializedName(value = "items_packed")
    public List<PackedItem> itemsPacked = null;
    @SerializedName(value = "items_not_packed")
    public List<PackedItem> itemsNotPacked = null;
}
