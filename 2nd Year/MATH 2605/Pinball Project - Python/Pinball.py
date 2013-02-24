from __future__ import division
from visual import *
from visual.graph import *
from random import *
from math import *

#Joon Ki Hong Calculus III for CS
#GTID: jhong70 902767001
#Pinball Project
#This project is written in Python to demonstrate a particle that is shot from
#the origin vector(0,0,0) in random or systematic angles and to see how many
#consecutive reflections this particle hits (3 spheres) using the concepts
#learned in Calculus III.

#Constants
s = 6
r = 2

#Particle
Particle = sphere(pos=vector(0,0,0),radius=0.1,color=color.yellow)

#Sparse Array & Lists
hitList = []
sequenceList = []
angleList = []

for i in range(0,1000):
    hitList.insert(i,0)
for i in range(0,50):
    angleList.insert(i,"")
    sequenceList.insert(i,"")
    

#Circles
Circle1 = sphere(pos=vector((s/2),((-s*pow(3,1/2))/6),0), radius=r, color=color.cyan)
Circle2 = sphere(pos=vector((-s/2),((-s*pow(3,(1/2)))/6),0), radius=r, color=color.cyan)
Circle3 = sphere(pos=vector(0,(s*pow(3,(1/2)))/3,0), radius=r, color=color.cyan)


#methods

def dotProduct(v,x):
    xcomp = v.x*x.x
    ycomp = v.y*x.y
    zcomp = v.z*x.z
    return (xcomp+ycomp+zcomp)


def intersect(c,r,x,v):
    tpart1 = dotProduct(v,(c-x))
    D = pow(tpart1,2)-dotProduct((c-x),(c-x))+pow(r,2)
    if (tpart1 <= 0):
        return -1
    if (D <= 0):
        return -1
    elif (D > 0):
        t = tpart1 - pow(D,(1/2))
        intersection = Particle.pos + (t*v)
        curvy.append(intersection)
        circleIntersection = sphere(pos=intersection,radius=0.1,color=color.white)
        x = intersection
        return t
    return

def reflect(c,x,v):
    wpart1 = v
    wpart2 = ((dotProduct(2*v,(c-x)))/(dotProduct((c-x),(c-x))))*(c-x)
    w = wpart1 - wpart2
    return (w)


def systematicVelocity(theta):
    v = vector(cos(theta),sin(theta),0)
    return v
#End methods




#main execution

#outside variables
count = 0
velocityCounts = 0

#Systematic velocity variable (theta)
theta = 0


