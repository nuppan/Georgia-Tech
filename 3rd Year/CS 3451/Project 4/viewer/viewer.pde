//*********************************************************************
//**                            3D template                          **
//**                 Jarek Rossignac, Oct  2012                      **   
//*********************************************************************
import processing.opengl.*;                // load OpenGL libraries and utilities
import javax.media.opengl.*; 
import javax.media.opengl.glu.*; 
import java.nio.*;
GL gl; 
GLU glu; 

// ****************************** GLOBAL VARIABLES FOR DISPLAY OPTIONS *********************************
Boolean 

  translucent=false,
  showSpine=true,
  showControl=true,
  showTube=true,
  showFrenetQuads=false,
  showFrenetNormal=false,
  filterFrenetNormal=true,
  showTwistFreeNormal=false, 
  showHelpText=false; 
   
// ****************************** VIEW PARAMETERS *******************************************************
pt F = P(0,0,0); pt T = P(0,0,0); pt E = P(0,0,1000); vec U=V(0,1,0);  // focus  set with mouse when pressing ';', eye, and up vector
pt Q=P(0,0,0); vec I=V(1,0,0); vec J=V(0,1,0); vec K=V(0,0,1); // picked surface point Q and screen aligned vectors {I,J,K} set when picked
void initView() {Q=P(0,0,0); I=V(1,0,0); J=V(0,1,0); K=V(0,0,1); F = P(0,0,0); E = P(0,0,1000); U=V(0,1,0); } // declares the local frames

// ******************************** GLOBE ***********************************************

PImage bg;
PImage texmap;

pt globe = P();
int sDetail = 12;  // Sphere detail setting
float globeRadius = 450;

float[] cx, cz, sphereX, sphereY, sphereZ;
float sinLUT[];
float cosLUT[];
float SINCOS_PRECISION = 0.5;
int SINCOS_LENGTH = int(360.0 / SINCOS_PRECISION);



// ******************************** PARTICLE ***********************************************
int s=0;  //vertex index
float interval = 0;
pt particleGenerator = P();
int numParticles = 0;
int partRadius = 15;
float vScalar = 0.5;
ArrayList<pt> particles = new ArrayList(60);

// ******************************** CURVES & SPINES ***********************************************
Curve C0 = new Curve(5), S0 = new Curve(), C1 = new Curve(5), S1 = new Curve();  // control points and spines 0 and 1
Curve C= new Curve(11,130,P());
int nsteps=250; // number of smaples along spine
float sd=10; // sample distance for spine
pt sE = P(), sF = P(); vec sU=V(); //  view parameters (saved with 'j'

