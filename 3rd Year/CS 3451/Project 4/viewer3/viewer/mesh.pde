// CORNER TABLE FOR TRIANGLE MESHES by Jarek Rosignac
// Last edited October, 2011
// example meshesshowShrunkOffsetT
String [] fn= {"HeartReallyReduced.vts","horse.vts","bunny.vts","torus.vts","flat.vts","tet.vts","fandisk.vts","squirrel.vts","venus.vts","mesh.vts","hole.vts","gs_dimples_bumps.vts"};
int fni=0; int fniMax=fn.length; // file names for loading meshes
Boolean [] vis = new Boolean [10]; 
Boolean onTriangles=true, onEdges=true; // projection control
float shrunk; // >0 for showoing shrunk triangles
//========================== class MESH ===============================
class Mesh {
//  ==================================== Internal variables ====================================
 // max sizes, counts, selected corners
 int maxnv = 45000;                         //  max number of vertices
 int maxnt = maxnv*2;                       // max number of triangles
 int nv = 0;                              // current  number of vertices
 int nt = 0;                   // current number of triangles
 int nc = 0;                                // current number of corners (3 per triangle)
 int nvr=0, ntr=0, ncr=0; // remember state to restore
 int cc=0, pc=0, sc=0;                      // current, previous, saved corners
 float vol=0, surf=0, edgeLength=0;                      // vol and surface and average edge length
 float aR=0; // Average radius of curvature

 // boundingbox
 pt Cbox = new pt(width/2,height/2,0);                   // mini-max box center
 float rbox=1000;                                        // half-diagonal of enclosing box
 float r=2;                                // detail size: radius of spheres for displaying vertices and of gaps between triangles when showing edges

 // rendering modes
 Boolean flatShading=false, showEdges=false, showCurvature=false;  // showEdges shoes edges as gaps. Smooth shading works only when !showEdge


 // primary tables
 pt[] G = new pt [maxnv];                   // geometry table (vertices)
 int[] V = new int [3*maxnt];               // V table (triangle/vertex indices)
 int[] O = new int [3*maxnt];               // O table (opposite corner indices)

 // Normal vectors
vec[] Nv = new vec [maxnv];                 // vertex normals or laplace vectors
vec[] Nt = new vec [maxnt];                // triangles normals

 // auxiliary vertices
 int[] W = new int [3*maxnt];               // mid-edge vertex indices for subdivision (associated with corner opposite to edge)
 pt[] G2 = new pt [maxnv]; //2008-03-06 JJ misc

 // vertex markers
 int[] Mv = new int[maxnv];                  // vertex markers
 int[] vm = new int[3*maxnt];               // vertex markers: 0=not marked, 1=interior, 2=border, 3=non manifold
 int [] Valence = new int [maxnv];          // vertex valence (count of incident triangles)
 boolean [] Border = new boolean [maxnv];   // vertex is border
 boolean [] VisitedV = new boolean [maxnv];  // vertex visited
 float [] distance = new float [maxnv];      // distance associated with each vertex
 float [] curvature = new float [maxnv];
 float [] varea = new float [maxnv];

 // corner markers
 int[] cm = new int[3*maxnt];               // corner markers: 

 // triangle markers
 boolean[] visible = new boolean[maxnt];     // set if triangle visible, used to hide/unhide and then delete triangles
 boolean [] VisitedT = new boolean [maxnt];  // triangle marked as visited during traversal
 int[] tm = new int[3*maxnt];               // triangle markers: 0=not marked, 
 int[] Mt = new int[maxnt];                 // triangle markers for distance and other things   

 // variables used in geodesic calculations
 boolean  showPath=false, showDistance=false;  
 boolean[] P = new boolean [3*maxnt];       // marker of corners in a path to parent triangle for tracing back the paths
 int[] Distance = new int[maxnt];           // triangle markers for distance fields 
 int[] SMt = new int[maxnt];                // sum of triangle markers for isolation
 int prevc = 0;                             // previously selected corner
 int rings=2;                           // number of rings for colorcoding

//  ==================================== OFFSETS ====================================
 void offset() {
   normals();
   float d=rbox/100;
   for (int i=0; i<nv; i++) G[i]=P(G[i],d,Nv[i]);
   }
 void offset(float d) {
   normals();
   for (int i=0; i<nv; i++) G[i]=P(G[i],d,Nv[i]);
   }
 void adjustVolume(float w) {
   float a = surfaceArea();
   float v = volume();
   float d = (w-v)/a;
   dis += d; // update recorded distance (when using adjustVolume4
   offset(d);
   }
  void adjustVolumeX(float w) { dis=0; for (int i=0; i<3; i++) adjustVolume(w);}

 
 vec Tedge(int c) {return V(g(n(c)),g(p(c)));}
 vec Ntri(int c) {return Nt[t(c)];}
 vec Nvert(int c) {return Nv[v(c)];}
 
 float computeCurvature() {
   normals(); normalizeTriNormals();
   float m=0;
   for (int c=0; c<nc; c++) m+=m(Nvert(c),Ntri(c),Tedge(c));
   return m;
   }
   
 void compareRadii() {
   normals(); 
   float a;
   float m;
   m=0; for (int c=0; c<nc; c++) m+=m(Nvert(c),U(Ntri(c)),Tedge(c));
   a = surfaceArea();
   float R=4.*a/m;
   println("R1="+R+", a="+a);
   
   m=0; for (int c=0; c<nc; c++) m+=m(Nvert(c),U(Ntri(c)),Tedge(c));
   a=0; for (int t=0; t<nt; t++) a+=sqrt(n2(Nt[t]))  ; 
   R=2.*a/m;
   println("R2="+R+", a="+a);

   }

  
 void setCurvatures() {
   normals();
   for (int v=0; v<nv; v++) varea[v]=0;
   for (int c=0; c<nc; c++) varea[v(c)]+=d(Nvert(c),Ntri(c))/2;
   for (int v=0; v<nv; v++) curvature[v]=0;
   for (int c=0; c<nc; c++) curvature[v(c)]+=m(Nvert(c),U(Ntri(c)),Tedge(c));
   for (int v=0; v<nv; v++) curvature[v]=curvature[v]/(varea[v])*3./4;
   println("setCurvatures: R[7]="+1./curvature[7]+", R="+d(G[7],P()));
   }
 
   
  void showCurvatures() {stroke(dblue); for (int v=0; v<nv; v++) show(G[v],V(1./curvature[v],Nv[v]));}
  void checkVareas() {float a=0; for (int v=0; v<nv; v++) a+=varea[v]; println("Area x 3 = "+3*surfaceArea()+", sum = "+a); }
   
   
   
//  ==================================== INIT, CREATE, COPY ====================================
 Mesh() {}

 Mesh declareVectors() {
   for (int i=0; i<maxnv; i++) {G[i]=P(); Nv[i]=V();};   // init vertices and normals
   for (int i=0; i<maxnt; i++) Nt[i]=V();       // init triangle normals and skeleton lab els
   return this;
   }

