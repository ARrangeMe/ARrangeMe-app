package com.google.ar.sceneform.samples.src.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.JobsList;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.jobs.JobsActivity;

public class LoginActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 6. Finally, replace the AndroidLauncher activity content with the libGDX Fragment.
        GameFragment fragment = new GameFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.loginButton, fragment);
        trans.commit();
    }

    // 4. Create a Class that extends AndroidFragmentApplication which is the Fragment implementation for libGDX.
    public static class GameFragment extends AndroidFragmentApplication
    {
        // 5. Add the initializeForView() code in the Fragment's onCreateView method.
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return initializeForView(new MyGame());
        }
    }

    @Override
    public void exit() {}
}