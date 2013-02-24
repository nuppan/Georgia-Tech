/* Ellie Suh
 *  
 *  HW#10 Christmas Tree
 *  
 *  Prof Ames
 */


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TreeDrawer extends JComponent {

	public static int SIZE_OF_X = 500;
	public static int SIZE_OF_Y = 500;
	private BufferedImage tree;
	Ornament[] array = new Ornament[30];

	public TreeDrawer() {
		JFrame frame = new JFrame("Tree Picture");
		setPreferredSize(new Dimension(SIZE_OF_X, SIZE_OF_Y));

		for (int i=0;i<array.length;i++){
			if (i<=array.length/3){
				array[i] = new Light();
			}
			else if (i<=(array.length/3)*2){
				array[i] = new ImageOrnament();
			}
			else{
				array[i] = new BlinkingLight();
			}
		}
		try {
			tree = ImageIO.read(new File("tree.gif"));
		} catch (IOException e) {
			System.out.println("Image could not be read");
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(300,300));
		frame.pack();
		frame.setBounds(0,0,tree.getWidth(),tree.getHeight());
		frame.getContentPane().add(this);
		frame.setVisible(true);
	}

	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(tree,0,0,null);
		for (int i=0;i<array.length;i++){
			array[i].draw(g);
		}
	}
	
	public void run() {
		try {
			Thread.sleep(700);
			repaint();
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		TreeDrawer drawer = new TreeDrawer();
		while (true){
			drawer.run();
		}
		
	}
}
