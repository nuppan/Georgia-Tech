import java.awt.Graphics;


public class BlinkingLight extends Light{
	
	public BlinkingLight() {
		super();
	}
	
	@Override
	public void draw (Graphics g) {
		if (Math.random() > .5)
			super.draw(g);
	}
}
