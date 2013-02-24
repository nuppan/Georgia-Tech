// sketch014: Template for project 3, Sketch01 Subdivision 
// Author(s): Jarek ROSSIGNAC *** Joon Ki Hong
// Class: CS3451 
// Last update on: October 4, 2012
// Usage: pres and draw, Press v to create subdivided curve
// Keep pressing v to show further subdivided curves. The most recent
// subdivided curve is painted as black where as the previous subdivided
// curve is green.
// Contains: point and vector classes
// Demonstrates: Curve subdivision

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true, animate=false, showAcceleration=true;
STROKES P = new STROKES(); //Drawn curve
STROKES U = new STROKES(); //Subdivided curve
int f=0;
boolean firstTime; //indicates first time subdividing

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(600, 600);            // window size
  smooth();
  frameRate(30);
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  P.empty();
  firstTime = true;
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(green,2);
  if(keyPressed&&key=='f') {P.tuck(.5); U.tuck(.5); }
  if(keyPressed&&key=='d') {P.tuck(.5); P.tuck(-.5); U.tuck(.5); U.tuck(-.5);}
  P.draw();
  pen(black,2);
  U.draw();
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
  if(key=='v'){ //subdivide
    if(firstTime==false){
      U.shiftLeft(150); //shift the subdivided curve to the left (now represents green curve)
      P = U;
    }
    U = P.refine(); //subdivide green curve and shift right
    U.shiftRight(150);
    firstTime = false; //no longer first time
  }
  if(key=='r'){
    P.resample(40);
  }
}

void mouseReleased(){ //tuck 10 times after the curve is drawn
  for(int i=0;i<10;i++){
    P.tuck(.5);
    P.tuck(-.5);
  } 
}

void mousePressed() {if (!keyPressed){ P.empty(); U.empty(); firstTime=true; P.addPt(Mouse()); }} //Drawing new curve to be subdivided
  
void mouseDragged() {
  if (!keyPressed){
    //if( d(P.getLast(),Mouse() )>20 ){ *TEMPORARY TEST*
      P.addPt(Mouse());
    //}
  }
  if (keyPressed) {
      if (key=='t') P.dragAll(); // move all vertices
      if (key=='z') P.scaleAllAroundCentroid(Mouse(),Pmouse()); // scale all vertices with respect to their center of mass
      }
  }  


 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS3451 Fall 2012, Project 3 template: Sketch01 Subdivision", 
       name ="Joon Ki Hong",
       menu="mouseDrag: draw stroke, v:subdivide",
       guide="click&drag mouse to draw the stroke. Press v to subdivide."; // help info

