package tetrisgame;

import java.awt.event.KeyListener;

import javax.swing.*;

public class TetrisGame extends JFrame
{
    public static final int GAME_WIDTH  = 350,
                            GAME_HEIGHT = 400;
    
    JPanel tetrisPanel = new TetrisPanel(this);
    JPanel gamePanel;
    
    public TetrisGame()
    {
        super("Tetris");

        this.setBackground(java.awt.Color.white);
        this.setPreferredSize(new java.awt.Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setSize(new java.awt.Dimension(GAME_WIDTH, GAME_HEIGHT));

       
        
        this.add(tetrisPanel);
       // tetrisPanel.requestFocusInWindow();
       // tetrisPanel.validate();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        
    }
    
    /*
     * called inside of TetrisPanel
     * 
     * Adds GamePanel to the TetrisPanel which replaces start button and 
     * instruction JLabel
     */
    public void addGamePanel(){
    	
    	this.remove(tetrisPanel);	
    	this.revalidate();	
    	//setFocusable(false);
    	this.add(gamePanel = new GamePanel());
    	gamePanel.requestFocus();		//Sets focus back to gamePanel
    	//setFocusable(true);
    	
    	this.revalidate();			//Re-validate
    	repaint();				//Repaint
    }
    
    public static void main(String[] args)
    {
        new TetrisGame();
    }

}