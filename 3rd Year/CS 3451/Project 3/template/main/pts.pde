class pts {
 int maxnv = 4000; //  max number of points
 int nv=0;         // current point count
 int pv = 0;       // index of picked point 
 pt[] G = new pt [maxnv];                 // geometry table (vertices)
 
  // ******** constructors
  pts() {for (int i=0; i<maxnv; i++) G[i]=P(); pv=0;}
  
  // ******** pick, add and delete points
  pts addPt(float x,float y) { G[nv].x=x; G[nv].y=y; pv=nv; nv++; return this;}
  pts addPt(pt P) { addPt(P.x,P.y);  return this;}
  void pickClosest(pt M) {pv=0; for (int i=1; i<nv; i++) if (d(M,G[i])<d(M,G[pv])) pv=i;}
  pts deletePickedPt() {for(int i=pv; i<nv; i++) G[i].setTo(G[i+1]); pv=max(0,pv-1); nv--;  return this;}
  pts empty() {nv=0; nv=0; return this;}

  // ******** display points and their numbers
  pts draw() {beginShape(); for (int v=0; v<nv; v++) G[v].show().v(); endShape(); return this;}
  
  pts draw(int i, float r) {show(G[i],r); return this;}
  pts drawDisplaced(int i, float r) {
    if(i>0&&i<nv-1) show(P(G[i],4.,V(V(G[i],G[i-1]),V(G[i],G[i+1]))),r); 
    else show(G[i],r); 
    return this;}

  pts labels() {
    for (int v=0; v<nv; v++) { 
      fill(white); show(G[v],13); fill(black); 
      if(v<10) label(G[v],str(v)); else label(G[v],V(-5,0),str(v)); 
      }
    noFill();
    return this;
    }

  // ******** refining
   pts refine(float s) {

    return this;
    }

  
  // ******** smoothing
  pts tuck(float s) {
    pt[] S = new pt [nv]; // temporry array
    // copy of each intermediate point moved by s towards the average of its neighbors
    for (int v=1; v<nv-1; v++) S[v]=P(G[v],s,P(G[v-1],G[v+1]));  // S = G + s((P+N)/2-G)
    for (int v=1; v<nv-1; v++) G[v]=S[v]; // copy back
    return this;
    }
    
    // ******** measure
  pt Centroid() {pt C=P(); for (int i=0; i<nv; i++) C.add(G[i]); return P(1./nv,C);}
  
  // ******** move
  pts dragPicked() { G[pv].moveWithMouse(); return this;}      // moves selected point (index p) by amount mouse moved recently
  pts dragAll() { for (int i=0; i<nv; i++) G[i].moveWithMouse(); return this;}      // moves selected point (index p) by amount mouse moved recently
  pts scaleAll(float s, pt C) {for (int i=0; i<nv; i++) G[i].translateTowards(s,C); return this;};  
  pts scaleAllAroundCentroid(pt M, pt P) {pt C=Centroid(); float m=d(C,M),p=d(C,P); scaleAll((p-m)/p,C); return this;};

  // ******** archive to file
 void savePts() {
  String savePath = selectOutput("Select or specify .pts file where the points will be saved");  // Opens file chooser
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
  String loadPath = selectInput("Select .pts file to load");  // Opens file chooser
  if (loadPath == null) {println("No input file was selected..."); return;}
  else println("reading from "+loadPath); 
  loadPts(loadPath);
 }

void loadPts(String fn) {
  println("loading: "+fn); 
  String [] ss = loadStrings(fn);
  int s=0;   int comma;   float x, y;   
  nv = int(ss[s++]); print("nv="+nv);
  for(int k=0; k<nv; k++) {
    int i=k+s; 
    comma=ss[i].indexOf(',');   
    x=float(ss[i].substring(0, comma)); y=float(ss[i].substring(comma+1, ss[i].length()));
    G[k].setTo(x,y);
    };
  pv=0;
  }; 
  
 }  // end class pts
