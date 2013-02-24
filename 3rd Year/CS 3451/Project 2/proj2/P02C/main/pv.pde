//*****************************************************************************
// TITLE:         GEOMETRY UTILITIES OF THE GSB TEMPLATE  
// DESCRIPTION:   Classes and functions for manipulating points, vectors, and frames in the Geometry SandBox Geometry (GSB)  
// AUTHOR:        Prof Jarek Rossignac
// DATE CREATED:  September 2009
// EDITS:         Revised July 2011
//*****************************************************************************

//************************************************************************
//**** POINTS
//************************************************************************

// P: create or copy points 
pt P() {return P(0,0); };                                                                            // make point (0,0)
pt P(float x, float y) {return new pt(x,y); };                                                       // make point (x,y)
pt P(pt P) {return P(P.x,P.y); };                                                                    // make copy of point A

// show points, edges, triangles, and quads
void show(pt P, float r) {ellipse(P.x, P.y, 2*r, 2*r);};                                              // draws circle of center r around P
void show(pt P) {ellipse(P.x, P.y, 6,6);};                                                            // draws small circle around point
void show(pt P, pt Q) {line(P.x,P.y,Q.x,Q.y); };                                                      // draws edge (P,Q)
void show(pt A, pt B, pt C)  {beginShape();  A.v(); B.v(); C.v(); endShape(CLOSE);}                   // render triangle A, B, C
void show(pt A, pt B, pt C, pt D)  {beginShape();  A.v(); B.v(); C.v(); D.v(); endShape(CLOSE);}      // render quad A, B, C, D
void v(pt P) {vertex(P.x,P.y);};                                                                      // vertex for drawing polygons between beginShape() and endShape()
void vert(pt P) {vertex(P.x,P.y);};                                                                      // vertex for drawing polygons between beginShape() and endShape()
void cross(pt P, float r) {line(P.x-r,P.y,P.x+r,P.y); line(P.x,P.y-r,P.x,P.y+r);};                    // shows P as cross of length r
void cross(pt P) {cross(P,2);};                                                                       // shows P as small cross
void arrow(pt P, pt Q) {arrow(P,V(P,Q)); }                                                            // draws arrow from P to Q
void label(pt P, String S) {text(S, P.x-4,P.y+6.5); }                                                 // writes string S next to P on the screen ( for example label(P[i],str(i));)
void label(pt P, vec V, String S) {text(S, P.x-3.5+V.x,P.y+7+V.y); }                                  // writes string S at P+V

// Input points: mouse & canvas 
pt Mouse() {return P(mouseX,mouseY);};                                                                 // returns point at current mouse location
pt Pmouse() {return P(pmouseX,pmouseY);};                                                              // returns point at previous mouse location
pt MouseInWindow() {float x=mouseX, y=mouseY; x=max(x,0); y=max(y,0); x=min(x,width); y=min(y,height);  return P(x,y);}; // nearest  canvas point to mouse 
pt ScreenCenter() {return P(width/2,height/2);}                                                        //  point in center of  canvas
boolean mouseIsInWindow() {return(((mouseX>0)&&(mouseX<width)&&(mouseY>0)&&(mouseY<height)));};        //  if mouse is in  canvas
void drag(pt P) { P.x+=mouseX-pmouseX; P.y+=mouseY-pmouseY; }                                          // adjusts P by mouse drag vector

// measure points (equality, distance)
boolean isSame(pt A, pt B) {return (A.x==B.x)&&(A.y==B.y) ;}                                         // A==B
boolean isSame(pt A, pt B, float e) {return ((abs(A.x-B.x)<e)&&(abs(A.y-B.y)<e));}                   // ||A-B||<e
float d(pt P, pt Q) {return sqrt(d2(P,Q));  };                                                       // ||AB|| (Distance)
float d2(pt P, pt Q) {return sq(Q.x-P.x)+sq(Q.y-P.y); };                                             // AB*AB (Distance squared)

// A: average of points
pt A(pt A, pt B) {return P((A.x+B.x)/2.0,(A.y+B.y)/2.0); };                                          // (A+B)/2 (average)
pt A(pt A, pt B, pt C) {return P((A.x+B.x+C.x)/3.0,(A.y+B.y+C.y)/3.0); };                            // (A+B+C)/3 (average)

