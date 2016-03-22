#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;

uniform float u_Contrast;
uniform float u_Brightness;
uniform float u_Saturation;

uniform float u_Strength;

varying vec2 texCoord;



void main() {


     vec2 onePixel = vec2(1.0 / 480.0, 1.0 / 320.0);
     vec4 color;
     color.rgb = vec3(0.5);
     color -= texture2D(u_TextureUnit, texCoord - onePixel) * 5.0;
     color += texture2D(u_TextureUnit, texCoord + onePixel) * 5.0;
     // 5
     color.rgb = vec3((color.r + color.g + color.b) / 3.0);
     gl_FragColor = vec4(color.rgb, 1);

}



