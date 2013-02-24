float w=0;
class Curve {
  int n=0;                            // current number of control points
  pt [] P = new pt[5000];            //  array of points
  vec [] Nx = new vec[5000];          // twist free normal vectors 
  vec [] Ny = new vec[5000];          // twist free binormal vectors
  vec [] V = new vec[5000];          // Velocity vectors
  float [] twist = new float[5000];       // angle between Frenet and twist-free normal
  float [] curvature = new float[5000];   // curvature between Frenet and twist-free normal
  float [] curv = new float[5000];   // curvature between Frenet and twist-free normal
  
  vec [] L = new vec[5000];          // Laplace vectors for smoothing
  int p=0;                          // index to the currently selected vertex being dragged
  Curve(int pn) {n=pn; declarePoints();}
  Curve(int pn, float r) {n=pn; declarePoints(); resetPoints(r); }
  Curve(int pn, float r, pt Q) {n=pn; declarePoints(); resetPoints(r,Q); }
  Curve() {declarePoints(); resetPoints(); }
  int next(int j) {  return min(n-1,j+1); }  // next point 
  int prev(int j) {  return max(0,j-1); }   // previous point                                                      
  void pp() {p=prev(p);}
  void np() {p=next(p);}
  pt Pof(int p) {return P[p];}
  pt cP() {return P[p];}
  pt pP() {return P[prev(p)];}
  pt nP() {return P[next(p)];}
  void declarePoints() {for (int i=0; i<P.length; i++) {P[i]=P(); Nx[i]=V() ;Ny[i]=V();} }  // allocates space
  void resetPoints() {float r=100; for (int i=0; i<n; i++) {P[i].x=r*cos(TWO_PI/n); P[i].y=r*sin(TWO_PI/n);}; } // init the points to be on a circle
  void resetPoints(float r) {println(">>n="+n); for (int i=0; i<n; i++) {P[i].x=r*cos(TWO_PI/n*i); P[i].y=r*sin(TWO_PI/n*i);}; } // init the points to be on a circle
  void resetPoints(float r, pt Q) {println(">>n="+n); for (int i=0; i<n; i++) {P[i].x=r*cos(TWO_PI/n*i); P[i].y=r*sin(TWO_PI/n*i); P[i].add(Q);}; } // init the points to be on a circle
  Curve empty(){ n=0; return this; };      // resets the vertex count to zero
  void pick(pt M) {p=0; for (int i=1; i<n; i++) if (d(M,P[i])<d(M,P[p])) p=i;}
  void dragPoint(vec V) {if (p != 0 && p != n - 1) P[p].add(V);}
  void dragAll(vec V) {for (int i=0; i<n; i++) P[i].add(V);}
  void dragAll(int s, int e, vec V) {for (int i=s; i<e+1; i++) P[i].add(V);}
  void movePointTo(pt Q) {P[p].set(Q);}
  Curve append(pt Q)  {if(n+1 < P.length) { p=n; P[n++].set(Q); } return this;}; // add point at end of list
  Curve appendN(Curve C) {
    for(int i=0;i<C.n;i++){
      append(C.Pof(i));
    }
    return this;
  }
  void delete(){ n--;}
  void deletePt(int index){
    for (int i = index; i < n-1; i++){
      P[i].set(P[i+1]); 
    }
    n--; 
  }  
  void deletePick(){
    if (p == 0){
      p++; 
    }
    else if (p == n-1) {
      p--;  
    }
     for (int i=p; i<n-1; i++){ 
       P[i].set(P[i+1]);
     }
     n--;
  }
  void deleteMousePt(int mX, int mY){
    for (int i = 0; i < n; i++){
      if (abs(P[i].x - mX) <= 10 && abs(P[i].y - mY) <= 10){
        deletePt(i);    
      }
    }    
  }
  void insert() { // inserts after p
    pt newPt = P(P[n-1].x+20,P[n-1].y+20,P[n-1].z+20);
    P[n] = P(newPt);
    n++;
    }
  void insertOnCurve() {       // grabs closeest vertex or adds vertex at closest edge. It will be dragged by te mouse
    for (int i=n; i>p; i--){ 
      P[i].set(P[i-1]);
    }
    pt midPoint = P(P[p],P[p+2]);
    P[p+1] =  midPoint;
    n++;
  }
  
