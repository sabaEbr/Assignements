package com.GO.frame.states;

import com.GO.frame.ui.game_over_ui.GameOverStateUI;
import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;

import java.awt.*;

public class GameOverState extends GameState {
    private PlayState playState; // Each GameOverState is associated to his playState

    private GameOverStateUI gameOverStateUI;

    public GameOverState(GameStateManager gameStateManager, PlayState playState){
        super(gameStateManager);
        this.playState = playState;

        gameOverStateUI = new GameOverStateUI(this);
    }

    public void gotoMenu(){
        gsm.gotoMenu();
    }

    public PlayState getPlayState() {
        return playState;
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        gameOverStateUI.input(mouse, key);
    }

    @Override
    public void render(Graphics2D g) {
        gameOverStateUI.render(g);
    }
}
