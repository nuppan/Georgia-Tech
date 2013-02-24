// sketch014: Template for project 3, Sketch03 stroke aligning
// Author(s): Jarek ROSSIGNAC *** Joon Ki Hong
// Class: CS3451 
// Last update on: Oct 3, 2012
// Usage: Press v to convert the drawn stroke into a shape object (which is drawn in black)
// with the first edge parallel to the x-axis). It then converts it back to a stroke object
// and aligned with controls points A and B in magenta.
// Contains: point and vector classes
// Demonstrates: Conversion of a stroke to a shape and back aligned.

// GLOBAL VARIABLES FOR THIS PROGRAM
Boolean scribeText=true, animate=false, showAcceleration=true;
STROKES P = new STROKES();
SHAPES U = new SHAPES();
STROKES S = new STROKES();
pt A;
pt B;
int f=0;


//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(600, 600);            // window size
  smooth();
  frameRate(30);
  A = new pt(250,250);
  B = new pt(400,400);
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data
  defineMyColors();
  P.empty();
  }

//**************************** display next frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(green,2);
  if(keyPressed&&key=='f') {P.tuck(.5);}
  if(keyPressed&&key=='d') {P.tuck(.5); P.tuck(-.5);}
  A.setRad(25);
  B.setRad(25);
  P.draw();
  pen(black,2);
  U.draw();
  A.show(); //show control points
  B.show();
  text("A",A.x-3,A.y+4);
  text("B",B.x-3,B.y+4);
  pen(magenta,2);
  S.draw();
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
  if(key=='v'){
    if(P.nv>0){ //only convert to shape and back if there is a curve
      U = P.convertShape(); //convert to shape
      S = U.convertStroke(A,B); //convert to stroke
    }
  }
}

void mouseReleased(){ //tuck 10 times after the curve is drawn
  /*for(int i=0;i<10;i++){
    P.tuck(.5);
    P.tuck(-.5);
  } */
}

void mousePressed() {
  if (!keyPressed){ //Control point movement
    if( d(Mouse(),A)<A.rad ){
      A.moveWithMouse();
    }else if( d(Mouse(),B)<B.rad ){      
      B.moveWithMouse(); 
    }else{ //Start new curve
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
      if( d(P.getLast(),Mouse() )>20 ){//Curve with distanced vertices
        P.addPt(Mouse());
      }
    }
  }
  if (keyPressed) {
      if (key=='t') P.dragAll(); // move all vertices
      if (key=='z') P.scaleAllAroundCentroid(Mouse(),Pmouse()); // scale all vertices with respect to their center of mass
      }
  }  


 // EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS
String title ="CS3451 Fall 2012, Project 3 template: Sketch03 Stroke aligning", 
       name ="Joon Ki Hong",
       menu="mouseDrag: draw stroke, v: convert to shape and back, mouseDrag on A or B: move control points",
       guide="The black curve=Shape constructed stroke; Magenta curve=Stroke constructed from shape"; // help info

