package com.google.ar.sceneform.samples.src.ui.login;

import com.google.ar.sceneform.samples.src.model.PackingStrategy;
import com.google.ar.sceneform.samples.src.services.SortingServerService;
import com.google.gson.Gson;

import java.io.IOException;


public class LoginPresenterImpl implements LoginPresenter {
    SortingServerService service = new SortingServerService();
    Gson gson = new Gson();
    public PackingStrategy getUserByUsername(String username) {
        //TODO: validate username

        //use service to make get request
        try {
            String response = service.get("get use endpoint", "{request body}");
            PackingStrategy strategy = gson.fromJson(response, PackingStrategy.class);
            return strategy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
