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

import java.util.List;

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

    public void editDimensions(View view) throws Exception {
        submitButtonHandler(null);
        Intent intent = new Intent(this, SceneformActivity.class);
        startActivity(intent);
    }

    private boolean validate() {
        //TODO: write validation code
        return true;
    }


    public void submitButtonHandler(View view) throws Exception {
        SharedDataService instance = SharedDataService.getInstance();
        Item item = instance.getItem();
        if (item ==null)
        {
            item = new Item();
            instance.setItem(item);
        }else if (item.getItemID() == 0) {
            throw new Exception("Item missing item id");
        }

        item.setName(getStringValueFromID(R.id.itemName));
        item.setDescription(getStringValueFromID(R.id.itemDescription));
        item.setWidth(getDoubleValueFromID(R.id.itemWidth));
        item.setLength(getDoubleValueFromID(R.id.itemLength));
        item.setHeight(getDoubleValueFromID(R.id.itemHeight));
        item.setWeight(getDoubleValueFromID(R.id.itemWeight));
        item.setFragile(getBoolValueFromID(R.id.itemIsFragile));

        List<Item> items = instance.getJob().getItemsUnpacked();
        Item old = null;
        for (Item i: items) {
            if (item.getItemID() == i.getItemID()) {
                old = i;
                break;
            }
        }
        items = instance.getJob().getItemsPacked();
        for (Item i: items) {
            if (item.getItemID() == i.getItemID()) {
                old = i;
                break;
            }
        }
        items.remove(old);
        instance.getJob().getItemsUnpacked().add(item);

        finish();
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
        width.setText(String.format("%.2f", item.getWidth()));
        length.setText(String.format("%.2f", item.getLength()));
        height.setText(String.format("%.2f", item.getHeight()));
        weight.setText(String.valueOf(item.getWeight()));
        isFragile.setChecked(item.isFragile());
    }

    private String getStringValueFromID(int id) {
        EditText editTextField = findViewById(id);
        return editTextField.getText().toString();
    }

    private double getDoubleValueFromID(int id) {
        EditText editTextField = findViewById(id);
        return Double.parseDouble(editTextField.getText().toString());
    }

    private boolean getBoolValueFromID(int id) {
        CheckBox conditionsCheckBox = findViewById(id);
        return conditionsCheckBox.isChecked();
    }

}