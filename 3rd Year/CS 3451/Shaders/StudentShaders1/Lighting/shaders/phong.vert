uniform vec4 ambientMat;
uniform vec4 diffuseMat;
uniform vec4 specMat;
uniform float specPow;


void main()
{
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}
