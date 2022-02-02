package com.google.ar.sceneform.samples.src.ui.qrCode;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.items.AddItemActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCodeActivity extends AppCompatActivity {

    private QRCodePresenter qrPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.qrPresenter = new QRCodePresenterImpl();

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a QR Code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.setCameraId(0);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.initiateScan();

    }

    @Override
    protected void onResume() {
        finish();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        if (result.getContents() != null) {
            int itemID = Integer.parseInt(result.getContents());

            new AsyncTask<String, String, Item>() {
                // potential for memory leak if this task lives longer than the main thread. Unlikely.
                @Override
                protected Item doInBackground(String... username) {
                    return qrPresenter.getItemFromID(itemID);
                }
                @Override
                protected void onPostExecute(Item result) {
                    //pipe the result to a new activity
                    if(result == null) {
                        //there was a problem
                        return;
                    }
                    //set the data we'll need on the next app screen
                    SharedDataService.getInstance().setItem(result);
                    openEdit();
                }
            }.execute("test");

            finish();
        }
    }

    private void openEdit() {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
}