uniform vec4 ambientMat;
uniform vec4 diffuseMat;
uniform vec4 specMat;
uniform float specPow;


uniform int ambientOn;
uniform int diffuseOn;
uniform int specularOn;

void main()
{
	gl_FrontColor = vec4(1.0, 0.0, 0.0, 1.0);
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}