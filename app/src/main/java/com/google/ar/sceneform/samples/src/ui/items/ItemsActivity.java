package com.google.ar.sceneform.samples.src.ui.items;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.model.PackingStrategy;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.items.ItemsPresenter;
import com.google.ar.sceneform.samples.src.ui.items.ItemsPresenterImpl;
import com.google.ar.sceneform.samples.src.ui.qrCode.QRCodeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private Job job;
    private ItemsPresenter itemsPresenter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        this.itemsPresenter = new ItemsPresenterImpl();
        this.job = SharedDataService.getInstance().getJob();
        if (job != null) {
            this.items = job.getItems();
        }
        ListView listView = (ListView) findViewById(R.id.itemsListView);

        List<HashMap<String, String>> listItems = new ArrayList<>();// add items to list as a hashmap
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.itemslist_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.name, R.id.desc}); //maps data here to UI element

        for (Item item : items) {
            HashMap<String, String> listItem = new HashMap<>();
            listItem.put("First Line", item.getName());
            listItem.put("Second Line", item.getDescription());
            listItems.add(listItem);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedDataService.getInstance().setItem(items.get(position));
                editManually();
            }
        });
    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_item_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.scanQr:
                        scanQrCode();
                        return true;
                    case R.id.manual:
                        editManually();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void editManually() {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    private void scanQrCode() {
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);
    }

    public void generateStrategy(View view) throws IOException {
        new AsyncTask<String, String, PackingStrategy>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected PackingStrategy doInBackground(String... username) {
                return itemsPresenter.getPackingStrategy();
            }

            @Override
            protected void onPostExecute(PackingStrategy result) {
                //pipe the result to a new activity
                if (result == null) {
                    //there was a problem
                    return;
                }
                showPackingStrategy(result);
            }
        }.execute("test");


    }

    private void showPackingStrategy(PackingStrategy strategy) {
        //TODO:open new activity and send the strategy
    }
}