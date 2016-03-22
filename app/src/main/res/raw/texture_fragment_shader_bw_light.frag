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


void main() {

	vec4 normalColor = texture2D(u_TextureUnit,texCoord);

	vec3 normalColor1 = BrightnessContrastSaturation(normalColor.rgb, u_Brightness, u_Contrast, u_Saturation);
	
	vec3 normalColor2 = Vignette(normalColor1, texCoord, u_Vignette);

    gl_FragColor = vec4(u_Strength * normalColor2.r + (1.0-u_Strength) * normalColor2.r, u_Strength * normalColor2.r + (1.0-u_Strength) * normalColor2.g, u_Strength * normalColor2.r + (1.0-u_Strength) * normalColor2.b, 1.0);

}
