#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;

uniform float u_Contrast;
uniform float u_Brightness;
uniform float u_Saturation;

varying vec2 texCoord;



vec3 BrightnessContrastSaturation(vec3 color, float brt, float con, float sat){
   vec3 black = vec3(0., 0., 0.);
   vec3 middle = vec3(0.5, 0.5, 0.5);

   vec3 brtColor = mix(black, color, brt);
   vec3 conColor = mix(middle, brtColor, con);
   vec3 satColor = mix(color, conColor, sat);
   return satColor;
}

float intensity(in vec4 color)
{
   return sqrt((color.x*color.x)+(color.y*color.y)+(color.z*color.z));
}


vec3 sobel(float step, vec2 center)
{
   // get samples around pixel
    float tleft = intensity(texture2D(u_TextureUnit,center + vec2(-step,step)));
    float left = intensity(texture2D(u_TextureUnit,center + vec2(-step,0)));
    float bleft = intensity(texture2D(u_TextureUnit,center + vec2(-step,-step)));
    float top = intensity(texture2D(u_TextureUnit,center + vec2(0,step)));
    float bottom = intensity(texture2D(u_TextureUnit,center + vec2(0,-step)));
    float tright = intensity(texture2D(u_TextureUnit,center + vec2(step,step)));
    float right = intensity(texture2D(u_TextureUnit,center + vec2(step,0)));
    float bright = intensity(texture2D(u_TextureUnit,center + vec2(step,-step)));

   // Sobel masks (to estimate gradient)
   //        1 0 -1     -1 -2 -1
   //    X = 2 0 -2  Y = 0  0  0
   //        1 0 -1      1  2  1

    float x = tleft + 2.0*left + bleft - tright - 2.0*right - bright;
    float y = -tleft - 2.0*top - tright + bleft + 2.0 * bottom + bright;
    float color = sqrt((x*x) + (y*y));
    float limit = 0.3;
    if (color > limit){return vec3(0.0, 0.0, 0.0);}
    else{
        vec3 akt = texture2D(u_TextureUnit,center).rgb;
        float maxi = max(akt.r, max(akt.g,akt.b));

        float absolutRG = abs(akt.r - akt.g);
        float absolutRB = abs(akt.r - akt.b);
        float absolutGB = abs(akt.g - akt.b);

        if(maxi == akt.r){
            /*if(absolutRG <= 0.1 && absolutRB <= 0.1){
                return vec3(1.0, 1.0, 1.0);
            }*/
            if(absolutRG <= 0.04){
                return vec3(0.9, 0.9, 0.9);
            }
            else if(absolutRB <= 0.04){
                return vec3(0.9, 0.0, 0.9);
            }
            else{
                return vec3(0.9, 0.0, 0.0);
            }

        }
        else if(maxi == akt.g){
             /*if(absolutRG <= 0.1 && absolutGB <= 0.1){
                 return vec3(1.0, 1.0, 1.0);
             }*/
             if(absolutRG <= 0.04){
                 return vec3(0.9, 0.9, 0.9);
             }
             else if(absolutGB <= 0.04){

                 return vec3(0.0, 0.9, 0.9);
             }
             else{
                return vec3(0.0, 0.9, 0.0);
             }
        }
        else{
             /*if(absolutRB <= 0.1 && absolutGB <= 0.1){
                 return vec3(1.0, 1.0, 1.0);
             }*/
             if(absolutRB <= 0.04){
                 return vec3(0.9, 0.0, 0.9);
             }
             else if(absolutGB <= 0.04){

                 return vec3(0.0, 0.9, 0.9);
             }
             else{
                return vec3(0.0, 0.0, 0.9);
             }
        }


    }
 }


void main() {
        
   vec4 normalColor = texture2D(u_TextureUnit,texCoord);

   vec3 normalColor1 = BrightnessContrastSaturation(normalColor.rgb, u_Brightness, u_Contrast, u_Saturation);

   float step = 1.0 / 512.0;
   vec2 center = texCoord.st;
   
   gl_FragColor = vec4(sobel(step,center), 1.0);



}