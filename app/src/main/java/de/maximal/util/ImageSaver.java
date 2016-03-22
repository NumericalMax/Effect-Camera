package de.maximal.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Max Kapsecker on 11.12.2015.
 */

public class ImageSaver {

    private final String TAG = "ImageSaver";

    public ImageSaver(byte[] bytes, File file) {

        save(bytes, file);
    }

    public void save(byte[] bytes, File file) {

        FileOutputStream output = null;

        try {
            output = new FileOutputStream(file);
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}