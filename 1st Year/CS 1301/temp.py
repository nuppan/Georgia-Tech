def countup(x,y):
    counter = x
    while(counter < y-1):
        print counter + 1
        counter = counter +1

    
def find(myStr,ch):
    if not(ch in myStr):
        return "none"
    index = 0
    while (ch != myStr[index]):
        index = index + 1
    return index        

def findsearch(myStr,ch,startPos):
    index = startPos
    while (index <= len(myStr)):
        if ch == myStr[index]:
            return index
        else:
            index = index +1

    return "none"

def printTimestable():
    print "Times:  1\t2\t3\t4\t5\t6\t7\t8\t9"
    i = 1
    for y in range(1,10):
        print y,
        for x in range(1,10):
            op = x*y
            print ("\t%i" %op),
            if x ==9:
                print ""
            
def printTimestabl(N):
    print "Times:"
    for u in range(1,N+1):
        print "\t",u,
    i = 1
    for y in range(1,N+1):
        print y,
        for x in range(1,N+1):
            op = x*y
            print ("\t%i" %op),
            if x ==9:
                print ""
def pongg():

    aWin = GraphWin("Pong", 500, 500)
    
    xPos = [50,75]
    yPos = [50,75]
    xDelta = [-3,2]
    yDelta = [5,7]

    balls = []
    for i in range(0,len(xPos)):
        ball = Circle(Point(xPos[i]),10)
        ball.draw(aWin)
        balls.append(ball)

    ball[0].setFill(color.rgb(255,0,0))
    ball[1].setFill(color.rgb(0,255,0))

    while timeRemaining(10):
        for i in range(0,len(xPos)):
            if(0>xPos[i]) or (500<xPos[i]):
                xDelta[i]=-xDelta[i]
            if (0>yPos[i]) or (500<yPos[i]):
                yDelta[i] = -yDelta[i]
            xPos[i] = xPos[i]+xDelta[i]
            yPos[i] = yPos[i]+yDelta[i]

        balls[i].move(xDelta[i],yDelta[i])
    wait(.01)
