package de.maximal.effectcamera;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import de.maximal.objects.NavDrawerItem;
import de.maximal.util.SQLHelper;

import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;
import static de.maximal.data.Constants.*;


/**
 *
 * Main Activity for the Effect Camera
 *
 * @author Maximilian Kapsecker
 * @version 1.0
 * Environment: Android Studio
 * Created on 19.05.2015
 * Last change: 16.10.2015
 *
 */

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnLongClickListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    //private static final String DEBUG_TAG_GESTURE = "Gestures";


    private CustomSurfaceView surface;

    private ImageButton editButton;
    private ImageButton captureButton;
    private ImageButton nonEffectButton;

    private ImageButton extendButton;
    private ImageButton menuButton;

    private ImageButton switchCameraButton;
    private ImageButton flashButton;

    private ImageButton sceneButton;
    private ImageButton whiteBalanceButton;
    private ImageButton focusButton;
    private ImageButton autoExposureLockButton;

    private ImageButton aeExposureButton;
    private ImageButton hdrButton;
    private ImageButton selfTimerButton;
    private ImageButton centerFocusButton;

    private Button favoriteButton;
    private Button effectButton;
    private Button tuneButton;
    private Button collageButton;
    private Button cropButton;
    private Button vignetteButton;
    private Button projectionButton;

    private Button addFavoriteButton;

    private Button classicButton;
    private Button bwButton;
    private Button retroButton;
    private Button paintedButton;
    //...//
    private Button testButton;

    private Button strengthButton;
    private Button brightnessButton;
    private Button contrastButton;
    private Button saturationButton;

    private Button cropOriginalButton;
    private Button crop16to9Button;
    private Button crop3to2Button;
    private Button crop5to4Button;
    private Button crop7to5Button;
    private Button cropdinButton;
    private Button cropLandscapeButton;
    private Button cropPortraitButton;
    private Button cropSquareButton;

    private Button rotateButton;
    private Button gridButton;

    private Button originalEffect;
    private Button bwEffect;
    private Button sepiaEffect;
    private Button negativeEffect;
    private Button edgeEffect;
    private Button portraitEffect;

    private Button bwLightEffect;
    private Button bwLightEffect1;
    private Button bwLightEffect2;
    private Button bwLightEffect3;
    private Button bwLightEffect4;
    private Button bwLightEffect5;

    private Button retroEffect;
    private Button retroEffect1;
    private Button retroEffect2;
    private Button retroEffect3;
    private Button retroEffect4;
    private Button retroEffect5;

    private Button paintedEffect;
    private Button paintedEffect1;
    private Button paintedEffect2;
    private Button paintedEffect3;
    private Button paintedEffect4;
    private Button paintedEffect5;
    //...//
    private Button testEffect1;
    private Button testEffect2;
    private Button testEffect3;
    private Button testEffect4;
    private Button testEffect5;
    private Button testEffect6;

    private Display display;
    private ActionBar bar;
    private OrientationEventListener orientationEventListener;


    private TextView hwSettingsText;
    private ImageView actionBarImage;

    private int effect;
    private int orientation;
    private int currentView;

    private float strength_value;
    private float brightness_value;
    private float contrast_value;
    private float saturation_value;
    private float vignette_value;
    private float rotation_value;

    private boolean tablet;
    private boolean back_cam;
    private boolean gridOnOff;
    private int extended;

    private String flash_mode;
    private String wb_mode;
    private String scene_mode;
    private boolean ae_mode;
    private boolean hdr_mode;
    private String focus_mode;
    private int brightness_mode;

    private int selfTimer_mode;
    private boolean centerFocus_mode;

    private ViewSwitcher cameraSwitcher;
    private ViewFlipper viewFlipper;

    private ViewFlipper hwSettings;

    private SeekBar strength;
    private SeekBar contrast;
    private SeekBar brightness;
    private SeekBar saturation;

    private SeekBar rotation;

    private SeekBar vignette;

    private Point size;

    private HorizontalScrollView scrollViewActionBar;

    private GestureDetectorCompat mDetector;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;

    private String[] drawerTitles;
    private int[] drawerIcons;

    private Camera camera;

    SQLHelper sqlLite;

    Intent intent;

    private RendererView renderer;


    private boolean visibility_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkForOPENGL();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTheme(android.R.style.Theme_Holo_Light);

        setContentView(R.layout.main_layout);

        surface = new CustomSurfaceView(this);

        createActionBar();
        getResViews();
        setListener();
        initialize();

        createDatabase();
        createGestureDetector();
        //orientationListener();
        touchAreaButton();
        createNavigationDrawer();

        checkCameraProperties();

        cameraSwitcher.addView(surface);

        if(renderer.getCamera() != null){
            Log.i(TAG, "Found class Camera!");
            camera = renderer.getCamera();

            checkCameraProperties();
        }


    }

    @Override
    protected void onResume(){
        super.onResume();
        //camera.startBackgroundThread();


    }

    @Override
    protected void onPause(){
        //camera.closeCamera();
        //camera.stopBackgroundThread();
        super.onPause();
    }

    @Override
    protected void onStop(){
        //camera.closeCamera();
        //camera.stopBackgroundThread();
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        //camera.closeCamera();
        //camera.stopBackgroundThread();
        super.onDestroy();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        //renderer.openCamera(cameraId[0]);

    }

    private void checkForOPENGL(){
        /*Check if the System fulfills the requirements of OpenGl 2*/
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= OPENGLES_VERSION2;


        /*TODO: Make it work! Checking for OpenGl Es 2.0 in the right way*/
        if(!supportsEs2){
            new AlertDialog.Builder(this)
                    .setTitle("Sorry...")
                    .setMessage("This App requires OpenGL 2.0")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void getResViews(){


        hwSettingsText = (TextView) findViewById(R.id.hwSettingsText);
        actionBarImage = (ImageView) findViewById(R.id.actionBarImage);

        captureButton = (ImageButton) findViewById(R.id.capturePic);
        editButton = (ImageButton) findViewById(R.id.edit);
        nonEffectButton = (ImageButton) findViewById(R.id.noneEffect);

        extendButton = (ImageButton) findViewById(R.id.extend);
        menuButton = (ImageButton) findViewById(R.id.menu);

        switchCameraButton = (ImageButton) findViewById(R.id.switch_camera);
        flashButton = (ImageButton) findViewById(R.id.flash);

        sceneButton = (ImageButton) findViewById(R.id.scene);
        whiteBalanceButton = (ImageButton) findViewById(R.id.whiteBalance);
        focusButton = (ImageButton) findViewById(R.id.focus);
        autoExposureLockButton = (ImageButton) findViewById(R.id.autoExposureLock);

        aeExposureButton = (ImageButton) findViewById(R.id.aeExposure);
        hdrButton = (ImageButton) findViewById(R.id.hdr);
        selfTimerButton = (ImageButton) findViewById(R.id.selfTimer);
        centerFocusButton = (ImageButton) findViewById(R.id.centerFocus);

        favoriteButton = (Button) findViewById(R.id.favorite);
        effectButton = (Button) findViewById(R.id.effect);
        tuneButton = (Button) findViewById(R.id.tune);
        collageButton = (Button) findViewById(R.id.collage);
        cropButton = (Button) findViewById(R.id.crop);
        vignetteButton = (Button) findViewById(R.id.vignetteB);
        projectionButton = (Button) findViewById(R.id.projection);

        addFavoriteButton = (Button) findViewById(R.id.add_favorite);

        classicButton = (Button) findViewById(R.id.classic);
        bwButton = (Button) findViewById(R.id.bw);
        retroButton = (Button) findViewById(R.id.retro);
        paintedButton = (Button) findViewById(R.id.painted);
        //...//
        testButton = (Button) findViewById(R.id.test);

        strengthButton = (Button) findViewById(R.id.strengthB);
        saturationButton = (Button) findViewById(R.id.saturationB);
        brightnessButton = (Button) findViewById(R.id.brightnessB);
        contrastButton = (Button) findViewById(R.id.contrastB);


        cropOriginalButton = (Button) findViewById(R.id.crop_original);
        crop16to9Button = (Button) findViewById(R.id.crop_16_9);
        crop3to2Button = (Button) findViewById(R.id.crop_3_2);
        crop5to4Button = (Button) findViewById(R.id.crop_5_4);
        crop7to5Button = (Button) findViewById(R.id.crop_7_5);
        cropdinButton = (Button) findViewById(R.id.crop_din);
        cropLandscapeButton = (Button) findViewById(R.id.crop_landscape);
        cropPortraitButton = (Button) findViewById(R.id.crop_portrait);
        cropSquareButton = (Button) findViewById(R.id.crop_square);


        rotateButton = (Button) findViewById(R.id.rotateB);
        gridButton = (Button) findViewById(R.id.gridB);

        originalEffect = (Button) findViewById(R.id.original);
        bwEffect = (Button) findViewById(R.id.blackwhite);
        sepiaEffect = (Button) findViewById(R.id.sepia);
        negativeEffect = (Button) findViewById(R.id.negative);
        edgeEffect = (Button) findViewById(R.id.edge);
        portraitEffect = (Button) findViewById(R.id.portrait);

        bwLightEffect = (Button) findViewById(R.id.bwlight);
        bwLightEffect1 = (Button) findViewById(R.id.bwlight1);
        bwLightEffect2 = (Button) findViewById(R.id.bwlight2);
        bwLightEffect3 = (Button) findViewById(R.id.bwlight3);
        bwLightEffect4 = (Button) findViewById(R.id.bwlight4);
        bwLightEffect5 = (Button) findViewById(R.id.bwlight5);

        retroEffect = (Button) findViewById(R.id.retro0);
        retroEffect1 = (Button) findViewById(R.id.retro1);
        retroEffect2 = (Button) findViewById(R.id.retro2);
        retroEffect3 = (Button) findViewById(R.id.retro3);
        retroEffect4 = (Button) findViewById(R.id.retro4);
        retroEffect5 = (Button) findViewById(R.id.retro5);

        paintedEffect = (Button) findViewById(R.id.painted0);
        paintedEffect1 = (Button) findViewById(R.id.painted1);
        paintedEffect2 = (Button) findViewById(R.id.painted2);
        paintedEffect3 = (Button) findViewById(R.id.painted3);
        paintedEffect4 = (Button) findViewById(R.id.painted4);
        paintedEffect5 = (Button) findViewById(R.id.painted5);
        //...//
        testEffect1 = (Button) findViewById(R.id.testEffect1);
        testEffect2 = (Button) findViewById(R.id.testEffect2);
        testEffect3 = (Button) findViewById(R.id.testEffect3);
        testEffect4 = (Button) findViewById(R.id.testEffect4);
        testEffect5 = (Button) findViewById(R.id.testEffect5);
        testEffect6 = (Button) findViewById(R.id.testEffect6);

        cameraSwitcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        hwSettings = (ViewFlipper) findViewById(R.id.hwSettings);

        strength = (SeekBar) findViewById(R.id.strength);
        contrast = (SeekBar) findViewById(R.id.contrast);
        brightness = (SeekBar) findViewById(R.id.brightness);
        saturation = (SeekBar) findViewById(R.id.saturation);

        rotation = (SeekBar) findViewById(R.id.rotation);

        vignette = (SeekBar) findViewById(R.id.vignette);

        scrollViewActionBar = (HorizontalScrollView) findViewById(R.id.hwSettings1);
    }

    private void setListener(){
        surface.setOnTouchListener(this);

        viewFlipper.setOnClickListener(this);

        editButton.setOnClickListener(this);
        captureButton.setOnClickListener(this);

        /*TODO: Put this also to gesture listener*/
        nonEffectButton.setLongClickable(true);
        nonEffectButton.setOnLongClickListener(this);
        nonEffectButton.setOnTouchListener(this);

        menuButton.setOnClickListener(this);
        extendButton.setOnClickListener(this);

        switchCameraButton.setOnClickListener(this);
        flashButton.setOnClickListener(this);

        sceneButton.setOnClickListener(this);
        whiteBalanceButton.setOnClickListener(this);
        focusButton.setOnClickListener(this);
        autoExposureLockButton.setOnClickListener(this);

        aeExposureButton.setOnClickListener(this);
        hdrButton.setOnClickListener(this);
        selfTimerButton.setOnClickListener(this);
        centerFocusButton.setOnClickListener(this);

        favoriteButton.setOnClickListener(this);
        effectButton.setOnClickListener(this);
        tuneButton.setOnClickListener(this);
        collageButton.setOnClickListener(this);
        cropButton.setOnClickListener(this);
        vignetteButton.setOnClickListener(this);
        projectionButton.setOnClickListener(this);

        addFavoriteButton.setOnClickListener(this);

        classicButton.setOnClickListener(this);
        bwButton.setOnClickListener(this);
        retroButton.setOnClickListener(this);
        paintedButton.setOnClickListener(this);
        //...//
        testButton.setOnClickListener(this);

        strengthButton.setOnClickListener(this);
        saturationButton.setOnClickListener(this);
        brightnessButton.setOnClickListener(this);
        contrastButton.setOnClickListener(this);


        cropOriginalButton.setOnClickListener(this);
        crop16to9Button.setOnClickListener(this);
        crop3to2Button.setOnClickListener(this);
        crop5to4Button.setOnClickListener(this);
        crop7to5Button.setOnClickListener(this);
        cropdinButton.setOnClickListener(this);
        cropLandscapeButton.setOnClickListener(this);
        cropPortraitButton.setOnClickListener(this);
        cropSquareButton.setOnClickListener(this);


        rotateButton.setOnClickListener(this);
        gridButton.setOnClickListener(this);

        originalEffect.setOnClickListener(this);
        bwEffect.setOnClickListener(this);
        sepiaEffect.setOnClickListener(this);
        negativeEffect.setOnClickListener(this);
        edgeEffect.setOnClickListener(this);
        portraitEffect.setOnClickListener(this);

        bwLightEffect.setOnClickListener(this);
        bwLightEffect1.setOnClickListener(this);
        bwLightEffect2.setOnClickListener(this);
        bwLightEffect3.setOnClickListener(this);
        bwLightEffect4.setOnClickListener(this);
        bwLightEffect5.setOnClickListener(this);

        retroEffect.setOnClickListener(this);
        retroEffect1.setOnClickListener(this);
        retroEffect2.setOnClickListener(this);
        retroEffect3.setOnClickListener(this);
        retroEffect4.setOnClickListener(this);
        retroEffect5.setOnClickListener(this);

        paintedEffect.setOnClickListener(this);
        paintedEffect1.setOnClickListener(this);
        paintedEffect2.setOnClickListener(this);
        paintedEffect3.setOnClickListener(this);
        paintedEffect4.setOnClickListener(this);
        paintedEffect5.setOnClickListener(this);
        //...//
        testEffect1.setOnClickListener(this);
        testEffect2.setOnClickListener(this);
        testEffect3.setOnClickListener(this);
        testEffect4.setOnClickListener(this);
        testEffect5.setOnClickListener(this);
        testEffect6.setOnClickListener(this);

        strength.setOnSeekBarChangeListener(this);
        contrast.setOnSeekBarChangeListener(this);
        brightness.setOnSeekBarChangeListener(this);
        saturation.setOnSeekBarChangeListener(this);

        vignette.setOnSeekBarChangeListener(this);

        rotation.setOnSeekBarChangeListener(this);
    }

    private void initialize(){

        intent = new Intent(this, de.maximal.effectcamera.Settings.class);

        renderer = surface.getRenderer();
        renderer.setDisplayDimensions(getDisplayDimensions());

        tablet = getResources().getBoolean(R.bool.isTablet);

        flash_mode = FLASH_MODE_OFF;
        wb_mode = WHITE_BALANCE_AUTO;
        scene_mode = SCENE_MODE_AUTO;
        ae_mode = AUTO_EXPOSURE_OPEN;
        hdr_mode = HDR_MODE_OFF;
        focus_mode = FOCUS_MODE_AUTO;
        brightness_mode = BRIGHTNESS_LEVEL_0;
        selfTimer_mode= SELF_TIMER_OFF;
        centerFocus_mode = CENTER_FOCUS_OFF;

        back_cam = true;

        extended = 0;

        effect = EFFECT_ORIGINAL;

        vignette_value = ORG_VIGNETTE;

        orientation = ORIENTATION_0;

        renderer.setImageContrast(ORG_CONTRAST * 2.0f);
        renderer.setImageBrightness(ORG_BRIGHTNESS * 2.0f);
        renderer.setImageSaturation(ORG_SATURATION * 2.0f);
        renderer.setImageVignette(ORG_VIGNETTE + 0.1f);


        strength.setProgress(PROGRESS100);
        contrast.setProgress(PROGRESS50);
        brightness.setProgress(PROGRESS50);
        saturation.setProgress(PROGRESS50);
        vignette.setProgress(PROGRESS0);

        visibility_menu = true;

    }

    private void touchAreaButton(){

        final View parent_edit = (View) editButton.getParent();
        parent_edit.post(new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                editButton.getHitRect(rect);
                rect.top -= TOP;
                rect.left -= LEFT;
                rect.bottom += BOTTOM;
                rect.right += RIGHT;
                parent_edit.setTouchDelegate(new TouchDelegate(rect, editButton));
            }
        });

        final View parent_capture = (View) captureButton.getParent();
        parent_capture.post(new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                captureButton.getHitRect(rect);
                rect.top -= TOP;
                rect.left -= LEFT;
                rect.bottom += BOTTOM;
                rect.right += RIGHT;
                parent_capture.setTouchDelegate(new TouchDelegate(rect, captureButton));
            }
        });

        final View parent_nonEffect = (View) nonEffectButton.getParent();
        parent_nonEffect.post(new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                nonEffectButton.getHitRect(rect);
                rect.top -= TOP;
                rect.left -= LEFT;
                rect.bottom += BOTTOM;
                rect.right += RIGHT;
                parent_nonEffect.setTouchDelegate(new TouchDelegate(rect, nonEffectButton));
            }
        });

    }

    private void createActionBar(){


        /*TODO: Anpassen der Variablen und Deklarieren ausserhalb der Methode*/
        bar = getSupportActionBar();
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar, null);
        ActionBar.LayoutParams layoutParams = new  ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        bar.setCustomView(mCustomView, layoutParams);
        bar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);




        /*bar1 = getSupportActionBar();
        bar1.setDisplayShowHomeEnabled(false);
        bar1.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater1 = LayoutInflater.from(this);
        View mCustomView1 = mInflater1.inflate(R.layout.actionbar_bottom, null);
        ActionBar.LayoutParams layoutParams1 = new  ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        bar1.setCustomView(mCustomView1, layoutParams1);
        bar1.setDisplayShowCustomEnabled(true);
        Toolbar parent1 = (Toolbar) mCustomView1.getParent();
        parent1.setContentInsetsAbsolute(0, 0);*/


    }


    private void createDatabase(){

        //SQLHelper mDbHelper = new SQLHelper(this);
        //effectCameraFavorites = mDbHelper.getWritableDatabase();
        //values = new ContentValues();

    }

    private void createGestureDetector(){

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);



    }

    private void orientationListener(){

                /*TODO: Listener einbauen falls während die App läuft der Knops Orientierung ändern gedrückt wird*/

        orientationEventListener = new OrientationEventListener(this, SENSOR_DELAY_NORMAL){

            @Override
            public void onOrientationChanged(int arg0) {

                if(arg0 >= 315 && arg0 < 360 || arg0 >= 0 && arg0 < 45){

                    orientation = ORIENTATION_0;

                    /*favoriteButton.setText(getResources().getString(R.string.favorite_button));
                    effectButton.setText(getResources().getString(R.string.effect_button));
                    tuneButton.setText(getResources().getString(R.string.tune_button));
                    collageButton.setText(getResources().getString(R.string.collage_button));
                    cropButton.setText(getResources().getString(R.string.crop_button));
                    vignetteButton.setText(getResources().getString(R.string.vignette_button));

                    addFavoriteButton.setText(getResources().getString(R.string.favorite_button));

                    classicButton.setText(getResources().getString(R.string.classic_button));
                    bwButton.setText(getResources().getString(R.string.bw_button));

                    strengthButton.setText(getResources().getString(R.string.strength_button));
                    saturationButton.setText(getResources().getString(R.string.saturation_button));
                    brightnessButton.setText(getResources().getString(R.string.brightness_button));
                    contrastButton.setText(getResources().getString(R.string.contrast_button));

                    originalEffect.setText(getResources().getString(R.string.original));
                    bwEffect.setText(getResources().getString(R.string.blackwhite));
                    sepiaEffect.setText(getResources().getString(R.string.sepia));
                    negativeEffect.setText(getResources().getString(R.string.negative));
                    edgeEffect.setText(getResources().getString(R.string.edge));
                    portraitEffect.setText(getResources().getString(R.string.portrait));

                    bwLightEffect.setText(getResources().getString(R.string.noText));
                    bwLightEffect1.setText(getResources().getString(R.string.noText));
                    bwLightEffect2.setText(getResources().getString(R.string.noText));
                    bwLightEffect3.setText(getResources().getString(R.string.noText));
                    bwLightEffect4.setText(getResources().getString(R.string.noText));
                    bwLightEffect5.setText(getResources().getString(R.string.noText));*/

                    if(tablet){



                        captureButton.setRotation(ROTATION90);
                        editButton.setRotation(ROTATION90);
                        nonEffectButton.setRotation(ROTATION90);

                        captureButton.setRotation(ROTATION90);
                        editButton.setRotation(ROTATION90);
                        nonEffectButton.setRotation(ROTATION90);

                        favoriteButton.setRotation(ROTATION90);
                        effectButton.setRotation(ROTATION90);
                        tuneButton.setRotation(ROTATION90);
                        collageButton.setRotation(ROTATION90);
                        cropButton.setRotation(ROTATION90);
                        vignetteButton.setRotation(ROTATION90);

                        strengthButton.setRotation(ROTATION90);
                        brightnessButton.setRotation(ROTATION90);
                        contrastButton.setRotation(ROTATION90);
                        saturationButton.setRotation(ROTATION90);

                        classicButton.setRotation(ROTATION90);
                        bwButton.setRotation(ROTATION90);

                        originalEffect.setRotation(ROTATION90);
                        bwEffect.setRotation(ROTATION90);
                        sepiaEffect.setRotation(ROTATION90);
                        negativeEffect.setRotation(ROTATION90);
                        edgeEffect.setRotation(ROTATION90);
                        portraitEffect.setRotation(ROTATION90);

                    }
                    else {

                        captureButton.setRotation(ROTATION0);
                        editButton.setRotation(ROTATION0);
                        nonEffectButton.setRotation(ROTATION0);

                        favoriteButton.setRotation(ROTATION0);
                        effectButton.setRotation(ROTATION0);
                        tuneButton.setRotation(ROTATION0);
                        collageButton.setRotation(ROTATION0);
                        cropButton.setRotation(ROTATION0);
                        vignetteButton.setRotation(ROTATION0);

                        strengthButton.setRotation(ROTATION0);
                        brightnessButton.setRotation(ROTATION0);
                        contrastButton.setRotation(ROTATION0);
                        saturationButton.setRotation(ROTATION0);

                        classicButton.setRotation(ROTATION0);
                        bwButton.setRotation(ROTATION0);

                        originalEffect.setRotation(ROTATION0);
                        bwEffect.setRotation(ROTATION0);
                        sepiaEffect.setRotation(ROTATION0);
                        negativeEffect.setRotation(ROTATION0);
                        edgeEffect.setRotation(ROTATION0);
                        portraitEffect.setRotation(ROTATION0);

                    }

                }
                else if(arg0 >= 45 && arg0 < 135){

                    orientation = ORIENTATION_90;


                    /*favoriteButton.setText(getResources().getString(R.string.noText));
                    effectButton.setText(getResources().getString(R.string.noText));
                    tuneButton.setText(getResources().getString(R.string.noText));
                    collageButton.setText(getResources().getString(R.string.noText));
                    cropButton.setText(getResources().getString(R.string.noText));
                    vignetteButton.setText(getResources().getString(R.string.noText));

                    addFavoriteButton.setText(getResources().getString(R.string.noText));

                    classicButton.setText(getResources().getString(R.string.noText));
                    bwButton.setText(getResources().getString(R.string.noText));

                    strengthButton.setText(getResources().getString(R.string.noText));
                    saturationButton.setText(getResources().getString(R.string.noText));
                    brightnessButton.setText(getResources().getString(R.string.noText));
                    contrastButton.setText(getResources().getString(R.string.noText));

                    originalEffect.setText(getResources().getString(R.string.noText));
                    bwEffect.setText(getResources().getString(R.string.noText));
                    sepiaEffect.setText(getResources().getString(R.string.noText));
                    negativeEffect.setText(getResources().getString(R.string.noText));
                    edgeEffect.setText(getResources().getString(R.string.noText));
                    portraitEffect.setText(getResources().getString(R.string.noText));

                    bwLightEffect.setText(getResources().getString(R.string.noText));
                    bwLightEffect1.setText(getResources().getString(R.string.noText));
                    bwLightEffect2.setText(getResources().getString(R.string.noText));
                    bwLightEffect3.setText(getResources().getString(R.string.noText));
                    bwLightEffect4.setText(getResources().getString(R.string.noText));
                    bwLightEffect5.setText(getResources().getString(R.string.noText));*/

                    if(tablet){
                        captureButton.setRotation(ROTATION0);
                        editButton.setRotation(ROTATION0);
                        nonEffectButton.setRotation(ROTATION0);

                        favoriteButton.setRotation(ROTATION0);
                        effectButton.setRotation(ROTATION0);
                        tuneButton.setRotation(ROTATION0);
                        collageButton.setRotation(ROTATION0);
                        cropButton.setRotation(ROTATION0);
                        vignetteButton.setRotation(ROTATION0);

                        strengthButton.setRotation(ROTATION0);
                        brightnessButton.setRotation(ROTATION0);
                        contrastButton.setRotation(ROTATION0);
                        saturationButton.setRotation(ROTATION0);

                        classicButton.setRotation(ROTATION0);
                        bwButton.setRotation(ROTATION0);

                        originalEffect.setRotation(ROTATION0);
                        bwEffect.setRotation(ROTATION0);
                        sepiaEffect.setRotation(ROTATION0);
                        negativeEffect.setRotation(ROTATION0);
                        edgeEffect.setRotation(ROTATION0);
                        portraitEffect.setRotation(ROTATION0);

                    }
                    else {

                        captureButton.setRotation(ROTATION270);
                        editButton.setRotation(ROTATION270);
                        nonEffectButton.setRotation(ROTATION270);

                        favoriteButton.setRotation(ROTATION270);
                        effectButton.setRotation(ROTATION270);
                        tuneButton.setRotation(ROTATION270);
                        collageButton.setRotation(ROTATION270);
                        cropButton.setRotation(ROTATION270);
                        vignetteButton.setRotation(ROTATION270);

                        strengthButton.setRotation(ROTATION270);
                        brightnessButton.setRotation(ROTATION270);
                        contrastButton.setRotation(ROTATION270);
                        saturationButton.setRotation(ROTATION270);

                        classicButton.setRotation(ROTATION270);
                        bwButton.setRotation(ROTATION270);

                        originalEffect.setRotation(ROTATION270);
                        bwEffect.setRotation(ROTATION270);
                        sepiaEffect.setRotation(ROTATION270);
                        negativeEffect.setRotation(ROTATION270);
                        edgeEffect.setRotation(ROTATION270);
                        portraitEffect.setRotation(ROTATION270);


                    }

                }
                else if(arg0 >= 135 && arg0 < 225){

                    orientation = ORIENTATION_180;

                    /*favoriteButton.setText(getResources().getString(R.string.favorite_button));
                    effectButton.setText(getResources().getString(R.string.effect_button));
                    tuneButton.setText(getResources().getString(R.string.tune_button));
                    collageButton.setText(getResources().getString(R.string.collage_button));
                    cropButton.setText(getResources().getString(R.string.crop_button));
                    vignetteButton.setText(getResources().getString(R.string.vignette_button));

                    addFavoriteButton.setText(getResources().getString(R.string.favorite_button));

                    classicButton.setText(getResources().getString(R.string.classic_button));
                    bwButton.setText(getResources().getString(R.string.bw_button));

                    strengthButton.setText(getResources().getString(R.string.strength_button));
                    saturationButton.setText(getResources().getString(R.string.saturation_button));
                    brightnessButton.setText(getResources().getString(R.string.brightness_button));
                    contrastButton.setText(getResources().getString(R.string.contrast_button));

                    originalEffect.setText(getResources().getString(R.string.original));
                    bwEffect.setText(getResources().getString(R.string.blackwhite));
                    sepiaEffect.setText(getResources().getString(R.string.sepia));
                    negativeEffect.setText(getResources().getString(R.string.negative));
                    edgeEffect.setText(getResources().getString(R.string.edge));
                    portraitEffect.setText(getResources().getString(R.string.portrait));

                    bwLightEffect.setText(getResources().getString(R.string.noText));
                    bwLightEffect1.setText(getResources().getString(R.string.noText));
                    bwLightEffect2.setText(getResources().getString(R.string.noText));
                    bwLightEffect3.setText(getResources().getString(R.string.noText));
                    bwLightEffect4.setText(getResources().getString(R.string.noText));
                    bwLightEffect5.setText(getResources().getString(R.string.noText));*/

                    if(tablet){
                        captureButton.setRotation(ROTATION270);
                        editButton.setRotation(ROTATION270);
                        nonEffectButton.setRotation(ROTATION270);

                        favoriteButton.setRotation(ROTATION270);
                        effectButton.setRotation(ROTATION270);
                        tuneButton.setRotation(ROTATION270);
                        collageButton.setRotation(ROTATION270);
                        cropButton.setRotation(ROTATION270);
                        vignetteButton.setRotation(ROTATION270);

                        strengthButton.setRotation(ROTATION270);
                        brightnessButton.setRotation(ROTATION270);
                        contrastButton.setRotation(ROTATION270);
                        saturationButton.setRotation(ROTATION270);

                        classicButton.setRotation(ROTATION270);
                        bwButton.setRotation(ROTATION270);

                        originalEffect.setRotation(ROTATION270);
                        bwEffect.setRotation(ROTATION270);
                        sepiaEffect.setRotation(ROTATION270);
                        negativeEffect.setRotation(ROTATION270);
                        edgeEffect.setRotation(ROTATION270);
                        portraitEffect.setRotation(ROTATION270);

                    }
                    else {

                        captureButton.setRotation(ROTATION180);
                        editButton.setRotation(ROTATION180);
                        nonEffectButton.setRotation(ROTATION180);

                        favoriteButton.setRotation(ROTATION180);
                        effectButton.setRotation(ROTATION180);
                        tuneButton.setRotation(ROTATION180);
                        collageButton.setRotation(ROTATION180);
                        cropButton.setRotation(ROTATION180);
                        vignetteButton.setRotation(ROTATION180);

                        strengthButton.setRotation(ROTATION180);
                        brightnessButton.setRotation(ROTATION180);
                        contrastButton.setRotation(ROTATION180);
                        saturationButton.setRotation(ROTATION180);

                        classicButton.setRotation(ROTATION180);
                        bwButton.setRotation(ROTATION180);

                        originalEffect.setRotation(ROTATION180);
                        bwEffect.setRotation(ROTATION180);
                        sepiaEffect.setRotation(ROTATION180);
                        negativeEffect.setRotation(ROTATION180);
                        edgeEffect.setRotation(ROTATION180);
                        portraitEffect.setRotation(ROTATION180);

                    }

                }
                else if(arg0 >= 225 && arg0 < 315){

                    orientation = ORIENTATION_270;

                    /*favoriteButton.setText(getResources().getString(R.string.noText));
                    effectButton.setText(getResources().getString(R.string.noText));
                    tuneButton.setText(getResources().getString(R.string.noText));
                    collageButton.setText(getResources().getString(R.string.noText));
                    cropButton.setText(getResources().getString(R.string.noText));
                    vignetteButton.setText(getResources().getString(R.string.noText));

                    addFavoriteButton.setText(getResources().getString(R.string.noText));

                    classicButton.setText(getResources().getString(R.string.noText));
                    bwButton.setText(getResources().getString(R.string.noText));

                    strengthButton.setText(getResources().getString(R.string.noText));
                    saturationButton.setText(getResources().getString(R.string.noText));
                    brightnessButton.setText(getResources().getString(R.string.noText));
                    contrastButton.setText(getResources().getString(R.string.noText));

                    originalEffect.setText(getResources().getString(R.string.noText));
                    bwEffect.setText(getResources().getString(R.string.noText));
                    sepiaEffect.setText(getResources().getString(R.string.noText));
                    negativeEffect.setText(getResources().getString(R.string.noText));
                    edgeEffect.setText(getResources().getString(R.string.noText));
                    portraitEffect.setText(getResources().getString(R.string.noText));

                    bwLightEffect.setText(getResources().getString(R.string.noText));
                    bwLightEffect1.setText(getResources().getString(R.string.noText));
                    bwLightEffect2.setText(getResources().getString(R.string.noText));
                    bwLightEffect3.setText(getResources().getString(R.string.noText));
                    bwLightEffect4.setText(getResources().getString(R.string.noText));
                    bwLightEffect5.setText(getResources().getString(R.string.noText));*/

                    if(tablet){
                        captureButton.setRotation(ROTATION180);
                        editButton.setRotation(ROTATION180);
                        nonEffectButton.setRotation(ROTATION180);

                        favoriteButton.setRotation(ROTATION180);
                        effectButton.setRotation(ROTATION180);
                        tuneButton.setRotation(ROTATION180);
                        collageButton.setRotation(ROTATION180);
                        cropButton.setRotation(ROTATION180);
                        vignetteButton.setRotation(ROTATION180);

                        strengthButton.setRotation(ROTATION180);
                        brightnessButton.setRotation(ROTATION180);
                        contrastButton.setRotation(ROTATION180);
                        saturationButton.setRotation(ROTATION180);

                        classicButton.setRotation(ROTATION180);
                        bwButton.setRotation(ROTATION180);

                        originalEffect.setRotation(ROTATION180);
                        bwEffect.setRotation(ROTATION180);
                        sepiaEffect.setRotation(ROTATION180);
                        negativeEffect.setRotation(ROTATION180);
                        edgeEffect.setRotation(ROTATION180);
                        portraitEffect.setRotation(ROTATION180);

                    }
                    else {

                        captureButton.setRotation(ROTATION90);
                        editButton.setRotation(ROTATION90);
                        nonEffectButton.setRotation(ROTATION90);

                        captureButton.setRotation(ROTATION90);
                        editButton.setRotation(ROTATION90);
                        nonEffectButton.setRotation(ROTATION90);

                        favoriteButton.setRotation(ROTATION90);
                        effectButton.setRotation(ROTATION90);
                        tuneButton.setRotation(ROTATION90);
                        collageButton.setRotation(ROTATION90);
                        cropButton.setRotation(ROTATION90);
                        vignetteButton.setRotation(ROTATION90);

                        strengthButton.setRotation(ROTATION90);
                        brightnessButton.setRotation(ROTATION90);
                        contrastButton.setRotation(ROTATION90);
                        saturationButton.setRotation(ROTATION90);

                        classicButton.setRotation(ROTATION90);
                        bwButton.setRotation(ROTATION90);

                        originalEffect.setRotation(ROTATION90);
                        bwEffect.setRotation(ROTATION90);
                        sepiaEffect.setRotation(ROTATION90);
                        negativeEffect.setRotation(ROTATION90);
                        edgeEffect.setRotation(ROTATION90);
                        portraitEffect.setRotation(ROTATION90);

                    }

                }

            }};

        if (orientationEventListener.canDetectOrientation()) {

            orientationEventListener.enable();

        }


    }

    private void createNavigationDrawer(){

        //TEST
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerTitles = getResources().getStringArray(R.array.drawerTitles_array);
        drawerIcons = new int[] {android.R.drawable.ic_menu_manage,
                android.R.drawable.ic_menu_edit,
                android.R.drawable.ic_menu_help,
                android.R.drawable.ic_menu_info_details,
                android.R.drawable.ic_menu_close_clear_cancel};


        NavDrawerItem mMenuAdapter = new NavDrawerItem(this, drawerTitles, drawerIcons);
        mDrawerList.setAdapter(mMenuAdapter);
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setOnItemClickListener(this);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }



    /*TODO: CHECKING HARDWARE SPECIFIC CAMERA OPTIONS E.G. IS FLASH AVAILABLE*/
    private void checkCameraProperties(){
        //CameraManager manager;
        //String[] character_values = {"Hallo", "test"};
        //CameraCharacteristics c = camera.getCameraCharacteristic(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE);



    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v == surface) {

            /*float normal;

            Point size = getDisplayDimensions();
            height = size.y;
            width = size.x;
            orientation = ORIENTATION_0;
            if(orientation == ORIENTATION_0 || orientation == ORIENTATION_180){

                normal = (event.getY() - event.getY()) / height;

                if(Math.abs(normal) > 0.15f) {

                    if (normal < 0) {
                        if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_4) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_0;
                            camera.setBrightness(BRIGHTNESS_LEVEL_0);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_4;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_4);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);

                        }
                    } else if (normal >= 0) {
                        if (brightness_mode == BRIGHTNESS_LEVEL_DARK_4) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_0;
                            camera.setBrightness(BRIGHTNESS_LEVEL_0);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                            event.setLocation(event.getX(), event.getY());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_4;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_4);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);
                        }
                    }
                }
                return true;
            }
            else if(orientation == ORIENTATION_90 || orientation == ORIENTATION_270){
                normal = (event.getX() - event.getX()) / width;

                if(Math.abs(normal) > 0.15f) {

                    if (normal < 0) {

                        if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_4) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_0;
                            camera.setBrightness(BRIGHTNESS_LEVEL_0);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_4;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_4);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);

                        }
                    } else if (normal >= 0) {
                        if (brightness_mode == BRIGHTNESS_LEVEL_DARK_4) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_0;
                            camera.setBrightness(BRIGHTNESS_LEVEL_0);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_1;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_1);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                        } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                            event.setLocation(event.getY(), event.getX());
                            brightness_mode = BRIGHTNESS_LEVEL_LIGHT_4;
                            camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_4);
                            aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);

                        }
                    }
                }
                return true;
            }*/


            //this.mDetector.onTouchEvent(event);
            //return super.onTouchEvent(event);
            return true;

        }
        else if(v == nonEffectButton){

            int effect1 = effect;
            float contrast_value1 = contrast_value;
            float brightness_value1 = brightness_value;
            float saturation_value1 = saturation_value;
            float vignette_value1 = vignette_value;

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                /*TODO: why 2 times? somewhere we might divie it by two for the original effect*/
                renderer.effect = EFFECT_ORIGINAL;
                renderer.setImageContrast(ORG_CONTRAST * 2.0f);
                renderer.setImageBrightness(ORG_BRIGHTNESS * 2.0f);
                renderer.setImageSaturation(ORG_SATURATION * 2.0f);
                renderer.setImageVignette(ORG_VIGNETTE + 0.1f);

                return true;
            }

            else if(event.getAction() == MotionEvent.ACTION_UP){

                long eventDuration = event.getEventTime() - event.getDownTime();

                if(eventDuration > TOUCH_DURATION_LONG){

                    renderer.effect = EFFECT_ORIGINAL;
                    renderer.setImageContrast(ORG_CONTRAST * 2.0f);
                    renderer.setImageBrightness(ORG_BRIGHTNESS * 2.0f);
                    renderer.setImageSaturation(ORG_SATURATION * 2.0f);
                    renderer.setImageVignette(ORG_VIGNETTE + 0.1f);


                } else{

                    effect = effect1;
                    renderer.effect = effect1;
                    renderer.setImageContrast(contrast_value1);
                    renderer.setImageBrightness(brightness_value1);
                    renderer.setImageSaturation(saturation_value1);
                    renderer.setImageVignette(vignette_value1);
                }

                return true;
            }

        }

        return false;

    }

    @Override
    public void onClick(View v) {

        if(v.getId()== R.id.capturePic){

             camera.takePicture();

        }

        else if(v.getId() == R.id.extend){

            if(extended == 0){

                extendButton.setImageResource(R.mipmap.ic_looks_one_white_24dp);
                actionBarImage.setVisibility(View.GONE);

                sceneButton.setVisibility(View.VISIBLE);
                whiteBalanceButton.setVisibility(View.VISIBLE);
                focusButton.setVisibility(View.VISIBLE);
                autoExposureLockButton.setVisibility(View.VISIBLE);
                aeExposureButton.setVisibility(View.VISIBLE);
                hdrButton.setVisibility(View.VISIBLE);
                selfTimerButton.setVisibility(View.VISIBLE);
                centerFocusButton.setVisibility(View.VISIBLE);
                hwSettings.setDisplayedChild(0);

                extended = 1;

            }
            else if(extended == 1){



                extendButton.setImageResource(R.mipmap.ic_looks_two_white_24dp);
                hwSettings.setDisplayedChild(1);
                extended = 2;

            }
            else if(extended == 2){


                extendButton.setImageResource(R.mipmap.ic_looks_3_white_24dp);
                hwSettings.setDisplayedChild(2);
                extended = 3;

            }
            else if(extended == 3){
                hdrButton.setVisibility(View.GONE);
                aeExposureButton.setVisibility(View.GONE);
                sceneButton.setVisibility(View.GONE);
                whiteBalanceButton.setVisibility(View.GONE);
                focusButton.setVisibility(View.GONE);
                autoExposureLockButton.setVisibility(View.GONE);
                selfTimerButton.setVisibility(View.GONE);
                centerFocusButton.setVisibility(View.GONE);


                hwSettings.setDisplayedChild(0);
                extendButton.setImageResource(R.mipmap.ic_more_horiz_white_24dp);
                actionBarImage.setVisibility(View.VISIBLE);
                extended = 0;
            }

        }

        else if(v.getId() == R.id.menu){
            currentView = viewFlipper.getDisplayedChild();
            if(currentView != STAGE_TOP){

                if(currentView == STAGE_TUNE_SATURATION || currentView == STAGE_TUNE_CONTRAST || currentView == STAGE_TUNE_BRIGHTNESS || currentView == STAGE_TUNE_STRENGTH){
                    viewFlipper.setDisplayedChild(STAGE_TUNE);
                    currentView = STAGE_TUNE;
                }
                else if(currentView == STAGE_EFFECT_CLASSIC || currentView == STAGE_EFFECT_BW || currentView == STAGE_EFFECT_RETRO || currentView == STAGE_EFFECT_PAINTED){

                    viewFlipper.setDisplayedChild(STAGE_EFFECT);
                    currentView = STAGE_EFFECT;

                }
                else if(currentView == STAGE_FAVORITE || currentView == STAGE_EFFECT || currentView == STAGE_TUNE || currentView == STAGE_COLLAGE
                        || currentView == STAGE_CROP || currentView == STAGE_VIGNETTE || currentView == STAGE_PROJECTION){

                    viewFlipper.setDisplayedChild(STAGE_EDIT);
                    currentView = STAGE_EDIT;

                }
                else if(currentView == STAGE_EDIT){

                    viewFlipper.setDisplayedChild(STAGE_TOP);
                    currentView = STAGE_TOP;
                    menuButton.setImageResource(R.mipmap.ic_menu_white_24dp);

                }

            }
            else{

                /*TODO: Navigation Drawer*/
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                    viewFlipper.bringToFront();
                    surface.bringToFront();

                } else {
                    mDrawerLayout.openDrawer(mDrawerList);

                    mDrawerLayout.bringToFront();
                    viewFlipper.bringToFront();
                }
            }

        } else if(v.getId() == R.id.switch_camera){

            if(back_cam){
                renderer.projectionMatrix = PROJECTION_MATRIX_FRONT;
                back_cam = false;
            }
            else{
                renderer.projectionMatrix = PROJECTION_MATRIX_BACK;
                back_cam = true;
            }

            camera.changeCamera();


        }

        else if(v.getId() == R.id.flash){

            if(flash_mode == FLASH_MODE_OFF) {

                camera.setFlash(CameraMetadata.FLASH_MODE_SINGLE);
                flash_mode = FLASH_MODE_ON;
                flashButton.setImageResource(R.mipmap.ic_flash_on_white_24dp);
            }
            else if(flash_mode == FLASH_MODE_ON) {


                camera.setFlash(CameraMetadata.FLASH_MODE_TORCH);
                flash_mode = FLASH_MODE_AUTO;
                flashButton.setImageResource(R.mipmap.ic_flash_auto_white_24dp);

            }
            else if(flash_mode == FLASH_MODE_AUTO) {

                camera.setFlash(CameraMetadata.FLASH_MODE_OFF);
                flash_mode = FLASH_MODE_OFF;
                flashButton.setImageResource(R.mipmap.ic_flash_off_white_24dp);
            }

        }

        else if(v.getId() == R.id.autoExposureLock){
            if(!ae_mode){
                autoExposureLockButton.setImageResource(R.mipmap.ic_lock_outline_white_24dp);
                ae_mode = AUTO_EXPOSURE_LOCK;
                camera.setAutoExposureLock(AUTO_EXPOSURE_LOCK);
            }
            else{
                autoExposureLockButton.setImageResource(R.mipmap.ic_lock_open_white_24dp);
                ae_mode = AUTO_EXPOSURE_OPEN;
                camera.setAutoExposureLock(AUTO_EXPOSURE_OPEN);
            }


        }

        else if(v.getId() == R.id.hdr){

            if(!hdr_mode){

                hdrButton.setImageResource(R.mipmap.ic_hdr_on_white_24dp);
                hdr_mode = HDR_MODE_ON;
                camera.setHDR(HDR_MODE_ON);

            }
            else{
                hdrButton.setImageResource(R.mipmap.ic_hdr_off_white_24dp);
                hdr_mode = HDR_MODE_OFF;
                camera.setHDR(HDR_MODE_OFF);
            }

        }

        else if(v.getId() == R.id.aeExposure){


            if(brightness_mode == BRIGHTNESS_LEVEL_DARK_4){
                camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);
            }
            else if(brightness_mode == BRIGHTNESS_LEVEL_DARK_3){
                camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);
            }
            else if(brightness_mode == BRIGHTNESS_LEVEL_DARK_2){
                camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);
            }
            else if(brightness_mode == BRIGHTNESS_LEVEL_DARK_1){
                camera.setBrightness(BRIGHTNESS_LEVEL_0);
                brightness_mode = BRIGHTNESS_LEVEL_0;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);
            }
            else if(brightness_mode == BRIGHTNESS_LEVEL_0){
                camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_1);
                brightness_mode = BRIGHTNESS_LEVEL_LIGHT_1;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);
            }

            else if(brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1){
                camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);
            }
            else if(brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2){
                camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);
            }
            else if(brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3){
                camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_4);
                brightness_mode = BRIGHTNESS_LEVEL_LIGHT_4;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);
            }
            else if(brightness_mode == BRIGHTNESS_LEVEL_LIGHT_4){
                camera.setBrightness(BRIGHTNESS_LEVEL_DARK_4);
                brightness_mode = BRIGHTNESS_LEVEL_DARK_4;
                aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);
            }

            /*TODO: IWAS BASST DA NO NED*/
            /*switch(brightness_mode) {

                case BRIGHTNESS_LEVEL_DARK_3:
                    camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                    brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                    aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);
                case BRIGHTNESS_LEVEL_DARK_2:
                    camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                    brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                    aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);
                case BRIGHTNESS_LEVEL_DARK_1:
                    camera.setBrightness(BRIGHTNESS_LEVEL_0);
                    brightness_mode = BRIGHTNESS_LEVEL_0;
                    aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);
                case BRIGHTNESS_LEVEL_0:
                    camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_1);
                    brightness_mode = BRIGHTNESS_LEVEL_LIGHT_1;
                    aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                case BRIGHTNESS_LEVEL_LIGHT_1:
                    camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                    brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                    aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);
                case BRIGHTNESS_LEVEL_LIGHT_2:
                    camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                    brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                    aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);
                case BRIGHTNESS_LEVEL_LIGHT_3:
                    camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                    brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                    aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

            }*/

        }

        /*TODO: ICONS!! & iwas passt da noch nicht*/
        else if(v.getId() == R.id.focus){

            if(focus_mode.equals(FOCUS_MODE_AUTO)){
                focusButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                focus_mode = FOCUS_MODE_CONTINUOUS_PICTURE;
                camera.setFocus(FOCUS_MODE_CONTINUOUS_PICTURE);
            }
            else if(focus_mode.equals(FOCUS_MODE_CONTINUOUS_PICTURE)){
                focusButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                focus_mode = FOCUS_MODE_EDOF;
                camera.setFocus(FOCUS_MODE_FIXED);
            }
            else if(focus_mode.equals(FOCUS_MODE_EDOF)){
                focusButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                focus_mode = FOCUS_MODE_FIXED;
                camera.setFocus(FOCUS_MODE_FIXED);
            }
            else if(focus_mode.equals(FOCUS_MODE_FIXED)){
                focusButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                focus_mode = FOCUS_MODE_INFINITY;
                camera.setFocus(FOCUS_MODE_INFINITY);
            }
            else if(focus_mode.equals(FOCUS_MODE_INFINITY)){
                focusButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                focus_mode = FOCUS_MODE_MACRO;
                camera.setFocus(FOCUS_MODE_MACRO);
            }
            else if(focus_mode.equals(FOCUS_MODE_MACRO)){
                focusButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                focus_mode = FOCUS_MODE_AUTO;
                camera.setFocus(FOCUS_MODE_AUTO);
            }


        }


        /*TODO: first of all check whether the parameters are available on device*/
        else if(v.getId() == R.id.whiteBalance){

            if(wb_mode.equals(WHITE_BALANCE_AUTO)){
                whiteBalanceButton.setImageResource(R.mipmap.ic_wb_cloudy_white_24dp);
                wb_mode = WHITE_BALANCE_CLOUDY_DAYLIGHT;
                camera.setWhiteBalance(WHITE_BALANCE_CLOUDY_DAYLIGHT);
            }
            else if(wb_mode.equals(WHITE_BALANCE_CLOUDY_DAYLIGHT)){
                whiteBalanceButton.setImageResource(R.mipmap.ic_wb_sunny_white_24dp);
                wb_mode = WHITE_BALANCE_DAYLIGHT;
                camera.setWhiteBalance(WHITE_BALANCE_DAYLIGHT);
            }
            else if(wb_mode.equals(WHITE_BALANCE_DAYLIGHT)){
                whiteBalanceButton.setImageResource(R.mipmap.ic_wb_incandescent_white_24dp);
                wb_mode = WHITE_BALANCE_INCANDESCENT;
                camera.setWhiteBalance(WHITE_BALANCE_INCANDESCENT);
            }
            else if(wb_mode.equals(WHITE_BALANCE_INCANDESCENT)){
                whiteBalanceButton.setImageResource(R.mipmap.ic_wb_iridescent_white_24dp);
                wb_mode = WHITE_BALANCE_WARM_FLUORESCENT;

                /*TODO: richtigen parameter setzen*/
                camera.setWhiteBalance(WHITE_BALANCE_INCANDESCENT);
            }
            else if(wb_mode.equals(WHITE_BALANCE_WARM_FLUORESCENT)){

                whiteBalanceButton.setImageResource(R.mipmap.ic_wb_auto_white_24dp);
                wb_mode = WHITE_BALANCE_AUTO;
                camera.setWhiteBalance(WHITE_BALANCE_AUTO);

            }


        }
        /*TODO: ICONS!!  & sth does not work as expected.. prob. we have to check for availability of parameter*/
        else if(v.getId() == R.id.scene){

            if(scene_mode.equals(SCENE_MODE_AUTO)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_ACTION;
                camera.setScene(SCENE_MODE_ACTION);
            }
            else if(scene_mode.equals(SCENE_MODE_ACTION)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_BARCODE;
                camera.setScene(SCENE_MODE_NIGHT);
            }
            else if(scene_mode.equals(SCENE_MODE_BARCODE)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_BEACH;
                camera.setScene(SCENE_MODE_BEACH);
            }
            else if(scene_mode.equals(SCENE_MODE_BEACH)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_CANDLELIGHT;
                camera.setScene(SCENE_MODE_CANDLELIGHT);
            }
            else if(scene_mode.equals(SCENE_MODE_CANDLELIGHT)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_FIREWORKS;
                camera.setScene(SCENE_MODE_FIREWORKS);
            }
            else if(scene_mode.equals(SCENE_MODE_FIREWORKS)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_HDR;
                camera.setScene(SCENE_MODE_HDR);
            }
            else if(scene_mode.equals(SCENE_MODE_HDR)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_LANDSCAPE;
                camera.setScene(SCENE_MODE_LANDSCAPE);
            }
            else if(scene_mode.equals(SCENE_MODE_LANDSCAPE)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_NIGHT;
                camera.setScene(SCENE_MODE_NIGHT);
            }
            else if(scene_mode.equals(SCENE_MODE_NIGHT)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_NIGHT_PORTRAIT;
                camera.setScene(SCENE_MODE_NIGHT_PORTRAIT);
            }
            else if(scene_mode.equals(SCENE_MODE_NIGHT_PORTRAIT)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_PARTY;
                camera.setScene(SCENE_MODE_PARTY);
            }
            else if(scene_mode.equals(SCENE_MODE_PARTY)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_PORTRAIT;
                camera.setScene(SCENE_MODE_PORTRAIT);
            }
            else if(scene_mode.equals(SCENE_MODE_PORTRAIT)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_SNOW;
                camera.setScene(SCENE_MODE_SNOW);
            }
            else if(scene_mode.equals(SCENE_MODE_SNOW)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_SPORTS;
                camera.setScene(SCENE_MODE_SPORTS);
            }
            else if(scene_mode.equals(SCENE_MODE_SPORTS)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_SUNSET;
                camera.setScene(SCENE_MODE_SUNSET);
            }
            else if(scene_mode.equals(SCENE_MODE_SUNSET)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_THEATRE;
                camera.setScene(SCENE_MODE_THEATRE);
            }
            else if(scene_mode.equals(SCENE_MODE_THEATRE)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_STEADYPHOTO;
                camera.setScene(SCENE_MODE_STEADYPHOTO);
            }
            else if(scene_mode.equals(SCENE_MODE_STEADYPHOTO)){
                sceneButton.setImageResource(R.mipmap.ic_favorite_white_24dp);
                scene_mode = SCENE_MODE_AUTO;
                camera.setScene(SCENE_MODE_AUTO);
            }

        }

        else if(v.getId() == R.id.selfTimer){

            if(selfTimer_mode == SELF_TIMER_OFF){

                selfTimerButton.setImageResource(R.mipmap.ic_timer_3_white_24dp);
                selfTimer_mode = SELF_TIMER_3;

            }
            else if(selfTimer_mode == SELF_TIMER_3){
                selfTimerButton.setImageResource(R.mipmap.ic_timer_10_white_24dp);
                selfTimer_mode = SELF_TIMER_10;

            }
            else if(selfTimer_mode == SELF_TIMER_10){
                selfTimerButton.setImageResource(R.mipmap.ic_timer_off_white_24dp);
                selfTimer_mode = SELF_TIMER_OFF;

            }


        }

        else if(v.getId() == R.id.centerFocus){

            if(!centerFocus_mode){
                    centerFocus_mode = CENTER_FOCUS_ON;
                    centerFocusButton.setImageResource(R.mipmap.ic_center_focus_strong_white_24dp);
            }
            else{

                centerFocus_mode = CENTER_FOCUS_OFF;
                centerFocusButton.setImageResource(R.mipmap.ic_center_focus_weak_white_24dp);
            }
        }

        else if(v.getId() == R.id.viewFlipper){

            if(visibility_menu){
                viewFlipper.setBackgroundColor(Color.TRANSPARENT);
                visibility_menu = false;
            }
            else{
                viewFlipper.setBackgroundColor(getResources().getColor(R.color.background_color));
                visibility_menu = true;
            }

        }

        else if(v.getId() == R.id.edit) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_EDIT);

            menuButton.setImageResource(R.mipmap.ic_chevron_left_white_24dp);

        }

        else if(v.getId() == R.id.favorite){

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_FAVORITE);

        }

        else if(v.getId() == R.id.effect){

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_EFFECT);

        }

        else if(v.getId() == R.id.tune){

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_TUNE);

        }

        else if(v.getId() == R.id.collage){

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_COLLAGE);

        }

        else if(v.getId() == R.id.crop){

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_CROP);

        }

        else if(v.getId() == R.id.vignetteB){

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_VIGNETTE);

        }

        else if (v.getId() == R.id.projection) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_PROJECTION);

        }

        else if (v.getId() == R.id.strengthB) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_TUNE_STRENGTH);

        }

        else if (v.getId() == R.id.saturationB) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_TUNE_SATURATION);

        }
        else if(v.getId() == R.id.brightnessB) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_TUNE_BRIGHTNESS);

        }

        else if(v.getId() == R.id.contrastB) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_TUNE_CONTRAST);

        }

        else if(v.getId() == R.id.rotateB){

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_PROJECTION_ROTATION);

        }

        else if(v.getId() == R.id.crop_original){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_16_9){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_3_2){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_5_4){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_7_5){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_din){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_landscape){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_portrait){
            camera.setFormat();
        }
        else if(v.getId() == R.id.crop_square){
            camera.setFormat();
        }

        else if(v.getId() == R.id.gridB){

            if(!gridOnOff){
                gridButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grid_off_white_24dp, 0, 0);
                gridOnOff = GRID_ON;

            }
            else{
                gridButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grid_on_white_24dp, 0, 0);
                gridOnOff = GRID_OFF;

            }



        }

        else if(v.getId() == R.id.classic) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_EFFECT_CLASSIC);

        }

        else if(v.getId() == R.id.bw) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_EFFECT_BW);

        }

        else if(v.getId() == R.id.retro) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_EFFECT_RETRO);

        }

        else if(v.getId() == R.id.painted) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_EFFECT_PAINTED);

        }
        //...//
        else if(v.getId() == R.id.test) {

            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out));
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in));
            viewFlipper.setDisplayedChild(STAGE_EFFECT_TEST);

        }

        else if(v.getId() == R.id.add_favorite){

            //SQLiteDatabase effectCameraFavorites = sqlLite.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            //ContentValues values = new ContentValues();
            //values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, EFFECT_BLACKWHITE);
            //values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, EFFECT_BLACKWHITE);
            //values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, EFFECT_BLACKWHITE);

            // Insert the new row, returning the primary key value of the new row
            //long newRowId;
            //newRowId = effectCameraFavorites.insert(
             //       FeedReaderContract.FeedEntry.TABLE_NAME,
            //        FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE,
             //       values);

            //values.clear();

            /*TODO: Build nice custom Alert*/
            /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Effect Name...");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText("Effect");
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //favoriteEffectName = input.getText().toString();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();*/

        }

        //Classic
        /*TODO: Optimieren dass nicht bei jedem Button Click soviel passiert*/
        else if(v.getId() == R.id.original){
            effect = EFFECT_ORIGINAL;
            renderer.effect = EFFECT_ORIGINAL;

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.blackwhite){

            effect = EFFECT_BLACKWHITE;
            renderer.effect = EFFECT_BLACKWHITE;

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.sepia){

            effect = EFFECT_SEPIA;
            renderer.effect = EFFECT_SEPIA;

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.negative){
            effect = EFFECT_NEGATIVE;
            renderer.effect = EFFECT_NEGATIVE;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.edge){
            effect = EFFECT_EDGE;
            renderer.effect = EFFECT_EDGE;

            renderer.setImageStrength(ORG_STRENGTH_EDGE);
            renderer.setImageBrightness(ORG_RED_VALUE);
            renderer.setImageContrast(ORG_BLUE_VALUE);
            renderer.setImageSaturation(ORG_GREEN_VALUE);

            strength.setProgress(Math.round(ORG_STRENGTH_EDGE * 100));
            brightness.setProgress(Math.round(ORG_RED_VALUE * 100));
            contrast.setProgress(Math.round(ORG_GREEN_VALUE * 100));
            saturation.setProgress(Math.round(ORG_BLUE_VALUE * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_edge_color_red_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_edge_color_green_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_edge_color_blue_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.red_value));
            contrastButton.setText(getResources().getString(R.string.green_value));
            saturationButton.setText(getResources().getString(R.string.blue_value));
        }
        else if(v.getId() == R.id.portrait){
            effect = EFFECT_PAINTED;
            renderer.effect = EFFECT_PAINTED;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

        //BW
        else if(v.getId() == R.id.bwlight){
            effect = EFFECT_BWLIGHT;
            renderer.effect = EFFECT_BWLIGHT;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));

        }
        else if(v.getId() == R.id.bwlight1){
            effect = EFFECT_BWLIGHT1;
            renderer.effect = EFFECT_BWLIGHT1;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));

        }
        else if(v.getId() == R.id.bwlight2){
            effect = EFFECT_BWLIGHT2;
            renderer.effect = EFFECT_BWLIGHT2;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));

        }
        else if(v.getId() == R.id.bwlight3){
            effect = EFFECT_BWLIGHT3;
            renderer.effect = EFFECT_BWLIGHT3;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));

        }
        else if(v.getId() == R.id.bwlight4){
            effect = EFFECT_BWLIGHT4;
            renderer.effect = EFFECT_BWLIGHT4;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));

        }
        else if(v.getId() == R.id.bwlight5){
            effect = EFFECT_BWLIGHT5;
            renderer.effect = EFFECT_BWLIGHT5;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

        //RETRO
        else if(v.getId() == R.id.retro0){
            effect = EFFECT_RETRO;
            renderer.effect = EFFECT_RETRO;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.retro1){
            effect = EFFECT_RETRO1;
            renderer.effect = EFFECT_RETRO1;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.retro2){
            effect = EFFECT_RETRO2;
            renderer.effect = EFFECT_RETRO2;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.retro3){
            effect = EFFECT_RETRO3;
            renderer.effect = EFFECT_RETRO3;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.retro4){
            effect = EFFECT_RETRO4;
            renderer.effect = EFFECT_RETRO4;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.retro5){
            effect = EFFECT_RETRO5;
            renderer.effect = EFFECT_RETRO5;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        //PAINTED
        else if(v.getId() == R.id.painted0){
            effect = EFFECT_PAINTED0;
            renderer.effect = EFFECT_PAINTED0;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.painted1){
            effect = EFFECT_PAINTED1;
            renderer.effect = EFFECT_PAINTED1;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.painted2){
            effect = EFFECT_PAINTED2;
            renderer.effect = EFFECT_PAINTED2;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.painted3){
            effect = EFFECT_PAINTED3;
            renderer.effect = EFFECT_PAINTED3;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.painted4){
            effect = EFFECT_PAINTED4;
            renderer.effect = EFFECT_PAINTED4;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        else if(v.getId() == R.id.painted5){
            effect = EFFECT_PAINTED5;
            renderer.effect = EFFECT_PAINTED5;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }
        //....//
        else if(v.getId() == R.id.testEffect1){
            effect = EFFECT_TEST1;
            renderer.effect = EFFECT_TEST1;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

        else if(v.getId() == R.id.testEffect2){
            effect = EFFECT_TEST2;
            renderer.effect = EFFECT_TEST2;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

        else if(v.getId() == R.id.testEffect3){
            effect = EFFECT_TEST3;
            renderer.effect = EFFECT_TEST3;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

        else if(v.getId() == R.id.testEffect4){
            effect = EFFECT_TEST4;
            renderer.effect = EFFECT_TEST4;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

        else if(v.getId() == R.id.testEffect5){
            effect = EFFECT_TEST5;
            renderer.effect = EFFECT_TEST5;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

        else if(v.getId() == R.id.testEffect6){
            effect = EFFECT_TEST6;
            renderer.effect = EFFECT_TEST6;

            renderer.setImageBrightness(ORG_BRIGHTNESS);
            renderer.setImageContrast(ORG_CONTRAST);
            renderer.setImageSaturation(ORG_SATURATION);

            strength.setProgress(Math.round(ORG_STRENGTH * 100));
            brightness.setProgress(Math.round(ORG_BRIGHTNESS * 100));
            contrast.setProgress(Math.round(ORG_CONTRAST * 100));
            saturation.setProgress(Math.round(ORG_SATURATION * 100));

            brightnessButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_brightness_low_white_24dp, 0, 0);
            contrastButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_grain_white_24dp, 0, 0);
            saturationButton.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_filter_vintage_white_24dp, 0, 0);

            brightnessButton.setText(getResources().getString(R.string.brightness_value));
            contrastButton.setText(getResources().getString(R.string.contrast_value));
            saturationButton.setText(getResources().getString(R.string.saturation_value));
        }

    }


    @Override
    public boolean onLongClick(View v) {

        if(v == nonEffectButton){
            renderer.effect = EFFECT_ORIGINAL;
            brightness_value = ORG_BRIGHTNESS;
            saturation_value = ORG_SATURATION;
            contrast_value = ORG_CONTRAST;
            vignette_value = ORG_VIGNETTE;
            strength_value = ORG_STRENGTH;
            //rotation_value = ORG_ROTATION;
            return true;
        }
        return true;
    }

    /*TODO: Volume up and down buttons as zoom if wanted*/


    @Override
    public void onBackPressed() {

        currentView = viewFlipper.getDisplayedChild();

        if (currentView == STAGE_TOP) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            MainActivity.this.finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to close?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();



        } else {

            currentView = viewFlipper.getDisplayedChild();

            if (currentView == STAGE_TUNE_STRENGTH || currentView == STAGE_TUNE_BRIGHTNESS || currentView == STAGE_TUNE_CONTRAST || currentView == STAGE_TUNE_SATURATION){

                    viewFlipper.setDisplayedChild(STAGE_TUNE);
                    currentView = STAGE_TUNE;

            } else if (currentView == STAGE_EDIT) {

                    viewFlipper.setDisplayedChild(STAGE_TOP);
                    currentView = STAGE_TOP;
                    menuButton.setImageResource(R.mipmap.ic_menu_white_24dp);

            }
            else{

                    viewFlipper.setDisplayedChild(STAGE_EDIT);
                    currentView = STAGE_EDIT;

            }


        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(seekBar.getId() == R.id.strength){

            strength_value = progress/NORM_STRENGTH;
            renderer.setImageStrength(strength_value);

        }

        else if(seekBar.getId() == R.id.contrast){

            contrast_value = progress/NORM_CONTRAST;
            renderer.setImageContrast(contrast_value);

        }
        else if(seekBar.getId() == R.id.brightness){

            brightness_value = progress/NORM_BRIGHTNESS;
            renderer.setImageBrightness(brightness_value);

        }
        else if(seekBar.getId() == R.id.saturation){

            saturation_value = progress/NORM_SATURATION;
            renderer.setImageSaturation(saturation_value);

        }
        else if(seekBar.getId() == R.id.vignette){

            vignette_value =  1.1f - progress/NORM_VIGNETTE;
            renderer.setImageVignette(vignette_value);

        }

        else if(seekBar.getId() == R.id.rotation){

            rotation_value = 1.0f - progress / NORM_ROTATION;
            renderer.setImageRotation(rotation_value);


        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        int progress = seekBar.getProgress();

        if(seekBar.getId() == R.id.contrast){

            contrast_value = progress/NORM_CONTRAST;
            renderer.setImageContrast(contrast_value);

        }
        else if(seekBar.getId() == R.id.brightness){

            brightness_value = progress/NORM_BRIGHTNESS;
            renderer.setImageBrightness(brightness_value);

        }
        else if(seekBar.getId() == R.id.saturation){

            saturation_value = progress/NORM_SATURATION;
            renderer.setImageSaturation(saturation_value);

        }
        else if(seekBar.getId() == R.id.vignette){

            vignette_value = 1.1f - progress/NORM_VIGNETTE;
            renderer.setImageVignette(vignette_value);

        }
        else if(seekBar.getId() == R.id.rotation){

            rotation_value = progress / NORM_ROTATION;
            renderer.setImageRotation(rotation_value);


        }
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        /*TODO: check for */
        /*float normal;

        Point size = getDisplayDimensions();
        height = size.y;
        width = size.x;
        Log.i("JAP", "jojojojojojojo");
        orientation = ORIENTATION_0;
        if(orientation == ORIENTATION_0 || orientation == ORIENTATION_180){

            normal = (e2.getY() - e1.getY()) / height;

            if(Math.abs(normal) > 0.15f) {

                if (normal < 0) {
                    if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_4) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_0;
                        camera.setBrightness(BRIGHTNESS_LEVEL_0);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_4;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_4);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);

                    }
                } else if (normal >= 0) {
                    if (brightness_mode == BRIGHTNESS_LEVEL_DARK_4) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_0;
                        camera.setBrightness(BRIGHTNESS_LEVEL_0);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                        e1.setLocation(e1.getX(), e2.getY());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_4;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_4);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);
                    }
                }
            }
            return true;
        }
        else if(orientation == ORIENTATION_90 || orientation == ORIENTATION_270){
            normal = (e2.getX() - e1.getX()) / width;

            if(Math.abs(normal) > 0.15f) {

                if (normal < 0) {

                    if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_4) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_0;
                        camera.setBrightness(BRIGHTNESS_LEVEL_0);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_4;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_4);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);

                    }
                } else if (normal >= 0) {
                    if (brightness_mode == BRIGHTNESS_LEVEL_DARK_4) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_3) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_1_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_2) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_DARK_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_DARK_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_2_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_DARK_1) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_0;
                        camera.setBrightness(BRIGHTNESS_LEVEL_0);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_3_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_0) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_1;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_1);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_4_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_1) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_2;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_2);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_5_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_2) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_3;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_3);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_6_white_24dp);

                    } else if (brightness_mode == BRIGHTNESS_LEVEL_LIGHT_3) {
                        e1.setLocation(e1.getY(), e2.getX());
                        brightness_mode = BRIGHTNESS_LEVEL_LIGHT_4;
                        camera.setBrightness(BRIGHTNESS_LEVEL_LIGHT_4);
                        aeExposureButton.setImageResource(R.mipmap.ic_brightness_7_white_24dp);

                    }
                }
            }
            return true;
        }*/

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        camera = renderer.getCamera();
        /*TODO: longPress is no longPress rather a shortPress/click*/
        if (e.getEventTime() > 3000000) {
            if (!ae_mode) {
                autoExposureLockButton.setImageResource(R.mipmap.ic_lock_outline_white_24dp);
                ae_mode = AUTO_EXPOSURE_LOCK;
                camera.setAutoExposureLock(AUTO_EXPOSURE_LOCK);
            } else{
                autoExposureLockButton.setImageResource(R.mipmap.ic_lock_open_white_24dp);
                ae_mode = AUTO_EXPOSURE_OPEN;
                camera.setAutoExposureLock(AUTO_EXPOSURE_OPEN);
            }
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 4){
            camera.closeCamera();
            this.finish();
        }
        else {
            camera.closeCamera();
            this.finish();
            startActivity(intent);
        }
    }


    public Point getDisplayDimensions(){

        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);

        return size;

    }


}
