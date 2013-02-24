// Lectures in Graphics (LiG): sketch003
// Author(s): Jarek ROSSIGNAC                *** replace my name with yours and delete this guideline
// Subject: Template for class projects      *** replacwe with Project XXX: Project title
// Class: CS3451 or CS6491                   *** delete the wrong class number
// Last update on: August 15, 2012           *** update date of last modification
// Usage: press 'r' to see rows
//        click&drag mouse horizontally to change number of disks
// Contains: tools for using colors, images, screen (help) text                      *** update as needed
// Comments: Demonstrates loops, classes, 2D transforms                              *** update as needed

//**************************** global variables ****************************
Boolean scribeText=true; // toggle for displaying of help text
int n=20; float n_float=20; // number of disks shown, float is used to provide smooth mouse-drag edit of n
// color variables are defined in the "utilities" tab and set in "defineColors" during initialization


//**************************** initialization ****************************
void setup() {               // executed once at the begining 
  size(600, 600);            // canvas size
  myFace = loadImage("data/pic.jpg");  // load image from file pic.jpg in folder data *** replace that file with your pic of your own face
  defineMyColors(); // sets HSB color mode and THEN defines values for color variables 
  }


//**************************** display current frame ****************************
void draw() {      // executed at each frame
  background(white); // clear screen and paints white background
  pen(black,3); // sets stroke color (to balck) and width (to 3 pixels)
  float r=width/(n+3); // radius of disks initialized to fill width of screen
  if(keyPressed && key=='r') { // if a key is pressed and the last key action was to press 'r'
    pushMatrix();
      translate(r,height/2);
      for(int j=0; j<4; j++) { // shows 4 rows
        ramp.setTo(red_hue,green_hue,saturated*(j+1)/5,light); // sets a different ramp
        pushMatrix(); // save previous frame to avoid residual translation
          for(int i=0; i<n; i++) { // paints row of disks
            translate(r,0); // translate local frame to the right by r
            fill(ramp.at(i,n)); // set color using color i in a ramp of n colors 
            showDisk(0,0,r-2); // shows disk of radius r-2 at origin of local frame
            } 
        popMatrix(); // restores saved frame, canceling all translations to the right
        translate(0,r*2);
        }
    popMatrix();
    }
  else { // SHOW SPIRAL 
  
  
      // shows mouse location or whether mouse is pressed an d which key is pressed                 *** delete these 3 lines
      if (mousePressed) {fill(white); stroke(red); showDisk(mouseX,mouseY-4,12);}
      if (keyPressed) {fill(black); text(str(key),mouseX-2,mouseY);}
      if (!mousePressed && !keyPressed) scribeMouseCoordinates();

      //****************************** add your code here to display the two spirals ***************
  
  
  
      //************ submit your code (above) on paper (with your name and screen snap shot with your face) at the beginning of lecture on due date
    }

  if(scribeText) displayTextImage();
  }  // end of draw()


// User actions
void keyPressed() { // executed each time a key is pressed: sets the "keyPressed" and "key" state variables, 
                    // till it is released or another key is pressed or released
  if(key=='?') scribeText=!scribeText; // toggle display of help text and authors picture
  if(key=='!') snapPicture(); // make a picture of the canvas
  if(key=='+') {n++; n_float=n;}  // increment n and sets n_float in case it is dragged with a continuous motion of the mouse
  if(key=='-') if(n>=0) {n--; n_float=n;}  // decrements n
  if(key=='Q') exit();  // quit application
  }


void mouseDragged() { // executed when mouse is pressed and moved
  n_float+=20.*(mouseX-pmouseX)/width; // updates float value of n
  if(n_float>0) n=round(n_float);  // snaps n to closest int
  }


// EDIT WITH PROPER CLASS, PROJECT, STUDENT'S NAME, AND DESCRIPTION OF ACTION KEYS ***************************
String title ="CS6491 Fall 2012, Project 1: SHOW COLORED SPIRAL", name ="Jarek Rossignac",
       menu="?:help(on/off), !:snapPicure,  r: to see ramp, Q:quit",
       guide="press&drag mouse right/left to change the number of disks"; // help info

  
