/* Ellie Suh
 * 
 * HW#12 Sudoku Part II
 * 
 * Ames
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Sudoku extends JPanel implements ActionListener,Runnable{
	private JButton checkButton = new JButton("Check");
	private JButton clearButton = new JButton("Clear");
	private JButton solveButton = new JButton("Solve");
	private JTextField[][] cells = new JTextField[9][9];
	private JTextField frame1 = new JTextField(1);
	private JMenuItem easy = new JMenuItem("Easy");
	private JMenuItem medium = new JMenuItem("Medium");
	private JMenuItem hard = new JMenuItem("Hard");
	
	public Sudoku() {
		JFrame frame = new JFrame("Sudoku");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(9,9));
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		for(int row=0; row<cells.length; row++) {
			for(int col=0; col<cells.length; col++) {
				cells[row][col] = new JTextField(2);
				panel.add(cells[row][col]);
			}
		}
		
		panel.add(checkButton);
		panel.add(clearButton);
		panel.add(solveButton);
		
		JMenu samples = new JMenu("Samples");
		samples.add(easy);
		samples.add(medium);
		samples.add(hard);
		menuBar.add(samples);
		
		checkButton.addActionListener(this);
		clearButton.addActionListener(this);
		solveButton.addActionListener(this);
		//solveButton.addActionListener(this);
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		
		setupBorders();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.getContentPane().add(frame1, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private boolean solve(){
		for(int row=0; row<cells.length; row++) {
			for(int col=0; col<cells.length; col++) {
				if (cells[row][col].getText().equals("")){
					for (int x=1;x<10;x++){
						cells[row][col].setText(Integer.toString(x));
						if (checkCell(row,col)){
							boolean recursiveCall = solve();
							if (recursiveCall==true){
								return true;
							}
						}
					}
					cells[row][col].setText("");
					return false;
				}
			}
		}
		return true;
	}
	
	private void setupBorders() {
        int wide=3, narrow=1;
        int top, bottom, left, right;
        
        for (int row=0; row<9; ++row)
            for (int col=0; col<9; col++) {
                top=bottom=left=right=narrow;
                if (col==2 || col==5) right  = wide;
                if (row==2 || row==5) bottom = wide;
                cells[row][col].setBorder(new MatteBorder(top, left, bottom, right, Color.BLACK));
            }
    }
	
    private void set(int row, int col, int val) {
        if (val==0)
            cells[row][col].setText("");
        else
            cells[row][col].setText(String.valueOf(val));
    }

    private void setRow(int row,   int v0,int v1,int v2,int v3,int v4,int v5,int v6,int v7,int v8) {
        set(row,0,v0);
        set(row,1,v1);
        set(row,2,v2);
        set(row,3,v3);
        set(row,4,v4);
        set(row,5,v5);
        set(row,6,v6);
        set(row,7,v7);
        set(row,8,v8);
    }

    private void easy() {
        setRow(0, 0,2,3,4,5,6,7,8,9);
        setRow(1, 4,5,6,7,8,9,1,2,3);
        setRow(2, 7,8,9,1,2,3,0,5,0);
        setRow(3, 2,1,4,3,6,5,8,9,7);
        setRow(4, 3,6,5,8,9,7,2,1,4);
        setRow(5, 8,9,7,2,1,4,0,6,5);
        setRow(6, 5,3,1,6,4,2,9,7,8);
        setRow(7, 6,4,2,9,7,8,5,3,1);
        setRow(8, 9,7,8,5,3,1,6,4,0);
    }

    private void medium() {
        setRow(0, 0,0,3,2,0,0,5,7,0);
        setRow(1, 0,0,7,0,0,0,0,0,0);
        setRow(2, 0,0,0,6,0,0,3,0,0);
        setRow(3, 0,2,0,0,3,9,0,1,0);
        setRow(4, 9,0,0,0,1,0,0,0,4);
        setRow(5, 0,5,0,7,2,0,0,8,0);
        setRow(6, 0,0,9,0,0,3,0,0,0);
        setRow(7, 0,0,0,0,0,0,4,0,0);
        setRow(8, 0,4,5,0,0,8,6,0,0);
    }

    private void hard() {
        setRow(0, 5,0,0,7,0,0,6,0,0);
        setRow(1, 0,0,3,8,0,0,0,0,0);
        setRow(2, 0,0,0,0,0,0,2,0,0);
        setRow(3, 6,2,0,4,0,0,0,0,0);
        setRow(4, 0,0,0,0,0,0,0,9,1);
        setRow(5, 7,0,0,0,0,0,0,0,0);
        setRow(6, 0,0,0,0,3,5,0,8,0);
        setRow(7, 4,0,0,0,0,0,1,0,0);
        setRow(8, 0,0,0,0,9,0,0,0,0);
    }
	
	private boolean checkCell(int row, int col) {
		if (cells[row][col].getText().equals(""))
			return true;
		if (checkRow(row, col) == true)
			if (checkCol(row, col) == true)
				if (checkCluster(row, col) == true)
					return true;
		
		return false;
	}
	
	private boolean checkRow(int row, int col) {
		for (int c=0; c<9; ++c) {
			if (col != c)
				if (cells[row][col].getText().equals(cells[row][c].getText()))
					return false;
		}
		return true;
	}
	
	private boolean checkCol(int row, int col) {
		for (int r=0; r<9; r++) {
			if (row != r)
				if (cells[row][col].getText().equals(cells[r][col].getText()))
					return false;
		}
		return true;
	}
	
	private boolean checkCluster(int row, int col) {
		int tLrow = row - (row%3);
		int tLcol = col - (col%3);
		
		for (int r=tLrow; r<=tLrow+2; r++) {
			for (int c=tLcol; c<=tLcol+2; c++) {
				if (row != r || col != c)
					if (cells[r][c].getText().equals(cells[row][col].getText()))
						return false;
			}
		}
		return true;
	}
	
	public boolean checkBoard() {
		for (int row=0; row<cells.length; row++) {
			for (int col=0; col<cells.length; col++) {
				if (checkCell(row, col) == false)
					return false;
			}
		}
		return true;
	}
	
	public void clear() {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells.length; col++) {
				cells[row][col].setText(" ");
			}
		}	
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == easy)
			easy();
		if (event.getSource() == medium)
			medium();
		if (event.getSource() == hard)
			hard();
		if (event.getSource()==solveButton)
			(new Thread(this)).start();
		if (event.getSource() == checkButton)
			if (checkBoard())
				frame1.setText("Board is valid.");
			else
				frame1.setText("Board is invalid. Try again.");
		if (event.getSource() == clearButton)
			clear();
	}
	
	@SuppressWarnings("unused")
	public static void main (String[] args) {
		Sudoku test = new Sudoku();
	}

	@Override
	public void run() {
		frame1.setText("Working");
		boolean solved = solve();
		if (solved==true){
			frame1.setText("Solved");
		}
		else{
			frame1.setText("Not Solvable");
		}
	}
}