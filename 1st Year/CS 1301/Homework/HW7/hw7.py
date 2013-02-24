
from myro import *

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
    

        
    

    
            
        
        

            
          
   
            
            
    
