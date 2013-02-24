# I worked on this assignment alone using only the resources provided to me on the 2010 Georgia Tech CS1301 website
#Joon Ki Hong, xjo0nn@gatech.edu


from myro import *
from random import *
from os import *


def avgLightValues(sec):
    leftLight = []
    leftcount = 0.
    centLight = []
    centcount = 0.
    rightLight = []
    rightcount = 0.

    while timeRemaining(sec):
        Light = getLight()
        leftLight.append(Light[0])
        leftcount += 1
        centLight.append(Light[1])
        centcount += 1
        rightLight.append(Light[2])
        rightcount += 1
        randval = random()
        forward(randval,1)

    avgLeft = float(reduce(lambda x,y: x+y, leftLight))/leftcount
    avgCent = float(reduce(lambda x,y: x+y, centLight))/centcount
    avgRight = float(reduce(lambda x,y: x+y, rightLight))/rightcount
    avgTuple = (avgLeft,avgCent,avgRight)
    return avgTuple


def hypCalculator(namein, nameout):

    inFile = open(namein, "r")
    outFile = open(nameout, "w")
    line = inFile.readline()
    pointslist = []
    while len(line) > 0:
        index = 0
        for char in line:
            if char != " ":
                index += 1
            else:
                templist = [int(line[:index])]
                remove = line[index+1:]
                remove = int(remove.strip("\n"))
                templist.append(remove)                
                pointslist.append(templist)
        line = inFile.readline()      
    
    hyplist = map(lambda x: (((x[0])**2)+((x[1])**2))**(.5) ,pointslist)
    for hyp in hyplist:
        outFile.write(str(hyp))
        outFile.write("\n")
    
    inFile.close()
    outFile.close()

#helper functions
def imageHelper(path):
    path = path.lower()
    if path[-4:] == ".jpg" or path[-4:] == ".png" or path[-4:] == ".gif" or path[-4:] == ".bmp":
        return True
    else:
        return False
#end helper functions

    
def findImages(path):
    files = listdir(path)
    files = filter(imageHelper,files)
    return files


def imagesWithSubdirectories(path):
    myDict = {}
    for files in walk(path):
        fullpath = files[0]
        subfold = files[1]
        filez = files[2]
        filez = filter(imageHelper,filez)
        if filez == []:
            pass
        else:
            myDict[fullpath] = subfold+filez
    return myDict
        
       
    
    
    
     
                
                
