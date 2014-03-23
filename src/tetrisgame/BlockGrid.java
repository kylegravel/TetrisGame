package tetrisgame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BlockGrid
{
    private int width;
    private int height;
    private int drawX;
    private int drawY;

    private ArrayList<Block> blocks;

    public BlockGrid(int width, int height, int drawX, int drawY)
    {
        this.width  = width;
        this.height = height;
        this.drawX  = drawX;
        this.drawY  = drawY;
        blocks = new ArrayList<Block>();
    }

    public boolean isBlockHere(int x, int y)
    {
        if ((x >= width) || (y >= height))
            return false;

        for (Block block : blocks)
            if (block.getX() == x && block.getY() == y)
                return true;

        return false;
    }

    public void draw(Graphics2D g2)
    {
        for (Block block : blocks) {
            g2.setColor(block.getColor());
            g2.fill(new Rectangle2D.Double(drawX + block.getX()*TetrisPiece.BLOCK_WIDTH,
                                           drawY + block.getY()*TetrisPiece.BLOCK_HEIGHT,
                                           TetrisPiece.BLOCK_WIDTH,
                                           TetrisPiece.BLOCK_HEIGHT));
        }
    }

    private void removeRow(int rowToRemove)
    {
        /*for (int row = rowToRemove; row>0; row--)
            for (int block=0; block<width; block++)
                grid[row][block] = grid[row-1][block];*/

        // Clear row
        for (int block = 0; block < width; block++)
            if (blocks.get(block).getY() == rowToRemove)
                blocks.remove(block);

        // Drop rows
        for (int row = rowToRemove - 1; row > 0; row--) {
            for (int block = 0; block < width; block++) {
                if (blocks.get(block).getY() == row)
                    blocks.get(block).dropOnce();
            }
        }

    }

    public void placeBlock(int x, int y, Color color)
    {
        if ((x >= width) || (y >= height))
            return;
        blocks.add(new Block(x, y, color));
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

        public void dropOnce() { this.y++; }
    }
}
