#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;
uniform sampler2D u_TextureUnit1;

uniform float u_Contrast;
uniform float u_Brightness;
uniform float u_Saturation;


varying vec2 texCoord;
const vec3 W = vec3(0.2125, 0.7154, 0.0721);


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
    if (color > limit){return vec3(0.0,0.0,0.0);}
    return vec3(1.0,1.0,1.0);
 }

vec3 ovelayBlender(vec3 Color, vec3 tt){
	vec3 filter_result;
	
	//if(luminance < 0.5)
	//	filter_result = 2. * trans_filter * Color;
	//else
		filter_result = 1. - (1. - (2. *(tt - 0.5)))*(1. - Color);
		
	return filter_result;
}

void main() {
        
	vec4 normalColor = texture2D(u_TextureUnit,texCoord);
	vec4 normalColor2 = texture2D(u_TextureUnit1,texCoord);

   //vec3 normalColor1 = BrightnessContrastSaturation(normalColor.rgb, u_Brightness, u_Contrast, u_Saturation);

   float step = 1.0/512.0;
   vec2 center = texCoord.st;
    vec3 normalColor1 = sobel(step,center);
    vec3 after_filter = mix(sobel(step,center), ovelayBlender(normalColor2.rgb, sobel(step,center)), 0.55);
	
	gl_FragColor = vec4(after_filter, 1.0);

}
