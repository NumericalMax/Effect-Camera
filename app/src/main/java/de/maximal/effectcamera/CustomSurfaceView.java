package de.maximal.effectcamera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import static de.maximal.data.Constants.OPENGL_ES_VERSION2;

/************************************************************
 *                                                          *
 * GLSurfaceView for displaying the camera input            *
 *                                                          *
 * Copyright (C) 2015 Maximilian Kapsecker                  *
 *                                                          *
 * @author Maximilian Kapsecker                             *
 * @version 1.0                                             *
 * Environment: Android Studio                              *
 * Created on 19.05.2015                                    *
 * Last change: 21.12.2015                                  *
 *                                                          *
 ***********************************************************/

public class CustomSurfaceView extends GLSurfaceView{

    private static final String TAG = "CustomSurfaceView";
    private RendererView render;
    private int ratioWidth = 0;
    private int ratioHeight = 0;

    public CustomSurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        render = new RendererView(this, context);
        setEGLContextClientVersion(OPENGL_ES_VERSION2);
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public CustomSurfaceView(Context context){
        super(context);
        render = new RendererView(this, context);
        setEGLContextClientVersion(OPENGL_ES_VERSION2);
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (0 == ratioWidth || 0 == ratioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * ratioWidth / ratioHeight) {
                setMeasuredDimension(width, width * ratioHeight / ratioWidth);
            } else {
                setMeasuredDimension(height * ratioWidth / ratioHeight, height);
            }
        }
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        ratioWidth = width;
        ratioHeight = height;

    }

    public RendererView getRenderer(){
        return render;
    }
}