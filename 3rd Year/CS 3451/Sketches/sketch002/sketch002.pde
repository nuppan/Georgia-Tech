// Lectures in Graphics (LiG): sketch002
// Author: Jarek Rossignac
// Subject: Demo of screen size, coordinates, mouse location, rubber-band, print mouse coordinates on canvas
// Date created/updated: August 15, 2012

float centerX, centerY; // Screen center coordinates, cannot initialize until size()
float pressedX, pressedY; // Screen center coordinates, cannot initialize until size()

void setup() {  // executed once to initialize
  size(600, 600); // canvas size
  centerX=width/2; centerY=height/2; // set screen center cooridnates
  fill(0); // color for filling shapes and text
  }
 
void draw() {  // executed at each frame
  background(255);  // clear canvas painting it with white color (255, 255, 255)
  text("Keep mouse key pressed and move mouse to see rubber band effect",10,20);
  if (mousePressed) {        // when mouse pressed, draw rubberband from screen center to mouse location
      strokeWeight(1); // thin line
      stroke(255,0,0);      // color for drawing edges and borders (Red, Green, Blue) in [0.255]
      line(centerX,centerY,mouseX,mouseY); // line from canvas center to mouase
      strokeWeight(3); // fat line
      stroke(0,255,0);     // green
      line(pressedX,pressedY,mouseX,mouseY); // line from cmouse pressed to mouse
      }
   else text("("+str(mouseX)+","+str(mouseY)+")",mouseX,mouseY);
        // otherwise print mouse coordinates next to it
  }
void mousePressed() {pressedX=mouseX; pressedY=mouseY; }

  