  vec getBaseVec(){
    return V(P[0],P[n-1]);
  }
  
  Curve getScaledCurve(pt origin, vec direc){
    Curve newCurve = new Curve();
    vec CD = V(P[0],P[n-1]);
    float nCD = n(CD);
    vec AB = V(direc);
    vec RAB = V(-AB.y,AB.x,0);
    float relX=0;
    float relY=0;
    pt V;
    newCurve.append(P(origin));
    for(int i=1;i<n-1;i++){
      V = P[i];
      vec CV = V(P[0],V);
      relX = d(CD,CV)/(nCD*nCD);
      relY = -det(CD,CV)/(nCD*nCD);
      newCurve.append( P(P(origin,relX,AB),relY,RAB) );
    }
    newCurve.append(P(origin,1,AB));
    return newCurve;    
  }
  
   Curve getRotatedCurve(float a){
    Curve rotatedCurve = new Curve();
    vec AD = V(P(P[0]),P(P[n-1]));
    for (int i=0; i<n; i++){
      vec AB = V(P(P[0]),P(P[i]));
      float projection = d(AB, AD)/d(AD,AD);
      vec EB = M(AB, V(projection, AD)); //j
      vec EF = DCross(EB, AD); //i
      pt G = P(P(P[0]),projection,AD);
      pt rotated = R(P(P[i]), a, U(EB), U(EF), G);
      rotatedCurve.append(rotated);      
    }  
    return rotatedCurve;
  }
  
  /*Curve getRotatedCurve(float a, vec I, vec J){
    Curve rotatedCurve = new Curve();
    vec CD = V(P(P[0]),P(P[n-1]));
    float nCD = n(CD);
    float relX=0;
    pt G;
    for(int i=0;i<n;i++){
      vec CV = V(P(P[0]),P(P[i]));
      relX = d(CD,CV)/(nCD*nCD);
      G = P(P(P[0]),relX,CD);
      pt rotated = R(P(P[i]),a,I,J,G);
      rotatedCurve.append(rotated);
    } 
    return rotatedCurve;
  }
   */
  Curve makeFrom(Curve C, int v) {empty(); for(float t=0; t<=1.001; t+=.1/v) append(C.interpolate(t)); return this;}
  pt interpolate(float t) { return D(P[0],P[1],P[2],P[3],P[4],t); }

