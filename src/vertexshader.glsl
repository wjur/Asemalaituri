varying vec3 normal, eyeVec, ld[4];
varying float att[4];

void main()
{	
	normal = gl_NormalMatrix * gl_Normal;

	vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);
	
	for (int i=0; i<4; i++)
	{
		ld[i] = vec3(gl_LightSource[i].position.xyz - vVertex);
		float d = length(ld[i]);
		att[i] = 1.0 / ( gl_LightSource[i].constantAttenuation + 
		(gl_LightSource[i].linearAttenuation*d) + 
		(gl_LightSource[i].quadraticAttenuation*d*d) );
	}
	
	eyeVec = -vVertex;

	gl_Position = ftransform();		
}