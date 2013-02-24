// Project 2, ANIMATION OF CURVES
// Author(s): Jarek ROSSIGNAC *** Krista Patel, Joon Ki Hong
// Class: CS3451 (please delete the wrong class number)
// Last update on: July 23, 2012
// Usage: click&drag to move points
// Contains: point and vector classes
// Demonstrates: use of parametric curves to define animations

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true,same2and1=false, showRails=true, animate=true, keyFrames=false, paths=true;
pts P = new pts();
float t=0, f=0;

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(600, 600);            // window size
  //myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  P.declare().makeGrid(4);
  ramp.setTo(green_hue,red_hue,saturated,dark);
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  if(keyPressed && key=='r') { }
  pt A=Curve(P.G[0],P.G[1],P.G[2],P.G[3],f); 
  pt B=Curve(P.G[4],P.G[5],P.G[6],P.G[7],f); 
  pt C=Curve(P.G[8],P.G[9],P.G[10],P.G[11],f);
  pt D=Curve(P.G[12],P.G[13],P.G[14],P.G[15],f);
  if(showRails) { 
    if(paths) { 
      pen(magenta,2);   drawCurve(P.G[0],P.G[1],P.G[2],P.G[3]); show(A,4);
      pen(cyan,2); drawCurve(P.G[4],P.G[5],P.G[6],P.G[7]); show(B,4);
      pen(blue,2);  drawCurve(P.G[8],P.G[9],P.G[10],P.G[11]);  show(C,4);
      pen(yellow,2);  drawCurve(P.G[12],P.G[13],P.G[14],P.G[15]);  show(D,4);
      }
    if(keyFrames) { 
      pen(green,2);   drawCurve(P.G[0],P.G[1],P.G[2],P.G[3]); show(A,4);
      pen(orange,2); drawCurve(P.G[4],P.G[5],P.G[6],P.G[7]); show(B,4);
      pen(red,2);  drawCurve(P.G[8],P.G[9],P.G[10],P.G[11]);  show(C,4);
      pen(black,2);  drawCurve(P.G[12],P.G[13],P.G[14],P.G[15]);  show(D,4);
      }
    pen(grey,1); P.labels();
    }  
  pen(ramp.at(0.5*f),8); drawCurve(A,B,C,D);
  if(animate) {t+=0.02; if (t>=4) t=0; f=3*sq(cos(t*PI/4));} 
  if(scribeText) displayTextImage();
  }

//**************************** interrupts ****************************
void keyPressed() {
  if(key=='?') scribeText=!scribeText; // toggle sign of depth offset
  if(key=='!') snapPicture(); // make a picture of the canvas
  if(key=='a') animate=!animate; 
  if(key=='k') keyFrames=!keyFrames; 
  if(key=='p') paths=!paths; 
  if(key==' ') showRails=!showRails; 
  if(key==']') P.fitToCanvas();
  if(key=='S') P.savePts();   
  if(key=='L') P.loadPts(); 
  if(key=='s') P.savePts("data/pts");   
  if(key=='l') P.loadPts("data/pts"); 
  if(key=='Q') exit();  // quit application
  }

void mousePressed() {  // executed when the mouse is pressed
  if (!Mouse().isInWindow()) return; // do nothing if pressed out of the window (can change that method of pt to restric where one can click
  P.pickClosest(Mouse()); // used to pick the closest vertex of C to the mouse
  if (keyPressed) {
     if (key=='a')  P.addPt(Mouse()); 
     if (key=='d')  P.deletePickedPt(); 
     }  
  }
  
void mouseDragged() {
  if (!keyPressed || (key=='a')) P.dragPicked();   // drag selected point with mouse
  if (keyPressed) {
      if (key=='.') f+=2.*float(mouseX-pmouseX)/width;  // adjust current frame   
      if (key=='t') P.dragAll(); // move all vertices
      if (key=='r') P.rotateAllAroundCentroid(Mouse(),Pmouse()); // turn all vertices around their center of mass
      if (key=='z') P.scaleAllAroundCentroid(Mouse(),Pmouse()); // scale all vertices with respect to their center of mass
      }
  }  

 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS3451 Fall 2012, Project 2 demo: MOVING CURVE", name ="Joon Ki Hong, Krista Patel",
       menu="?:txt, t:tran, r:rot, z:zoom, ]:fit, a:anim, .:time, k:key, p:path, S,s:save, L,l:load, !:pic, Q:quit",
       guide="click&drag mouse to move points, press space (_) to hide control curves"; // help info

