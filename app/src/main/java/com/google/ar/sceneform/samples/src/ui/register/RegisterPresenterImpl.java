package com.google.ar.sceneform.samples.src.ui.register;

import com.google.ar.sceneform.samples.src.model.RegisterResponse;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.gson.Gson;


public class RegisterPresenterImpl implements RegisterPresenter{
    HttpRequestService service = new HttpRequestService();
    Gson gson = new Gson();
    public RegisterResponse registerUser(String username,String firstName, String lastName, String email, String password) {
        String body = "{\n" +
                "    \"first_name\": \""+firstName+"\",\n" +
                "    \"last_name\": \""+lastName+"\",\n" +
                "    \"email\": \""+email+"\",\n" +
                "    \"username\": \""+username+"\",\n" +
                "    \"password\": \""+password+"\"\n" +
                "}"; //TODO: use a string builder
        try {
            String response = service.post(Constants.registerEndpoint, body);
            return  gson.fromJson(response, RegisterResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
