package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobsList {
    private List<JobInfo> jobs;
    public JobsList(List<JobInfo> jobs){
        this.jobs = jobs;
    }

    public List<JobInfo> getJobs(){ return jobs; }
}
