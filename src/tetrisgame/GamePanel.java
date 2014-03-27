package tetrisgame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel
{
    TetrisPiece activePiece; // Piece currently in play
    BlockGrid blockGrid; // Inactive block array

    private int fallDelay = 500; // milliseconds

    private final int gridX = 0, gridY = 0;
    private final int gridWidth = 20, gridHeight = 37;

    private boolean gameOver = false;
    private boolean paused = false;

    private Timer fallTimer;  // Timer used to drop tetris piece
    private int score = 0;
    private int rowPoints = 100;  // Points per row being cleared


    public GamePanel()
    {
        setFocusable(true);
        activePiece = TetrisPiece.createRandomPiece(gridWidth / 2);
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
                        // Check to see if active piece collides with piece on grid
                        if (activePiece.checkAnyCollision(blockGrid))
                            // Return to original rotation
                            for (int i = 0; i < 3; i++)
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

                    addPieceToGridIfAtBottom(); // Once piece cannot move down any further
                }

                repaint();
            }

            public void keyReleased(KeyEvent e)
            {
            }
        });

        //Setting Timer
        fallTimer = new Timer(fallDelay, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                activePiece.translate(0, 1);
                addPieceToGridIfAtBottom();
                repaint();
            }
        });

        fallTimer.start();


    }


    public void addPieceToGridIfAtBottom()
    {
        if (activePiece.checkBottomCollision(blockGrid)) {
            activePiece.translate(0, -1);
            activePiece.transferToGrid(blockGrid);
            updateScore(); // method adds to score from counter
            blockGrid.removeFullRows();

            activePiece = TetrisPiece.createRandomPiece(gridWidth / 2);
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
    public int updateScore()
    {

        int rowsCleared = 0;
        for (int row = 0; row < getHeight(); row++)

            if (blockGrid.rowIsFull(row)) {
                rowsCleared++;
                System.out.println(rowsCleared); // For debugging purposes
            }

        if (rowsCleared == 1) {
            score = score + rowPoints;
            repaint();
        } else if (rowsCleared == 2) {
            score = score + (rowPoints * 2 + 50);
            repaint();
        } else if (rowsCleared == 3) {
            score = score + (rowPoints * 3 + 100);
            repaint();
        } else if (rowsCleared >= 4) {
            score = score + (rowPoints * 4 + 150);
            repaint();
        }

        return score;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D brush = (Graphics2D) g;

        brush.setColor(Color.WHITE);
        brush.fillRect(0, 0, TetrisGame.GAME_WIDTH, TetrisGame.GAME_HEIGHT);

        blockGrid.draw(brush);
        activePiece.draw(brush);

        brush.setColor(Color.WHITE);
        brush.setFont(new Font("arial", Font.BOLD, 25)); // Font for Game Over and Pause
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
        brush.drawString("TETRIS", TetrisGame.GAME_WIDTH - 120, 32);    //Title
        brush.setFont(new Font("arial", Font.PLAIN, 20));
        brush.setColor(Color.RED);
        brush.drawString("Score " + updateScore(), TetrisGame.GAME_WIDTH - 120, 100);    //Score


    }
}
