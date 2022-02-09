package com.google.ar.sceneform.samples.src.ui.jobs;

import com.google.ar.sceneform.samples.src.model.Job;

public interface JobsPresenter {
    Job getJob(String jobId);
    Job createJob(String userId, String jobName);
    void deleteJob(String jobId);
    }
