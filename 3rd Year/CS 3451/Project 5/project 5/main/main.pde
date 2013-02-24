import peasy.*;
import processing.opengl.*;                // load OpenGL libraries and utilities
import javax.media.opengl.*; 
import javax.media.opengl.glu.*; 
import java.nio.*;
GL gl; 
GLU glu; 

pt F = P(0,0,0); pt T = P(0,0,0); pt E = P(0,0,1000); vec U=V(0,1,0);  // focus  set with mouse when pressing ';', eye, and up vector
pt Q=P(0,0,0); vec I=V(1,0,0); vec J=V(0,1,0); vec K=V(0,0,1); // picked surface point Q and screen aligned vectors {I,J,K} set when picked

//Camera
PeasyCam cam;
int windowHeight;
int windowWidth;

// Model View Parameters
vec X; vec Y; vec Z;
pt modelOrigin;

//Local View Parameters
pt localOrigin;

//Models and solid
pt s1Position;
pt s2Position;
pt s3Position;
pt s4Position;
Solid solid_01;
Solid solid_02;
Solid solid_03;
Solid solid_04;
Solid currentSolid;

boolean manualTime = true;
boolean morph4;
boolean showMesh;
int meshMode;
boolean morph2;
float t;

void initView() {
  X=V(1, 0, 0); Y=V(0, 1, 0); Z=V(0, 0, 0);
  I=V(1, 0, 0); J=V(0, 1, 0); K=V(0, 0, 1);
  modelOrigin = P(0, 0, 0);
  windowHeight = 600;
  windowWidth = 1024;
}

void setup() {
  //Initialize views and colors
  initView();
  setColors();
  
  //Peasy Cam Setup
  size(windowWidth, windowHeight, P3D);
  cam = new PeasyCam(this, 0, 0, 0, 0);
  cam.setMinimumDistance(200);
  cam.setMaximumDistance(500);
  //cam.setActive(false); //DISABLED FREE LOOK CAMERA FOR DEBUGGING

  //Initialize models and set current model(side profiles for models/solids)
  s1Position = P(-120,0,0);
  s2Position = P(-50,-30,0);
  s3Position = P(30,-30,0);
  s4Position = P(180,0,0);
  solid_01 = new Solid(1,P(s1Position));
  solid_02 = new Solid(2,P(s2Position));
  solid_03 = new Solid(3,P(s3Position));
  solid_04 = new Solid(4,P(s4Position));
  currentSolid = solid_01; //Default starting solid
  loadParams();
  showMesh = true;
  meshMode = 0;
  morph2 = true;
  morph4 = false;
  t=0;
}
void draw() {
  background(255);
  if (showMesh){
    fill(orange);
    solid_01.drawMesh(meshMode);
    solid_02.drawMesh(meshMode);
    solid_03.drawMesh(meshMode);
    solid_04.drawMesh(meshMode);
    noFill();
  }else{
    stroke(black);
    solid_01.drawSolidSweep();
    solid_02.drawSolidSweep();
    solid_03.drawSolidSweep();
    solid_04.drawSolidSweep();
    noStroke();
  }
  if(morph4){
    if(t<1){
      morph2Solids(solid_01.mesh,solid_02.mesh,t);
    }
    else if (t<2){
      morph2Solids(solid_02.mesh,solid_03.mesh,t-1);
    }
    else if (t<3){
       morph2Solids(solid_03.mesh,solid_04.mesh,t-2);
    }
    if(t>=3){t=0;}
  }
  else if (morph2){
    if (t<1){
       morph2Solids(solid_01.mesh,solid_02.mesh,t);
    }  
    if(t>=1){t=0;}
  }
  
  if (!manualTime){
    t+=0.01;
  }

  
  drawSolidProfile(); //Draw 2-D solid profile w/ 2-D coordinate system
}

void mouseDragged(){
  if(keyPressed&&key=='a') {cam.setActive(false);currentSolid.dragProfilePoint();} //Drag a single profile vertex
  if(keyPressed&&key=='o') {currentSolid.dragDx();};
  if(keyPressed&&key=='p') {currentSolid.dragDy();};
}

