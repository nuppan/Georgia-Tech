import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageOrnament extends Ornament{
	private BufferedImage image;
	public ImageOrnament(){
		try {
			image = ImageIO.read(new File("angel.png"));
		} catch (IOException e) {
			System.out.println("Image could not be read");
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(image,x,y,Color.white,null);
	}

}
