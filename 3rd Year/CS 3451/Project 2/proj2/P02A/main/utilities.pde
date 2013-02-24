// ************************************************************************ 
// ************************************************************************ 
// ************************************************************************ 
// ************************************************************************ GRAPHICS 
void pen(color c, float w) {stroke(c); strokeWeight(w);}
void showDisk(float x, float y, float r) {ellipse(x,y,r*2,r*2);}

// ************************************************************************ IMAGES & VIDEO 
int pictureCounter=0;
PImage myFace; // picture of author's face, should be: data/pic.jpg in sketch folder
void snapPicture() {saveFrame("PICTURES/P"+nf(pictureCounter++,3)+".jpg"); }

// ************************************************************************ COLORS 
color black, grey, white, red, green, blue, yellow, cyan, magenta, orange; // predefined
int red_hue=0, yellow_hue=59, green_hue=119, cyan_hue=179, blue_hue=239, magenta_hue=299, last_hue=359;
int saturated=99, pastel=49;
int light=99, dark=59;
void defineMyColors() {
  colorMode(HSB,360,100,100,100);
  black=#000000; grey=#525867; white=#FFFFFF; // set colors using Menu >  Tools > Color Selector
  red=#FF0000; green=#00FF01; blue=#0300FF; yellow=#FEFF00; cyan=#00FDFF; magenta=#FF00FB; orange=#E88102;
  ramp = new COLOR_RAMP(color(red_hue,saturated,light),color(last_hue,pastel,light)); 
  }  
COLOR_RAMP ramp; // color ramp dispatcher 
class COLOR_RAMP {
  color c0=color(0), c1=color(255);
  COLOR_RAMP(color pc0, color pc1) {c0=pc0; c1=pc1;}
  void setTo(color pc0, color pc1) {c0=pc0; c1=pc1;}
  void setTo(int h0, int h1, int s0, int s1, int i0, int i1) {c0=color(h0,s0,i0); c1=color(h1,s1,i1);}
  void setTo(int h0, int h1, int s, int i) {c0=color(h0,s,i); c1=color(h1,s,i);}
  color at(int i, int n) {return at(float(i)/n);}
  color at(float s) {return color((1.-s)*hue(c0)+s*hue(c1),(1.-s)*saturation(c0)+s*saturation(c1),(1.-s)*brightness(c0)+s*brightness(c1));}
  }

// ************************************************************************ TEXT 
void scribe(String S, float x, float y) {fill(0); text(S,x,y); noFill();} // writes on screen at (x,y) with current fill color
void scribeHeader(String S, int i) {fill(0); text(S,10,20+i*20); noFill();} // writes black at line i
void scribeHeaderRight(String S) {fill(0); text(S,width-7.5*S.length(),20); noFill();} // writes black on screen top, right-aligned
void scribeFooter(String S, int i) {fill(0); text(S,10,height-10-i*20); noFill();} // writes black on screen at line i from bottom
void scribeAtMouse(String S) {fill(0); text(S,mouseX,mouseY); noFill();} // writes on screen near mouse
void scribeMouseCoordinates() {fill(black); text("("+mouseX+","+mouseY+")",mouseX+7,mouseY+25); noFill();}
void displayTextImage() { // Displays text and authors face on screen
    scribeHeader(title,0); scribeHeaderRight(name); 
    //image(myFace, width-myFace.width/2,25,myFace.width/2,myFace.height/2); 
    scribeFooter(guide,1); 
    scribeFooter(menu,0); 
    }


