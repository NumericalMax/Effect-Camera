package de.maximal.effectcamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.maximal.objects.Table;
import de.maximal.programs.classic.TextureShaderProgramBlackWhite;
import de.maximal.programs.bw.TextureShaderProgramBwLight;
import de.maximal.programs.bw.TextureShaderProgramBwMedium;
import de.maximal.programs.bw.TextureShaderProgramBwDark;
import de.maximal.programs.bw.TextureShaderProgramBwLight3;
import de.maximal.programs.bw.TextureShaderProgramBwLinear;
import de.maximal.programs.bw.TextureShaderProgramBwEmboss;
import de.maximal.programs.classic.TextureShaderProgramEdge;
import de.maximal.programs.TextureShaderProgramEffect6;
import de.maximal.programs.TextureShaderProgramEffect7;
import de.maximal.programs.classic.TextureShaderProgramNegative;
import de.maximal.programs.classic.TextureShaderProgramOriginal;
import de.maximal.programs.classic.TextureShaderProgramPortrait;
import de.maximal.programs.classic.TextureShaderProgramSepia;
import de.maximal.programs.painted.TextureShaderProgramPainted0;
import de.maximal.programs.painted.TextureShaderProgramPainted1;
import de.maximal.programs.painted.TextureShaderProgramPainted2;
import de.maximal.programs.painted.TextureShaderProgramPainted3;
import de.maximal.programs.painted.TextureShaderProgramPainted4;
import de.maximal.programs.painted.TextureShaderProgramPainted5;
import de.maximal.programs.retro.TextureShaderProgramRetro0;
import de.maximal.programs.retro.TextureShaderProgramRetro1;
import de.maximal.programs.retro.TextureShaderProgramRetro2;
import de.maximal.programs.retro.TextureShaderProgramRetro3;
import de.maximal.programs.retro.TextureShaderProgramRetro4;
import de.maximal.programs.retro.TextureShaderProgramRetro5;
import de.maximal.programs.test.TextureShaderProgramTest1;
import de.maximal.programs.test.TextureShaderProgramTest2;
import de.maximal.programs.test.TextureShaderProgramTest3;
import de.maximal.programs.test.TextureShaderProgramTest4;
import de.maximal.programs.test.TextureShaderProgramTest5;
import de.maximal.programs.test.TextureShaderProgramTest6;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glViewport;
import static de.maximal.data.Constants.EFFECT_BLACKWHITE;
import static de.maximal.data.Constants.EFFECT_BWLIGHT;
import static de.maximal.data.Constants.EFFECT_BWLIGHT1;
import static de.maximal.data.Constants.EFFECT_BWLIGHT2;
import static de.maximal.data.Constants.EFFECT_BWLIGHT3;
import static de.maximal.data.Constants.EFFECT_BWLIGHT4;
import static de.maximal.data.Constants.EFFECT_BWLIGHT5;
import static de.maximal.data.Constants.EFFECT_EDGE;
import static de.maximal.data.Constants.EFFECT_NEGATIVE;
import static de.maximal.data.Constants.EFFECT_ORIGINAL;
import static de.maximal.data.Constants.EFFECT_PAINTED;
import static de.maximal.data.Constants.EFFECT_RETRO;
import static de.maximal.data.Constants.EFFECT_RETRO1;
import static de.maximal.data.Constants.EFFECT_SEPIA;
import static de.maximal.data.Constants.EFFECT_TEST1;
import static de.maximal.data.Constants.EFFECT_TEST2;
import static de.maximal.data.Constants.EFFECT_TEST3;
import static de.maximal.data.Constants.EFFECT_TEST4;
import static de.maximal.data.Constants.EFFECT_TEST5;
import static de.maximal.data.Constants.EFFECT_TEST6;
import static de.maximal.data.Constants.ORG_BRIGHTNESS;
import static de.maximal.data.Constants.ORG_CONTRAST;
import static de.maximal.data.Constants.ORG_ROTATION;
import static de.maximal.data.Constants.ORG_SATURATION;
import static de.maximal.data.Constants.ORG_STRENGTH;
import static de.maximal.data.Constants.ORG_VIGNETTE;
import static de.maximal.data.Constants.PROJECTION_MATRIX_BACK;
import static de.maximal.data.Constants.*;

