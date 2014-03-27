package tetrisgame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class TetrisPanel extends JPanel {
	
	private JButton start;
	private JLabel instructions;
		
	public TetrisPanel(final TetrisGame  app) {
		
		super(new BorderLayout());	
	
		
		//Start Button
		start = new JButton ("Start");		
		start.setVerticalTextPosition(AbstractButton.CENTER);
	    start.setMnemonic(KeyEvent.VK_D); 
	    start.setActionCommand("disable");
	    
	    //Instruction JLabel
	    instructions = new JLabel ("<html><center>Welcome to Tetris!</center><br><br><center>Instructions</center><br><br>"
	    							+ "1. Use the arrow keys to move the tetris piece<br>"
	    							+ "2. Use the space key to rotate the piece<br>"
	    							+ "3. Move and rotate the pieces to fill rows<br>"
	    							+ "4. Once filled, rows will clear and points get added<br>"
	    							+ "5. Game over happens when all rows fill.<br>" 
	    							+ "6. For maximum points, clear 4 rows at a time<br></html>");
	    
	    instructions.setVerticalTextPosition(AbstractButton.CENTER);
	    instructions.setHorizontalTextPosition(AbstractButton.CENTER);
	    
	    //Action Listener calls for a switch from the TetrisPanel to the GamePanel
	    //This starts the timer for the GamePanel, and begins game play
	    start.addActionListener(new ActionListener() {
	    	
			public void actionPerformed(ActionEvent e) {   
				app.addGamePanel();		//Method defined inside of TetrisGame
				
			}
			
	    });
	    
	    //Add instruction and start to Panel	     
	    this.add(instructions, BorderLayout.NORTH);
	    this.add(start, BorderLayout.SOUTH);     
        
	}
	
	
	
}
