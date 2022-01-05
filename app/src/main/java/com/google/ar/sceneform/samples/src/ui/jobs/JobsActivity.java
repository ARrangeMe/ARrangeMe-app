package com.google.ar.sceneform.samples.src.ui.jobs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobInfo;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.main.PackingJobActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobsActivity extends AppCompatActivity {
    private JobsList jobs;
    private JobsPresenter jobsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        this.jobs = SharedDataService.getInstance().getJobsList();
        jobsPresenter = new JobsPresenterImpl();

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String jobName = ((TextView) view.findViewById(R.id.text1)).getText().toString();
                String jobId = listItems.get(position).get("Second Line");

                new AsyncTask<String, String, Job>() {
                    // potential for memory leak if this task lives longer than the main thread. Unlikely.
                    @Override
                    protected Job doInBackground(String... username) {
                        return jobsPresenter.getJob(jobId);
                    }
                    @Override
                    protected void onPostExecute(Job result) {
                        //pipe the result to a new activity
//                        if(result == null) {
//                            //there was a problem. TODO: print to logs or something
//                            return;
//                        }
                        //set the data we'll need on the next app screen
                        SharedDataService.getInstance().setJob(result);
                        openPackingJob();
                    }
                }.execute("test");

            }
        });
    }
    private void openPackingJob(){
        Intent intent = new Intent(this, PackingJobActivity.class);
        startActivity(intent);
    }
}