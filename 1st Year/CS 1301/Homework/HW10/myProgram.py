#I worked on this assignment using the resources provided to me on the 2010 Georgia Tech CS1301 website
#Joon Ki Hong - xjo0nn@gatech.edu

#This program is a GPA calculator based on the Georgia Tech GPA system. It was created with the intent
#simplifying the task of calculating ones' GPA. I got the idea after brainstorming for ideas for programs
#that I thought would be useful to anyone This program also saves the users' Class:(Credit,Grade)
#information into a .txt file located in the same directory as this python file. The text file may be
#named to whatever the user wishes. The user also may load a previously saved file to update any grades
#and recalculate the GPA after modification.

#This program was written in sections with multiple methods/functions:
#addClass(),gpaCalc(),saveClass(),loadClass(), and updateClass() are functions that perform the calculations
#and display the prompts.

#Possible inputs following each prompt are displayed. Letter cases and extra spaces are tolerable for(y/n)
#inputs. Letter cases and extra spaces are also tolerable for updating or adding grades for each
#respective class. The program is not tolerable to invalid file names.

from myro import *

print """This program calculates your Georgia Tech GPA.
Note: Please do not include any Pass/Fail classes when calculating your GPA
as these classes do not count towards your GPA."""
classDict = {}
def addClass():
    global classDict
    gradeDict = { "A" : 4, "B" : 3, "C" : 2, "D" : 1, "F" : 0}
    classn = raw_input("Add a class:")
    classn = classn.strip(" ")
    cred = raw_input("How many credit hours is this class worth?:")
    cred = cred.strip(" ")
    grade = raw_input("What is your current letter grade(excluding + and -)?:")
    grade = grade.upper()
    grade = grade.strip(" ")
    grade = gradeDict.get(grade)
    finalvals = str((int(cred),grade))
    finalvals = finalvals[:3]+finalvals[4:]
    classDict[classn] = finalvals    
    moreprmpt = raw_input("Would you like to add any more classes?(y/n):")
    moreprmpt = moreprmpt.strip(" ")
    moreprmpt = moreprmpt.lower()
    if moreprmpt == "y":
        addClass()
    else:
        return

def gpaCalc():
    global classDict
    creditlist = []
    gradelist = []
    for classes in classDict:
        vals = classDict.get(classes)
        creditlist.append(int(vals[1]))
        gradelist.append(int(vals[3]))
    totalcred = reduce(lambda x,y: x+y, creditlist)
    qualityPts = map(lambda x,y: x*y, creditlist, gradelist)
    totalQP = reduce(lambda x,y:x+y, qualityPts)
    gpa = totalQP/float(totalcred)
    print "Your current actual GPA is a",gpa
    print "Your current GT GPA (rounded down) is a", int(gpa)
    return gpa

def saveClass():

    prmpt = raw_input("Would you like to save your class and grades into a text file?(y/n)")
    prmpt = prmpt.strip(" ")
    prmpt = prmpt.lower()
    if prmpt == "y":
        name = raw_input("What would you like to name the file?(exclude the .txt extension):")
        f = open("%s.txt" %name, "w")
        for classes in classDict:
            vals = str(classDict.get(classes))
            f.write(classes)
            f.write(" ")
            f.write(vals)
            f.write("\n")
        f.close()
    elif prmpt == "f":
        return

def loadClass():
    prmpt = raw_input("Would you like to load a previously saved file?(y/n):")
    prmpt = prmpt.strip(" ")
    prmpt = prmpt.lower()
    if prmpt == "y":
        name = raw_input("What is the name of the file?(excluding the .txt extension):")
        name = name +".txt"
        f = open(name, "r")
        lines = f.readlines()
        classDict = {}
        for line in lines:
            for index in range(len(line)):
                if line[index] == "(":
                    classn = line[:index-1]
                    vals = line[index:len(line)-1]
                    classDict[classn] = vals
        return classDict
    elif prmpt == "n":
        return False
    
def updateClass(load):
    for classes in load:
        updpmt = raw_input("Would you like to update your grade for %s?(y/n):" %classes)
        updpmt = updpmt.lower()
        updpmt = updpmt.strip(" ")
        if updpmt == "y":
            
            gradeDict = { "A" : 4, "B" : 3, "C" : 2, "D" : 1, "F" : 0}
            newgrade = raw_input("Input the new letter grade (excluding the + and -):")
            newgrade = newgrade.upper()
            newgrade = newgrade.strip(" ")
            newgrade = gradeDict.get(newgrade)
            vals = load.get(classes)
            finalvals = str((int(vals[1]),newgrade))
            finalvals = finalvals[:3] +finalvals[4:]
            load[classes] = finalvals
        elif updpmt == "n":
            pass
    return load


  
load = loadClass()
if load == False:
    addClass()
    gpaCalc()
    saveClass()
else:
    classDict = updateClass(load)
    gpaCalc()
    saveClass()

