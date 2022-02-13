package com.google.ar.sceneform.samples.src.ui.register.RenderListUtils;

import com.threed.jpct.Object3D;

public class RenderListItem {
    String text1;
    String text2;
    Object3D object; //handle to the object

    public RenderListItem(String text1, String text2){
        this.text1 = text1;
        this.text2 = text2;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public void setObject(Object3D object) {
        this.object = object;
    }

    public Object3D getObject() {return object;}
}