 Mesh empty() {nv=0; nt=0; nc=0; return this;}
 void resetCounters() {nv=0; nt=0; nc=0;}
 void rememberCounters() {nvr=nv; ntr=nt; ncr=nc;}
 void restoreCounters() {nv=nvr; nt=ntr; nc=ncr;}

 void makeGrid (int w) { // make a 2D grid of w x w vertices
   for (int i=0; i<w; i++) {for (int j=0; j<w; j++) { G[w*i+j].set(height*.8*j/(w-1)+height/10,height*.8*i/(w-1)+height/10,0);}}    
   for (int i=0; i<w-1; i++) {for (int j=0; j<w-1; j++) {                  // define the triangles for the grid
     V[(i*(w-1)+j)*6]=i*w+j;       V[(i*(w-1)+j)*6+2]=(i+1)*w+j;       V[(i*(w-1)+j)*6+1]=(i+1)*w+j+1;
     V[(i*(w-1)+j)*6+3]=i*w+j;     V[(i*(w-1)+j)*6+5]=(i+1)*w+j+1;     V[(i*(w-1)+j)*6+4]=i*w+j+1;}; };
   nv = w*w;
   nt = 2*(w-1)*(w-1); 
   nc=3*nt;  }

void makeChain (float w, int m) { // make a chain of size 2w wiht m elements
   resetCounters();
   for (int k=0; k<=m; k++) for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*2*k));
   for (int k=1; k<=m; k++) {
     int p=4*(k-1); 
     if(k%2==0) {fillSquare(p,p+4,p+6,p+2); fillSquare(p+1,p+3,p+7,p+5); fillSquare(p,p+2,p+3,p+1);}
     else {fillSquare(p,p+4,p+5,p+1); fillSquare(p+2,p+3,p+7,p+6); fillSquare(p,p+1,p+3,p+2);}
     }
  int p=4*m;  if(m%2==1) fillSquare(p,p+2,p+3,p+1); else fillSquare(p,p+1,p+3,p+2); 
  }

void makeXChain (float w, int m) { // make a chain of size 2w wiht m elements
   resetCounters();
   float d=0.1;
   for (int k=0; k<=m; k++) for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*2*k));
   for (int k=1; k<=m; k++) {
     int p=4*(k-1); 
     if(k%2==0) {fillXSquare(p,p+4,p+6,p+2,d); fillXSquare(p+1,p+3,p+7,p+5,d); fillXSquare(p,p+2,p+3,p+1,d);}
     else {fillXSquare(p,p+4,p+5,p+1,d); fillXSquare(p+2,p+3,p+7,p+6,d); fillXSquare(p,p+1,p+3,p+2,d);}
     }
  int p=4*m;  if(m%2==1) fillXSquare(p,p+2,p+3,p+1,d); else fillXSquare(p,p+1,p+3,p+2,d); 
  }

void makeNChain (float w, int m) { // make a chain of size 2w wiht m elements
   resetCounters();
   float d=0.1;
   for (int k=0; k<=m; k++) for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*2*k));
   for (int k=1; k<=m; k++) {
     int p=4*(k-1); 
     if(k%6==1 || k%6==3 || k%6==4 || k%6==5) {fillXSquare(p,p+4,p+6,p+2,d); fillXSquare(p+1,p+3,p+7,p+5,d); fillXSquare(p,p+2,p+3,p+1,-d);}
     else {fillXSquare(p,p+4,p+5,p+1,-d); fillXSquare(p+2,p+3,p+7,p+6,-d); fillXSquare(p,p+1,p+3,p+2,-d);}
     }
  int p=4*m;  if(m%2==1) fillXSquare(p,p+2,p+3,p+1,d); else fillXSquare(p,p+1,p+3,p+2,d); 
  }

