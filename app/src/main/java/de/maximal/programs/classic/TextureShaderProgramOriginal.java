package de.maximal.programs.classic;

import android.content.Context;
import android.opengl.GLES11Ext;

import de.maximal.effectcamera.R;
import de.maximal.programs.ShaderProgram;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 *
 * Shader Program for the Effect Original.
 *
 * @author Maximilian Kapsecker
 * @version 1.0
 * Environment: Android Studio
 * Created on 22.05.2015
 * Last change: 10.10.2015
 * @see de.maximal.programs.ShaderProgram
 *
 */

public class TextureShaderProgramOriginal extends ShaderProgram {

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    /*TODO: uBrightnessLocation respell it to uBrightnessLocation*/

    private final int uContrastLocation;
    private final int uBrightnessLocation;
    private final int uSaturationLocation;
    private final int uVignetteLocation;

    private final int uGridLocation;

    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public TextureShaderProgramOriginal(Context context){

        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader_original);

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        uContrastLocation = glGetUniformLocation(program, U_CONTRAST);
        uBrightnessLocation = glGetUniformLocation(program, U_Brightness);
        uSaturationLocation = glGetUniformLocation(program, U_SATURATION);
        uVignetteLocation = glGetUniformLocation(program, U_VIGNETTE);

        uGridLocation = glGetUniformLocation(program, U_GRID);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);

    }


    public void setUniforms(float[] matrix, int[] hTex, float contrast, float brightness, float saturation, float vignette, int grid) {

        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, hTex[0]);
        glUniform1i(uTextureUnitLocation, 0);

        glUniform1f(uContrastLocation, contrast);
        glUniform1f(uBrightnessLocation, brightness);
        glUniform1f(uSaturationLocation, saturation);
        glUniform1f(uVignetteLocation, vignette);

        glUniform1i(uGridLocation, grid);


    }

    public int getPositionAttributeLocation(){

        return aPositionLocation;

    }

    public int getTextureCoordinatesAttributeLocation(){

        return aTextureCoordinatesLocation;

    }


}
