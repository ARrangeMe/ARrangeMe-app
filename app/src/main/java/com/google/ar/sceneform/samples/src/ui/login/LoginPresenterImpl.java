package com.google.ar.sceneform.samples.src.ui.login;

import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.gson.Gson;


public class LoginPresenterImpl implements LoginPresenter {
    HttpRequestService service = new HttpRequestService();
    Gson gson = new Gson();
    public JobsList getUserByUsername(String username) {
        //TODO: validate username

        //use service to make get request
        try {
            String response = service.get(Constants.jobsEndpoint, "{request body}");
            JobsList jobs = gson.fromJson(response, JobsList.class);
            return jobs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
