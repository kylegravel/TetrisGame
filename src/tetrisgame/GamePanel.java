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

    private int fallDelay = 500; // milliseconds

    private final int gridX = 0, gridY = 0;
    private final int gridWidth = 20, gridHeight = 37;

    private boolean gameOver = false;
    private boolean paused = false;

    private Timer fallTimer;
    private int score = 0;
    private int rowPoints = 100;
    

    public GamePanel()
    {
        setFocusable(true);
        activePiece = TetrisPiece.createRandomPiece(gridWidth/2);
        blockGrid = new BlockGrid(gridWidth, gridHeight, gridX, gridY);
        
        this.requestFocusInWindow();
        this.validate();
        
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

                if (keyPressed == KeyEvent.VK_P) {
                    if (paused)
                        fallTimer.restart();
                    else fallTimer.stop();

                    paused = !paused;
                }

                if (!paused) {
                    if (keyPressed == KeyEvent.VK_SPACE) {
                        activePiece.rotateCounterClockwise();
                        if (activePiece.checkAnyCollision(blockGrid))
                            // Return to original rotation
                            for (int i=0; i<3; i++)
                                activePiece.rotateCounterClockwise();
                    } else if (keyPressed == KeyEvent.VK_LEFT) {
                        activePiece.translate(-1, 0);
                        if (activePiece.checkAnyCollision(blockGrid))
                            activePiece.translate(1, 0);
                    } else if (keyPressed == KeyEvent.VK_RIGHT) {
                        activePiece.translate(1, 0);
                        if (activePiece.checkAnyCollision(blockGrid))
                            activePiece.translate(-1, 0);
                    } else if (keyPressed == KeyEvent.VK_DOWN)
                        activePiece.translate(0, 1);

                    addPieceToGridIfAtBottom();
                }

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
            if (fallDelay > 50) fallDelay -= 4;
        }
    }
    
    public int updateScore() {
    	
    	if (blockGrid.rowIsFull(1)) {
    		score = score + rowPoints;
    		System.out.println(score);
    		repaint();
    	} else if (blockGrid.rowIsFull(2)) {
    		score = score + (rowPoints * 2 + 50);
    		System.out.println(score);
    		repaint();
    		
    	} else if (blockGrid.rowIsFull(3)) {
    		score = score + (rowPoints * 3 + 100);
    		System.out.println(score);
    		repaint();
    		
    	} else if (blockGrid.rowIsFull(4)) {
    		score = score + (rowPoints * 4 + 200);
    		System.out.println(score);
    		repaint();
    		
    	}
    	return score;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D brush = (Graphics2D) g;

        brush.setColor(Color.WHITE);
        brush.fillRect(0, 0, TetrisGame.GAME_WIDTH, TetrisGame.GAME_HEIGHT);

        blockGrid.draw(brush);
        activePiece.draw(brush);
        
        brush.setColor(Color.WHITE);
        brush.setFont(new Font("arial", Font.BOLD, 25));
        if (gameOver)
            brush.drawString("Game Over", 35, 200);
        else if (paused)
            brush.drawString("Paused", 50, 200);

        brush.setColor(Color.LIGHT_GRAY);
        brush.fillRect(gridX + gridWidth * TetrisPiece.BLOCK_WIDTH, 0,
                       TetrisGame.GAME_WIDTH - gridWidth * TetrisPiece.BLOCK_WIDTH, 50);
        brush.setColor(Color.BLACK);
        brush.drawString("TETRIS", TetrisGame.GAME_WIDTH - 120, 32);
        brush.setFont(new Font("arial", Font.PLAIN, 20));
        brush.setColor(Color.RED);
        brush.drawString("Score " + score, TetrisGame.GAME_WIDTH - 120, 100);
        

    }
}
