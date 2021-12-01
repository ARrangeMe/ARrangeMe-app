package com.google.ar.sceneform.samples.src.services;

import com.google.ar.sceneform.samples.src.model.JobsList;

//Singleton for sharing data between activitites in the app
public class SharedDataService {
    private SharedDataService() {};
    private static final SharedDataService sharedData = new SharedDataService();
    public static SharedDataService getInstance() {return sharedData;}

    // Add data fields and getters/setters below ------

    private JobsList jobsList;
    public JobsList getJobsList() {return jobsList;}
    public void setJobsList(JobsList jobsList) {this.jobsList = jobsList;}
}
