package com.google.ar.sceneform.samples.src.ui.login;

import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.model.LoginResponse;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.gson.Gson;


public class LoginPresenterImpl implements LoginPresenter {
    HttpRequestService service = new HttpRequestService();
    Gson gson = new Gson();
    public JobsList getUserByUsername(String username) {
        String body = "{\"username\": \""+username+"\",\"password\": \"password\"}";

        //use service to make get request
        try {
            String response = service.post(Constants.loginEndpoint, body);
            LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
            return getJobsFromUserId(loginResponse.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private JobsList getJobsFromUserId(int id){
        try {
            String response = service.get("https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/users/"+id+"/jobs");
            return gson.fromJson(response, JobsList.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
