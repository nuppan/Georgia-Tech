# I worked on this homework assignment using only the resources provided to me in this years CS1301 Georgia Tech website.
#Joon Ki Hong - xjo0nn@gatech.edu

from myro import *
init()

averagelist = []
for x in range(9):
    p = takePicture()
    l = getLight()
    left = float(l[0])
    center = float(l[1])
    right = float(l[2])
    savePicture(p, "pic%i.gif" %x)
    rotate(1)
    stop()
    average =((left+center+right)/3.0)
    averagelist.append(average)

average0 = averagelist[0]
average1 = averagelist[1]
average2 = averagelist[2]
average3 = averagelist[3]
average4 = averagelist[4]
average5 = averagelist[5]
average6 = averagelist[6]
average7 = averagelist[7]
average8 = averagelist[8]
name = getName()
    
f = open("myPage.html", "w")

htcode = """
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head> <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Joon's HW6 Web Assignment</title>
</head>
<body>
    <h1>Welcome to My Picture Page!</h1>
    <p>Made by Joon Ki Hong</p>
    <table>
        <tr>
            <td><p><img src="pic0.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
            <td><p><img src="pic3.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
            <td><p><img src="pic6.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
        </tr>
        <tr>
            <td><p><img src="pic1.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
            <td><p><img src="pic4.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
            <td><p><img src="pic7.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
        </tr>
        <tr>
            <td><p><img src="pic2.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
            <td><p><img src="pic5.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
            <td><p><img src="pic8.gif" height="192" width="256" alt="robotscreen" /></p>%f</td>
        </tr>
    </table>
    <p>Picture taken by %s</p>

    		
    
</body>

</html>
        
"""

f.write(htcode %(average0,average3,average6,average1,average4,average7,average2,average5,average8,name))
    
f.close()
