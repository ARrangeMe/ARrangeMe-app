package com.google.ar.sceneform.samples.src.model;


import java.util.Date;

public class Item {
    private int containerID;
    private int itemID;
    private int userID;
    private int jobID;
    private Date createdDate;
    private double width;
    private double length;
    private double height;
    private double weight;
    private boolean isFragile;
    private int priority;
    private String name;
    private String description;
    private int qrCode;

    public Item() {}

    public Item(int containerID, int itemID, int userID, int jobID, Date createdDate, double width, double length, double height, double weight, boolean isFragile, int priority, String name, String description) {
        this.setContainerID(containerID);
        this.setItemID(itemID);
        this.setUserID(userID);
        this.setJobID(jobID);
        this.setCreatedDate(createdDate);
        this.setWidth(width);
        this.setLength(length);
        this.setHeight(height);
        this.setWeight(weight);
        this.setFragile(isFragile);
        this.setPriority(priority);
        this.setName(name);
        this.setDescription(description);
    }

    public int getContainerID() {
        return containerID;
    }

    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
