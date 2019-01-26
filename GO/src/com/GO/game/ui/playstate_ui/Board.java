package com.GO.game.ui.playstate_ui;

import com.GO.game.entity.Stone;
import com.GO.game.states.PlayState;
import com.GO.game.util.KeyHandler;
import com.GO.game.util.MouseHandler;
import com.GO.game.util.Position;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    //3 types of grid possible
    public static int xNine = 0;
    public static int xThirteen = 1;
    public static int xNineteen = 2;

    // Slave of playState owner
    PlayState playStateOwner;

    // Grid specs
    public int nCells = 19;
    public int nCross = nCells + 1;
    public int cellWidth = 32;
    public int cellHeight = 32;
    public int tokenWidth = cellWidth;
    public int tokenHeight = cellHeight;

    //Grid specs
    public int leftGridExtremity = 1280/2-nCells* cellWidth /2;
    public int rightGridExtremity = 1280/2+nCells* cellWidth /2;
    public int topGridExtremity = 720/2-nCells* cellHeight /2;
    public int bottomGridExtremity = 720/2+nCells* cellHeight /2;

    //Token specs
    public int leftTokenExtremity = leftGridExtremity - tokenWidth/2;
    public int rightTokenExtremity = rightGridExtremity + tokenWidth/2;
    public int topTokenExtremity = topGridExtremity - tokenHeight/2;
    public int bottomTokenExtremity = bottomGridExtremity + tokenHeight/2;

    public Board(int boardType, PlayState game){
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

        playStateOwner = game;

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

    public void input(MouseHandler mouse, KeyHandler key){
        // If mouse is pressed in a valid position add token for players turn
        if(mouse.getButton() == 1) {
            if (mouse.getX() >= leftTokenExtremity && mouse.getX() < rightTokenExtremity &&
                    mouse.getY() >= topTokenExtremity && mouse.getY() < bottomTokenExtremity) {
                int posX = (mouse.getX() - leftTokenExtremity) / tokenWidth;
                int posY = (mouse.getY() - topTokenExtremity) / tokenWidth;
                playStateOwner.newMove(new Position(posX, posY));
            }
        }
    }

    public void render(Graphics2D g){
        for(int x = 0; x < nCells; x++) {
            for(int y =0; y < nCells; y++) {
                g.setColor(Color.BLACK);
                g.drawRect( leftGridExtremity + x * cellWidth, topGridExtremity + y * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public void render(Graphics2D g, ArrayList<Stone> stones){
        // Grid while playing game
        render(g);

        // Stone rendering for ArrayList of Stones given by PlaySate
        for(Stone stone : stones){
            g.setColor(stone.getOwner().getColor());
            g.fillOval(leftTokenExtremity + stone.getPositionX() * tokenWidth,
                    topTokenExtremity + stone.getPositionY() * tokenHeight,
                    tokenWidth, tokenHeight);
        }

    }
}
