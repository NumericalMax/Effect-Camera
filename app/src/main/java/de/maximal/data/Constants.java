package de.maximal.data;

/**
 * Created by Max Kapsecker on 22.05.2015.
 */
public class Constants {



    public static final int BYTES_PER_FLOAT = 4;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final int FLOAT_SIZE_BYTES = 4;

    public static final int OPENGLES_VERSION2 = 020000;

    public static final float[] PROJECTION_MATRIX_BACK = {0, -1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    public static final float[] PROJECTION_MATRIX_FRONT = {0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};

    public static final boolean GRID_ON = true;
    public static final boolean GRID_OFF = false;

    public static final boolean AUTO_EXPOSURE_LOCK = true;
    public static final boolean AUTO_EXPOSURE_OPEN = false;

    public static final int TOP = 100;
    public static final int LEFT = 100;
    public static final int RIGHT = 100;
    public static final int BOTTOM = 100;

    public static final int ROTATION0 = 0;
    public static final int ROTATION90 = 90;
    public static final int ROTATION180 = 180;
    public static final int ROTATION270 = 270;

    public static final int PROGRESS100 = 100;
    public static final int PROGRESS50 = 50;
    public static final int PROGRESS0 = 0;

    public static final int EFFECT_ORIGINAL = 0;
    public static final int EFFECT_BLACKWHITE = 1;
    public static final int EFFECT_SEPIA = 2;
    public static final int EFFECT_NEGATIVE = 3;
    public static final int EFFECT_EDGE = 4;
    public static final int EFFECT_PAINTED = 5;

    public static final int EFFECT_BWLIGHT = 6;
    public static final int EFFECT_BWLIGHT1 = 7;
    public static final int EFFECT_BWLIGHT2 = 8;
    public static final int EFFECT_BWLIGHT3 = 9;
    public static final int EFFECT_BWLIGHT4 = 10;
    public static final int EFFECT_BWLIGHT5 = 11;

    public static final int EFFECT_RETRO = 12;
    public static final int EFFECT_RETRO1 = 13;
    public static final int EFFECT_RETRO2 = 14;
    public static final int EFFECT_RETRO3 = 15;
    public static final int EFFECT_RETRO4 = 16;
    public static final int EFFECT_RETRO5 = 17;

    public static final int EFFECT_PAINTED0 = 18;
    public static final int EFFECT_PAINTED1 = 19;
    public static final int EFFECT_PAINTED2 = 20;
    public static final int EFFECT_PAINTED3 = 21;
    public static final int EFFECT_PAINTED4 = 22;
    public static final int EFFECT_PAINTED5 = 23;

    public static final int EFFECT_TEST1 = 1001;
    public static final int EFFECT_TEST2 = 1002;
    public static final int EFFECT_TEST3 = 1003;
    public static final int EFFECT_TEST4 = 1004;
    public static final int EFFECT_TEST5 = 1005;
    public static final int EFFECT_TEST6 = 1006;

    public static final int ORIENTATION_0 = 1;
    public static final int ORIENTATION_90 = 2;
    public static final int ORIENTATION_180 = 3;
    public static final int ORIENTATION_270 = 4;

    public static final int CAMERA_BACK = 0;
    public static final int CAMERA_FRONT = 1;

    public static final int STAGE_TOP = 0;
    public static final int STAGE_EDIT = 1;
    public static final int STAGE_FAVORITE = 2;
    public static final int STAGE_EFFECT = 3;
    public static final int STAGE_TUNE = 4;
    public static final int STAGE_COLLAGE = 5;
    public static final int STAGE_CROP = 6;
    public static final int STAGE_VIGNETTE = 7;
    public static final int STAGE_PROJECTION = 8;

    public static final int STAGE_EFFECT_CLASSIC = 9;
    public static final int STAGE_EFFECT_BW = 10;
    public static final int STAGE_EFFECT_RETRO = 11;
    public static final int STAGE_EFFECT_PAINTED = 12;

    public static final int STAGE_EFFECT_1 = 13;
    public static final int STAGE_EFFECT_2 = 14;
    public static final int STAGE_EFFECT_3 = 15;

    public static final int STAGE_EFFECT_TEST = 16;

    public static final int STAGE_TUNE_STRENGTH = 17;
    public static final int STAGE_TUNE_BRIGHTNESS = 18;
    public static final int STAGE_TUNE_CONTRAST = 19;
    public static final int STAGE_TUNE_SATURATION = 20;
    public static final int STAGE_PROJECTION_ROTATION = 21;

    public static final int TOUCH_DURATION_LONG = 2000;

    public static final int BRIGHTNESS_LEVEL_LIGHT_4 = 4;
    public static final int BRIGHTNESS_LEVEL_LIGHT_3 = 3;
    public static final int BRIGHTNESS_LEVEL_LIGHT_2 = 2;
    public static final int BRIGHTNESS_LEVEL_LIGHT_1 = 1;
    public static final int BRIGHTNESS_LEVEL_0 = 0;
    public static final int BRIGHTNESS_LEVEL_DARK_1 = -1;
    public static final int BRIGHTNESS_LEVEL_DARK_2 = -2;
    public static final int BRIGHTNESS_LEVEL_DARK_3 = -3;
    public static final int BRIGHTNESS_LEVEL_DARK_4 = -4;

    public static final int SELF_TIMER_OFF = 0;
    public static final int SELF_TIMER_3 = 1;
    public static final int SELF_TIMER_10 = 2;

    public static final float ORG_STRENGTH = 1.0f;
    public static final float ORG_BRIGHTNESS = 0.5f;
    public static final float ORG_CONTRAST = 0.5f;
    public static final float ORG_SATURATION = 0.5f;
    public static final float ORG_VIGNETTE = 1.5f;
    public static final float ORG_ROTATION = 1.0f;

    public static final float NORM_STRENGTH = 100.0f;
    public static final float NORM_BRIGHTNESS = 50.0f;
    public static final float NORM_CONTRAST = 50.0f;
    public static final float NORM_SATURATION = 50.0f;
    public static final float NORM_VIGNETTE = 150.0f;
    public static final float NORM_ROTATION = 100.0f;

    public static final float ORG_STRENGTH_EDGE = 0.3f;
    public static final float ORG_RED_VALUE = 1.0f;
    public static final float ORG_BLUE_VALUE = 1.0f;
    public static final float ORG_GREEN_VALUE = 1.0f;

    public static final String FLASH_MODE_AUTO = "auto";
    public static final String FLASH_MODE_ON = "on";
    public static final String FLASH_MODE_OFF = "off";

    public static final boolean HDR_MODE_ON = true;
    public static final boolean HDR_MODE_OFF = false;

    public static final boolean CENTER_FOCUS_ON = true;
    public static final boolean CENTER_FOCUS_OFF = false;

    public static final String WHITE_BALANCE_AUTO = "auto";
    public static final String WHITE_BALANCE_CLOUDY_DAYLIGHT = "cloudy-daylight";
    public static final String WHITE_BALANCE_DAYLIGHT = "daylight";
    public static final String WHITE_BALANCE_INCANDESCENT = "incandescent";
    /*TODO: checken ob das wirklich dem symbol entspricht das ich verwende. Stichwort: iridescent*/
    public static final String WHITE_BALANCE_WARM_FLUORESCENT = "warm-fluorescent";

    public static final String FOCUS_MODE_AUTO = "auto";
    public static final String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";
    public static final String FOCUS_MODE_EDOF = "edof";
    public static final String FOCUS_MODE_FIXED = "fixed";
    public static final String FOCUS_MODE_INFINITY = "infinity";
    public static final String FOCUS_MODE_MACRO = "maro";


    public static final String SCENE_MODE_AUTO = "action";
    public static final String SCENE_MODE_ACTION = "auto";
    public static final String SCENE_MODE_BARCODE = "barcode";
    public static final String SCENE_MODE_BEACH = "beach";
    public static final String SCENE_MODE_CANDLELIGHT = "candlelight";
    public static final String SCENE_MODE_FIREWORKS = "fireworks";
    public static final String SCENE_MODE_HDR = "hdr";
    public static final String SCENE_MODE_LANDSCAPE = "landscape";
    public static final String SCENE_MODE_NIGHT = "night";
    public static final String SCENE_MODE_NIGHT_PORTRAIT = "night-portrait";
    public static final String SCENE_MODE_PARTY = "party";
    public static final String SCENE_MODE_PORTRAIT = "portrait";
    public static final String SCENE_MODE_SNOW = "snow";
    public static final String SCENE_MODE_SPORTS = "sports";
    public static final String SCENE_MODE_STEADYPHOTO = "steadyphoto";
    public static final String SCENE_MODE_SUNSET = "sunset";
    public static final String SCENE_MODE_THEATRE = "theatre";


    public static final int OPENGL_ES_VERSION2 = 2;

    public static final int STATE_PREVIEW = 0;
    public static final int STATE_WAITING_LOCK = 1;
    public static final int STATE_WAITING_PRECAPTURE = 2;
    public static final int STATE_WAITING_NON_PRECAPTURE = 3;
    public static final int STATE_PICTURE_TAKEN = 4;



    public static final int MAX_PREVIEW_WIDTH = 1920;
    public static final int MAX_PREVIEW_HEIGHT = 1080;


}
