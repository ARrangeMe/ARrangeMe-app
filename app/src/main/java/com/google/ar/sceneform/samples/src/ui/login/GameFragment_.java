package com.google.ar.sceneform.samples.src.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

// 4. Create a Class that extends AndroidFragmentApplication which is the Fragment implementation for libGDX.
public class GameFragment_ extends AndroidFragmentApplication
{
    // 5. Add the initializeForView() code in the Fragment's onCreateView method.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return initializeForView(new LibgdxGame());
    }
}