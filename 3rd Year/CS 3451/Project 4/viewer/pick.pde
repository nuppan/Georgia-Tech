// ************************ Graphic pick utilities *******************************

// returns 3D point under mouse     
pt Pick() { 
  ((PGraphicsOpenGL)g).beginGL(); 
  int viewport[] = new int[4]; 
  double[] proj=new double[16]; 
  double[] model=new double[16]; 
  gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0); 
  gl.glGetDoublev(GL.GL_PROJECTION_MATRIX,proj,0); 
  gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX,model,0); 
  FloatBuffer fb=ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asFloatBuffer(); 
  gl.glReadPixels(mouseX, height-mouseY, 1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, fb); 
  fb.rewind(); 
  double[] mousePosArr=new double[4]; 
  glu.gluUnProject((double)mouseX,height-(double)mouseY,(double)fb.get(0), model,0,proj,0,viewport,0,mousePosArr,0); 
  ((PGraphicsOpenGL)g).endGL(); 
  return P((float)mousePosArr[0],(float)mousePosArr[1],(float)mousePosArr[2]);
  }

// sets Q where the mouse points to and I, J, K to be aligned with the screen (I right, J up, K towards thre viewer)
void SetFrame(pt Q, vec I, vec J, vec K) { 
     glu= ((PGraphicsOpenGL) g).glu;  PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;  
     float modelviewm[] = new float[16]; gl = pgl.beginGL(); gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, modelviewm, 0); pgl.endGL();
     Q.set(Pick()); 
     I.set(modelviewm[0],modelviewm[4],modelviewm[8]);  J.set(modelviewm[1],modelviewm[5],modelviewm[9]); K.set(modelviewm[2],modelviewm[6],modelviewm[10]);   // println(I.x+","+I.y+","+I.z);
     noStroke();
     }
     

// ********************** frame display ****************************
void showFrame(pt Q, vec I, vec J, vec K, float s) {  // sets the matrix and displays the second model (here the axes as blocks)
  pushMatrix();
  applyMatrix( I.x,    J.x,    K.x,    Q.x,
               I.y,    J.y,    K.y,    Q.y,
               I.z,    J.z,    K.z,    Q.z,
               0.0,    0.0,    0.0,    1.0      );
  showAxes(s); // replace this (showing the axes) with code for showing your second model
  popMatrix();
  }
  
void showAxes(float s) { // shows three orthogonal axes as red, green, blue blocks aligned with the local frame coordinates
  noStroke();
  pushMatrix(); 
  pushMatrix(); fill(red);  scale(s,1,1); box(2); popMatrix();
  pushMatrix(); fill(green);  scale(1,s,1); box(2); popMatrix();
  pushMatrix(); fill(blue);  scale(1,1,s); box(2); popMatrix();  
  popMatrix();  
  }
  

