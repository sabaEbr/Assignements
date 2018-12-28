package com.jungle.game.states;

import com.jungle.game.GamePanel;
import com.jungle.game.util.KeyHandler;
import com.jungle.game.util.MouseHandler;
import com.jungle.game.util.Vector2f;

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
        states.add(new PlayState(this));
    }

    public void pop(int state){
        states.remove(state);
    }

    public void add(int state){
        if (state == PLAY){
            states.add(new PlayState(this));
        }
        if (state == MENU){
            states.add(new MenuState(this));
        }
        if (state == PAUSE){
            states.add(new PauseState(this));
        }
        if (state == GAMEOVER){
            states.add(new GameOverState(this));
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
