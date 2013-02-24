def retVowCons(string):
    vowList = ["a","e","i","o","u","A","E","I","O","U"]
    vow = ""
    other = ""
    for char in string:
        if char in vowList:
            vow = vow + char
        else:
            other = other + char
    return (vow,other)
            
    
