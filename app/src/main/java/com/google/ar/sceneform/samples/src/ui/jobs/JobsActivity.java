package com.google.ar.sceneform.samples.src.ui.jobs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Container;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.jobs.ListUtils.CustomListAdapter;
import com.google.ar.sceneform.samples.src.ui.jobs.ListUtils.CustomListItem;

import java.util.ArrayList;
import java.util.List;

public class JobsActivity extends AppCompatActivity {
    private JobsList jobs;
    private JobsPresenter jobsPresenter;
    private Integer userId;
    private List<CustomListItem> listItems;
    private ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        this.jobs = SharedDataService.getInstance().getJobsList();
        this.userId = SharedDataService.getInstance().getUser().getUserID();
        jobsPresenter = new JobsPresenterImpl();

        listView = (ListView) findViewById(R.id.jobsListView);

        listItems = new ArrayList<>();// add items to list as a hashmap


        for (Job job : jobs.getJobs()) {
            CustomListItem listItem = new CustomListItem(job.getName(),String.valueOf(job.getJobID()));
            listItems.add(listItem);
        }

        adapter = new CustomListAdapter(listItems, this);

        listView.setAdapter(adapter);
    }

    public void onClick(View view) throws Exception {
        if (userId == null) {
            throw new Exception("User ID is not set!");
        }

        //need to put all the builder code here so that we wait until OK is clicked before starting the asyc task
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editText); // displays the user input bar

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_container_dialog, null);
        builder2.setView(dialogView);
        builder2.setMessage("Enter Container Dimensions:")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String width = ((EditText) dialogView.findViewById(R.id.contWidth)).getText().toString();
                        String height = ((EditText) dialogView.findViewById(R.id.contHeight)).getText().toString();
                        String depth = ((EditText) dialogView.findViewById(R.id.contDepth)).getText().toString();
                        String maxWeight = ((EditText) dialogView.findViewById(R.id.maxWeight)).getText().toString();

                        addJobApi(editText.getText().toString(), width, height, depth, maxWeight);// async task
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        builder.setMessage("New Job Name:")
                .setPositiveButton("next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        builder2.create().show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setTitle("Create New Job")
                .setView(editText)
                .setView(layout);
        builder.create().show();

    }


    private void addJobToList(Job jobInfo){
        jobs.addJob(jobInfo); //not necessary, but do for completion
        CustomListItem listItem = new CustomListItem(jobInfo.getName(),String.valueOf(jobInfo.getJobID()));
        listItems.add(listItem);
        adapter.notifyDataSetChanged(); //this tells the UI it needs to refresh itself. (Must be called in UI thread)
    }

    private void addJobApi(String jobName, String width, String height, String depth, String maxWeight){
        new AsyncTask<String, String, Job>() {
            @Override
            protected Job doInBackground(String... params) {
                Container cont = getContainer(params[2], params[3], params[4], params[5]);
                return jobsPresenter.createJob(params[0], params[1], cont);
            }
            @Override
            protected void onPostExecute(Job result) {
                if(result == null) {
                    return;
                }
                addJobToList(result);
            }
        }.execute(userId.toString(), jobName, width, height, depth, maxWeight);
    }

    private Container getContainer(String width, String height, String depth, String maxWeight) {
        double widthVal = Double.parseDouble(width);
        double heightVal = Double.parseDouble(height);
        double depthVal = Double.parseDouble(depth);
        double weightVal = Double.parseDouble(maxWeight);


        return new Container(0, widthVal, heightVal, depthVal, weightVal);

    }


}