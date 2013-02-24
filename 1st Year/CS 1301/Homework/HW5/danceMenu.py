#We completed this assignment using only the resources provided to use in the 2011 CS1301 website.



from myro import *
init()
def dance():
    turnRight(1,1.25)
    beep(.5,400,1000)
    turnLeft(1,.975)
    beep(.5,400,400)
    forward(.5,1)
    turnRight(1,.625)
    beep(.5,400,1000)
    forward(1,1)
    turnLeft(1,1.25)
    beep(.5,400,1000)
    turnLeft(1,.3125)
    forward(.5,1)
    forward(.5,1)
    backward(1,1)
    forward(.5,1)
    turnRight(1,.5)
    turnLeft(1,1)
    turnRight(1,.5)
    turnRight(1,1.25)
    beep(.5,400,1000)
    turnLeft(1,.975)
    beep(.5,400,400)
    forward(.5,1)
    turnRight(1,.625)
    beep(.5,400,1000)
    forward(1,1)
    turnLeft(1,1.25)
    beep(.5,400,1000)
    turnLeft(1,.3125)
    forward(.5,1)
    beep(.5,400,1000)
    turnLeft(1,.3125)
    forward(.5,1)
          
def menu():
    dance = raw_input("""1. The Charleston
2. The Tango
3. The Foxtrot
0. Exit
Which dance step would you like?""")
    if dance == "1":
        rotate(1)
        rotate(-1)
        beep(4,400,800)
        forward(1,1)
        backward(1,1)
        rotate(1)
        rotate(-1)
        beep(2,400,800)
        forward(1,2)
        rotate(1)
        rotate(-1)
        stop()
        menu()
    elif dance == "2":
        beep(5,400,800)
        forward(1,1.2)
        backward(1,1.2)
        rotate(-1)
        rotate(1)
        forward(1,3)
        backward(1,1)
        rotate(1)
        rotate(-1)
        beep(1,400,800)
        backward(1,1)
        stop()
        menu()
    elif dance == "3":
        forward(1,1)
        backward(1,1)
        rotate(1)
        rotate(-1)
        rotate(.35)
        beep(3,400,800)
        forward(.4,.3)
        backward(.4,.3)
        rotate(1)
        rotate(-1)
        stop()
        menu()
    elif dance == "0":
        print "Have a good day!"
    elif dance > "3" or dance < "1":
        print "I'm sorry, I don't know that one."
        menu()
    
        
    
        
        