while (velocityCounts<1000):
    theta = uniform(0,2*pi) #Random Angle generator
    v = vector(cos(theta),sin(theta),0) #Random velocity
    
    #v = systematicVelocity(theta+0.00628) #Systematic velocity
    #theta = theta+0.00628 #Systematic Angle (theta incrementer assuming trial=1000)

    velocityCounts=velocityCounts+1
    Particle.pos = vector(0,0,0)
    if (count>0):
        curvy.visible = False
    count = 0
    curvy = curve(pos=[vector(0,0,0)])
    hits = 0
    halt = False
    tempSequence = ""
    initTheta = str(theta/(2*pi)) + "pi"
    lastCircle = "Empty"
    
    c1Intersection = intersect(Circle1.pos,r,Particle.pos,v)
    c2Intersection = intersect(Circle2.pos,r,Particle.pos,v)
    c3Intersection = intersect(Circle3.pos,r,Particle.pos,v)

    while (halt==False):
        rate(100) #increase rate to speed up animation
        if (count>0):
                if (lastCircle=="c1"):
                    c2Intersection = intersect(Circle2.pos,r,Particle.pos,v)
                    c3Intersection = intersect(Circle3.pos,r,Particle.pos,v)
                elif (lastCircle=="c2"):
                    c1Intersection = intersect(Circle1.pos,r,Particle.pos,v)
                    c3Intersection = intersect(Circle3.pos,r,Particle.pos,v)
                elif (lastCircle=="c3"):
                    c1Intersection = intersect(Circle1.pos,r,Particle.pos,v)
                    c2Intersection = intersect(Circle2.pos,r,Particle.pos,v)
        if (lastCircle=="c1"):
            if (0<c3Intersection and 0<c2Intersection):
                if (c3Intersection>c2Intersection):
                    w = reflect(Circle2.pos,Particle.pos,v)
                    Particle.pos = Particle.pos + (c2Intersection*v)
                    v=w
                    count+=1
                    hits+=1
                    lastCircle="c2"
                    tempSequence = tempSequence+"c2"
                else:
                    w = reflect(Circle3.pos,Particle.pos,v)
                    Particle.pos = Particle.pos + (c3Intersection*v)
                    v=w
                    count+=1
                    hits+=1
                    lastCircle="c3"
                    tempSequence = tempSequence+"c3"
            elif(0<c2Intersection):
                w = reflect(Circle2.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c2Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c2"
                tempSequence = tempSequence+"c2"
            elif (0<c3Intersection):
                w = reflect(Circle3.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c3Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c3"
                tempSequence = tempSequence+"c2"
            else:
                halt=True
        elif (lastCircle=="c2"):
            if (0<c3Intersection and 0<c1Intersection):
                if (c3Intersection>c1Intersection):
                    w = reflect(Circle1.pos,Particle.pos,v)
                    Particle.pos = Particle.pos + (c1Intersection*v)
                    v=w
                    count+=1
                    hits+=1
                    lastCircle="c1"
                    tempSequence = tempSequence+"c1"
                else:
                    w = reflect(Circle3.pos,Particle.pos,v)
                    Particle.pos = Particle.pos + (c3Intersection*v)
                    v=w
                    count+=1
                    hits+=1
                    lastCircle="c3"
                    tempSequence = tempSequence+"c3"
            elif (0<c1Intersection):
                w = reflect(Circle1.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c1Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c1"
                tempSequence = tempSequence+"c1"
            elif (0<c3Intersection):
                w = reflect(Circle3.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c3Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c3"
                tempSequence = tempSequence+"c3"
            else:
                halt=True
        elif (lastCircle=="c3"):
            if (0<c2Intersection and 0<c1Intersection):
                if (c2Intersection>c1Intersection):
                    w = reflect(Circle1.pos,Particle.pos,v)
                    Particle.pos = Particle.pos + (c1Intersection*v)
                    v=w
                    count+=1
                    hits+=1
                    lastCircle="c1"
                    tempSequence = tempSequence+"c1"
                else:
                    w = reflect(Circle2.pos,Particle.pos,v)
                    Particle.pos = Particle.pos + (c2Intersection*v)
                    v=w
                    count+=1
                    hits+=1
                    lastCircle="c2"
                    tempSequence = tempSequence+"c2"
            elif (0<c1Intersection):
                w = reflect(Circle1.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c1Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c1"
                tempSequence = tempSequence+"c1"
            elif (0<c2Intersection):
                w = reflect(Circle2.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c2Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c2"
                tempSequence = tempSequence+"c2"
            else:
                halt=True
        elif (lastCircle=="Empty"):
            if (0<c1Intersection):
                w = reflect(Circle1.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c1Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c1"
                tempSequence = tempSequence+"c1"
            elif (0<c2Intersection):
                w = reflect(Circle2.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c2Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c2"
                tempSequence = tempSequence+"c2"
            elif (0<c3Intersection):
                w = reflect(Circle3.pos,Particle.pos,v)
                Particle.pos = Particle.pos + (c3Intersection*v)
                v=w
                count+=1
                hits+=1
                lastCircle="c3"
                tempSequence = tempSequence+"c3"
            else:
                halt=True
    
    hitList[count] +=1
    
    sequenceList[count] = tempSequence
    angleList[count] = initTheta

print("Frequencies")
print(hitList)
print
print "Sequences"
print sequenceList
print
print "Angles"
print angleList