void makeDChain (float w, int m) { // make a chain of size 2w wiht m elements
   resetCounters();
   float d=0.1;
   for (int k=0; k<=m; k++) for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*2*k));
   for (int k=1; k<=m; k++) {
     int p=4*(k-1); 
     if(k%6==1 || k%6==3 || k%6==4 || k%6==5) {fillXSquare(p,p+4,p+6,p+2,d); fillXSquare(p+1,p+3,p+7,p+5,d); fillXSquare(p,p+2,p+3,p+1,-d); fillXSquare(p,p+5,p+7,p+2,0);}
     else {fillXSquare(p,p+4,p+5,p+1,-d); fillXSquare(p+2,p+3,p+7,p+6,-d); fillXSquare(p,p+1,p+3,p+2,-d); }
     }
  int p=4*m;  if(m%2==1) fillXSquare(p,p+2,p+3,p+1,d); else fillXSquare(p,p+1,p+3,p+2,d); 
  }

 Mesh makeTet (pt P, float w) { // make a tet at P
   resetCounters();
   int a=addVertex(P().add(P)), b=addVertex(P(w,0,0).add(P)), c=addVertex(P(0,w,0).add(P)), d=addVertex(P(0,0,w).add(P));
   addTriangle(a,c,b); addTriangle(a,d,c); addTriangle(a,b,d); addTriangle(b,c,d);
   return this; 
   }
   
 Mesh makeCube (float w) { // make a cube of size 2w
   resetCounters();
   for (int k=-1; k<=1; k+=2)  for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*k));
   fillSquare(0,2,3,1); fillSquare(4,5,7,6);
   fillSquare(0,4,6,2); fillSquare(1,3,7,5);
   fillSquare(0,1,5,4); fillSquare(2,6,7,3);
   return this; 
   }

 Mesh makeCube (pt P, float w) { // make a cube of size 2w
   resetCounters();
   for (int k=-1; k<=1; k+=2)  for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*k).add(P));
   fillSquare(0,2,3,1); fillSquare(4,5,7,6);
   fillSquare(0,4,6,2); fillSquare(1,3,7,5);
   fillSquare(0,1,5,4); fillSquare(2,6,7,3);
   return this; 
   }

 void fillSquare(int a, int b, int c, int d) {addTriangle(a,b,c); addTriangle(a,c,d);}
 
 void makeXCube (float w, float s) { // make a cube of size 2w
   resetCounters();
   for (int k=-1; k<=1; k+=2)  for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*k));
   fillXSquare(0,2,3,1,s); fillXSquare(4,5,7,6,s);
   fillXSquare(0,4,6,2,s); fillXSquare(1,3,7,5,s);
   fillXSquare(0,1,5,4,s); fillXSquare(2,6,7,3,s);
   }
 Mesh makeXCube (pt P, float w, float s) { // make a cube of size 2w
   resetCounters();
   for (int k=-1; k<=1; k+=2)  for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*k).add(P));
   fillXSquare(0,2,3,1,s); fillXSquare(4,5,7,6,s);
   fillXSquare(0,4,6,2,s); fillXSquare(1,3,7,5,s);
   fillXSquare(0,1,5,4,s); fillXSquare(2,6,7,3,s);
   return this;
   }
 void fillXSquare(int a, int b, int c, int d, float s) {
   int v=addVertex(P(G[a],G[b],G[c],G[d])); 
   addTriangle(a,b,v);  addTriangle(b,c,v);  addTriangle(c,d,v);  addTriangle(d,a,v); 
   vec N=U(N(V(G[d],G[b]),V(G[a],G[c]))); 
   float l=(d(G[a],G[c])+d(G[b],G[d]))/2; // diagonal length
   G[v].add(l*s,N); // bulge center vertex out
   }
 void makeCross (float w, float s) { // make a cube of size 2w
   resetCounters();
   for (int k=-1; k<=1; k+=2)  for (int j=-1; j<=1; j+=2) for (int i=-1; i<=1; i+=2) addVertex(P(w*i,w*j,w*k));
   fillCross(0,2,3,1,s); fillCross(4,5,7,6,s);
   fillCross(0,4,6,2,s); fillCross(1,3,7,5,s);
   fillCross(0,1,5,4,s); fillCross(2,6,7,3,s);
   }
 void fillCross(int a, int b, int c, int d, float s) {
   vec N=U(N(V(G[d],G[b]),V(G[a],G[c]))); N.mul(s);
   int a1=addVertex(P(G[a],s,N)), b1=addVertex(P(G[b],s,N)), c1=addVertex(P(G[c],s,N)), d1=addVertex(P(G[d],s,N)); 
   fillXSquare(a1,b1,c1,d1,s/20);
   fillXSquare(a1,a,b,b1,0); fillXSquare(b1,b,c,c1,0); fillXSquare(c1,c,d,d1,0); fillXSquare(d1,d,a,a1,0);
   }
 void snapToSphere(float r) {for(int v=0; v<nv; v++) G[v].snap(r);}

 float averageEdgeLength() {float L=0; for(int c=0; c<nc; c++) L+=d(g(n(c)),g(p(c))); edgeLength=L/nc; return edgeLength;}
 void setr() {r=averageEdgeLength()/10;}
 
 Mesh resetMarkers() { // reset the seed and current corner and the markers for corners, triangles, and vertices
   cc=0; pc=0; sc=0;
   for (int i=0; i<nv; i++) vm[i]=0;
   for (int i=0; i<nc; i++) cm[i]=0;
   for (int i=0; i<nt; i++) tm[i]=0;
   for (int i=0; i<nt; i++) visible[i]=true;
   return this;
   }
 
 int addVertex(pt P) { G[nv].set(P); nv++; return nv-1;};
 int addVertex(float x, float y, float z) { G[nv].x=x; G[nv].y=y; G[nv].z=z; nv++; return nv-1;};
  
 void addTriangle(int i, int j, int k) {V[nc++]=i; V[nc++]=j; V[nc++]=k; visible[nt++]=true;} // adds a triangle
 void addTriangle(int i, int j, int k, int m) {V[nc++]=i; V[nc++]=j; V[nc++]=k; tm[nt]=m; visible[nt++]=true; } // adds a triangle

 Mesh updateON() {computeO(); normals(); return this;} // recomputes O and normals
 
 void copyTo(Mesh M) {
   for (int i=0; i<nv; i++) M.G[i].set(G[i]);
   M.nv=nv;
   for (int i=0; i<nc; i++) M.V[i]=V[i];
   M.nt=nt;
   for (int i=0; i<nc; i++) M.O[i]=O[i];
   M.nc=nc;
   M.resetMarkers();
   }
 

  // ============================================= CORNER OPERATORS =======================================
    // operations on a corner
  int t (int c) {return int(c/3);};              // triangle of corner    
  int n (int c) {return 3*t(c)+(c+1)%3;};        // next corner in the same t(c)    
  int p (int c) {return n(n(c));};               // previous corner in the same t(c)  
  int v (int c) {return V[c] ;};                 // id of the vertex of c             
  int o (int c) {return O[c];};                  // opposite (or self if it has no opposite)
  int l (int c) {return o(n(c));};               // left neighbor (or next if n(c) has no opposite)                      
  int r (int c) {return o(p(c));};               // right neighbor (or previous if p(c) has no opposite)                    
  int s (int c) {return n(l(c));};               // swings around v(c) or around a border loop
  int u (int c) {return p(r(c));};               // unswings around v(c) or around a border loop
  int c (int t) {return t*3;}                    // first corner of triangle t
  boolean b (int c) {return O[c]==c;};           // if faces a border (has no opposite)
  boolean vis(int c) {return visible[t(c)]; };   // true if tiangle of c is visible

    // operations on the selected corner cc
  int t() {return t(cc); }
  int n() {return n(cc); }        Mesh next() {pc=cc; cc=n(cc); return this;};
  int p() {return p(cc); }        Mesh previous() {pc=cc; cc=p(cc); return this;};
  int v() {return v(cc); }
  int o() {return o(cc); }        Mesh back() {if(!b(cc)) {pc=cc; cc=o(cc);}; return this;};
  boolean b() {return b(cc);}             
  int l() {return l(cc);}         Mesh left() {next(); back(); return this;}; 
  int r() {return r(cc);}         Mesh right() {previous(); back(); return this;};
  int s() {return s(cc);}         Mesh swing() {left(); next();  return this;};
  int u() {return u(cc);}         Mesh unswing() {right(); previous();  return this;};

    // geometry for corner c
  pt g (int c) {return G[v(c)];};                // shortcut to get the point of the vertex v(c) of corner c
  pt cg(int c) {pt cPt = P(g(c),0.3,triCenter(t(c)));  return(cPt); };   // computes point at corner
  pt corner(int c) {return P(g(c),triCenter(t(c)));   };   // returns corner point

    // normals fot t(c) (must be precomputed)
  vec Nv (int c) {return(Nv[V[c]]);}; vec Nv() {return Nv(cc);}            // shortcut to get the normal of v(c) 
  vec Nt (int c) {return(Nt[t(c)]);}; vec Nt() {return Nt(cc);}            // shortcut to get the normal of t(c) 

    // geometry for corner cc
  pt g() {return g(cc);}            // shortcut to get the point of the vertex v(c) of corner c
  pt gp() {return g(p(cc));}            // shortcut to get the point of the vertex v(c) of corner c
  pt gn() {return g(n(cc));}            // shortcut to get the point of the vertex v(c) of corner c
  void setG(pt P) {G[v(cc)].set(P);} // moves vertex of c to P

     // debugging prints
  void writeCorner (int c) {println("cc="+cc+", n="+n(cc)+", p="+p(cc)+", o="+o(cc)+", v="+v(cc)+", t="+t(cc)+"."+", nt="+nt+", nv="+nv ); }; 
  void writeCorner () {writeCorner (cc);}
  void writeCorners () {for (int c=0; c<nc; c++) {println("T["+c+"]="+t(c)+", visible="+visible[t(c)]+", v="+v(c)+",  o="+o(c));};}

