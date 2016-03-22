#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_TextureUnit;
uniform sampler2D u_TextureUnit1;

uniform float u_Contrast;
uniform float u_Brightness;
uniform float u_Saturation;
uniform float u_Vignette;

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

    vec3 normalColor3 = texture2D(u_TextureUnit1,texCoord).rgb;

		normalColor2.r = dot(normalColor2, vec3(.393, .769, .189)) * normalColor3.r;
        normalColor2.g = dot(normalColor2, vec3(.349, .686, .168)) * normalColor3.g;
        normalColor2.b = dot(normalColor2, vec3(.272, .534, .131)) * normalColor3.b;

	gl_FragColor = vec4(normalColor2, 1.0);

}
