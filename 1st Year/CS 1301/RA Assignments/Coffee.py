#We worked on this assignment alone using only the resources provided on this year's CS 1301 website.
#Joon Ki Hong - xjo0nn@gatech.edu
#Alex Sullivan - asull238@gatech.edu


def coffee():
    cups = raw_input("How many cups of coffee did you consume today?:")
    fltcups = float(cups)
    hours = raw_input("How many hours of sleep get last night?")
    flthours = float(hours)
    score = 3*fltcups+flthours
    if score >=12:
        return "super hyper"
    elif score >= 6 and score < 12:
        return "mellow"
    elif score < 6:
        return "sluggish"

    
def typeOfDay():
    weather = raw_input("The weather was favorable today (enter YES for true, NO for false):")
    friends = raw_input("I had a good time with my friends today (enter YES for true, NO for false):")
    if weather == "YES" and friends == "YES":
        return "spectacular"
    elif weather == "YES" and friends == "NO":
        return "decent"
    elif weather == "NO" and friends == "YES":
        return "decent"
    elif weather == "NO" and friends == "NO":
        return "crummy"

def main():
    cofresult = coffee()
    dayresult = typeOfDay()
    print "You completed a", dayresult,"day in a", cofresult,"manner!!"
