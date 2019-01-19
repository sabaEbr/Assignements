package com.GO.game.states;

import com.GO.game.board.Board;
import com.GO.game.entity.Entity;
import com.GO.game.entity.Position;
import com.GO.game.entity.Stone;
import com.GO.game.graphics.Font;
import com.GO.game.util.KeyHandler;
import com.GO.game.util.MouseHandler;
import com.GO.game.util.Time;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayState extends GameState {

    // PlayMode : PvP or PvC
    private static int gameMode;
    private static final int PvP = 0; //player vs player
    private static final int PvC = 1; //player vs cpu
    private static final int CvP = 2; // cpu vs player

    private Font font;
    private Board board;
    private ArrayList<Stone> stones;

    ///////////////////////////////////////////////////// Needs to disappear soon
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
    ///////////////////////////////////////////////////// Needs to disappear soon


    // Skip button specs
    public static int skipButtonWidth = 4 * cellWidth;
    public static int skipButtonHeight = 3/2 * cellHeight;
    public static int leftSkipExtremity = 1280/2 - skipButtonWidth / 2;
    public static int rightSkipExtremity = 1280/2 + skipButtonWidth / 2;
    public static int topSkipExtremity = bottomTokenExtremity + cellHeight;
    public static int bottomSkipExtremity = topSkipExtremity + skipButtonHeight;

    private Entity player1;
    private Entity player2;

    private Position hoverPos;

    // Turn management variables
    private static int turn = 1;
    private static boolean turnSkip = false;
    private static boolean previousTurnSkip = false;
    private static double turnSkipTimer = System.currentTimeMillis();
    private static double timeBeforeNextTurnSkip = 200.0;

    // Game time variable
    private static long startTime;
    private static long currentTime;

    public PlayState(GameStateManager gameStateManager, int gameMode){

        super(gameStateManager);
        //font = new Font("font/font_magic.jpg", 64, 64);

        PlayState.gameMode = gameMode;

        // Build entities for correct gameMode
        initiateGameMode(gameMode);

        hoverPos = new Position(-1 , -1);// Initiate to -1 for invalid position

        startTime = System.currentTimeMillis();

        stones = new ArrayList<>();
    }

    public void initiateGameMode (int gameMode){
        // Build entities for correct gameMode
        switch (gameMode){
            case PvP : player1 = new Entity(Color.BLACK, false, this);
                player2 = new Entity(Color.WHITE, false, this);
                break;
            case PvC : player1 = new Entity(Color.BLACK, false, this);
                player2 = new Entity(Color.WHITE, true, this);
                break;
            case CvP: player1 = new Entity(Color.BLACK, true, this);
                player2 = new Entity(Color.WHITE, false, this);
                break;
        }
    }

    public static int getGameMode(){
        return gameMode;
    }

    public Entity getPlayer1(){
        return player1;
    }

    public Entity getPlayer2(){
        return player2;
    }

    public int getScoreP1(){
        return getScore(player1);
    }

    public int getScoreP2(){
        return getScore(player2);
    }

    public int getScore(Entity player){
        int score = 0;
        for(Stone stone : stones) {
            if (stone.getOwner() == player) {
                score++;
            }
        }

        return score;
    }

    public int getWinner(){
        int scoreP1 = getScoreP1();
        int scoreP2 = getScoreP2();

        if(scoreP1 == scoreP2){
            return 0;
        }

        return scoreP1 > scoreP2 ? 1 : 2;
    }

    public boolean isOccupied(Position position){
        for(int i = 0; i < stones.size(); i++){
            if (stones.get(i).getPositionX() == position.getX() && stones.get(i).getPositionY() == position.getY()){
                return true;
            }
        }
        return false;
    }

    public boolean isOccupied(int x, int y){
        for(int i = 0; i < stones.size(); i++){
            if (stones.get(i).getPositionX() == x && stones.get(i).getPositionY() == y){
                return true;
            }
        }
        return false;
    }

    public Stone getStone(Position position){
        return getStone(position.getX(), position.getY());
    }

    public Stone getStone(int x, int y){
        if(isOccupied(x, y)){
           for(int i = 0; i < stones.size(); i++){
               if(stones.get(i).getPositionX() == x && stones.get(i).getPositionY() == y){
                   return stones.get(i);
               }
           }
        }
        // Be careful to verify if isOccupied before using this function as it may be deprecated (java sucks)
        return null;
    }

    public boolean addStone(int x, int y, Entity player){
        if (!isOccupied(x, y)){
            stones.add(new Stone(x, y, player, this));
            return true;
        }
        return false;
    }

    public boolean removeStone(Stone stone){
        if (isOccupied(stone.getPositionX(), stone.getPositionY())){
            stones.remove(stone);
            return true;
        }
        return false;
    }

    public void cleanUp(ArrayList<Stone> stonesToRem){
        for(int i = 0; i < stonesToRem.size(); i++){
            removeStone(stonesToRem.get(i));
        }
    }

    public void update(){

        // Update turn if turnSkip is signaled
        if (turnSkip) {
            if (turn == 1) {
                turn = 2;
            } else if (turn == 2) {
                turn = 1;
            }
            turnSkip = false;
        }

        //Allow CPU players to play if is there turn
        if(turn == 1 && player1.isCPU()){
            player1.play();
            turn = 2;
        } else if(turn == 2 && player2.isCPU()){
            player2.play();
            turn = 1;
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

            if(!isOccupied(tokenX, tokenY)){
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
                int tokenX = (mouse.getX() - PlayState.leftTokenExtremity) / PlayState.tokenWidth;
                int tokenY = (mouse.getY() - PlayState.topTokenExtremity) / PlayState.tokenHeight;
                if (turn == 1){
                    if(!isOccupied(tokenX, tokenY)) {
                        addStone(tokenX, tokenY, player1);
                        getStone(tokenX, tokenY).verifySurrounding(); // Verify surrounding of new stone added to board
                        turn = 2;
                        previousTurnSkip = false; // Set previous turn skip to false because input was valid
                    }
                } else if(turn == 2) {
                    if (!isOccupied(tokenX, tokenY)){
                        addStone(tokenX, tokenY, player2);
                        getStone(tokenX, tokenY).verifySurrounding(); // Verify surrounding of new stone added to board
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
        if(hoverPos.getX() != -1 && hoverPos.getY() != -1) {
            g.setColor(Color.CYAN);
            g.drawOval(leftTokenExtremity + hoverPos.getX() * tokenWidth, topTokenExtremity +  hoverPos.getY() * tokenHeight, tokenWidth, tokenHeight);
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
        g.drawString(Integer.toString(getScoreP1()), 100, 40);
        g.drawString("Player 2 (White): ", 10, 60);
        g.drawString(Integer.toString(getScoreP2()), 100, 60);

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

        // Render tokens Tokens
        for(int i = 0; i < stones.size(); i++){
            stones.get(i).render(g);
        }

    }
}
