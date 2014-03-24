package tetrisgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TetrisPanel extends JPanel {
	
	private JButton start;
		
	public TetrisPanel() {
		
		super(new GridLayout(2,0));
		
		//Tetris Panel
		//Adds Start, Instruction, switches to game panel upon start
		JPanel tetrisPanel = new JPanel(new BorderLayout());	    
	    
		JPanel center = new JPanel();  //GamePanel
		JPanel south = new JPanel();	//StartButton
		
		//Start Button
		start = new JButton ("Start");		
		start.setVerticalTextPosition(AbstractButton.CENTER);
	    start.setMnemonic(KeyEvent.VK_D); 
	    start.setActionCommand("disable");
	    
	    //add start button to south panel 
	    south.add(start);
		//Add gamePanel to center panel
	    center.add(new GamePanel());
	    //Add center and south panels to tetrisPanel
	    tetrisPanel.add(center, BorderLayout.CENTER);
	    tetrisPanel.add(south, BorderLayout.SOUTH);
	    //Add tetris panel to frame
        add(tetrisPanel);
        
        this.setFocusable(true);
        this.setVisible(true);
 
        
	}
	
}
