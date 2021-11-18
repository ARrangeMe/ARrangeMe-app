package com.google.ar.sceneform.samples.src.services;

public class SharedData {
    private SharedData() {};
    private static final SharedData sharedData = new SharedData();
    public static SharedData getInstance() {return sharedData;}

    private String testData;
    public String getTestData() {return testData;}
    public void setTestData(String testData) {this.testData = testData;}
}
