# Jonathan Betancourt, Victoria Arnold, Joon Hong
# jbetancourt3@gatech.edu, varnold@gatech.edu, jhong70@gatech.edu
# A03
# We worked alone on this project and only used the resources given to use by the CS 1301 Course.

from myro import *

# Don't worry about this function.
def Sifter(Pic1, Pic2):
    a = loadPicture(Pic1)
    a = copyPicture(a)
    b = loadPicture(Pic2)
    b = copyPicture(b)
    f = 11
    savePicture(a,"pic12.gif")
    while f > 0:
        for c in getPixels(a):
            d = getX(c)
            h = getY(c)
            e = d%f
            if e == 0:
                j = getPixel(b,d,h)
                g = getRed(j)
                i = getBlue(j)
                k = getGreen(j)
                setRed(c, g)
                setBlue(c, i)
                setGreen(c, k)
            else:
                pass
        savePicture(a,"pic"+str(f)+".gif")
        f = f - 1    
    a = loadPicture("pic12.gif")
    b = loadPicture("pic11.gif")
    c = loadPicture("pic10.gif")
    d = loadPicture("pic9.gif")
    e = loadPicture("pic8.gif")
    f = loadPicture("pic7.gif")
    g = loadPicture("pic6.gif")
    h = loadPicture("pic5.gif")
    i = loadPicture("pic4.gif")
    j = loadPicture("pic3.gif")
    k = loadPicture("pic2.gif")
    l = loadPicture("pic1.gif")
    savePicture([a,b,c,d,e,f,g,h,i,j,k,l],"Sifter.gif")

# this function is the create-your-own one. 50 points
def DoTrans(Pic1, Pic2):
    a = loadPicture(Pic1)
    a = copyPicture(a)
    b = loadPicture(Pic2)
    b = copyPicture(b)
    f = 19
    savePicture(a,"pic20.gif")
    while f > 0:
        for c in getPixels(a):
            d = getX(c)
            h = getY(c)
            e = d%f
            q = h%f
            if e == 0 and q == 0:
                j = getPixel(b,d,h)
                g = getRed(j)
                i = getBlue(j)
                k = getGreen(j)
                setRed(c, g)
                setBlue(c, i)
                setGreen(c, k)
            else:
                pass
        savePicture(a,"pic"+str(f)+".gif")
        f = f - 1    
    c = loadPicture("pic20.gif")
    d = loadPicture("pic19.gif")
    e = loadPicture("pic18.gif")
    f = loadPicture("pic17.gif")
    g = loadPicture("pic16.gif")
    h = loadPicture("pic15.gif")
    i = loadPicture("pic14.gif")
    j = loadPicture("pic13.gif")
    k = loadPicture("pic12.gif")
    l = loadPicture("pic11.gif")
    m = loadPicture("pic10.gif")
    n = loadPicture("pic9.gif")
    o = loadPicture("pic8.gif")
    p = loadPicture("pic7.gif")
    q = loadPicture("pic6.gif")
    r = loadPicture("pic5.gif")
    s = loadPicture("pic4.gif")
    t = loadPicture("pic3.gif")
    u = loadPicture("pic2.gif")
    v = loadPicture("pic1.gif")
    savePicture([c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v],"DoTrans.gif")


