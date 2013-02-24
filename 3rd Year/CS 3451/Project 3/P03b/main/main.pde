// sketch014: Template for project 3, Sketch02 Stroke Aligning
// Author(s): Jarek ROSSIGNAC *** Joon Ki Hong
// Class: CS3451 
// Last update on: Oct 3, 2012
// Usage: press r to align the user drawn curve contained by A and B
// Contains: point and vector classes
// Demonstrates: This demonstrates the scaling/rotation/translation
// of a user defined curve, confined by control points A and B.

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true, animate=false, showAcceleration=true;
pts P = new pts();
pt A = new pt(140,140);
pt B = new pt(200,200);
int f=0;

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(600, 600);            // window size
  smooth();
  frameRate(30);
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  P.empty();
  A.setRad(30); //set radius of A and B for displaying purposes
  B.setRad(30);
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(blue,2);
  if(keyPressed&&key=='f') {P.tuck(.5); }
  if(keyPressed&&key=='d') {P.tuck(.5); P.tuck(-.5);}
  P.draw();
  pen(black,1);
  A.show();
  B.show();
  text("A",A.x-3,A.y+4);
  text("B",B.x-3,B.y+4);
  if(animate) {
    noStroke(); fill(green); P.draw(f,16); 
    fill(red); if(showAcceleration) P.drawDisplaced(f,12);}
  if(animate) {f++; if (f>=P.nv) f=0;} 
  if(scribeText) displayTextImage();
  }

//**************************** interrupts ****************************
void keyPressed() {
  if(key=='?') scribeText=!scribeText; // toggle sign of depth offset
  if(key=='!') snapPicture(); // make a picture of the canvas
  if(key=='a') animate=!animate; 
  if(key=='A') showAcceleration=!showAcceleration; 
  if(key==' ') P.empty();  
  if(key=='S') P.savePts();   
  if(key=='L') P.loadPts(); 
  if(key=='s') P.savePts("data/pts");   
  if(key=='l') P.loadPts("data/pts"); 
  if(key=='Q') exit();  // quit application
  if(key=='r'){
    P = P.scaleRotateTrans(A,B); //return aligned curve bounded by A and B
  }
}

void mousePressed() {
  if (!keyPressed){ //Control point movement
    if( d(Mouse(),A)<A.rad ){
      A.moveWithMouse();
    }else if( d(Mouse(),B)<B.rad ){      
      B.moveWithMouse(); 
    }else{
      P.empty();
      P.addPt(Mouse());
    }
  } 
}
  
void mouseDragged() {
  if (!keyPressed){ //Control point movement when dragged
    if( d(Mouse(),A)<A.rad ){
      A.moveWithMouse();
    }else if( d(Mouse(),B)<B.rad ){      
      B.moveWithMouse(); 
    }else{
      P.addPt(Mouse());
    }
  } 
  if (keyPressed) {
      if (key=='t') P.dragAll(); // move all vertices
      if (key=='z') P.scaleAllAroundCentroid(Mouse(),Pmouse()); // scale all vertices with respect to their center of mass
      }
  }  


 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS3451 Fall 2012, Project 3 template: Sketch02 Stroke Aligning", 
       name ="Joon Ki Hong",
       menu="mouseDrag: Draw curve, r: Align curve against control points",
       guide="click&drag mouse to move points, press R to align"; // help info

