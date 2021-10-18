package com.google.ar.sceneform.samples.src.ui.measurement;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.List;

public class SceneformPresenterImpl implements  SceneformPresenter{
    private List<AnchorNode> anchorNodeList =  new ArrayList<>();

    public SceneformPresenterImpl(){

    }

    @Override
    public double getDistance() {
        if (anchorNodeList.size() == 2) {
            Pose poseA = anchorNodeList.get(0).getAnchor().getPose();
            Pose poseB = anchorNodeList.get(1).getAnchor().getPose();
            double squaredDistance =
                    Math.pow((poseA.tx() - poseB.tx()), 2) +
                            Math.pow((poseA.ty() - poseB.ty()), 2) +
                            Math.pow((poseA.tz() - poseB.tz()), 2);
            return Math.sqrt(squaredDistance); // value in meters
        }
        return -1;
    }

    @Override
    public AnchorNode addAnchor(HitResult hitResult, ArFragment arFragment){
        // Create the Anchor.
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        anchorNodeList.add(anchorNode);
        while (anchorNodeList.size() > 2) {
            anchorNodeList.get(0).getAnchor().detach(); //tell arcore to stop tracking
            anchorNodeList.remove(0);
        }
        return anchorNode;

    }

}
