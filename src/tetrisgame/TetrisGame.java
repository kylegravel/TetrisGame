package tetrisgame;

import javax.swing.*;

public class TetrisGame extends JFrame
{
    public static final int GAME_WIDTH  = 350,
                            GAME_HEIGHT = 400;

    public TetrisGame()
    {
        super("Tetris");

        this.setBackground(java.awt.Color.white);
        this.setPreferredSize(new java.awt.Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setSize(new java.awt.Dimension(GAME_WIDTH, GAME_HEIGHT));

        GamePanel gamePanel = new GamePanel();
        
        this.add(gamePanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new TetrisGame();
    }

}