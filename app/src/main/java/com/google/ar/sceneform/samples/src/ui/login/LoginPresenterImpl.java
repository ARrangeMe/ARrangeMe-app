package com.google.ar.sceneform.samples.src.ui.login;

import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.model.LoginResponse;
import com.google.ar.sceneform.samples.src.model.User;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.gson.Gson;


public class LoginPresenterImpl implements LoginPresenter {
    HttpRequestService service = new HttpRequestService();
    Gson gson = new Gson();
    public JobsList getUserByUsername(String username, String password) {
        String body = "{\"username\": \""+username+"\",\"password\": \""+password+"\"}";

        //use service to make get request
        try {
            String response = service.post(Constants.loginEndpoint, body);
            LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
            //set some info about the user

            User user = SharedDataService.getInstance().getUser();
            if (user == null) {
                user = new User(0,"","",""); //TODO: instead make a request to GET user
            }
            user.setUserID(loginResponse.getUserId());
            SharedDataService.getInstance().setUser(user);
            return getJobsFromUserId(loginResponse.getUserId());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private JobsList getJobsFromUserId(int id){
        try {
            String endpoint = String.format(Constants.userJobsEndpoint, id);
            String response = service.get(endpoint);
            return gson.fromJson(response, JobsList.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
