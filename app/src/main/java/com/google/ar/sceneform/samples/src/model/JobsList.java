package com.google.ar.sceneform.samples.src.model;

import java.util.List;

public class JobsList {
    private List<Job> jobs;
    public JobsList(List<Job> jobs){
        this.jobs = jobs;
    }

    public List<Job> getJobs(){ return jobs; }
    public void addJob(Job jobInfo){
        jobs.add(jobInfo);
    }
}