// S: weighted sum of points
pt W(pt A, pt B) {return new pt(A.x+B.x,A.y+B.y); };                                                 // A+B
pt W(pt A, pt B, pt C) {return W(A,W(B,C)); };                                                       // A+B+C
pt W(pt A, pt B, pt C, pt D) {return W(W(A,B),W(C,D)); };                                            // A+B+C+D
pt W(pt A, float s, pt B) {return W(A,S(s,B)); };                                                    // A+sB (used in summations)
pt W(float a, pt A, float b, pt B) {return W(S(a,A),S(b,B));}                                        // aA+bB 
pt W(float a, pt A, float b, pt B, float c, pt C) {return W(S(a,A),S(b,B),S(c,C));}                  // aA+bB+cC 
pt W(float a, pt A, float b, pt B, float c, pt C, float d, pt D){return W(W(a,A,b,B),W(c,C,d,D));}   // aA+bB+cC+dD (used in smoothing and subdivision)

// Interpolation of points
pt I(pt A, float s, pt B) {return P(A.x+s*(B.x-A.x),A.y+s*(B.y-A.y)); };                             // A+sAB (linear interpolation between points)
pt L(pt A, float s, pt B) {return P(A.x+s*(B.x-A.x),A.y+s*(B.y-A.y)); };                             // same as above (left for legacy)

// Scale, rotate, translate points
pt S(float s, pt A) {return new pt(s*A.x,s*A.y); };                                                     // sA
pt S(float s, pt A, pt B) {return new pt((s+1)*B.x-s*A.x,(s+1)*B.y-s*A.y); };                           // B+sBA=(1+s)B-sA (scaling of A by s wrt fixed point B)
pt R(pt Q, float a) {float dx=Q.x, dy=Q.y, c=cos(a), s=sin(a); return new pt(c*dx+s*dy,-s*dx+c*dy); };  // Q rotated by angle a around the origin
pt R(pt Q, float a, pt P) {float dx=Q.x-P.x, dy=Q.y-P.y, c=cos(a), s=sin(a); return P(P.x+c*dx-s*dy, P.y+s*dx+c*dy); };  // Q rotated by angle a around point P
pt T(pt P, vec V) {return P(P.x + V.x, P.y + V.y); }                                                 //  P+V (P transalted by vector V)
pt T(pt P, float s, vec V) {return T(P,S(s,V)); }                                                    //  P+sV (P transalted by sV)
pt MoveByDistanceTowards(pt P, float d, pt Q) { return T(P,d,U(V(P,Q))); };                          //  P+dU(PQ) (transalted P by *distance* s towards Q)!!!

//************************************************************************
//**** vectors
//************************************************************************
// V: create vectors
vec V(vec V) {return new vec(V.x,V.y); };                                                             // make copy of vector V
vec V(pt P) {return new vec(P.x,P.y); };                                                              // make vector from origin to P
vec V(float x, float y) {return new vec(x,y); };                                                      // make vector (x,y)
vec V(pt P, pt Q) {return new vec(Q.x-P.x,Q.y-P.y);};                                                 // PQ (make vector Q-P from P to Q
vec U(vec V) {float n = n(V); if (n==0) return new vec(0,0); else return new vec(V.x/n,V.y/n);};      // V/||V|| (Unit vector : normalized version of V)
vec U(pt P, pt Q) {return U(V(P,Q));};                                                                // PQ/||PQ| (Unit vector : from P towards Q)


// show  vectors
void show(pt P, vec V) {line(P.x,P.y,P.x+V.x,P.y+V.y); }                                              // show V as line-segment from P 
void show(pt P, float s, vec V) {show(P,S(s,V));}                                                     // show sV as line-segment from P 
void arrow(pt P, float s, vec V) {arrow(P,S(s,V));}                                                   // show sV as arrow from P 
void arrow(pt P, vec V, String S) {arrow(P,V); T(T(P,0.70,V),15,R(U(V))).showLabel(S,V(-5,4));}       // show V as arrow from P and print string S on its side
void arrow(pt P, vec V) {show(P,V);  float n=n(V); if(n<0.01) return; float s=max(min(0.2,20./n),6./n);       // show V as arrow from P 
     pt Q=T(P,V); vec U = S(-s,V); vec W = R(S(.3,U)); beginShape(); v(T(T(Q,U),W)); v(Q); v(T(T(Q,U),-1,W)); endShape(CLOSE);}; 

// input  vectors
vec MouseDrag() {return new vec(mouseX-pmouseX,mouseY-pmouseY);};                                      // vector representing recent mouse displacement

