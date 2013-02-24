//Joon Ki Hong
//CS3451 Fall 2012
//Project 1 - Parabola w/ 3 points

//Anchor class that represents a single anchor point with coordinates x,y
class Anchor{
  int x;
  int y;
  //Constructor that sets x,y
  Anchor(int x, int y){
    this.x = x;
    this.y = y;
  }
}

//Global variables
PImage myFace;
Anchor p1,p2,p3;
//radius
int r = 26;
//time to start at
float t = -2;
//Anchor point booleans
boolean p1Move=false;
boolean p2Move=false;
boolean p3Move=false;

void setup(){
  size(720,700);
  myFace = loadImage("pic.jpeg");
  //Initialize 3 anchor points
  p1 = new Anchor(width/4,height/4);
  p2 = new Anchor(width/2,height/2);
  p3 = new Anchor(width*3/4,height/4);
}

void draw(){
  //Refresh screen with a white background
  background(255); 
  //Check if the  mouse is pressed and check if the x,y of the mouse lies
  //within the area of each anchor point (circles with radius r).
  //This uses a point circle intersection algorithm.
  if(mousePressed){
    if( ( pow(mouseX-(p1.x+r/2),2) + pow(mouseY-(p1.y+r/2),2) ) <= pow(r,2) ){
      p1Move = true;
    } 
    else if( ( pow(mouseX-(p2.x+r/2),2) + pow(mouseY-(p2.y+r/2),2) ) <= pow(r,2) ){
      p2Move = true;
    }
    else if( ( pow(mouseX-(p3.x+r/2),2) + pow(mouseY-(p3.y+r/2),2) ) <= pow(r,2) ){
      p3Move = true;
    }
  }else{
    //The mouse is not pressed and achor points are not moveable
    p1Move=false;
    p2Move=false;
    p3Move=false;
  }
  //If the mouse does intersect one of them, then set the anchor points to the 
  //mouse point.
  if(p1Move){
     p1.x = mouseX;
     p1.y = mouseY;
  }else if(p2Move){
     p2.x = mouseX;
     p2.y = mouseY;
  }else if(p3Move){
     p3.x = mouseX;
     p3.y = mouseY; 
  }
  //Redraw the parabola up to a certain incremented time
  for(float i=-2;i<=t;i+=.05){
   float x = .5*i*i*(p1.x-p2.x+p3.x-p2.x) + i*((p3.x-p1.x)/2)+p2.x;
   float y = .5*i*i*(p1.y-p2.y+p3.y-p2.y) + i*((p3.y-p1.y)/2)+p2.y;
   ellipse(x,y,5,5);
  }
  //Increment t to draw the next step
  t = t + .02;
  //Reset t after it reaches ~5 seconds
  if(t>3){
   t=-2; 
  }
  //Draw the anchor points
  stroke(0,0,0);
  strokeWeight(1);
  fill(color(255,255,255),100);
  ellipse(p1.x,p1.y,r,r);
  ellipse(p2.x,p2.y,r,r);
  ellipse(p3.x,p3.y,r,r);  
  //Draw the text on the screen
  fill(0);
  text("A",p1.x-4,p1.y+5);
  text("("+p1.x+","+p1.y+")",p1.x+6,p1.y-6);
  text("("+p2.x+","+p2.y+")",p2.x+6,p2.y-6);
  text("("+p3.x+","+p3.y+")",p3.x+6,p3.y-6);
  text("B",p2.x-4,p2.y+5);
  text("C",p3.x-4,p3.y+5);
  text("CS3451 Fall 2012, Project 1: Parabola",10,40);
  text("Joon Ki Hong",width-7.5*("Joon Ki Hong").length(),20);
  image(myFace,width-myFace.width/2,25,myFace.width/2,myFace.height/2);
  noFill();
}
