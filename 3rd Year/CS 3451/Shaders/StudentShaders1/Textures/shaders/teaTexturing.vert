uniform sampler2D my_color_texture;


void main()
{	
  gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}
