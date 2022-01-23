package com.google.ar.sceneform.samples.src.services;

import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.model.User;

//Singleton for sharing data between activitites in the app
public class SharedDataService {
    private SharedDataService() {};
    private static final SharedDataService sharedData = new SharedDataService();
    public static SharedDataService getInstance() {return sharedData;}

    // Add data fields and getters/setters below ------

    private JobsList jobsList;
    public JobsList getJobsList() {return jobsList;}
    public void setJobsList(JobsList jobsList) {this.jobsList = jobsList;}

    private Job job;
    public Job getJob() {return job;}
    public void setJob(Job job) {this.job = job;}

    private Item item;
    public Item getItem() {return item;}
    public void setItem(Item item) {this.item = item;}

    private User user;
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
