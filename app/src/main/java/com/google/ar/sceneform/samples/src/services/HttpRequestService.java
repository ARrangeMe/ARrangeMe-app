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

public class HttpRequestService {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IOException("Response code: "+response.code());
            }
            return response.body().string();
        }

    }

    public String get(String url, String json) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IOException("Response code: "+response.code());
            }
            return response.body().string();
        }
    }
}
