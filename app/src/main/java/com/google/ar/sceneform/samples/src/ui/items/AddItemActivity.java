package com.google.ar.sceneform.samples.src.ui.items;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.measurement.SceneformActivity;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_item);
        SharedDataService instance = SharedDataService.getInstance();
        if (instance.getItem() != null) {
            populateFields(instance.getItem());
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        SharedDataService instance = SharedDataService.getInstance();
        if (instance.getItem() != null) {
            populateFields(instance.getItem());
        }
        super.onResume();
    }

    public void editDimensions(View view) {
        submitButtonHandler(null);
        Intent intent = new Intent(this, SceneformActivity.class);
        startActivity(intent);
    }

    private boolean validate() {
        //TODO: write validation code
        return true;
    }


    public void submitButtonHandler(View view) {
        Item item = new Item();

        SharedDataService instance = SharedDataService.getInstance();
        Job job = instance.getJob();
        item.setContainerID(job.getContainer().getContainerId());
        item.setUserID(instance.getUser().getUserID());
        item.setJobID(job.getJobID());

        item.setName(getStringValueFromID(R.id.itemName));
        item.setDescription(getStringValueFromID(R.id.itemDescription));
        item.setWidth(getIntValueFromID(R.id.itemWidth));
        item.setLength(getIntValueFromID(R.id.itemLength));
        item.setHeight(getIntValueFromID(R.id.itemHeight));
        item.setWeight(getIntValueFromID(R.id.itemWeight));
        item.setFragile(getBoolValueFromID(R.id.itemIsFragile));
        item.setPriority(0); //TODO: Is this set by the user?

        instance.setItem(item);
    }

    private void populateFields (Item item) {
        EditText name = findViewById(R.id.itemName);
        EditText description = findViewById(R.id.itemDescription);
        EditText width = findViewById(R.id.itemWidth);
        EditText length = findViewById(R.id.itemLength);
        EditText height = findViewById(R.id.itemHeight);
        EditText weight = findViewById(R.id.itemWeight);
        CheckBox isFragile = findViewById(R.id.itemIsFragile);

        name.setText(item.getName());
        description.setText(item.getDescription());
        width.setText(String.valueOf(item.getWidth()));
        length.setText(String.valueOf(item.getLength()));
        height.setText(String.valueOf(item.getHeight()));
        weight.setText(String.valueOf(item.getWeight()));
        isFragile.setChecked(item.isFragile());
    }

    private String getStringValueFromID(int id) {
        EditText editTextField = findViewById(id);
        return editTextField.getText().toString();
    }

    private int getIntValueFromID(int id) {
        EditText editTextField = findViewById(id);
        return Integer.parseInt(editTextField.getText().toString());
    }

    private boolean getBoolValueFromID(int id) {
        CheckBox conditionsCheckBox = findViewById(id);
        return conditionsCheckBox.isChecked();
    }

}