package com.GO.frame.states;

import com.GO.frame.util.MouseHandler;
import com.GO.frame.util.KeyHandler;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> states;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;

    public static int currentState;

    public GameStateManager(){
        states = new ArrayList<>();
        states.add(new PlayState(this, 0, 2));
        states.add(new MenuState(this));
        states.add(new PauseState(this));
        states.add(new GameOverState(this, (PlayState)states.get(PLAY)));

        setCurrentState(MENU);
    }

    public void setCurrentState(int state){
        currentState = state;
    }

    public void gotoMenu(){
        states.set(MENU, null); // to delete the old PlayState object by garbage collector (maybe useless further document java practices)
        states.set(MENU, new MenuState(this));
        currentState = MENU;
    }

    public void gotoGameOverState(){
        states.set(GAMEOVER, null); // to delete the old PlayState object by garbage collector (maybe useless further document java practices)
        states.set(GAMEOVER, new GameOverState(this, (PlayState)states.get(PLAY)));
        currentState = GAMEOVER;
    }

    public void gotoPlayState(){
        currentState = PLAY;
    }

    public void newPlaySession(int gameMode, int boardType){
        states.set(PLAY, null); // to delete the old PlayState object by garbage collector (maybe useless further document java practices)
        states.set(PLAY, new PlayState(this, gameMode, boardType));
        currentState = PLAY;
    }

    public void pop(int state){
        states.remove(state);
    }

    public void add(int state){
        if (state == PLAY){
            states.add(new PlayState(this, 0, 0));
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