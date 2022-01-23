package com.google.ar.sceneform.samples.src.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.ar.sceneform.samples.src.R;

import com.google.ar.sceneform.samples.src.model.PackingStrategy;
import com.google.ar.sceneform.samples.src.ui.items.AddItemActivity;

import java.io.IOException;

public class PackingJobActivity extends AppCompatActivity {
    private PackingJobPresenter packingJobPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        packingJobPresenter = new PackingJobPresenterImpl();
    }
    public void editDimensions(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    private void showPackingStrategy(PackingStrategy strategy) {
        //open new activity and send the strategy
    }
    public void generateStrategy(View view) throws IOException {
        new AsyncTask<String, String, PackingStrategy>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected PackingStrategy doInBackground(String... username) {
                return packingJobPresenter.getPackingStrategy();
            }
            @Override
            protected void onPostExecute(PackingStrategy result) {
                //pipe the result to a new activity
                if(result == null) {
                    //there was a problem
                    return;
                }
                showPackingStrategy(result);
            }
        }.execute("test");

    }
}