void mouseReleased(){
  if (keyPressed&&key=='d'){currentSolid.deleteProfilePt(mouseX, mouseY);} //delete a point if click when mouse is hovering the point
}

void keyReleased(){
  if(key=='i') {currentSolid.insertProfilePoint();}  //Solid profile vertex insertion
  if(key=='1') {currentSolid = solid_01;}            //Switch between solids
  if(key=='2') {currentSolid = solid_02;}
  if(key=='3') {currentSolid = solid_03;}
  if(key=='4') {currentSolid = solid_04;}
  if(key=='C') {currentSolid.makeConvex();}
  if(key==',') {currentSolid.decreaseK();}
  if(key=='.') {currentSolid.increaseK();}
  if(key=='m') {showMesh = !showMesh;}
  if(key=='v') {morph2=!morph2; morph4 = false;}
  if(key=='g') {meshMode++;if(meshMode>2)meshMode=0;}
  if(key=='W') {writeParams();}
  if(key=='a') {cam.setActive(true);}
  if(key=='h') {morph2 = false; morph4 = !morph4;} 
  if(key=='y') {manualTime = !manualTime;}
  if(key=='u') {t +=0.01; if(t>1){t=0;}}
  
  
}

//Draw Profiles
void drawSolidProfile(){
  cam.beginHUD(); //Draw relative to screen (Models and text)
  //show global coordinate system scaled by 6
  strokeWeight(5);
  show(modelOrigin, windowHeight, X, Y, Z);
  strokeWeight(1);
  currentSolid.drawProfile();   //show editable profile
  //show coordinate system labels
  textSize(20);
  fill(green);
  text("Y",5,windowHeight/2,0);
  fill(red);
  text("X",windowWidth/2,20,0);
  fill(black);
  text("Solid "+currentSolid.solidID,5,20,0);
  noFill();
  cam.endHUD(); //End drawing relative to screen
}

class MorphElement{pt A,B,C,V,a1,a2,b1,b2;
  MorphElement(pt A, pt B, pt C, pt V, int type){
    if(type==0){this.A=P(A);this.B=P(B);this.C=P(C);this.V=P(V);}
    else{a1=A;a2=B;b1=C;b2=V;}
  }
}

void morph2Solids(Mesh s1, Mesh s2, float t){
   ArrayList<MorphElement> AtoB = morphMesh(s1,s2);
   ArrayList<MorphElement> BtoA = morphMesh(s2,s1);
   ArrayList<MorphElement> edges = morphEdge(s1,s2);
   for(int i=0;i<AtoB.size();i++){
     pt Alerp = L(AtoB.get(i).A,AtoB.get(i).V,t);
     pt Blerp = L(AtoB.get(i).B,AtoB.get(i).V,t);
     pt Clerp = L(AtoB.get(i).C,AtoB.get(i).V,t);
     pt V = AtoB.get(i).V;
     fill(green);
     beginShape(TRIANGLES);
     vertex(Alerp);vertex(Blerp);
     vertex(Clerp);endShape();
     noFill();
     //show(Alerp,1);show(Blerp,1);show(Clerp,1);//show(V,2);
   }
   for(int i=0;i<BtoA.size();i++){
     pt A2lerp = L(BtoA.get(i).A,BtoA.get(i).V,1-t);
     pt B2lerp = L(BtoA.get(i).B,BtoA.get(i).V,1-t);
     pt C2lerp = L(BtoA.get(i).C,BtoA.get(i).V,1-t);
     pt V2 = BtoA.get(i).V;
     fill(red);
     beginShape(TRIANGLES);
     vertex(A2lerp);vertex(B2lerp);
     vertex(C2lerp);endShape();
     noFill();
     //show(A2lerp,1);show(B2lerp,1);show(C2lerp,1);//show(V2,2);
     
   }
   //morph edges
   for(int i=0;i<edges.size();i++){
     pt a1 = L(edges.get(i).a1,edges.get(i).b1,t);
     pt a2 = L(edges.get(i).a2,edges.get(i).b1,t);
     pt a3 = L(edges.get(i).a1,edges.get(i).b2,t);
     pt a4 = L(edges.get(i).a2,edges.get(i).b2,t);
     
     fill(blue);
     beginShape(QUADS);vertex(a1);vertex(a3);
     vertex(a2);vertex(a1);
     vertex(a2);vertex(a4);
     vertex(a1);vertex(a4);endShape();
     noFill();
     
   }
}

