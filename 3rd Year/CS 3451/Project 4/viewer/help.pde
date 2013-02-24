void writeHelp () {fill(dblue);
    int i=0;
    scribe("Joon Ki Hong & Krista Patel, Project 4)",i++);
    scribe("CURVE t:show, s:move XY, a:move XZ , v:move all XY, b:move all XZ, A;archive, C.load, ;=show control points, [=rotate view",i++);
    scribe("Control Points: i: insert between control points, I: insert at end, x: delete at the end,\n release z: remove closest vertex", i++);
    scribe("Particle Generator: g: move generator, r: decrease radius, R: increase radius",  i++);
    scribe("SHOW ):silhouette, B:backfaces, |:normals, -:edges, c:curvature, g:Gouraud/flat, =:translucent",i++);
    scribe("",i++);

   }
void writeFooterHelp (float vScalar) {fill(dbrown);
    scribeFooter("Joon Ki Hong & Krista Patel Project 4.  Press ?:help.  velocityScalar="+vScalar,1);
  }
void scribeHeader(String S) {text(S,10,20);} // writes on screen at line i
void scribeHeaderRight(String S) {text(S,width-S.length()*15,20);} // writes on screen at line i
void scribeFooter(String S) {text(S,10,height-10);} // writes on screen at line i
void scribeFooter(String S, int i) {text(S,10,height-10-i*20);} // writes on screen at line i from bottom
void scribe(String S, int i) {text(S,10,i*60+20);} // writes on screen at line i
void scribeAtMouse(String S) {text(S,mouseX,mouseY);} // writes on screen near mouse
void scribeAt(String S, int x, int y) {text(S,x,y);} // writes on screen pixels at (x,y)
void scribe(String S, float x, float y) {text(S,x,y);} // writes at (x,y)
void scribe(String S, float x, float y, color c) {fill(c); text(S,x,y); noFill();}
;
