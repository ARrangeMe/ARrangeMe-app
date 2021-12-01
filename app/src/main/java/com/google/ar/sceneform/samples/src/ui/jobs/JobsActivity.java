package com.google.ar.sceneform.samples.src.ui.jobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.JobInfo;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobsActivity extends AppCompatActivity {
    private JobsList jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        this.jobs = SharedDataService.getInstance().getJobsList();

        ListView listView = (ListView) findViewById(R.id.jobsListView);

        List<HashMap<String, String>> listItems = new ArrayList<>();// add items to list as a hashmap
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2}); //maps data here to UI element

        for (JobInfo job : jobs.getJobs()) {
            HashMap<String, String> listItem = new HashMap<>();
            listItem.put("First Line",job.getName());
            listItem.put("Second Line", job.getId());
            listItems.add(listItem);
        }
        listView.setAdapter(adapter);
    }
}