package com.jungle.game.entity;

import com.jungle.game.states.PlayState;
import com.jungle.game.util.KeyHandler;
import com.jungle.game.util.MouseHandler;

import java.awt.*;

public class Entity {

    protected Color color;
    protected int tokenPos[][];

    public Entity(){
        tokenPos = new int[PlayState.nCross][PlayState.nCross];
    }

    public int getTokenValue(int x, int y){
        return tokenPos[x][y];
    }

    public void setTokenPos(int x, int y){
        if (tokenPos[x][y] == 0){
            tokenPos[x][y] = 1;
        }
    }

    public void resetTokenPos(int x, int y){
        if (tokenPos[x][y] == 1){
            tokenPos[x][y] = 0;
        }
    }

    public void update(){

    }
    public void input(MouseHandler mouse, KeyHandler key) {

    }
    public void render(Graphics2D g){
        g.setColor(color);
        for (int x = 0; x < PlayState.nCross; x++) {
            for (int y = 0; y < PlayState.nCross; y++) {
                if (tokenPos[x][y] == 1){
                    g.fillOval(PlayState.leftTokenExtremity + x * PlayState.tokenWidth, PlayState.topTokenExtremity + y * PlayState.tokenHeight, PlayState.tokenWidth, PlayState.tokenHeight);
                }

            }
        }
    }

}