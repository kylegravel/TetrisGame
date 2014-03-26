package tetrisgame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

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
	    instructions = new JLabel ("<html>Welcome to Tetris!<br><br>Instructions<br><br>1. Use the arrow keys to move the tetris piece<br>" +
	    							"2. Use the space key to rotate the piece counter-clockwise<br>3. Move and rotate the pieces to " +
	    							"fill whole rows of tetrominos<br>4. Once a row has been fully filled, it will disappear and you " +
	    							"will recieve points<br>5. If you cannot place anymore pieces, the game is over.<br>" +
	    							"6. For maximum points, clear 4 rows at a time<br></html>");
	    instructions.setVerticalTextPosition(AbstractButton.CENTER);
	    instructions.setHorizontalTextPosition(AbstractButton.CENTER);
	    
	    start.addActionListener(new ActionListener() {
	    	
			public void actionPerformed(ActionEvent e) {   
				app.addGamePanel();
				//repaint();
				
			}
			
	    });
	    
	     
	    this.add(instructions, BorderLayout.CENTER);
	    this.add(start, BorderLayout.SOUTH);

        
        
	}
	
	
	
}
