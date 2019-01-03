package com.GO.game.states;

import com.GO.game.entity.CPU;
import com.GO.game.entity.Player1;
import com.GO.game.entity.Player2;
import com.GO.game.graphics.Font;
import com.GO.game.util.KeyHandler;
import com.GO.game.util.MouseHandler;
import com.GO.game.util.Time;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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

    // Skip button specs
    public static int skipButtonWidth = 4 * cellWidth;
    public static int skipButtonHeight = 3/2 * cellHeight;
    public static int leftSkipExtremity = 1280/2 - skipButtonWidth / 2;
    public static int rightSkipExtremity = 1280/2 + skipButtonWidth / 2;
    public static int topSkipExtremity = bottomTokenExtremity + cellHeight;
    public static int bottomSkipExtremity = topSkipExtremity + skipButtonHeight;

    public static Player1 player1;
    public static Player2 player2;
    public static CPU cpu;

    private Map<String, Integer> hoverPos = new HashMap<String, Integer>();

    // Turn management variables
    public static int turn = 1;
    public static boolean turnSkip = false;
    public static boolean previousTurnSkip = false;
    private static double turnSkipTimer = System.currentTimeMillis();
    private static double timeBeforeNextTurnSkip = 200.0;

    // Game time variable
    private static long startTime;
    private static long currentTime;

    public PlayState(GameStateManager gameStateManager){

        super(gameStateManager);
        //font = new Font("font/font_magic.jpg", 64, 64);

        player1 = new Player1();
        player2 = new Player2();
        cpu = new CPU();

        hoverPos.put("x", -1); // Initiate to -1 for invalid position
        hoverPos.put("y", -1); // Initiate to -1 for invalid position

        startTime = System.currentTimeMillis();
    }

    public static int getWinner(){
        return player1.getTotalScore() > player2.getTotalScore() ? 1 : 2;
    }

    public void update(){

        // Kill token Rules; all situations covered
        // todo: Optimisation required
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

        // Update turn if turnSkip is signaled
        if (turnSkip) {
            if (turn == 1) {
                turn = 2;
            } else if (turn == 2) {
                turn = 1;
            }
            turnSkip = false;
        }

        player1.update();
        player2.update();
    }

    public void input(MouseHandler mouse, KeyHandler key){
        if ( key.up.down){
            System.out.println("'W' is being pressed");
        }

        // Update hover position
        if (mouse.getX() >= PlayState.leftTokenExtremity && mouse.getX() < PlayState.rightTokenExtremity && mouse.getY() >= PlayState.topTokenExtremity && mouse.getY() < PlayState.bottomTokenExtremity) {
            int tokenX = (mouse.getX() - PlayState.leftTokenExtremity) / PlayState.tokenWidth;
            int tokenY = (mouse.getY() - PlayState.topTokenExtremity) / PlayState.tokenHeight;

            if(player1.getTokenValue(tokenX, tokenY) != 1 && player2.getTokenValue(tokenX, tokenY) != 1){
                hoverPos.put("x", tokenX);
                hoverPos.put("y", tokenY);
            } else {
                hoverPos.put("x", -1); // Indicates invalid
                hoverPos.put("y", -1); // Indicates invalid
            }
        } else{
            hoverPos.put("x", -1); // Indicates invalid
            hoverPos.put("y", -1); // Indicates invalid
        }

        // Update turn if Skip is clicked
        if (System.currentTimeMillis() - turnSkipTimer > timeBeforeNextTurnSkip ) {
            if(mouse.getButton() == 1) {
                if (mouse.getX() >= leftSkipExtremity && mouse.getX() < rightSkipExtremity && mouse.getY() >= topSkipExtremity && mouse.getY() < bottomSkipExtremity){
                    turnSkip = true;
                    if(previousTurnSkip){
                        gsm.setCurrentState(1);
                    }
                    previousTurnSkip = turnSkip;
                }
            }
            turnSkipTimer = System.currentTimeMillis();
        }

        // If mouse is pressed in a valid position add token for players turn
        if(mouse.getButton() == 1) {
            if (mouse.getX() >= PlayState.leftTokenExtremity && mouse.getX() < PlayState.rightTokenExtremity && mouse.getY() >= PlayState.topTokenExtremity && mouse.getY() < PlayState.bottomTokenExtremity){
                //tokenPos[(mouse.getX() - PlayState.leftTokenExtremity) / PlayState.tokenWidth][(mouse.getY() - PlayState.topTokenExtremity) / PlayState.tokenHeight] = 1;
                int tokenX = (mouse.getX() - PlayState.leftTokenExtremity) / PlayState.tokenWidth;
                int tokenY = (mouse.getY() - PlayState.topTokenExtremity) / PlayState.tokenHeight;
                if (turn == 1){
                    if(player2.getTokenValue(tokenX, tokenY) == 0 && player1.getTokenValue(tokenX, tokenY) == 0) {
                        player1.setTokenPos(tokenX, tokenY);
                        turn = 2;
                        previousTurnSkip = false; // Set previous turn skip to false because input was valid
                    }
                } else if(turn == 2) {
                    if (player1.getTokenValue(tokenX, tokenY) == 0 && player2.getTokenValue(tokenX, tokenY) == 0){
                        player2.setTokenPos(tokenX, tokenY);
                        turn = 1;
                        previousTurnSkip = false; // Set previous turn skip to false because input was valid
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

        // Render Hover Position
        if(hoverPos.get("x") != -1 && hoverPos.get("y") != -1) {
            g.setColor(Color.CYAN);
            g.drawOval(leftTokenExtremity + hoverPos.get("x") * tokenWidth, topTokenExtremity +  hoverPos.get("y") * tokenHeight, tokenWidth, tokenHeight);
        }

        // Render Skip Button
        g.setColor(Color.BLACK);
        g.drawRect(leftSkipExtremity, topSkipExtremity, skipButtonWidth, skipButtonHeight);
        // Todo : Make it look nice
        g.drawString("Skip Turn", leftSkipExtremity+ 35, topSkipExtremity +20);

        // Render score board
        // Todo : Make it look nice
        g.setColor(Color.BLACK);
        g.drawString("Score : ", 10, 20);
        g.drawString("Player 1 (Black): ", 10, 40);
        g.drawString(Integer.toString(player1.getTotalScore()), 100, 40);
        g.drawString("Player 2 (White): ", 10, 60);
        g.drawString(Integer.toString(player2.getTotalScore()), 100, 60);

        // Render current player turn
        g.drawString("Current turn : ", 10, 150);
        if (turn == 1){
            g.drawString("Player 1 (Black)", 100, 150);
        } else{
            g.drawString("Player 2 (White)", 100, 150);
        }

        // Render Game time
        currentTime = System.currentTimeMillis();
        String timeDiff = Time.printDifference(startTime, currentTime);
        g.drawString("Game Time : " + timeDiff, 1100, 20);

        // Render player Tokens
        player1.render(g);
        player2.render(g);

    }
}
