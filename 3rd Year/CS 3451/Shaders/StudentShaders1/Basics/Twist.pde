import javax.media.opengl.*;
import processing.opengl.*;
import javax.media.opengl.glu.GLU;


public class Twist implements Scene
{
   GLSL glsl;
   
   float twist;
   int subd;
   
   public Twist()
   {
       glsl = new GLSL();
       
       glsl.loadVertexShader("shaders/twist.vert");
       glsl.loadFragmentShader("shaders/basic.frag");
       glsl.useShaders();
       
       reset();
   }
  
   public void reset()
   {
       twist = 0.0;
       subd = 1;
   }
   
   public void performAction(char k)
   {
       if( k == 'a')
         twist+=0.1;
       else if(k == 'd')
         twist-=0.1;
       else if(k == 'w')
         subd *= 2;
       else if(k == 's' && subd > 1)
         subd /= 2;
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
       
       glsl.startShader();
       
         int twistLoc = glsl.getUniformLocation("twisting");
         gl.glUniform1f(twistLoc,twist);
         
         float[] a = {-1.0, 1.0, 0.0};
         float[] b = {1.0, 1.0, 0.0};
         float[] c = {0.0, -1.0, 0.0};
         
         for(int i=0;i<subd;i++){
       
           gl.glBegin(gl.GL_TRIANGLE_STRIP);
            
           gl.glColor4f(1.0, i/(subd*1.0), 0.0, 1.0);
           
           float topC = i/(subd*1.0);
           float botC = (i+1)/(subd*1.0);
           
           float topA = 1-topC;
           float botA = 1-botC;
           
           float topB = 0;
           float botB = 0;
           
           while(topA>=0){
             if(topA==1-topC){
               gl.glVertex3f(botA*a[0] + botB*b[0] + botC*c[0],
                             botA*a[1] + botB*b[1] + botC*c[1],
                             botA*a[2] + botB*b[2] + botC*c[2]);
             } 
             
             gl.glVertex3f(topA*a[0] + topB*b[0] + topC*c[0],
                             topA*a[1] + topB*b[1] + topC*c[1],
                             topA*a[2] + topB*b[2] + topC*c[2]);
           }
           
           if(topA>0){
             gl.glVertex3f(botA*a[0] + botB*b[0] + botC*c[0],
                             botA*a[1] + botB*b[1] + botC*c[1],
                             botA*a[2] + botB*b[2] + botC*c[2]);
           }
           
           topA -=(1/(subd*1.0));
           botA -=(1/(subd*1.0));
           topB +=(1/(subd*1.0));
           botB +=(1/(subd*1.0));
        
           gl.glEnd();
         }
         
       glsl.endShader();
       
       pgl.endGL();
   }
}
