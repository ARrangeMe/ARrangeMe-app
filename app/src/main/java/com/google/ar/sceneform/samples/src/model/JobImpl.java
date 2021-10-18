package com.google.ar.sceneform.samples.src.model;

import java.util.ArrayList;
import java.util.List;

public class JobImpl implements Job {
    List<Item> items;

    public JobImpl() {
        items = new ArrayList<>();
    }
}
