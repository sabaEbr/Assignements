package com.GO.frame.ui.menu_ui;

import com.GO.frame.ui.Button;
import com.GO.frame.ui.UI;
import com.GO.frame.ui.playstate_ui.Board;
import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;

import java.awt.*;

public class BoardMenuUI extends UI {
    private MenuStateUI menuStateUI;

    private Button xNineButton;
    private Button xThirteenButton;
    private Button xNineteenButton;

    public BoardMenuUI(MenuStateUI menuStateUI){
        super();

        this.menuStateUI = menuStateUI;

        xNineButton = new Button(1280/2-80, 150, 160, 40,
                "9 x 9", 68, 25);
        xThirteenButton = new Button(1280/2-80, 230, 160, 40,
                "13 x 13", 60, 25);
        xNineteenButton = new Button(1280/2-80, 310, 160, 40,
                "19 x 19", 60, 25);

        buttonsList.add(xNineButton);
        buttonsList.add(xThirteenButton);
        buttonsList.add(xNineteenButton);
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        super.input(mouse, key);

        if (xNineButton.isClicked(mouse)) {
            menuStateUI.setBoardSelection(Board.xNine);
            menuStateUI.signalGameSelection();
        } else if (xThirteenButton.isClicked(mouse)) {
            menuStateUI.setBoardSelection(Board.xThirteen);
            menuStateUI.signalGameSelection();
        } else if(xNineteenButton.isClicked(mouse)){
            menuStateUI.setBoardSelection(Board.xNineteen);
            menuStateUI.signalGameSelection();
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }
}