def CrossFade(Pic1, Pic2):
    a = loadPicture(Pic1)
    a = copyPicture(a)
    b = loadPicture(Pic2)
    b = copyPicture(b)
    f = 0
    u = 20
    while u >= 0:
        a = loadPicture(Pic1)
        for c in getPixels(a):
            f = 1 - f
            d = getX(c)
            h = getY(c)
            z = getRed(c)
            z = z*f
            i = getBlue(c)
            i = i*f
            o = getGreen(c)
            o = o*f
            j = getPixel(b,d,h)
            f = 1 - f
            g = getRed(j)
            g = g*f
            p = getBlue(j)
            p = p*f
            k = getGreen(j)
            k = k*f
            setRed(c, z+g)
            setBlue(c, i+p)
            setGreen(c, o+k)
        savePicture(a,"pic"+str(u)+".gif")
        f = f + .05
        u = u - 1
    c = loadPicture("pic20.gif")
    d = loadPicture("pic19.gif")
    e = loadPicture("pic18.gif")
    f = loadPicture("pic17.gif")
    g = loadPicture("pic16.gif")
    h = loadPicture("pic15.gif")
    i = loadPicture("pic14.gif")
    j = loadPicture("pic13.gif")
    k = loadPicture("pic12.gif")
    l = loadPicture("pic11.gif")
    m = loadPicture("pic10.gif")
    n = loadPicture("pic9.gif")
    o = loadPicture("pic8.gif")
    p = loadPicture("pic7.gif")
    q = loadPicture("pic6.gif")
    r = loadPicture("pic5.gif")
    s = loadPicture("pic4.gif")
    t = loadPicture("pic3.gif")
    u = loadPicture("pic2.gif")
    v = loadPicture("pic1.gif")
    x = loadPicture("pic0.gif")
    savePicture([c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,x],"CrossFade.gif")
    

def RobotZoom():
    zoomList = []
    for i in range (7):
        p = takePicture()
        savePicture(p, "zoomPic" + str(i) + ".gif")
        zoomList.append(p)
        forward(1,1)
    savePicture(zoomList, "zoom.gif")


    

def dollyShot():
    dollyList = []
    for i in range (5):
        turnRight()
        forward(1,.2)
        turnLeft()
        p = takePicture()
        savePicture(p, "dollyPic" + str(i) + ".gif")
        dollyList.append(p)
    savePicture(dollyList, "dolly.gif")
        


def threeSixty():
    threeSixtyList = []
    for i in range (8):
        rotate(-.5)
        p = takePicture()
        savePicture(p, "360pic" + str(i) + ".gif")
        threeSixtyList.append(p)
    savePicture(threeSixtyList, "360.gif")    

def SeeingRed(pic1):
    piclist = []
    picoriginal = loadPicture(pic1)
    pic = copyPicture(picoriginal)
    for x in range(getWidth(pic)):
        for y in range(getHeight(pic)):
            if x%2 == 0 and y%2==0:
                pixel = getPixel(pic,x,y)
                setRed(pixel,255)
    show(pic)
    savePicture(pic,"SeeingRedAfter.gif")

                
def Fade(Pic1):
    a = loadPicture(Pic1)
    a = copyPicture(a)
    f = 1
    u = 20
    while u >= 0:
        a = loadPicture(Pic1)
        for c in getPixels(a):
        
            z = getRed(c)
            z = z*f
            i = getBlue(c)
            i = i*f
            o = getGreen(c)
            o = o*f           
            setRed(c, z)
            setBlue(c, i)
            setGreen(c, o)
        savePicture(a,"pic"+str(u)+".gif")
        f = f - .05
        u = u - 1
    c = loadPicture("pic20.gif")
    d = loadPicture("pic19.gif")
    e = loadPicture("pic18.gif")
    f = loadPicture("pic17.gif")
    g = loadPicture("pic16.gif")
    h = loadPicture("pic15.gif")
    i = loadPicture("pic14.gif")
    j = loadPicture("pic13.gif")
    k = loadPicture("pic12.gif")
    l = loadPicture("pic11.gif")
    m = loadPicture("pic10.gif")
    n = loadPicture("pic9.gif")
    o = loadPicture("pic8.gif")
    p = loadPicture("pic7.gif")
    q = loadPicture("pic6.gif")
    r = loadPicture("pic5.gif")
    s = loadPicture("pic4.gif")
    t = loadPicture("pic3.gif")
    u = loadPicture("pic2.gif")
    v = loadPicture("pic1.gif")
    x = loadPicture("pic0.gif")
    savePicture([c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,x],"Fade.gif")    
            
        
        
