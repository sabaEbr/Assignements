package com.GO.core.engine;

import com.GO.core.entity.Player;
import com.GO.core.util.*;
import com.GO.core.entity.Stone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/* Go Game Engine
* API for cross platform application*
* */
public class GoEngine {

    protected long gameID;
    protected byte nPlayers;

    // PlayMode : PvP or PvC
    protected int gameMode;

    private ArrayList<Stone> stones;

    private Player player1;
    private Player player2;

    // Turn management variables
    protected int turn;
    private static boolean turnSkipMonitor;

    // Need the nCells for setting validity bounds
    private int nCells;

    // Status : 0 = In Progress, 1 = End of Game, -1 = Game Error, 2 = Searching
    protected int status;
    protected boolean playValidity; // Block game from being played may be used for online or bot


    public GoEngine(int gameMode, int nCells){
        this.gameMode = gameMode;
        this.nCells = nCells;

        stones = new ArrayList<>(); // Initialise to empty ArrayList<>

        // Turn Monitoring Init
        turn = 1;
        turnSkipMonitor = false;

        // Init Status to in progress
        status = 0;
        // Init playValidity to True;
        playValidity = true;

        player1 = new Player(Color.BLACK, false, this);
        player2 = new Player(Color.WHITE, false, this);
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

    public boolean getPlayValidity(){
        return playValidity;
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

    public void hardSetStones(ArrayList<Stone> stones){
        this.stones = stones;
    }

    public void softSetStones(ArrayList<Stone> newStones){
        for(Stone stone : this.stones){
            if(!Stone.listContains(newStones, stone)){
                removeStone(stone);
            }
        }
        for(Stone newStone : newStones){
            if(!Stone.listContains(this.stones, newStone)){
                this.stones.add(newStone);
            }
        }
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

    }

    public void endGame(){
        // Cannot use for each because involves concurrentModification during handling
        for(int i = 0; i < stones.size(); i++){
            stones.get(i).verifyPossession();
        }

        status = 1; // Signal End of game
    }

    @SuppressWarnings("unchecked")
//    Todo: fix unchecked warning
    public String marshall(){
        String messageT = "";

        for(Stone stone : stones){
            messageT += stone.marshall();
        }

        JSONObject jo = new JSONObject();

        jo.put("GameID", gameID);
        jo.put("NPlayers", nPlayers);
        jo.put("Turn", turn);
        jo.put("TurnSkip", turnSkipMonitor);
        jo.put("Status", status);
        jo.put("MessageT", messageT);

        return jo.toJSONString();
    }

    public void unMarshall(String jsonData) throws Exception{
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonData);

        gameID = (long)jo.get("GameId");
        nPlayers = ((Long)jo.get("NPlayers")).byteValue();
        turn = ((Long)jo.get("Turn")).intValue();
        turnSkipMonitor = (Boolean)jo.get("TurnSkip");
        status = ((Long)jo.get("Status")).intValue();

        ArrayList<String> stonesRep = new ArrayList<>(Arrays.asList(((String)jo.get("MessageT")).split("\\)")));

        if (!stonesRep.contains("")){
            ArrayList<Stone> newStones = new ArrayList<>();
            for(String stoneRep : stonesRep){
                String col = stoneRep.split("\\(")[0];
                int posX = Integer.valueOf(stoneRep.split("\\(")[1].split("x")[0]);
                int posY = Integer.valueOf(stoneRep.split("\\(")[1].split("x")[1]);
                if(col.equals(Integer.toString(player1.getColor().getRGB()))){
                    newStones.add(new Stone(posX, posY, player1, this));
                } else if(col.equals(Integer.toString(player2.getColor().getRGB()))){
                    newStones.add(new Stone(posX, posY, player2, this));
                }
            }
            softSetStones(newStones);
        }
    }

}
