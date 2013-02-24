# Jonathan Betancourt, Victoria Arnold, Joon Hong
# jbetancourt3@gatech.edu, varnold3@gatech.edu, xjo0nn@gatech.edu
# We worked alone on this project and only used the resources given to us by the CS 1301 Course.


# f is the number of images
# c is the number for when to stop image making
# d is wait time for the functions used for filming

#Use when you want to move Forward and film.
def ForwFilm(f,c,d):
    while f > c:
        a = takePicture()
        savePicture(a,"pic"+str(f)+".gif")
        forward(.4,d)
        f = f-1
        
# Use when you want to rotate Left and film.  
def LeftFilm(f,c,d):
    while f > c:
        a = takePicture()
        savePicture(a,"pic"+str(f)+".gif")
        turnLeft(.2,d)
        f = f-1
        
#Use when you want to move Backwards and film.
def BackFilm(f,c,d):
    while f > c:
        a = takePicture()
        savePicture(a,"pic"+str(f)+".gif")
        backward(1,d)
        f = f-1


# Use when you want to rotate Right and film.
def RightFilm(f,c,d):
    while f > c:
        a = takePicture()
        savePicture(a,"pic"+str(f)+".gif")
        turnRight(.2,d)
        f = f-1

        
#Use when you want to be Stationary and film.
def StationFilm(f,c,d):
    while f > c:
        a = takePicture()
        savePicture(a,"pic"+str(f)+".gif")
        wait(d)
        f = f-1

#Use to show pics within a range from c-f.    
def Show(f,c):
    while f > c:
        a = loadPicture("pic"+str(f)+".gif")
        show(a)
        f=f-1
        wait(.3)

