package com.GO.frame.states;

import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;

import java.awt.Graphics2D;

public abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm){
        this.gsm = gsm;
    }

    public abstract void update();
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D g);

}
