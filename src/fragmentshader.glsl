varying vec3 normal, eyeVec, ld[4];
varying float att[4];

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
		
		if (dot(-L, D) > gl_LightSource[i].spotCosCutoff) 
		{
			float lambertTerm = dot(N,L);
			
			if(lambertTerm > 0.0)
			{
				final_color += gl_LightSource[i].diffuse * 
				               gl_FrontMaterial.diffuse * 
							   lambertTerm * att[i];	
				
				
				vec3 R = reflect(-L, N);
				float specular = pow( max(dot(R, E), 0.0), 
				                 gl_FrontMaterial.shininess );
				final_color += gl_LightSource[i].specular * 
				               gl_FrontMaterial.specular * 
							   specular * att[i];	
			}
		}
	}

	gl_FragColor = final_color;			
}