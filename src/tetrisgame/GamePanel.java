package tetrisgame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel
{
    TetrisPiece activePiece;
    BlockGrid blockGrid;

    private int fallDelay = 1000; // milliseconds

    private final int gridX = 0, gridY = 0;
    private final int gridWidth = 20, gridHeight = 37;

    private boolean gameOver = false;

    private Timer fallTimer;

    public GamePanel()
    {
        setFocusable(true);
        activePiece = TetrisPiece.createRandomPiece(gridWidth/2);
        blockGrid = new BlockGrid(gridWidth, gridHeight, gridX, gridY);

        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (gameOver) return;

                int keyPressed = e.getKeyCode();

                if (keyPressed == KeyEvent.VK_SPACE) {
                    activePiece.rotateCounterClockwise();
                } else if (keyPressed == KeyEvent.VK_LEFT) {
                    activePiece.translate(-1, 0);
                    if (activePiece.checkSideCollision(blockGrid))
                        activePiece.translate(1, 0);
                } else if (keyPressed == KeyEvent.VK_RIGHT) {
                    activePiece.translate(1, 0);
                    if (activePiece.checkSideCollision(blockGrid))
                        activePiece.translate(-1, 0);
                } else if (keyPressed == KeyEvent.VK_DOWN) {
                    activePiece.translate(0, 1);
                }

                // for various test
                if (keyPressed == KeyEvent.VK_1)
                    blockGrid.removeRow(19);
                else if (keyPressed == KeyEvent.VK_2) {
                    activePiece.transferToGrid(blockGrid);
                }

                addPieceToGridIfAtBottom();

                repaint();
            }

            public void keyReleased(KeyEvent e)
            {
            }
        });
        
        //Setting Timer
        fallTimer = new Timer(fallDelay, new ActionListener() {
      	   public void actionPerformed(ActionEvent e) {
               activePiece.translate(0, 1);
               addPieceToGridIfAtBottom();
      		   repaint();
             }
         });

        fallTimer.start();		//start on start of application
    }
    
    
    public void addPieceToGridIfAtBottom()
    {
        if (activePiece.checkBottomCollision(blockGrid)) {	//Defined in tetrisPiece class, boolean value if
            activePiece.translate(0, -1);
            activePiece.transferToGrid(blockGrid);
            blockGrid.removeFullRows();

            activePiece = TetrisPiece.createRandomPiece(gridWidth/2);
            if (activePiece.checkBottomCollision(blockGrid)) {
                while (activePiece.checkBottomCollision(blockGrid))
                    activePiece.translate(0, -1);
                gameOver = true;
                fallTimer.stop();
            }

            // increase difficulty by decreasing falling speed of pieces
            if (fallDelay > 300) fallDelay -= 10;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D brush = (Graphics2D) g;
        blockGrid.draw(brush);
        activePiece.draw(brush);

        if (gameOver) {
            brush.setColor(Color.WHITE);
            brush.setFont(new Font("arial", Font.BOLD, 25));
            brush.drawString("Game Over", 50, 200);
        }
    }
}
