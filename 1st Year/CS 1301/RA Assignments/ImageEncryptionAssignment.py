

from myro import *

def encryptimage(pic1):
    pic = loadPicture(pic1)
    for x in range(getWidth(pic)):
        for y in range(getHeight(pic)):
            pixel = getPixel(pic,x,y)
            r = getRed(pixel)
            g = getGreen(pixel)
            b = getBlue(pixel)
            if r >= g and r >= b:
                setGreen(pixel, 255)
                setRed(pixel,0)
                setBlue(pixel,0)
            elif g >= r and g >= b:
                setBlue(pixel, 255)
                setRed(pixel,0)
                setGreen(pixel,0)
            elif b >= r and b >= g:
                setRed(pixel, 255)
                setGreen(pixel,0)
                setBlue(pixel,0)
    show(pic)