// ============================================= MESH MANIPULATION =======================================
  // pick corner closest to point X
  void pickcOfClosestVertex (pt X) {for (int b=0; b<nc; b++) if(vis[tm[t(b)]]) if(d(X,g(b))<d(X,g(cc))) {cc=b; pc=b; } } // picks corner of closest vertex to X
  void pickc (pt X) {for (int b=0; b<nc; b++) if(vis[tm[t(b)]]) if(d(X,cg(b))<d(X,cg(cc))) {cc=b; pc=b; } } // picks closest corner to X
  void picksOfClosestVertex (pt X) {for (int b=0; b<nc; b++) if(vis[tm[t(b)]]) if(d(X,g(b))<d(X,g(sc))) {sc=b;} } // picks corner of closest vertex to X
  void picks (pt X) {for (int b=0; b<nc; b++)  if(vis[tm[t(b)]]) if(d(X,cg(b))<d(X,cg(sc))) {sc=b;} } // picks closest corner to X

  // move the vertex of a corner
  void setG(int c, pt P) {G[v(c)].set(P);}       // moves vertex of c to P
  Mesh add(int c, vec V) {G[v(c)].add(V); return this;}             // moves vertex of c to P
  Mesh add(int c, float s, vec V) {G[v(c)].add(s,V); return this;}   // moves vertex of c to P
  Mesh add(vec V) {G[v(cc)].add(V); return this;} // moves vertex of c to P
  Mesh add(float s, vec V) {G[v(cc)].add(s,V); return this;} // moves vertex of c to P
  void move(int c) {g(c).add(pmouseY-mouseY,Nv(c));}
  void move(int c, float d) {g(c).add(d,Nv(c));}
  void move() {move(cc); normals();}

  Mesh addROI(float s, vec V) { return addROI(int(nt/32),s,V);}
  Mesh addROI(int d, float s, vec V) {
     float md=setROI(d); 
     for (int c=0; c<nc; c++) if(!VisitedV[v(c)]&&(Mv[v(c)]!=0))  G[v(c)].add(s*(1.-distance[v(c)]/md),V);   // moves ROI
     smoothROI();
     setROI(d*2); // marks ROI of d rings
     smoothROI(); smoothROI();
     return this;
     }   

  void tuckROI(float s) {for (int i=0; i<nv; i++) if (Mv[i]!=0) G[i].add(s,Nv[i]); };  // displaces each vertex by a fraction s of its normal
  void smoothROI() {computeLaplaceVectors(); tuckROI(0.5); computeLaplaceVectors(); tuckROI(-0.5);};
  
float setROI(int n) { // marks vertices and triangles at a graph distance of maxr
  float md=0;
  int tc=0; // triangle counter
  int r=1; // ring counter
  for(int i=0; i<nt; i++) {Mt[i]=0;};  // unmark all triangles
  Mt[t(cc)]=1; tc++;                   // mark t(cc)
  for(int i=0; i<nv; i++) {Mv[i]=0;};  // unmark all vertices
  while ((tc<nt)&&(tc<n)) {  // while not finished
     for(int i=0; i<nc; i++) {if ((Mv[v(i)]==0)&&(Mt[t(i)]==r)) {Mv[v(i)]=r; distance[v(i)]=d(g(cc),g(i)); md = max(md,distance[v(i)]); };};  // mark vertices of last marked triangles
     for(int i=0; i<nc; i++) {if ((Mt[t(i)]==0)&&(Mv[v(i)]==r)) {Mt[t(i)]=r+1; tc++;};}; // mark triangles incident on last marked vertices
     r++; // increment ring counter
     };
  rings=r;
  return md;
  }

 //  ==========================================================  HIDE TRIANGLES ===========================================
void markRings(int maxr) { // marks vertices and triangles at a graph distance of maxr
  int tc=0; // triangle counter
  int r=1; // ring counter
  for(int i=0; i<nt; i++) {Mt[i]=0;};  // unmark all triangles
  Mt[t(cc)]=1; tc++;                   // mark t(cc)
  for(int i=0; i<nv; i++) {Mv[i]=0;};  // unmark all vertices
  while ((tc<nt)&&(r<=maxr)) {  // while not finished
     for(int i=0; i<nc; i++) {if ((Mv[v(i)]==0)&&(Mt[t(i)]==r)) {Mv[v(i)]=r;};};  // mark vertices of last marked triangles
     for(int i=0; i<nc; i++) {if ((Mt[t(i)]==0)&&(Mv[v(i)]==r)) {Mt[t(i)]=r+1; tc++;};}; // mark triangles incident on last marked vertices
     r++; // increment ring counter
     };
  rings=r; // sets ring variable for rendring?
  }

void hide() {
  visible[t(cc)]=false; 
  if(!b(cc) && visible[t(o(cc))]) cc=o(cc); else {cc=n(cc); if(!b(cc) && visible[t(o(cc))]) cc=o(cc); else {cc=n(cc); if(!b(cc) && visible[t(o(cc))]) cc=o(cc); };};
  }
void purge(int k) {for(int i=0; i<nt; i++) visible[i]=Mt[i]==k;} // hides triangles marked as k


// ============================================= Bounding box and detail size =======================================

  // enclosing box
  Mesh computeBox() { // computes center Cbox and half-diagonal Rbox of minimax box
    pt Lbox =  P(G[0]);  pt Hbox =  P(G[0]);
    for (int i=1; i<nv; i++) { 
      Lbox.x=min(Lbox.x,G[i].x); Lbox.y=min(Lbox.y,G[i].y); Lbox.z=min(Lbox.z,G[i].z);
      Hbox.x=max(Hbox.x,G[i].x); Hbox.y=max(Hbox.y,G[i].y); Hbox.z=max(Hbox.z,G[i].z); 
      };
    Cbox.set(P(Lbox,Hbox));  rbox=d(Cbox,Hbox); 
    float el=averageEdgeLength()/10; 
    if(el==0) r=rbox/100; else r=min(el,rbox/100);
    println("r="+r);
    F.set(Cbox); E.set(P(F,rbox*2,K));  // ************************************************* changes view !!!
    return this;
    };

  Mesh moveBy(vec V) {for(int v=0; v<nv; v++) G[v].add(V); Cbox.add(V); return this;}
  Mesh moveTo(pt P) {moveBy(V(Cbox,P)); return this;}
  void scaleToFit() {float s=width/rbox; for(int v=0; v<nv; v++) G[v].mul(s);}
  void scale(float s) {for(int v=0; v<nv; v++) G[v].mul(s);}
  void scale(float x, float y, float z) {for(int v=0; v<nv; v++) G[v].mul(x,y,z);}
  Mesh perturb() {float s=r/10; if(s==0) s=0.1; for(int v=0; v<nv; v++) G[v].add(V(random(s*2)-s,random(s*2)-s,random(s*2)-s)); return this;}
