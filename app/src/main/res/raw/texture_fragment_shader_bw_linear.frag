#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;

uniform float u_Contrast;
uniform float u_Brightness;
uniform float u_Saturation;
uniform float u_Strength;
varying vec2 texCoord;



vec3 BrightnessContrastSaturation(vec3 color, float brt, float con, float sat){
   vec3 black = vec3(0., 0., 0.);
   vec3 middle = vec3(0.5, 0.5, 0.5);

   vec3 brtColor = mix(black, color, brt);
   vec3 conColor = mix(middle, brtColor, con);
   vec3 satColor = mix(color, conColor, sat);
   return satColor;
}



void main() {

   vec4 normalColor = texture2D(u_TextureUnit,texCoord);

   vec3 normalColor1 = BrightnessContrastSaturation(normalColor.rgb, u_Brightness, u_Contrast, u_Saturation);

    float g = u_Strength * texCoord.x * normalColor1.g + (1.0 - u_Strength * texCoord.x) * normalColor1.r;
    float b = u_Strength * texCoord.x * normalColor1.b + (1.0 - u_Strength * texCoord.x) * normalColor1.r;

   gl_FragColor = vec4(normalColor1.r, g, b, 1.0);


}
