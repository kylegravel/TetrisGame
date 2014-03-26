package tetrisgame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class BlockGrid
{
    private int width;
    private int height;
    private int drawX;
    private int drawY;
    
    //Array of inactive tetris blocks
    private ArrayList<Block> blocks;

    public BlockGrid(int width, int height, int drawX, int drawY)
    {
        this.width  = width;
        this.height = height;
        this.drawX  = drawX;
        this.drawY  = drawY;
        blocks = new ArrayList<Block>();
    }
    
    //Return height of blockGrid
    public int getHeight()
    {
        return height;
    }
    
    //Return width of blockGrid
    public int getWidth()
    {
        return width;
    }
    
    //Get current x on blockGrid
    public int getX()
    {
        return drawX;
    }
    
    //Get current y on blockGrid
    public int getY()
    {
        return drawY;
    }
    
    //Determine is space is active
    public boolean isBlockHere(int x, int y)
    {	
    	//If block does not lie in current position
        if ((x >= width) || (y >= height))
            return false;
        
        //Loop through Block grid and determine if block lies in position
        for (Block block : blocks)
            if (block.getX() == x && block.getY() == y)
                return true;

        return false;
    }
    
    //Place inactive Tetris piece inside the blockGrid
    //Parameters: x = curr x, y = curr y, color = current tetris color
    public void placeBlock(int x, int y, Color color)
    {
        if ((x >= width) || (y >= height))
            return;
        blocks.add(new Block(x, y, color));		//Add block to array
    }
    
    //Draw blockGrid
    public void draw(Graphics2D g2)
    {
        g2.setColor(Color.BLACK);
        g2.fillRect(drawX, drawY, width*TetrisPiece.BLOCK_WIDTH, height*TetrisPiece.BLOCK_HEIGHT);

        for (Block block : blocks) {
            g2.setColor(block.getColor());
            g2.fill(new Rectangle2D.Double(drawX + block.getX()*TetrisPiece.BLOCK_WIDTH,
                                           drawY + block.getY()*TetrisPiece.BLOCK_HEIGHT,
                                           TetrisPiece.BLOCK_WIDTH,
                                           TetrisPiece.BLOCK_HEIGHT));
        }
    }
    
    //Remove a full row
    public void removeRow(int rowToRemove)
    {
        // Clear row
        Iterator<Block> it = blocks.iterator();		//Iterator pattern to remove all blocks in full row
        while (it.hasNext()) {	
            Block block = it.next();	//sets next block in array to next
            if (block.getY() == rowToRemove)
                it.remove();		//removes full Y row
        }

        // Drop rows
        for (Block block : blocks) {	//For rows not full
            if (block.getY() < rowToRemove)
                block.dropOnce();	//drop down (0,1)
        }
    }
    
    //Removes full rows
    public void removeFullRows()
    {
        for (int row = 0; row < getHeight(); row++)	//Removes multiple full rows
            if (rowIsFull(row))
                removeRow(row);
    }
    
    //Boolean determines whether row is full 
    public boolean rowIsFull(int row)
    {
        return numberOfBlocksInRow(row) == width;	//Compares number of blocks in row to the width (each block is 1 space)
    }	
    
    //Returns an integer with how many blocks are in the row
    private int numberOfBlocksInRow(int row)
    {
        int count = 0;	//sets original count to 0
        for (Block block : blocks)
            if (block.getY() == row)		//if block is in a Y
                count++;		//Add to count
        return count;
    }
    
    private class Block
    {
        private int x, y;
        private Color color;

        public Block(int x, int y, Color color)
        {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public Color getColor() { return color; }

        public void dropOnce() { this.y++; }	//Drop once by adding 1 row to the y
    }
}