// ============================================= O TABLE CONSTRUCTION =========================================
  boolean [] NME = new boolean [3*maxnt];  // E[c] is true when c faces a non-anifold edge 
  void hook(int c, int d) {O[c]=d; O[d]=c;}  // make opposite
  void computeOnaive() {                        // sets the O table from the V table, assumes consistent orientation of triangles
    resetCounters();
    for (int i=0; i<3*nt; i++) {O[i]=i;};                              // init O table to -1: has no opposite (i.e. is a border corner)
    for (int i=0; i<nc; i++) for (int j=i+1; j<nc; j++)               // for each corner i, for each other corner j
        if( (v(n(i))==v(p(j))) && (v(p(i))==v(n(j))) ) hook(i,j);    // make i and j opposite if they match         
    }
  void computeO() { // computes O for oriented non-manifold meshes
    for (int c=0; c<nc; c++) NME[c]=false; // assume initially that no edge is non-manifold
    int val[] = new int [nv]; for (int v=0; v<nv; v++) val[v]=0;  
    println("*2*");
    for (int c=0; c<nc; c++) val[v(c)]++;   //  val[v] : vertex valence
    println("*3*");
    int fic[] = new int [nv]; int rfic=0; for (int v=0; v<nv; v++) {fic[v]=rfic; rfic+=val[v];};  // fic[v] : head of list of incident corners
    for (int v=0; v<nv; v++) val[v]=0;   // reset valences to be reused to track how many incident corners were already encountered for each vertex
    int [] C = new int [nc]; // will be filed with corner IDs c, sorted by v(c): for each vertex, the list of its val[v] incident corners starts at C[fic[v]]
    for (int c=0; c<nc; c++) C[fic[v(c)]+val[v(c)]++]=c;  // fill C, using val[v] to keep track of the end of the list for v
    for (int c=0; c<nc; c++) O[c]=c;    // init O table so that each corner has no opposite (i.e. faces a border edge)
    for (int v=0; v<nv; v++)             // for each vertex...
      for (int a=fic[v]; a<fic[v]+val[v]-1; a++) for (int b=a+1; b<fic[v]+val[v]; b++)  { // for each pair (C[a],C[b[]) of its incident corners
       int c=n(C[a]), d=p(C[b]); // we'll test whether corners c and d match as unclaimed opposites
       if(v(n(c))==v(p(d)))  // they are facig each other (we assume the mesh was properly oriented
          if(b(c) && b(d)) { // both were unclaimed, so they can be hooked as opposites, but these are not ordered around their shared edge
            for (int e=b+1; e<fic[v]+val[v]; e++) { // look for a better opposite around the shared edge 
               int f=n(C[e]); 
               if(v(n(c))==v(p(f)) && b(f)) if(ang(c,f)<ang(c,d)) d=f;   // *** JAREK
               }
            hook(c,d);
            }        
          else {NME[c]=true; NME[o(c)]=true; NME[d]=true; NME[o(d)]=true;} // if any one was claimed, mark them and their possible opposites as facing a non-manifold edge
          //
       } // end of pairs               
    } // end of computeO
  float ang(int c, int f) {
    vec T=U(V(g(n(c)),g(p(c))));  // tangent to shared edge
    vec N=U(N(V(g(c),g(n(c))),V(g(c),g(p(c))))); // normal to t(c)
    vec B=U(N(N,T)); // binormal pointing inside triangle
    pt Q=g(n(c)); pt P=g(f);
    vec QP=V(Q,P);
    vec V=A(QP,-d(QP,T),T);
    float a = atan2(d(V,N),d(V,B)); if(a<0) a+=TWO_PI;
    return a;
    }
  
  void showMBEs() {for (int c=0; c<nc; c++) if (b(c) && !NME[c]) drawEdge(c); }        // draws all manifold border edges 
  void showNMBEs() {for (int c=0; c<nc; c++) if (b(c) && NME[c]) drawEdge(c); }        // draws all non-manifold border edges 
  void showNMNBEs() {for (int c=0; c<nc; c++) if(!b(c) && NME[c]) drawEdge(c);}         // draws all non-manifold non border edges   

// ============================================= DISPLAY CORNERS and LABELS =============================
  void showCorner(int c, float r) {show(cg(c),r); };   // renders corner c as small ball
  
  void showc(){noStroke(); fill(dred); showCorner(cc,r); } // displays corner markers

  void showcc(){noStroke(); fill(blue); showCorner(sc,r);  fill(green); showCorner(o(cc),r*.9); fill(dred); showCorner(cc,r); } // displays corner markers
  
  void showLabels() { // displays IDs of corners, vertices, and triangles
   fill(black); 
   for (int i=0; i<nv; i++) {show(G[i],"v"+str(i),V(10,Nv[i])); }; 
   for (int i=0; i<nc; i++) {show(corner(i),"c"+str(i),V(10,Nt[i])); }; 
   for (int i=0; i<nt; i++) {show(triCenter(i),"t"+str(i),V(10,Nt[i])); }; 
   noFill();
   }

  String SCC() { String S = "cc = "+str(cc);
     
     if(b(cc)) S=S+", border";  else  S = S+", o = "+str(o(cc))+" , dihedral angle = "+str(toDeg(ang(cc,o(cc)))); 
     if(NME[cc]) S=S+", faces NME";   
     return S;}
// ============================================= DISPLAY VERTICES =======================================
  void showVertices () {
    noStroke(); noSmooth(); fill(metal,150);
    for (int v=0; v<nv; v++)  {
      if (vm[v]==0) fill(brown);
      if (vm[v]==1) fill(red,150);
      if (vm[v]==2) fill(green,150);
      if (vm[v]==3) fill(blue,150);
       show(G[v],r);  
      }
    noFill();
    }

// ============================================= DISPLAY EDGES =======================================
  void showBorder() {for (int c=0; c<nc; c++) {if (b(c) && visible[t(c)] && !NME[c]) {drawEdge(c);}; }; };         // draws all manifold border edges 
  void showEdges () {for(int c=0; c<nc; c++) drawEdge(c); };  
  void drawEdge(int c) {show(g(p(c)),g(n(c))); };  // draws edge of t(c) opposite to corner c
  void drawSilhouettes() {for (int c=0; c<nc; c++) if (c<o(c) && frontFacing(t(c))!=frontFacing(t(o(c)))) drawEdge(c); }  

