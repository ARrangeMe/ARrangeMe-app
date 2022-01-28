package com.google.ar.sceneform.samples.src.model;

import com.google.gson.annotations.SerializedName;

public class JobInfo {
    @SerializedName(value="id", alternate={"job_id","jobId"})
    private String id;
    @SerializedName(value="name", alternate={"job_name","jobName"})
    private String name;

    public JobInfo(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId(){return id;}
    public String getName(){return name;}
}
