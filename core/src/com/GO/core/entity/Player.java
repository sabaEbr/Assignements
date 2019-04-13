package com.GO.core.entity;

import com.GO.core.engine.GoEngine;

import java.awt.*;

public class Player {

    private Color color;
    private boolean isCPU;
    private GoEngine goEngineOwner; // Indicating which game is belongs to

    private int score; // May be needed

    public Player(Color color, boolean isCPU, GoEngine game){
        this.color = color;
        this.isCPU = isCPU;
        goEngineOwner = game;
    }

    public Color getColor() {
        return color;
    }

    public void updateScore(){
        score = goEngineOwner.getScore(this);
    }

    public int getScore() {
        return score;
    }

    public boolean isCPU(){
        return isCPU;
    }

    public void play(){
//        Random rand = new Random();
//        if(isCPU){
//            int x = GoEngine.nCells;
//            int y = GoEngine.nCells;
//
//            while(goEngineOwner.isOccupied(x, y)){
//                x = rand.nextInt(GoEngine.nCells); // randomize x
//                y = rand.nextInt(GoEngine.nCells); // randomize y
//            }
//            goEngineOwner.addStone(x, y, this);
//        }
    }
}