package com.google.ar.sceneform.samples.src.model;

import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class Job {
    private final int jobID;
    private List<Item> items;
    private Container container;

    public Job(int projectID, List<Item> items, Container container) {
        this.jobID = projectID;
        this.items = items;
        this.container = container;
    }

    public int getJobID() {
        return jobID;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}