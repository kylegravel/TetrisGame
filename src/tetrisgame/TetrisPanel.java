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
	    instructions = new JLabel ("Instructions!");
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
