package com.google.ar.sceneform.samples.src.ui.render;

import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ListView;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.model.Container;
import com.google.ar.sceneform.samples.src.model.Item;
import com.google.ar.sceneform.samples.src.model.Job;
import com.google.ar.sceneform.samples.src.services.SharedDataService;
import com.google.ar.sceneform.samples.src.ui.register.RenderListUtils.RenderListAdapter;
import com.google.ar.sceneform.samples.src.ui.register.RenderListUtils.RenderListItem;
import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Polyline;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderActivity extends AppCompatActivity {
    // Used to handle pause and resume...
//    private static RenderActivity master = null; //used for pause/resume handling

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

    private List<RenderListItem> listItems;
    private ListView listView;
    private RenderListAdapter adapter;

    private Light sun = null;

    private SimpleVector origin =  new SimpleVector(0,0,0);
    private SimpleVector worldCenter =  new SimpleVector(0,0,0);

    private float cameraZoom = 0;

    protected void onCreate(Bundle savedInstanceState) {

        Logger.log("onCreate");

        SharedDataService instance = SharedDataService.getInstance();
        if (instance.getJob() != null) {
            job = instance.getJob();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render);
        mGLView = (GLSurfaceView) findViewById(R.id.renderView); //this is where the render is actually shown

        renderer = new MyRenderer();
        mGLView.setRenderer(renderer);

        listView = (ListView) findViewById(R.id.packedItemsListView);

        listItems = new ArrayList<>();// add items to list as a hashmap


        for (Item item : job.getItemsPacked()) {
            RenderListItem listItem = new RenderListItem(item.getName(),String.valueOf(item.getItemID()));
            listItems.add(listItem);
        }

        adapter = new RenderListAdapter(listItems, this);

        listView.setAdapter(adapter);
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
        //convert coordinate system
        length *= -1; //z
        height *=-1; //y
        pivotPoint.z *=-1;
        pivotPoint.y *=-1;
        Object3D box=new Object3D(12);

        // in JPCT, positive coordinate is x to right, y to down, z into screen
        SimpleVector upperLeftFront = pivotPoint;
        SimpleVector upperRightFront = new SimpleVector(pivotPoint.x + width, pivotPoint.y, pivotPoint.z);
        SimpleVector lowerLeftFront = new SimpleVector(pivotPoint.x, pivotPoint.y + height, pivotPoint.z);
        SimpleVector lowerRightFront = new SimpleVector(pivotPoint.x + width, pivotPoint.y + height, pivotPoint.z);

        SimpleVector upperLeftBack = new SimpleVector(pivotPoint.x, pivotPoint.y, pivotPoint.z + length);
        SimpleVector upperRightBack = new SimpleVector(pivotPoint.x + width, pivotPoint.y, pivotPoint.z + length);
        SimpleVector lowerLeftBack = new SimpleVector(pivotPoint.x, pivotPoint.y + height, pivotPoint.z + length);
        SimpleVector lowerRightBack = new SimpleVector(pivotPoint.x + width, pivotPoint.y + height, pivotPoint.z + length);

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
        Random random = new Random();
        box.setAdditionalColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        box.build();

        return box;
    }
    void makeBoundingBox(World world, SimpleVector pivotPoint, double width, double height, double length) {
        length *= -1;
        height *=-1;
        pivotPoint.z *=-1;
        pivotPoint.y *=-1;

        // in JPCT, positive coordinate is x to right, y to down, z into screen
        SimpleVector upperLeftFront = pivotPoint;
        SimpleVector upperRightFront = new SimpleVector(pivotPoint.x + width, pivotPoint.y, pivotPoint.z);
        SimpleVector lowerLeftFront = new SimpleVector(pivotPoint.x, pivotPoint.y + height, pivotPoint.z);
        SimpleVector lowerRightFront = new SimpleVector(pivotPoint.x + width, pivotPoint.y + height, pivotPoint.z);


        SimpleVector upperLeftBack = new SimpleVector(pivotPoint.x, pivotPoint.y, pivotPoint.z + length);
        SimpleVector upperRightBack = new SimpleVector(pivotPoint.x + width, pivotPoint.y, pivotPoint.z + length);
        SimpleVector lowerLeftBack = new SimpleVector(pivotPoint.x, pivotPoint.y + height, pivotPoint.z + length);
        SimpleVector lowerRightBack = new SimpleVector(pivotPoint.x + width, pivotPoint.y + height, pivotPoint.z + length);

        SimpleVector [] backPoints = {upperLeftBack, upperRightBack, lowerRightBack,lowerLeftBack,upperLeftBack};
        SimpleVector [] frontPoints = {lowerLeftFront, upperLeftFront, upperRightFront, lowerRightFront, lowerLeftFront};
        RGBColor color = new RGBColor(66, 234, 221);
        Polyline front = new Polyline(frontPoints,  color);
        front.setWidth(3);
        world.addPolyline(front);
        Polyline back = new Polyline(backPoints,  color);
        back.setWidth(3);
        world.addPolyline(back);

        SimpleVector [] side1Points = {upperLeftBack, upperLeftFront};
        Polyline side1 = new Polyline(side1Points,  color);
        side1.setWidth(3);
        world.addPolyline(side1);

        SimpleVector [] side2Points = {upperRightBack, upperRightFront};
        Polyline side2 = new Polyline(side2Points,  color);
        side2.setWidth(3);
        world.addPolyline(side2);

        SimpleVector [] side3Points = {lowerLeftFront, lowerLeftBack};
        Polyline side3 = new Polyline(side3Points,  color);
        side3.setWidth(3);
        world.addPolyline(side3);

        SimpleVector [] side4Points = {lowerRightFront, lowerRightBack};
        Polyline side4 = new Polyline(side4Points, color );
        side4.setWidth(3);
        world.addPolyline(side4);

        // build opaque floor
        RGBColor floorColor = new RGBColor(0, 255, 0);
        Object3D floor = new Object3D(2);
        floor.addTriangle(upperRightBack,0,0, upperLeftFront,0,1,  upperLeftBack,1,0);
        floor.addTriangle(upperRightFront,1,0, upperLeftFront,0,1, upperRightBack,1,1);
        floor.setAdditionalColor(floorColor);
        floor.build();
        world.addObject(floor);
    }


        class MyRenderer implements GLSurfaceView.Renderer {

        public MyRenderer() {
        }

        public void onSurfaceChanged(GL10 gl, int w, int h) {
            if (fb != null) {
                fb.dispose();
            }
            fb = new FrameBuffer(gl, w, h);

                world = new World();
                world.setAmbientLight(20, 20, 20);

                // probably never have to change the sun
                sun = new Light(world);
                sun.setIntensity(250, 250, 250);

                // edit rendering logic here
                List<Item> itemsToRender = job.getItemsPacked();

                int count = 0;
                for(Item item : itemsToRender){
                    // where do i get the vector for the reference point?
                    Object3D cube = makeBox(item.getPivot(), item.getWidth(), item.getHeight(), item.getLength());
                    world.addObject(cube);

                    // cache the handle to the object so we can change its attributes dynamically later
                    listItems.get(count).setObject(cube);

                    // pre-build all the packing items here and store them in a list for performance.
                    // We can choose whether or not to render them later.
                    List<Object3D> storedItems = new ArrayList<>();
                    storedItems.add(cube);
                    count++;
                }
                //build container object
                Container container =  job.getContainer();
                makeBoundingBox(world, origin, container.getWidth(), container.getHeight(),container.getDepth());
                Object3D containerObject3d = makeBox(origin, job.getContainer().getWidth(), job.getContainer().getHeight(),job.getContainer().getDepth());
                worldCenter = containerObject3d.getTransformedCenter();

                //zoom out a little bit father than the containers longest dimension
                cameraZoom = Collections.max(Arrays.asList(container.getWidth(), container.getHeight(),container.getDepth())).floatValue();
                cameraZoom *= 1.5;

                Camera cam = world.getCamera();
                cam.setPosition(worldCenter); // move camera to center of scene
                cam.moveCamera(Camera.CAMERA_MOVEOUT, cameraZoom);

                // probably doesn't ever have to be changed
                SimpleVector sv = new SimpleVector();
                sv.set(origin);
                sv.y -= 100;
                sv.z -= 100;
                sun.setPosition(sv);
                MemoryHelper.compact();

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
            cam.moveCamera(Camera.CAMERA_MOVEOUT, cameraZoom); //move the camera backwards relative to current direction

            //these four steps do the actual drawing
            fb.clear(back); //clear previous frame
            world.renderScene(fb); //render the new scene based o the world
            world.draw(fb); //draw the new scene into the frame buffer
            fb.display(); //display the new frame buffer

        }
    }

}