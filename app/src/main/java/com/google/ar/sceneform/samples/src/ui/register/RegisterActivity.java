package com.google.ar.sceneform.samples.src.ui.register;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.ui.jobs.JobsActivity;
import com.google.ar.sceneform.samples.src.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {
    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenterImpl();
    }
    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextRegister);
        String username = editText.getText().toString();

        new AsyncTask<String, String, Boolean>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected Boolean doInBackground(String... username) {
                //TODO: make input fields in UI for these
                registerPresenter.registerUser(username[0],"firstName","lastName","email","password");
                return true;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                //TODO: Check result or something and give user a notification success/failure
                openLogin();
            }
        }.execute(username);
    }
}