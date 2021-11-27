package com.google.ar.sceneform.samples.src.ui.login;

import com.google.ar.sceneform.samples.src.model.JobsList;

public interface LoginPresenter {
    public JobsList getUserByUsername(String username);
}
