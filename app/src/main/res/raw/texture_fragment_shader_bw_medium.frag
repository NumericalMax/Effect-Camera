#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;

uniform float u_Contrast;
uniform float u_Brightness;
uniform float u_Saturation;
uniform float u_Vignette;

uniform float u_Strength;


varying vec2 texCoord;
const vec3 vektor = vec3(0.2125, 0.7154, 0.0721);


vec3 BrightnessContrastSaturation(vec3 color, float brt, float con, float sat){
	vec3 black = vec3(0., 0., 0.);
	vec3 middle = vec3(0.5, 0.5, 0.5);

	float luminance = dot(color, vektor);
	vec3 gray = vec3(luminance, luminance, luminance);

	vec3 brtColor = mix(black, color, brt);
	vec3 conColor = mix(middle, brtColor, con);
	vec3 satColor = mix(gray, conColor, sat);
	return satColor;
}

vec3 Vignette(vec3 color, vec2 tex, float vig){

	float dist = distance(tex.xy, vec2(0.5,0.5));
  	color *= smoothstep(vig, 0.1, dist);
	return color;

}

/*float intensity(in vec4 color)
{
   return sqrt((color.x*color.x)+(color.y*color.y)+(color.z*color.z));
}*/

void main() {

	vec4 normalColor = texture2D(u_TextureUnit,texCoord);

	vec3 normalColor1 = BrightnessContrastSaturation(normalColor.rgb, 2.0 * u_Brightness, u_Contrast, u_Saturation);

	vec3 normalColor2 = Vignette(normalColor1, texCoord, u_Vignette);

    /*float middle = intensity(normalColor);
    float step = 1.0 / 900.0;
    vec2 center = texCoord.st;
    float tleft = intensity(texture2D(u_TextureUnit,center + vec2(-step,step)));
    float left = intensity(texture2D(u_TextureUnit,center + vec2(-step,0)));
    float bleft = intensity(texture2D(u_TextureUnit,center + vec2(-step,-step)));
    float top = intensity(texture2D(u_TextureUnit,center + vec2(0,step)));
    float bottom = intensity(texture2D(u_TextureUnit,center + vec2(0,-step)));
    float tright = intensity(texture2D(u_TextureUnit,center + vec2(step,step)));
    float right = intensity(texture2D(u_TextureUnit,center + vec2(step,0)));
    float bright = intensity(texture2D(u_TextureUnit,center + vec2(step,-step)));


    if(middle < u_Strength){
            gl_FragColor = vec4(normalColor2.r, normalColor2.r, normalColor2.r, 1.0);
    }
    else{
        if(tleft < u_Strength || left < u_Strength ||bleft < u_Strength ||top < u_Strength ||bottom < u_Strength ||tright < u_Strength ||right < u_Strength ||bright < u_Strength){
            gl_FragColor = vec4(0.9 * normalColor2.r, 0.9 * normalColor2.r, 0.9 * normalColor2.r, 1.0);
        }
        else{
            gl_FragColor = vec4(0.8 * normalColor2.r, 0.8 * normalColor2.r, 0.8 * normalColor2.r, 1.0);
        }

    }*/

gl_FragColor = vec4(normalColor2.r*normalColor2.g,normalColor2.r*normalColor2.g,normalColor2.r*normalColor2.g, 1.0);

}
