from myro import *

def countSecretPattern1(name):
    pic = loadPicture(name)
    counter = 0
    for y in range(getHeight(pic)):
        for x in range(getWidth(pic)):
            pixel = getPixel(pic, x, y)
            red = getRed(pixel)
            green = getGreen(pixel)
            blue = getBlue(pixel)
            if red == 255 and green == 0 and blue == 0:
                try:
                    pixelright = getPixel(pic, x+1, y)
                    redright = getRed(pixelright)
                    greenright = getGreen(pixelright)
                    blueright = getBlue(pixelright)
                    if redright == 255 and greenright == 255 and blueright == 255:
                        counter+=1
                except:
                    pass

    return counter
                
                
                

    
            
