package com.GO.game.states;

import com.GO.game.util.KeyHandler;
import com.GO.game.util.MouseHandler;

import java.awt.*;

public class GameOverState extends GameState {
    public GameOverState(GameStateManager gameStateManager){
        super(gameStateManager);
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString("-- Game Ended -- ", 10, 20);
        g.drawString("Winner : ", 10, 40);
        if(PlayState.getWinner() == 1){
            g.drawString("Player 1 (Black) ", 80, 40);
        }else {
            g.drawString("Player 2 (Black) ", 80, 40);
        }

        g.drawString("Final Score - Player 1 : " + PlayState.player1.getTotalScore(), 10, 100);
        g.drawString("              Player 2 : " + PlayState.player2.getTotalScore(), 10, 120);
    }
}