// ============================================= DISPLAY TRIANGLES =======================================
  // displays triangle if marked as visible using flat or smooth shading (depending on flatShading variable
  void simpleShade(int t) {beginShape(); vertex(g(3*t)); vertex(g(3*t+1)); vertex(g(3*t+2));  endShape(CLOSE); }
  void shade(int t) { // displays triangle t if visible
    if(visible[t])  
      if(flatShading) {beginShape(); vertex(g(3*t)); vertex(g(3*t+1)); vertex(g(3*t+2));  endShape(CLOSE); }
      else {beginShape(); normal(Nv[v(3*t)]); vertex(g(3*t)); normal(Nv[v(3*t+1)]); vertex(g(3*t+1)); normal(Nv[v(3*t+2)]); vertex(g(3*t+2));  endShape(CLOSE); }; 
    }
  
  // display shrunken and offset triangles
  void showShrunkT(int t, float e) {if(visible[t]) showShrunk(g(3*t),g(3*t+1),g(3*t+2),e);}
  void showSOT(int t) {if(visible[t]) showShrunkOffsetT(t,r,r/2);}
  void showSOT() {if(visible[t(cc)]) showShrunkOffsetT(t(cc),r,r/2);}
  void showShrunkOffsetT(int t, float e, float h) {if(visible[t]) showShrunkOffset(g(3*t),g(3*t+1),g(3*t+2),e,h);}
  void showShrunkT() {int t=t(cc); if(visible[t]) showShrunk(g(3*t),g(3*t+1),g(3*t+2),r/2);}
  void showShrunkOffsetT(float h) {int t=t(cc); if(visible[t]) showShrunkOffset(g(3*t),g(3*t+1),g(3*t+2),r,h);}

  // display front and back triangles shrunken if showEdges  
  Boolean frontFacing(int t) {return !cw(E,g(3*t),g(3*t+1),g(3*t+2)); } 
  void showFrontTrianglesSimple() {for(int t=0; t<nt; t++) if(frontFacing(t)) {if(showEdges) showShrunkT(t,1); else shade(t);}};  
  void showFront() {for(int t=0; t<nt; t++) if(frontFacing(t)) shade(t);}  
  void showTs() {for(int t=0; t<nt; t++) simpleShade(t);}  
 
  void showBackTriangles() {for(int t=0; t<nt; t++) if(!frontFacing(t)) shade(t);};  
  void showAllTriangles() {for(int t=0; t<nt; t++) if(showEdges) showShrunkT(t,r); else shade(t);};  
  void showMarkedTriangles() {for(int t=0; t<nt; t++) if(visible[t]&&Mt[t]!=0) {fill(ramp(Mt[t],rings)); showShrunkOffsetT(t,r,r/2); }};  

//  ==========================================================  PROCESS EDGES ===========================================
  // FLIP 
  void flip(int c) {      // flip edge opposite to corner c, FIX border cases
    if (b(c)) return;
      V[n(o(c))]=v(c); V[n(c)]=v(o(c));
      int co=o(c); 
      
      O[co]=r(c); 
      if(!b(p(c))) O[r(c)]=co; 
      if(!b(p(co))) O[c]=r(co); 
      if(!b(p(co))) O[r(co)]=c; 
      O[p(c)]=p(co); O[p(co)]=p(c);  
    }
  void flip() {flip(cc); pc=cc; cc=p(cc);}

  void flipWhenLonger() {for (int c=0; c<nc; c++) if (d(g(n(c)),g(p(c)))>d(g(c),g(o(c)))) flip(c); } 

  int cornerOfShortestEdge() {  // assumes manifold
    float md=d(g(p(0)),g(n(0))); int ma=0;
    for (int a=1; a<nc; a++) if (vis(a)&&(d(g(p(a)),g(n(a)))<md)) {ma=a; md=d(g(p(a)),g(n(a)));}; 
    return ma;
    } 
  void findShortestEdge() {cc=cornerOfShortestEdge();  } 

//  ========================================================== PROCESS  TRIANGLES ===========================================
  pt triCenter(int i) {return P( G[V[3*i]], G[V[3*i+1]], G[V[3*i+2]] ); };  
  pt triCenter() {return triCenter(t());}  // computes center of triangle t(i) 
  void writeTri (int i) {println("T"+i+": V = ("+V[3*i]+":"+v(o(3*i))+","+V[3*i+1]+":"+v(o(3*i+1))+","+V[3*i+2]+":"+v(o(3*i+2))+")"); };
   
//  ==========================================================  NORMALS ===========================================
  void normals() {computeTriNormals(); computeVertexNormals(); }
  void computeValenceAndResetNormals() {      // caches valence of each vertex
    for (int i=0; i<nv; i++) {Nv[i]=V();  Valence[i]=0;};  // resets the valences to 0
    for (int i=0; i<nc; i++) {Valence[v(i)]++; };
    }
  vec triNormal(int t) { return N(V(g(3*t),g(3*t+1)),V(g(3*t),g(3*t+2))); };  
  void computeTriNormals() {for (int i=0; i<nt; i++) {Nt[i].set(triNormal(i)); }; };             // caches normals of all tirangles
  void computeVertexNormals() {  // computes the vertex normals as sums of the normal vectors of incident tirangles scaled by area/2
    for (int i=0; i<nv; i++) {Nv[i].set(0,0,0);};  // resets the valences to 0
    for (int i=0; i<nc; i++) {Nv[v(i)].add(Nt[t(i)]);};
    for (int i=0; i<nv; i++) {Nv[i].normalize();};            };
  void showVertexNormals() {for (int i=0; i<nv; i++) show(G[i],V(10*r,Nv[i]));  };
  void showTriNormals() {for (int i=0; i<nt; i++) show(triCenter(i),V(10*r,U(Nt[i])));  };
  void normalizeTriNormals() {for (int i=0; i<nt; i++) Nt[i].normalize(); };
  void showNormals() {if(flatShading) showTriNormals(); else showVertexNormals(); }
  vec normalTo(int m) {vec N=V(); for (int i=0; i<nt; i++) if (tm[i]==m) N.add(triNormal(i)); return U(N); }

//  ==========================================================  VOLUME ===========================================
  float volume() {float v=0; for (int i=0; i<nt; i++) v+=triVol(i); vol=v/6; return vol; }
  float volume(int m) {float v=0; for (int i=0; i<nt; i++) if (tm[i]==m) v+=triVol(i); return v/6; }
  float triVol(int t) { return m(P(),g(3*t),g(3*t+1),g(3*t+2)); };  

  float surfaceArea() {float s=0; for (int i=0; i<nt; i++) s+=triSurf(i); surf=s; return surf; }
  float surfaceArea(int m) {float s=0; for (int i=0; i<nt; i++) if (tm[i]==m) s+=triSurf(i); return s; }
  float triSurf(int t) { if(visible[t]) return area(g(3*t),g(3*t+1),g(3*t+2)); else return 0;};  

// ============================================================= SMOOTHING ============================================================
  void computeLaplaceVectors() {  // computes the vertex normals as sums of the normal vectors of incident tirangles scaled by area/2
    computeValenceAndResetNormals();
    for (int i=0; i<3*nt; i++) {Nv[v(p(i))].add(V(g(p(i)),g(n(i))));};
    for (int i=0; i<nv; i++) {Nv[i].div(Valence[i]);};                         };
  void tuck(float s) {for (int i=0; i<nv; i++) G[i].add(s,Nv[i]); };  // displaces each vertex by a fraction s of its normal
  void smoothen() {normals(); computeLaplaceVectors(); tuck(0.6); computeLaplaceVectors(); tuck(-0.6);};

