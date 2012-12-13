varying vec3 normal, eyeVec, ld[4];
varying float att[4];
uniform sampler2D color_texture0;
uniform sampler2D color_texture1;

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
	gl_FragColor = final_color;
	//gl_FragColor = texture2D(color_texture0, gl_TexCoord[0].st);
	if (texturesOn == 1)
	{
		gl_FragColor *= texture2D(color_texture0, gl_TexCoord[0].st);
	}
	else if (texturesOn == 2)
	{
		vec4 t1 = texture2D(color_texture0,gl_TexCoord[0].st);
		vec4 t2 = texture2D(color_texture1,gl_TexCoord[1].st);
		vec4 color= mix(t1,t2,t2.a);
		gl_FragColor*=color;
	}
	else if (texturesOn == 3)
	{
		gl_FragColor = texture2D(color_texture0, gl_TexCoord[0].st);
	}
	
	//exp2
	if (fogOn == 3)
	{
		//fogFactor = (end - z) / (end - start) 
		//fogFactor = e-(density * z)
		//fogFactor = e-(density * z)^2
		
		float d=length(eyeVec);
		//d=exp(-d*0.1);
		const float LOG2 = 1.442695;
		float fogFactor = exp2( -gl_Fog.density * 
				   gl_Fog.density * 
				   d * 
				   d * 
				   LOG2 );
		fogFactor = clamp(fogFactor, 0.0, 1.0);
		
		gl_FragColor=mix(gl_Fog.color, gl_FragColor, fogFactor );
		//gl_FragColor=(d*gl_FragColor)+((1.0-d)*gl_Fog.color);
	}
	else if (fogOn == 2)
	{
		//exp
		float d=length(eyeVec);
		float fogFactor = exp( -gl_Fog.density * d);
		fogFactor = clamp(fogFactor, 0.0, 1.0);
		gl_FragColor=mix(gl_Fog.color, gl_FragColor, fogFactor);
	}
	else if (fogOn == 1)
	{
		float z = gl_FragCoord.z / gl_FragCoord.w;
		float fogFactor = (gl_Fog.end - z) / (gl_Fog.end - gl_Fog.start);
		fogFactor = clamp(fogFactor, 0.0, 1.0);
		gl_FragColor=mix(gl_Fog.color, gl_FragColor, fogFactor);
		
	}
				
}