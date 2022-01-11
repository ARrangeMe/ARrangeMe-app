package com.google.ar.sceneform.samples.src.ui.render;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.ar.sceneform.samples.src.R;

import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.google.ar.sceneform.samples.src.ui.login.LoginPresenter;
import com.google.ar.sceneform.samples.src.ui.login.LoginPresenterImpl;
import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;

public class RenderActivity extends AppCompatActivity {
    // Used to handle pause and resume...
    private static RenderActivity master = null; //used for pause/resume handling

    private GLSurfaceView mGLView;
    private MyRenderer renderer = null;
    private FrameBuffer fb = null;
    private World world = null;
    private RGBColor back = new RGBColor(50, 50, 100);

    private float touchTurn = 0;
    private float touchTurnUp = 0;

    private float xpos = -1;
    private float ypos = -1;

    private Object3D cube = null;

    private Light sun = null;

    protected void onCreate(Bundle savedInstanceState) {

        Logger.log("onCreate");

        if (master != null) {
            copy(master);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render);
        mGLView = (GLSurfaceView) findViewById(R.id.renderView);

        renderer = new MyRenderer();
        mGLView.setRenderer(renderer);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void copy(Object src) {
        try {
            Logger.log("Copying data from master Activity!");
            Field[] fs = src.getClass().getDeclaredFields();
            for (Field f : fs) {
                f.setAccessible(true);
                f.set(this, f.get(src));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean onTouchEvent(MotionEvent me) {
        //action up and down help isolate touches up and down so, the user has more control. Do not remove these.
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            xpos = me.getX();
            ypos = me.getY();
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_UP) {
            xpos = -1;
            ypos = -1;
            touchTurn = 0;// set here to be used by rendering thread
            touchTurnUp = 0;
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_MOVE) {
            float xd = me.getX() - xpos;
            float yd = me.getY() - ypos;

            xpos = me.getX();
            ypos = me.getY();

            touchTurn = xd / -100f;
            touchTurnUp = yd / -100f;
            return true;
        }

        return super.onTouchEvent(me);
    }

    class MyRenderer implements GLSurfaceView.Renderer {

        public MyRenderer() {
        }

        public void onSurfaceChanged(GL10 gl, int w, int h) {
            if (fb != null) {
                fb.dispose();
            }
            fb = new FrameBuffer(gl, w, h);

            if (master == null) { //set up the world if we haven't already

                world = new World();
                world.setAmbientLight(20, 20, 20);

                sun = new Light(world);
                sun.setIntensity(250, 250, 250);

                // Create a texture out of the icon...:-)
                Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(getResources().getDrawable(R.drawable.ic_launcher)), 64, 64));
                TextureManager.getInstance().addTexture("texture", texture);

                cube = Primitives.getCube(10);
                cube.calcTextureWrapSpherical();
                cube.setTexture("texture");
                cube.strip();
                cube.build();
                // pre-build all the packing items here and store them in a list for performance.
                // We can choose whether or not to render them later.

                world.addObject(cube);

                Camera cam = world.getCamera();
                cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
                cam.lookAt(cube.getTransformedCenter());

                SimpleVector sv = new SimpleVector();
                sv.set(cube.getTransformedCenter());
                sv.y -= 100;
                sv.z -= 100;
                sun.setPosition(sv);
                MemoryHelper.compact();

                if (master == null) {
                    Logger.log("Saving master Activity!");
                    master = RenderActivity.this;
                }
            }
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        }

        public void onDrawFrame(GL10 gl) { //does the rendering in android's render thread
//            //rotate the cube
//            if (touchTurn != 0) {
//                cube.rotateY(touchTurn);
//                touchTurn = 0;
//            }
//
//            if (touchTurnUp != 0) {
//                cube.rotateX(touchTurnUp);
//                touchTurnUp = 0;
//            }

            // Determine which objects need to be rendered

            //rotate the camera around the scene
            Camera cam = world.getCamera();
            cam.rotateX(touchTurnUp); //rotate the camera
            cam.rotateY(touchTurn);
            touchTurn = 0; //reset these values
            touchTurnUp = 0;
            cam.setPosition(cube.getTransformedCenter()); // move camera to center of scene
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 50); //move the camera backwards relative to current direction

            //these four steps do the actual drawing
            fb.clear(back); //clear previous frame
            world.renderScene(fb); //render the new scene based o the world
            world.draw(fb); //draw the new scene into the frame buffer
            fb.display(); //display the new frame buffer

        }
    }

}