// sketch014: Template for project 3, sketch05 final
// Author(s): Jarek ROSSIGNAC *** Joon Ki Hong
// Class: CS3451 
// Last update on: Oct 3, 2012
// Usage: Draw 4 strokes that define the motion of S0 and S1 by holding 1,2,3,4
// + mouse down respectively to draw the strokes. You may adjust the 4 control
// points by dragging them. Press a to animate. Note that you cannot redraw
// strokes while an animation is taking place. Press a to disable animations
// then redraw your strokes.
// Demonstrates: Morphing curves using lengths and angles and exponential/linear methods
// of interpolation of lengths.

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true, animate=false, showAcceleration=true;
STROKES S0 = new STROKES();
STROKES S1 = new STROKES();
STROKES S2 = new STROKES();
STROKES S3 = new STROKES();
STROKES St = new STROKES();
pt A;
pt B;
pt C;
pt D;
int iDelta = 1;
float f=0;
int i=0;
float t=0;
int cornerRad = 50;

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(800, 600);            // window size
  smooth();
  frameRate(30);
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  S0.empty();
  S1.empty();
  S2.empty();
  S3.empty();
  S0.loadPts("data/s0");S1.loadPts("data/s1");S2.loadPts("data/s2");S3.loadPts("data/s3");
  animate=true;
  A = new pt(S0.G[0]);
  A.setRad(cornerRad);
  B = new pt(S1.G[0]);
  B.setRad(cornerRad);
  C = new pt(S1.G[S1.nv-1]);
  C.setRad(cornerRad);
  D = new pt(S0.G[S0.nv-1]);
  D.setRad(cornerRad);
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(green,2);
  if(keyPressed&&key=='f') {S0.tuck(.5);S1.tuck(.5);S2.tuck(.5);S3.tuck(.5);}
  if(keyPressed&&key=='d') {S0.tuck(.5); S0.tuck(-.5);S1.tuck(.5);S1.tuck(-.5);S2.tuck(.5);S2.tuck(-.5);S3.tuck(.5);S3.tuck(-.5);}
  S0.draw();
  pen(red,2);
  S1.draw();
  pen(cyan,2);
  S2.draw();
  pen(magenta,2);
  S3.draw();
  pen(black,2);
  St.draw();
  A.show();
  B.show();
  C.show();
  D.show();
  text("A",A.x-3,A.y+4);
  text("B",B.x-3,B.y+4);
  text("C",C.x-3,C.y+4);
  text("D",D.x-3,D.y+4);

  if(animate) {
    pen(black,2);
    St = WAG(S0,S1,f).scaleRotateTrans(S2.G[i],S3.G[i]);
    if (i>=S2.nv-1){
      iDelta = -iDelta;
    }
    if(t>=4){
      t=0;
      i=0;
      iDelta=1;
    }
    i+=iDelta;
    t+=2.0/(S2.nv-1);
    f=sin(t*PI/4);
  } 
  if(scribeText) displayTextImage();
  }

//**************************** interrupts ****************************
void keyPressed() {
  if(key=='?') scribeText=!scribeText; // toggle sign of depth offset
  if(key=='!') snapPicture(); // make a picture of the canvas
  if(key=='a') if(S0.nv>0 && S1.nv>0 && S2.nv>0 && S3.nv>0) animate=!animate; 
  if(key=='A') showAcceleration=!showAcceleration; 
  if(key==' '){ S0.empty();S1.empty();S2.empty();S3.empty();St.empty();animate=false;}  
  if(key=='S'){ S0.savePts();S1.savePts();S2.savePts();S3.savePts();}
  if(key=='L'){ S0.loadPts();S1.loadPts();S2.loadPts();S3.loadPts();}
  if(key=='s'){ S0.savePts("data/s0");S1.savePts("data/s1");S2.savePts("data/s2");S3.savePts("data/s3");}   
  if(key=='l'){ S0.loadPts("data/s0");S1.loadPts("data/s1");S2.loadPts("data/s2");S3.loadPts("data/s3");}
  if(key=='Q') exit();  // quit application
}

