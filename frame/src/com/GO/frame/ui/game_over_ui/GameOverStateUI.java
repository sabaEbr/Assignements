package com.GO.frame.ui.game_over_ui;

import com.GO.frame.states.GameOverState;
import com.GO.frame.ui.common_ui.Button;
import com.GO.frame.ui.common_ui.UI;
import com.GO.frame.ui.playstate_ui.Board;
import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;
import com.GO.frame.ui.common_ui.StringWriter;

import java.awt.*;

public class GameOverStateUI extends UI {
    private GameOverState gameOverStateInterface;

    private Button mainMenuButton;

    private Board board;

    public GameOverStateUI(GameOverState gameOverState){
        this.gameOverStateInterface = gameOverState;

        this.mainMenuButton = new Button(150, 628, 3*32, 32,
                "Main Menu", 20, 20);
        buttonsList.add(mainMenuButton);

        this.board = gameOverState.getPlayState().getPlayStateUI().getBoard();
    }

    public void update(){

    }

    public void input(MouseHandler mouse, KeyHandler key){
        super.input(mouse, key);

        if(mainMenuButton.isClicked(mouse)){
            gameOverStateInterface.gotoMenu();
        }
    }

    public void render(Graphics2D g){
        super.render(g);


        StringWriter.writeString(g, "-- Game Ended -- ", 10, 20);
        StringWriter.writeString(g, "Winner : ", 10, 40);
        if(gameOverStateInterface.getPlayState().getWinner() == 1){
            StringWriter.writeString(g, "Player 1 (Black) ", 80, 40);
        }else {
            StringWriter.writeString(g, "Player 2 (White) ", 80, 40);
        }
        StringWriter.writeString(g, "Final Score - Player 1 : " + gameOverStateInterface.getPlayState().getScoreP1(),
                10, 100);
        StringWriter.writeString(g, "              Player 2 : " + gameOverStateInterface.getPlayState().getScoreP2(),
                10, 120);

        // Render Game time
        StringWriter.writeString(g, "Final Game Time : " + gameOverStateInterface.getPlayState().getGameTime(),
                10, 70);

        board.render(g, gameOverStateInterface.getPlayState().getGoEngine().getStones());
    }
}
