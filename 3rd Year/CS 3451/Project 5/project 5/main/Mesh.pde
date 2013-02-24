class Mesh {
  int maxnv = 45000;                         //  max number of vertices
  int maxnt = maxnv*2;                       // max number of triangles
  int nv = 0;                                // current  number of vertices
  int nt = 0;                                // current number of triangles
  int nc = 0;                                // current number of corners (3 per triangle)
  int nvr=0, ntr=0, ncr=0;                   // remember state to restore
  int cc=0, pc=0, sc=0;                      // current, previous, saved corners 
  float vol=0, surf=0, edgeLength=0;         // vol and surface and average edge length

  // BOUNDINGBOX
  pt Cbox = new pt(width/2, height/2, 0);                   // mini-max box center
  float rbox=1000;                                        // half-diagonal of enclosing box
  float r=2;                                // detail size: radius of spheres for displaying vertices and of gaps between triangles when showing edges

  // rendering modes
  Boolean flatShading=false, showEdges=false;  // showEdges shoes edges as gaps. Smooth shading works only when !showEdge

    // primary tables
  pt[] G = new pt [maxnv];                   // geometry table (vertices)
  int[] V = new int [3*maxnt];               // V table (triangle/vertex indices)
  int[] O = new int [3*maxnt];               // O table (opposite corner indices)

  // Normal vectors
  vec[] Nv = new vec [maxnv];                 // vertex normals or laplace vectors
  vec[] Nt = new vec [maxnt];                // triangles normals

  // vertex markers
  int[] Mv = new int[maxnv];                  // vertex markers
  int[] vm = new int[3*maxnt];               // vertex markers: 0=not marked, 1=interior, 2=border, 3=non manifold
  int [] Valence = new int [maxnv];          // vertex valence (count of incident triangles)

  // corner markers
  int[] cm = new int[3*maxnt];               // corner markers: 

  // triangle markers
  boolean[] visible = new boolean[maxnt];     // set if triangle visible, used to hide/unhide and then delete triangles
  boolean [] VisitedT = new boolean [maxnt];  // triangle marked as visited during traversal
  int[] tm = new int[3*maxnt];               // triangle markers: 0=not marked

  Mesh() {
  }

  Mesh declareVectors() {
    for (int i=0; i<maxnv; i++) {
      G[i]=P(); 
      Nv[i]=V();
    };   // init vertices and normals
    for (int i=0; i<maxnt; i++) Nt[i]=V();       // init triangle normals and skeleton lab els
    return this;
  }

  void resetCounters() {
    nv=0; 
    nt=0; 
    nc=0;
  }

  void rememberCounters() {
    nvr=nv; 
    ntr=nt; 
    ncr=nc;
  }

  void restoreCounters() {
    nv=nvr; 
    nt=ntr; 
    nc=ncr;
  }

  float averageEdgeLength() {
    float L=0; 
    for (int c=0; c<nc; c++) L+=d(g(n(c)), g(p(c))); 
    edgeLength=L/nc; 
    return edgeLength;
  }

  void setr() {
    r=averageEdgeLength()/10;
  }

  Mesh empty() {
    nv=0; 
    nt=0; 
    nc=0; 
    return this;
  }

  Mesh resetMarkers() { // reset the seed and current corner and the markers for corners, triangles, and vertices
    cc=0; 
    pc=0; 
    sc=0;
    for (int i=0; i<nv; i++) vm[i]=0;
    for (int i=0; i<nc; i++) cm[i]=0;
    for (int i=0; i<nt; i++) tm[i]=0;
    for (int i=0; i<nt; i++) visible[i]=true;
    return this;
  }

  int addVertex(pt P) { 
    G[nv].set(P); 
    nv++; 
    return nv-1;
  };
  int addVertex(float x, float y, float z) { 
    G[nv].x=x; 
    G[nv].y=y; 
    G[nv].z=z; 
    nv++; 
    return nv-1;
  };

  void addTriangle(int i, int j, int k) {
    V[nc++]=i; 
    V[nc++]=j; 
    V[nc++]=k; 
    visible[nt++]=true;
  } // adds a triangle
  void addTriangle(int i, int j, int k, int m) {
    V[nc++]=i; 
    V[nc++]=j; 
    V[nc++]=k; 
    tm[nt]=m; 
    visible[nt++]=true;
  } // adds a triangle

    Mesh updateON() {
    computeO(); 
    normals(); 
    return this;
  } // recomputes O and normals

    // ============================================= CORNER OPERATORS =======================================
  // operations on a corner
  int t (int c) {
    return int(c/3);
  };              // triangle of corner    
  int n (int c) {
    return 3*t(c)+(c+1)%3;
  };        // next corner in the same t(c)    
  int p (int c) {
    return n(n(c));
  };               // previous corner in the same t(c)  
  int v (int c) {
    return V[c] ;
  };                 // id of the vertex of c             
  int o (int c) {
    return O[c];
  };                  // opposite (or self if it has no opposite)
  int l (int c) {
    return o(n(c));
  };               // left neighbor (or next if n(c) has no opposite)                      
  int r (int c) {
    return o(p(c));
  };               // right neighbor (or previous if p(c) has no opposite)                    
  int s (int c) {
    return n(l(c));
  };               // swings around v(c) or around a border loop
  int u (int c) {
    return p(r(c));
  };               // unswings around v(c) or around a border loop
  int c (int t) {
    return t*3;
  }                    // first corner of triangle t
  boolean b (int c) {
    return O[c]==c;
  };           // if faces a border (has no opposite)
  boolean vis(int c) {
    return visible[t(c)];
  };   // true if tiangle of c is visible

  int getCorner(int vId){
    for(int c=0;c<nc;c++){
      if(V[c]==vId){return c;}
    } 
    return -1;
  }

  // geometry for corner c
  pt g (int c) {
    return G[v(c)];
  };                                        // shortcut to get the point of the vertex v(c) of corner c
  pt cg(int c) {
    pt cPt = P(g(c), 0.3, triCenter(t(c)));  
    return(cPt);
  };   // computes point at corner
  pt corner(int c) {
    return P(g(c), triCenter(t(c)));
  };                 // returns corner point

  // normals fot t(c) (must be precomputed)
  vec Nv (int c) {
    return(Nv[V[c]]);
  }; 
  vec Nv() {
    return Nv(cc);
  }          // shortcut to get the normal of v(c) 
  vec Nt (int c) {
    return(Nt[t(c)]);
  }; 
  vec Nt() {
    return Nt(cc);
  }          // shortcut to get the normal of t(c) 

  // ============================================= Bounding box and detail size =======================================

  // enclosing box
  Mesh computeBox() { // computes center Cbox and half-diagonal Rbox of minimax box
    pt Lbox =  P(G[0]);  
    pt Hbox =  P(G[0]);
    for (int i=1; i<nv; i++) { 
      Lbox.x=min(Lbox.x, G[i].x); 
      Lbox.y=min(Lbox.y, G[i].y); 
      Lbox.z=min(Lbox.z, G[i].z);
      Hbox.x=max(Hbox.x, G[i].x); 
      Hbox.y=max(Hbox.y, G[i].y); 
      Hbox.z=max(Hbox.z, G[i].z);
    };
    Cbox.set(P(Lbox, Hbox));  
    rbox=d(Cbox, Hbox); 
    float el=averageEdgeLength()/10; 
    if (el==0) r=rbox/100; 
    else r=min(el, rbox/100);
    F.set(Cbox); 
    E.set(P(F, rbox*2, K));  // ************************************************* changes view !!!
    return this;
  };

  // ============================================= O TABLE CONSTRUCTION =========================================
  boolean [] NME = new boolean [3*maxnt];  // E[c] is true when c faces a non-anifold edge 
  void hook(int c, int d) {
    O[c]=d; 
    O[d]=c;
  }  // make opposite

  void computeO() { // computes O for oriented non-manifold meshes
    for (int c=0; c<nc; c++) NME[c]=false; // assume initially that no edge is non-manifold
    int val[] = new int [nv]; 
    for (int v=0; v<nv; v++) val[v]=0;  
    for (int c=0; c<nc; c++) val[v(c)]++;   //  val[v] : vertex valence
    int fic[] = new int [nv]; 
    int rfic=0; 
    for (int v=0; v<nv; v++) {
      fic[v]=rfic; 
      rfic+=val[v];
    };  // fic[v] : head of list of incident corners
    for (int v=0; v<nv; v++) val[v]=0;   // reset valences to be reused to track how many incident corners were already encountered for each vertex
    int [] C = new int [nc]; // will be filed with corner IDs c, sorted by v(c): for each vertex, the list of its val[v] incident corners starts at C[fic[v]]
    for (int c=0; c<nc; c++) C[fic[v(c)]+val[v(c)]++]=c;  // fill C, using val[v] to keep track of the end of the list for v
    for (int c=0; c<nc; c++) O[c]=c;    // init O table so that each corner has no opposite (i.e. faces a border edge)
    for (int v=0; v<nv; v++)             // for each vertex...
      for (int a=fic[v]; a<fic[v]+val[v]-1; a++) for (int b=a+1; b<fic[v]+val[v]; b++) { // for each pair (C[a],C[b[]) of its incident corners
        int c=n(C[a]), d=p(C[b]); // we'll test whether corners c and d match as unclaimed opposites
        if (v(n(c))==v(p(d)))  // they are facig each other (we assume the mesh was properly oriented
            if (b(c) && b(d)) { // both were unclaimed, so they can be hooked as opposites, but these are not ordered around their shared edge
            for (int e=b+1; e<fic[v]+val[v]; e++) { // look for a better opposite around the shared edge 
              int f=n(C[e]);
            }
            hook(c, d);
          }        
          else {
            NME[c]=true; 
            NME[o(c)]=true; 
            NME[d]=true; 
            NME[o(d)]=true;
          } // if any one was claimed, mark them and their possible opposites as facing a non-manifold edge
        //
      } // end of pairs
  } // end of computeO

  // ============================================= DISPLAY CORNERS =============================
  void showCorner(int c, float r) {
    show(cg(c), r);
  };   // renders corner c as small ball

  void showLabels() { // displays IDs of corners, vertices, and triangles
    fill(black); 
    for (int i=0; i<nv; i++) {
      show(G[i], "v"+str(i), V(10, Nv[i]));
    }; 
    for (int i=0; i<nc; i++) {
      show(corner(i), "c"+str(i), V(10, Nt[i]));
    }; 
    for (int i=0; i<nt; i++) {
      show(triCenter(i), "t"+str(i), V(10, Nt[i]));
    }; 
    noFill();
  }

  // ============================================= DISPLAY VERTICES =======================================
  void showVertices () {
    noStroke(); 
    noSmooth(); 
    fill(metal, 150);
    for (int v=0; v<nv; v++) {
      if (vm[v]==0) fill(brown);
      if (vm[v]==1) fill(red, 150);
      if (vm[v]==2) fill(green, 150);
      if (vm[v]==3) fill(blue, 150);
      show(G[v], r);
    }
    noFill();
  }

  void showEdges () {
    for (int c=0; c<nc; c++) drawEdge(c);
  };  

  void drawEdge(int c) {
    show(g(p(c)), g(n(c)));
  };  // draws edge of t(c) opposite to corner c  

    // ============================================= DISPLAY TRIANGLES =======================================
  // displays triangle if marked as visible using flat or smooth shading (depending on flatShading variable
  void simpleShade(int t) {
    beginShape(); 
    vertex(g(3*t)); 
    vertex(g(3*t+1)); 
    vertex(g(3*t+2));  
    endShape(CLOSE);
  }

  void shade(int t) { // displays triangle t if visible
    if (visible[t])  
      if (flatShading) {
        beginShape(); 
        vertex(g(3*t)); 
        vertex(g(3*t+1)); 
        vertex(g(3*t+2));  
        endShape(CLOSE);
      }
      else {
        beginShape(); 
        normal(Nv[v(3*t)]); 
        vertex(g(3*t)); 
        normal(Nv[v(3*t+1)]); 
        vertex(g(3*t+1)); 
        normal(Nv[v(3*t+2)]); 
        vertex(g(3*t+2));  
        endShape(CLOSE);
      };
  }

  // display front and back triangles shrunken if showEdges  
  Boolean frontFacing(int t) {
    return !cw(E, g(3*t), g(3*t+1), g(3*t+2));
  } 

  void showFront() {
    for (int t=0; t<nt; t++) if (frontFacing(t)) shade(t);
  }  
  void showTs() {
    for (int t=0; t<nt; t++) simpleShade(t);
  }  

  void showBackTriangles() {
    for (int t=0; t<nt; t++) if (!frontFacing(t)) shade(t);
  };  

  //  ========================================================== PROCESS  TRIANGLES ===========================================
  pt triCenter(int i) {
    return P( G[V[3*i]], G[V[3*i+1]], G[V[3*i+2]] );
  };  

  //  ==========================================================  NORMALS ===========================================
  void normals() {
    computeTriNormals(); 
    computeVertexNormals();
  }

  void computeValenceAndResetNormals() {      // caches valence of each vertex
    for (int i=0; i<nv; i++) {
      Nv[i]=V();  
      Valence[i]=0;
    };  // resets the valences to 0
    for (int i=0; i<nc; i++) {
      Valence[v(i)]++;
    };
  }  

  vec triNormal(int t) { 
    return N(V(g(3*t), g(3*t+1)), V(g(3*t), g(3*t+2)));
  };  
  
  pt[] getVertices(int t){
    pt[] ret = new pt[3];
    ret[0] = g(3*t);ret[1] = g(3*t+1);ret[2] = g(3*t+2);
    return ret;
  }

  void computeTriNormals() {
    for (int i=0; i<nt; i++) {
      Nt[i].set(triNormal(i));
    };
  };             // caches normals of all tirangles

  void computeVertexNormals() {  // computes the vertex normals as sums of the normal vectors of incident tirangles scaled by area/2
    for (int i=0; i<nv; i++) {
      Nv[i].set(0, 0, 0);
    };  // resets the valences to 0
    for (int i=0; i<nc; i++) {
      Nv[v(i)].add(Nt[t(i)]);
    };
    for (int i=0; i<nv; i++) {
      Nv[i].normalize();
    };
  };

  void showVertexNormals() {
    for (int i=0; i<nv; i++) show(G[i], V(10*r, Nv[i]));
  };

  void showTriNormals() {
    for (int i=0; i<nt; i++) show(triCenter(i), V(10*r, U(Nt[i])));
  };

  void normalizeTriNormals() {
    for (int i=0; i<nt; i++) Nt[i].normalize();
  };

  void showNormals() {
    if (flatShading) showTriNormals(); 
    else showVertexNormals();
  }

  vec normalTo(int m) {
    vec N=V(); 
    for (int i=0; i<nt; i++) if (tm[i]==m) N.add(triNormal(i)); 
    return U(N);
  }
}

float distPE (pt P, pt A, pt B) {
  return n(N(V(A, B), V(A, P)))/d(A, B);
} // distance from P to edge(A,B)
float distPtPlane (pt P, pt A, pt B, pt C) {
  vec N = U(N(V(A, B), V(A, C))); 
  return abs(d(V(A, P), N));
} // distance from P to plane(A,B,C)
Boolean projPonE (pt P, pt A, pt B) {
  return d(V(A, B), V(A, P))>0 && d(V(B, A), V(B, P))>0;
} // P projects onto the interior of edge(A,B)
Boolean projPonT (pt P, pt A, pt B, pt C) {
  vec N = U(N(V(A, B), V(A, C))); 
  return m(N, V(A, B), V(A, P))>0 && m(N, V(B, C), V(B, P))>0 && m(N, V(C, A), V(C, P))>0 ;
} // P projects onto the interior of edge(A,B)
pt CPonE (pt P, pt A, pt B) {
  return P(A, d(V(A, B), V(A, P))/d(V(A, B), V(A, B)), V(A, B));
}
pt CPonT (pt P, pt A, pt B, pt C) {
  vec N = U(N(V(A, B), V(A, C))); 
  return P(P, -d(V(A, P), N), N);
}