void mouseReleased(){
  if(S0.refined!=true){
    for(int i=0;i<2;i++){S0 = S0.refine();S0.tuck(.5);S0.tuck(-.5);}
    S0.refined = true;
  }
  if(S1.refined!=true){
    for(int i=0;i<2;i++){S1 = S1.refine();S1.tuck(.5);S1.tuck(-.5);}
    S1.refined = true;
  }
  if(S2.refined!=true){
    for(int i=0;i<2;i++){S2 = S2.refine();S2.tuck(.5);S2.tuck(-.5);}
    S2.refined = true;
  }
  if(S3.refined!=true){
    for(int i=0;i<2;i++){S3 = S3.refine();S3.tuck(.5);S3.tuck(-.5);} 
    S3.refined = true;
  }
  if(S0.nv>0 && S1.nv>0 && S2.nv>0 && S3.nv>0){
     if(S0.nv>S1.nv){
       S0.resample(S1.nv); 
     }else if(S1.nv>S0.nv){
       S1.resample(S0.nv); 
     }
     if(S2.nv>S3.nv){
       S2.resample(S3.nv); 
     }else if(S3.nv>S2.nv){
       S3.resample(S2.nv); 
     }
     S0 = S0.scaleRotateTrans(A,D);
     S0.refined=true;
     S1 = S1.scaleRotateTrans(B,C);
     S1.refined=true;
     S2 = S2.scaleRotateTrans(A,B);
     S2.refined=true;
     S3 = S3.scaleRotateTrans(D,C);
     S3.refined=true;
     
  }
}

void mousePressed() {
  if (keyPressed&&key=='1' && animate==false){
    S0.empty();
    S0.addPt(Mouse());
  }else if(keyPressed&&key=='2' && animate==false){
    S1.empty();
    S1.addPt(Mouse()); 
  }else if(keyPressed&&key=='3' && animate==false){
    S2.empty();
    S2.addPt(Mouse()); 
  }else if(keyPressed&&key=='4' & animate==false){
    S3.empty();
    S3.addPt(Mouse());
  }else if(d(Mouse(),A)<cornerRad){
    A.setTo(Mouse());
  }else if(d(Mouse(),D)<cornerRad){
    D.setTo(Mouse());
  }else if(d(Mouse(),B)<cornerRad){
    B.setTo(Mouse());
  }else if(d(Mouse(),C)<cornerRad){
    C.setTo(Mouse());
  }
}

void mouseDragged() {
  if (keyPressed&&key=='1' && animate==false){ 
    S0.addPt(Mouse());
  }else if(keyPressed&&key=='2' && animate==false){
    S1.addPt(Mouse()); 
  }else if(keyPressed&&key=='3' && animate==false){
    S2.addPt(Mouse()); 
  }else if(keyPressed&&key=='4' && animate==false){
    S3.addPt(Mouse());
  }else if(d(Mouse(),A)<cornerRad){
    A.setTo(Mouse());
  }else if(d(Mouse(),D)<cornerRad){
    D.setTo(Mouse());
  }else if(d(Mouse(),B)<cornerRad){
    B.setTo(Mouse());
  }else if(d(Mouse(),C)<cornerRad){
    C.setTo(Mouse());
  }
  
  if (keyPressed) {
      if (key=='t'){ S0.dragAll(); S1.dragAll();S2.dragAll();S3.dragAll();} // move all vertices
      if (key=='z'){ S0.scaleAllAroundCentroid(Mouse(),Pmouse());S1.scaleAllAroundCentroid(Mouse(),Pmouse());} // scale all vertices with respect to their center of mass
      }
}  



 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS3451 Fall 2012, Project 3 template: Sketch05 Final", 
       name ="Joon Ki Hong",
       menu="1+mouse:drawS0, 2+mouse:drawS1, 3+mouse:drawS2, 4+mouse:drawS3, a:toggleanimate, f:tuck, l:load, s:save",
       guide="click&drag mouse over corner to adjust corners. Stop animation to redraw strokes!"; // help info

