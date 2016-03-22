attribute vec2 a_Position;
attribute vec2 a_TextureCoordinates;
varying vec2 texCoord;


uniform mat4 u_Matrix;

	void main(){
		texCoord = a_TextureCoordinates;
		gl_Position = u_Matrix*vec4(a_Position.x, a_Position.y, 0.0, 1.0);
	}