// *******************************************************************************************************************    SETUP
void setup() {
  size(1200, 800, OPENGL); 
  texmap = loadImage("world32k.jpg");    
  initializeSphere(sDetail); 
  setColors(); sphereDetail(sDetail); 
  PFont font = loadFont("GillSans-24.vlw"); textFont(font, 20);  // font for writing labels on //  PFont font = loadFont("Courier-14.vlw"); textFont(font, 12); 
  // ***************** OpenGL and View setup
  glu= ((PGraphicsOpenGL) g).glu;  PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;  gl = pgl.beginGL();  pgl.endGL();
  initView(); // declares the local frames for 3D GUI

  // ***************** Load Curve
  C.declarePoints();
  C.loadPts();
   
  // ***************** Set Particle
  pt first = C.P[0];
  
  // ***************** Set Particle Generator
  particleGenerator.set(first.x+100,first.y+100,first.z);
  particleGenerator.setRadius(100);
  particleGenerator.setInterval(5);
  
  // ***************** Set Globe
  globe.set(first.x+40,first.y-400,first.z+200);
  
  // ***************** Set view
 
  F=P(); E=P(0,0,500);
}

  
// ******************************************************************************************************************* DRAW      
void draw() {  
  background(white);
  // -------------------------------------------------------- Help ----------------------------------
  if(showHelpText) {
    camera(); // 2D display to show cutout
    lights();
    fill(black); writeHelp();
    return;
    } 
    
  // -------------------------------------------------------- 3D display : set up view ----------------------------------
  camera(E.x, E.y, E.z, F.x, F.y, F.z, U.x, U.y, U.z); // defines the view : eye, ctr, up
  vec Li=U(A(V(E,F),0.1*d(E,F),J));   // vec Li=U(A(V(E,F),-d(E,F),J)); 
  directionalLight(255,255,255,Li.x,Li.y,Li.z); // direction of light: behind and above the viewer
  specular(255,255,0); shininess(5);
  
  // -------------------------- display and edit control points of the spines and box ----------------------------------   
    if(pressed) {
     if (keyPressed&&(key=='a'||key=='s')) {
       fill(white,0); noStroke(); if(showControl) C.showSamples(20);
       C.pick(Pick());
       }
     }
    if (keyPressed&&(key=='z' || key=='i')) {
       fill(white,0); noStroke(); if(showControl) C.showSamples(20);
       C.pick(Pick());
     } 
     
  // -------------------------------------------------------- create control curves  ----------------------------------
   C0.empty().appendN(C); 

   // -------------------------------------------------------- create and show spines  ----------------------------------      
   //refine the velocity curve and compute normals
   S0 = C;
   while (S0.n < 200){     
     S0=S0.refine();
   }
     S0=S0.prepareSpine(0);

   
   //draw velocity curve
   stroke(blue); 
   noFill(); 
   if(showSpine) S0.drawEdges(); 
   
   //Show velocity tangents
   //stroke(red);
  // for(int i=0;i<S0.n;i++){
     //show(S0.P[i],100.0,S0.V[i]); 
  // }
   fill(black);
  
  //--------------------------------------------------------- Show Globe --------------------------------
  renderGlobe();
  
  // -------------------------------------------------------- graphic picking on surface and view control ----------------------------------   
  if (keyPressed&&key==' ') T.set(Pick()); // sets point T on the surface where the mouse points. The camera will turn toward's it when the ';' key is released
  SetFrame(Q,I,J,K);  // showFrame(Q,I,J,K,30);  // sets frame from picked points and screen axes
  // rotate view 
  if(!keyPressed&&mousePressed) {E=R(E,  PI*float(mouseX-pmouseX)/width,I,K,F); E=R(E,-PI*float(mouseY-pmouseY)/width,J,K,F); } // rotate E around F 
  if(keyPressed&&key=='D'&&mousePressed) {E=P(E,-float(mouseY-pmouseY),K); }  //   Moves E forward/backward
  if(keyPressed&&key=='d'&&mousePressed) {E=P(E,-float(mouseY-pmouseY),K);U=R(U, -PI*float(mouseX-pmouseX)/width,I,J); }//   Moves E forward/backward and rotatees around (F,Y)
   
  // -------------------------------------------------------- Disable z-buffer to display occluded silhouettes and other things ---------------------------------- 
  hint(DISABLE_DEPTH_TEST);  // show on top
  stroke(black); if(showControl) {C0.showSamples(8);}
  // -------------------------------------------------------- Computing/Drawing Particle here! ---------------------------------- 

  //set color for particles
  fill(green);
  noStroke();
  
   //Add or stop particle generation depending on limits
  if(interval>=particleGenerator.interval && numParticles<=20){
    pt randomParticle = particleGenerator.randomParticle(partRadius);
   /* pt testP = P(0, 0, 0);
    pt testP2 = P(100, 0, 100);
    testP.radius = partRadius;
    testP2.radius = partRadius;
    testP.setV(V(10, 0, 10));
    testP2.setV(V(-10, 0, -10));*/

   s = S0.closestVertexID(randomParticle);
    float distance = abs( d( randomParticle,S0.P[s] ) );
    randomParticle.setV(S0.V[s].mul(200/distance));
    particles.add(randomParticle);
    //particles.add(testP);
    //particles.add(testP2);
    numParticles++;
    interval = 0;
  }
  interval+=1;
  
  //Calculate the new velocity for each particle
  for(int i=0;i<numParticles;i++){
    pt curr = (pt)particles.get(i);
    s = S0.closestVertexID(curr);
    pt closestPoint = S0.P[s];
    float distance = abs(d(curr,closestPoint));
    if(s==S0.n-1){
      //delete particles if at end of the curve
      particles.remove(i);
      numParticles--;
    }else{
      //show particles and displace based on distance
      vec prevV = curr.velocity;
      //vec curveV = V(200/distance, S0.V[s]); //FIX TO COS DECAY
      float maxDis = 10000;
      if (distance > maxDis){
        distance = maxDis; 
      }
      float theta = (float)((distance*(Math.PI/2))/maxDis);
      float thScalar = cos(theta)*cos(theta);
      vec curveV = V(2*thScalar, S0.V[s]);
      vec currV = prevV.mul(1-vScalar).add(curveV.mul(vScalar));
      //particles.set( i, curr.add( 4, currV) );
      //show(curr,partRadius);
      curr.setV(currV);
    }
  }
  

  
  
  // -------------------------------------------------------- Compute Collisions HERE! ---------------------------------- 
  float T=1/30.0; //inter time-frame
  float t=0;
  float result[] = new float[3];
  while(t<T){
     result=computeS(T);
     if(result!=null){ // smallest t value found within current interframe
       //update two colliding particle velocities
       int i = (int)result[0];
       int j = (int)result[1];
       t = result[2];
       pt A = particles.get(i);
       pt B = particles.get(j);
       vec U = A.velocity;
       vec V = B.velocity;
       vec AB = V(A,B);
       vec N = U(AB);
       vec Un = V(d(U, N), N);
       vec Vn = V(d(V, N), N);
       U.sub(Un).add(Vn);
       V.sub(Vn).add(Un);
       //update positions of all particles
       for(int f=0;f<numParticles;f++){
         pt curr = (pt)particles.get(f);
         vec currV = ((pt)particles.get(f)).velocity;
         curr.add(t+0.01,currV);
       }
      // println("T: "+T);
      // println("t: "+t);
       T=-1;
       t=0;
     }else{ //No collision within remaining interframe
       for(int i=0;i<numParticles;i++){
         pt curr = (pt)particles.get(i);
         vec currV = ((pt)particles.get(i)).velocity;
         curr.add(T,currV);
       }
       T=-1;
     }  
  }

  for(int z=0;z<numParticles;z++){
    pt curr = particles.get(z);
    show(curr,curr.radius);
  }
    
  
  
  
  // -------------------------------------------------------- END Compute Collisions ---------------------------------- 
  
  

 
  
  //show particle generator
  fill(cyan);
  show(particleGenerator,particleGenerator.radius);
  
  // -------------------------------------------------------- WRITING TEXT ---------------------------------- 
  camera(); // 2D view to write help text
  writeFooterHelp(vScalar);
  hint(ENABLE_DEPTH_TEST); // show silouettes
 

  // -------------------------------------------------------- SNAP PICTURE ---------------------------------- 
   if(snapping) snapPicture(); // does not work for a large screen
    pressed=false;

 } 
