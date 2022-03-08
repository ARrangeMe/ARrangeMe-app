package com.google.ar.sceneform.samples.src.ui.login;

import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Container;
import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.dialogs.LoginFailedDialogFragment;
import com.google.ar.sceneform.samples.src.ui.jobs.JobsActivity;
import com.google.ar.sceneform.samples.src.ui.render.RenderActivity;
import com.google.ar.sceneform.samples.src.ui.register.RegisterActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        loginPresenter = new LoginPresenterImpl();
    }

    public void openJobs(){
        Intent intent = new Intent(this, JobsActivity.class);
        startActivity(intent);
    }
    public void openRender(){

        Container container = new Container(11,4.0,4.0,4.0,20);
        Job job = new Job(42,container);
        List<Item> itemsPacked = new ArrayList<>();

        Item item3 = new Item();
        item3.setName("Test name 3");
        item3.setItemID(3);

        item3.setWidth(3);
        item3.setHeight(3);
        item3.setLength(2);
        item3.setPivot(Arrays.asList(0,0,0));

        itemsPacked.add(item3);

        Item item4 = new Item();
        item4.setName("Test name 3");
        item4.setItemID(4);

        item4.setWidth(1);
        item4.setHeight(3);
        item4.setLength(3);
        item4.setPivot(Arrays.asList(3,0,0));

        itemsPacked.add(item4);

        Item item1 = new Item();
        item1.setName("Test name 1");
        item1.setItemID(1);

        item1.setWidth(4);
        item1.setHeight(1);
        item1.setLength(1);
        item1.setPivot(Arrays.asList(0,3,0));

        itemsPacked.add(item1);

        Item item2 = new Item();
        item2.setName("Test name 2");
        item2.setItemID(2);

        item2.setWidth(1);
        item2.setHeight(1);
        item2.setLength(4);
        item2.setPivot(Arrays.asList(3,1,0));

        itemsPacked.add(item2);

        job.setItemsPacked(itemsPacked);
        SharedDataService.getInstance().setJob(job);
        Intent intent = new Intent(this, RenderActivity.class);
        startActivity(intent);
    }
    public void onRenderClick(View view) {
        openRender();
    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextLogin);
        String username = editText.getText().toString();
        EditText editTextPassword = (EditText) findViewById(R.id.editTextLogin2);
        String password = editTextPassword.getText().toString();

        new AsyncTask<String, String, JobsList>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected JobsList doInBackground(String... params) {
                return loginPresenter.getUserByUsername(params[0], params[1]);
            }
            @Override
            protected void onPostExecute(JobsList result) {
                //pipe the result to a new activity
                if(result == null) {
                    //there was a problem
                    DialogFragment newFragment = new LoginFailedDialogFragment();
                    newFragment.show(getSupportFragmentManager(), "failed");
                    return;
                }
                //set the data we'll need on the next app screen
                SharedDataService.getInstance().setJobsList(result);
                openJobs();
            }
        }.execute(username,password);
    }

    public void onCreateNewAccountClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}