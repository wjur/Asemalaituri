varying vec3 normal, eyeVec, ld[4];
varying float att[4];

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

	gl_FragColor = final_color;			
}