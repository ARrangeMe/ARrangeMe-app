package com.google.ar.sceneform.samples.src.ui.register;

import com.google.ar.sceneform.samples.src.model.RegisterResponse;

public interface RegisterPresenter {
    RegisterResponse registerUser(String username, String firstName, String lastName, String email, String password);
}
