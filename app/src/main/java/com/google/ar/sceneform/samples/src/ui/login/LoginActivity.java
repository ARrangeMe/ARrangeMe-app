package com.google.ar.sceneform.samples.src.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.jobs.JobsActivity;

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

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextLogin);
        String username = editText.getText().toString();

        new AsyncTask<String, String, JobsList>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected JobsList doInBackground(String... username) {
                return loginPresenter.getUserByUsername(username[0]);
            }
            @Override
            protected void onPostExecute(JobsList result) {
                //pipe the result to a new activity
                if(result == null) {
                    //there was a problem
                    return;
                }
                //set the data we'll need on the next app screen
                SharedDataService.getInstance().setJobsList(result);
                openJobs();
            }
        }.execute("test");
    }
}