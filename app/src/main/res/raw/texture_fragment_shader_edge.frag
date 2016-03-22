#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;

uniform float u_Strength;
uniform float u_Contrast;
uniform float u_Brightness;
uniform float u_Saturation;

uniform float u_Vignette;

varying vec2 texCoord;

const float PI = 3.1415926;
//was ist mit den pixeln an den rändern??!
vec3 Vignette(vec3 colort, vec2 tex, float vig){

	float dist = distance(tex.xy, vec2(0.5,0.5));
  	colort *= smoothstep(vig, 0.1, dist);
	return colort;

}

float intensity(in vec4 color)
{
   return sqrt((color.x*color.x)+(color.y*color.y)+(color.z*color.z));
}

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

vec4 sobel(float step, vec2 center, vec3 colorUse)
{
   // get samples around pixel
    vec4 middle = texture2D(u_TextureUnit,center);

    vec4 one = texture2D(u_TextureUnit,center + vec2(-step,step));
    vec4 two = texture2D(u_TextureUnit,center + vec2(-step,0));
    vec4 three = texture2D(u_TextureUnit,center + vec2(-step,-step));
    vec4 four = texture2D(u_TextureUnit,center + vec2(0,step));

    vec4 six = texture2D(u_TextureUnit,center + vec2(0,-step));
    vec4 seven = texture2D(u_TextureUnit,center + vec2(step,step));
    vec4 eight = texture2D(u_TextureUnit,center + vec2(step,0));
    vec4 nine = texture2D(u_TextureUnit,center + vec2(step,-step));

    float r = (one.r + two.r + three.r + four.r + middle.r + six.r + seven.r + eight.r + nine.r) / 9.0;
    float g = (one.g + two.g + three.g + four.g + middle.g + six.g + seven.g + eight.g + nine.g) / 9.0;
    float b = (one.b + two.b + three.b + four.b + middle.b + six.b + seven.b + eight.b + nine.b) / 9.0;

    float tleft = intensity(one);
    float left = intensity(two);
    float bleft = intensity(three);
    float top = intensity(four);
    float bottom = intensity(six);
    float tright = intensity(seven);
    float right = intensity(eight);
    float bright = intensity(nine);

    float x = tleft + 2.0*left + bleft - tright - 2.0*right - bright;
    float y = -tleft - 2.0*top - tright + bleft + 2.0 * bottom + bright;
    float color = sqrt((x*x) + (y*y));
    float limit = u_Strength;

     if (color > limit){return vec4(0.0, 0.0, 0.0, 1.0);}
     else{return vec4(r, g, b, 1.0);}

 }


void main() {

   float step = 1.0 / 900.0;
   vec2 center = texCoord.st;
   vec3 colorUsed;
   colorUsed = vec3(u_Brightness/2.0 ,u_Contrast/2.0,u_Saturation/2.0);
   //colorUsed = vec3(u_Brightness ,u_Contrast ,u_Saturation );
   vec4 sobelOperator = sobel(step, center, colorUsed);
   //vec3 normalColor2 = Vignette(sobelOperator, texCoord, u_Vignette);

    //float alpha = 1.0 - 1.5 * sqrt((texCoord.s - 0.5) * (texCoord.s - 0.5) + (texCoord.t - 0.5) * (texCoord.t - 0.5));

   gl_FragColor = sobelOperator;

}