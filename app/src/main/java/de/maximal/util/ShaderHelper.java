package de.maximal.util;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * Created by Max Kapsecker on 19.05.2015.
 */
public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode){

        return compileShader(GL_VERTEX_SHADER, shaderCode);

    }

    public static int compileFragmentShader(String shaderCode){

        return compileShader(GL_FRAGMENT_SHADER, shaderCode);

    }

    private static int compileShader(int type, String shaderCode){

        final int shaderObjectId = glCreateShader(type);
        if(shaderObjectId == 0){
            if(LoggerConfig.ON){

                Log.w(TAG, "Could not create new Shader");

            }

            return 0;

        }

        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if(LoggerConfig.ON){

            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                + glGetShaderInfoLog(shaderObjectId));

        }

        if(compileStatus[0] == 0){

            glDeleteShader(shaderObjectId);

            if(LoggerConfig.ON){

                Log.w(TAG, "Compilation of Shader failed");
            }

            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId){

        final int programObjectId = glCreateProgram();

        if(programObjectId == 0){
            if(LoggerConfig.ON){

                Log.w(TAG, "Could not create new program");

            }
            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if(LoggerConfig.ON){

            Log.v(TAG, "Results of Linking program: \n"
                + glGetProgramInfoLog(programObjectId));

        }
        if(linkStatus[0] == 0){

            glDeleteProgram(programObjectId);
            if(LoggerConfig.ON){
                Log.w(TAG, "Linking of program failed.");
            }
            return 0;
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId){
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);

        Log.v(TAG, "Results of validating program: " + validateStatus[0]
            + "\nLog:" +glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;

    }


    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource){

        int program;

        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        program = linkProgram(vertexShader, fragmentShader);

        if(LoggerConfig.ON){

            validateProgram(program);

        }

        return program;

    }


}