// ============================================================= SUBDIVISION ============================================================
  int w (int c) {return(W[c]);};               // temporary indices to mid-edge vertices associated with corners during subdivision

  void splitEdges() {            // creates a new vertex for each edge and stores its ID in the W of the corner (and of its opposite if any)
    for (int i=0; i<3*nt; i++) {  // for each corner i
      if(b(i)) {G[nv]=P(g(n(i)),g(p(i))); W[i]=nv++;}
        else {if(i<o(i)) {G[nv]=P(g(n(i)),g(p(i))); W[o(i)]=nv; W[i]=nv++; }; }; }; } // if this corner is the first to see the edge
  
  void bulge() {              // tweaks the new mid-edge vertices according to the Butterfly mask
    for (int i=0; i<3*nt; i++) {
      if((!b(i))&&(i<o(i))) {    // no tweak for mid-vertices of border edges
       if (!b(p(i))&&!b(n(i))&&!b(p(o(i)))&&!b(n(o(i))))
        {G[W[i]].add(0.25,V(P(P(g(l(i)),g(r(i))),P(g(l(o(i))),g(r(o(i))))),(P(g(i),g(o(i)))))); }; }; }; };
  
  void splitTriangles() {    // splits each tirangle into 4
    for (int i=0; i<3*nt; i=i+3) {
      V[3*nt+i]=v(i); V[n(3*nt+i)]=w(p(i)); V[p(3*nt+i)]=w(n(i));
      V[6*nt+i]=v(n(i)); V[n(6*nt+i)]=w(i); V[p(6*nt+i)]=w(p(i));
      V[9*nt+i]=v(p(i)); V[n(9*nt+i)]=w(n(i)); V[p(9*nt+i)]=w(i);
      V[i]=w(i); V[n(i)]=w(n(i)); V[p(i)]=w(p(i));
      };
    nt=4*nt; nc=3*nt;  };
    
  Mesh refine() { updateON(); splitEdges(); bulge(); splitTriangles(); updateON(); return this;}
  Mesh split() { updateON(); splitEdges(); splitTriangles(); updateON(); return this;}

//  ==========================================================  GARBAGE COLLECTION ===========================================
void clean() {
   excludeInvisibleTriangles();  println("excluded");
   compactVO(); println("compactedVO");
   compactV(); println("compactedV");
   normals(); println("normals");
   computeO();
   resetMarkers();
   }  // removes deleted triangles and unused vertices
   
void excludeInvisibleTriangles () {for (int b=0; b<nc; b++) {if (!visible[t(o(b))]) {O[b]=b;};};}

void compactVO() {  
  int[] U = new int [nc];
  int lc=-1; for (int c=0; c<nc; c++) {if (visible[t(c)]) {U[c]=++lc; }; };
  for (int c=0; c<nc; c++) {if (!b(c)) {O[c]=U[o(c)];} else {O[c]=c;}; };
  int lt=0;
  for (int t=0; t<nt; t++) {
    if (visible[t]) {
      V[3*lt]=V[3*t]; V[3*lt+1]=V[3*t+1]; V[3*lt+2]=V[3*t+2]; 
      O[3*lt]=O[3*t]; O[3*lt+1]=O[3*t+1]; O[3*lt+2]=O[3*t+2]; 
      visible[lt]=true; 
      lt++;
      };
    };
  nt=lt; nc=3*nt;    
  println("      ...  NOW: nv="+nv +", nt="+nt +", nc="+nc );
  }

void compactV() {  
  println("COMPACT VERTICES: nv="+nv +", nt="+nt +", nc="+nc );
  int[] U = new int [nv];
  boolean[] deleted = new boolean [nv];
  for (int v=0; v<nv; v++) {deleted[v]=true;};
  for (int c=0; c<nc; c++) {deleted[v(c)]=false;};
  int lv=-1; for (int v=0; v<nv; v++) {if (!deleted[v]) {U[v]=++lv; }; };
  for (int c=0; c<nc; c++) {V[c]=U[v(c)]; };
  lv=0;
  for (int v=0; v<nv; v++) {
    if (!deleted[v]) {G[lv].set(G[v]);  deleted[lv]=false; 
      lv++;
      };
    };
 nv=lv;
 println("      ...  NOW: nv="+nv +", nt="+nt +", nc="+nc );
  }

// ============================================================= ARCHIVAL ============================================================
boolean flipOrientation=false;            // if set, save will flip all triangles

void saveMeshVTS() {
  String savePath = selectOutput("Select or specify .vts file where the mesh will be saved");  // Opens file chooser
  if (savePath == null) {println("No output file was selected..."); return;}
  else println("writing to "+savePath);
  saveMeshVTS(savePath);
  }

void saveMeshVTS(String fn) {
  String [] inppts = new String [nv+1+nt+1];
  int s=0;
  inppts[s++]=str(nv);
  for (int i=0; i<nv; i++) {inppts[s++]=str(G[i].x)+","+str(G[i].y)+","+str(G[i].z);};
  inppts[s++]=str(nt);
  if (flipOrientation) {for (int i=0; i<nt; i++) {inppts[s++]=str(V[3*i])+","+str(V[3*i+2])+","+str(V[3*i+1]);};}
    else {for (int i=0; i<nt; i++) {inppts[s++]=str(V[3*i])+","+str(V[3*i+1])+","+str(V[3*i+2]);};};
  saveStrings(fn,inppts);  
  };
  
Mesh loadMeshVTS() {
  String loadPath = selectInput("Select .vts mesh file to load");  // Opens file chooser
  if (loadPath == null) {println("No input file was selected..."); return this;}
  else println("reading from "+loadPath); 
  loadMeshVTS(loadPath);
  return this;
 }

