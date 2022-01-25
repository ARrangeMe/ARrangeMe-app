package com.google.ar.sceneform.samples.src.ui.register;

import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;

public class RegisterPresenterImpl implements RegisterPresenter{
    HttpRequestService service = new HttpRequestService();
    @Override
    public void registerUser(String username) {
        String body = "{}"; //TODO: fill this out based on API contract
        try {

            String response = service.get(Constants.registerEndpoint, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
