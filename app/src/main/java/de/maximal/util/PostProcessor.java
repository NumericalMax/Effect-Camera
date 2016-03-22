package de.maximal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import de.maximal.effectcamera.ScriptC_grey;

/************************************************************
 *                                                          *
 * Post Processing                                           *
 *                                                          *
 * Copyright (C) 2015 Maximilian Kapsecker                  *
 *                                                          *
 * @author Maximilian Kapsecker                             *
 * @version 1.0                                             *
 * Environment: Android Studio                              *
 * Created on 19.12.2015                                    *
 * Last change: 23.12.2015                                  *
 *                                                          *
 ***********************************************************/

public class PostProcessor extends AsyncTask<Image, Void, byte[]>{


    private Context context;
    private int effect;
    private int height;
    private int width;

    private Bitmap bitmap;
    private Bitmap bitmapIn;
    private Bitmap bitmapOut;

    private Allocation inAllocation;
    private Allocation outAllocation;

    private RenderScript renderScript;


    public PostProcessor(Context context, ImageReader reader, int effect) {

        this.context = context;
        this.effect = effect;

        Image image = reader.acquireLatestImage();
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();

        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        this.height = image.getHeight();
        this.width = image.getWidth();

        image.close();
    }

    @Override
    public byte[] doInBackground(Image... params) {

        byte[] test = null;
        return test;

    }
}
