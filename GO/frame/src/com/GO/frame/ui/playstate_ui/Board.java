package com.GO.frame.ui.playstate_ui;

import com.GO.frame.states.PlayState;
import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;
import com.GO.frame.util.Position;

import com.GO.core.entity.Stone;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    //3 types of grid possible
    public static int xNine = 0;
    public static int xThirteen = 1;
    public static int xNineteen = 2;

    // Slave of playState owner
    PlayStateUI playStateUI;

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

    // Hover Position
    private Position hoverPos;

    public Board(int boardType, PlayStateUI ui){
        if (boardType == xNineteen){
            initBoard(19);
        } else if (boardType == xThirteen){
            initBoard(13);
        } else if (boardType == xNine){
            initBoard(9);
        } else {
            System.out.println("Error setting board configurations - errno (10)");
            System.exit(10);
        }

        hoverPos = new Position(-1 , -1);// Initiate to -1 for invalid position

        playStateUI = ui;

    }

    // Constructor that does not need a ui (Used only for rendering purposes)
    public Board(int boardType){
        if (boardType == xNineteen){
            initBoard(19);
        } else if (boardType == xThirteen){
            initBoard(13);
        } else if (boardType == xNine){
            initBoard(9);
        } else {
            System.out.println("Error setting board configurations - errno (10)");
            System.exit(10);
        }

        hoverPos = new Position(-1 , -1);// Initiate to -1 for invalid position
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

    public int getnCells(){
        return nCells;
    }

    public void update(){

    }

    public void input(MouseHandler mouse, KeyHandler key){
        // Update hover position
        if (mouse.getX() >= leftTokenExtremity && mouse.getX() < rightTokenExtremity &&
                mouse.getY() >= topTokenExtremity && mouse.getY() < bottomTokenExtremity) {
            int tokenX = (mouse.getX() - leftTokenExtremity) / tokenWidth;
            int tokenY = (mouse.getY() - topTokenExtremity) / tokenHeight;

            if(!playStateUI.isOccupied(tokenX, tokenY) && playStateUI.getPlayValidity()){
                hoverPos.setX(tokenX);
                hoverPos.setY(tokenY);
            } else {
                hoverPos.setX(-1); // Indicates invalid
                hoverPos.setY(-1); // Indicates invalid
            }
        } else{
            hoverPos.setX(-1); // Indicates invalid
            hoverPos.setY(-1); // Indicates invalid
        }

        // If mouse is pressed in a valid position add token for players turn
        if(mouse.getButton() == 1 && playStateUI.getPlayValidity()) {
            if (mouse.getX() >= leftTokenExtremity && mouse.getX() < rightTokenExtremity &&
                    mouse.getY() >= topTokenExtremity && mouse.getY() < bottomTokenExtremity) {
                int posX = (mouse.getX() - leftTokenExtremity) / tokenWidth;
                int posY = (mouse.getY() - topTokenExtremity) / tokenWidth;
                playStateUI.signalNewMove(posX, posY);
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

        // Render Hover Position
        if(hoverPos.getX() != -1 && hoverPos.getY() != -1 && playStateUI.getPlayValidity()) {
            g.setColor(Color.CYAN);
            g.drawOval(leftTokenExtremity + hoverPos.getX() * tokenWidth,
                    topTokenExtremity +  hoverPos.getY() * tokenHeight,
                       tokenWidth, tokenHeight);
        }

        // Stone rendering for ArrayList of Stones given by PlaySate
        stones.forEach(stone -> {
            g.setColor(stone.getOwner().getColor());
            g.fillOval(leftTokenExtremity + stone.getPositionX() * tokenWidth,
                    topTokenExtremity + stone.getPositionY() * tokenHeight,
                    tokenWidth, tokenHeight);
        });

    }
}
