package de.maximal.programs;

import android.content.Context;
import android.graphics.Bitmap;

import de.maximal.util.ShaderHelper;
import de.maximal.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by Max Kapsecker on 22.05.2015.
 */
public class ShaderProgram {

    //Uniforms
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_TEXTURE_UNIT1 = "u_TextureUnit1";

    protected static final String U_STRENGTH = "u_Strength";
    protected static final String U_CONTRAST = "u_Contrast";
    protected  static final String U_Brightness = "u_Brightness";
    protected static final String U_SATURATION = "u_Saturation";
    protected static final String U_VIGNETTE = "u_Vignette";

    protected static final String U_GRID = "u_Grid";

    //Attributes
    protected static final String A_POSITION = "a_Position";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";


    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId){

        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId), TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));

    }

    public void useProgram(){

        glUseProgram(program);

    }


    //IMAGE PROCESSOR
    protected Bitmap imagePostProcessing(Bitmap bitmap){



        return bitmap;
    }


}
