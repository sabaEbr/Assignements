package com.GO.frame.states;

import com.GO.frame.ui.playstate_ui.Board;
import com.GO.frame.ui.playstate_ui.PlayStateUI;
import com.GO.frame.util.*;
import com.GO.core.entity.Player;
import com.GO.core.entity.Stone;
import com.GO.core.engine.GoEngine;

import java.awt.*;
import java.util.ArrayList;


public class PlayState extends GameState {
    private GoEngine goEngine;

    private PlayStateUI playStateUI;

    // PlayMode : PvP or PvC
    private int gameMode;
    public static final int PvP = 0; //player vs player
    public static final int PvC = 1; //player vs cpu
    public static final int CvP = 2; // cpu vs player
    public static final int ONL = 3; // online

    // Game time variable
    private static long startTime;
    private static long currentTime;
    private String gameTimeString;


    public PlayState(GameStateManager gameStateManager, int gameMode, int boardType){
        super(gameStateManager);

        startTime = System.currentTimeMillis();

        this.gameMode = gameMode;

        playStateUI = new PlayStateUI(boardType, this);

        goEngine = new GoEngine(gameMode, playStateUI.getBoard().getnCells());

    }

    public GoEngine getGoEngine() {
        return goEngine;
    }

    public PlayStateUI getPlayStateUI(){
        return playStateUI;
    }

    public int getGameMode(){
        return gameMode;
    }

    public int getScoreP1(){
        return goEngine.getScoreP1();
    }

    public int getScoreP2(){
        return goEngine.getScoreP2();
    }

    public int getWinner(){
        return goEngine.getWinner();
    }

    public boolean isOccupied(int x, int y){
        return goEngine.isOccupied(x, y);
    }

    public boolean newMove(int x, int y){
        return goEngine.newMove(x, y);
    }

    public void signalTurnSkip(){
        goEngine.triggerTurnSwitch();
    }

    public int getTurn(){
        return goEngine.getTurn();
    }

    public String getGameTime(){
        return gameTimeString;
    }

    public void update(){
        // goEngine needs to be updated for cpu player
        goEngine.update();

        // If game if Done
        if(goEngine.getStatus() == 1){
            Time.delay();
            gsm.gotoGameOverState();
        }

        // Update game time
        currentTime = System.currentTimeMillis();
        gameTimeString = Time.printDifference(startTime, currentTime);
    }

    public void input(MouseHandler mouse, KeyHandler key){
        if (key.up.down){
            System.out.println("'W' is being pressed");
        }

        playStateUI.input(mouse, key);

    }

    public void render(Graphics2D g){
        playStateUI.render(g);
    }
}
