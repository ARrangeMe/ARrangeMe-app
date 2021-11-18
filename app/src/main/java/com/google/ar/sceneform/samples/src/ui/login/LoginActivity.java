package com.google.ar.sceneform.samples.src.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.PackingStrategy;
import com.google.ar.sceneform.samples.src.ui.main.PackingJobActivity;

public class LoginActivity extends AppCompatActivity {
    private LoginPresenter loginPresenter;
//    private class LoginAsyncTask extends android.os.AsyncTask<String,String,String> {
//
//        @Override
//        protected String doInBackground(String... username) {
//            loginPresenter.getUserByUsername(username[0]);
//            return null;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            //pipe the result to a new activity
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//

//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        loginPresenter = new LoginPresenterImpl();
    }

    public void openJobs(){
        Intent intent = new Intent(this, PackingJobActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextLogin);
        String username = editText.getText().toString();

        new AsyncTask<String, String, PackingStrategy>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected PackingStrategy doInBackground(String... username) {
                return loginPresenter.getUserByUsername(username[0]);
            }
            @Override
            protected void onPostExecute(PackingStrategy result) {
                //pipe the result to a new activity
//                if(result == null) {
//                    //there was a problem
//                    return;
//                }
                openJobs();
            }
        }.execute("test");
    }
}