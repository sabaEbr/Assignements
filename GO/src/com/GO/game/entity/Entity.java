package com.GO.game.entity;

import com.GO.game.util.KeyHandler;
import com.GO.game.util.MouseHandler;
import com.GO.game.states.PlayState;

import java.awt.*;
import java.util.Random;

public class Entity {

    private Color color;
    private boolean isCPU;
    private PlayState playStateOwner; // Indicating which game is belongs to

    private int score; // May be needed

    public Entity(Color color, boolean isCPU, PlayState game){
        this.color = color;
        this.isCPU = isCPU;
        playStateOwner = game;
    }

    public Color getColor() {
        return color;
    }

    public void updateScore(){
        score = playStateOwner.getScore(this);
    }

    public int getScore() {
        return score;
    }

    public boolean isCPU(){
        return isCPU;
    }

    public void play(){
        Random rand = new Random();
        if(isCPU){
            int x = PlayState.nCells;
            int y = PlayState.nCells;

            while(playStateOwner.isOccupied(x, y)){
                x = rand.nextInt(PlayState.nCells); // randomize x
                y = rand.nextInt(PlayState.nCells); // randomize y
            }
            playStateOwner.addStone(x, y, this);
        }
    }

    public void update(){
        updateScore();
    }
    public void input(MouseHandler mouse, KeyHandler key) {

    }
    public void render(Graphics2D g){

    }

}