// A; Averge of vectors
vec A(vec U, vec V) {return new vec((U.x+V.x)/2.0,(U.y+V.y)/2.0); };                                  // (U+V)/2 (average)

// S: weighted sums of vectors
vec W(vec U, vec V) {return new vec(U.x+V.x,U.y+V.y);}                                                // U+V 
vec W(float a, vec U, float b, vec V) {return W(S(a,U),S(b,V));}                                      // aU+bV ( Linear combination)
vec W(vec U,float s,vec V) {return W(U,S(s,V));}                                                      // U+sV
 
// Interpolation of vectors
vec I(vec U, float s, vec V) {float a = angle(U,V); vec W = R(U,s*a);                     // interpolation (of angle and length) between U and V
    float u = n(U); float v=n(V); S((u+s*(v-u))/u,W); return W ; };
vec L(vec U,float s,vec V) {return new vec(U.x+s*(V.x-U.x),U.y+s*(V.y-U.y));};                        // (1-s)U+sV (Linear interpolation between vectors)

// measure vectors (dot product,  norm, parallel)
float dot(vec U, vec V) {return U.x*V.x+U.y*V.y; };                                                    // dot(U,V): U*V (dot product U*V)
float c(vec U, vec V) {return U.x*V.x+U.y*V.y; };                                                    // dot(U,V): U*V (dot product U*V)
float cross(vec U, vec V) {return -U.x*V.y+U.y*V.x; };                                                 // cross(U,V): || UxV || (dot product U*R(V))
float s(vec U, vec V) {return -U.x*V.y+U.y*V.x; };                                                 // cross(U,V): || UxV || (dot product U*R(V))
float n(vec V) {return sqrt(dot(V,V));};                                                               // n(V): ||V|| (norm: length of V)
float n2(vec V) {return sq(V.x)+sq(V.y);};                                                             // n2(V): V*V (norm squared)
boolean parallel (vec U, vec V) {return dot(U,R(V))==0; }; 

// rotate, scale, and normalize vectors
vec R(vec V) {return new vec(-V.y,V.x);};                                                             // V turned right 90 degrees (as seen on screen)
vec R(vec V, float a) {float c=cos(a), s=sin(a); return(new vec(V.x*c-V.y*s,V.x*s+V.y*c)); };                                     // V rotated by a radians
vec S(float s,vec V) {return new vec(s*V.x,s*V.y);};                                                  // sV
vec Reflection(vec V, vec N) { return W(V,-2.*dot(V,N),N);};
vec RR(vec V) { return V(-V.x,-V.y); }


//************************************************************************
//**** ANGLES
//************************************************************************
float angle (vec U, vec V) {return atan2(dot(R(U),V),dot(U,V)); };                                   // angle <U,V> (between -PI and PI)
float angle(vec V) {return(atan2(V.y,V.x)); };                                                       // angle between <1,0> and V (between -PI and PI)
float angle(pt A, pt B, pt C) {return  angle(V(B,A),V(B,C)); }                                       // angle <BA,BC>
float turnAngle(pt A, pt B, pt C) {return  angle(V(A,B),V(B,C)); }                                   // angle <AB,BC> (positive when right turn as seen on screen)
int toDeg(float a) {return int(a*180/PI);}                                                           // convert radians to degrees
float toRad(float a) {return(a*PI/180);}                                                             // convert degrees to radians 
float positive(float a) { if(a<0) return a+TWO_PI; else return a;}                                   // adds 2PI to make angle positive

//************************************************************************
//**** POINT CLASS
//************************************************************************
class pt { float x=0,y=0; 
  // CREATE
  pt () {}
  pt (float px, float py) {x = px; y = py;};
  pt (pt P) {x = P.x; y = P.y;};
  pt (pt P, vec V) {x = P.x+V.x; y = P.y+V.y;};
  pt (pt P, float s, vec V) {x = P.x+s*V.x; y = P.y+s*V.y;};             // P+sV
  pt (pt A, float s, pt B) {x = A.x+s*(B.x-A.x); y = A.y+s*(B.y-A.y);};  // A+sAB

