package com.google.ar.sceneform.samples.src.ui.register;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.ar.sceneform.samples.src.R;

public class RegisterActivity extends AppCompatActivity {
    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenterImpl();
    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextRegister);
        String username = editText.getText().toString();

        new AsyncTask<String, String, Boolean>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected Boolean doInBackground(String... username) {
                registerPresenter.registerUser(username[0]);
                return true;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                //TODO: Check result or something and give user a notification success/failure
            }
        }.execute("test");
    }
}