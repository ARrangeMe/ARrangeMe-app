package com.google.ar.sceneform.samples.src.ui.items;

import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.gson.Gson;

import java.io.IOException;

public class ItemsPresenterImpl implements ItemsPresenter {
    Gson gson = new Gson();
    HttpRequestService service = new HttpRequestService();

    @Override
    public Job getPackingStrategy()  {
        try {
            String response = service.post("https://y4ff702tki.execute-api.us-east-2.amazonaws.com/test/pack", "{}");
            return gson.fromJson(response, Job.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
