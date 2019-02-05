package com.GO.core.engine;

import com.GO.core.entity.Player;
import com.GO.core.util.*;
import com.GO.core.entity.Stone;

import java.awt.*;
import java.util.ArrayList;

/* Go Game Engine
* API for cross platform application*
* */
public class GoEngine {

    // PlayMode : PvP or PvC
    private int gameMode;
    public static final int PvP = 0; //player vs player
    public static final int PvC = 1; //player vs cpu
    public static final int CvP = 2; // cpu vs player

    private ArrayList<Stone> stones;

    private Player player1;
    private Player player2;

    // Turn management variables
    private int turn;
    private static boolean turnSkipMonitor;

    // Need the nCells for setting validity bounds
    private int nCells;

    // Status : 0 = In Progress, 1 = End of Game, -1 = Game Error
    private int status;


    public GoEngine(int gameMode, int nCells){
        this.gameMode = gameMode;
        this.nCells = nCells;

        // Build entities for correct gameMode
        initiateGameMode();

        stones = new ArrayList<>(); // Initialise to empty ArrayList<>

        // Turn Monitoring Init
        turn = 1;
        turnSkipMonitor = false;

        // Init Status to in progress
        status = 0;
    }

    public void initiateGameMode (){
        // Build entities for correct gameMode
        switch (gameMode){
            case PvP : player1 = new Player(Color.BLACK, false, this);
                player2 = new Player(Color.WHITE, false, this);
                break;
            case PvC : player1 = new Player(Color.BLACK, false, this);
                player2 = new Player(Color.WHITE, true, this);
                break;
            case CvP: player1 = new Player(Color.BLACK, true, this);
                player2 = new Player(Color.WHITE, false, this);
                break;
        }
    }

    public int getGameMode(){
        return gameMode;
    }

    public int getnCells() {
        return nCells;
    }

    public int getTurn(){
        return turn;
    }

    public int getStatus() {
        return status;
    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public int getScoreP1(){
        return getScore(player1);
    }

    public int getScoreP2(){
        return getScore(player2);
    }

    public int getScore(Player player){
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
        return isOccupied(position.getX(), position.getY());
    }

    public boolean isOccupied(int x, int y){
        for(Stone stone: stones){
            if(stone.getPositionX() == x && stone.getPositionY() == y){
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
           for(Stone stone: stones){
               if(stone.getPositionX() == x && stone.getPositionY() == y){
                   return stone;
               }
           }
        }
        // Be careful to verify if isOccupied before using this function as it may be deprecated (java sucks)
        return null;
    }

    public ArrayList<Stone> getStones(){
        return stones;
    }

    public void setStones(ArrayList<Stone> stones){
        this.stones = stones;
    }

    public boolean positionValidity(Position position){
        return (position.getX() >= 0 && position.getX() <= nCells &&
                position.getY() >= 0 && position.getY() <= nCells);
    }

    public boolean addStone(Position position, Player player){
        return addStone(position.getX(), position.getY(), player);
    }

    public boolean addStone(int x, int y, Player player){
        if (!isOccupied(x, y)){
            stones.add(new Stone(x, y, player, this));
            return true;
        }
        return false;
    }

    public boolean newMove(int x, int y){
        // Differentiates from addStone() as it handles turn
        Position position = new Position(x, y);

        if (turn == 1){
            if (addStone(position, player1)){
                turn = 2;
            } else {
                return false;
            }
        } else if(turn == 2){
            if (addStone(position, player2)){
                turn = 1;
            } else {
                return false;
            }
        }
        getStone(position).verifySurrounding(); // Verify surrounding of new stone added to board
        getStone(position).verifySuicideMove(); // Verify if stone is suicidal
        turnSkipMonitor = false;
        return true;
    }

    public boolean removeStone(Stone stone){
        if (isOccupied(stone.getPositionX(), stone.getPositionY())){
            stones.remove(stone);
            return true;
        }
        return false;
    }

    public void cleanUp(ArrayList<Stone> stonesToRem){
        for(Stone stone : stonesToRem){
            removeStone(stone);
        }
    }

    public void capitulate(ArrayList<Position> positionToAssign, Player player){
        for(Position position : positionToAssign){
            addStone(position, player);
        }
    }

    public void triggerTurnSwitch(){
        turn = turn == 1 ? 2 : 1;   // Triggers turn change because only  1 & 2 are defined
        if(turnSkipMonitor){
            endGame();
        }else{
            turnSkipMonitor = true;
        }
    }

    public void update(){
        //Allow CPU players to play if is there turn
        if(turn == 1 && player1.isCPU()){
            player1.play();
            turn = 2;
        } else if(turn == 2 && player2.isCPU()){
            player2.play();
            turn = 1;
        }
    }

    public void endGame(){
        // Cannot use for each because involves concurrentModification during handling
        for(int i = 0; i < stones.size(); i++){
            stones.get(i).verifyPossession();
        }

        status = 1; // Signal End of game
    }

}
