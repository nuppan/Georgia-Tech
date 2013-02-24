// sketch003: Template for project 2, 
// Author(s): Jarek ROSSIGNAC *** put your name here
// Class: CS3451 or CS6491 (please delete the wrong class number)
// Last update on: AUgust 17, 2012
// Usage: click&drag to move points, switch between bi-linear and Neville's curves
// Contains: point and vector classes
// Demonstrates: selection, editing, LERP, Quadratic Bezier (parabola), Neville's construction through 3 points

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true, same2and1=false, animate=false, neville=false, midCross=false, bilin=true;
pts P = new pts();
float f=0, u=0.5;

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(600, 600);            // window size
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  P.empty().addPt(100,200).addPt(300,200).addPt(300,400).addPt(500,400);
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  if(keyPressed && key=='r') { }
  if(same2and1) P.G[2].setTo(P.G[1]);
  if(midCross) P.G[2].setTo(L(P.G[3],2,L(P.G[0],.5,P.G[1])));
  pt P00=P.G[0]; pt P01=P.G[1];
  pt P10=P.G[2]; pt P11=P.G[3];
  if(bilin) {   
    pen(grey,2); fill(grey); show(P00,P10); pt Q0=L(P00,u,P10); show(Q0);
    pen(cyan,2); fill(cyan);  show(P01,P11); pt Q1=L(P01,u,P11); show(Q1);
    pen(orange,2); fill(orange); show(Q0,Q1);  
    pen(green,2); fill(green); show(P00,P01);  Q0=L(P00,f,P01); show(Q0);
    pen(blue,2); fill(blue);  show(P10,P11); Q1=L(P10,f,P11); show(Q1);
    pen(red,2); fill(red); show(Q0,Q1); pt Q = L(Q0,u,Q1); 
    pen(blue,2); P.labels();
    pen(black,1); fill(red); show(Q,10);
    if(animate) {f+=0.01; if (f>=1) f=0;} 
    }
  else if(neville) {
    pen(cyan,1); show(P01,P10); fill(cyan); show(L(P01,.5,P10));
    pen(green,1); fill(green); show(P01,L(P00,2,P01)); pen(green,2); show(P00,P01); pt Q0=L(P00,f,P01); show(Q0);
    pen(blue,1);  fill(blue);  show(L(P10,-1,P11),P10); pen(blue,2); show(P10,P11); pt Q1=L(P10,f-1,P11); show(Q1);
    pen(red,2); fill(red); show(Q0,Q1); pt Q = L(Q0,f/2,Q1); 
    noFill(); pen(magenta,4); drawNeville(P00,P01,P10,P11);
    noStroke(); fill(cyan); show(L(P01,.5,P10));
    pen(blue,2); P.labels();
    pen(black,1); fill(red); show(Q,10);
    if(animate) {f+=0.02; if (f>=2) f=0;} 
    }
  else {
    pen(green,2); fill(green); show(P00,P01); pt Q0=L(P00,f,P01); show(Q0);
    pen(blue,2); fill(blue);  show(P10,P11); pt Q1=L(P10,f,P11); show(Q1);
    pen(red,2); fill(red); show(Q0,Q1); pt Q = L(Q0,f,Q1); 
    noFill(); pen(orange,4); drawBL(P00,P01,P10,P11); 
    noStroke(); fill(green); show(L(P00,.5,P01)); fill(blue); show(L(P10,.5,P11));
    pen(blue,2); P.labels();
    pen(black,1); fill(red); show(Q,10);
    if(animate) {f+=0.01; if (f>=1) f=0;} 
    }
  if(scribeText) displayTextImage();
  }

//**************************** interrupts ****************************
void keyPressed() {
  if(key=='?') scribeText=!scribeText; // toggle sign of depth offset
  if(key=='!') snapPicture(); // make a picture of the canvas
  if(key=='=') same2and1=!same2and1; // make P2=P1
  if(key=='b') {bilin=!bilin; if(bilin) neville=false;}
  if(key=='n') {neville=!neville; if(neville) bilin=false;}
  if(key=='x') midCross=!midCross; 
  if(key=='a') animate=!animate;
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
      if (key=='.') {f+=1.*float(mouseX-pmouseX)/width; u+=1.*float(mouseY-pmouseY)/width;} // adjust current frame   
      if (key=='t') P.dragAll(); // move all vertices
      if (key=='z') P.scaleAllAroundCentroid(Mouse(),Pmouse()); // scale all vertices with respect to their center of mass
      }
  }  


 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS491 Fall 2012, Project 2 template: MOTIONS AND CURVES", name ="Jarek Rossignac",
       menu="?:txt, t:tran, z:zoom, =:P1=P2, x:cross, a=animate, b:bilin, n:Neville, .:time, !:pic, Q:quit",
       guide="click&drag mouse to move points"; // help info

