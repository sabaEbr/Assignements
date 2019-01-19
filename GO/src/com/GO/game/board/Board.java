package com.GO.game.board;

import java.awt.*;

public class Board {
    //3 types of grid possible
    public static int xNine = 0;
    public static int xThirteen = 1;
    public static int xNineteen = 3;

    // Grid specs
    public static int nCells = 19;
    public static int nCross = nCells + 1;
    public static int cellWidth = 32;
    public static int cellHeight = 32;
    public static int tokenWidth = cellWidth;
    public static int tokenHeight = cellHeight;

    //Grid specs
    public static int leftGridExtremity = 1280/2-nCells* cellWidth /2;
    public static int rightGridExtremity = 1280/2+nCells* cellWidth /2;
    public static int topGridExtremity = 720/2-nCells* cellHeight /2;
    public static int bottomGridExtremity = 720/2+nCells* cellHeight /2;

    //Token specs
    public static int leftTokenExtremity = leftGridExtremity - tokenWidth/2;
    public static int rightTokenExtremity = rightGridExtremity + tokenWidth/2;
    public static int topTokenExtremity = topGridExtremity - tokenHeight/2;
    public static int bottomTokenExtremity = bottomGridExtremity + tokenHeight/2;

    public Board(int dim){
        if (dim == xNine){
            setNineGrid();
        }else if (dim == xThirteen){
            setThirteenGrid();
        }else if (dim == xNineteen){
            setNineteenGrid();
        }
    }
    private void setNineGrid() {
    }
    private void setThirteenGrid() {
    }
    private void setNineteenGrid() {
    }

    public void update(){

    }

    public void input(){

    }

    public void render(Graphics2D g){
        // Grid while playing game
        for(int x = 0; x < nCells; x++) {
            for(int y =0; y < nCells; y++) {
                g.setColor(Color.BLACK);
                g.drawRect( leftGridExtremity + x * cellWidth, topGridExtremity + y * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