// ****************************************************************************************************************************** END DRAW


// ****************************************************************************************************************************** Compute Collision S
 
 float[] computeS(float T){
   float result[] = new float[3];
   boolean found = false;
   boolean firstTime = true;
   for(int i=0;i<numParticles-1;i++){
       pt A = (pt)particles.get(i);
       vec U = A.velocity;
       for(int j=i+1;j<numParticles;j++){ //Compare all pairs of particles
         pt B = (pt)particles.get(j);
         vec V = B.velocity;
         for(float k=0;k<=T;k+=0.0001){
            pt At = P(A,k,U);
            pt Bt = P(B,k,V);
            vec AB = V(At,Bt);
            if(AB.norm() <= (A.radius+B.radius)){
              if(firstTime==true){
                result[0] = i+0.0;
                result[1] = j+0.0;
                result[2] = k;
                firstTime=false;
                found=true;
              }else if(k<result[2]){
                result[0] = i+0.0;
                result[1] = j+0.0;
                result[2] = k;
              }
            }
          }
       }
   }
   if(found==true){
     return result; 
   }
   return null;
 }
 

 
 
 // ****************************************************************************************************************************** INTERRUPTS
Boolean pressed=false;

void mousePressed() {pressed=true;}
  
void mouseDragged() {
  if(keyPressed&&key=='a') {C.dragPoint( V(.5*(mouseX-pmouseX),I,.5*(mouseY-pmouseY),K) ); } // move selected vertex of curve C in screen plane
  if(keyPressed&&key=='s') {} 
  if(keyPressed&&key=='b') {C.dragAll(0,C.n, V(.5*(mouseX-pmouseX),I,.5*(mouseY-pmouseY),K) ); } // move selected vertex of curve C in screen plane
  if(keyPressed&&key=='v') {C.dragAll(0,C.n, V(.5*(mouseX-pmouseX),I,-.5*(mouseY-pmouseY),J) ); } // move selected vertex of curve Cb in XZ
  if(keyPressed&&key=='g') {particleGenerator.add(V((mouseX-pmouseX),I,-1*(mouseY-pmouseY),J) ); } //move the generator
  if(keyPressed&&key=='x') {} // move selected vertex in screen plane
  if(keyPressed&&key=='z') {}  // move selected vertex in X/Z screen plane
  if(keyPressed&&key=='X') {} // move selected vertex in screen plane
  if(keyPressed&&key=='Z') {}  // move selected vertex in X/Z screen plane 
  if(keyPressed&&key=='i') {}  // insert point in curve
  if(keyPressed&&key=='I') {C.insert();}  // append point to the end of the curve 
  if(keyPressed&&key=='p') {C.dragPoint( V(.5*(mouseX-pmouseX),I,-.5*(mouseY-pmouseY),J) );}  //// move selected vertex of curve C in screen plane/
  
  if(keyPressed&&key=='q')  {} //Move particle along x-axis
  
  }

