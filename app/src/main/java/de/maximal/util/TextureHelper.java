package de.maximal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glTexParameteri;

/**
 * Created by Max Kapsecker on 22.05.2015.
 */
public class TextureHelper {

    private static final String TAG = "TextureHelper";

    public static int loadTexture(Context context, int resourceId){
        final int[] textureObjectIds = new int[1];
        glGenTextures(1,textureObjectIds,0);

        if(textureObjectIds[0] == 0){

            if(LoggerConfig.ON){

                Log.w(TAG, "Could not generate a new OpenGL texture object.");

            }

            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        if(bitmap == null){
            if(LoggerConfig.ON){

                Log.w(TAG, "Resource ID" + resourceId + "could not be decoded.");

            }

            glDeleteTextures(1, textureObjectIds, 0);
            return 0;

        }

        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0 ,bitmap, 0);
        bitmap.recycle();

        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);

        return textureObjectIds[0];
    }


    public static int loadShader ( String vss, String fss ) {
        int vshader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vshader, vss);
        glCompileShader(vshader);
        int[] compiled = new int[1];
        glGetShaderiv(vshader, GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e("Shader", "Could not compile vshader");
            Log.v("Shader", "Could not compile vshader:"+glGetShaderInfoLog(vshader));
            glDeleteShader(vshader);
            vshader = 0;
        }

        int fshader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fshader, fss);
        glCompileShader(fshader);
        glGetShaderiv(fshader, GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e("Shader", "Could not compile fshader");
            Log.v("Shader", "Could not compile fshader:" + glGetShaderInfoLog(fshader));
            glDeleteShader(fshader);
            fshader = 0;
        }

        int program = glCreateProgram();
        glAttachShader(program, vshader);
        glAttachShader(program, fshader);
        glLinkProgram(program);

        return program;
    }


}
