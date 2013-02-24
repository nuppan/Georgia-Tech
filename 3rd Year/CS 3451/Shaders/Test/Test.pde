import javax.media.opengl.*;
import processing.opengl.*;
import javax.media.opengl.glu.GLU;

ArrayList<Scene> scenes;

int currScene = 0;



void setup()
{
  size(512,512,OPENGL);
  
  GL gl = ((PGraphicsOpenGL)g).gl;
  
  String glVersion = gl.glGetString(GL.GL_VERSION);
  String glslVersion = gl.glGetString(GL.GL_SHADING_LANGUAGE_VERSION);
  
  IntBuffer xx = IntBuffer.allocate(1);
  gl.glGetIntegerv(GL.GL_MAX_COLOR_ATTACHMENTS_EXT, xx);
  
  println("OpenGL Version: " + glVersion);
  println("GLSL Version: " + glslVersion);
  println("You have " + xx.array()[0] + " color attachments");
  
    
  scenes = new ArrayList<Scene>();
  scenes.add(new BasicTriangle());
}

void keyPressed() 
{
  if(keyCode == RIGHT && currScene < scenes.size()-1)
  {
    currScene++;
    scenes.get(currScene).reset();
  }
  else if(keyCode == LEFT && currScene > 0)
  {
    currScene--;
    scenes.get(currScene).reset();
  }
  else
    scenes.get(currScene).performAction(key);
}

void draw()
{
   if(scenes.size() > 0)
     scenes.get(currScene).render();
} 

int[] loadTexture(GL gl, String textureFile)
{
   PImage img = loadImage(textureFile); 

   
   int tex[] = {0};

  gl.glGenTextures(1,tex,0);
  gl.glBindTexture(GL.GL_TEXTURE_2D,tex[0]);
  
    gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR);
  gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
  
  gl.glTexParameterf( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S,  GL.GL_REPEAT );
   gl.glTexParameterf( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T,  GL.GL_REPEAT);
  
  gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 4,img.width, img.height, 0, GL.GL_BGRA, GL.GL_UNSIGNED_BYTE, IntBuffer.wrap(img.pixels));
  
 
                    
  return tex;
}
