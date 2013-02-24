import javax.media.opengl.*;
import processing.opengl.*;
import javax.media.opengl.glu.GLU;

public class BasicTriangle implements Scene
{
   GLSL glsl;
   
   float roty = 0.0;
   
   public BasicTriangle()
   {
       glsl = new GLSL();
       
       glsl.loadVertexShader("shaders/basic.vert");
       glsl.loadFragmentShader("shaders/basic.frag");
       glsl.useShaders();
   }
  
   public void reset()
   {
       roty = 0.0;
   }
   
   public void performAction(char k)
   {
       if( k == 'q')
         roty++;
       else if(k == 'e')
         roty--;
   }
   
   
   public void render()
   {
       PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;
       GLU glu = pgl.glu;
       GL gl = pgl.beginGL();
       
       gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);

       gl.glMatrixMode(gl.GL_PROJECTION);
       gl.glLoadIdentity();
        
       glu.gluPerspective(60.0, 1.0, 0.01, 1000.0);
     
       gl.glMatrixMode(gl.GL_MODELVIEW);
       gl.glLoadIdentity();
       
       glu.gluLookAt(0.0,0.0,2.0,  0.0,0.0,0.0,  0.0,1.0,0.0);
       
       gl.glRotatef(roty, 0.0,1.0,0.0);
       
       glsl.startShader();
       
           gl.glBegin(gl.GL_TRIANGLES);
            
               gl.glColor4f(1.0, 0.0, 0.0, 1.0);
               gl.glVertex3f(-1.0, 1.0, 0.0);
               
               gl.glColor4f(0.0, 1.0, 0.0, 1.0);
               gl.glVertex3f(1.0, 1.0, 0.0);
               
               gl.glColor4f(0.0, 0.0, 1.0, 1.0);
               gl.glVertex3f(0.0, -1.0, 0.0);
           
           gl.glEnd();
                 
       glsl.endShader();
       
       pgl.endGL();
   }
}
