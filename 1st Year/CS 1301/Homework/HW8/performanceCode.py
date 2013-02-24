# Jonathan Betancourt, Victoria Arnold, Joon Hong
# jbetancourt3@gatech.edu, varnold3@gatech.edu, xjo0nn@gatech.edu
# We worked alone on this project and only used the resources given to us by the CS 1301 Course.

# this code will play back the images taken
# we have 80 images, so this perform code plays the 80 images back back
# save as Movie.gif in the end

def perform():
    b = []
    n = 1
    while n < 81:
        a = loadPicture("pic%i.gif"%n)
        b.append(a)
        n = n+1
    savePicture(b,"Movie.gif")
        



# images for cross fade were named differently to easily implant them into a
# scene
# we called them pic6 with a letter to better fit them
def crossEffect():
    a = loadPicture("pic6a.gif")
    b = loadPicture("pic6b.gif")
    c = loadPicture("pic6c.gif")
    d = loadPicture("pic6d.gif")
    e = loadPicture("pic6e.gif")
    f = loadPicture("pic6f.gif")
    g = loadPicture("pic6g.gif")
    h = loadPicture("pic6h.gif")
    i = loadPicture("pic6i.gif")
    show(a)
    wait(.2)
    show(b)
    wait(.2)
    show(c)
    wait(.2)
    show(d)
    wait(.2)
    show(e)
    wait(.2)
    show(f)
    wait(.2)
    show(g)
    wait(.2)
    show(h)
    wait(.2)
    show(i)
    
    
    
    
    
    
    
