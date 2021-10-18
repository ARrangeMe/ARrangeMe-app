package com.google.ar.sceneform.samples.src.ui.measurement;

import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.ArFragment;

public interface SceneformPresenter {
    double getDistance();
    AnchorNode addAnchor(HitResult hitResult, ArFragment arFragment);
}
