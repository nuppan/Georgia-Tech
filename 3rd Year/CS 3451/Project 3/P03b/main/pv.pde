//*****************************************************************************
// TITLE:         GEOMETRY UTILITIES OF THE GSB TEMPLATE  
// DESCRIPTION:   Classes and functions for manipulating points, vectors, and frames in the Geometry SandBox Geometry (GSB)  
// AUTHOR:        Prof Jarek Rossignac
// DATE CREATED:  September 2009
// EDITS:         Simplified July 2012
//*****************************************************************************

//************************************************************************
//**** Functions
//************************************************************************

// P: create or copy vectors and points 
vec V(vec V) {return new vec(V.x,V.y); };                                                             // make copy of vector V
vec V(vec U,vec V) {return new vec(U.x+V.x,U.y+V.y); };                                                             // make copy of vector V
vec V(float x, float y) {return new vec(x,y); };                                                      // make vector (x,y)
vec V(pt P, pt Q) {return new vec(Q.x-P.x,Q.y-P.y);};                                                 // PQ (make vector Q-P from P to Q
vec U(vec V) {float n = n(V); if (n==0) return new vec(0,0); else return new vec(V.x/n,V.y/n);};      // V/||V|| (Unit vector : normalized version of V)
vec V(float s,vec V) {return new vec(s*V.x,s*V.y);};                                                  // sV

pt P() {return P(0,0); };                                                                            // make point (0,0)
pt P(float x, float y) {return new pt(x,y); };                                                       // make point (x,y)
pt P(pt P) {return P(P.x,P.y); };                                                                    // make copy of point A
pt P(float s, pt A) {return new pt(s*A.x,s*A.y); };                                                  // sA
pt P(pt P, vec V) {return P(P.x + V.x, P.y + V.y); }                                                 //  P+V (P transalted by vector V)
pt P(pt P, float s, vec V) {return P(P,V(s,V)); }                                                    //  P+sV (P transalted by sV)
pt P(pt A, float s, pt B) {return P(A.x+s*(B.x-A.x),A.y+s*(B.y-A.y)); };                             // A+sAB
pt P(pt A, pt B) {return P((A.x+B.x)/2,(A.y+B.y)/2); };                                              // (A+B)/2

// show points, edges, triangles, and quads
void show(pt P, float r) {ellipse(P.x, P.y, 2*r, 2*r);};                                              // draws circle of center r around P
void show(pt P) {ellipse(P.x, P.y, 6,6);};                                                            // draws small circle around point
void show(pt P, pt Q) {line(P.x,P.y,Q.x,Q.y); };                                                      // draws edge (P,Q)
void v(pt P) {vertex(P.x,P.y);};                                                                      // vertex for drawing polygons between beginShape() and endShape()
void label(pt P, String S) {text(S, P.x-4,P.y+6.5); }                                                 // writes string S next to P on the screen ( for example label(P[i],str(i));)
void label(pt P, vec V, String S) {text(S, P.x-3.5+V.x,P.y+7+V.y); }                                  // writes string S at P+V

// Input points: mouse & canvas 
pt Mouse() {return P(mouseX,mouseY);};                                                                 // returns point at current mouse location
pt Pmouse() {return P(pmouseX,pmouseY);};                                                              // returns point at previous mouse location
pt ScreenCenter() {return P(width/2,height/2);}                                                        //  point in center of  canvas
void drag(pt P) { P.x+=mouseX-pmouseX; P.y+=mouseY-pmouseY; }                                          // adjusts P by mouse drag vector

// measure points (equality, distance)
float dot(vec U, vec V) {return U.x*V.x+U.y*V.y; }
float det(vec U, vec V) {return dot(U,R(V)); }
vec R(vec V) {return new vec(-V.y,V.x);};
float n(vec V) {return sqrt(sq(V.x)+sq(V.y));};                                                       // n(V): ||V|| (norm: length of V)
float d(pt P, pt Q) {return sqrt(d2(P,Q));  };                                                       // ||AB|| (Distance)
float d2(pt P, pt Q) {return sq(Q.x-P.x)+sq(Q.y-P.y); };                                             // AB*AB (Distance squared)

//************************************************************************
//****  CLASSES
//************************************************************************
// points
class pt { float x=0,y=0;
  int rad = 3;
  pt (float px, float py) {x = px; y = py;};
  void setRad(int r){rad = r;};
  pt (pt P) {x = P.x; y = P.y;};
  pt setTo(float px, float py) {x = px; y = py; return this;};  
  pt setTo(pt P) {x = P.x; y = P.y; return this;}; 
  pt moveWithMouse() { x += mouseX-pmouseX; y += mouseY-pmouseY;  return this;}; 
  pt translateTowards(float s, pt P) {x+=s*(P.x-x);  y+=s*(P.y-y);  return this;};    
  pt add(float u, float v) {x += u; y += v; return this;}                       
  pt add(pt P) {x += P.x; y += P.y; return this;};        
  pt add(float s, pt P)   {x += s*P.x; y += s*P.y; return this;};   
  pt add(vec V) {x += V.x; y += V.y; return this;}                              
  pt add(float s, vec V) {return (new pt(x+s*V.x,y+s*V.y));}  
  pt show() {ellipse(x,y,rad,rad); return this;}                 
  pt v() {vertex(x,y); return this;}                 
  } // end of pt class

// vectors
class vec { float x=0,y=0; 
  vec (float px, float py) {x = px; y = py;};
   } // end vec class
 