/**
 *
 * GL Renderer, which renders the GL SurfaceView
 *
 * @author Maximilian Kapsecker
 * @version 1.0
 * Environment: Android Studio
 * Created on 19.05.2015
 * Last change: 21.12.2015
 *
 */

public class RendererView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    //private static final String TAG = "RendererView";

    private final Context context;

    private boolean updateSurfaceTexture;

    private int[] hTex;
    private SurfaceTexture sTexture;
    private Table table;

    public int cam;
    public int effect;

    public float strength;
    public float contrast;
    public float brightness;
    public float saturation;
    public float vignette;

    private float rotation;
    private int grid;

    private TextureShaderProgramOriginal textureProgramOriginal;
    private TextureShaderProgramBlackWhite textureProgramBlackWhite;
    private TextureShaderProgramSepia textureProgramSepia;
    private TextureShaderProgramNegative textureProgramNegative;
    private TextureShaderProgramEdge textureProgramEdge;
    private TextureShaderProgramPortrait textureProgramPortrait;
    private TextureShaderProgramEffect6 textureShaderProgramEffect6;
    private TextureShaderProgramEffect7 textureShaderProgramEffect7;

    private TextureShaderProgramBwLight textureProgramBwLight;
    private TextureShaderProgramBwMedium textureProgramBwLight1;
    private TextureShaderProgramBwDark textureProgramBwLight2;
    private TextureShaderProgramBwLight3 textureProgramBwLight3;
    private TextureShaderProgramBwLinear textureProgramBwLight4;
    private TextureShaderProgramBwEmboss textureProgramBwLight5;

    private TextureShaderProgramRetro0 textureProgramRetro0;
    private TextureShaderProgramRetro1 textureProgramRetro1;
    private TextureShaderProgramRetro2 textureProgramRetro2;
    private TextureShaderProgramRetro3 textureProgramRetro3;
    private TextureShaderProgramRetro4 textureProgramRetro4;
    private TextureShaderProgramRetro5 textureProgramRetro5;

    private TextureShaderProgramPainted0 textureProgramPainted0;
    private TextureShaderProgramPainted1 textureProgramPainted1;
    private TextureShaderProgramPainted2 textureProgramPainted2;
    private TextureShaderProgramPainted3 textureProgramPainted3;
    private TextureShaderProgramPainted4 textureProgramPainted4;
    private TextureShaderProgramPainted5 textureProgramPainted5;
    //...//
    private TextureShaderProgramTest1 textureProgramTest1;
    private TextureShaderProgramTest2 textureProgramTest2;
    private TextureShaderProgramTest3 textureProgramTest3;
    private TextureShaderProgramTest4 textureProgramTest4;
    private TextureShaderProgramTest5 textureProgramTest5;
    private TextureShaderProgramTest6 textureProgramTest6;

    private CustomSurfaceView surfaceView;
    public float[] projectionMatrix;

    private Camera camera;

    Point dimensions;

    public RendererView(CustomSurfaceView view, Context mContext) {

        context = mContext;
        surfaceView = view;

        updateSurfaceTexture = false;

        projectionMatrix = PROJECTION_MATRIX_BACK;
        effect = EFFECT_ORIGINAL;

        strength = ORG_STRENGTH;
        contrast = ORG_CONTRAST;
        brightness = ORG_BRIGHTNESS;
        saturation = ORG_SATURATION;
        vignette = ORG_VIGNETTE;
        rotation = ORG_ROTATION;
        grid = 0;

        camera = new Camera(context, surfaceView);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        glClearColor(63.0f / 255.0f, 81.0f / 255.0f, 181.0f / 255.0f, 1.0f);

        initTex();

        sTexture = new SurfaceTexture(hTex[0]);
        camera.openCamera(surfaceView.getWidth(), surfaceView.getHeight(), sTexture, dimensions);

        sTexture.setDefaultBufferSize(camera.previewSize.getWidth(), camera.previewSize.getHeight());
        sTexture.setOnFrameAvailableListener(this);
        table = new Table();

        textureProgramOriginal = new TextureShaderProgramOriginal(context);
        textureProgramBlackWhite = new TextureShaderProgramBlackWhite(context);
        textureProgramSepia = new TextureShaderProgramSepia(context);
        textureProgramNegative = new TextureShaderProgramNegative(context);
        textureProgramEdge = new TextureShaderProgramEdge(context, this);
        textureProgramPortrait = new TextureShaderProgramPortrait(context);

        textureProgramBwLight = new TextureShaderProgramBwLight(context);
        textureProgramBwLight1 = new TextureShaderProgramBwMedium(context);
        textureProgramBwLight2 = new TextureShaderProgramBwDark(context);
        textureProgramBwLight3 = new TextureShaderProgramBwLight3(context);
        textureProgramBwLight4 = new TextureShaderProgramBwLinear(context);
        textureProgramBwLight5 = new TextureShaderProgramBwEmboss(context);

        textureProgramRetro0 = new TextureShaderProgramRetro0(context);
        textureProgramRetro1 = new TextureShaderProgramRetro1(context);
        textureProgramRetro2 = new TextureShaderProgramRetro2(context);
        textureProgramRetro3 = new TextureShaderProgramRetro3(context);
        textureProgramRetro4 = new TextureShaderProgramRetro4(context);
        textureProgramRetro5 = new TextureShaderProgramRetro5(context);

        textureProgramPainted0 = new TextureShaderProgramPainted0(context);
        textureProgramPainted1 = new TextureShaderProgramPainted1(context);
        textureProgramPainted2 = new TextureShaderProgramPainted2(context);
        textureProgramPainted3 = new TextureShaderProgramPainted3(context);
        textureProgramPainted4 = new TextureShaderProgramPainted4(context);
        textureProgramPainted5 = new TextureShaderProgramPainted5(context);
        //...//
        textureProgramTest1 = new TextureShaderProgramTest1(context);
        textureProgramTest2 = new TextureShaderProgramTest2(context);
        textureProgramTest3 = new TextureShaderProgramTest3(context);
        textureProgramTest4 = new TextureShaderProgramTest4(context);
        textureProgramTest5 = new TextureShaderProgramTest5(context);
        textureProgramTest6 = new TextureShaderProgramTest6(context);

        textureShaderProgramEffect6 = new TextureShaderProgramEffect6(context);
        textureShaderProgramEffect7 = new TextureShaderProgramEffect7(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (this) {
            if (updateSurfaceTexture) {
                sTexture.updateTexImage();
                updateSurfaceTexture = false;
            }
            useProgram(effect);
        }
    }

    @Override
    public synchronized void onFrameAvailable(SurfaceTexture st) {
        updateSurfaceTexture = true;
        surfaceView.requestRender();
    }

    private void initTex() {

        hTex = new int[16];
        GLES20.glGenTextures(16, hTex, 0);

        loadBitmapToTexture(R.drawable.layer1, 1);
        loadBitmapToTexture(R.drawable.layer2, 2);
        loadBitmapToTexture(R.drawable.layer3, 3);
        loadBitmapToTexture(R.drawable.layer4, 4);
        loadBitmapToTexture(R.drawable.layer5, 5);
        loadBitmapToTexture(R.drawable.layer6, 6);
        loadBitmapToTexture(R.drawable.layer7, 7);
        loadBitmapToTexture(R.drawable.layer20, 8);
        loadBitmapToTexture(R.drawable.layer9, 9);
        loadBitmapToTexture(R.drawable.layer10, 10);
        loadBitmapToTexture(R.drawable.layer11, 11);
        loadBitmapToTexture(R.drawable.layer12, 12);
        loadBitmapToTexture(R.drawable.layer13, 13);
        loadBitmapToTexture(R.drawable.layer14, 14);
        loadBitmapToTexture(R.drawable.layer15, 15);

    }

    private void loadBitmapToTexture(int id, int i){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);

        GLES20.glBindTexture(GL_TEXTURE_2D, hTex[i]);

        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();
    }

    /*TODO: Use this function in closing camera*/
    public void deleteTex() {
        glDeleteTextures(16, hTex, 0);
    }

    public void changeProjection(float rotation) {
        double alpha;
        alpha = rotation * 2.0 * Math.PI + Math.PI / 2.0;

        float[] projectionMatrix1 = {
                (float) Math.cos(alpha), -(float) Math.sin(alpha), 0, 0,
                (float) Math.sin(alpha), (float) Math.cos(alpha), 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1};

        projectionMatrix = projectionMatrix1;
    }

    //OpenGl Programs
    private void useProgram(int effect) {
        glClear(GL_COLOR_BUFFER_BIT);
        switch (effect) {
            case EFFECT_ORIGINAL:
                textureProgramOriginal.useProgram();
                textureProgramOriginal.setUniforms(projectionMatrix, hTex, contrast, brightness, saturation, vignette, grid);
                table.bindDataOriginal(textureProgramOriginal);
                break;
            case EFFECT_BLACKWHITE:
                textureProgramBlackWhite.useProgram();
                textureProgramBlackWhite.setUniforms(projectionMatrix, hTex, contrast, brightness, saturation, vignette);
                table.bindDataBlackWhite(textureProgramBlackWhite);
                break;
            case EFFECT_SEPIA:
                textureProgramSepia.useProgram();
                textureProgramSepia.setUniforms(projectionMatrix, hTex, contrast, brightness, saturation, vignette);
                table.bindDataSepia(textureProgramSepia);
                break;
            case EFFECT_NEGATIVE:
                textureProgramNegative.useProgram();
                textureProgramNegative.setUniforms(projectionMatrix, hTex, contrast, brightness, saturation, vignette);
                table.bindDataNegative(textureProgramNegative);
                break;
            case EFFECT_EDGE:
                textureProgramEdge.useProgram();
                textureProgramEdge.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette);
                table.bindDataEdge(textureProgramEdge);
                break;
            case EFFECT_PAINTED:
                textureProgramPortrait.useProgram();
                textureProgramPortrait.setUniforms(projectionMatrix, hTex, contrast, brightness, saturation, vignette);
                table.bindDataPortrait(textureProgramPortrait);
                break;

            case EFFECT_BWLIGHT:
                textureProgramBwLight.useProgram();
                textureProgramBwLight.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette);
                table.bindDataBwLight(textureProgramBwLight);
                break;
            case EFFECT_BWLIGHT1:
                textureProgramBwLight1.useProgram();
                textureProgramBwLight1.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette);
                table.bindDataBwLight1(textureProgramBwLight1);
                break;
            case EFFECT_BWLIGHT2:
                textureProgramBwLight2.useProgram();
                textureProgramBwLight2.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette);
                table.bindDataBwLight2(textureProgramBwLight2);
                break;

            case EFFECT_BWLIGHT3:
                textureProgramBwLight3.useProgram();
                textureProgramBwLight3.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette);
                table.bindDataBwLight3(textureProgramBwLight3);
                break;

            case EFFECT_BWLIGHT4:
                textureProgramBwLight4.useProgram();
                textureProgramBwLight4.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette);
                table.bindDataBwLight4(textureProgramBwLight4);
                break;

            case EFFECT_BWLIGHT5:
                textureProgramBwLight5.useProgram();
                textureProgramBwLight5.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette);
                table.bindDataBwLight5(textureProgramBwLight5);
                break;

            case EFFECT_RETRO:
                textureProgramRetro0.useProgram();
                textureProgramRetro0.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataRetro0(textureProgramRetro0);
                break;
            case EFFECT_RETRO1:
                textureProgramRetro1.useProgram();
                textureProgramRetro1.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataRetro1(textureProgramRetro1);
                break;
            case EFFECT_RETRO2:
                textureProgramRetro2.useProgram();
                textureProgramRetro2.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataRetro2(textureProgramRetro2);
                break;

            case EFFECT_RETRO3:
                textureProgramRetro3.useProgram();
                textureProgramRetro3.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataRetro3(textureProgramRetro3);
                break;

            case EFFECT_RETRO4:
                textureProgramRetro4.useProgram();
                textureProgramRetro4.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataRetro4(textureProgramRetro4);
                break;

            case EFFECT_RETRO5:
                textureProgramRetro5.useProgram();
                textureProgramRetro5.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataRetro5(textureProgramRetro5);
                break;

            case EFFECT_PAINTED0:
                textureProgramPainted0.useProgram();
                textureProgramPainted0.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataPainted0(textureProgramPainted0);
                break;
            case EFFECT_PAINTED1:
                textureProgramPainted1.useProgram();
                textureProgramPainted1.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataPainted1(textureProgramPainted1);
                break;
            case EFFECT_PAINTED2:
                textureProgramPainted2.useProgram();
                textureProgramPainted2.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataPainted2(textureProgramPainted2);
                break;

            case EFFECT_PAINTED3:
                textureProgramPainted3.useProgram();
                textureProgramPainted3.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataPainted3(textureProgramPainted3);
                break;

            case EFFECT_PAINTED4:
                textureProgramPainted4.useProgram();
                textureProgramPainted4.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataPainted4(textureProgramPainted4);
                break;

            case EFFECT_PAINTED5:
                textureProgramPainted5.useProgram();
                textureProgramPainted5.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataPainted5(textureProgramPainted5);

            //...//
            case EFFECT_TEST1:
                textureProgramTest1.useProgram();
                textureProgramTest1.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataTest1(textureProgramTest1);
                break;

            case EFFECT_TEST2:
                textureProgramTest2.useProgram();
                textureProgramTest2.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataTest2(textureProgramTest2);
                break;

            case EFFECT_TEST3:
                textureProgramTest3.useProgram();
                textureProgramTest3.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataTest3(textureProgramTest3);
                break;

            case EFFECT_TEST4:
                textureProgramTest4.useProgram();
                textureProgramTest4.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataTest4(textureProgramTest4);
                break;

            case EFFECT_TEST5:
                textureProgramTest5.useProgram();
                textureProgramTest5.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataTest5(textureProgramTest5);
                break;

            case EFFECT_TEST6:
                textureProgramTest6.useProgram();
                textureProgramTest6.setUniforms(projectionMatrix, hTex, strength, contrast, brightness, saturation, vignette, grid);
                table.bindDataTest6(textureProgramTest6);
                break;
        }
        table.draw();
    }

    //GETTER & SETTER
    public void setDisplayDimensions(Point dimensions){
        this.dimensions = dimensions;
    }

    public void setImageContrast(float contrast){
        this.contrast = contrast;
    }
    public void setImageBrightness(float brightness){
        this.brightness = brightness;
    }
    public void setImageSaturation(float saturation){
        this.saturation = saturation;
    }
    public void setImageVignette(float vignette){
        this.vignette = vignette;
    }
    public void setImageRotation(float rotation){
        this.changeProjection(rotation);
    }
    public void setImageStrength(float strength) {
        this.strength = strength;
    }

    public Camera getCamera(){
        return this.camera;
    }
    public SurfaceTexture getSurfaceTexture(){
        return this.sTexture;
    }
}