package tetrisgame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel
{
    TetrisPiece activePiece;		//Piece currently in play
    BlockGrid blockGrid;		//Inactive piece array

    private int fallDelay = 500; // milliseconds

    private final int gridX = 0, gridY = 0;
    private final int gridWidth = 20, gridHeight = 37;

    private boolean gameOver = false;
    private boolean paused = false;
    
    private Timer fallTimer;	//Timer used to drop tetris piece
    private int score = 0;		//Start Score at 0
    private int rowPoints = 100;	//Points per row being cleared
   
    

    public GamePanel()
    {
        setFocusable(true);
        activePiece = TetrisPiece.createRandomPiece(gridWidth/2);
        blockGrid = new BlockGrid(gridWidth, gridHeight, gridX, gridY);
        
        this.requestFocusInWindow();
        this.validate();
        
        /*
         * Handles all movement of the Tetris pieces
         * 
         * Controls:
         * Space: rotates piece
         * P: pauses game
         * Left Arrow: moves piece left (-1, 0)
         * Right Arrow: moves piece right (1, 0)
         * Down Arrow: moves piece down (0,1) 
         */
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

                int keyPressed = e.getKeyCode();		//Get user input key 

                if (keyPressed == KeyEvent.VK_P) {
                    if (paused)			//if paused == true
                        fallTimer.restart();
                    else fallTimer.stop();

                    paused = !paused;		//set pause back to false
                }

                if (!paused) {
                    if (keyPressed == KeyEvent.VK_SPACE) {
                        activePiece.rotateCounterClockwise();
                        if (activePiece.checkAnyCollision(blockGrid))	//Check to see if active piece collides with piece on grid
                            // Return to original rotation
                            for (int i=0; i<3; i++)		//If collides with another piece, rotate back to original position
                                activePiece.rotateCounterClockwise();
                    } else if (keyPressed == KeyEvent.VK_LEFT) {
                        activePiece.translate(-1, 0);
                        if (activePiece.checkAnyCollision(blockGrid))		//Check collision
                            activePiece.translate(1, 0);
                    } else if (keyPressed == KeyEvent.VK_RIGHT) {
                        activePiece.translate(1, 0);
                        if (activePiece.checkAnyCollision(blockGrid))		//check collision
                            activePiece.translate(-1, 0);
                    } else if (keyPressed == KeyEvent.VK_DOWN)
                        activePiece.translate(0, 1);

                    addPieceToGridIfAtBottom();		//Once piece cannot move down any further
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
               addPieceToGridIfAtBottom();		//If piece hits bottom with timer, add to grid
      		   repaint();
             }
         });

        fallTimer.start();		//start on start of application
        
        
    }
    
    
    public void addPieceToGridIfAtBottom()
    {
        if (activePiece.checkBottomCollision(blockGrid)) {	//Defined in tetrisPiece class, boolean value if
            activePiece.translate(0, -1);
            activePiece.transferToGrid(blockGrid);	//Adds tetris blocks to block grid class
            //Add in update score here
            updateScore();		//method adds to score from counter
            blockGrid.removeFullRows();		//remove rows and drop piece down 1

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
    
    /*
     * updateScore
     * 
     * Method updates score depending on how many rows user fills at a time
     * 
     * Scoring:
     * 1 Row: 100 Points
     * 2 Rows: 250 Points
     * 3 Rows: 400 Points
     * 4 Rows: 550 Points
     */
    public int updateScore() {
    	
    	int counter = 0;
    	for (int row = 0; row < getHeight(); row++)		//Loop through all rows on the grid
    		
    		if (blockGrid.rowIsFull(row)) {          	
            	counter ++;		//If a row is full, increment by 1 on the counter
            	System.out.println(counter);	//For debugging purposes
    		} 
    	
    	if (counter == 1) {		//1 Row is cleared
    		score = score + rowPoints;
    		repaint();
    	} else if (counter == 2) {		//2 Rows are cleared
    		score = score + (rowPoints * 2 + 50);
    		repaint();
    	} else if (counter == 3) {		//3 Rows are cleared
    		score = score + (rowPoints * 3 + 100);
    		repaint();
    	} else if (counter >= 4) {		//4 Rows are cleared
    		score = score + (rowPoints * 4 + 150);
    		repaint();
    	}
    	
    	return score;		//return score
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D brush = (Graphics2D) g;

        brush.setColor(Color.WHITE);
        brush.fillRect(0, 0, TetrisGame.GAME_WIDTH, TetrisGame.GAME_HEIGHT);

        blockGrid.draw(brush);		//draw blockGrid
        activePiece.draw(brush);	//draw active piece
        
        brush.setColor(Color.WHITE);
        brush.setFont(new Font("arial", Font.BOLD, 25));	//Set font for Game Over and Pause
        if (gameOver)
            brush.drawString("Game Over", 35, 200);
        else if (paused)
            brush.drawString("Paused", 50, 200);
        
        //Right side of Tetris Frame
        //Holds Title and score
        brush.setColor(Color.LIGHT_GRAY);
        brush.fillRect(gridX + gridWidth * TetrisPiece.BLOCK_WIDTH, 0,
                       TetrisGame.GAME_WIDTH - gridWidth * TetrisPiece.BLOCK_WIDTH, 50);
        brush.setColor(Color.BLACK);
        brush.drawString("TETRIS", TetrisGame.GAME_WIDTH - 120, 32);	//Title
        brush.setFont(new Font("arial", Font.PLAIN, 20));
        brush.setColor(Color.RED);
        brush.drawString("Score " + updateScore(), TetrisGame.GAME_WIDTH - 120, 100);	//Score
        

    }
}
