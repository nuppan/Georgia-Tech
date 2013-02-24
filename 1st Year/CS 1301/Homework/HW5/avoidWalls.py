#I completed this assignment using only the resources provided to use in the 2011 CS1301 website.


from myro import *
init()
def avoidWalls():
    while timeRemaining(60):
        while getObstacle('center') < 900:
            forward(.4, .5)
        rotate(1)   
    forward(.5,.5)
    backward(.5,.5)
    beep(.5 ,800)
    forward(.5,.5)
    backward(.5,.5)
    beep(.5, 600, 800)
    
    
    
