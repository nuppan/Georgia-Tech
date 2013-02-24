class Solid{
  Curve profile;
  Curve scaledProfile;
  int solidID; 
  int k; //the number of sample profiles
  pt position; //position from the top vertex
  Mesh mesh;
  vec direction;
  
  Solid(int id, pt position){
    direction = V(0,100,0);
    k = 10;
    this.position = position;
    mesh = new Mesh();
    mesh.declareVectors();
    mesh.showEdges = false;
    solidID = id;
    profile = new Curve(); //initial the profile to some default curve
    profile.append(modelOrigin);
    profile.append(P(modelOrigin, windowWidth/6, X, windowHeight/5, Y, 0, Z));
    profile.append(P(modelOrigin, windowWidth/5, X, windowHeight/2, Y, 0, Z));
    profile.append(P(modelOrigin, 0, X, windowHeight, Y, 0, Z));
    calcScaledProfile();
    createMesh();
  }
  
  void calcScaledProfile(){
    scaledProfile = profile.getScaledCurve(position,direction);
  }
  
  void dragProfilePoint(){ //drag profile model points
    profile.pick(P(mouseX,mouseY,0));
    profile.dragPoint( V((mouseX-pmouseX),X,(mouseY-pmouseY),Y) );
    createMesh();
  }
  
  void dragDx(){
    direction.x+=1;
  }
  
  void dragDy(){
    direction.y+=1; 
  }
  
  void drawSolidSweep(){ //draw solid using a sweep, not mesh
    Curve tempProfile;
    float angle=0;
    for(int i=0;i<k;i++){
      scaledProfile.getRotatedCurve(angle).drawEdges();
      angle = angle + ((2*PI)/k);
    }
  }
  
  void createMesh(){ //create the mesh based on the sweeped scaled profile
     mesh.empty();
     Curve profiles[] = new Curve[k];
     calcScaledProfile();
     float angle=0;
     for(int i=0;i<k;i++){
       profiles[i] = scaledProfile.getRotatedCurve(angle);
       angle = angle + ((2*PI)/k);
     }
     
     int firstVID[] = new int[scaledProfile.n-1];
     int prevVID[] = new int[scaledProfile.n-1];
     int currVID[] = new int[scaledProfile.n-1];
     int topVID = 0;
     int botVID = 1;
     //initialize first profile corners/vertices
     mesh.addVertex(profiles[0].P[0]);
     mesh.addVertex(profiles[0].P[scaledProfile.n-1]);
     for(int k=1;k<scaledProfile.n-1;k++){
        prevVID[k] = mesh.addVertex(profiles[0].P[k]);
        firstVID[k] = prevVID[k];
     }
     
     for(int i=1;i<=k;i++){ //loop through profiles
       Curve currProfile;
       if(i==k){currProfile = profiles[0];currVID = firstVID;}else{currProfile = profiles[i];}
       
       for(int j=1;j<scaledProfile.n;j++){ //loop through points on each profile   
         if(j==1){ //apex garaunteed a triangle
           if(i!=k){currVID[j] = mesh.addVertex(currProfile.P[j]);}
           mesh.addTriangle(topVID,prevVID[j],currVID[j]);
         }else if(j==scaledProfile.n-1){
           mesh.addTriangle(currVID[j-1],prevVID[j-1],botVID);
         }else{
           if(i!=k){currVID[j] = mesh.addVertex(currProfile.P[j]);}
           mesh.addTriangle(currVID[j],currVID[j-1],prevVID[j]);
           mesh.addTriangle(currVID[j-1],prevVID[j-1],prevVID[j]);
         }
       }
       for(int l=1;l<scaledProfile.n-1;l++){
         prevVID[l] = currVID[l];
       }
     }
     mesh.resetMarkers().computeBox().updateON();
  }
  
  void drawMesh(int mode){
    if(mode==0){
      mesh.flatShading = true;
      mesh.showFront();mesh.showBackTriangles();
    }else if(mode==1){
      mesh.flatShading = true;
      mesh.showFront();mesh.showBackTriangles();
      strokeWeight(5);stroke(red);
      mesh.showTriNormals(); 
      strokeWeight(1);stroke(black);
    }else if(mode==2){
      mesh.flatShading = false;
      mesh.showFront();mesh.showBackTriangles();
      strokeWeight(5);stroke(red);
      mesh.showVertexNormals(); 
      strokeWeight(1);stroke(black);
    }
  } 
  
  void drawProfile(){ //draw unscaled profile used for editing
    profile.drawEdges();
    profile.showSamples(5);
  }
  
  void insertProfilePoint(){
    profile.pick(P(mouseX,mouseY,0));
    profile.insertOnCurve();
    createMesh();
  }
  
  void increaseK(){
    k++;
    createMesh();
  }
  
  void decreaseK(){
    if(k>3){
      k--; 
      createMesh();
    }
  }
  
  void makeConvex(){
    if(profile.n>3){
      for (int i = 0; i < profile.n - 2; i++){
        vec currVecA = V(profile.Pof(i), profile.Pof(i+1));
        vec currVecB = V(profile.Pof(i+1), profile.Pof(i+2));
        if (det(currVecA, currVecB) > 0){
           profile.deletePt(i+1);   
        }   
      }
      
      for (int i = 0; i < profile.n - 2; i++){
        vec currVecA = V(profile.Pof(i), profile.Pof(i+1));
        vec currVecB = V(profile.Pof(i+1), profile.Pof(i+2));
        if (det(currVecA, currVecB) > 0){
           profile.deletePt(i+1);   
        }   
      }
    } 
    createMesh();   
  }  
  
  void deleteProfilePt(int mX, int mY){
    profile.deleteMousePt(mX, mY);  
    createMesh();
  }
  
  void empty(){
    profile.empty(); 
  }
  
}
