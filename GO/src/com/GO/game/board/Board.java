package com.GO.game.board;

import com.GO.game.entity.Stone;

import java.awt.*;
import java.util.ArrayList;

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

    public Board(int boardType){
        if (boardType == xNineteen){
            initBoard(19);
        } else if (boardType == xThirteen){
            initBoard(13);
        } else if (boardType == xNine){
            initBoard(9);
        } else {
            System.out.println("Error loading board configurations - errno (10)");
            System.exit(10);
        }

    }

    private void initBoard(int boardSize) {
        nCells = boardSize;
        nCross = nCells + 1;

        leftGridExtremity = 1280/2-nCells* cellWidth /2;
        rightGridExtremity = 1280/2+nCells* cellWidth /2;
        topGridExtremity = 720/2-nCells* cellHeight /2;
        bottomGridExtremity = 720/2+nCells* cellHeight /2;

        leftTokenExtremity = leftGridExtremity - tokenWidth/2;
        rightTokenExtremity = rightGridExtremity + tokenWidth/2;
        topTokenExtremity = topGridExtremity - tokenHeight/2;
        bottomTokenExtremity = bottomGridExtremity + tokenHeight/2;
    }

    public void update(){

    }

    public void input(){

    }

    public void render(Graphics2D g, ArrayList<Stone> stones){
        // Grid while playing game
        for(int x = 0; x < nCells; x++) {
            for(int y =0; y < nCells; y++) {
                g.setColor(Color.BLACK);
                g.drawRect( leftGridExtremity + x * cellWidth, topGridExtremity + y * cellHeight, cellWidth, cellHeight);
            }
        }

        // Stone
        for(Stone stone : stones){
            g.setColor(stone.getOwner().getColor());
            g.fillOval(leftTokenExtremity + stone.getPositionX() * tokenWidth,
                    topTokenExtremity + stone.getPositionY() * tokenHeight,
                    tokenWidth, tokenHeight);
        }

    }
}
