varying vec3 normal, eyeVec, ld[4];
varying float att[4];

void main()
{	
	gl_Position = ftransform();
	gl_ClipVertex = gl_ModelViewMatrix * gl_Vertex;	
	normal = gl_NormalMatrix * gl_Normal;
	gl_TexCoord[0] = gl_MultiTexCoord0;
	gl_TexCoord[1] = gl_MultiTexCoord1;
	//gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;
	//gl_TexCoord[1] = gl_TextureMatrix[1] * gl_MultiTexCoord1;

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

	
}