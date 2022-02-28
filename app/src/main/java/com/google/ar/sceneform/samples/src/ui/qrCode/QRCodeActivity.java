package com.google.ar.sceneform.samples.src.ui.qrCode;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.src.model.Item;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        if (result.getContents() != null  && resultCode == RESULT_OK) {
            int itemID = Integer.parseInt(result.getContents());
            Item item = new Item(itemID);
            SharedDataService.getInstance().setItem(item);
            openEdit();

        }
        finish();
    }

    private void openEdit() {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
}