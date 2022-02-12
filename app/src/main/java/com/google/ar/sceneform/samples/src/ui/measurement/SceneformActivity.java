/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.ar.sceneform.samples.src.ui.measurement;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.text.DecimalFormat;


/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class SceneformActivity extends AppCompatActivity implements SceneformView {
    private static final String TAG = SceneformActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable marker;
    private SceneformPresenter sceneformPresenter;
    private Item item;

    public void saveButtonHandler(View view) {
        SharedDataService instance = SharedDataService.getInstance();
        if (instance.getItem() != null) {
            Item currItem = instance.getItem();
            currItem.setHeight(item.getHeight());
            currItem.setWidth(item.getWidth());
            currItem.setLength(item.getLength());
            instance.setItem(currItem);
        } else {
            instance.setItem(item);
        }
        finish();
    }

    private enum Measurement {WIDTH, HEIGHT, LENGTH}

    ;
    private Measurement side;
    private RadioButton radioButtonWidth;
    private RadioButton radioButtonLength;
    private RadioButton radioButtonHeight;
    private DecimalFormat formatter;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        side = Measurement.WIDTH;
        item = new Item(); //dummy, this should get passed in
        item.setHeight(1.0);
        item.setWidth(2.0);
        item.setLength(3.0);
        formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(3);

        sceneformPresenter = new SceneformPresenterImpl();

        setContentView(R.layout.activity_ux);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        radioButtonHeight = (RadioButton) findViewById(R.id.radioButtonHeight);
        radioButtonWidth = (RadioButton) findViewById(R.id.radioButtonWidth);
        radioButtonLength = (RadioButton) findViewById(R.id.radioButtonLength);
        configureRadioButtons();

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().

        //set up renderable to use as marker
        MaterialFactory
                .makeOpaqueWithColor(this, new Color(android.graphics.Color.CYAN))
                .thenAccept(material -> {
                    marker = ShapeFactory.makeSphere((float) 0.02, new Vector3(0f, 0f, 0f), material);
                });

        arFragment.setOnTapArPlaneListener(
            (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                if (marker == null) {
                    return;
                }

                AnchorNode anchorNode = sceneformPresenter.addAnchor(hitResult, arFragment);

                // Create the transformable and add it to the anchor.
                TransformableNode markerNode = new TransformableNode(arFragment.getTransformationSystem());
                markerNode.setParent(anchorNode);
                markerNode.setRenderable(marker);
                markerNode.select();

                double distance = sceneformPresenter.getDistance();
                if (distance > 0) {
                    String distanceText = formatter.format(distance);

                    TextView textView = null;
                    if (side.equals(Measurement.HEIGHT)) {
                        //update height text view
                        textView = findViewById(R.id.textViewHeight);
                        item.setHeight(distance);
                    } else if (side.equals(Measurement.LENGTH)) {
                        //update length textview
                        textView = findViewById(R.id.textViewLength);
                        item.setLength(distance);
                    } else if (side.equals(Measurement.WIDTH)) {
                        //update width textview
                        textView = findViewById(R.id.textViewWidth);
                        item.setWidth(distance);
                    }
                    if (textView != null) {
                        textView.setText(distanceText);
                    }
                }
            });

        //set initial measurement values according to item that was passed to this activity:
//        ((TextView)findViewById(R.id.textViewHeight)).setText(formatter.format(item.getHeight()));
//        ((TextView)findViewById(R.id.textViewWidth)).setText(formatter.format(item.getWidth()));
//        ((TextView)findViewById(R.id.textViewLength)).setText(formatter.format(item.getLength()));
    }

    private void configureRadioButtons() {
        //do this manually because android doesn't like nested layouts for whatever reason
        radioButtonLength.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                side = Measurement.LENGTH;
                radioButtonLength.setChecked(true);
                radioButtonWidth.setChecked(false);
                radioButtonHeight.setChecked(false);
            }
        });
        radioButtonWidth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                side = Measurement.WIDTH;
                radioButtonWidth.setChecked(true);
                radioButtonLength.setChecked(false);
                radioButtonHeight.setChecked(false);
            }
        });
        radioButtonHeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                side = Measurement.HEIGHT;
                radioButtonHeight.setChecked(true);
                radioButtonLength.setChecked(false);
                radioButtonWidth.setChecked(false);
            }
        });
    }


    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
            ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                .getDeviceConfigurationInfo()
                .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
