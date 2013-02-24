from myro import *

def gray(x):
    return makeColor(x,x,x)
def main():
    MAX = 50
    W = 100
    H = 100
    myPic = makePicture(W,H)
    show(myPic, "Shades of Gray")
    for x in range(W):
        for y in range(H):
            setPixel(myPic,x,y,gray((x+y)%(MAX+1)))
    show(myPic)