  // MODIFY
  void reset() {x = 0; y = 0;}                                       // P.reset(): P=(0,0)
  pt setTo(float px, float py) {x = px; y = py; return this;};  
  pt setTo(pt P) {x = P.x; y = P.y; return this;}; 
  pt setToMouse() { x = mouseX; y = mouseY;  return this;}; 
  pt add(float u, float v) {x += u; y += v; return this;}                       // P.add(u,v): P+=<u,v>
  pt add(pt P) {x += P.x; y += P.y; return this;};        // incorrect notation, but useful for computing weighted averages
  pt add(float s, pt P)   {x += s*P.x; y += s*P.y; return this;};   
  pt add(vec V) {x += V.x; y += V.y; return this;}                              // P.add(V): P+=V
  pt add(float s, vec V) {x += s*V.x; y += s*V.y; return this;}                 // P.add(s,V): P+=sV
  pt scale(float s) {x*=s; y*=s; return this;}                                  // P.scale(s): P*=s
  pt scale(float s, pt C) {x*=C.x+s*(x-C.x); y*=C.y+s*(y-C.y); return this;}    // P.scale(s,C): P=L(C,s,P);
  pt rotate(float a) {float dx=x, dy=y, c=cos(a), s=sin(a); x=c*dx+s*dy; y=-s*dx+c*dy; return this;};     // P.rotate(a): rotate P around origin by angle a in radians
  pt rotate(float a, pt P) {float dx=x-P.x, dy=y-P.y, c=cos(a), s=sin(a); x=P.x+c*dx+s*dy; y=P.y-s*dx+c*dy; return this;};   // P.rotate(a,G): rotate P around G by angle a in radians
  pt rotateBy(float s, float t, pt P) {float dx=x-P.x, dy=y-P.y; dx-=dy*t; dy+=dx*s; dx-=dy*t; x=P.x+dx; y=P.y+dy;  return this;};   // fast rotate s=sin(a); t=tan(a/2); 
  pt moveWithMouse() { x += mouseX-pmouseX; y += mouseY-pmouseY;  return this;}; 
  pt translateTowards(float s, pt P) {x+=s*(P.x-x);  y+=s*(P.y-y);  return this;};  // transalte by ratio s towards P
  pt translateTowardsByDistance(float s, pt P) {vec V = makeVecTo(P); V.normalize(); add(s,V);  return this;};
  pt scale(float u, float v) {x*=u; y*=v; return this;};
  
  // OUTPUT POINT
  pt clone() {return new pt(x,y); };

   // OUTPUT VEC
  vec vecTo(pt P) {return(new vec(P.x-x,P.y-y)); };
  vec makeVecTo(pt P) {return(new vec(P.x-x,P.y-y)); };
  vec makeVecToCenter () {return(new vec(x-height/2.,y-height/2.)); };
  vec makeVecToAverage (pt P, pt Q) {return(new vec((P.x+Q.x)/2.0-x,(P.y+Q.y)/2.0-y)); };
  vec makeVecToAverage (pt P, pt Q, pt R) {return(new vec((P.x+Q.x+R.x)/3.0-x,(P.y+Q.y+R.x)/3.0-y)); };
  vec makeVecToMouse () {return(new vec(mouseX-x,mouseY-y)); };
  vec makeVecToBisectProjection (pt P, pt Q) {float a=this.disTo(P), b=this.disTo(Q);  return(this.makeVecTo(L(P,a/(a+b),Q))); };
  vec makeVecToNormalProjection (pt P, pt Q) {float a=dot(P.makeVecTo(this),P.makeVecTo(Q)), b=dot(P.makeVecTo(Q),P.makeVecTo(Q)); return(this.makeVecTo(L(P,a/b,Q))); };
  vec makeVecTowards(pt P, float d) {vec V = makeVecTo(P); float n = V.norm(); V.normalize(); V.scaleBy(d-n); return V; };
 
  // OUTPUT TEST OR MEASURE
  float disTo(pt P) {return(sqrt(sq(P.x-x)+sq(P.y-y))); };
  float disToMouse() {return(sqrt(sq(x-mouseX)+sq(y-mouseY))); };
  boolean isInWindow() {return(((x>0)&&(x<width)&&(y>0)&&(y<height)));};
  boolean projectsBetween(pt P, pt Q) {float a=dot(P.makeVecTo(this),P.makeVecTo(Q)), b=dot(P.makeVecTo(Q),P.makeVecTo(Q)); return((0<a)&&(a<b)); };
  float ratioOfProjectionBetween(pt P, pt Q) {float a=dot(P.makeVecTo(this),P.makeVecTo(Q)), b=dot(P.makeVecTo(Q),P.makeVecTo(Q)); return(a/b); };
  float disToLine(pt P, pt Q) { return abs(dot(V(P,this),R(U(P,Q)))); };
  boolean isLeftOf(pt P, pt Q) { return dot(V(P,this),R(V(P,Q)))>0; };
  boolean isLeftOf(pt P, pt Q, float e) { return dot(V(P,this),R(U(P,Q)))>e; };
  boolean isInCircle(pt C, float r) {return d(this,C)<r; }  // returns true if point is in circle C,r
  
