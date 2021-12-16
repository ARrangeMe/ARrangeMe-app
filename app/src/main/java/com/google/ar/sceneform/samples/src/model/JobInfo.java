package com.google.ar.sceneform.samples.src.model;

public class JobInfo {
    private String id;
    private String name;

    public JobInfo(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId(){return id;}
    public String getName(){return name;}
}