void mouseReleased() {
     U.set(M(J)); // reset camera up vector
    }
  
void keyReleased() {
   if(key==' ') F=P(T);   //   if(key=='c') M0.moveTo(C.Pof(10));
   if(key=='z' && C.n>3) {C.deletePick();}
   if(key=='i') {C.insertOnCurve();}
   if(key=='r') {particleGenerator.radius-=5;}
   if(key=='R') {particleGenerator.radius+=5;}
   U.set(M(J)); // reset camera up vector
   } 

 
void keyPressed() {
  if(key=='a') {} // drag curve control point in xz (mouseDragged)
  if(key=='b') {}  // move S2 in XZ
  if(key=='c') {} // load curve
  if(key=='d') {} 
  if(key=='e') {}
  if(key=='f') {filterFrenetNormal=!filterFrenetNormal; if(filterFrenetNormal) println("Filtering"); else println("not filtering");}
  if(key=='g') {} // change global twist w (mouseDrag)
  if(key=='h') {} // hide picked vertex (mousePressed)
  if(key=='i') {}
  if(key=='j') {}
  if(key=='k') {}
  if(key=='l') {}
  if(key=='m') {}
  if(key=='n') {}
  if(key=='o') {}
  if(key=='p') {}
  if(key=='q') {}
  if(key=='r') {}
  if(key=='s') {if(vScalar>0){vScalar-=0.025;}else{vScalar=0;}} //adjust velocity scalar value
  if(key=='t') {showTube=!showTube;}
  if(key=='u') {}
  if(key=='v') {} // move S2
  if(key=='w') {}
  if(key=='x' && C.n>3) {C.delete();} // drag mesh vertex in xy (mouseDragged)
  if(key=='y') {}
  if(key=='z') {} 

   
  if(key=='A') {C.savePts();}
  if(key=='B') {}
  if(key=='C') {C.loadPts();} // save curve
  if(key=='D') {} //move in depth without rotation (draw)
  if(key=='E') {}
  if(key=='F') {}
  if(key=='G') {}
  if(key=='H') {}
  if(key=='I') {C.insert();}
  if(key=='J') {}
  if(key=='K') {}
  if(key=='L') {}
  if(key=='M') {}
  if(key=='N') {}
  if(key=='O') {}
  if(key=='P') {}
  if(key=='Q') {exit();}
  if(key=='R') {}
  if(key=='S') {if(vScalar<1){vScalar+=0.025;}else{vScalar=1;}}
  if(key=='T') {}
  if(key=='U') {}
  if(key=='V') {} 
  if(key=='W') {}
  if(key=='X') {} // drag mesh vertex in xy and neighbors (mouseDragged)
  if(key=='Y') {}
  if(key=='Z') {} // drag mesh vertex in xz and neighbors (mouseDragged)

  if(key=='`') {}
  if(key=='~') {showSpine=!showSpine;}
  if(key=='!') {snapping=true;}
  if(key=='@') {}
  if(key=='#') {}
  if(key=='$') {} // ???????
  if(key=='%') {}
  if(key=='&') {}
  if(key=='*') {}
  if(key=='(') {}
  if(key==')') {}
  if(key=='_') {}
  if(key=='+') {} // flip edge of M
  if(key=='-') {}
  if(key=='=') {C.P[5].set(C.P[0]); C.P[6].set(C.P[1]); C.P[7].set(C.P[2]); C.P[8].set(C.P[3]); C.P[9].set(C.P[4]);}
  if(key=='{') {showFrenetQuads=!showFrenetQuads;}
  if(key=='}') {}
  if(key=='|') {}
  if(key=='[') {initView(); F.set(new pt(width/2,height/2,0)); E.set(P(F,2000,K));}
  if(key==']') {F.set(new pt(width/2,height/2,0));}
  if(key==':') {translucent=!translucent;}
  if(key==';') {showControl=!showControl;}
  if(key=='<') {}
  if(key=='>') {if (shrunk==0) shrunk=1; else shrunk=0;}
  if(key=='?') {showHelpText=!showHelpText;}
  if(key=='.') {} // pick corner
  if(key==',') {}
  if(key=='^') {} 
  if(key=='/') {} 
  //if(key==' ') {} // pick focus point (will be centered) (draw & keyReleased)

  if(key=='0') {w=0;}
//  for(int i=0; i<10; i++) if (key==char(i+48)) vis[i]=!vis[i];
  
  } 
