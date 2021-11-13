package com.google.ar.sceneform.samples.src.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.ar.sceneform.samples.src.R;

import com.google.ar.sceneform.samples.src.services.SortingServerService;
import com.google.ar.sceneform.samples.src.ui.measurement.SceneformActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, SceneformActivity.class);
        startActivity(intent);
    }

    public void generateStrategy(View view) throws IOException {
        SortingServerService service = new SortingServerService();
        service.post("https://y4ff702tki.execute-api.us-east-2.amazonaws.com/test/pack", "{}");

    }
}