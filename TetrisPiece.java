package tetrisgame;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TetrisPiece {

    public final int NUMBER_OF_BLOCKS_IN_PIECE = 4;
    public final int BLOCK_WIDTH = 10, BLOCK_HEIGHT = 10;

    private int centerX = 0;
    private int centerY = 0;

    private int shapeX[] = new int[4];
    private int shapeY[] = new int[4];

    private int xPos, yPos;

    private Color color;



	public TetrisPiece(int xPos, int yPos, Color color)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;

		shapeX[0] = 0;
		shapeY[0] = 2;
		shapeX[1] = 0;
		shapeY[1] = 1;
		shapeX[2] = 0;
		shapeY[2] = 0;
		shapeX[3] = 1;
		shapeY[3] = 0;
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
