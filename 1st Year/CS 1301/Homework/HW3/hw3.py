#I worked on this assignment alone using the resources from this year's CS1301 class website
#Joon Ki Hong - xjo0nn@gatech.edu

def isPrime(num):
    for x in range (2,num-1):
        if num % x == 0:
            return False
        elif num % x > 0:
            x = x-1
    return True

def nextPrime(num):
    for x in range (1,1476): #largest prime gap to date is 1476. Range 
        checknum = num + x
        if isPrime(checknum) == True:
            return num+x
        elif isPrime(checknum) == False:
            x=x+1
        




        

    

        
    
        



