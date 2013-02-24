// CS3451-Fall-2012, Project P2-A: 4-point Neville interpolation
// Template for students
// Shows 3 point Neville
// Author(s): Jarek ROSSIGNAC , Joon Ki Hong, Krista Patel
// Last update on: August 30, 2012

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true;
pts P = new pts();
float f=0;

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(800, 800);            // window size
  smooth();
  //myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  P.empty().addPt(100,200).addPt(300,200).addPt(500,400).addPt(300,400);
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(green,6); drawNeville(P.G[0],P.G[1],P.G[2]);

  pen(orange,3); drawNeville(P.G[0],P.G[1],P.G[2],P.G[3]); // not written yet!!!!

  pen(black,2); P.draw().labels();
  if(scribeText) displayTextImage();
  }

//**************************** interrupts ****************************
void keyPressed() {
  if(key=='?') scribeText=!scribeText; // toggle sign of depth offset
  if(key=='!') snapPicture(); // make a picture of the canvas
  if(key=='S') P.savePts();   
  if(key=='L') P.loadPts(); 
  if(key=='s') P.savePts("data/pts");   
  if(key=='l') P.loadPts("data/pts"); 
  if(key=='Q') exit();  // quit application
  }

void mousePressed() {  // executed when the mouse is pressed
  P.pickClosest(Mouse()); // used to pick the closest vertex of C to the mouse
  if (keyPressed) {
     if (key=='a')  P.addPt(Mouse()); 
     if (key=='d')  P.deletePickedPt(); 
     }  
  }
  
void mouseDragged() {
  if (!keyPressed || (key=='a')) P.dragPicked();   // drag selected point with mouse
  if (keyPressed) {
      if (key=='.') {f+=1.*float(mouseX-pmouseX)/width;} // adjust current frame   
      if (key=='t') P.dragAll(); // move all vertices
      if (key=='z') P.scaleAllAroundCentroid(Mouse(),Pmouse()); // scale all vertices with respect to their center of mass
      }
  }  


 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS3491 Fall 2012, template for Project 2-A : Neville interpolation", name ="Joon Ki Hong, Krista Patel",
       menu="?:txt, t:tran, z:zoom, !:Snap pic, .:drag time, Q:quit",
       guide="click&drag mouse to move points"; // help info

