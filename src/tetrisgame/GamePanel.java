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
    BlockGrid blockGrid;

    private final int DELAY = 2000; //piece moves down 2000 milliseconds (2 seconds)
    private final int gridX = 0, gridY = 0;
    private final int gridWidth = 20, gridHeight = 20;

    public GamePanel()
    {
        setFocusable(true);
        testPiece = TetrisPiece.createRandomPiece();
        blockGrid = new BlockGrid(20, 20, gridX, gridY);

        for (int i=0; i<19; i++)
            blockGrid.placeBlock(i, 19, Color.BLACK);
        for (int i=0; i<18; i++)
            blockGrid.placeBlock(i, 18, Color.BLUE);
        for (int i=0; i<17; i++)
            blockGrid.placeBlock(i, 17, Color.GREEN);

        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                int keyPressed = e.getKeyCode();

                if (keyPressed == KeyEvent.VK_SPACE) {
                    testPiece.rotateCounterClockwise();
                } else if (keyPressed == KeyEvent.VK_LEFT) {
                    testPiece.translate(-1, 0);
                    if (testPiece.checkSideCollision(blockGrid))
                        testPiece.translate(1, 0);
                } else if (keyPressed == KeyEvent.VK_RIGHT) {
                    testPiece.translate(1, 0);
                    if (testPiece.checkSideCollision(blockGrid))
                        testPiece.translate(-1, 0);
                } else if (keyPressed == KeyEvent.VK_DOWN) {
                    testPiece.translate(0, 1);
                }

                // for various test
                if (keyPressed == KeyEvent.VK_1)
                    blockGrid.removeRow(19);
                else if (keyPressed == KeyEvent.VK_2) {
                    testPiece.transferToGrid(blockGrid);
                }

                addPieceToGridIfAtBottom();

                repaint();
            }

            public void keyReleased(KeyEvent e)
            {
            }
        });
        
        //Setting Timer
        Timer t = new Timer(DELAY, new ActionListener() {
      	  
      	   public void actionPerformed(ActionEvent e) {
      		   testPiece.translate(0,1);
               addPieceToGridIfAtBottom();
      		   repaint();
             }
         });
         
         t.start();		//start on start of application
    }
    
    
    public void addPieceToGridIfAtBottom()
    {
        if (testPiece.checkBottomCollision(blockGrid)) {	//Defined in tetrisPiece class, boolean value if 
            testPiece.translate(0, -1);
            testPiece.transferToGrid(blockGrid);
            blockGrid.removeFullRows();
            testPiece = TetrisPiece.createRandomPiece();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D brush = (Graphics2D) g;
        testPiece.draw(brush);
        blockGrid.draw(brush);
    }
}
