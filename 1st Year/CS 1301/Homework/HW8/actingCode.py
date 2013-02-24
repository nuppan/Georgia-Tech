# Jonathan Betancourt, Victoria Arnold, Joon Hong
# jbetancourt3@gatech.edu, varnold3@gatech.edu, xjo0nn@gatech.edu
# We worked alone on this project and only used the resources given to us by the CS 1301 Course.

#this "act" is when there is a third person perspective on the hero robot
#capturing the action that is about to take place, to make the movie a little
#more cinematic as opposed to straight "gameplay" footage.

def intro():
    forward(.4,7)


# This code advances the hero robot, coming face to face with the enemy robot.
def hero():
    forward(1,15)


    
#this function is for the enemy robot that turns from behind the couch and
#is shot by the hero robot.

def enemy():
    forward(1,2)     
    turnLeft(.3,.6)

#in this code there are also pictures being taken.
#after the turn left, the camera is manually brought up to the enemy as the
#zoom function and then is manually shaken and turned on its side to represent
#death
def turnHero():
    turnLeft(.3,5)

