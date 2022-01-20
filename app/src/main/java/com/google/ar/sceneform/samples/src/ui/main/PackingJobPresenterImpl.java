package com.google.ar.sceneform.samples.src.ui.main;

import com.google.ar.sceneform.samples.src.model.PackingStrategy;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.gson.Gson;

import java.io.IOException;

public class PackingJobPresenterImpl implements PackingJobPresenter{
    Gson gson = new Gson();
    HttpRequestService service = new HttpRequestService();

    @Override
    public PackingStrategy getPackingStrategy()  {
        try {
            String response = service.post("https://y4ff702tki.execute-api.us-east-2.amazonaws.com/test/pack", "{}");
            return gson.fromJson(response, PackingStrategy.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
