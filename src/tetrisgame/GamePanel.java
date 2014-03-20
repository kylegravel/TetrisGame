package tetrisgame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel
{
    TetrisPiece testPiece;
    private int DELAY = 2000; //piece moves down 2000 miliseconds (2 seconds)

    public GamePanel()
    {
        setFocusable(true);
        testPiece = TetrisPiece.createRandomPiece();
        // testPiece = new TetrisPiece(10, 10, Color.BLUE);
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e)
            {
                int keyPressed = e.getKeyCode();

                if (keyPressed == KeyEvent.VK_SPACE) {
                    testPiece.rotateCounterClockwise();
                } else if (keyPressed == KeyEvent.VK_LEFT) {
                    testPiece.translate(-1, 0);
                } else if (keyPressed == KeyEvent.VK_RIGHT) {
                    testPiece.translate(1, 0);
                } else if (keyPressed == KeyEvent.VK_DOWN) {
                    testPiece.translate(0, 1);
                }

                repaint();
            }

            public void keyReleased(KeyEvent e) {}
        });
        
        
        //Setting Timer
        Timer t = new Timer(DELAY, new ActionListener() {
      	  
      	   public void actionPerformed(ActionEvent e) {
      		   testPiece.translate(0,1);
      		   repaint();
             }
         });
         
         t.start();		//start on start of application
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D brush = (Graphics2D) g;
        testPiece.draw(brush);
    }
}
