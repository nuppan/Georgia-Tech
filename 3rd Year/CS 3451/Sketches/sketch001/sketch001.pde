// Lectures in Graphics (LiG): sketch001
// Author(s): Jarek Rossignac
// Subject: Demo of the structure of an interactive graphic program in Processing
// Last update on: August 15, 2012
// Usage: 
//    click and drag mouse to move disk, 
//    type upper-case letter to change what is displayed in the disk
//    press SPACE to toggle teleporting (mouseDrag) vs snapping to mouse
// Comment: Demonstrates relative (teleport) edits


// GLOBAL VARIABLES
float x, y, r;  // coordinates of center and radius of disk
char c;         // CAP letter last typed on keyboard. It will be shown in disk when mouse is pressed
boolean teleport=true;

// INITIALIZATION
void setup() {  // executed once at the beginning of the program
  size(720, 405); // open canvas with specified size
  x=width/2; y=height/2; r=13; // initialize global variables using current canvas dimensions
  }

// DISPLAY LOOP
void draw() { // executed at each frame (typically more than 30 frames per second)
  background(255);   // erase screen and paint it white
  fill(0,0,255); text("sketch001: Press CAP letter to display it, press&drag mouse to move, press SPACE to toggle snap/teleport  ",10,20);
  // MODIFIERS
  if(mousePressed) { // check modifiers: is the mouse buttom pressed?
    if(teleport) {x+=mouseX-pmouseX; y+=mouseY-pmouseY;}  // update x and y by mouse drag
    else {x=mouseX; y=mouseY;}  // snap x and y to mouse location
    } 
 
  // display model
  stroke(255,0,0); // border color = red
  strokeWeight(2); // border width = 2
  fill(color(255,255,0),100); // interior color = yellow (red+green) and translucent
  ellipse(x,y,r*2,r*2);  // paints circle(x,y,r)
  fill(0); text(c,x-4,y+5); // draws letter c centered at (x,y)
  }

// USER ACTIONS
void keyPressed() {  // executed once each time a key is pressed
  if (' '== key) teleport=!teleport;
  if ('A'<= key  && key <= 'Z') c=key; // set c if upper case letter pressed
  }