//---------------------------------END keyPressed------------------------------------------------------


//---------------------------------RENDERING GLOBE METHODS--------------------------------------------------------

void renderGlobe() {
  pushMatrix();
  translate(globe.x,globe.y, globe.z);
  pushMatrix();
  noFill();
  stroke(255,200);
  strokeWeight(2);
  smooth();
  popMatrix();
  //lights();    
  pushMatrix();
  fill(200);
  noStroke();
  textureMode(IMAGE);  
  texturedSphere(globeRadius, texmap, globe);
  popMatrix();  
  popMatrix();


}

void initializeSphere(int res)
{
  sinLUT = new float[SINCOS_LENGTH];
  cosLUT = new float[SINCOS_LENGTH];

  for (int i = 0; i < SINCOS_LENGTH; i++) {
    sinLUT[i] = (float) Math.sin(i * DEG_TO_RAD * SINCOS_PRECISION);
    cosLUT[i] = (float) Math.cos(i * DEG_TO_RAD * SINCOS_PRECISION);
  }

  float delta = (float)SINCOS_LENGTH/res;
  float[] cx = new float[res];
  float[] cz = new float[res];
  
  // Calc unit circle in XZ plane
  for (int i = 0; i < res; i++) {
    cx[i] = -cosLUT[(int) (i*delta) % SINCOS_LENGTH];
    cz[i] = sinLUT[(int) (i*delta) % SINCOS_LENGTH];
  }
  
  // Computing vertexlist vertexlist starts at south pole
  int vertCount = res * (res-1) + 2;
  int currVert = 0;
  
  // Re-init arrays to store vertices
  sphereX = new float[vertCount];
  sphereY = new float[vertCount];
  sphereZ = new float[vertCount];
  float angle_step = (SINCOS_LENGTH*0.5f)/res;
  float angle = angle_step;
  
  // Step along Y axis
  for (int i = 1; i < res; i++) {
    float curradius = sinLUT[(int) angle % SINCOS_LENGTH];
    float currY = -cosLUT[(int) angle % SINCOS_LENGTH];
    for (int j = 0; j < res; j++) {
      sphereX[currVert] = cx[j] * curradius;
      sphereY[currVert] = currY;
      sphereZ[currVert++] = cz[j] * curradius;
    }
    angle += angle_step;
  }
  sDetail = res;
}

