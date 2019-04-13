package com.GO.frame.ui.common_ui;


import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;

import java.awt.*;


public class ScoreUI extends UI{

    private byte posX;
    private byte posY;

    public ScoreUI(byte x, byte y){

    }

    public void update(){

    }

    public void input(MouseHandler mouse, KeyHandler key){
        super.input(mouse, key);
    }

    public void render(Graphics2D g, byte score1, byte score2, boolean showWinner){
        super.render(g);
    }
}