  // DRAW , PRINT
  void show() {ellipse(x, y, height/200, height/200); }; // shows point as small dot
  void show(float r) {ellipse(x, y, 2*r, 2*r); }; // shows point as disk of radius r
  void showCross(float r) {line(x-r,y,x+r,y); line(x,y-r,x,y+r);}; 
  void v() {vertex(x,y);};  // used for drawing polygons between beginShape(); and endShape();
  void write() {print("("+x+","+y+")");};  // writes point coordinates in text window
  void showLabel(String s, vec D) {text(s, x+D.x-5,y+D.y+4);  };  // show string displaced by vector D from point
  void showLabel(String s) {text(s, x+5,y+4);  };
  void showLabel(int i) {text(str(i), x+5,y+4);  };  // shows integer number next to point
  void showLabel(String s, float u, float v) {text(s, x+u, y+v);  };
  void showSegmentTo (pt P) {line(x,y,P.x,P.y); }; // draws edge to another point
  void to (pt P) {line(x,y,P.x,P.y); }; // draws edge to another point

  } // end of pt class

//************************************************************************
//**** VECTORS
//************************************************************************
class vec { float x=0,y=0; 
 // CREATE
  vec () {};
  vec (vec V) {x = V.x; y = V.y;};
  vec (float s, vec V) {x = s*V.x; y = s*V.y;};
  vec (float px, float py) {x = px; y = py;};
  vec (pt P, pt Q) {x = Q.x-P.x; y = Q.y-P.y;};
 
 // MODIFY
  vec setTo(float px, float py) {x = px; y = py; return this;}; 
  vec setTo(pt P, pt Q) {x = Q.x-P.x; y = Q.y-P.y; return this;}; 
  vec setTo(vec V) {x = V.x; y = V.y; return this;}; 
  vec zero() {x=0; y=0; return this;}
  vec scaleBy(float f) {x*=f; y*=f; return this;};
  vec reverse() {x=-x; y=-y; return this;};
  vec divideBy(float f) {x/=f; y/=f; return this;};
  vec scaleBy(float u, float v) {x*=u; y*=v; return this;};
  vec normalize() {float n=sqrt(sq(x)+sq(y)); if (n>0.000001) {x/=n; y/=n;}; return this;};
  vec add(float u, float v) {x += u; y += v; return this;};
  vec add(vec V) {x += V.x; y += V.y; return this;};   
  vec add(float s, vec V) {x += s*V.x; y += s*V.y; return this;};   
  vec rotate() {float w=x; x=-y; y=w; return this;};
  vec rotateBy(float a) {float xx=x, yy=y; x=xx*cos(a)-yy*sin(a); y=xx*sin(a)+yy*cos(a); return this;};
  vec left() {return(new vec(-y,x));};
 
  // OUTPUT VEC
  vec clone() {return(new vec(x,y));}; 

  // OUTPUT TEST MEASURE
  float norm() {return(sqrt(sq(x)+sq(y)));}
  boolean isNull() {return((abs(x)+abs(y)<0.000001));}
  float angle() {return(atan2(y,x)); }

  // DRAW, PRINT
  void write() {println("("+x+","+y+")");};
  void show (pt P) {line(P.x,P.y,P.x+x,P.y+y); }; 
  void showAt (pt P) {line(P.x,P.y,P.x+x,P.y+y); }; 
  void showArrowAt (pt P) {line(P.x,P.y,P.x+x,P.y+y); 
      float n=min(this.norm()/10.,height/50.); 
      pt Q=T(P,this); 
      vec U = S(-n,U(this));
      vec W = S(.3,R(U)); 
      beginShape(); Q.add(U).add(W).v(); Q.v(); Q.add(U).add(RR(W)).v(); endShape(CLOSE); }; 
  void showLabel(String s, pt P) {P(P).add(0.5,this).add(3,R(U(this))).showLabel(s); };
  } // end vec class
 


