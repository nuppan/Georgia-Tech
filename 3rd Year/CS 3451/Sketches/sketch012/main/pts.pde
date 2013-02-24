class pts {
 int nv=0;
 int pv = 0;                              // picked vertex 
 int maxnv = 1024;                         //  max number of vertices
 pt[] G = new pt [maxnv];                 // geometry table (vertices)
  pts() {}
  pts declare() {for (int i=0; i<maxnv; i++) G[i]=P(); return this;}               // init points
  pts empty() {nv=0; nv=0; return this;}
  pts resetOnCircle(int k) { // init the points to be on a circle
    pt C = ScreenCenter(); 
    for (int i=0; i<k; i++) addPt(R(T(C,V(0,-width/3)),2.*PI*i/k,C));
    return this;
    } 
  pts makeGrid (int w) { // make a 2D grid of w x w vertices
   for (int i=0; i<w; i++) for (int j=0; j<w; j++)  
     addPt(P(.7*height*j/(w-1)+.1*height,.7*height*i/(w-1)+.1*height));
   return this;
   }    
  pts addPt(pt P) { G[nv].setTo(P); pv=nv; nv++;  return this;}
  pts addPt(float x,float y) { G[nv].x=x; G[nv].y=y; pv=nv; nv++; return this;}
  pts deletePickedPt() {for(int i=pv; i<nv; i++) G[i].setTo(G[i+1]); pv=max(0,pv-1); nv--;  return this;}
  pts setPt(pt P, int i) { G[i].setTo(P); return this;}
  pts labels() {
    for (int v=0; v<nv; v++) { 
      fill(white); show(G[v],13); fill(black); 
      if(v<10) label(G[v],str(v));  else label(G[v],V(-5,0),str(v)); 
      }
    noFill();
    return this;
    }
  pts showPicked() {show(G[pv],13); return this;}
  pts draw() {for (int v=0; v<nv; v++) show(G[v],13);return this;}
  void pickClosest(pt M) {pv=0; for (int i=1; i<nv; i++) if (d(M,G[i])<d(M,G[pv])) pv=i;}

  pt Centroid() {pt C=P(); for (int i=0; i<nv; i++) C.add(G[i]); return S(1./nv,C);}
  
  pts dragPicked() { G[pv].moveWithMouse(); return this;}      // moves selected point (index p) by amount mouse moved recently
  pts dragAll() { for (int i=0; i<nv; i++) G[i].moveWithMouse(); return this;}      // moves selected point (index p) by amount mouse moved recently
  pts moveAll(vec V) {for (int i=0; i<nv; i++) G[i].add(V); return this;};   

  pts rotateAll(float a, pt C) {for (int i=0; i<nv; i++) G[i].rotate(a,C); return this;}; // rotates points around pt G by angle a
  pts rotateAllAroundCentroid(float a) {rotateAll(a,Centroid()); return this;}; // rotates points around their center of mass by angle a
  pts rotateAll(pt G, pt P, pt Q) {rotateAll(angle(V(G,P),V(G,Q)),Centroid()); return this;}; // rotates points around G by angle <GP,GQ>
  pts rotateAllAroundCentroid(pt P, pt Q) {rotateAll(Centroid(),P,Q); return this;}; // rotates points around their center of mass G by angle <GP,GQ>

  pts scaleAll(float s, pt C) {for (int i=0; i<nv; i++) G[i].translateTowards(s,C); return this;};  
  pts scaleAllAroundCentroid(float s) {scaleAll(s,Centroid()); return this;};
  pts scaleAllAroundCentroid(pt M, pt P) {pt C=Centroid(); float m=d(C,M),p=d(C,P); scaleAll((p-m)/p,C); return this;};

  pts fitToCanvas() {  // translates and scales mesh to fit canvas
     float sx=100000; float sy=10000; float bx=0.0; float by=0.0; 
     for (int i=0; i<nv; i++) {
       if (G[i].x>bx) {bx=G[i].x;}; if (G[i].x<sx) {sx=G[i].x;}; 
       if (G[i].y>by) {by=G[i].y;}; if (G[i].y<sy) {sy=G[i].y;}; 
       }
     for (int i=0; i<nv; i++) {
       G[i].x=0.93*(G[i].x-sx)*(width)/(bx-sx)+23;  
       G[i].y=0.90*(G[i].y-sy)*(height-100)/(by-sy)+100;
       } 
     return this;
     }   
     
 void savePts() {
  String savePath = selectOutput("Select or specify file name where the points will be saved");  // Opens file chooser
  if (savePath == null) {println("No output file was selected..."); return;}
  else println("writing to "+savePath);
  savePts(savePath);
  }

void savePts(String fn) {
  String [] inppts = new String [nv+1];
  int s=0;
  inppts[s++]=str(nv);
  for (int i=0; i<nv; i++) {inppts[s++]=str(G[i].x)+","+str(G[i].y);}
  saveStrings(fn,inppts);
  };
  
void loadPts() {
  String loadPath = selectInput("Select file to load");  // Opens file chooser
  if (loadPath == null) {println("No input file was selected..."); return;}
  else println("reading from "+loadPath); 
  loadPts(loadPath);
 }

void loadPts(String fn) {
  println("loading: "+fn); 
  String [] ss = loadStrings(fn);
  String subpts;
  int s=0;   int comma, comma1, comma2;   float x, y;   int a, b, c;
  nv = int(ss[s++]); print("nv="+nv);
  for(int k=0; k<nv; k++) {
    int i=k+s; 
    comma=ss[i].indexOf(',');   
    x=float(ss[i].substring(0, comma));
    y=float(ss[i].substring(comma+1, ss[i].length()));
    G[k].setTo(x,y);
    };
  pv=0;
  }; 
  

 }  // end class pts
