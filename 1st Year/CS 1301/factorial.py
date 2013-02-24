def factorial():
    number = input("Enter a non negative number:")
    def factor(n):
        if n==0:
            return 1
        else:
            result = n*factor(n-1)
            return result

    answer = factor(number)
    print "your answer is:", answer
    
    def restart():
        restartinput = raw_input("Restart? 1=yes, 0=no:")
        if restartinput == "1":
            factorial()
        elif restartinput == "0":
            print "goodbye!"
        elif restartinput > "1":
            print "invalid input"
            restart()
        elif restartinput < "0":
            print "invalid input"
            restart()
        elif restartinput == float:
            print "invalid input"
            restart()
        elif restartinput == str:
            print "invalid input"
            restart()
    restart()
        
        



    

        

 

