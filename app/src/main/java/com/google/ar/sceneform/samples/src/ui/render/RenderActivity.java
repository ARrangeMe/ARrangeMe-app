package com.google.ar.sceneform.samples.src.ui.render;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderActivity extends AppCompatActivity {
    // Used to handle pause and resume...
    private static RenderActivity master = null; //used for pause/resume handling

    private Job job;
    private GLSurfaceView mGLView;
    private MyRenderer renderer = null;
    private FrameBuffer fb = null;
    private World world = null;
    private RGBColor back = new RGBColor(50, 50, 100);

    private float touchTurn = 0;
    private float touchTurnUp = 0;

    private float xpos = -1;
    private float ypos = -1;



    private Light sun = null;

    private SimpleVector worldCenter =  new SimpleVector(0,0,0);

    protected void onCreate(Bundle savedInstanceState) {

        Logger.log("onCreate");

        if (master != null) {
            copy(master);
        }

        SharedDataService instance = SharedDataService.getInstance();
        if (instance.getJob() != null) {
            job = instance.getJob();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render);
        mGLView = (GLSurfaceView) findViewById(R.id.renderView); //this is where the render is actually shown

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

    public boolean onTouchEvent(MotionEvent me) { //probably don't need to change
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

    Object3D makeBox(SimpleVector pivotPoint, double width, double height, double length) {
        Object3D box=new Object3D(12);

        // in JPCT, positive coordinate is x to right, y to down, z into screen
        SimpleVector upperLeftBack = pivotPoint;
        SimpleVector upperRightBack = new SimpleVector(pivotPoint.x + width, pivotPoint.y, pivotPoint.z);
        SimpleVector lowerLeftBack = new SimpleVector( pivotPoint.x, pivotPoint.y + height, pivotPoint.z);
        SimpleVector lowerRightBack = new SimpleVector(pivotPoint.x + width, pivotPoint.y + height, pivotPoint.z);

        SimpleVector upperLeftFront=new SimpleVector(pivotPoint.x, pivotPoint.y, pivotPoint.z - length);
        SimpleVector upperRightFront=new SimpleVector(pivotPoint.x + width, pivotPoint.y, pivotPoint.z - length);
        SimpleVector lowerLeftFront=new SimpleVector(pivotPoint.x, pivotPoint.y + height, pivotPoint.z - length);
        SimpleVector lowerRightFront=new SimpleVector(pivotPoint.x + width, pivotPoint.y + height, pivotPoint.z - length);

        // Front
        box.addTriangle(upperLeftFront,0,0, lowerLeftFront,0,1, upperRightFront,1,0);
        box.addTriangle(upperRightFront,1,0, lowerLeftFront,0,1, lowerRightFront,1,1);

        // Back
        box.addTriangle(upperLeftBack,0,0, upperRightBack,1,0, lowerLeftBack,0,1);
        box.addTriangle(upperRightBack,1,0, lowerRightBack,1,1, lowerLeftBack,0,1);

        // Upper
        box.addTriangle(upperLeftBack,0,0, upperLeftFront,0,1, upperRightBack,1,0);
        box.addTriangle(upperRightBack,1,0, upperLeftFront,0,1, upperRightFront,1,1);

        // Lower
        box.addTriangle(lowerLeftBack,0,0, lowerRightBack,1,0, lowerLeftFront,0,1);
        box.addTriangle(lowerRightBack,1,0, lowerRightFront,1,1, lowerLeftFront,0,1);

        // Left
        box.addTriangle(upperLeftFront,0,0, upperLeftBack,1,0, lowerLeftFront,0,1);
        box.addTriangle(upperLeftBack,1,0, lowerLeftBack,1,1, lowerLeftFront,0,1);

        // Right
        box.addTriangle(upperRightFront,0,0, lowerRightFront,0,1, upperRightBack,1,0);
        box.addTriangle(upperRightBack,1,0, lowerRightFront, 0,1, lowerRightBack,1,1);

        // set the box texture
        box.setTexture("texture");
        box.build();

        return box;
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

                // probably never have to change the sun
                sun = new Light(world);
                sun.setIntensity(250, 250, 250);

                // Create a texture out of the icon...:-)
                Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(getResources().getDrawable(R.drawable.ic_launcher)), 64, 64));
                TextureManager.getInstance().addTexture("texture", texture);

                // edit rendering logic here
                List<Item> itemsToRender = job.getItemsPacked();


                for(Item item : itemsToRender){
                    // where do i get the vector for the reference point?
                    Object3D cube = makeBox(item.getPivot(), item.getWidth(), item.getHeight(), item.getLength());
                    world.addObject(cube);
                    // pre-build all the packing items here and store them in a list for performance.
                    // We can choose whether or not to render them later.
                    List<Object3D> storedItems = new ArrayList<>();
                    storedItems.add(cube);
                }



                Camera cam = world.getCamera();
                cam.moveCamera(Camera.CAMERA_MOVEOUT, 3);
                cam.lookAt(worldCenter);

                // probably doesn't ever have to be changed
                SimpleVector sv = new SimpleVector();
                sv.set(worldCenter);
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
            // TODO: Determine which objects need to be rendered. But this could slow down thread

            //rotate the camera around the scene
            Camera cam = world.getCamera();
            cam.rotateX(touchTurnUp); //rotate the camera
            cam.rotateY(touchTurn);
            touchTurn = 0; //reset these values
            touchTurnUp = 0;
            cam.setPosition(worldCenter); // move camera to center of scene
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 3); //move the camera backwards relative to current direction

            //these four steps do the actual drawing
            fb.clear(back); //clear previous frame
            world.renderScene(fb); //render the new scene based o the world
            world.draw(fb); //draw the new scene into the frame buffer
            fb.display(); //display the new frame buffer

        }
    }

}