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
