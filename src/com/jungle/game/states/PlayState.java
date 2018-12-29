package com.jungle.game.states;

import com.jungle.game.entity.Player1;
import com.jungle.game.entity.Player2;
import com.jungle.game.graphics.Font;
import com.jungle.game.util.KeyHandler;
import com.jungle.game.util.MouseHandler;

import java.awt.*;

public class PlayState extends GameState {

    private Font font;
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

    private Player1 player1;
    private Player2 player2;

    public static int turn = 1;

    public PlayState(GameStateManager gameStateManager){

        super(gameStateManager);
        font = new Font("font/font_magic.jpg", 64, 64);

        player1 = new Player1();
        player2 = new Player2();

    }

    public void update(){
        for(int x = 0; x < nCross; x++){
            for(int y = 0; y < nCross; y++){
                if (x == 0 && y == 0){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x+1, y) == 1 && player2.getTokenValue(x, y+1) == 1 ) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x+1, y) == 1 && player1.getTokenValue(x, y+1) == 1 ){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else if (x == nCross - 1 && y == 0){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x-1, y) == 1 && player2.getTokenValue(x, y+1) == 1 ) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x-1, y) == 1 && player1.getTokenValue(x, y+1) == 1 ){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else if (x == 0 && y == nCross - 1){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x+1, y) == 1 && player2.getTokenValue(x, y-1) == 1 ) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x+1, y) == 1 && player1.getTokenValue(x, y-1) == 1 ){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else if (x == nCross - 1 && y == nCross - 1){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x-1, y) == 1 && player2.getTokenValue(x, y-1) == 1 ) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x-1, y) == 1 && player1.getTokenValue(x, y-1) == 1 ){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else if (x == 0){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x+1, y) == 1 && player2.getTokenValue(x, y+1) == 1  && player2.getTokenValue(x, y-1) == 1) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x+1, y) == 1 && player1.getTokenValue(x, y+1) == 1 && player1.getTokenValue(x, y-1) == 1){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else if (x == nCross - 1){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x-1, y) == 1 && player2.getTokenValue(x, y+1) == 1  && player2.getTokenValue(x, y-1) == 1) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x-1, y) == 1 && player1.getTokenValue(x, y+1) == 1 && player1.getTokenValue(x, y-1) == 1){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else if (y == 0){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x-1, y) == 1 && player2.getTokenValue(x+1, y) == 1  && player2.getTokenValue(x, y+1) == 1) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x-1, y) == 1 && player1.getTokenValue(x+1, y) == 1 && player1.getTokenValue(x, y+1) == 1){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else if (y == nCross - 1){
                    if (player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x-1, y) == 1 && player2.getTokenValue(x+1, y) == 1  && player2.getTokenValue(x, y-1) == 1) {
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x-1, y) == 1 && player1.getTokenValue(x+1, y) == 1 && player1.getTokenValue(x, y-1) == 1){
                            player2.resetTokenPos(x, y);
                        }
                    }
                } else {
                    if(player1.getTokenValue(x, y) == 1){
                        if (player2.getTokenValue(x-1, y) == 1 && player2.getTokenValue(x+1, y) == 1 && player2.getTokenValue(x, y-1) == 1 &&  player2.getTokenValue(x, y+1) == 1){
                            player1.resetTokenPos(x, y);
                        }
                    } else if(player2.getTokenValue(x, y) == 1){
                        if (player1.getTokenValue(x-1, y) == 1 && player1.getTokenValue(x+1, y) == 1 && player1.getTokenValue(x, y-1) == 1 &&  player1.getTokenValue(x, y+1) == 1){
                            player2.resetTokenPos(x, y);
                        }
                    }
                }
            }
        }
        player1.update();
        player2.update();
    }

    public void input(MouseHandler mouse, KeyHandler key){
        if ( key.up.down){
            System.out.println("'W' is being pressed");
        }
        if(mouse.getButton() == 1) {
            if (mouse.getX() >= PlayState.leftTokenExtremity && mouse.getX() <= PlayState.rightTokenExtremity && mouse.getY() >= PlayState.topTokenExtremity && mouse.getY() <= PlayState.bottomTokenExtremity){
                //tokenPos[(mouse.getX() - PlayState.leftTokenExtremity) / PlayState.tokenWidth][(mouse.getY() - PlayState.topTokenExtremity) / PlayState.tokenHeight] = 1;
                int tokenX = (mouse.getX() - PlayState.leftTokenExtremity) / PlayState.tokenWidth;
                int tokenY = (mouse.getY() - PlayState.topTokenExtremity) / PlayState.tokenHeight;
                if (turn == 1){
                    if(player2.getTokenValue(tokenX, tokenY) == 0 && player1.getTokenValue(tokenX, tokenY) == 0) {
                        player1.setTokenPos(tokenX, tokenY);
                        turn = 2;
                    }
                } else if(turn == 2) {
                    if (player1.getTokenValue(tokenX, tokenY) == 0 && player2.getTokenValue(tokenX, tokenY) == 0){
                        player2.setTokenPos(tokenX, tokenY);
                        turn = 1;
                    }
                }

            }
        }
        player1.input(mouse, key);
        player2.input(mouse, key);

    }

    public void render(Graphics2D g){
        // Grid while playing game
        for(int x = 0; x < nCells; x++) {
            for(int y =0; y < nCells; y++) {
                g.setColor(Color.BLACK);
                g.drawRect( leftGridExtremity + x * cellWidth, topGridExtremity + y * cellHeight, cellWidth, cellHeight);
            }
        }
        // Render player Tokens
        player1.render(g);
        player2.render(g);

    }
}
