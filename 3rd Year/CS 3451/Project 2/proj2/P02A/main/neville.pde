//Uniform Neville construction through points A, B, C
pt Neville (pt A, pt B, pt C, float t) {
    pt P=P(A,t,B); pt Q=P(B,t-1,C); return P(P,t/2,Q);
    }
void drawNeville(pt A, pt B, pt C) {
    //beginShape(); for(float t=0; t<=2.01; t+=0.1) v(Neville(A,B,C,t)); endShape(); 
    //fill(green); Neville(A,B,C,f*2).show(14).label(nf(f*2,1,2)); noFill();
    }

// Neville construction through points A, B, C, D **********  Write your code here
void drawNeville(pt A, pt B, pt C, pt D) {
     beginShape(); 
     for(float t=0; t<=3.01; t+=0.1){
       pt firstN = Neville(A, B, C, t);
       pt secondN = Neville(B, C, D, t-1);
       float mult1 = (3-t)/(3);
       float mult2 = (t)/(3);
       firstN = P(firstN.x*mult1, firstN.y*mult1);
       secondN =  P(secondN.x*mult2, secondN.y*mult2);
       v(firstN.add(secondN));
     }
     endShape(); 
    //fill(green); Neville(A,B,C,f*2).show(14).label(nf(f*2,1,2)); noFill();
  //    beginShape(); for(float t=0; t<=2.01; t+=0.1){
    //   v(Neville(B,C, D, t+1));
     //}endShape(); 
   
    


  }


  
