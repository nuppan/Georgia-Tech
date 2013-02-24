// ********************** display utilities ****************************
void showModel() {  // sets the matrix and displays the second model (here the axes as blocks)
  pushMatrix();
  applyMatrix( mI[m].x,mJ[m].x,mK[m].x,mQ[m].x,
               mI[m].y,mJ[m].y,mK[m].y,mQ[m].y,
               mI[m].z,mJ[m].z,mK[m].z,mQ[m].z,
               0.0,    0.0,    0.0,    1.0      );
  showAxes(30); // replace this (showing the axes) with code for showing your second model
  popMatrix();
  }
  
void showAxes(float s) { // shows three orthogonal axes as red, green, blue blocks aligned with the local frame coordinates
  noStroke();
  pushMatrix(); 
  pushMatrix(); fill(red);  scale(s,1,1); box(2); popMatrix();
  pushMatrix(); fill(green);  scale(1,s,1); box(2); popMatrix();
  pushMatrix(); fill(blue);  scale(1,1,s); box(2); popMatrix();  
  popMatrix();  
  }

