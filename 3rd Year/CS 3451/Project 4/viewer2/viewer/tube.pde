// TUBE
void showTube(pt P0, vec T0, pt P1, vec T1, int n) {
  pt[] C = new pt[4]; makePts(C);
  stroke(cyan); noFill(); PTtoBezier(P0,T0,P1,T1,C); bezier(C); noStroke(); // shows an interpolating Bezier curve from frame 1 to frames 2 (tangents are defined by K vectors)
  for (float t=0; t<=1; t+=0.1) {pt B=bezierPoint(C,t); vec T=bezierTangent(C,t); stroke(magenta); show(B,0.1,T); noStroke(); fill(brown); show(B,1); }
  }
  
void showQuads(pt P0, vec T0, vec N0, pt P1, vec T1, vec N1, int n, int ne, float r, color col) {
      pt[] G = new pt[4]; makePts(G);
      float d=d(P0,P1)/3;  G[0].set(P(P0,d,U(T0))); G[1].set(P(P0,-d,U(T0))); G[2].set(P(P1,-d,U(T1))); G[3].set(P(P1,d,U(T1)));
      G[0].add(r,N0);  G[1].add(r,N0);     G[2].add(r,N1);        G[3].add(r,N1);
      pt[] C = new pt[n]; makePts(C);  
      for(int i=0; i<n; i++) C[i]=bezierPoint(G,float(i)/(n-1));
      vec [] L = new vec[ne];  // displacement vectors
      vec T = U(V(C[0],C[1])); 
      vec LL=N(V(0,0,1),T); if (n2(LL)<0.01) LL=N(V(1,0,0),T); if (n2(LL)<0.01) LL=N(V(0,1,0),T); L[0]=U(N(LL,T)); 
      pt [][] P = new pt [2][ne]; makePts(P[0]); makePts(P[1]); 
      int p=0; boolean dark=true;
      float [] c = new float [ne]; float [] s = new float [ne];
      for (int j=0; j<ne; j++) {c[j]=r*cos(TWO_PI*j/ne); s[j]=r*sin(TWO_PI*j/ne); }; 
      vec I0=U(L[0]); vec J0=U(N(L[0],T));
      for (int j=0; j<ne; j++) P[p][j].set(P(P(C[0],C[1]),c[j],I0,s[j],J0)); p=1-p;
      for (int i=1; i<n-1; i++) {dark=!dark; 
        vec I=U(V(C[i-1],C[i])); vec Ip=U(V(C[i],C[i+1])); vec IpmI=M(Ip,I); vec N=N(I,Ip); 
        if (n(N)<0.001) L[i]=V(L[i-1]);
        else L[i] = A( L[i-1] , m(U(N),I,L[i-1]) , N(U(N),M(Ip,I)) );
        I=U(L[i]);
        vec J=U(N(I,Ip));                 
        for (int j=0; j<ne; j++) P[p][j].set(P(P(C[i],C[i+1]),c[j],I,s[j],J)); p=1-p;
        for (int j=0; j<ne; j++) {
            if(dark) fill(200,200,200); else fill(col); dark=!dark; 
            int jp=(j+ne-1)%ne; beginShape(); v(P[p][jp]); v(P[p][j]); v(P[1-p][j]); v(P[1-p][jp]); endShape(CLOSE);};
        }        
      }

