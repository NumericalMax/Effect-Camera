#version 120
attribute vec2 a_Position;
attribute vec2 a_TextureCoordinates;
varying vec4 texCoord;


uniform mat4 u_Matrix;

	void main(){

		gl_Position = u_Matrix * vec4(a_Position.x, a_Position.y, 1.0, 0.0);
		texCoord = gl_MultiTexCoord0;
	}
