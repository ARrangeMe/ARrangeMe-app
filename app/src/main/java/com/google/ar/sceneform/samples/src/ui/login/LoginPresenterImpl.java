package com.google.ar.sceneform.samples.src.ui.login;

import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.model.PackingStrategy;
import com.google.ar.sceneform.samples.src.services.SortingServerService;
import com.google.gson.Gson;

import java.io.IOException;


public class LoginPresenterImpl implements LoginPresenter {
    SortingServerService service = new SortingServerService();
    Gson gson = new Gson();
    public JobsList getUserByUsername(String username) {
        //TODO: validate username

        //use service to make get request
        try {
            String response = service.get("https://y4ff702tki.execute-api.us-east-2.amazonaws.com/test/jobs", "{request body}");
            JobsList jobs = gson.fromJson(response, JobsList.class);
            return jobs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
