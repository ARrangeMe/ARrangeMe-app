package com.google.ar.sceneform.samples.src.ui.jobs;

import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobInfo;
import com.google.ar.sceneform.samples.src.model.LoginResponse;
import com.google.ar.sceneform.samples.src.model.User;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.gson.Gson;

public class JobsPresenterImpl implements JobsPresenter{
    HttpRequestService service = new HttpRequestService();
    Gson gson = new Gson();

    @Override
    public Job getJob(String jobId) {
        //use service to make get request
        try {
            String url = Constants.jobsEndpoint + "/" +jobId;
            String response = service.get(url);
            Job jobResponse = gson.fromJson(response, Job.class);

            return jobResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JobInfo createJob(String userId, String jobName) {
        String body = "{\"user_id\": "+userId+",\"job_name\": \""+jobName+"\"}";

        //use service to make get request
        try {
            String response = service.post(Constants.jobsEndpoint, body);
            return gson.fromJson(response, JobInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
