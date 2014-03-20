package tetrisgame;

import javax.swing.*;

public class TetrisGame extends JFrame
{
    public TetrisGame()
    {
        super("Tetris");

        this.setBackground(java.awt.Color.white);
        this.setPreferredSize(new java.awt.Dimension(400, 400));
        this.setSize(new java.awt.Dimension(400, 400));

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
        new TetrisGame();
    }
}