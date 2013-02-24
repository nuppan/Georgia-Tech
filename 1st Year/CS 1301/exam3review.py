from myro import *

def showPic():
    filename = pickAFile()
    while not(".jpeg" in filename) or not(".jpg" in filename):
        filename = pickAFile()

    pic = loadPicture(filename)
    show(pic)
        
        
    
