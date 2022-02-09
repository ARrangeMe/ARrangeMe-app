package com.google.ar.sceneform.samples.src.model;

import java.util.List;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private List<Job> projects;
    private List<Item> items;

    public User(int userID, String firstName, String lastName, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID){ this.userID = userID; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Job> getProjects() {
        return projects;
    }

    public void setProjects(List<Job> projects) {
        this.projects = projects;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}