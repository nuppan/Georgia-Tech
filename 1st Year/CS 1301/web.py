# Jay Summet
# Released to the public domain, Sep 29th 2008
#
# A Program that creates a simple webpage that includes
# an animated picture from the robot's camera  and light 
# sensor readings.

from myro import *
init()


#Open the webpage file for writing.
myFile = open("demo.html","w")


#Write the top of the webpage.
pageTop = """<html>
 <body>
 <h1> CS 1301 Demo!</h1>

 <img src="class.gif">
 Here is some text
 """
myFile.write(pageTop)

picList = []

for i in range(10):
    #Turn a bit, and sample the light sensor.
    turnRight(0.33,0.33)
    lightValue = getLight("center")
    lvStr = "<br>Light Sensor value is: %i" % lightValue
    myFile.write(lvStr)
	
    #Take a picture and append it to the picList
    wait(0.1)
    pic = takePicture()
    picList.append(pic)

#Save an animated GIF.
savePicture(picList,"class.gif")

#Finish up the webpage...
pageBottom = """</body>
</html>"""

myFile.write(pageBottom)
myFile.close()

print "All Done!"

