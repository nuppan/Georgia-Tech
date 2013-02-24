##I worked on this assignment alone and only using the resources on the CS 1301 class website
##Joon Ki Hong, 902767001, jhong70@gatech.edu, CS1301 A03

def kilometersToMiles():  ##kilometers to miles function defined
    print "This program converts kilometers to miles" ##user friendly prompt
    kminput = raw_input("Enter the number of kilometers:")  ## prompts the user for a value for kilometers and assigns that value to kminput
    kminputflt = float(kminput) ##converts the raw input into a floating point
    kmtomilesconv = (kminputflt/1.609344) ##translates the floating point value of km into miles and assigns it to kmtomilesconv
    print kminputflt,"kilometers is equal to", kmtomilesconv,"miles" ##prints the original inputed value and the converted value of miles in a user friendly way

def sphereVolume(): ##function that calculates the volume of a sphere is defined
    from math import pi ##imports the pi value from the math module
    print "This program calculates the volume of a sphere given the radius in inches" ##user friendly prompt
    radrawinput = raw_input("Enter the value of the radius in inches:") ##prompts the user for a value for the radius in inches and assigns that value to radrawinput
    radinputflt = float(radrawinput) ##converts the input from radrawinput into a floating point value.
    volume = (4/3.0)*(pi)*(radinputflt**3) ##takes the floating point value and calculates the volume and assigns that value to volume
    print "The volume of a sphere with radius", radrawinput,"inches is", volume, "cubic inches" ##prints the radius and volume within user friendly text

def timeCleanUp(): ##function that separates inputed seconds into hours, minutes, and seconds is defined
    print "This program calculates the hours, minutes, and seconds from a given number of seconds in integer form" ##user friendly prompt
    inputseconds = raw_input("Enter an integer value for the number of seconds:") ##prompts the user for an integer value for seconds
    intseconds = int(inputseconds) ##converts the inputed user value into an integer
    hours = intseconds/3600 ##calculates hours from seconds
    secremafthr = intseconds % 3600 ##calculates the remaining number of seconds after the hours calculation
    minutes = secremafthr/60 ##calculates minutes with the remaining seconds above
    secs = secremafthr % 60 ##calculates the remaining number of seconds after minutes calculation
    print hours,"hours", minutes,"minutes","and", secs,"seconds" ##prints the hours,minutes,seconds
    
def bodyMassIndex(): ##function that calculates bmi defined
    print "This program calculates your body mass index (BMI)" ##prints a user friendly message
    rawweight = input("Enter your weight in lbs (pounds):") ##prompts the user for a value for weight in pounds
    weight = float(rawweight) ##converts the raw input from raweight into a floating point value
    rawheight = input("Enter your height in inches (in):") ##prompts the user for a value for height in inches
    height = float(rawheight) ##converts the raw input from rawheight into a floating point value
    bmi = (weight/(height**2))*703 ##calculates the bmi using the height and weight variables
    print "Your BMI is %.1f" %bmi ##prints back the bmi value up to one decimal place


