// sketch014: Template for project 3, stroke smoothing 
// Author(s): Jarek ROSSIGNAC *** put your name here
// Class: CS3451 
// Last update on: Sept 7, 2012
// Usage: pres and draw, SPACE to erase
// Contains: point and vector classes
// Demonstrates: smoothing a sequence of points

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true, animate=false, showAcceleration=true;
pts P = new pts();
int f=0;

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(600, 600);            // window size
  smooth();
  frameRate(30);
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  P.empty();
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(blue,2);
  if(keyPressed&&key=='f') {P.tuck(.5); }
  if(keyPressed&&key=='d') {P.tuck(.5); P.tuck(-.5);}
  P.draw();
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
  }

void mousePressed() {if (!keyPressed) P.addPt(Mouse()); }
  
void mouseDragged() {
  if (!keyPressed) P.addPt(Mouse());
  if (keyPressed) {
      if (key=='t') P.dragAll(); // move all vertices
      if (key=='z') P.scaleAllAroundCentroid(Mouse(),Pmouse()); // scale all vertices with respect to their center of mass
      }
  }  


 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS491 Fall 2012, Project 3 template: Stroke Processing", 
       name ="Jarek Rossignac",
       menu="?:txt, t:tran, z:zoom, _:erase, a=animate, A:accel, f:filter, d:dual, L:load, S:save, !:pic, Q:quit",
       guide="click&drag mouse to move points"; // help info