ArrayList<MorphElement> morphMesh (Mesh s1, Mesh s2){
  ArrayList<MorphElement> ret = new ArrayList<MorphElement>();
  for(int t=0;t<s1.nt;t++){ //for each triangle in s1
    vec tNormal = s1.triNormal(t); //get the triangle's normal
    for(int v=0;v<s2.nv;v++){ //for each vertex in s2
      int c = s2.getCorner(v);int cStart = c; //get corner for vertex and record starting corner
      
      vec vertexEdges[] = new vec[s2.nv*(s2.nv-1)/2];int nv=0;
      do{ // get all edges around v
        vertexEdges[nv++] = V(s2.g(c),s2.g(s2.n(c)));
        c = s2.s(c);
      }while(c!=cStart);
      boolean goodTri=true;
      for(int e=0;e<nv;e++){
        if(d(vertexEdges[e],tNormal)<=0){goodTri=false;}
      }
      if(goodTri==false){
        for(int e=0;e<nv;e++){
          goodTri=true;
          if(d(vertexEdges[e],tNormal)>=0){goodTri=false;}
        } 
      }
      if(goodTri==true){
        pt[] triVertices = s1.getVertices(t);
        ret.add(new MorphElement(triVertices[0],triVertices[1],triVertices[2],s2.g(cStart),0));
      }
    }
  }
  return ret;
}

ArrayList<MorphElement> morphEdge (Mesh s1, Mesh s2){
  ArrayList<MorphElement> ret = new ArrayList<MorphElement>();
  for(int c=0;c<s1.nc;c++){//iterate through corners of s1
    if(c<s1.o(c)){
      pt corner1 = P(s1.g(c)); pt corner1n = P(s1.g(s1.n(c))); pt corner1p = P(s1.g(s1.p(c)));pt corner1o = P(s1.g(s1.o(c)));
      for(int c2=0;c2<s2.nc;c2++){
        if(c2<s2.o(c2)){
          pt corner2 = P(s2.g(c2)); pt corner2n = P(s2.g(s2.n(c2))); pt corner2p = P(s2.g(s2.p(c2))); pt corner2o = P(s2.g(s2.o(c2)));
          if(edgeCheck(corner1,corner1n,corner1p,corner1o,corner2,corner2n,corner2p,corner2o)==true){
             ret.add(new MorphElement(corner1n,corner1p,corner2n,corner2p,1));
          }
        }
      } 
    }
  }
  return ret; 
}

boolean edgeCheck(pt a1, pt b1, pt c1, pt d1, pt a2, pt b2, pt c2, pt d2){
  vec normA = N(N(V(a1,b1),V(a1,c1)),V(b1,c1));
  
  vec normA2 = N(N(V(d1,c1),V(d1,b1)),V(c1,b1));
  vec normB = N(N(V(a2,b2),V(a2,c2)),V(b2,c2));
  vec normB2 = N(N(V(d2,c2),V(d2,b2)),V(c2,b2));
  vec biNorm = N(V(b1,c1),V(b2,c2));
  if(d(biNorm,normA)<=0 && d(biNorm,normA2)<=0 && d(biNorm,normB)<=0 && d(biNorm,normB2)<=0){
    return true; 
  }
   if(d(biNorm,normA)>=0 && d(biNorm,normA2)>=0 && d(biNorm,normB)>=0 && d(biNorm,normB2)>=0){
    return true; 
  }
  return false;
}

