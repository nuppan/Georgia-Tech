import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AudioCheck implements ActionListener {

	private JButton playButton = new JButton("Play");
	private JButton stopButton = new JButton("Stop");
	private JButton quietButton = new JButton("Quiet");
	private JButton reverseButton = new JButton("Reverse");
	private JButton echoButton = new JButton("Echo");
	private JButton fasterButton = new JButton("Faster");
	private JButton fasterNotHigherButton = new JButton("Faster not higher");
	private JButton louder = new JButton("Louder");
	private JButton reduce = new JButton("Reduce Bits");
	private JButton echo = new JButton("Add Echo");
	private JButton mono = new JButton("Mono");
	private JButton maximize = new JButton("Maximize");
	private JButton reset = new JButton("Reset");
	private Audio song;
	private Audio songOriginal;
	private final static int DELTA = 44100 / 8;

	public AudioCheck() {
		song = new Audio("shortclip.wav");
		songOriginal = new Audio("shortclip.wav");
		JFrame frame = new JFrame("Audio Editor");
		frame.getContentPane().setLayout(new GridLayout(0,2));

		frame.getContentPane().add(playButton);
		frame.getContentPane().add(stopButton);
		frame.getContentPane().add(maximize);
		frame.getContentPane().add(echo);
		frame.getContentPane().add(mono);
		frame.getContentPane().add(reduce);
		frame.getContentPane().add(quietButton);
		frame.getContentPane().add(echoButton);
		frame.getContentPane().add(fasterButton);
		frame.getContentPane().add(fasterNotHigherButton);
		frame.getContentPane().add(louder);
		frame.getContentPane().add(reset);
		
		
		playButton.addActionListener(this);
		stopButton.addActionListener(this);
		quietButton.addActionListener(this);
		reverseButton.addActionListener(this);
		echoButton.addActionListener(this);
		fasterButton.addActionListener(this);
		fasterNotHigherButton.addActionListener(this);
		louder.addActionListener(this);
		reduce.addActionListener(this);
		echo.addActionListener(this);
		mono.addActionListener(this);
		maximize.addActionListener(this);
		reset.addActionListener(this);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	private void quiet() {
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();

		for (int i = 0; i < left.length; i++) {
			left[i] = left[i] / 2;
			right[i] = right[i] / 2;

		}
		song.setLeftChannel(left);
		song.setRightChannel(right);

	}

	private void reverse() { //
		float[] left = song.getLeftChannel();
		float[] leftRev = new float[left.length];
		float[] right = song.getRightChannel();
		float[] rightRev = new float[right.length];
		//Counter that increments up in the for loop
		int forwardCount = 0;
		//Assuming either channel can have different lengths use two for loops
		for (int i=left.length-1;i>=0;i--) {
			leftRev[forwardCount] = left[i];
			forwardCount++;
		}
		forwardCount=0;
		for (int i=right.length-1;i>=0;i--) {
			rightRev[forwardCount] = right[i];
			forwardCount++;
		}
		song.setLeftChannel(leftRev);
		song.setRightChannel(rightRev);
	}

	private void echo() { //
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();
		for (int i = DELTA; i <left.length; i++){
			left[i] = (float) (.25*left[i] + .75*left[i-DELTA]);
			right[i] = (float) (.25*right[i] + .75*right[i-DELTA]);
		}
		song.setLeftChannel(left);
		song.setRightChannel(right);
	}

	private void faster() {
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();
		float[] leftNew = new float[left.length/2];
		float[] rightNew = new float[right.length/2];
		int forwardCount = 0;
		for (int i = 0; i < left.length; i=i+2) {
			leftNew[forwardCount] = left[i];
			rightNew[forwardCount] = right[i];
			forwardCount++;
		}
		song.setLeftChannel(leftNew);
		song.setRightChannel(rightNew);
		
	}
	private void fasterNotHigher(){
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();
		float[] leftNew = new float[left.length/2];
		float[] rightNew = new float[right.length/2];
		int forwardCount = 0;
		int count = 0;
		for (int i = 0; i < left.length; i++) {
			if (count==1000){
				i+=1000;
				count=0;
			}
			leftNew[forwardCount]=left[i];
			rightNew[forwardCount]=right[i];
			forwardCount++;
			count++;
		}
		song.setLeftChannel(leftNew);
		song.setRightChannel(rightNew);
		
	}
	
	private void louder(){
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();
		for (int i = 0; i < left.length; i++) {
			if (left[i]<0){
				left[i] *=-1;
				left[i] = (float) Math.pow(left[i], 0.8);
				left[i] *=-1;
			}
			else{
				left[i] = (float) Math.pow(left[i], 0.8);
			}
			
			if (right[i]<0){
				right[i] *=-1;
				right[i] = (float) Math.pow(right[i], 0.8);
				right[i] *=-1;
			}
			else{
				right[i] = (float) Math.pow(right[i], 0.8);
			}
		}
		song.setLeftChannel(left);
		song.setRightChannel(right);
	}
	
	@SuppressWarnings("static-access")
	private void reduce(){
		JOptionPane optionPane = new JOptionPane("Reduce Bits");
		String value = null;
		value = optionPane.showInputDialog("Please enter a value for N");
		int intValue = Integer.parseInt(value);
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();

		for (int i = 0; i < left.length; i++) {
			left[i] = left[i]*(float)Math.pow(2.0, intValue-1);
			left[i] = (float)Math.round(left[i]);
			left[i] = left[i]/(float)Math.pow(2.0, intValue-1);
			right[i] = right[i]*(float)Math.pow(2.0, intValue-1);
			right[i] = (float)Math.round(right[i]);
			right[i] = right[i]/(float)Math.pow(2.0, intValue-1);
		}
		song.setLeftChannel(left);
		song.setRightChannel(right);
	}
	
	private void mono(){
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();

		for (int i = 0; i < left.length; i++) {
			left[i] = (left[i]+right[i])/2;
			right[i] = (left[i]+right[i])/2;

		}
		song.setLeftChannel(left);
		song.setRightChannel(right);
	}
	
	private void maximize(){
		float[] left = song.getLeftChannel();
		float[] right = song.getRightChannel();
		float leftMax = 0;
		float rightMax = 0;
		float totalMax = 0;
		for (int i = 0; i < left.length; i++) {
			if (Math.abs(left[i])>leftMax){
				leftMax = Math.abs(left[i]);
			}
			if (Math.abs(right[i])>rightMax){
				rightMax = Math.abs(right[i]);
			}
		}
		if (leftMax<rightMax){
			totalMax = rightMax;
		}
		else {
			totalMax = leftMax;
		}
		for (int i = 0; i < left.length; i++) {
			left[i] = left[i]/totalMax;
			right[i] = right[i]/totalMax;
		}
		song.setLeftChannel(left);
		song.setRightChannel(right);
	}
	
	private void reset(){
		song = songOriginal;
	}
		

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == playButton){
			song.play();
		}
		else if (event.getSource() == stopButton){
			song.stop();
		}
		else if (event.getSource() == quietButton){
			quiet();
		}
		else if(event.getSource() == reverseButton){
			reverse();
		}
		else if (event.getSource() == fasterButton){
			faster();
		}
		else if (event.getSource() == fasterNotHigherButton){
			fasterNotHigher();
		}
		else if (event.getSource()==louder){
			louder();
		}
		else if (event.getSource()==reduce){
			reduce();
		}
		else if (event.getSource()==echo){
			echo();
		}
		else if (event.getSource()==mono){
			mono();
		}
		else if (event.getSource()==maximize){
			maximize();
		}
		else if (event.getSource()==reset){
			reset();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		AudioCheck frame = new AudioCheck();

	}

}
