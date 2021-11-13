package com.google.ar.sceneform.samples.src.services;

import com.google.ar.sceneform.samples.src.model.Container;
import com.google.ar.sceneform.samples.src.model.PackingStrategy;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SortingServerService {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public void post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {

           @Override
           public void onFailure(Call call, IOException e) {

           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               Gson gson = new Gson();
               String res = response.body().string();
               PackingStrategy strategy = gson.fromJson(res, PackingStrategy.class);
               strategy = null;
               //do something with response
           }
       });

    }
}
