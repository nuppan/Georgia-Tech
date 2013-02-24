uniform float twisting;
void main()
{	
	float angle = twisting*length(gl_Vertex.xy);
	
	float cosLength = cos(angle);
	float sinLength = sin(angle);
	
	vec4 twistVertex = gl_Vertex;
	
	twistVertex.x = cosLength*gl_Vertex.x - sinLength*gl_Vertex.y;
	twistVertex.y = dot(vec2(sinLength,cosLength),gl_Vertex.xy);
	
	gl_Position = gl_ModelViewProjectionMatrix * twistVertex;
	gl_FrontColor = gl_Color;
}