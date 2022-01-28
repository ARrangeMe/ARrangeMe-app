package com.google.ar.sceneform.samples.src.ui.jobs;

import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobInfo;

public interface JobsPresenter {
    Job getJob(String jobId);
    JobInfo createJob(String userId, String jobName);
    }