Mesh loadMeshVTS(String fn) {
  println("loading: "+fn); 
  String [] ss = loadStrings(fn);
  String subpts;
  int s=0;   int comma1, comma2;   float x, y, z;   int a, b, c;
  nv = int(ss[s++]);
    println("nv="+nv);
    for(int k=0; k<nv; k++) {int i=k+s; 
      comma1=ss[i].indexOf(',');   
      x=float(ss[i].substring(0, comma1));
      String rest = ss[i].substring(comma1+1, ss[i].length());
      comma2=rest.indexOf(',');    y=float(rest.substring(0, comma2)); z=float(rest.substring(comma2+1, rest.length()));
      G[k].set(x,y,z);
      };
  s=nv+1;
  nt = int(ss[s]); nc=3*nt;
  println("nt="+nt);
  s++;
  for(int k=0; k<nt; k++) {int i=k+s;
      comma1=ss[i].indexOf(',');   a=int(ss[i].substring(0, comma1));  
      String rest = ss[i].substring(comma1+1, ss[i].length()); comma2=rest.indexOf(',');  
      b=int(rest.substring(0, comma2)); c=int(rest.substring(comma2+1, rest.length()));
      V[3*k]=a;  V[3*k+1]=b;  V[3*k+2]=c;
    }
  println("done loading");
  return this;  
  }; 
  
  void makeAllVisible() { for(int i=0; i<nt; i++) visible[i]=true; }
        
  pt closestProjectionMark(pt P) {
    float md=d(P,g(0));
    int cc=0; // corner of closest cell
    int type = 0; // type of closest projection: - = vertex, 1 = edge, 2 = triangle
    pt Q = P(); // closest point
    for (int c=0; c<nc; c++) if (d(P,g(c))<md) {Q.set(g(c)); cc=c; type=0; md=d(P,g(c));} 
    for (int c=0; c<nc; c++) if (c<=o(c)) {float d = distPE(P,g(n(c)),g(p(c))); if (d<md && projPonE(P,g(n(c)),g(p(c)))) {md=d; cc=c; type=1; Q=CPonE(P,g(n(c)),g(p(c)));} } 
    if(onTriangles) 
       for (int t=0; t<nt; t++) {int c=3*t; float d = distPtPlane(P,g(c),g(n(c)),g(p(c))); if (d<md && projPonT(P,g(c),g(n(c)),g(p(c)))) {md=d; cc=c; type=2; Q=CPonT(P,g(c),g(n(c)),g(p(c)));} } 
    if(type==2) tm[t(cc)]=1;
    if(type==1) {tm[t(cc)]=2; tm[t(o(cc))]=2;}
    if(type==0) {tm[t(cc)]=3; int c=s(cc); while(c!=cc) {c=s(c); tm[t(c)]=3;} }
    return Q;
    }
 
  void drawClosestProjections(Curve L) {
    for (int p=0; p<L.n; p++) {drawLineToClosestProjection(L.P[p]);};  
    }
    
  
 void drawLineToClosestProjection(pt P) {
    float md=d(P,g(0));
    int cc=0; // corner of closest cell
    int type = 0; // type of closest projection: - = vertex, 1 = edge, 2 = triangle
    pt Q = P(); // closest point
    for (int c=0; c<nc; c++) if (d(P,g(c))<md) {Q.set(g(c)); cc=c; type=0; md=d(P,g(c));} 
    for (int c=0; c<nc; c++) if (c<=o(c)) {float d = distPE(P,g(n(c)),g(p(c))); if (d<md && projPonE(P,g(n(c)),g(p(c)))) {md=d; cc=c; type=1; Q=CPonE(P,g(n(c)),g(p(c)));} } 
    if(onTriangles) 
      for (int t=0; t<nt; t++) {int c=3*t; float d = distPtPlane(P,g(c),g(n(c)),g(p(c))); if (d<md && projPonT(P,g(c),g(n(c)),g(p(c)))) {md=d; cc=c; type=2; Q=CPonT(P,g(c),g(n(c)),g(p(c)));} } 
    if(type==2) stroke(dred);   if(type==1) stroke(dgreen);  if(type==0) stroke(dblue);  show(P,Q);   
    }
 
  pt closestProjection(pt P) {  // ************ closest projection of P on this mesh
    float md=d(P,G[0]);
    pt Q = P();
    int v=0; for (int i=1; i<nv; i++) if (d(P,G[i])<md) {Q=G[i]; md=d(P,G[i]);} 
    for (int c=0; c<nc; c++) if (c<=o(c)) {
         float d = abs(distPE(P,g(n(c)),g(p(c)))); 
         if (d<md && projPonE(P,g(n(c)),g(p(c)))) {md=d; Q=CPonE(P,g(n(c)),g(p(c)));} 
         } 
    for (int t=0; t<nt; t++) {
         int c=3*t; 
         float d = distPtPlane(P,g(c),g(n(c)),g(p(c))); 
         if (d<md && projPonT(P,g(c),g(n(c)),g(p(c)))) {md=d; Q=CPonT(P,g(c),g(n(c)),g(p(c)));} 
         } 
    return Q;
    }
 
   pt closestProjection(pt P, int k) { //closest projection on triangles marked as tm[t]==k
    float md=d(P,G[0]);
    pt Q = P();
    for (int c=0; c<nc; c++) if(tm[t(c)]==k) if (d(P,g(c))<md) {Q=g(c); md=d(P,g(c));} 
    for (int c=0; c<nc; c++)  if(tm[t(c)]==k) if (c<=o(c)) {float d = distPE(P,g(n(c)),g(p(c))); if (d<md && projPonE(P,g(n(c)),g(p(c)))) {md=d; Q=CPonE(P,g(n(c)),g(p(c)));} } 
    for (int t=0; t<nt; t++)  if(tm[t]==k) {int c=3*t; float d = distPtPlane(P,g(c),g(n(c)),g(p(c))); if (d<md && projPonT(P,g(c),g(n(c)),g(p(c)))) {md=d; Q=CPonT(P,g(c),g(n(c)),g(p(c)));} } 
    return Q;
    }
    
    
  int closestVertexNextCorner(pt P, int k) { //closest projection on triangles marked as tm[t]==k
    int bc=0; // best corner index
    float md=d(P,g(p(bc)));
    for (int c=0; c<nc; c++) if(tm[t(c)]==k && tm[t(o(c))]!=k) if (d(P,g(p(c)))<md) {bc=c; md=d(P,g(p(c)));} 
    return bc;
    }

  int closestVertex(pt P, int k) { //closest projection on triangles marked as tm[t]==k
    int v=0;
    float md=d(P,G[v]);
    for (int c=0; c<nc; c++) if(tm[t(c)]==k) if (d(P,g(c))<md) {v=v(c); md=d(P,g(c));} 
    return v;
    }
    
  int nextAlongSplit(int c, int mk) {
    c=p(c);
    if(tm[t(o(c))]==mk) return c;
    c=p(o(c));
    while(tm[t(o(c))]!=mk) c=p(o(c));
    return c;
    }  

  int prevAlongSplit(int c, int mk) {
    c=n(c);
    if(tm[t(o(c))]==mk) return c;
    c=n(o(c));
    while(tm[t(o(c))]!=mk) c=n(o(n(c)));
    return c;
    }  
    

     
  } // ==== END OF MESH CLASS
  
vec labelD=new vec(-4,+4, 12);           // offset vector for drawing labels  

float distPE (pt P, pt A, pt B) {return n(N(V(A,B),V(A,P)))/d(A,B);} // distance from P to edge(A,B)
float distPtPlane (pt P, pt A, pt B, pt C) {vec N = U(N(V(A,B),V(A,C))); return abs(d(V(A,P),N));} // distance from P to plane(A,B,C)
Boolean projPonE (pt P, pt A, pt B) {return d(V(A,B),V(A,P))>0 && d(V(B,A),V(B,P))>0;} // P projects onto the interior of edge(A,B)
Boolean projPonT (pt P, pt A, pt B, pt C) {vec N = U(N(V(A,B),V(A,C))); return m(N,V(A,B),V(A,P))>0 && m(N,V(B,C),V(B,P))>0 && m(N,V(C,A),V(C,P))>0 ;} // P projects onto the interior of edge(A,B)
pt CPonE (pt P, pt A, pt B) {return P(A,d(V(A,B),V(A,P))/d(V(A,B),V(A,B)),V(A,B));}
pt CPonT (pt P, pt A, pt B, pt C) {vec N = U(N(V(A,B),V(A,C))); return P(P,-d(V(A,P),N),N);}
