// sketch014: Template for project 3, sketch04 stroke morphing
// Author(s): Jarek ROSSIGNAC *** Joon Ki Hong
// Class: CS3451 
// Last update on: Oct 3, 2012
// Usage: Draw the first curve by holding 1 and draw the second one by holding 2.
// Hold '.' and drag your mouse from left to right to morph the curve with 0>=t>=1.
// The black curve represents the exponential interpolation of lengths. Press 'm'
// to switch the interpolation method to linear and an orange curve will replace the
// black curve to represent this change in method.
// Demonstrates: Morphing curves using lengths and angles and exponential/linear methods
// of interpolation of lengths.

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true, animate=false, showAcceleration=true;
STROKES S0 = new STROKES();
STROKES S1 = new STROKES();
STROKES St = new STROKES();
STROKES StLinear = new STROKES();
SHAPES Si0 = new SHAPES();
SHAPES Si1 = new SHAPES();
int f=0;
float t=0;
float mouseInitial = 0;
boolean linearMorph = false;

//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(800, 600);            // window size
  smooth();
  frameRate(30);
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  S0.empty();
  S1.empty();
  S0.loadPts("data/s0");S1.loadPts("data/s1");
  Si0 = S0.convertShape();
  Si1 = S1.convertShape();
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(green,2);
  if(keyPressed&&key=='f') {S0.tuck(.5);S1.tuck(.5);}
  if(keyPressed&&key=='d') {S0.tuck(.5); S0.tuck(-.5);S1.tuck(.5);S1.tuck(-.5);}
  S0.draw();
  pen(red,2);
  S1.draw();
  pen(black,2);
  St.draw();
  pen(orange,2);
  StLinear.draw();
  /*if(animate) {
    noStroke(); fill(green); S1.draw(f,16); 
    fill(red); if(showAcceleration) S1.drawDisplaced(f,12);}
  if(animate) {f++; if (f>=S1.nv) f=0;}*/ 
  if(scribeText) displayTextImage();
  }

//**************************** interrupts ****************************
void keyPressed() {
  if(key=='?') scribeText=!scribeText; // toggle sign of depth offset
  if(key=='!') snapPicture(); // make a picture of the canvas
  if(key=='a') animate=!animate; 
  if(key=='A') showAcceleration=!showAcceleration; 
  if(key==' '){ S0.empty();S1.empty();}  
  if(key=='S'){ S0.savePts();S1.savePts();}
  if(key=='L'){ S0.loadPts();S1.loadPts();}
  if(key=='s'){ S0.savePts("data/s0");S1.savePts("data/s1");}   
  if(key=='l'){ S0.loadPts("data/s0");S1.loadPts("data/s1");}
  if(key=='Q') exit();  // quit application
  if(key=='m'){linearMorph= !linearMorph;}
}

void mouseReleased(){
  S0.empty();
  S1.empty();
  S0 = Si0.convertStroke(new pt(250,300));
  S1 = Si1.convertStroke(new pt(250,300));
}

void mousePressed() {
  if (keyPressed&&key=='1'){ 
    S0.empty();
    S0.addPt(Mouse());
  }else if(keyPressed&&key=='2'){
    S1.empty();
    S1.addPt(Mouse()); 
  }else if(keyPressed&&key=='.'){
    mouseInitial = Mouse().x; 
  }
}

void mouseDragged() {
  if (keyPressed&&key=='1'){ 
    if(d(S0.G[S0.nv-1],Mouse())>10 && S0.nv<10){
        S0.addPt(Mouse());
        Si0 = S0.convertShape();
    }
  }else if(keyPressed&&key=='2'){
    if( d(S1.G[S1.nv-1],Mouse())>10 && S1.nv<10){
        S1.addPt(Mouse());
        Si1 = S1.convertShape();
    }
  }else if(keyPressed&&key=='.'){
    float diff = Mouse().x-mouseInitial;
    if(diff>0 && diff<200){
      t = diff/200;
      StLinear.empty();
      St = WAG(S0,S1,t);
      StLinear = WAGLINEAR(S0,S1,t);
    }
  }
  if (keyPressed) {
      if (key=='t'){ S0.dragAll(); S1.dragAll();} // move all vertices
      if (key=='z'){ S0.scaleAllAroundCentroid(Mouse(),Pmouse());S1.scaleAllAroundCentroid(Mouse(),Pmouse());} // scale all vertices with respect to their center of mass
      }
}  



 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS3451 Fall 2012, Project 3 template: Sketch04 Stroke Morphing", 
       name ="Joon Ki Hong",
       menu="1+mouse: draw first curve, 2+mouse: draw second curve, '.':control t to animate",
       guide="Move the mouse left and right to observe the transition while holding '.'"; // help info

