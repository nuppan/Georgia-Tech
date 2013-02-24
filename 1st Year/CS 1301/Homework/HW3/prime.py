#I worked on this assignment alone using the resources from this year's CS1301 class website
#Joon Ki Hong - xjo0nn@gatech.edu

def isPrime(num):
    for x in range (2,num):
        if num % x == 0:
            return False
        elif num % x > 0:
            pass
    return True

def nextPrime(num):
    for x in range (1,1476): #largest prime gap to date is 1476. The prime before this gap will cause an overflow error anyways (1425172824437699411)
        checknum = num + x
        if isPrime(checknum) == True:
            return num+x
        elif isPrime(checknum) == False:
            pass
        




        

    

        
    
        



