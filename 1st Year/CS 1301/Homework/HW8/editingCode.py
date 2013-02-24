# Jonathan Betancourt, Victoria Arnold, Joon Hong
# jbetancourt3@gatech.edu, varnold3@gatech.edu, xjo0nn@gatech.edu
# We worked alone on this project and only used the resources given to us by the CS 1301 Course.

# used to fade the end scene to red
# indicating death
def redFade():
    f=1
    for image in range(72,81): #alter this range according to the images you want to apply the effect to
        a = loadPicture("pic%i.gif" %image)        
        for x in range(getWidth(a)):
            for y in range(getHeight(a)):
                
                c=getPixel(a,x,y)
    
                i = getBlue(c)
                i = i*f
                o = getGreen(c)
                o = o*f           
                
                setBlue(c, i)
                setGreen(c, o)
        savePicture(a,"pic%i.gif" %image)
        f = f - .01

# used this to indicate when being shot at
# used near the end
# Seeing red specialfx
def SeeingRed(pic1,num):
    pic = loadPicture(pic1)
    for x in range(getWidth(pic)):
        for y in range(getHeight(pic)):
            if x%2 == 0 and y%2==0:
                pixel = getPixel(pic,x,y)
                setRed(pixel,255)
    savePicture(pic,"pic%i.gif" %num)
    
# overlay specialfx    
# places a gun/hud in every image
def gunfilter():
    gun = loadPicture("white_gun.jpg")
    for x in range(20,8,-1): #Pick the image range to implant the gun 
        pic = loadPicture("pic%i.gif" %x)
        for pg in getPixels(gun):
            g = getGreen(pg)
            r = getRed(pg)
            b = getBlue(pg)
            color = getColor(pg)
            xcoord = getX(pg)
            ycoord = getY(pg)
            if g  < 255 and r < 255 and b < 255:
                setPixel(pic, xcoord, ycoord, color)
            else:
                pass
            
        savePicture(pic,"pic%i.gif" %x)

# overlay specialfx
# places a gun with a shot effect
def gunfilterFlash(): 
    gun = loadPicture("muzzle_gun.jpg")
    for x in range(29,30): #Picture range for putting the shooting gun
        pic = loadPicture("pic%i.gif" %x)
        for pg in getPixels(gun):
            g = getGreen(pg)
            r = getRed(pg)
            b = getBlue(pg)
            color = getColor(pg)
            xcoord = getX(pg)
            ycoord = getY(pg)
            if g  < 255 and r < 255 and b < 255:
                setPixel(pic, xcoord, ycoord, color)
            else:
                pass
            
        savePicture(pic,"pic%i.gif" %x)


# used this effect after the intro
# this code will crossfade two image parameters
# reason to add 100 to u is to make sure it doesn't override previous images
def CrossFade(Pic1, Pic2):
    a = loadPicture(Pic1)
    a = copyPicture(a)
    b = loadPicture(Pic2)
    b = copyPicture(b)
    f = 0
    u = 10
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
        savePicture(a,"pic"+str(u+100)+".gif")
        f = f + .10
        u = u - 1



    
