#We worked on this assignment only with eachother and only using the resources provided on the CS1301 class website
#Joon Ki Hong - xjo0nn@gatech.edu / Alex Sullivan - asull238@gatech.edu
#CS1301 - A03

def mass(x):
    kilo = x
    stones = kilo * 0.157
    return stones

def volume(x):
    liters = x
    pints = liters * 2.11
    print "There are %.2f" %pints, "US pints in ", x, "liters."
    
def monkeys(x):
    monkeys = x
    hockeypucks = monkeys * 37.62533333333
    print "There are %.4f" %hockeypucks, "hockey pucks in", x, "average spider monkeys."

def tipCalculator():
    from math import *
    billx = raw_input("How much is the bill before tax and tip?")
    bill = float(billx)
    percenttipx = raw_input("How much percent tip do you want to leave?")
    percenttip = float(percenttipx)
    tax = bill * .078
    tip = bill * (percenttip / 100)
    totaltip = ceil(tip)
    tipfloat = float(totaltip)
    total = bill + tax + tipfloat
    print "Tax is $%.2f." %tax
    print "Tip is $%.2f." %tipfloat
    print "Total is $%.2f." %total
    
                           
