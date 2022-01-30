package com.google.ar.sceneform.samples.src.ui.jobs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
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
import com.google.ar.sceneform.samples.src.ui.dialogs.AddJobInfoDialogFragment;
import com.google.ar.sceneform.samples.src.ui.main.PackingJobActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobsActivity extends AppCompatActivity {
    private JobsList jobs;
    private JobsPresenter jobsPresenter;
    private Integer userId;
    private List<HashMap<String, String>> listItems;
    private ListView listView;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        this.jobs = SharedDataService.getInstance().getJobsList();
        this.userId = SharedDataService.getInstance().getUser().getUserID();
        jobsPresenter = new JobsPresenterImpl();

        listView = (ListView) findViewById(R.id.jobsListView);

        listItems = new ArrayList<>();// add items to list as a hashmap
        adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
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
                    protected Job doInBackground(String... jobId) {
                        return jobsPresenter.getJob(jobId[0]);
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
                }.execute(jobId);

            }
        });
    }

    public void onClick(View view) throws Exception {
        if (userId == null) {
            throw new Exception("User ID is not set!");
        }
        DialogFragment newFragment = new AddJobInfoDialogFragment();
        newFragment.show(getSupportFragmentManager(), "tag1");


        new AsyncTask<String, String, JobInfo>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected JobInfo doInBackground(String... params) {
                return jobsPresenter.createJob(params[0], params[1]);
            }
            @Override
            protected void onPostExecute(JobInfo result) {
                if(result == null) {
                    return;
                }
                addJobToList(result);
            }
        }.execute(userId.toString(), "testJobName");
    }
    private void openPackingJob(){
        Intent intent = new Intent(this, PackingJobActivity.class);
        startActivity(intent);
    }
    private void addJobToList(JobInfo jobInfo){
        jobs.addJob(jobInfo); //not necessary, but do for completion
        HashMap<String, String> listItem = new HashMap<>();
        listItem.put("First Line",jobInfo.getName());
        listItem.put("Second Line", jobInfo.getId());
        listItems.add(listItem);
        adapter.notifyDataSetChanged(); //this tells the UI it needs to refresh itself. (Must be called in UI thread)
    }
}