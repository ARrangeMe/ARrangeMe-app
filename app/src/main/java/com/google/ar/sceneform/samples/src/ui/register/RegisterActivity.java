package com.google.ar.sceneform.samples.src.ui.register;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.RegisterResponse;
import com.google.ar.sceneform.samples.src.ui.dialogs.LoginFailedDialogFragment;
import com.google.ar.sceneform.samples.src.ui.dialogs.RegisterFailedDialogFragment;
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

        EditText editTextPassword = (EditText) findViewById(R.id.editTextRegister2);
        String password = editTextPassword.getText().toString();

        new AsyncTask<String, String, RegisterResponse>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected RegisterResponse doInBackground(String... params) {
                //TODO: make input fields in UI for these
                return registerPresenter.registerUser(params[0],"firstName","lastName","email",params[1]);

            }
            @Override
            protected void onPostExecute(RegisterResponse result) {
                if(result == null) {
                    //there was a problem
                    DialogFragment newFragment = new RegisterFailedDialogFragment();
                    newFragment.show(getSupportFragmentManager(), "registerFailed");
                    return;
                }
                openLogin();
            }
        }.execute(username,password);
    }
}