  Curve drawEdges() {beginShape(); for (int i=0; i<n; i++)vertex(P[i]); vertex(P[0]); endShape(); return this;}  // fast draw of edges
  Curve showSamples() {for (int i=0; i<n; i++) show(P[i],1); return this;}  // fast draw of edges
  Curve showSamples(float r) {for (int i=0; i<n; i++) show(P[i],r); return this;}  // fast draw of edges
  void showPick() {show(P[p],2); }  // fast draw of edges
  void cloneFrom(Curve D) {for (int i=0; i<max(n,D.n); i++) P[i].set(D.P[i]); n=D.n;}
  pt pt(int i) {return P[i];}
  void showLoop() { noFill(); stroke(orange); drawEdges(); noStroke(); fill(orange); showSamples(); }  
  int closestVertexID(pt M) {int v=0; for (int i=1; i<n; i++) if (d(M,P[i])<d(M,P[v])) v=i; return v;}
  pt ClosestVertex(pt M) {pt R=P[0]; for (int i=1; i<n; i++) if (d(M,P[i])<d(M,R)) R=P[i]; return P(R);}
  float distanceTo(pt M) {float md=d(M,P[0]); for (int i=1; i<n; i++) md=min(md,d(M,P[i])); return md;}
  void savePts() {savePts("data/P2.pts");}
  void savePts(String fn) { String [] inppts = new String [n+1];
    int s=0; inppts[s++]=str(n); 
    for (int i=0; i<n; i++) {inppts[s++]=str(P[i].x)+","+str(P[i].y)+","+str(P[i].z);};
    saveStrings(fn,inppts);  };
  void loadPts() {loadPts("data/P.pts");}
  void loadPts(String fn) { String [] ss = loadStrings(fn);
    String subpts;
    int s=0; int comma1, comma2; n = int(ss[s]);
    for(int i=0; i<n; i++) { 
      String S =  ss[++s];
      comma1=S.indexOf(',');
      float x=float(S.substring(0, comma1));
      String R = S.substring(comma1+1);
      comma2=R.indexOf(',');      
      float y=float(R.substring(0, comma2)); 
      float z=float(R.substring(comma2+1));
      P[i]= P(x,y,z);  
      }; 
    }
  void addDif(Curve R, Curve C) {for(int i=0;i<n; i++) P[i].add(V(R.pt(i),C.pt(i)));}    
  float length () {float L=0; for (int i=0; i<n; i++) L+=d(P[i],P[next(i)]);  return(L); }    

// ******************************************************************************************** LACING ***************
   Curve refine() {
     Curve newCurve =new Curve();
     if(n>2){
       pt newArray[] = new pt[5000]; 
       int newArrCount = 0;
       //First single parabola 
       float t = .5;
       newArray[newArrCount++] = P(P[0]);
       pt m = P( P(P[0],t,P[1]) , t/2 , P(P[1],t-1,P[2]) );  
       newArray[newArrCount++] = P(m);
       //4 point subdivision excluding first and last parabolas
       t = 1.5;
       for (int i=0;i<=n-4;i+=1){
         pt H1 = P( P(P[i],t,P[i+1]) , t/2 , P(P[i+1],t-1,P[i+2]) );
         pt H2 = P( P(P[i+3],t,P[i+2]) , t/2 , P(P[i+2],t-1,P[i+1]) );
         pt M = P(H1,H2);   
         newArray[newArrCount++] = P(P[i+1]);
         newArray[newArrCount++] = P(M);
       }
       //final single parabola
       m = P( P(P[n-3],t,P[n-2]) , t/2 , P(P[n-2],t-1,P[n-1]) ); 
       newArray[newArrCount++] = P(P[n-2]);
       newArray[newArrCount++] = P(m);
       newArray[newArrCount++] = P(P[n-1]);  
       //Prep new stroke to return
       for( int i=0;i<newArrCount;i++){
         newCurve.append(newArray[i]);  
       }
     }else{
       return this;
     }
     return newCurve;
  }
   
   
  Curve resampleDistance(float r) { 
    Curve NL = new Curve();
    NL.append(P[0]);          if (n<3) return NL;
    pt C = new pt();
    C.set(P[0]);
    int i=0; 
    Boolean go=true;
    while(go) {
      int j=i; while(j<n && d(P[j+1],C)<r) j++; // last vertex in sphere(C,r);
      if(j>=n-1) go=false; 
      else {
        pt A=P[j], B=P[j+1]; 
        float a=d2(A,B), b=d(V(C,A),V(A,B)), c=d2(C,A)-sq(r);  
        float s=(-b+sqrt(sq(b)-a*c))/a; 
        C.set(P(A,s,B)); 
        NL.append(C);
        i=j;           
        }
      }
    return NL;
    }
 
  void resample(int nn) { // resamples the curve with new nn vertices
    if(nn<3) return;
    float L = length();  // current total length  
    float d = L / nn;   // desired arc-length spacing                        
    float rd=d;        // remaining distance to next sample
    float cl=0;        // length of remaining portion of current edge
    int k=0,nk;        // counters
    pt [] R = new pt [nn]; // temporary array for the new points
    pt Q;
    int s=0;
    Q=P[0];         
    R[s++]=P(Q);     
    while (s<nn) {
       nk=next(k);
       cl=d(Q,P[nk]);                            
       if (rd<cl) {Q=P(Q,rd,P[nk]); R[s++]=P(Q); cl-=rd; rd=d; } 
       else {rd-=cl; Q.set(P[nk]); k++; };
       };
     n=s;   for (int i=0; i<n; i++)  P[i].set(R[i]);
   }

   
   void save() {
    String savePath = selectOutput("Select or specify .pts file where the curve points will be saved");  // Opens file chooser
    if (savePath == null) {println("No output file was selected..."); return;}
    else println("writing curve to "+savePath);
    save(savePath);
    }

  void save(String fn) {
    String [] inppts = new String [n+1];
    int s=0;
    inppts[s++]=str(n);
    for (int i=0; i<n; i++) {inppts[s++]=str(P[i].x)+","+str(P[i].y)+","+str(P[i].z);};
    saveStrings(fn,inppts);  
    };
  
