class STROKES {
 int maxnv = 4000; //  max number of points
 int nv=0;         // current point count
 int pv = 0;       // index of picked point 
 pt[] G = new pt [maxnv];                 // geometry table (vertices)
 
  // ******** constructors
  STROKES() {for (int i=0; i<maxnv; i++) G[i]=P(); pv=0;}
  
  // ******** pick, add and delete points
  STROKES addPt(float x,float y) { G[nv].x=x; G[nv].y=y; pv=nv; nv++; return this;}
  STROKES addPt(pt P) { addPt(P.x,P.y);  return this;}
  void pickClosest(pt M) {pv=0; for (int i=1; i<nv; i++) if (d(M,G[i])<d(M,G[pv])) pv=i;}
  STROKES deletePickedPt() {for(int i=pv; i<nv; i++) G[i].setTo(G[i+1]); pv=max(0,pv-1); nv--;  return this;}
  STROKES empty() {nv=0; G=new pt[maxnv];for (int i=0; i<maxnv; i++) G[i]=P(); pv=0;  return this;}
  pt getLast(){ return G[nv-1];}

  // ******** display points and their numbers
  STROKES draw() {beginShape(); for (int v=0; v<nv; v++) G[v].show().v(); endShape(); return this;}
  
  STROKES draw(int i, float r) {show(G[i],r); return this;}
  STROKES drawDisplaced(int i, float r) {
    if(i>0&&i<nv-1) show(P(G[i],4.,V(V(G[i],G[i-1]),V(G[i],G[i+1]))),r); 
    else show(G[i],r); 
    return this;}

  STROKES labels() {
    for (int v=0; v<nv; v++) { 
      fill(white); show(G[v],13); fill(black); 
      if(v<10) label(G[v],str(v)); else label(G[v],V(-5,0),str(v)); 
      }
    noFill();
    return this;
    }

  // ******** refining
   STROKES refine() {
     STROKES newPts;
     if(nv>2){
       pt newArray[] = new pt[maxnv]; 
       int newArrCount = 0;
       //First single parabola 
       float t = .5;
       newArray[newArrCount++] = new pt(G[0]);
       pt m = P( P(G[0],t,G[1]) , t/2 , P(G[1],t-1,G[2]) );  
       newArray[newArrCount++] = new pt(m);
       //4 point subdivision excluding first and last parabolas
       t = 1.5;
       for (int i=0;i<=nv-4;i+=1){
         pt H1 = P( P(G[i],t,G[i+1]) , t/2 , P(G[i+1],t-1,G[i+2]) );
         pt H2 = P( P(G[i+3],t,G[i+2]) , t/2 , P(G[i+2],t-1,G[i+1]) );
         pt M = P(H1,H2);   
         newArray[newArrCount++] = new pt(G[i+1]);
         newArray[newArrCount++] = new pt(M);
       }
       //final single parabola
       m = P( P(G[nv-3],t,G[nv-2]) , t/2 , P(G[nv-2],t-1,G[nv-1]) ); 
       newArray[newArrCount++] = new pt(G[nv-2]);
       newArray[newArrCount++] = new pt(m);
       newArray[newArrCount++] = new pt(G[nv-1]);  
       //Prep new stroke to return
       newPts = new STROKES();
       for( int i=0;i<newArrCount;i++){
         newPts.addPt(newArray[i]);  
       }
     }else{
       newPts = new STROKES();
       for( int i=0;i<nv;i++){
         newPts.addPt(G[i]);  
       }
     }
     return newPts;
   }
   
   void resample(int num){
     if(num<nv){
       int n = nv/num;
       pt[] R = new pt[maxnv];
       int count = 0;
       for(int i=0;i<nv;i=i+n){
         R[count] = new pt(G[i]);
         count++;
       }
       R[num-1] = new pt(G[nv-1]);
       nv = num;
       for(int i=0;i<nv;i++){
         G[i] = new pt(R[i]); 
       }
     }
   }
   
   void shiftRight(int px){
     for(int i=0;i<nv;i++){
       G[i] = G[i].add(px,0); 
     }
   }
   
   void shiftLeft(int px){
     for(int i=0;i<nv;i++){
       G[i] = G[i].add(-px,0); 
     }
   }
   
   SHAPES convertShape(){
     SHAPES newShape = new SHAPES();
     for(int i=0;i<=nv-3;i++){
        newShape.addLength(G[i],G[i+1]);
        newShape.addAngle(G[i],G[i+1],G[i+2]);
     }
     newShape.addLength(G[nv-2],G[nv-1]);
     
     return newShape;
   }
   
   STROKES scaleRotateTrans(pt A, pt B){
      STROKES newPts = new STROKES();
      pt newArray[] = new pt[maxnv];
      pt C = G[0];
      pt D = G[nv-1];
      vec AB = new vec(B.x-A.x,B.y-A.y);
      vec RAB = R(AB);
      float x=0;
      float y=0;
      pt V;
      for(int i=1;i<nv-1;i++){
        V = G[i];
        vec CD = new vec(D.x-C.x,D.y-C.y);
        float nCD = n(CD);
        vec CV = new vec(V.x-C.x,V.y-C.y);
        x = dot(CD,CV)/(nCD*nCD);
        y = -det(CD,CV)/(nCD*nCD);
        newArray[i] = A.add(x,AB).add(y,RAB);
      }
      newPts.addPt(A);
      for(int i=1;i<nv-1;i++){
        newPts.addPt(new pt(newArray[i]));  
      }
      newPts.addPt(B);
      return newPts;
   }
  

  
  // ******** smoothing
  STROKES tuck(float s) {
    pt[] S = new pt [nv]; // temporry array
    // copy of each intermediate point moved by s towards the average of its neighbors
    for (int v=1; v<nv-1; v++) S[v]=P(G[v],s,P(G[v-1],G[v+1]));  // S = G + s((P+N)/2-G)
    for (int v=1; v<nv-1; v++) G[v]=S[v]; // copy back
    return this;
    }
    
 
  
    // ******** measure
  pt Centroid() {pt C=P(); for (int i=0; i<nv; i++) C.add(G[i]); return P(1./nv,C);}
  
  // ******** move
  STROKES dragPicked() { G[pv].moveWithMouse(); return this;}      // moves selected point (index p) by amount mouse moved recently
  STROKES dragAll() { for (int i=0; i<nv; i++) G[i].moveWithMouse(); return this;}      // moves selected point (index p) by amount mouse moved recently
  STROKES scaleAll(float s, pt C) {for (int i=0; i<nv; i++) G[i].translateTowards(s,C); return this;};  
  STROKES scaleAllAroundCentroid(pt M, pt P) {pt C=Centroid(); float m=d(C,M),p=d(C,P); scaleAll((p-m)/p,C); return this;};

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
