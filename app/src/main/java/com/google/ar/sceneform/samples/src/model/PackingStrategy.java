package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackingStrategy {
    private Container container;
    @SerializedName(value = "items_packed")
    private List<PackedItem> itemsPacked;
    @SerializedName(value = "items_not_packed")
    private List<PackedItem> itemsNotPacked;

    public PackingStrategy(Container container, List<PackedItem> itemsPacked, List<PackedItem> itemsNotPacked) {
        this.container = container;
        this.itemsPacked = itemsPacked;
        this.itemsNotPacked = itemsNotPacked;
    }

    public Container getContainer() {
        return container;
    }

    public List<PackedItem> getItemsPacked() {
        return itemsPacked;
    }

    public List<PackedItem> getItemsNotPacked() {
        return itemsNotPacked;
    }
}
