package de.maximal.objects;

import de.maximal.data.VertexArray;
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

import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glFlush;
import static de.maximal.data.Constants.BYTES_PER_FLOAT;

/**
 * Created by Max Kapsecker on 22.05.2015.
 */
public class Table {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;


    private static final float[] vArray1 = { 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f };
    private static final float[] vArray2 = { 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f };


    private final VertexArray vertexArray1;
    private final VertexArray vertexArray2;

    public Table(){

        vertexArray1 = new VertexArray(vArray1);

        vertexArray2 = new VertexArray(vArray2);

    }

    public void bindDataOriginal(TextureShaderProgramOriginal textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataBlackWhite(TextureShaderProgramBlackWhite textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataSepia(TextureShaderProgramSepia textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataNegative(TextureShaderProgramNegative textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataEdge(TextureShaderProgramEdge textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataPortrait(TextureShaderProgramPortrait textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataEffect6(TextureShaderProgramEffect6 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataEffect7(TextureShaderProgramEffect7 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataBwLight(TextureShaderProgramBwLight textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataBwLight1(TextureShaderProgramBwMedium textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataBwLight2(TextureShaderProgramBwDark textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataBwLight3(TextureShaderProgramBwLight3 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataBwLight4(TextureShaderProgramBwLinear textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataBwLight5(TextureShaderProgramBwEmboss textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    //RETRO
    public void bindDataRetro0(TextureShaderProgramRetro0 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataRetro1(TextureShaderProgramRetro1 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataRetro2(TextureShaderProgramRetro2 textureProgram){
        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataRetro3(TextureShaderProgramRetro3 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataRetro4(TextureShaderProgramRetro4 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataRetro5(TextureShaderProgramRetro5 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    //PAINTED
    public void bindDataPainted0(TextureShaderProgramPainted0 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataPainted1(TextureShaderProgramPainted1 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataPainted2(TextureShaderProgramPainted2 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataPainted3(TextureShaderProgramPainted3 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataPainted4(TextureShaderProgramPainted4 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataPainted5(TextureShaderProgramPainted5 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    //...//
    public void bindDataTest1(TextureShaderProgramTest1 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }

    public void bindDataTest2(TextureShaderProgramTest2 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }
    public void bindDataTest3(TextureShaderProgramTest3 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }
    public void bindDataTest4(TextureShaderProgramTest4 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }
    public void bindDataTest5(TextureShaderProgramTest5 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }
    public void bindDataTest6(TextureShaderProgramTest6 textureProgram){

        vertexArray1.setVertexAttribPointer(0,textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 4*2);
        vertexArray2.setVertexAttribPointer(0, textureProgram.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, 4*2);

    }



    public void draw(){

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
        glFlush();

    }
}
