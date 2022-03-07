package com.google.ar.sceneform.samples.src.ui.items;

import com.google.ar.sceneform.samples.src.model.Container;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.services.Constants;
import com.google.ar.sceneform.samples.src.services.HttpRequestService;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ItemsPresenterImpl implements ItemsPresenter {
    Gson gson = new Gson();
    HttpRequestService service = new HttpRequestService();

    private void commitJob() {
        try{
            SharedDataService instance = SharedDataService.getInstance();
            String commitUrl = String.format(Constants.commitJobEndpoint, instance.getJob().getJobID());
            JSONArray itemsJson = new JSONArray();
            if (instance.getJob().getItemsUnpacked().isEmpty()) {
                return;
            }
            instance.getJob().getItemsUnpacked().forEach(item -> itemsJson.put(item.toJson()));
            JSONObject json = new JSONObject();
            json.put("user_id", instance.getUser().getUserID());
            json.put("items", itemsJson);

            service.post(commitUrl, json.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Job getPackingStrategy()  {
        try {
            SharedDataService instance = SharedDataService.getInstance();
            commitJob();

            String packJobUrl = String.format(Constants.packJobEndpoint, instance.getJob().getJobID());
            JSONObject json = new JSONObject();
            json.put("user_id", instance.getUser().getUserID());
            String response = service.post(packJobUrl, json.toString());
            return gson.fromJson(response, Job.class);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
