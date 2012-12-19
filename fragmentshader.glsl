varying vec3 normal, eyeVec, ld[4];
varying float att[4];
uniform sampler2D sampler[6];

uniform int texturesOn;
uniform int fogOn;


const float cos_delta_angle = -0.05;

void main (void)
{
	vec4 final_color = (gl_FrontLightModelProduct.sceneColor * gl_FrontMaterial.ambient);
							
	vec3 N = normalize(normal);
	vec3 E = normalize(eyeVec);
	
	for (int i=0; i<4; i++)
	{
		final_color += (gl_LightSource[i].ambient * gl_FrontMaterial.ambient);
	
		vec3 L = normalize(ld[i]);
		vec3 D = normalize(gl_LightSource[i].spotDirection);
		
		
		
		float cos_cur_angle = dot(-L, D);
		float cos_inner_cone_angle = gl_LightSource[i].spotCosCutoff;
		float cos_outer_cone_angle = cos_delta_angle + cos_inner_cone_angle;


		
		float spot = 0.0;
		spot = clamp((cos_cur_angle - cos_outer_cone_angle) / 
	       -cos_delta_angle, 0.0, 1.0);

		float lambertTerm = dot(N,L);
		
		if(lambertTerm > 0.0)
		{
			final_color += gl_LightSource[i].diffuse * 
			               gl_FrontMaterial.diffuse * 
						   lambertTerm * att[i]* spot;	
			
			
			vec3 R = reflect(-L, N);
			float specular = pow( max(dot(R, E), 0.0), 
			                 gl_FrontMaterial.shininess );
			final_color += gl_LightSource[i].specular * 
			               gl_FrontMaterial.specular * 
						   specular * att[i]* spot;	
		}
	}
	float intens = final_color.r*0.3 + final_color*0.59 + final_color * 0.11;//30 Red + 59 Green + 11 Blue
	intens *= 5;
	
	int i = 1;
	
	float colors[7];
	colors[0]=0;
	colors[1]=texture2D(sampler[0],gl_TexCoord[0].st);
	colors[2]=texture2D(sampler[1],gl_TexCoord[0].st);
	colors[3]=texture2D(sampler[2],gl_TexCoord[0].st);
	colors[4]=texture2D(sampler[3],gl_TexCoord[0].st);
	colors[5]=texture2D(sampler[4],gl_TexCoord[0].st);
	colors[6]=texture2D(sampler[5],gl_TexCoord[0].st);
		
	float lower = (int)floor(intens);
	float higher = (int)ceil(intens);
	
	int ilow = lower;
	int ihi = higher;
	
	
	gl_FragColor = vec4(higher/5.0f, higher/5.0f, higher/5.0f, 1) * colors[5];
	gl_FragColor = texture2D(sampler[0],gl_TexCoord[0].st);
}