// Generic routine to draw textured sphere
void texturedSphere(float r, PImage t, pt origin) 
{
  int v1,v11,v2;
  r = (r + 240 ) * 0.33;
  beginShape(TRIANGLE_STRIP);
  texture(t);
  float iu=(float)(t.width-1)/(sDetail);
  float iv=(float)(t.height-1)/(sDetail);
  float u=0,v=iv;
  for (int i = 0; i < sDetail; i++) {
    vertex(origin.x, origin.y-r, origin.z,u,0);
    vertex(origin.x+sphereX[i]*r, origin.y+sphereY[i]*r, origin.z+sphereZ[i]*r, u, v);
    u+=iu;
  }
  vertex(origin.x, origin.y-r, origin.z,u,0);
  vertex(origin.x+sphereX[0]*r, origin.y+sphereY[0]*r, origin.z+sphereZ[0]*r, u, v);
  endShape();   
  
  // Middle rings
  int voff = 0;
  for(int i = 2; i < sDetail; i++) {
    v1=v11=voff;
    voff += sDetail;
    v2=voff;
    u=0;
    beginShape(TRIANGLE_STRIP);
    texture(t);
    for (int j = 0; j < sDetail; j++) {
      vertex(origin.x+sphereX[v1]*r, origin.y+sphereY[v1]*r, origin.z+sphereZ[v1++]*r, u, v);
      vertex(origin.x+sphereX[v2]*r, origin.y+sphereY[v2]*r, origin.z+sphereZ[v2++]*r, u, v+iv);
      u+=iu;
    }
  
    // Close each ring
    v1=v11;
    v2=voff;
    vertex(origin.x+sphereX[v1]*r, origin.y+sphereY[v1]*r, origin.z+sphereZ[v1]*r, u, v);
    vertex(origin.x+sphereX[v2]*r, origin.y+sphereY[v2]*r, origin.z+sphereZ[v2]*r, u, v+iv);
    endShape();
    v+=iv;
  }
  u=0;
  
  // Add the northern cap
  beginShape(TRIANGLE_STRIP);
  texture(t);
  for (int i = 0; i < sDetail; i++) {
    v2 = voff + i;
    vertex(origin.x+sphereX[v2]*r, origin.y+sphereY[v2]*r, origin.z+sphereZ[v2]*r, u, v);
    vertex(origin.x, origin.y+r, origin.z,u,v+iv);    
    u+=iu;
  }
  vertex(origin.x+sphereX[voff]*r, origin.y+sphereY[voff]*r, origin.z+sphereZ[voff]*r, u, v);
  endShape();
  
}


//---------------------------------END Globe Rendering Methods-----------------------------------------

float [] Volume = new float [3];
float [] Area = new float [3];
float dis = 0;
  
Boolean prev=false;

void showGrid(float s) {
  for (float x=0; x<width; x+=s*20) line(x,0,x,height);
  for (float y=0; y<height; y+=s*20) line(0,y,width,y);
  }
  
  // Snapping PICTURES of the screen
PImage myFace; // picture of author's face, read from file pic.jpg in data folder
int pictureCounter=0;
Boolean snapping=false; // used to hide some text whil emaking a picture
void snapPicture() {saveFrame("PICTURES/P"+nf(pictureCounter++,3)+".jpg"); snapping=false;}

 

