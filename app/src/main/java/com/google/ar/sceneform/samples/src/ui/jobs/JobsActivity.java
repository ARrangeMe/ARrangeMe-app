package com.google.ar.sceneform.samples.src.ui.jobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;

public class JobsActivity extends AppCompatActivity {
    private JobsList jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        this.jobs = SharedDataService.getInstance().getJobsList();
    }
}