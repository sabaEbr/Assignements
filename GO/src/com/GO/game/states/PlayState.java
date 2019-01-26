package com.GO.game.states;

import com.GO.game.ui.playstate_ui.Board;
import com.GO.game.entity.Entity;
import com.GO.game.util.*;
import com.GO.game.entity.Stone;

import java.awt.*;
import java.util.ArrayList;


public class PlayState extends GameState {

    // PlayMode : PvP or PvC
    private int gameMode;
    private static final int PvP = 0; //player vs player
    private static final int PvC = 1; //player vs cpu
    private static final int CvP = 2; // cpu vs player

    private Board board;
    private ArrayList<Stone> stones;

    ///////////////////////////////////////////////////// Needs to disappear soon
    public static int nCells = 19;
    public static int cellWidth = 32;
    public static int cellHeight = 32;
    public static int tokenHeight = cellHeight;
    public static int tokenWidth = cellWidth;

    //Grid specs

    public static int bottomGridExtremity = 720/2+nCells* cellHeight /2;
    public static int leftGridExtremity = 1280/2-nCells* cellWidth /2;;

    //Token specs

    public static int bottomTokenExtremity = bottomGridExtremity + tokenHeight/2;
    public static int leftTokenExtremity = leftGridExtremity - tokenWidth/2;


    // Skip button specs
    public static int skipButtonWidth = 3 * cellWidth;
    public static int skipButtonHeight = 3/2 * cellHeight;
    public static int leftSkipExtremity = 150;
    public static int rightSkipExtremity = leftSkipExtremity + skipButtonWidth;
    public static int bottomSkipExtremity = bottomGridExtremity;
    public static int topSkipExtremity = bottomSkipExtremity - cellHeight;
    ///////////////////////////////////////////////////// Needs to disappear soon

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

    public PlayState(GameStateManager gameStateManager, int gameMode, int boardType){

        super(gameStateManager);

        setBoard(boardType);

        this.gameMode = gameMode;

        // Build entities for correct gameMode
        initiateGameMode();

        hoverPos = new Position(-1 , -1);// Initiate to -1 for invalid position

        startTime = System.currentTimeMillis();

        stones = new ArrayList<>();
    }

    public void initiateGameMode (){
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

    public int getGameMode(){
        return gameMode;
    }

    public void setBoard(int boardType){
        board = new Board(boardType, this);
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

    public boolean addStone(Position position, Entity player){
        return addStone(position.getX(), position.getY(), player);
    }

    public boolean addStone(int x, int y, Entity player){
        if (!isOccupied(x, y)){
            stones.add(new Stone(x, y, player, this));
            return true;
        }
        return false;
    }

    public boolean newMove(Position position){
        // Differentiates from addStone() as it handles turn

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
        previousTurnSkip = false; // Set previous turn skip to false because input was valid

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
        if (key.up.down){
            System.out.println("'W' is being pressed");
        }

        // Update hover position
        if (mouse.getX() >= board.leftTokenExtremity && mouse.getX() < board.rightTokenExtremity &&
                mouse.getY() >= board.topTokenExtremity && mouse.getY() < board.bottomTokenExtremity) {
            int tokenX = (mouse.getX() - board.leftTokenExtremity) / board.tokenWidth;
            int tokenY = (mouse.getY() - board.topTokenExtremity) / board.tokenHeight;

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


        player1.input(mouse, key);
        player2.input(mouse, key);

        board.input(mouse, key);

    }

    public void render(Graphics2D g){
        // Board is responsible of it's ui, stones from playState is required
        board.render(g, stones);

        // Render Hover Position
        if(hoverPos.getX() != -1 && hoverPos.getY() != -1) {
            g.setColor(Color.CYAN);
            g.drawOval(board.leftTokenExtremity + hoverPos.getX() * board.tokenWidth,
                    board.topTokenExtremity +  hoverPos.getY() * board.tokenHeight,
                    board.tokenWidth, board.tokenHeight);
        }

        // Render Skip Button
        g.setColor(Color.BLACK);
        g.drawRect(leftSkipExtremity, topSkipExtremity, skipButtonWidth, skipButtonHeight);
        // Todo : Make it look nice
        StringWriter.writeString(g, "Pass", new Position(leftSkipExtremity+35, topSkipExtremity +20));
//        g.drawString("Skip Turn", leftSkipExtremity+ 35, topSkipExtremity +20);

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

        StringWriter.writeString(g, "GO On The GO", new Font("Helvetica",
                        Font.ITALIC | Font.BOLD,22),
                        Color.DARK_GRAY,
                        new Position(1280/2 - 50 , 30));

    }
}
