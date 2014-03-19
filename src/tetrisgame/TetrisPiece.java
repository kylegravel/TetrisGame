package tetrisgame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class TetrisPiece {

    public final int NUMBER_OF_BLOCKS_IN_PIECE = 4;
    public final int BLOCK_WIDTH = 10, BLOCK_HEIGHT = 10;

    private int centerX = 0;
    private int centerY = 0;

    private int shapeX[] = new int[4];
    private int shapeY[] = new int[4];

    private int xPos, yPos;

    private Color color;

    private static Random randomNum = new Random();

    public static TetrisPiece createRandomPiece()
    {
        TetrisPiece randomPiece = new TetrisPiece(0, 0, new Color(randomNum.nextInt(256),
                                                                  randomNum.nextInt(256),
                                                                  randomNum.nextInt(256)));
        int pieceNumber = 2;//randomNum.nextInt(3);

        if (pieceNumber == 0) {
            randomPiece.centerX = 0;
            randomPiece.centerY = 1;
            randomPiece.shapeX[0] = 0;
            randomPiece.shapeY[0] = 2;
            randomPiece.shapeX[1] = 0;
            randomPiece.shapeY[1] = 1;
            randomPiece.shapeX[2] = 0;
            randomPiece.shapeY[2] = 0;
            randomPiece.shapeX[3] = 0;
            randomPiece.shapeY[3] = -1;
        } else if (pieceNumber == 1) {
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
        } else {
            randomPiece.centerX = 0;
            randomPiece.centerY = 0;
            randomPiece.shapeX[0] = 1;
            randomPiece.shapeY[0] = 2;
            randomPiece.shapeX[1] = 1;
            randomPiece.shapeY[1] = 1;
            randomPiece.shapeX[2] = 1;
            randomPiece.shapeY[2] = 0;
            randomPiece.shapeX[3] = 0;
            randomPiece.shapeY[3] = 0;
        }

        return randomPiece;
    }

	public TetrisPiece(int xPos, int yPos, Color color)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
	}

    public int getX()
    {
        return xPos;
    }

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
        for (int i=0; i<NUMBER_OF_BLOCKS_IN_PIECE; i++)
        {
            int newPositionX = centerX - centerY + shapeY[i];
            int newPositionY = centerX + centerY - shapeX[i];
            shapeX[i] = newPositionX;
            shapeY[i] = newPositionY;
        }
    }

    public void draw(Graphics2D g2)
    {
        for (int i=0; i<NUMBER_OF_BLOCKS_IN_PIECE; i++)
        {
            g2.setColor(color);
            g2.fill(new Rectangle2D.Double((xPos+centerX)*BLOCK_WIDTH + shapeX[i]*BLOCK_WIDTH,
                                           (yPos+centerY)*BLOCK_WIDTH + shapeY[i]*BLOCK_HEIGHT,
                                           BLOCK_WIDTH, BLOCK_HEIGHT));

        }
    }
}