//Writes the parameters of all the solids to a project5params.txt in data folder
//hacky
void writeParams(){
  Curve profile_01 = solid_01.profile;
  Curve profile_02 = solid_02.profile;
  Curve profile_03 = solid_03.profile;
  Curve profile_04 = solid_04.profile;
  int lineNums = profile_01.n + profile_02.n + profile_03.n + profile_04.n + 19;
  String[] dataFile = new String[lineNums];
  int currLine = 0;
  
  dataFile[currLine++] = "Solid 01:";
  dataFile[currLine++] = "Profile Points:";
  for (int i = 0; i < profile_01.n; i++){
    dataFile[currLine++] = str(profile_01.P[i].x)+","+str(profile_01.P[i].y)+","+str(profile_01.P[i].z);
  }
  dataFile[currLine++] = "K: " + solid_01.k;
  dataFile[currLine++] = "D: " + solid_01.direction.x +"," + solid_01.direction.y + "," + solid_01.direction.z;
  dataFile[currLine++] = "\n";
  
  dataFile[currLine++] = "Solid 02";  
  dataFile[currLine++] = "Profiles Points:";
  for (int i = 0; i < profile_02.n; i++){
    dataFile[currLine++] =  str(profile_02.P[i].x)+","+str(profile_02.P[i].y)+","+str(profile_02.P[i].z);   
  }
  dataFile[currLine++] = "K: " + solid_02.k;
  dataFile[currLine++] = "D: " + solid_02.direction.x +"," + solid_02.direction.y + "," + solid_02.direction.z;
  dataFile[currLine++] = "\n";
  
  dataFile[currLine++] = "Solid 03";  
  dataFile[currLine++] = "Profiles Points:";
  for (int i = 0; i < profile_03.n; i++){
    dataFile[currLine++] =  str(profile_03.P[i].x)+","+str(profile_03.P[i].y)+","+str(profile_03.P[i].z);   
  }
  dataFile[currLine++] = "K: " + solid_03.k;
  dataFile[currLine++] = "D: " + solid_03.direction.x +"," + solid_03.direction.y + "," + solid_03.direction.z;
  dataFile[currLine++] = "\n";
  
  dataFile[currLine++] = "Solid 04";  
  dataFile[currLine++] = "Profiles Points:";
  for (int i = 0; i < profile_04.n; i++){
    dataFile[currLine++] =  str(profile_04.P[i].x)+","+str(profile_04.P[i].y)+","+str(profile_04.P[i].z);   
  }
  dataFile[currLine++] = "K: " + solid_04.k;
  dataFile[currLine++] = "D: " + solid_04.direction.x +"," + solid_04.direction.y + "," + solid_04.direction.z;
  
  saveStrings("data/project5params.txt", dataFile);  
}

void loadParams(){
  String[] data = loadStrings("data/project5params.txt");
  solid_01.empty();
  solid_02.empty();
  solid_03.empty();
  solid_04.empty();
  int curr = 1;
  int count = 0;
  while (curr < data.length){
    if (data[curr].equals("Profile Points:")){
      count++;
      curr++;
      while (data[curr].charAt(0) != 'K'){
        int comma1 = data[curr].indexOf(',');
        float x = float(data[curr].substring(0, comma1));
        String rest = data[curr].substring(comma1+1);
        int comma2 = rest.indexOf(',');
        float y = float(rest.substring(0, comma2));
        float z = float(rest.substring(comma2+1));
        if (count == 1){
          solid_01.profile.append(P(x,y,z));
        }
        else if (count == 2){
          solid_02.profile.append(P(x,y,z));  
        }
        else if (count == 3){
          solid_03.profile.append(P(x,y,z));  
        }
        else{
          solid_04.profile.append(P(x,y,z));  
        }
        curr++;
      }
      int loadedK = Integer.parseInt(data[curr++].substring(3));
      int comma1 = data[curr].indexOf(',');
      float x = float(data[curr].substring(3, comma1));
      String rest = data[curr].substring(comma1+1);
      int comma2 = rest.indexOf(',');
      float y = float(rest.substring(0, comma2));
      float z = float(rest.substring(comma2+1));
      if (count == 1){
        solid_01.k = loadedK;
        solid_01.direction = V(x,y,z);
      }
      else if (count == 2){
        solid_02.k = loadedK;
        solid_02.direction = V(x,y,z);
      }
      else if (count == 3){
        solid_03.k = loadedK;
        solid_03.direction = V(x,y,z);
      }
      else if (count == 4){
        solid_04.k = loadedK;
        solid_04.direction = V(x,y,z); 
      }
    }
    curr++;
  }
  solid_01.createMesh();
  solid_02.createMesh();
  solid_03.createMesh();
  solid_04.createMesh();
}
