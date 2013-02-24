import javax.media.opengl.*;
import processing.opengl.*;
import javax.media.opengl.glu.GLU;

public class LitSphere implements Scene
{
     GLSL gouraud, phong, basic;
     
     float theta = 0.0;
     
     int ambientOn = 1;
     int diffuseOn = 1;
     int specularOn = 1;
     
     float camx = 0.0;
     float camz = 1.0;
     float alph = 0.0;
     
     float[] lpos = {0,0,2,1}; //4th term indicates it is a point light
     
     float[] ambientMat = {0.3, 0.03, 0.0, 1.0};
     float[] diffuseMat = {0.7, 0.0, 0.0, 1.0};
     float[] specMat = {1.0, 1.0, 1.0, 1.0};
     float specPow = 25.0;
  
     public LitSphere()
     {
          gouraud = new GLSL();
         
          gouraud.loadVertexShader("shaders/gouraud.vert");
          gouraud.loadFragmentShader("shaders/gouraud.frag");
          gouraud.useShaders();
          
          phong = new GLSL();
          phong.loadVertexShader("shaders/phong.vert");
          phong.loadFragmentShader("shaders/phong.frag");
          phong.useShaders();
          
          basic = new GLSL();
          basic.loadVertexShader("shaders/basic.vert");
          basic.loadFragmentShader("shaders/basic.frag");
          basic.useShaders();
     }
     
     public void reset()
     {
          theta = 0.0;
         
          ambientOn = 1;
          diffuseOn = 1;
          specularOn = 1;
        
          camx = 0.0;
          camz = 1.0;
          alph = 0.0;  
     }
     
     
     public void performAction(char k)
     {
         if(k == 'A' || k == 'a')
           ambientOn = (ambientOn + 1) % 2;
           
         if(k == 'D' || k == 'd')
           diffuseOn = (diffuseOn + 1) % 2;
         
         if(k == 'S' || k == 's')
           specularOn = (specularOn + 1) % 2;  
           
         if(k == 'J' || k == 'j')
           alph--;
           
         if(k == 'L' || k == 'l')
           alph++;
     }
     
     
     public void render()
     {
        theta += .5;
            
             PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;  // g may change
               GLU glu = pgl.glu;
               GLUT glut = new GLUT();
               
               GL gl = pgl.beginGL();  // always use the GL object returned by beginGL
            
          	gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
          	
          	gl.glMatrixMode(gl.GL_PROJECTION);
          	gl.glLoadIdentity();
          	
          	glu.gluPerspective (60.0, 1.0, 0.01, 1000.0);
          	
          	gl.glMatrixMode(gl.GL_MODELVIEW);
          	gl.glLoadIdentity();  
          
                  camx = sin(alph*PI/180.0);
                  camz = cos(alph*PI/180.0);
          
                  float magn = (float)(sqrt(camx*camx + camz*camz));
                  
                  camx = 6.0 * (camx/magn);
                  camz = 6.0 * (camz/magn);
          	
          	glu.gluLookAt(camx,0.0,camz,
                      0.0,0.0,1.0,
                      0.0,1.0,0.0);
                      
                      
                 basic.startShader();
                 
                       gl.glPushMatrix();
                       gl.glTranslatef(lpos[0], lpos[1], lpos[2]);
                       gl.glScalef(0.1, 0.1, 0.1);
                       
                       glut.glutSolidSphere(1, 10, 10);
                       
                       gl.glPopMatrix();
                 
                 basic.endShader();
                      
                      
                      
                 gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, lpos, 0);
                 
                    
                  gouraud.startShader();
                  
                        int gAmbM = gouraud.getUniformLocation("ambientMat");
                        gl.glUniform4f(gAmbM, ambientMat[0], ambientMat[1], ambientMat[2], ambientMat[3]);
                        
                        int gDiffM = gouraud.getUniformLocation("diffuseMat");
                        gl.glUniform4f(gDiffM, diffuseMat[0], diffuseMat[1], diffuseMat[2], diffuseMat[3]);
                        
                        int gSpM = gouraud.getUniformLocation("specMat");
                        gl.glUniform4f(gSpM, specMat[0], specMat[1], specMat[2], specMat[3]);
                        
                        int gSpP = gouraud.getUniformLocation("specPow");
                        gl.glUniform1f(gSpP, specPow);
                        
                        
                        
                        
                        
                              
                        int gAmbOn = gouraud.getUniformLocation("ambientOn");
                        gl.glUniform1i(gAmbOn, ambientOn);
                     
                        int gDiffOn = gouraud.getUniformLocation("diffuseOn");
                        gl.glUniform1i(gDiffOn, diffuseOn);  
                       
                       int gSpecOn = gouraud.getUniformLocation("specularOn");
                        gl.glUniform1i(gSpecOn, specularOn); 
                 
                        gl.glPushMatrix();       // spin the scene according to mouse position
                        gl.glTranslatef (-1.5, 0.0, 0.0);
                        gl.glRotatef(theta,0,1,0); 
                        gl.glRotatef(90,1,0,0); 
                  
                        glut.glutSolidSphere(1, 25, 25);
                        
                        gl.glPopMatrix();
                
                    gouraud.endShader();
                  
                  
                    phong.startShader();
                    
                        int pAmbM = phong.getUniformLocation("ambientMat");
                        gl.glUniform4f(pAmbM, ambientMat[0], ambientMat[1], ambientMat[2], ambientMat[3]);
                        
                        int pDiffM = phong.getUniformLocation("diffuseMat");
                        gl.glUniform4f(pDiffM, diffuseMat[0], diffuseMat[1], diffuseMat[2], diffuseMat[3]);
                        
                        int pSpM = phong.getUniformLocation("specMat");
                        gl.glUniform4f(pSpM, specMat[0], specMat[1], specMat[2], specMat[3]);
                        
                        int pSpP = phong.getUniformLocation("specPow");
                        gl.glUniform1f(pSpP, specPow);
                        
                        
                        
                        
                        int pAmbOn = phong.getUniformLocation("ambientOn");
                        gl.glUniform1i(pAmbOn, ambientOn);
                     
                        int pDiffOn = phong.getUniformLocation("diffuseOn");
                        gl.glUniform1i(pDiffOn, diffuseOn);  
                       
                        int pSpecOn = phong.getUniformLocation("specularOn");
                        gl.glUniform1i(pSpecOn, specularOn);                  
                        
                        gl.glPushMatrix();       // spin the scene according to mouse position
                        gl.glTranslatef (1.5, 0.0, 0.0);
                        gl.glRotatef(theta,0,1,0);  
                        gl.glRotatef(90,1,0,0); 
                  
                        glut.glutSolidSphere(1, 25, 25);
                        
                        gl.glPopMatrix();
                
                    phong.endShader();
              
            
            
              pgl.endGL();
     }
}
