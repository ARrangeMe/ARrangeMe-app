package com.google.ar.sceneform.samples.src.ui.jobs;

import com.google.ar.sceneform.samples.src.model.Container;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.gson.Gson;

import org.json.JSONObject;

public class JobsPresenterImpl implements JobsPresenter{
    HttpRequestService service = new HttpRequestService();
    Gson gson = new Gson();

    @Override
    public Job getJob(String jobId) {
        //use service to make get request
        try {
            String url = String.format(Constants.commitJobEndpoint, jobId);
            String response = service.get(url);
            Job jobResponse = gson.fromJson(response, Job.class);

            return jobResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Job createJob(String userId, String jobName, Container container) {
        //use service to make get request
        try {
            JSONObject json = new JSONObject();
            json.put("user_id", userId);
            json.put("job_name", jobName);
            json.put("container", container.toJson());

            String response = service.post(Constants.jobsEndpoint, json.toString());
            return gson.fromJson(response, Job.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteJob(String jobId) {
        try {
            String url = Constants.jobsEndpoint + "/" +jobId;
            service.delete(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
