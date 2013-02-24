#
# This program loads the mugshot photos, and shows one
# randomly.
# When a photo is shown, it is removed from the lineup, so it
# can not be selected again.
# The user should be able to tell the program to show another
# picture.
#
#
#

from os import getcwd
from os import listdir
from myro import *

def loadPhotos():
    #Find a list of all pictures
    fileList = listdir( getcwd() )

    listofjpegs = []

    for myFile in fileList:
        if (".jpg" == myFile[-4:]):
            listofjpegs.append(myFile)
            print myFile

    return(listofjpegs)
        

def showPicture( listOfJpegs):
    #Pick one at random.
    randChoice = pickOne( len(listOfJpegs) )

    picName = listOfJpegs[ randChoice]

    #Delete the picture from the list. (so it won't be choosen again)
    del listOfJpegs[randChoice]

    #Load a picture from a file.
    #Display a picture
    pic = loadPicture(picName)
    show(pic)


#main program starts here:
myList = loadPhotos()

result = "Yes"

while(result == 'Yes' and (len(myList) > 0)  ):
    showPicture(myList)
    result = askQuestion("Continue?", ["Yes", "No"])
    
        

    
