package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class Job {
    @SerializedName(value="id", alternate={"job_id","jobId"})
    private final int jobID;
    @SerializedName(value="name", alternate={"job_name","jobName"})
    private String name;
    @SerializedName(value="items_packed")
    private List<Item> itemsPacked;
    @SerializedName(value="items_not_packed")
    private List<Item> itemsUnpacked;
    private Container container;

    public Job(int projectID,  Container container) {
        this.jobID = projectID;
        this.container = container;
        this.itemsPacked = new ArrayList<>();
        this.itemsUnpacked = new ArrayList<>();
    }

    public int getJobID() {
        return jobID;
    }

    public List<Item> getItemsPacked() {
        return itemsPacked;
    }

    public List<Item> getItemsUnpacked() {
        return itemsUnpacked;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public String getName() {
        return name;
    }
}