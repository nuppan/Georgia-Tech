// ************************ Graphic pick utilities *******************************
pt T = P(); // camera target point set with mouse when pressing 't'
pt E = P(), L=P(); // eye and lookAt 
pt[] mQ= new pt [3];
vec[] mI= new vec [3], mJ= new vec [3], mK= new vec [3]; // three local frames {Q,I,J,K}
int m=1; // which frame is being shown / edited
float d=300, b=0, a=0; // view parameters: distance, angles, q
Boolean showModel=true, tilt=false;
Boolean [] first = {true, true, true}; // track if the frame has already been set
pt Q=P(); vec I=V(), J=V(), K=V(); // picked surface point Q and screen aligned vectors {I,J,K} set when picked

void initm() {for(int i=0; i<3; i++) {mQ[i]=P(); mI[i]=V(); mJ[i]=V(); mK[i]=V(); }} // declares the local frames

void  changeViewAndFrame() {
  float ca=cos(a), sa=sin(a), cb=cos(b), sb=sin(b); // viewing direction angles
  if (keyPressed&&key=='x') {mQ[m].add(mouseX-pmouseX,I).add(pmouseY-mouseY,J); } // moves the selected frame parallel to the screen
  if (keyPressed&&key=='z') {mQ[m].add(mouseX-pmouseX,I).add(mouseY-pmouseY,K); } // moves the selected frame on th ehorizontal plane
  if (keyPressed&&key=='d') d-=mouseY-pmouseY;  // changes distance form the target to the viewpoint
  if (keyPressed&&key=='a') {float a=float(-mouseY+pmouseY+mouseX-pmouseX)/width; mI[m].rotate(a,I,J);  mJ[m].rotate(a,I,J);  mK[m].rotate(a,I,J);} // rotates current frame parallel to the screen
  if (keyPressed&&key=='r') { // rotates the current frames in pitch and yaw
      float a=float(mouseY-pmouseY)/width; mI[m].rotate(a,J,K);  mJ[m].rotate(a,J,K);  mK[m].rotate(a,J,K);
      float b=float(pmouseX-mouseX)/width; mI[m].rotate(b,I,K);  mJ[m].rotate(b,I,K);  mK[m].rotate(b,I,K);  }
  E.set(d*cb*ca, d*sa, d*sb*ca); // sets the eye    
  camera(E.x, E.y, E.z, L.x, L.y, L.z, 0.0, 1.0, 0.0); // defines the view : eye, ctr, up
  directionalLight(250, 250, 250, -E.x, -E.y+100, -E.z); // puts a white light above and to the left of the viewer 
  //  ambientLight(100,100,0);  directionalLight(250, 250, 0, -20, 10, 5); directionalLight(100, 100, 250, 10, -20, -5); // in case you want the light to be fixed in model space
  }


void SetFrameFromPick(pt Q, vec I, vec J, vec K) { // sets Q where the mouse points to and I, J, K to be aligned with the screen (I right, J up, K towards thre viewer)
     glu= ((PGraphicsOpenGL) g).glu;  PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;  
     float modelviewm[] = new float[16]; gl = pgl.beginGL(); gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, modelviewm, 0); pgl.endGL();
     Q.set(Pick()); 
     I.set(modelviewm[0],modelviewm[4],modelviewm[8]);  J.set(modelviewm[1],modelviewm[5],modelviewm[9]); K.set(modelviewm[2],modelviewm[6],modelviewm[10]);   // println(I.x+","+I.y+","+I.z);
     }

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


