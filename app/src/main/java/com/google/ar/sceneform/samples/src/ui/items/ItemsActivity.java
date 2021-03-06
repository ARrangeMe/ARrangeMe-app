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
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.items.ItemsPresenter;
import com.google.ar.sceneform.samples.src.ui.items.ItemsPresenterImpl;
import com.google.ar.sceneform.samples.src.ui.qrCode.QRCodeActivity;
import com.google.ar.sceneform.samples.src.ui.render.RenderActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private Job job;
    private ItemsPresenter itemsPresenter;
    private List<Item> items;


    private void loadItems() {
        this.job = SharedDataService.getInstance().getJob();
        if (job != null) {
            if (items != null) {
                items.clear();
            }
            this.items = new ArrayList<>(job.getItemsPacked()); //TODO:revisit this later
            items.addAll(job.getItemsUnpacked());
        }
        ListView listView = (ListView) findViewById(R.id.itemsListView);

        List<HashMap<String, String>> listItems = new ArrayList<>();// add items to list as a hashmap
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.itemslist_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.name, R.id.desc}); //maps data here to UI element

        HashMap<Integer, Boolean> unique = new HashMap<>();

        for (Item item : items) {
            if (unique.get(item.getItemID()) != null) {
                continue;
            }
            HashMap<String, String> listItem = new HashMap<>();
            listItem.put("First Line", item.getName());
            listItem.put("Second Line", String.valueOf(item.getItemID()));
            listItems.add(listItem);
            unique.put(item.getItemID(), true);
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

    @Override
    protected void onResume() {
        loadItems();
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        this.itemsPresenter = new ItemsPresenterImpl();
        loadItems();
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
//                    case R.id.manual:
//                        editManually();
//                        return true;
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
//
//    @Override
//    public void onDestroy() {
//        itemsPresenter.getPackingStrategy();
//        super.onDestroy();
//    }

    public void generateStrategy(View view) throws IOException {
        new AsyncTask<String, String, Job>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected Job doInBackground(String... username) {
                return itemsPresenter.getPackingStrategy();
            }

            @Override
            protected void onPostExecute(Job result) {
                //pipe the result to a new activity
                if (result == null) {
                    //there was a problem
                    return;
                }
                showPackingStrategy(result);
            }
        }.execute("test");


    }

    private void showPackingStrategy(Job strategy) {
        //TODO:open new activity and send the strategy
        SharedDataService.getInstance().setJob(strategy);
        Intent intent = new Intent(this, RenderActivity.class);
        startActivity(intent);
    }
}