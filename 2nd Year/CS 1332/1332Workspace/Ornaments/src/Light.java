import java.awt.Color;
import java.awt.Graphics;


public class Light extends Ornament { 

	protected Color lightColor;
	public Light () {
		if (Math.random() > .5)
			lightColor = Color.RED;
		else
			lightColor = Color.GREEN;
	}
	
	@Override
	public void draw (Graphics g) {
		g.setColor(lightColor);
		g.fillOval(x, y, 10,10); //don't have to redeclare x, y
	}
	
}
