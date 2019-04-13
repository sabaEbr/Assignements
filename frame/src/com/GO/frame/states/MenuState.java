package com.GO.frame.states;

import com.GO.frame.ui.menu_ui.MenuStateUI;
import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;

import java.awt.*;

public class MenuState extends GameState {
    private MenuStateUI menuStateUI;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);

        this.menuStateUI = new MenuStateUI(this);
    }

    public void signalGameSelection(int gameMode, int boardType){
        gsm.newPlaySession(gameMode, boardType);
    }

    public void reset(){
        menuStateUI.setCurrentMenu(0);
    }

    @Override
    public void update() {
        menuStateUI.update();
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        menuStateUI.input(mouse, key);
    }

    @Override
    public void render(Graphics2D g) {
        menuStateUI.render(g);
    }
}
