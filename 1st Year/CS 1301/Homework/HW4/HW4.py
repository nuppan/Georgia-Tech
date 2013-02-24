#Alex Sullivan - asull238@gatech.edu
#Joon Ki Hong - xjo0nn@gatech.edu
#We worked on this assignment alone with help on badRecord from James Jett, only using the resources provided on this year's 1301 website.




def checkHeight(x):
    if x >= 56:
        print "Have a great ride!"
    else:
        print "Sorry.  You must be at least 4 feet 8 inches to ride."
    
def largest(num1, num2, num3):
    if num1>=num2 and num1>=num3:
        return num1
    elif num2>=num1 and num2>=num3:
        return num2
    elif num3>=num1 and num3>=num2:
        return num3
    
def countDown(x):
    if x == 0:
        print "Blast off!"
    else:
        print x
        countDown(x-1)

def multiplicationTables(number, limit):
    for x in range(0,limit+1):
        operation = number * x
        print "%i*%i = %i" %(number,limit,operation)

def complimentMaker(answer1, answer2, answer3, answer4):
    if answer1 == False and answer2 == False and answer3 == False and answer4 == False:
        return "Goodbye."
    else:
        if answer1 == True:
            comp1 = " pretty"
        else:
            comp1 = ""
        if answer2 == True:
            comp2 = " smart"
        else:
            comp2 = ""
        if answer3 == True:
            comp3 = " awesome"
        else:
            comp3 = ""
        if answer4 == True:
            comp4 = " fun"
        else:
            comp4 = ""
    return "You are" + comp1 + comp2 + comp3 + comp4 + "."

def comboLock(num1,num2,num3,num4,num5):
    if num1%2!=0 and num2%2==0 and num3%2!=0 and num4%2==0 and num5%2==0 and num1<10 and num2<10 and num3<10 and num4<10 and num5<10:
        return "You opened the lock."
    else:
        return "You are locked out."

def badRecord(sentence):
    aVar = ""
    aVar2 = 0
    for x in sentence:
        if aVar2 % 2 == 0:
            aVar = aVar + x
        aVar2 = aVar2 + 1
    return aVar

def printTimestable():
    print "Times:",
    for z in range(1,10):
        print "\t", z,
    print
    for x in range(1,10):
        print x,
        for y in range(1,10):
            print "\t", x*y,
        print

def printTimes(N):
    print "Times:",
    for z in range(1,N+1):
        print "\t", z,
    print
    for x in range(1,N+1):
        print x,
        for y in range(1,N+1):
            print "\t", x*y,
        print
