package com.GO.game.states;

import com.GO.game.util.MouseHandler;
import com.GO.game.util.KeyHandler;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> states;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;

    public static int currentState = 0;

    public GameStateManager(){
        states = new ArrayList<GameState>();
        states.add(new PlayState(this, 0));
        states.add(new GameOverState(this, (PlayState)states.get(PLAY)));
    }

    public void setCurrentState(int state){
        currentState = state;
    }

    public void pop(int state){
        states.remove(state);
    }

    public void add(int state){
        if (state == PLAY){
            states.add(new PlayState(this, 0));
        }
        if (state == MENU){
            states.add(new MenuState(this));
        }
        if (state == PAUSE){
            states.add(new PauseState(this));
        }
        if (state == GAMEOVER){
            states.add(new GameOverState(this, (PlayState)states.get(PLAY)));
        }
    }

    public void addAndPop(int state){
        states.remove(0);
        add(state);
    }

    public void update(){
        states.get(currentState).update();
    }

    public void input(MouseHandler mouse, KeyHandler key){
        states.get(currentState).input(mouse, key);
    }

    public void render(Graphics2D g){
        states.get(currentState).render(g);
    }
}
