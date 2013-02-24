void drawCurve(pt A, pt B, pt C, pt D) {beginShape(); for(float s=0; s<=3; s+=.01) v(Curve(A,B,C,D,s)); endShape();}
pt Curve(pt A, pt B, pt C, pt D, float s) {pt Q01=I(A,s,B), Q12=I(B,s-1,C), Q23=I(C,s-2,D), Q012=I(Q01,s/2,Q12), Q123=I(Q12,(s-1)/2,Q23); return I(Q012,s/3,Q123); }

