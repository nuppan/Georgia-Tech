//*********************************************************************
//**      3D viewer with camera control and surface picking          **
//**              Jarek Rossignac, October 2010                      **   
//**                    (using PVectors)                             **   
//*********************************************************************
import processing.opengl.*;                // load OpenGL libraries and utilities
import javax.media.opengl.*; 
import javax.media.opengl.glu.*; 
import java.nio.*;
GL gl; 
GLU glu; 
// Global variables:

void setup() {size(900, 900, OPENGL); // size(500, 500, OPENGL);  
  setColors(); sphereDetail(12); rectMode(CENTER);
  glu= ((PGraphicsOpenGL) g).glu;  PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;  gl = pgl.beginGL();  pgl.endGL();
  initm();
  }
void draw() {  background(white);  changeViewAndFrame();
  fill(yellow); pushMatrix(); rotateY(PI/2); rect(0,0,400,400); popMatrix(); // displays surface of model 1 to set the z-buffer for picking points Q and T and for picking the local frames 1 and 2
  if (keyPressed&&key=='t') T.set(Pick()); // sets the target point T where the mouse points. The camera will turn toward's it when the 't' key is released
  if (keyPressed&&key=='q') {SetFrameFromPick(Q,I,J,K); }   // sets Q to the picked surface-point and {I,J,K} to screen aligned vectors
  // the following 2 actions set frame (1 or 2) and edit its origin as the mouse is dragged
  if (keyPressed&&key=='1') {m=1; SetFrameFromPick(Q,I,J,K); mQ[m].set(Q); if(first[m]) {mI[m].set(I); mJ[m].set(J); mK[m].set(K); first[m]=false;} }
  if (keyPressed&&key=='2') {m=2; SetFrameFromPick(Q,I,J,K); mQ[m].set(Q); if(first[m]) {mI[m].set(I); mJ[m].set(J); mK[m].set(K); first[m]=false;} }
//  background(white);  // to reset the z-buffer used above for picking
  noStroke(); show(Q,30,I,J,K); // shows local frame aligned with screen when picked ('q' pressed) using (R,G,B) lines
  noStroke(); fill(cyan); show(T,2); // shows picked point in cyan (it is set when 't' is pressed and becomes the focus) 
  fill(red); show(mQ[1],3); fill(green); show(mQ[2],3); // shows origin of frame 1 and 2 as a small red or green ball
  if(m==1) fill(red); else if(m==2) fill(green); else if(m==0) fill(blue); show(mQ[m],5); // shows origin of selected frame (R,G,B) for (1,2,0) as a bigger ball
  fill(yellow); show(Q,2); // shows current point on surface. Changed when 'q' is pressed
  println("X: " + Q.x + "Y: " + Q.y + "Z: " + Q.z);
  noStroke(); showModel(); // shows second model (currently axes)
//  showTube(mQ[1],mK[1],mQ[2],mK[2],10);
  showQuads(mQ[1],mK[1],mJ[1],mQ[2],mK[2],mJ[2],20,20,20, green);
   } // end draw
 
 // ****************** INTERRUPTS ************************* 
void mouseDragged() {if(keyPressed) return; a-=PI*(mouseY-pmouseY)/height; a=max(-PI/2+0.1,a); a=min(PI/2-0.1,a);  b+=PI*(mouseX-pmouseX)/width; } // camera rotation around T when no key is pressed
void keyReleased() {if(key=='t') { L.x=T.x; L.y=T.y; L.z=T.z;  }; } // sets the new focus point to wher ethe mous points to when the mouse-button is released
void keyPressed() {
  if(key=='m') showModel=!showModel; // toggles shaded versus wireframe viewing of the first model
  if(key==' ') {d=300; b=0; a=0; L.y=0; L.x=0; L.z=0;} // reset the view
  if(key=='h') {mQ[m].set(Q); mI[m].set(I); mJ[m].set(J); mK[m].set(K); } // reset the current frame to be defined by the mouse position and the screen orientation
  } 


