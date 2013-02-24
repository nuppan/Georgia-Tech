void drawCurve(pt A, pt B, pt C) {beginShape(); for(float s=0; s<=2; s+=.05) v(Curve(A,B,C,s)); endShape();}
pt Curve(pt A, pt B, pt C, float s) {pt P=I(A,s,B), Q=I(B,s-1,C); return I(P,s/2,Q); }

