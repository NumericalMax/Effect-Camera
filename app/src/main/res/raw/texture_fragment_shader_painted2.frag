#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;
uniform sampler2D u_TextureUnit1;

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

    float tleft = intensity(texture2D(u_TextureUnit,center + vec2(-step,step)));
    float left = intensity(texture2D(u_TextureUnit,center + vec2(-step,0)));
    float bleft = intensity(texture2D(u_TextureUnit,center + vec2(-step,-step)));
    float top = intensity(texture2D(u_TextureUnit,center + vec2(0,step)));
    float bottom = intensity(texture2D(u_TextureUnit,center + vec2(0,-step)));
    float tright = intensity(texture2D(u_TextureUnit,center + vec2(step,step)));
    float right = intensity(texture2D(u_TextureUnit,center + vec2(step,0)));
    float bright = intensity(texture2D(u_TextureUnit,center + vec2(step,-step)));

    float x = tleft + 2.0*left + bleft - tright - 2.0*right - bright;
    float y = -tleft - 2.0*top - tright + bleft + 2.0 * bottom + bright;
    float color = sqrt((x*x) + (y*y));
    float limit = u_Strength;

        if (color > limit){return vec4(0.0, 0.0, 0.0, 1.0);}
        else{
        vec4 ttt = texture2D(u_TextureUnit1,center);
        return vec4(ttt.r * colorUse.r, ttt.g * colorUse.g, ttt.b * colorUse.b, 1.0);
        }

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