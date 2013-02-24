import javax.media.opengl.*;
import processing.opengl.*;
import javax.media.opengl.glu.GLU;




public class textureTeapot implements Scene
{
   GLSL glsl;

    int texDim = 512;
    
    float theta = 0.0;
    
    int[] displaceTex;
    
    public textureTeapot()
    {
          glsl=new GLSL();
        
          glsl.loadVertexShader("shaders/teaTexturing.vert");
          glsl.loadFragmentShader("shaders/teaTexturing.frag");
          glsl.useShaders();
          
          
        
          displaceTex = loadTexture(((PGraphicsOpenGL)g).gl, "textures/teatex.png");
    }
 
   public void reset()
   {
       displaceTex = loadTexture(((PGraphicsOpenGL)g).gl, "textures/teatex.png");
   }
   
   public void performAction(char k){}
   
   
   public void render()
   {
         theta += 0.5;
     
           PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;  // g may change
           GLU glu = pgl.glu;
           GLUT glut = new GLUT();
           
           GL gl = pgl.beginGL();  // always use the GL object returned by beginGL
         
        //stuff unrelated to drawing...
        //draw initial stuff that doens't need the shader.
        
        
        //draw things that you want the shader applied to
      
        
      
        
         
        
        // Do some things with gl.xxx functions here.
        // For example, the program above is translated into:
        //gl.glTranslatef(texDim/2.0, 0, 0);
      	gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
      	
      	gl.glMatrixMode(gl.GL_PROJECTION);
      	gl.glLoadIdentity();
      	
      	glu.gluPerspective (60.0, 1.0, 0.01, 1000.0);
      	
      	gl.glMatrixMode(gl.GL_MODELVIEW);
      	gl.glLoadIdentity();  
      	
      	glu.gluLookAt(0.0,1.0,6.0,
                  0.0,-1.0,0.0,
                  0.0,1.0,0.0);
                  
                  
                  glsl.startShader();
             
                            gl.glPushMatrix();       // spin the scene according to mouse position
                            gl.glRotatef(theta,0,1,0); 
                            
                            
                           
                            
                            
                            gl.glActiveTexture(GL.GL_TEXTURE0+0);
                            gl.glBindTexture(GL.GL_TEXTURE_2D, displaceTex[0]);
                            int texLoc = glsl.getUniformLocation("my_color_texture");
                            gl.glUniform1i(texLoc,0);
                      
                            glut.glutSolidTeapot(2);;
                            
                            gl.glPopMatrix();
        
                glsl.endShader();
        
          pgl.endGL();
     
   }
  
}
