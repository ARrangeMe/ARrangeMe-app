package com.google.ar.sceneform.samples.src.services;

import com.google.ar.sceneform.samples.src.model.JobsList;

//Singleton for sharing data between activitites in the app
public class SharedData {
    private SharedData() {};
    private static final SharedData sharedData = new SharedData();
    public static SharedData getInstance() {return sharedData;}

    // Add data fields and getters/setters below ------

    private JobsList jobsList;
    public JobsList getJobsList() {return jobsList;}
    public void setJobsList(JobsList jobsList) {this.jobsList = jobsList;}
}
