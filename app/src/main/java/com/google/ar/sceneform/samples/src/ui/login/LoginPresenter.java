package com.google.ar.sceneform.samples.src.ui.login;

import com.google.ar.sceneform.samples.src.model.PackingStrategy;

public interface LoginPresenter {
    public PackingStrategy getUserByUsername(String username);
}
