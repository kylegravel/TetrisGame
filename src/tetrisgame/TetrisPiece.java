package tetrisgame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class TetrisPiece
{

    public static final int NUMBER_OF_BLOCKS_IN_PIECE = 4;
    public static final int BLOCK_WIDTH = 10, BLOCK_HEIGHT = 10;

    private int centerX = 0;
    private int centerY = 0;

    private int shapeX[] = new int[NUMBER_OF_BLOCKS_IN_PIECE];
    private int shapeY[] = new int[NUMBER_OF_BLOCKS_IN_PIECE];

    private int xPos, yPos;

    private Color color;

    private static Random randomNum = new Random();

    private static final Color[] pieceColors = {
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.CYAN,
            Color.ORANGE
    };

    private static final int PIECE_I = 0, PIECE_O = 1, PIECE_S = 2, PIECE_Z = 3, PIECE_L = 4;

    /*
     * createRandomPiece
     * 
     * Method creates 1 of 7 tetris pieces
     * Uses a random number generator for ints 0-7
     */
    public static TetrisPiece createRandomPiece(int startingLocation)
    {
        TetrisPiece randomPiece = new TetrisPiece(0, 0, pieceColors[randomNum.nextInt(5)]);

        int pieceNumber = randomNum.nextInt(7);        //Random number generator to select pieces 1-7

        switch (pieceNumber) {
            case 0:
                randomPiece.centerX = 0;
                randomPiece.centerY = 1;
                randomPiece.shapeX[0] = 0;
                randomPiece.shapeY[0] = 3;
                randomPiece.shapeX[1] = 0;
                randomPiece.shapeY[1] = 2;
                randomPiece.shapeX[2] = 0;
                randomPiece.shapeY[2] = 1;
                randomPiece.shapeX[3] = 0;
                randomPiece.shapeY[3] = 0;
                break;
            case 1:
                randomPiece.centerX = 0;
                randomPiece.centerY = 0;
                randomPiece.shapeX[0] = 0;
                randomPiece.shapeY[0] = 2;
                randomPiece.shapeX[1] = 0;
                randomPiece.shapeY[1] = 1;
                randomPiece.shapeX[2] = 0;
                randomPiece.shapeY[2] = 0;
                randomPiece.shapeX[3] = 1;
                randomPiece.shapeY[3] = 0;
                break;
            case 2:
                randomPiece.centerX = 1;
                randomPiece.centerY = 0;
                randomPiece.shapeX[0] = 1;
                randomPiece.shapeY[0] = 2;
                randomPiece.shapeX[1] = 1;
                randomPiece.shapeY[1] = 1;
                randomPiece.shapeX[2] = 1;
                randomPiece.shapeY[2] = 0;
                randomPiece.shapeX[3] = 0;
                randomPiece.shapeY[3] = 0;
                break;
            case 3:
                randomPiece.centerX = 0;
                randomPiece.centerY = 0;
                randomPiece.shapeX[0] = 0;
                randomPiece.shapeY[0] = 0;
                randomPiece.shapeX[1] = 0;
                randomPiece.shapeY[1] = 1;
                randomPiece.shapeX[2] = 1;
                randomPiece.shapeY[2] = 0;
                randomPiece.shapeX[3] = 1;
                randomPiece.shapeY[3] = 1;
                break;
            case 4:
                randomPiece.centerX = 1;
                randomPiece.centerY = 0;
                randomPiece.shapeX[0] = 1;
                randomPiece.shapeY[0] = 0;
                randomPiece.shapeX[1] = 2;
                randomPiece.shapeY[1] = 0;
                randomPiece.shapeX[2] = 0;
                randomPiece.shapeY[2] = 1;
                randomPiece.shapeX[3] = 1;
                randomPiece.shapeY[3] = 1;
                break;
            case 5:
                randomPiece.centerX = 1;
                randomPiece.centerY = 1;
                randomPiece.shapeX[0] = 1;
                randomPiece.shapeY[0] = 0;
                randomPiece.shapeX[1] = 0;
                randomPiece.shapeY[1] = 1;
                randomPiece.shapeX[2] = 1;
                randomPiece.shapeY[2] = 1;
                randomPiece.shapeX[3] = 2;
                randomPiece.shapeY[3] = 1;
            case 6:
                randomPiece.centerX = 1;
                randomPiece.centerY = 0;
                randomPiece.shapeX[0] = 1;
                randomPiece.shapeY[0] = 0;
                randomPiece.shapeX[1] = 0;
                randomPiece.shapeY[1] = 0;
                randomPiece.shapeX[2] = 2;
                randomPiece.shapeY[2] = 1;
                randomPiece.shapeX[3] = 1;
                randomPiece.shapeY[3] = 1;
                break;
            default:
                break;
        }

        randomPiece.xPos = startingLocation;

        return randomPiece;
    }

    //TetrisPiece constructor
    public TetrisPiece(int xPos, int yPos, Color color)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
    }

    //Helper method used to return the private variable x
    public int getX()
    {
        return xPos;
    }

    //Helper method used to return the private variable y
    public int getY()
    {
        return yPos;
    }

    public void translate(int x, int y)
    {
        xPos += x;
        yPos += y;
    }

    public void rotateCounterClockwise()
    {
        for (int i = 0; i < NUMBER_OF_BLOCKS_IN_PIECE; i++) {
            int newPositionX = centerX - centerY + shapeY[i];
            int newPositionY = centerX + centerY - shapeX[i];
            shapeX[i] = newPositionX;
            shapeY[i] = newPositionY;
        }
    }

    /*
     * transferToGrid
     * 
     * Adds all inactive pieces to the blockGrid used to manage blocks
     * that have fallen to the bottom of the tetris playing field
     */
    public void transferToGrid(BlockGrid blockGrid)
    {
        for (int i = 0; i < NUMBER_OF_BLOCKS_IN_PIECE; i++) {
            blockGrid.placeBlock(xPos + shapeX[i], yPos + shapeY[i], color);
        }
    }

    /*
     * checkBottomCollision
     * 
     * Boolean value assigned if Tetris block collides with another
     * block in the block grid
     * or
     * if block hits the bottom of the screen stop piece
     */
    public boolean checkBottomCollision(BlockGrid blockGrid)
    {
        for (int i = 0; i < NUMBER_OF_BLOCKS_IN_PIECE; i++)
            if (blockGrid.isBlockHere(xPos + shapeX[i], yPos + shapeY[i])
                || (yPos + shapeY[i] >= blockGrid.getHeight()))
                return true;
        return false;
    }

    /*
     * checkSideCollision
     * 
     * Boolean value assigned if Tetris piece tries to leave the horizontal
     * bounds of the screen
     */
    public boolean checkSideCollision(BlockGrid blockGrid)
    {
        for (int i = 0; i < NUMBER_OF_BLOCKS_IN_PIECE; i++)
            if ((xPos + shapeX[i] >= blockGrid.getWidth())
                || ((xPos + shapeX[i] < blockGrid.getX())))
                return true;
        return false;
    }

    public boolean checkAnyCollision(BlockGrid blockGrid)
    {
        return checkSideCollision(blockGrid) || checkBottomCollision(blockGrid);
    }

    public void draw(Graphics2D g2)
    {
        for (int i = 0; i < NUMBER_OF_BLOCKS_IN_PIECE; i++) {
            g2.setColor(color);
            g2.fill(new Rectangle2D.Double((xPos) * BLOCK_WIDTH + shapeX[i] * BLOCK_WIDTH,
                                           (yPos) * BLOCK_WIDTH + shapeY[i] * BLOCK_HEIGHT,
                                           BLOCK_WIDTH, BLOCK_HEIGHT));

        }
    }
}
