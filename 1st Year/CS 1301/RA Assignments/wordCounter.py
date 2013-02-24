from myro import *

def wordCounter(filename):
    text = open(filename, "r")
    textlist = text.readlines()
    wordcounter = 0
    for sent in textlist:
        if sent == "\n":
            pass
        else:
            
            for x in range(0,len(sent)):
                if x == len(sent) - 1:
                    wordcounter +=1
                elif sent[x+1] == " ":
                    pass
                else:
                    if sent[x] == " ":
                        wordcounter += 1
    print textlist
    return wordcounter
              

