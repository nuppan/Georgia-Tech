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
  
  println("OpenGL Version: " + glVersion);
  println("GLSL Version: " + glslVersion);
  println();
  
  
  scenes = new ArrayList<Scene>();
  scenes.add(new textureTeapot());
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