  void load() {
    String loadPath = selectInput("Select .pts file to load for the curve");  // Opens file chooser
    if (loadPath == null) {println("No input file was selected..."); return;}
    else println("reading curve from "+loadPath); 
    load(loadPath);
    }

  void load(String fn) {
    println("loading curve: "+fn); 
    String [] ss = loadStrings(fn);
    String subpts;
    int s=0;   int comma1, comma2;   float x, y, z;   
    n = int(ss[s++]);
    println("n="+n);
    for(int k=0; k<n; k++) {
      int i=k+s; 
      comma1=ss[i].indexOf(',');   
      x=float(ss[i].substring(0, comma1));
      String rest = ss[i].substring(comma1+1, ss[i].length());
      comma2=rest.indexOf(',');    y=float(rest.substring(0, comma2)); z=float(rest.substring(comma2+1, rest.length()));
      P[k].set(x,y,z);
      //      println(k+":"+"("+x+","+y+","+z+"),");
      };
      println("done loading");  
    }; 
  
   /// ******************************** compute normals *******************************************************
   Curve computeFirstTwistFreeNormal() {
     vec V=V(P[0],P[1]); vec I=V(1,0,0); vec J=V(0,1,0); vec VxI = N(V,I); vec VxJ = N(V,J); 
     if (VxI.norm() > VxJ.norm()) Nx[0]=U(N(VxI,V)); else Nx[0]=U(N(VxJ,V));
     return this;}
     
   Curve computeTwistFreeNormals() {
     pt Q=P(P[0],50,Nx[0]);
     for(int i=1; i<n-1; i++) {vec BD=V(P[i-1],P[i+1]); float s=d(V(Q,P[i]),BD)/d(V(P[i-1],P[i]),BD); Q=P(Q,s,V(P[i-1],P[i])); Nx[i]=U(P[i],Q);}
     Nx[n-1]=Nx[n-2];
     return this;}
 
   Curve computeTwistFreeBiNormals() { //Also computes velocity field
     for(int i=1; i<n-1; i++) {
       vec T=U(P[i-1],P[i+1]);
       V[i] = V(P[i-1],P[i+1]); 
       Ny[i]=U(N(Nx[i],T)); 
     }
     V[0]=V(P[0],P[1]);
     V[n-1]=V(P[n-2],P[n-1]);
     Ny[0]=U(N(Nx[0],V(P[0],P[1])));
     Ny[n-1]=U( N( Nx[n-1],V(P[n-2],P[n-1]) ) );

     return this;}
     
   Curve rotateFirstTwistFreeNormal(float ang) {
     
     vec N=Nx[0];
     vec T=U(P[0],P[1]);
     vec B=U(N(N,T));
     N.rotate(ang,N,B);
     return this;} 

 
  Curve prepareSpine(float ang) {   
    computeFirstTwistFreeNormal();
    rotateFirstTwistFreeNormal(ang);
    computeTwistFreeNormals();
    computeTwistFreeBiNormals();
    return this;}
    
  void showTube(float r, int ne, int nq, color col) {
      pt [][] C = new pt [2][ne];
      boolean dark=true;
      // make circle in local cross section
      float [] c = new float [ne]; float [] s = new float [ne];
      for (int j=0; j<ne; j++) {c[j]=r*cos(TWO_PI*j/ne); s[j]=r*sin(TWO_PI*j/ne); };    
      int p=0; 
      for (int j=0; j<ne; j++) C[p][j]=P( P[0] , c[j],Nx[0], s[j],Ny[0] ); p=1-p;
      noStroke();
      for (int i=1; i<n-1; i++) {
        if(i%nq==0) dark=!dark; 
        for (int j=0; j<ne; j++) C[p][j]=P( P[i], c[j],Nx[i], s[j],Ny[i]); p=1-p;
        if(i>0) for (int j=0; j<ne; j++) {
            if(dark) fill(200,200,200); else fill(col); dark=!dark; 
            int jp=(j+ne-1)%ne; beginShape(); v(C[p][jp]); v(C[p][j]); v(C[1-p][j]); v(C[1-p][jp]); endShape(CLOSE);};
        }        
      }

  vec II = V(1,0,0), JJ = V(0,1,0);
  }  // end class Curve
  
