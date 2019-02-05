package com.GO.frame.ui.playstate_ui;

import com.GO.frame.states.PlayState;
import com.GO.frame.ui.Button;
import com.GO.frame.util.*;

import java.awt.*;

public class PlayStateUI {
    private PlayState playStateInterface;

    private Board board;

    private Button passButton;
    private static double turnSkipTimer = System.currentTimeMillis();
    private static double timeBeforeNextTurnSkip = 200.0;

    public PlayStateUI(int boardType, PlayState playStateInterface){
        this.playStateInterface = playStateInterface;

        setBoard(boardType);

        passButton = new Button(150, 628, 3*32, 32,
                "Pass", 35, 20);
    }

    public void setBoard(int boardType){
        board = new Board(boardType, this);
    }

    public Board getBoard() {
        return board;
    }

    public boolean isOccupied(int x, int y){
        return playStateInterface.isOccupied(x, y);
    }

    public void signalNewMove(int x, int y){
        playStateInterface.newMove(x, y);
    }

    public void update(){

    }

    public void input(MouseHandler mouse, KeyHandler key){
        board.input(mouse, key);

        if (System.currentTimeMillis() - turnSkipTimer > timeBeforeNextTurnSkip ) {
            if (passButton.isClicked(mouse)) {
                System.out.println("Turn Skip Signaled");
                playStateInterface.signalTurnSkip();
            }
            turnSkipTimer = System.currentTimeMillis();
        }

        passButton.input(mouse);
    }

    public void render(Graphics2D g){
        // Board is responsible of it's ui, stones from playState is required
        board.render(g, playStateInterface.getGoEngine().getStones());

        passButton.render(g);

        // Print out the page title
        StringWriter.writeString(g, "GO on the GO", new Font("Helvetica",
                        Font.ITALIC | Font.BOLD,22),
                        Color.DARK_GRAY,
                        1280/2 - 50, 30);

        // Render Game time
        StringWriter.writeString(g, "Game Time : " + playStateInterface.getGameTime(), 1100, 20);

        // Render score board
        StringWriter.writeString(g, "Score : ", 10, 20);
        StringWriter.writeString(g, "Player 1 (Black): ", 10, 40);
        StringWriter.writeString(g, Integer.toString(playStateInterface.getScoreP1()), 110, 40);
        StringWriter.writeString(g, "Player 2 (White): ", 10, 60);
        StringWriter.writeString(g, Integer.toString(playStateInterface.getScoreP2()), 110, 60);

        // Render current player turn
        if (playStateInterface.getTurn() == 1){
            StringWriter.writeString(g,"<", 130, 40);
        } else{
            StringWriter.writeString(g,"<", 130, 60);
        }
    }
}
